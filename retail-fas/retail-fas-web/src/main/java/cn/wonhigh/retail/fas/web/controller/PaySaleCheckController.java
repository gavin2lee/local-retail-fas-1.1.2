package cn.wonhigh.retail.fas.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.PaySaleCheck;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.manager.PaySaleCheckManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-17 18:00:37
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/pay_sale_check")
@ModuleVerify("30170023")
public class PaySaleCheckController extends ExcelImportController<PaySaleCheck> {
	@Resource
	private PaySaleCheckManager paySaleCheckManager;
	@Override
	public CrudInfo init() {
		return new CrudInfo("IndepShop_management/pay_sale_check/", paySaleCheckManager);
	}

	@Override
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		}
		
		List<Integer> businessTypeList = new ArrayList<Integer>();
    	//0-正常销售 1-跨店销售
		businessTypeList.add(Integer.valueOf(0));
		businessTypeList.add(Integer.valueOf(1));
		businessTypeList.add(Integer.valueOf(2));
		businessTypeList.add(Integer.valueOf(6));
		params.put("businessTypeList", businessTypeList);
		List<Integer> statusList = new ArrayList<Integer>();
		//30-已收银 41-已发货 1-处理中 2-已确认
		statusList.add(Integer.valueOf(30));
		statusList.add(Integer.valueOf(41));
		params.put("statusList", statusList);
		
		//modify by wang.m 添加自收银店铺过滤条件
		params.put("isCashRegister", String.valueOf(ShardingUtil.isPE()));
		
		//查询总记录数
		PaySaleCheck paySaleCheck = paySaleCheckManager.findPaySaleCheckCount(params);
		List<PaySaleCheck> paySaleCheckList = new ArrayList<PaySaleCheck>();
		//合计对象
		List<PaySaleCheck> footer = new ArrayList<PaySaleCheck>();
		if (paySaleCheck != null && paySaleCheck.getTotal() > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, paySaleCheck.getTotal());
			paySaleCheckList = paySaleCheckManager.findPaySaleCheckList(page, sortColumn, sortOrder, params);
			//合计手续费和实收金额计算
			paySaleCheck.setCompanyName("合计");
			footer.add(paySaleCheck);
			//按终端号显示小计
			paySaleCheckList = this.paySaleCheckListByTerminalNumber(paySaleCheckList);
		}
		map.put("total", paySaleCheck.getTotal());
		map.put("rows", paySaleCheckList);
		map.put("footer", footer);

		return map;
	}

	@RequestMapping({ "/do_save" })
	public ResponseEntity<Map<String, Boolean>> do_save(HttpServletRequest req) throws Exception {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		Map<CommonOperatorEnum, List<PaySaleCheck>> params = this.convertToMap(req);
		String type = req.getParameter("flag");
		List<PaySaleCheck> paySaleCheckList = new ArrayList<PaySaleCheck>();

		for (Entry<CommonOperatorEnum, List<PaySaleCheck>> param : params.entrySet()) {
			if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
				paySaleCheckList = params.get(CommonOperatorEnum.UPDATED);
			}
		}
		if (params.size() > 0) {
			paySaleCheckManager.updateData(paySaleCheckList,type);
		}

		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}

	private List<PaySaleCheck> paySaleCheckListByTerminalNumber(List<PaySaleCheck> paySaleCheckList) {
		PaySaleCheck temp = null;
		Map<String, PaySaleCheck> map = new LinkedHashMap<String, PaySaleCheck>();//终端号
		for (PaySaleCheck paySaleCheck : paySaleCheckList) {
			if (null == map.get(paySaleCheck.getTerminalNumber())) {
				temp = new PaySaleCheck();
				this.initPaySaleCheck(temp);
				temp.setCompanyName("小计");
				temp.setTerminalNumber(paySaleCheck.getTerminalNumber());// 设置终端号
				temp.setCreditCardRate(paySaleCheck.getCreditCardRate());//设置费率
				map.put(paySaleCheck.getTerminalNumber(), temp);
			}
			this.sumToPaySaleCheck(map.get(paySaleCheck.getTerminalNumber()), paySaleCheck);
		}
		//按终端账号汇总到列表底部
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			PaySaleCheck paySaleCheckObject = map.get(key);
			String terminalNumber = paySaleCheckObject.getTerminalNumber();
			int index = -1;//某品牌最后一个下标
			for (PaySaleCheck o : paySaleCheckList) {
				if (null!=o.getTerminalNumber()&&null!=terminalNumber&&terminalNumber.equals(o.getTerminalNumber())) {
					index = paySaleCheckList.indexOf(o);
				}
			}
			if (index > -1) {
				paySaleCheckList.add(index + 1, paySaleCheckObject);
			}
		}
		return paySaleCheckList;
	}
    
	@Override
	protected List<PaySaleCheck> queryExportData(Map<String, Object> params) throws ManagerException {
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		}
		
		List<Integer> businessTypeList = new ArrayList<Integer>();
    	//0-正常销售 1-跨店销售
		businessTypeList.add(Integer.valueOf(0));
		businessTypeList.add(Integer.valueOf(1));
		businessTypeList.add(Integer.valueOf(2));
		businessTypeList.add(Integer.valueOf(6));
		params.put("businessTypeList", businessTypeList);
		List<Integer> statusList = new ArrayList<Integer>();
		//30-已收银 41-已发货 1-处理中 2-已确认
		statusList.add(Integer.valueOf(30));
		statusList.add(Integer.valueOf(41));
		params.put("statusList", statusList);
		
		//modify by wang.m 添加自收银店铺过滤条件
		params.put("isCashRegister", String.valueOf(ShardingUtil.isPE()));
		
		//查询总记录数
		PaySaleCheck paySaleCheck = paySaleCheckManager.findPaySaleCheckCount(params);
		List<PaySaleCheck> paySaleCheckList = new ArrayList<PaySaleCheck>();
		if (paySaleCheck != null && paySaleCheck.getTotal() > 0) {
			SimplePage page = new SimplePage(1, paySaleCheck.getTotal(), paySaleCheck.getTotal());
			paySaleCheckList = paySaleCheckManager.findPaySaleCheckList(page, null, null, params);
			//合计手续费和实收金额计算
			paySaleCheck.setCompanyName("合计");
			//按终端号显示小计
			paySaleCheckList = this.paySaleCheckListByTerminalNumber(paySaleCheckList);
			paySaleCheckList.add(paySaleCheck);
		}
		return paySaleCheckList;
	}
    
	private void sumToPaySaleCheck(PaySaleCheck mainPaySaleCheck, PaySaleCheck addPaySaleCheck) {
		if (null != mainPaySaleCheck.getAmount() && null != addPaySaleCheck.getAmount()) {
			mainPaySaleCheck.setAmount(mainPaySaleCheck.getAmount().add(addPaySaleCheck.getAmount()));
		}

		if (null != mainPaySaleCheck.getPoundage() && null != addPaySaleCheck.getPoundage()) {
			mainPaySaleCheck.setPoundage(mainPaySaleCheck.getPoundage().add(addPaySaleCheck.getPoundage()));
		}

		if (null != mainPaySaleCheck.getPaidinAmount() && null != addPaySaleCheck.getPaidinAmount()) {
			mainPaySaleCheck.setPaidinAmount(mainPaySaleCheck.getPaidinAmount().add(addPaySaleCheck.getPaidinAmount()));
		}

	}

	private void initPaySaleCheck(PaySaleCheck temp) {
		temp.setAmount(new BigDecimal(0d));
		temp.setPoundage(new BigDecimal(0d));
		temp.setCreditCardRate(new BigDecimal(0d));
		temp.setPaidinAmount(new BigDecimal(0d));
	}

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return new String[] { "", "", "", "", "" };
	}

	@Override
	protected boolean doBatchAdd(List<PaySaleCheck> list) {
		// TODO Auto-generated method stub
		return false;
	}
}