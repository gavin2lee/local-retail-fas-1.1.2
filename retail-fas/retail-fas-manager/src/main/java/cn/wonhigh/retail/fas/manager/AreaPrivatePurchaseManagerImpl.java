/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：AreaPrivatePurchaseManagerImpl  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-11-7 上午11:01:35  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.SelfPurchaseDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.service.AreaPrivatePurchaseService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
@Service("areaPrivatePurchaseManager")
public class AreaPrivatePurchaseManagerImpl extends BaseCrudManagerImpl
		implements AreaPrivatePurchaseManager {
	@Resource
	private AreaPrivatePurchaseService areaPrivatePurchaseService;
	
	@Override
	protected BaseCrudService init() {
		return areaPrivatePurchaseService;
	}
	@Override
	public List<BillBuyBalance> findTotalRow(Map<String, Object> params) {
		return areaPrivatePurchaseService.findTotalRow(params);
	}
	@Override
	public int modifyCost(SelfPurchaseDto dto) {
		// 查询到货单号
		if (dto != null) {
			BillBuyBalance asnBill = areaPrivatePurchaseService.findAsnBill(dto);
			if (asnBill != null) {
				dto.setRefBillNo(asnBill.getRefBillNo());
				areaPrivatePurchaseService.modifyAsnBillCost(dto);
			}
		}
		return 	areaPrivatePurchaseService.modifyReceiptBillCost(dto);
	}
	
}
