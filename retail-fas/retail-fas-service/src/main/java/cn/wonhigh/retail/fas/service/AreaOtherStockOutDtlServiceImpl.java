/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaOtherStockOutDtlImpl  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-10 下午2:17:55  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.dal.database.AreaOtherStockOutDtlMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
@Service("areaOtherStockOutDtlService")
public class AreaOtherStockOutDtlServiceImpl extends BaseCrudServiceImpl implements
		AreaOtherStockOutDtlService {
	@Resource 
	private AreaOtherStockOutDtlMapper areaOtherStockOutDtlMapper;
	@Override
	public BaseCrudMapper init() {
		return areaOtherStockOutDtlMapper;
	}
	@Override
	public List<BillSaleBalance> findTotalRow(Map<String, Object> params) {
		return areaOtherStockOutDtlMapper.selectTotalRow(params);
	}

}
