/**  
*   
* 项目名称：retail-fas-manager  
* 类名称：AreaOtherStockOutDtlManagerImpl  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-10 下午2:15:13  
* @version       
*/ 
package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.service.HqOtherStockOutDtlService;

import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
@Service("hqOtherStockOutDtlManager")
public class HqOtherStockOutDtlManagerImpl extends BaseCrudManagerImpl
		implements HqOtherStockOutDtlManager {
	@Resource
	private HqOtherStockOutDtlService hqOtherStockOutDtlService;
	@Override
	protected BaseCrudService init() {
		return hqOtherStockOutDtlService;
	}
	@Override
	public List<BillSaleBalance> findTotalRow(Map<String, Object> params) {
		return hqOtherStockOutDtlService.findTotalRow(params);
	}

}
