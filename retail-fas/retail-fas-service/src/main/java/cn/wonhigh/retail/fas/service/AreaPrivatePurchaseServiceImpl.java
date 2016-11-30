/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaPrivatePurchaseServiceImpl  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-11-7 上午11:03:35  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.SelfPurchaseDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.dal.database.AreaPrivatePurchaseMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
@Service("areaPrivatePurchaseService")
public class AreaPrivatePurchaseServiceImpl extends BaseCrudServiceImpl
		implements AreaPrivatePurchaseService {
	@Resource
	private AreaPrivatePurchaseMapper areaPrivatePurchaseMapper;
	
	@Override
	public BaseCrudMapper init() {
		return areaPrivatePurchaseMapper;
	}
	
	@Override
	public List<BillBuyBalance> findTotalRow(Map<String, Object> params) {
		return areaPrivatePurchaseMapper.selectTotalRow(params);
	}
	
	@Override
	public int modifyReceiptBillCost(SelfPurchaseDto dto) {
		return areaPrivatePurchaseMapper.updateReceiptBillCost(dto);
	}
	
	@Override
	public void modifyAsnBillCost(SelfPurchaseDto dto) {
		areaPrivatePurchaseMapper.updateAsnBillCost(dto);
	}

	@Override
	public BillBuyBalance findAsnBill(SelfPurchaseDto dto) {
		return areaPrivatePurchaseMapper.selectAsnBill(dto);
	}

}
