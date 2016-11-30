package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BLKPeriodBalance;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface BaroqueCompanyPeriodBalanceMapper extends  BaseCrudMapper{
	
	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByPage(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params) ;
	
	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceByItemNo(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params) ;

	
	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooter(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params) ;
	
	List<BLKPeriodBalance> getBaroqueCompanyPeriodBalanceFooterItem(@Param("page") SimplePage page, @Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("params") Map<String, Object> params) ;
	
	Integer getBaroqueCompanyPeriodBalanceCount(@Param("params") Map<String, Object> params);
	
	Integer getBaroqueCompanyPeriodBalanceCountItem(@Param("params") Map<String, Object> params);

}
