package cn.wonhigh.retail.fas.dal.database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.BillBacksectionSplitDtl;

import com.yougou.logistics.base.common.model.AuthorityParams;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-13 15:01:44
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
public interface BillBacksectionSplitDtlMapper extends BaseCrudMapper {
	
	/**
	 * 查询合计
	 * @param params
	 * @return
	 */
	BillBacksectionSplitDtl selectTotalSum(@Param("params")Map<String, Object> params);
	
	public BigDecimal sumPaymentAmount(@Param("params")Map<String, Object> params);
	
	int selectShopBlanaceAccountCount(@Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams);

	List<BillBacksectionSplitDtl> selectShopBlanaceAccountByPage(@Param("page")SimplePage page, 
			@Param("orderByField")String sortColumn,
			@Param("orderBy")String sortOrder, @Param("params")Map<String, Object> params, 
			@Param("authorityParams") AuthorityParams authorityParams);
	
	/**
	 * 批量查询出新增明细数据
	 * @param params
	 * @return
	 */
	List<BillBacksectionSplitDtl> selectAddInsertDtl(@Param("params")Map<String, Object> params);
	
	/**
	 * 根据回款单编号删除明细
	 * @param backsectionNo
	 * @return
	 */
	int deleteByBacksectionNo(String backsectionNo);
}