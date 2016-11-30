package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BAReceiptDto;

import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author wangm
 * @date 2016-04-07 12:00:45
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
public interface BAReceiptMapper {

	int findReceiptItemCount(@Param("params") Map<String, Object> params);

	int findReceiptBillCount(@Param("params") Map<String, Object> params);

	List<BAReceiptDto> findReceiptItemList(@Param("page") SimplePage page, @Param("orderByField") String sortColumn,
			@Param("orderBy") String sortOrder, @Param("params") Map<String, Object> params);

	List<BAReceiptDto> findReceiptBillList(@Param("page") SimplePage page, @Param("orderByField") String sortColumn,
			@Param("orderBy") String sortOrder, @Param("params") Map<String, Object> params);

	List<BAReceiptDto> findReceiptFooter(@Param("params") Map<String, Object> params);

	List<BAReceiptDto> selectNeedUpdatePurchaseList(@Param("originalBillNo") String originalBillNo);

	int updateCostById(BAReceiptDto dto);

	List<BAReceiptDto> selectNeedUpdateRateList(@Param("originalBillNo") String originalBillNo);

	int updateRateById(BAReceiptDto dto);

	int updateBuyBalanceNo(@Param("params") Map<String, Object> params);

	String[] getItemNos(@Param("originalBillNo") String originalBillNo);

	void updateBuyBalanceNoByItem(@Param("params") Map<String, Object> params);
	
	List<BAReceiptDto> getReceiptForUpdate(@Param("params") Map<String, Object> params);
	
	void updateBuyBalanceTagPrice(@Param("params") Map<String, Object> params);
}