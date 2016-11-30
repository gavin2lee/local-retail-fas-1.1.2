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

import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.CompanyBrandSettlePeriodManager;

import com.yougou.logistics.base.common.exception.ManagerException;


@Controller
@RequestMapping("/brand_utils")
public class BrandWebApiController extends WebApiBaseController {

	@Resource
	private CompanyBrandSettlePeriodManager companyBrandSettlePeriodManager;
	
	/**
	 * 判断公司品牌是否已关账
	 * 
	 * @param request
	 * @param brandUnitName,year,month,companyNo,brandNo
	 * @return
	 */
	@RequestMapping(value = "/check_brand_unit_settle_date")
	@ResponseBody
	public Map<String, Object> checkBrandUnitSettleDate(HttpServletRequest request,Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = super.builderParams(request, model);
		result.put("success", true);
		try {
			List<CompanyBrandSettlePeriod> list = companyBrandSettlePeriodManager.findByBiz(null, params);
			if (!CollectionUtils.isEmpty(list)) {
				// 判断财务关账日
				int year = Integer.parseInt(params.get("year").toString());
				int month = Integer.parseInt(params.get("month").toString());
				if (list.get(0).getAccountSettleTime().after(DateUtil.getFirstDayOfMonth(year, month))) {
					result.put("success", false);
					result.put("message", "公司" + params.get("brandUnitName").toString() + "品牌部财务结账日已关账！");
				}
			} else {
				result.put("success", false);
				result.put("message", "公司品牌部结账日未维护！");
			}
		} catch (ManagerException e) {
			logger.error(e.getMessage()+e.getStackTrace());
			result.put("success", false);
			result.put("message", "公司品牌部结账日查询失败！");
		}
		return result;
	}
}
