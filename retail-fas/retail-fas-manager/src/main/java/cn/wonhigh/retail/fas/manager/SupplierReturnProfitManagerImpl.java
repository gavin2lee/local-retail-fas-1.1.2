package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.slf4j.ext.XLogger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillInvoiceDtl;
import cn.wonhigh.retail.fas.common.model.PayRelationship;
import cn.wonhigh.retail.fas.common.model.SupplierReturnProfit;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.service.BillInvoiceDtlService;
import cn.wonhigh.retail.fas.service.PayRelationshipService;
import cn.wonhigh.retail.fas.service.SupplierReturnProfitService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-04-19 10:35:13
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("supplierReturnProfitManager")
class SupplierReturnProfitManagerImpl extends BaseCrudManagerImpl implements SupplierReturnProfitManager {
    @Resource
    private SupplierReturnProfitService supplierReturnProfitService;
    
    @Resource
    private BillInvoiceDtlService billInvoiceDtlService;
    
    @Resource
    private PayRelationshipService payRelationshipService;

    @Override
    public BaseCrudService init() {
        return supplierReturnProfitService;
    }

	@Override
	public int addSupplierReturnProfit(PayRelationship payRelationShip,
			BigDecimal rate, String type, Integer returnProfitType) throws ManagerException {
		try {
			SupplierReturnProfit supplierReturnProfit = new SupplierReturnProfit();
			supplierReturnProfit.setId(UUIDGenerator.getUUID());
			supplierReturnProfit.setCompanyNo(payRelationShip.getBuyerNo());
			supplierReturnProfit.setCompanyName(payRelationShip.getBuyerName());
			supplierReturnProfit.setSupplierNo(payRelationShip.getSupplierNo());
			supplierReturnProfit.setSupplierName(payRelationShip.getSupplierName());
			supplierReturnProfit.setBillNo(payRelationShip.getBusinessBillNo());
			supplierReturnProfit.setItemNo(payRelationShip.getItemNo());
			supplierReturnProfit.setBrandNo(payRelationShip.getBrandNo());
			supplierReturnProfit.setGenerateDate(new Date());
			if("0".equals(type)){//结算价
				supplierReturnProfit.setAmount(payRelationShip.getAmount().multiply(rate));
			}else if("1".equals(type)){//厂商金额
				supplierReturnProfit.setAmount(payRelationShip.getSupplierAmount().multiply(rate));
			}else if("2".equals(type)){//牌价
				supplierReturnProfit.setAmount(payRelationShip.getTagAmount().multiply(rate));
			}
			supplierReturnProfit.setRemark("");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", payRelationShip.getBusinessBillNo());
			//根据结算单查询发票信息
			List<BillInvoiceDtl> invoiceList = billInvoiceDtlService.findByBiz(null, params);
			if(invoiceList.size()>0){
				BillInvoiceDtl dtl = invoiceList.get(0);
				supplierReturnProfit.setIsInvoiced(0);//已开票
				supplierReturnProfit.setInvoiceNo(dtl.getInvoiceNo());
				supplierReturnProfit.setInvoiceDate(dtl.getInvoiceDate());
			}else{
				supplierReturnProfit.setIsInvoiced(1);//未开票
			}
			SystemUser user = CurrentUser.getCurrentUser();
			supplierReturnProfit.setUpdateUser(user.getLoginName());
			supplierReturnProfit.setUpdateTime(getCurrentDateTime());
			supplierReturnProfit.setReturnType(returnProfitType);//返利生成方式
			
			return supplierReturnProfitService.add(supplierReturnProfit);
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}

	@Override
	public void deleteGenerateReturnProfit(PayRelationship payRelationShip, SupplierReturnProfit supplierReturnProfit) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			if(payRelationShip != null || supplierReturnProfit != null){
				params.put("companyNo", payRelationShip!=null?payRelationShip.getBuyerNo() : supplierReturnProfit.getCompanyNo());
				params.put("supplierNo", payRelationShip!=null?payRelationShip.getSupplierNo() : supplierReturnProfit.getSupplierNo());
				params.put("billNo", payRelationShip!=null?payRelationShip.getBusinessBillNo() : supplierReturnProfit.getBillNo());
				params.put("itemNo", payRelationShip!=null?payRelationShip.getItemNo() : supplierReturnProfit.getItemNo());
				params.put("brandNo", payRelationShip!=null?payRelationShip.getBrandNo() : supplierReturnProfit.getBrandNo());
				supplierReturnProfitService.deleteSupplierReturnProfit(params);
			}
		} catch (ServiceException e) {
			throw new ManagerException();
		}
	}
	
	@Override
	public List<PayRelationship> findReturnProfit(Map<String, Object> obj) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("businessBillType", "1301");//到货单
		params.put("supplierNo", obj.get("supplierNo"));
		params.put("buyerNo", obj.get("buyerNo"));
		params.put("sendDateStart", obj.get("sendDateStart"));
		params.put("sendDateEnd", obj.get("sendDateEnd"));
		params.put("itemNo", obj.get("itemNo"));
		params.put("itemCode", obj.get("itemCode"));
		String brandNos = (String) obj.get("brandNo");
		params.put("brandNos", FasUtil.formatInQueryCondition(brandNos));
		List<PayRelationship> list = new ArrayList<PayRelationship>();
		try {
			PayRelationship payRelationship = payRelationshipService.findReturnProfitCount(params);
			if(payRelationship.getTotal()>0){
				SimplePage page = new SimplePage(1, payRelationship.getTotal(), payRelationship.getTotal());
				list = payRelationshipService.findReturnProfitList(page, null, null, params);
			}
		} catch (ServiceException e) {
			throw new ManagerException();
		}
		return list;
	}
	
	private Date getCurrentDateTime() throws ManagerException{
		try {
			return DateUtil.getCurrentDateTime();
		} catch (Exception e) {
			throw new ManagerException("获取当前时间失败", e);
		}
	}
}