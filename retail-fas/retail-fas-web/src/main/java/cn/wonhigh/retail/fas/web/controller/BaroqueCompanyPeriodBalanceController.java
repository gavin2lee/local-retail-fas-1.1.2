package cn.wonhigh.retail.fas.web.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BaroqueCompanyPeriodBalanceManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Controller
@ModuleVerify("34000009")
@RequestMapping("/baroque_company_period_balance")
public class BaroqueCompanyPeriodBalanceController extends BaseController<BLKPeriodBalance> {

	@Resource
	private BaroqueCompanyPeriodBalanceManager baroqueCompanyPeriodBalanceManager;

	@RequestMapping({ "/baroque_cpb_list" })
	public String list() {
		return "baroque_company_period_balance/baroque_cpb_list";
	}

	@Override
	protected CrudInfo init() {
		return new CrudInfo("baroque_company_period_balance/", baroqueCompanyPeriodBalanceManager);
	}

	@RequestMapping(value = "/baroque_company_period_balance.json")
	@ResponseBody
	public Map<String, Object> queryBaroqueCompanyPeriodBalance(HttpServletRequest req, Model model)
			throws ManagerException {
		int total = 10;
		SimplePage page = null;
		List<BLKPeriodBalance> list = null;
		List<BLKPeriodBalance> footerList = null;

		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);

		String year = StringUtils.isEmpty(req.getParameter("year")) ? "" : req.getParameter("year");
		String month = StringUtils.isEmpty(req.getParameter("month")) ? "" : req.getParameter("month");
		Date currDate = DateUtil.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, -1);
		String lastYear = String.valueOf(cal.get(Calendar.YEAR));
		String lastMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		
		params.put("preYear", lastYear);
		params.put("preMonth", lastMonth);
		params.put("effectiveTimeLst", DateUtil.getLastDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
		params.put("effectiveTime", DateUtil.getFirstDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
		params.put("preMonthFirstDay", DateUtil.getFirstDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)-1));

		if (StringUtils.isNotEmpty(req.getParameter("brandNos"))) {
			params.put("brandNos", Arrays.asList(req.getParameter("brandNos").split(",")));
		}
		if(("B0000").equals(req.getParameter("companyNo"))){
			total = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceCountItem(params);
			if(total>0){
			page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceByItemNo(page, sortColumn,
					sortOrder, params);	
			footerList = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceFooterItem(page, sortColumn,
					sortOrder, params);
			}
		}else {
			total = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceCount(params);
			if(total>0){
			page = new SimplePage(pageNo, pageSize, (int) total);
			list = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceByPage(page, sortColumn,
					sortOrder, params);	
			footerList = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceFooter(page, sortColumn,
					sortOrder, params);
			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", this.baroqueCompanyPeriodBalanceManager.setExtendsPeriodBalanceProperties(list));
		obj.put("footer",footerList);
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
		params.put("effectiveTimeLst", DateUtil.getLastDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
		params.put("effectiveTime", DateUtil.getFirstDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)));
		params.put("preMonthFirstDay", DateUtil.getFirstDayOfMonthStr(Integer.valueOf(year), Integer.valueOf(month)-1));
		
		if (StringUtils.isNotEmpty(String.valueOf(params.get("brandNos")))) {
			params.put("brandNos", Arrays.asList(String.valueOf(params.get("brandNos")).split(",")));
		}
		List<BLKPeriodBalance> result = null;
		Integer total = 0;
		List<BLKPeriodBalance> footer;
		if(("B0000").equals(params.get("companyNo"))){
		    total = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceCountItem(params);
			result = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceByItemNo(new SimplePage(1,total,total), null,
					null, params);		
			result = this.baroqueCompanyPeriodBalanceManager.setExtendsPeriodBalanceProperties(result);
			footer = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceFooterItem(new SimplePage(1,total,total), null,
					null, params);
		}else {
			total = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceCount(params);
			result = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceByPage(new SimplePage(1,total,total), null,
					null, params);		
			result = this.baroqueCompanyPeriodBalanceManager.setExtendsPeriodBalanceProperties(result);
		    footer = this.baroqueCompanyPeriodBalanceManager.getBaroqueCompanyPeriodBalanceFooter(new SimplePage(1,total,total), null,
					null, params);
		}
	
		if(null!= footer){
			result.add(footer.get(0));
		}
		return result;
    }
}
