/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaOtherStockOutDtlService  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-10 下午2:17:18  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillSalesSum;
import cn.wonhigh.retail.fas.common.model.SalesSummary;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.service.BaseCrudService;

public interface SalesSummaryService extends BaseCrudService {
	/**
	 * @param params
	 * @return
	 * @throws ServiceException 
	 */
	SalesSummary selectSalesSummaryCount(Map<String,Object> params,AuthorityParams authorityParams)throws ManagerException;

	
	List<SalesSummary> selectSalesSummary(SimplePage page, @Param("params") Map<String,Object> params)  throws ManagerException ;

	int selectCount(Map<String, Object> params) throws  ManagerException ;

}
