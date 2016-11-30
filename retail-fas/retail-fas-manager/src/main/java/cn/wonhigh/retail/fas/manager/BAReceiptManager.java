package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.dto.BAReceiptDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途
 * 
 * @author ouyang.zm
 * @date 2014-08-25 12:10:54
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
public interface BAReceiptManager {

	int findReceiptCount(Map<String, Object> params) throws ManagerException;

	List<BAReceiptDto> findReceiptList(SimplePage page, String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException;

	List<BAReceiptDto> findReceiptFooter(Map<String, Object> params) throws ManagerException;

	int generateBalance(List<BAReceiptDto> lstChecked, Map<String, Object> params) throws ManagerException;

	String[] getItemNos(String originalBillNos) throws ManagerException;
}