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

import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.DepositCashManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-22 13:51:56
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/deposit_cash")
@ModuleVerify("30170021")
public class DepositCashController extends ExcelImportController<DepositCash> {
	@Resource
	private DepositCashManager depositCashManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("IndepShop_management/deposit_cash/", depositCashManager);
	}

	@Override
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {

		Map<String, Object> map = new HashMap<String, Object>();

		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));

		Map<String, Object> params = builderParams(req, model);
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		}
		DepositCash depositCash=depositCashManager.findDetailCount(params);
		List<DepositCash> depositCashList = new ArrayList<DepositCash>();
		//合计对象
		List<DepositCash> footer=new ArrayList<DepositCash>();
		if(depositCash!=null&&depositCash.getTotal()>0){
			SimplePage page = new SimplePage(pageNo, pageSize, depositCash.getTotal());
			depositCashList=depositCashManager.findDetail(page, null, null, params);
			depositCashList = this.getSaleAmountByOutDate(depositCashList);
			depositCash.setShopNo("合计");
			footer.add(depositCash);
			//按账号显示小计金额
			depositCashList = this.detailListByAccount(depositCashList,params);
		}
		map.put("total", depositCash.getTotal());
		map.put("rows", depositCashList);
		map.put("footer", footer);

		return map;
	}
	
	private List<DepositCash> getSaleAmountByOutDate(List<DepositCash> depositCashList) {
		return depositCashManager.getSaleAmountByOutDate(depositCashList);
	}

	@RequestMapping({ "/do_save" })
	public ResponseEntity<Map<String, Boolean>> do_save(HttpServletRequest req) throws Exception {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		Map<CommonOperatorEnum, List<DepositCash>> params = this.convertToMap(req);
		List<DepositCash> depositCashList = new ArrayList<DepositCash>();

		for (Entry<CommonOperatorEnum, List<DepositCash>> param : params.entrySet()) {
			if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
				depositCashList = params.get(CommonOperatorEnum.UPDATED);
			}
		}
		if (params.size() > 0) {
			depositCashManager.updateData(depositCashList);
		}

		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}
	
	@Override
    protected List<DepositCash> queryExportData(Map<String, Object> params) throws ManagerException {
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		//页面选择了特定的店铺
		if(StringUtils.isNotEmpty(shopNo)){
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		}
		
		DepositCash depositCash=depositCashManager.findDetailCount(params);
		List<DepositCash> depositCashList = new ArrayList<DepositCash>();
		if(depositCash!=null&&depositCash.getTotal()>0){
			SimplePage page = new SimplePage(1, depositCash.getTotal(), depositCash.getTotal());
			depositCashList=depositCashManager.findDetail(page, null, null, params);
			depositCashList = this.getSaleAmountByOutDate(depositCashList);
			//按账号显示小计金额
			depositCashList = this.detailListByAccount(depositCashList,params);
			depositCash.setShopNo("合计");
			depositCashList.add(depositCash);
		}
		return depositCashList;
	}

	private List<DepositCash> detailListByAccount(List<DepositCash> depositCashList,Map<String, Object> params) {
		String shopNo = (String) params.get("isCheckShopNo");
		String depositAccount = (String) params.get("isCheckDepositAccount");
		String depositDate = (String) params.get("isCheckDepositDate");
		String[] groupBy = {};
		if(!"true".equals(shopNo) && !"true".equals(depositAccount) && !"true".equals(depositDate)){
			return depositCashList;
		}else{
			groupBy=new String[]{shopNo,depositAccount,depositDate};
		}
		
		DepositCash temp = null;
		Map<String, DepositCash> map = new LinkedHashMap<String, DepositCash>();//存入账号分组
		Map<String, DepositCash> t = new LinkedHashMap<String, DepositCash>();//存入账号分组
		for (DepositCash depositCash : depositCashList) {
			if (null == map.get(this.getGroupBy(depositCash,groupBy))) {
				temp = new DepositCash();
				this.initOrderDtlDto(temp);
				temp.setShopNo(depositCash.getShopNo());
				temp.setAccount(depositCash.getAccount());
				temp.setDepositDate(depositCash.getDepositDate());
				map.put(this.getGroupBy(depositCash,groupBy), temp);
				t.put("temp", new DepositCash());
			}
			this.sumToOrderDtlDto(map.get(this.getGroupBy(depositCash,groupBy)), depositCash, t);
			t.put("temp", depositCash);
		}
		//按存入账户汇总到列表底部
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			DepositCash depositCashObject = map.get(key);
			int index = -1;//某存入账号最后一个下标
			for (DepositCash depositCash : depositCashList) {
				if (!"小计".equals(depositCash.getShopNo()) && this.getGroupBy(depositCashObject,groupBy).equals(this.getGroupBy(depositCash,groupBy))) {
					index = depositCashList.indexOf(depositCash);
				}
			}
			if (index > -1) {
				depositCashObject.setShopNo("小计");
				depositCashObject.setAccount(null);
				depositCashObject.setSaleAmount(null);
				depositCashObject.setDepositDiff(null);
				//depositCashObject.setDepositDiff(depositCashObject.getSaleAmount().subtract(depositCashObject.getAmount()));
				depositCashList.add(index + 1, depositCashObject);
			}
		}
		return depositCashList;
	}

	private String getGroupBy(DepositCash depositCash, String[] groupBys) {
		String groupBy = "";
		if("true".equals(groupBys[0])){
			groupBy = groupBy.concat(depositCash.getShopNo());
		}
		if("true".equals(groupBys[1])){
			groupBy = groupBy.concat(depositCash.getAccount());
		}
		if("true".equals(groupBys[2])){
			groupBy = groupBy.concat(depositCash.getDepositDate().toString());
		}
		return groupBy;
	}

	private void sumToOrderDtlDto(DepositCash mainDepositCash, DepositCash addDepositCash, Map<String, DepositCash> map) {
		if (null != mainDepositCash.getAmount() && null != addDepositCash.getAmount()) {
			mainDepositCash.setAmount(mainDepositCash.getAmount().add(addDepositCash.getAmount()));
		}
//		if (null != mainDepositCash.getSaleAmount() && null != addDepositCash.getSaleAmount()) {
//			DepositCash temp = map.get("temp");
//			String str = addDepositCash.getShopNo() + addDepositCash.getAccount() + addDepositCash.getStartOutDate()+addDepositCash.getEndOutDate();
//			if (temp.getShopNo() == null && temp.getAccount() == null && temp.getStartOutDate() == null && temp.getEndOutDate() == null) {
//				mainDepositCash.setSaleAmount(mainDepositCash.getSaleAmount().add(addDepositCash.getSaleAmount()));
//			}else if(!str.equals(temp.getShopNo() + temp.getAccount() + temp.getStartOutDate() + temp.getEndOutDate())){
//				mainDepositCash.setSaleAmount(mainDepositCash.getSaleAmount().add(addDepositCash.getSaleAmount()));
//			}
//		}
		
	}

	private void initOrderDtlDto(DepositCash depositCash) {
		depositCash.setAmount(new BigDecimal(0d));
		depositCash.setSaleAmount(new BigDecimal(0d));
		depositCash.setDepositDiff(new BigDecimal(0d));
	}

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<DepositCash> list) {
		// TODO Auto-generated method stub
		return false;
	}
}