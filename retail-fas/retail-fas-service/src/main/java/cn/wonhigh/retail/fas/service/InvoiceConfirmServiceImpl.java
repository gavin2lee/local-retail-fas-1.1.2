/**  
*   
* 项目名称：retail-fas-service  
* 类名称：InvoiceConfirmServiceImpl  
* 类描述：
* 创建人：ouyang.zm  
* 创建时间：2014-12-3 下午3:09:27  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.enums.FasBillTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillCommonInvoiceRegister;
import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.common.model.BillInvoice;
import cn.wonhigh.retail.fas.common.model.BillInvoiceDtl;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.model.InvoiceConfirm;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillInvoiceDtlMapper;
import cn.wonhigh.retail.fas.dal.database.BillInvoiceMapper;
import cn.wonhigh.retail.fas.dal.database.InvoiceConfirmMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
@Service("InvoiceConfirmService")
public class InvoiceConfirmServiceImpl extends BaseCrudServiceImpl implements
		InvoiceConfirmService {
	@Resource
	private InvoiceConfirmMapper invoiceConfirmMapper;
	@Resource
	private BillInvoiceMapper billInvoiceMapper;
	@Resource
	private BillInvoiceDtlMapper billInvoiceDtlMapper;
	@Resource
	private CommonUtilService commonUtilService;
	@Resource
	private BillCommonRegisterDtlService billCommonRegisterDtlService;
	@Resource
	private BillCommonInvoiceRegisterService billCommonInvoiceRegisterService;
	@Resource
	private CurrencyManagementService currencyManagementService;
	
	
	@Override
	public BaseCrudMapper init() {
		return invoiceConfirmMapper;
	}
	
	/**
	 * 到票确认
	 * @throws ServiceException 
	 */
	@Override
	public int updateById(String[] idList, InvoiceConfirm invoiceConfirm,Integer flag) throws ServiceException {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("auditor", invoiceConfirm.getAuditor());
		params.put("auditDate", invoiceConfirm.getAuditDate());
		params.put("confirmFlag", flag);
		int count=0;
		for(int i=0;i<idList.length;i++){
			params.put("id", idList[i]);
			//查询发票登记所在的库
			BillCommonInvoiceRegister bcir=new BillCommonInvoiceRegister();
			bcir.setId(idList[i]);
			BillCommonInvoiceRegister invoiceRegister=billCommonInvoiceRegisterService.findById(bcir);
			//设置所在库
			params.put("shardingFlag", invoiceRegister.getShardingFlag());
		    count=invoiceConfirmMapper.updateInvoiceConfirm(params);
		}
		return count;
	}
	
	/**
	 * 生成采购发票
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=ServiceException.class)
	public Map<String,Object> generateBill(List<BillCommonInvoiceRegister> oList, String loginName)
			throws ServiceException {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		List<String> billList=new ArrayList<String>();
		CurrencyManagement cm=currencyManagementService.findDefaultCurrency();
		try {
			if(!CollectionUtils.isEmpty(oList)){
				BillInvoice invoice = null;
				String billNo = "";
				for (BillCommonInvoiceRegister obj : oList) {
					invoice = new BillInvoice();
					billNo = commonUtilService.getNewBillNoCompanyNo(obj.getBuyerNo(), FasBillTypeEnums.PI.getRequestId());
					invoice.setId(UUIDGenerator.getUUID());
					invoice.setBillNo(billNo);
					invoice.setBuyerNo(obj.getBuyerNo());
					invoice.setSalerNo(obj.getSalerNo());
					invoice.setBuyerName(obj.getBuyerName());
					invoice.setSalerName(obj.getSalerName());
					invoice.setAmount(obj.getAmount());//发票金额合计
					invoice.setBalanceType(obj.getBalanceType());
					if (cm != null) {
						invoice.setCurrency(cm.getCurrencyCode());
					}
					invoice.setBillDate(new Date());
					invoice.setCreateTime(new Date());
					invoice.setCreateUser(loginName);
					//根据发票登记号查询开票申请的结算单信息
					//查询发票登记所在的库
					InvoiceConfirm invoiceConfirm=invoiceConfirmMapper.selectInvoiceConfirm(obj.getBillNo(),obj.getShardingFlag());
					invoice.setRefBillNo(invoiceConfirm.getBalanceNo());
					invoice.setRefAmount(invoiceConfirm.getBalanceAmount());

					//获取发票登记明细设置采购发票明细
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("billNo", obj.getBillNo());
					List<BillCommonRegisterDtl> lstItem = billCommonRegisterDtlService.findByBiz(null, params);
					BillInvoiceDtl invoiceDtl = null;
					int allQty = 0;
					for (BillCommonRegisterDtl registerDtl : lstItem) {
						invoiceDtl = new BillInvoiceDtl();
						invoiceDtl.setId(null);
						invoiceDtl.setBillNo(billNo);
						invoiceDtl.setInvoiceNo(registerDtl.getInvoiceNo());
						invoiceDtl.setInvoiceCode(registerDtl.getInvoiceCode());
						invoiceDtl.setInvoiceDate(registerDtl.getInvoiceDate());
						invoiceDtl.setInvoiceAmount(registerDtl.getInvoiceAmount());
						invoiceDtl.setTaxRate(registerDtl.getTaxRate());
						invoiceDtl.setNoTaxAmount(registerDtl.getNoTaxAmount());
						invoiceDtl.setTaxAmount(registerDtl.getTaxAmount());
						invoiceDtl.setQty(registerDtl.getQty());
						invoiceDtl.setPrice(registerDtl.getPrice());
						invoiceDtl.setCategoryNo(registerDtl.getCategoryNo());
						invoiceDtl.setCategoryName(registerDtl.getCategoryName());
						//保存采购发票明细
						billInvoiceDtlMapper.insertSelective(invoiceDtl);
						allQty += registerDtl.getQty() == null ? 0 : registerDtl.getQty();
					}
					invoice.setQty(allQty);
					//保存采购发票
					billInvoiceMapper.insertSelective(invoice);
					//回写采购发票号标志到发票登记表
					BillCommonInvoiceRegister bcir=new BillCommonInvoiceRegister();
					bcir.setId(obj.getId().toString());
					bcir.setInvoiceNoFlag(billNo);
					billCommonInvoiceRegisterService.modifyById(bcir);
					
					//回写采购发票号到结算单
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("invoiceNo", billNo);
					param.put("balanceNos", FasUtil.formatInQueryCondition(invoiceConfirm.getBalanceNo()));
					invoiceConfirmMapper.updateInvoiceNoOfBillBalance(param);
					
					billList.add(billNo);
					resultMap.put("billNos", billList);
					resultMap.put("flag", true);
				}
			}else{
				resultMap.put("flag", false);
			}
			return resultMap;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

}
