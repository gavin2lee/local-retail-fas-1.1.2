package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.dto.SettlePathQueryExportDto;
import cn.wonhigh.retail.fas.common.model.SettlePath;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
public interface SettlePathMapper extends BaseCrudMapper {

	List<SettlePath> selectByQueryVo(@Param("params")Map<String, Object> params);
	
	int updateStatus(@Param("params")Map<String, Object> params) throws Exception;

	int doAudit(SettlePath model) throws Exception;

	int doStatus(SettlePathDto model) throws Exception;
	
	int selectRelationCount(@Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams);

	List<SettlePathQueryExportDto> selectRelationByPage(@Param("page")SimplePage page, 
			@Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams);
}