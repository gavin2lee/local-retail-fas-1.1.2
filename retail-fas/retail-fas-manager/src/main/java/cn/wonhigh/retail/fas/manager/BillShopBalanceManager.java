package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceBrand;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDaysaleSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-04 17:20:02
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
public interface BillShopBalanceManager extends BaseCrudManager {
	
	BillShopBalance save(BillShopBalance billShopBalance,List<BillShopBalanceDiff> lstDiff, 
			List<BillShopBalanceDeduct> lstDeduct,List<BillShopBalanceBrand> lstBrand,List<BillShopBalanceDeduct> lstDeductAfter) throws ManagerException;
	
	List<Map<String, String>> batchAdd(List<BillShopBalance> list) throws ManagerException;
	
	int remove(String[] ids,String createUser,Date createTime) throws ManagerException;
	
	List<BillShopBalanceDaysaleSum> findPaymentMethodSum(SimplePage page,String orderByField,String orderBy,Map<String,Object> params) throws ManagerException;
	
	BigDecimal getBalanceDeductAmount(BillShopBalance billShopBalance) throws ManagerException, ServiceException;
	
	BigDecimal getBalanceDiffAmount(BillShopBalance billShopBalance)  throws ManagerException, ServiceException;
	
	BillShopBalance  getNumDataCalc(BillShopBalance billShopBalance) throws ManagerException,ServiceException;
	
	BigDecimal getSumAmount(Map<String,Object> params);

	/**
	 * 修改结算单
	 * @param billShopBalance 结算单对象
	 * @param deductDataMap 票前费用页签对应的数据
	 * @param diffDataMap 结算差异页签对应的数据
	 * @return 修改后的结算单对象
	 * @throws ManagerException 异常 
	 */
	BillShopBalance updateBill(BillShopBalance billShopBalance, Map<CommonOperatorEnum, List<BillShopBalanceDeduct>> deductDataMap,
			Map<CommonOperatorEnum, List<BillShopBalanceDiff>> diffDataMap,Map<CommonOperatorEnum, List<BillShopBalanceBrand>> brandDataMap,
			Map<CommonOperatorEnum, List<BillShopBalanceDeduct>> deductAfterDataMap,
			Map<CommonOperatorEnum, List<BillShopBalanceCateSum>> categoryDataMap) throws ManagerException;
	
	String getMaxMonth(BillShopBalance billShopBalance) throws ManagerException,ServiceException;
	
	/**
	 * 通过店铺和结算单中的终止结算日期,查询是否已生成下期结算单
	 * @param request HttpServletRequest
	 * @return 是否已生成下期结算单的标志
	 * @throws ManagerException 异常
	 */
	int hasNextBalanceDate(Map<String, Object> params) throws ManagerException;
	
	BillShopBalance getBacksectionSplitDeduct(Map<String,Object> params) throws ManagerException;
	
	BigDecimal getPaymentAmount(BillShopBalance billShopBalance)  throws ManagerException;
	
	/**
	 * 查询店铺结算单并用于收款回款拆分
	 * @param params
	 * @return
	 * @throws ManagerException
	 */
	List<BillShopBalance> findShopBalanceDeductAfter(Map<String,Object> params) throws ManagerException;
	
	/**
	 * 校验数据是否已平(表头和明细数据的逻辑数据是否相等)
	 * @param billShopBalance 结算单对象
	 * @return map
	 */
	Map<String, String> validateDataBalance(BillShopBalance billShopBalance);

	/**
	 * 复制结算单
	 * @param copyBill 待复制的结算单对象
	 * @param systemUser 系统登录用户
	 * @return 复制后的结算单对象
	 * @throws ManagerException 异常
	 */
	BillShopBalance copyBill(BillShopBalance copyBill,SystemUser systemUser) throws ManagerException;
	
	BillShopBalanceDaysaleSum getConBaseDeductAmount(Map<String,Object> params) throws ManagerException;
	
	/**
	 * 审核操作：审核时，将结算差异中，本期差异余额为0的数据，默认置为已完成状态
	 * @param billShopBalance 结算单对象
	 * @return 影响的行数
	 * @throws ManagerException 异常
	 */
	int confirm(BillShopBalance billShopBalance) throws ManagerException;

	/**
	 * 批量审核操作：审核时，将结算差异中，本期差异余额为0的数据，默认置为已完成状态
	 * @param list 结算单集合
	 * @return 影响的行数
	 * @throws ManagerException 异常
	 */
	int batchConfirm(List<BillShopBalance> list) throws ManagerException;
	
	
    List<BillShopBalance> selectBlanceList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	int	selectBlanceCount(Map<String,Object> params);
	
	BillShopBalanceCateSum getSalesAmount(BillShopBalance billShopBalance) throws ServiceException;
	
	List<BillShopBalance> checkBackPayTimeoutList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	int checkBackPayTimeoutCount(Map<String,Object> params);
	
	List<BillShopBalance> checkMakeInvoiceTimeoutList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	int checkMakeInvoiceTimeoutCount(Map<String,Object> params);

	BillShopBalanceDeduct getCostDeductTypeAmount(BillShopBalance billShopBalance,BillShopBalanceDeduct billShopBalanceDeduct) throws ServiceException;
	
	String getSystemParamSetValue(Map<String,Object> params)  throws ManagerException, ServiceException;
	
	
	List<BillShopBalance> selectSalesResultList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
	
	int	selectSalesResultCount(Map<String,Object> params);
		
	List<BillShopBalance> selectSalesBackSectionSplitList(SimplePage page,String orderByField,String orderBy,Map<String,Object> params);
			
	int	selectSalesBackSectionSplitCount(Map<String,Object> params);
}