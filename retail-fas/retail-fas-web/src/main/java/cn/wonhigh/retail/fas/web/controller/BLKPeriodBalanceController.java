package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.common.model.PaySaleCheck;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BLKPeriodBalanceManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@RequestMapping("/blk_period_balance")
@ModuleVerify("30120006")
public class BLKPeriodBalanceController extends BaseController<BLKPeriodBalance> {
	
	@Resource
	private BLKPeriodBalanceManager blkPeriodBalanceManager;

	@Override
	protected CrudInfo init() {
		return new CrudInfo("blk_period_balance/",blkPeriodBalanceManager);
	}
	
	@RequestMapping({"/list"})
    public String list() {
    	return "period_balance/blk_list";
    }
	
    @RequestMapping(value = "/blk_period_balance.json")
	@ResponseBody
	public  Map<String, Object> queryBLKPeriodBalances(HttpServletRequest req, Model model) throws ManagerException {
    	BLKPeriodBalance blkPeriodBalance = new BLKPeriodBalance();
    	List<BLKPeriodBalance> list = new ArrayList<BLKPeriodBalance>();
    	List<BLKPeriodBalance> footer = new ArrayList<BLKPeriodBalance>();
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
    	SimplePage page = null;
    	
    	String year = params.get("year").toString();
		String month = params.get("month").toString();
		Date currDate = DateUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, -1);
		String lastYear = String.valueOf(cal.get(Calendar.YEAR));
		String lastMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		
		params.put("preYear", lastYear);
		params.put("preMonth", lastMonth);
		params.put("startDate", DateUtil.getFirstDayOfMonth(currDate));
		params.put("endDate", currDate);
		
		if (StringUtils.isNotEmpty(req.getParameter("brandNos"))) {
			params.put("brandNos", Arrays.asList(req.getParameter("brandNos").split(",")));
		}
		String categoryNos = req.getParameter("categoryNos");
		if (StringUtils.isNotEmpty(categoryNos)) {
			params.put("multiCategorys", Arrays.asList(categoryNos.substring(1).split(",")));
		}
    	//按照小计方式查询
    	blkPeriodBalance.setTotal(0);
		if("true".equals(req.getParameter("isSubTotal")) || "true".equals(req.getParameter("onlySubTotal"))){
			blkPeriodBalance = blkPeriodBalanceManager.findBLKPeriodBalanceSubTotalCount(params);
			if(blkPeriodBalance.getTotal() > 0){
				page = new SimplePage(pageNo, pageSize, blkPeriodBalance.getTotal());
				list = blkPeriodBalanceManager.findBLKPeriodBalanceSubTotalPages(page, null, null, params);
			}
			
		}else {
			blkPeriodBalance = blkPeriodBalanceManager.findBLKPeriodBalanceCount(params);
			if(blkPeriodBalance.getTotal() > 0){
				page = new SimplePage(pageNo, pageSize, blkPeriodBalance.getTotal());
				list = blkPeriodBalanceManager.findBLKPeriodBalancePages(page, null, null, params);
			}
		}
		
		footer.add(blkPeriodBalance);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", blkPeriodBalance.getTotal());
		obj.put("rows", blkPeriodBalanceManager.setExtendsPeriodBalanceProperties(list));
		obj.put("footer", footer);
		return obj;
    }

	/**
	 * 查询导出数据的方法
	 * @param params 查询参数
	 * @return List<ModelType>
	 * @throws ManagerException 异常
	 */
    @Override
	protected List<BLKPeriodBalance> queryExportData(Map<String, Object> params) throws ManagerException {
    	BLKPeriodBalance blkPeriodBalance = new BLKPeriodBalance();
    	List<BLKPeriodBalance> list = new ArrayList<BLKPeriodBalance>();
    	SimplePage page = null;
    	
    	String year = params.get("year").toString();
		String month = params.get("month").toString();
		Date currDate = DateUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, -1);
		String lastYear = String.valueOf(cal.get(Calendar.YEAR));
		String lastMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		
		params.put("preYear", lastYear);
		params.put("preMonth", lastMonth);
		params.put("startDate", DateUtil.getFirstDayOfMonth(currDate));
		params.put("endDate", currDate);
		
		if (StringUtils.isNotEmpty(String.valueOf(params.get("brandNos")))) {
			params.put("brandNos", Arrays.asList(String.valueOf(params.get("brandNos")).split(",")));
		}
		String categoryNos = String.valueOf(params.get("categoryNos"));
		if (StringUtils.isNotEmpty(categoryNos)) {
			params.put("multiCategorys", Arrays.asList(categoryNos.substring(1).split(",")));
		}
    	//按照小计方式查询
    	blkPeriodBalance.setTotal(0);
		if("true".equals(params.get("isSubTotal")) || "true".equals(params.get("onlySubTotal"))){
			blkPeriodBalance = blkPeriodBalanceManager.findBLKPeriodBalanceSubTotalCount(params);
			if(blkPeriodBalance.getTotal() > 0){
				page = new SimplePage(1, blkPeriodBalance.getTotal(), blkPeriodBalance.getTotal());
				list = blkPeriodBalanceManager.findBLKPeriodBalanceSubTotalPages(page, null, null, params);
				
			}
		}else {
			blkPeriodBalance = blkPeriodBalanceManager.findBLKPeriodBalanceCount(params);
			if(blkPeriodBalance.getTotal() > 0){
				page = new SimplePage(1, blkPeriodBalance.getTotal(), blkPeriodBalance.getTotal());
				list = blkPeriodBalanceManager.findBLKPeriodBalancePages(page, null, null, params);
			}
		}
		
		list = blkPeriodBalanceManager.setExtendsPeriodBalanceProperties(list);
		
		blkPeriodBalance.setItemNo("合计");
		list.add(blkPeriodBalance);
		
		return list;
    }


}
