/**  
*   
* 项目名称：retail-fas-service  
* 类名称：HqOtherStockOutDtlServiceImpl  
* 类描述：
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.dal.database.HqOtherStockOutDtlMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
@Service("hqOtherStockOutDtlService")
public class HqOtherStockOutDtlServiceImpl extends BaseCrudServiceImpl implements
		HqOtherStockOutDtlService {
	@Resource 
	private HqOtherStockOutDtlMapper hqOtherStockOutDtlMapper;
	@Override
	public BaseCrudMapper init() {
		return hqOtherStockOutDtlMapper;
	}
	@Override
	public List<BillSaleBalance> findTotalRow(Map<String, Object> params) {
		return hqOtherStockOutDtlMapper.selectTotalRow(params);
	}

}
