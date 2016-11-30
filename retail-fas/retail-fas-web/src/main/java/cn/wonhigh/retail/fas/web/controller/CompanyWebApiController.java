package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.CompanySettlePeriodManager;

import com.yougou.logistics.base.common.exception.ManagerException;

@Controller
@RequestMapping("/company_utils")
public class CompanyWebApiController extends WebApiBaseController {

	@Resource
	private CompanySettlePeriodManager companySettlePeriodManager;
	/**
	 * 判断公司是否已关账
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 * @throws ManagerException
	 *             异常
	 */
	@RequestMapping(value = "/check_settle_date")
	@ResponseBody
	public Map<String, Object> checkCompanySettleDate(HttpServletRequest request, Model model)
			throws ManagerException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("success", true);

		Map<String, Object> companySettleParams = super.builderParams(request, model);
//		companySettleParams.put("companyNo", itemCost.getCompanyNo());
		List<CompanySettlePeriod> settlePeriods = companySettlePeriodManager.findByBiz(null, companySettleParams);
		if (!CollectionUtils.isEmpty(settlePeriods)) {
			// 判断财务关账日
			int year = Integer.parseInt(companySettleParams.get("year").toString());
			int month = Integer.parseInt(companySettleParams.get("month").toString());
			if (settlePeriods.get(0).getAccountSettleTime().after(DateUtil.getFirstDayOfMonth(year, month))) {
				params.put("success", false);
				params.put("message", "公司的财务结账日已关账！");
			}
		} else {
			params.put("success", false);
			params.put("message", "公司的结账日未维护！");
		}
		return params;
	}
}
