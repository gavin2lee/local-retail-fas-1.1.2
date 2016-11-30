package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BalanceDetailDto;
import cn.wonhigh.retail.fas.common.dto.BalanceGatherDto;
import cn.wonhigh.retail.fas.common.model.BillBalance;

import com.yougou.logistics.base.common.utils.SimplePage;


/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-09-05 14:50:55
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface BillBalanceQueryMapper {

	int selectDetailCount(@Param("params")Map<String,Object> params);

	List<BalanceDetailDto> selectDetailList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<BalanceDetailDto> selectDetailFooter(@Param("params")Map<String,Object> params);

	int selectGatherCount(@Param("params")Map<String,Object> params);

	List<BalanceGatherDto> selectGatherList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<BalanceGatherDto> selectGatherFooter(@Param("params")Map<String,Object> params);

	int selectBalanceCount(@Param("params")Map<String,Object> params);

	List<BillBalance> selectBalanceList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<BillBalance> selectBalanceFooter(@Param("params")Map<String,Object> params);

	int selectItemGatherCount(@Param("params")Map<String,Object> params);

	List<BalanceDetailDto> selectItemGatherList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<BalanceDetailDto> selectItemGatherFooter(@Param("params")Map<String,Object> params);

	List<BillBalance> queryBalance(@Param("params")Map<String,Object> params);

	int selectEntryGatherCount(@Param("params")Map<String,Object> params);

	List<BalanceGatherDto> selectEntryGatherList(@Param("page") SimplePage page,@Param("orderByField") String orderByField,@Param("orderBy") String orderBy,@Param("params")Map<String,Object> params);

	List<BalanceGatherDto> selectEntryGatherFooter(@Param("params")Map<String,Object> params);
	
}