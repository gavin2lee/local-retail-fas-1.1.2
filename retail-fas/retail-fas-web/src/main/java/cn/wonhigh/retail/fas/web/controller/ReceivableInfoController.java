/**
 * title:ReceivableInfoController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:地区间收款信息
 * auther:user
 * date:2015-4-9 下午3:59:41
 */
package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.TotalDto;
import cn.wonhigh.retail.fas.common.model.BillBalanceCashPayment;
import cn.wonhigh.retail.fas.manager.AreaAmongBalanceManager;
import cn.wonhigh.retail.fas.manager.BillBalanceCashPaymentManager;

import com.yougou.logistics.base.common.exception.ManagerException;

@Controller
@RequestMapping("/receivable_info")
public class ReceivableInfoController extends BaseController<BillBalanceCashPayment> {
	@Resource
	private BillBalanceCashPaymentManager billBalanceCashPaymentManager;
	@Resource
	private	AreaAmongBalanceManager areaAmongBalanceManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("receivable_info/", billBalanceCashPaymentManager);
	}
	
	/**
	 * 查询收款信息
	 */
	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model)
			throws ManagerException {
		Map<String, Object> obj = super.queryList(req, model);
		Map<String, Object> params = builderParams(req, model);
		List<TotalDto> totalDto=areaAmongBalanceManager.findReceivableAmountTotal(params);
		if (totalDto.size() >= 0) {
			BillBalanceCashPayment billPayment = new BillBalanceCashPayment();
			billPayment.setBillNo("合计");
			billPayment.setReceivableAmount(totalDto.get(0).getTotalSum());
			billPayment.setOverageAmount(totalDto.get(0).getOverAmount());
			List<BillBalanceCashPayment> BillBalanceCashPaymentDtlTotal = new ArrayList<BillBalanceCashPayment>();
			BillBalanceCashPaymentDtlTotal.add(billPayment);
			obj.put("footer", BillBalanceCashPaymentDtlTotal);
		}
		return obj;
	}
}
