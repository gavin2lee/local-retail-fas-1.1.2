package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillInvCostAdjust;
import cn.wonhigh.retail.fas.common.model.Item;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PeriodBalance;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.BLKItemCostManager;
import cn.wonhigh.retail.fas.manager.BillInvCostAdjustManager;
import cn.wonhigh.retail.fas.manager.ItemManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/bill_inv_cost_adjust_blk")
@ModuleVerify("30120305")
public class BillInvCostAdjustBLKController extends BaseController<BillInvCostAdjust> {
	@Resource
	private BillInvCostAdjustManager billInvCostAdjustManager;
	
	@Resource
	private BLKItemCostManager blkItemCostManager;
	
	@Resource
	private ItemManager itemManager;
	
	@Override
	protected CrudInfo init() {
		 return new CrudInfo("bill_inv_cost_adjust_blk/",billInvCostAdjustManager);
	}
	
	@RequestMapping("/list_dtl")
	public String listTabMain() throws ManagerException {
		return "bill_inv_cost_adjust_blk/list_dtl";
	}
	
	@RequestMapping("/batch_delete")
	@ResponseBody
	public Map<String, Boolean> batchDel(HttpServletRequest request) throws Exception {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			String deletedList = StringUtils.isEmpty(request.getParameter("deleted")) ? "" : request.getParameter("deleted");
			List<BillInvCostAdjust> list = convertListWithTypeReference(deletedList, request, BillInvCostAdjust.class);
			billInvCostAdjustManager.delete(list);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 确认单据
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/confirm")
	public ResponseEntity<BillInvCostAdjust> verify(HttpServletRequest request, @ModelAttribute("model") BillInvCostAdjust model) throws Exception {
		model = billInvCostAdjustManager.confirm(model);
		return new ResponseEntity<BillInvCostAdjust>(model, HttpStatus.OK);
	}
	
    /**
	 * 获取开关控制
	 * 
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping("get_controller_flag")
	public Map<String, String> getControllerFlag() throws ManagerException {
		return billInvCostAdjustManager.getControllerFlag();
	}
	
	/**
	 * 新增单据（库存成本调整单）
	 * 
	 * @param req
	 * @param billReceipt
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/post2")
	public ResponseEntity<BillInvCostAdjust> add(HttpServletRequest req, BillInvCostAdjust model)
			throws Exception {
		SystemUser currentUser = CurrentUser.getCurrentUser();
		// 如果单据编号不为空，则修改，否则就新增
		if (StringUtils.isNotBlank(model.getBillNo())) {
			billInvCostAdjustManager.modifyById(model);
		} else {
			model.setCreateUser(currentUser.getUsername());
			model.setShardingFlag(currentUser.getOrganTypeNo() + "_" + model.getCompanyNo().substring(0, 1));
			billInvCostAdjustManager.addFetchId(model);
			// //绑定页面数据
		}
		model = billInvCostAdjustManager.findById(model);// 页面底部要展示
		return new ResponseEntity<BillInvCostAdjust>(model, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping("/find_item_cost")
	public Map<String, Object> findItemCost(HttpServletRequest request) throws ManagerException {
		Map<String, Object> result = new HashMap<String, Object>();
		String billNo = request.getParameter("billNo");
		String styleNo = request.getParameter("styleNo");
		String shardingFlag = request.getParameter("shardingFlag");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billNo", billNo);
		params.put("shardingFlag", shardingFlag);
		List<BillInvCostAdjust> list = billInvCostAdjustManager.findByBiz(null, params);
		BillInvCostAdjust billInvCostAdjust = null;
		if(list.size() > 0){
			billInvCostAdjust = list.get(0);
		}
		ItemCost itemCost = null;
		PeriodBalance periodBalance = null;
		Item item = null;
		if(null != billInvCostAdjust){
			SimplePage page = new SimplePage(1, Integer.MAX_VALUE, 1);
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("companyNo", billInvCostAdjust.getCompanyNo());
			temp.put("year", billInvCostAdjust.getYear());
			temp.put("month", billInvCostAdjust.getMonth());
			temp.put("styleNo", styleNo);
			List<ItemCost> l = blkItemCostManager.findBLKItemCostList(page, null, null, temp);
			if(l.size() > 0){
				itemCost = l.get(0);
			}
			
			periodBalance = billInvCostAdjustManager.findPeriodBalanceBLK(temp);
			
			temp.put("queryCondition", " AND style_no = '" + styleNo + "'");
			//根据款号查找商品信息
			List<Item> items = itemManager.findByBiz(null, temp);
			if(items.size() > 0){
				item = items.get(0);
			}
		}
		if(null != item){
			result.put("brandNo", item.getBrandNo());
			result.put("sizeKind", item.getSizeKind());
		}
		BigDecimal unitCost = BigDecimal.valueOf(0d);
		if(null != itemCost){
			unitCost = itemCost.getUnitCost();
		}
		Integer closingQty = 0;
		if(null != periodBalance){
			closingQty = periodBalance.getOpeningQty()+periodBalance.getPurchaseInQty()+periodBalance.getOuterTransferInQty()+periodBalance.getPurchaseReturnQty();
		}
		result.put("unitCost", unitCost);
		result.put("closingQty", closingQty);//调整的数量
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private List<BillInvCostAdjust> convertListWithTypeReference(String valueList, HttpServletRequest request,
			Class<BillInvCostAdjust> entityClass) throws JsonParseException, JsonMappingException,
			JsonGenerationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(valueList, new TypeReference<List<Map>>() {
		});
		List<BillInvCostAdjust> tl = new ArrayList<BillInvCostAdjust>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BillInvCostAdjust type = mapper.readValue(mapper.writeValueAsString(list.get(i)), entityClass);
			tl.add(type);
		}
		return tl;
	}

}
