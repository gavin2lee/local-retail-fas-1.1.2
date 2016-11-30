package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.mockito.internal.util.StringJoiner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSplit;
import cn.wonhigh.retail.fas.common.model.ShopGroup;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BillBuyBalanceManager;
import cn.wonhigh.retail.fas.manager.BillSplitManager;
import cn.wonhigh.retail.fas.manager.FinancialAccountManager;
import cn.wonhigh.retail.fas.service.BillBuyBalanceService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Led 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/bill_split")
public class BillSplitController extends BaseCrudController<BillSplit> {
	
    @Resource
    private BillSplitManager billSplitManager;
    
    @Resource
    private FinancialAccountManager financialAccountManager;
    
    @Resource
    private BillBuyBalanceManager billBuyBalanceManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_split/",billSplitManager);
    }
    
    /**
     * 查询需要重新拆单的单据
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/selectSplitData")
	@ResponseBody
	public  Map<String, Object>  selectSalerCompany(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		resetBrandNoCondition(params);
		
		//只查询总部地区、总部统采的采购类单据
		String queryCondition = "buyer_no NOT IN (" +
				financialAccountManager.findLeadRoleCompanyNos() +
				") " +
				"AND ((bill_type IN(" +
				BillTypeEnums.ASN.getRequestId() +
				") AND biz_type IN (" +
				BizTypeEnums.FIRST_ORDER.getStatus() +
				"," +
				BizTypeEnums.REPLENISH_ORDER.getStatus() +
				")) OR (bill_type = " +
				BillTypeEnums.RETURNOWN.getRequestId() +
				"))";
		params.put("queryCondition", queryCondition);
		
		int iTotal = billSplitManager.selectSplitBillCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) iTotal);
		List<BillBuyBalance> buyListTemp = billSplitManager.selectSplitBill(page, sortColumn, sortOrder, params);
		
		for(BillBuyBalance billBuyBalance : buyListTemp) {
			//调整总部代采的结算公司
			if(billBuyBalance.getSalerNo().equals(billBuyBalance.getSupplierNo())) {
				billBuyBalance.setSalerNo(billBuyBalance.getBuyerNo());
				billBuyBalance.setSalerName(billBuyBalance.getBuyerName());
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", buyListTemp);
		map.put("total",iTotal);
   		return map;
	}
    
    private void resetBrandNoCondition(Map<String, Object> params){
    	Object val = params.get("brandNo");
    	if( val != null && StringUtils.isNotBlank(String.valueOf(params.get("brandNo")))){
    		params.remove("brandNo");
    		String[] vals = val.toString().split(",");    		
    		String brandNos ="'" + StringUtils.join(vals,"','") + "'";
    		params.put("brandNos", brandNos);
    	}
    }
    /**
     * 根据采购公司、供应商查询结算路径
     * @param req
     * @param model
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/selectSettlePath")
	@ResponseBody
	public Map<String, Object> selectSettlePath(HttpServletRequest req, Model model) throws ManagerException {
    	Map<String, Object> params = builderParams(req, model);
    	resetBrandNoCondition(params);
    	
    	//params.put("auditStatus", FasAduitStatusEnum.ADUIT_STATUS.getValue());
    	//params.put("queryDate", DateUtil.format1(new Date()));
    	
    	List<SettlePathDto> settlePathList = billSplitManager.selectSettlePath(params);
    	
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", settlePathList);
   		return map;
    }
    
    /**
     * 调整结算主体（重新拆单）
     * @param req
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/splitData")
	@ResponseBody
	public Map<String, Object> splitData(HttpServletRequest req, Model model) throws Exception {
    	String checkedRows = req.getParameter("checkedRows");
    	List<Object> billList = convertListWithTypeReference(checkedRows, BillBuyBalance.class);
    	
    	String checkedSettlePath = req.getParameter("checkedSettlePath");
    	List<Object> settlePathList = convertListWithTypeReference(checkedSettlePath, SettlePathDto.class);
    	SettlePathDto settlePath = (SettlePathDto) settlePathList.get(0);
    	
    	Map<String, Object> map = billSplitManager.splitData(billList, settlePath.getPathNo());
    	
		return map;
    }
    
    /**
	 * 转换成泛型列表
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */					 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Object> convertListWithTypeReference(String rows, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = mapper.readValue(rows, new TypeReference<List<Map>>() {});
		List<Object> tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}
}