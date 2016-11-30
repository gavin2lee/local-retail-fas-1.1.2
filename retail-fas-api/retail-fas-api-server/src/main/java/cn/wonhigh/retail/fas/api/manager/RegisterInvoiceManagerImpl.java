package cn.wonhigh.retail.fas.api.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dto.PagingDto;
import cn.wonhigh.retail.fas.api.dto.RegisterInvoiceDto;
import cn.wonhigh.retail.fas.api.dto.RegisterInvoiceParamDto;
import cn.wonhigh.retail.fas.api.model.RegisterInvoiceModel;
import cn.wonhigh.retail.fas.api.service.BillCommonInvoiceRegisterService;
import cn.wonhigh.retail.fas.api.service.RegisterInvoiceApi;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Service("registerInvoiceManagerImpl")
public class RegisterInvoiceManagerImpl implements RegisterInvoiceApi {

	private Logger log = Logger.getLogger(getClass());
	
	private static final String IS_ALREDY_USE = "0";
	
	private static final String IS_BILL_IN_ADVANCE = "2";
	
	private static final String STATUS = "1"; //确认状态才可以使用
	
	private static final String BALANCE_TYPE = "8"; //券登记使用的开票申请只能是POS 内购
	
	@Resource
	private BillCommonInvoiceRegisterService billCommonInvoiceRegisterService;
	
	@Override
	public PagingDto<RegisterInvoiceDto> queryRegisterInvoiceList(
			RegisterInvoiceParamDto registerInvoicePara) throws RpcException{
		// TODO Auto-generated method stub
		
		Map<String,Object> params = new HashMap<String,Object>();
		List<RegisterInvoiceDto> registerInvoiceDtoList = null;
		List<RegisterInvoiceModel> registerInvoiceModelList = null;
		PagingDto<RegisterInvoiceDto> pagingDto = null;
		try{
		
			if(validationParams(registerInvoicePara)){
				params.put("salerNo", registerInvoicePara.getCompanyNo());
				params.put("buyerNo", registerInvoicePara.getBuyerNo());
				params.put("startApplyDate", registerInvoicePara.getStartApplyDate());
				params.put("endApplyDate", registerInvoicePara.getEndApplyDate());
				params.put("preInvoice",IS_BILL_IN_ADVANCE);
				params.put("status", STATUS);
				params.put("useFlag", IS_ALREDY_USE);
				params.put("balanceType", BALANCE_TYPE);
				
				//1: 普通发票   2: 登记发票
				Integer totals = billCommonInvoiceRegisterService.queryBillCommonInvoiceTotal(params);
				
				if(totals != null && totals > 0){
					registerInvoiceModelList = billCommonInvoiceRegisterService.queryBillCommonInvoiceRegister(params);
				}else{
					return null;
				}
				
				if(registerInvoiceModelList != null && registerInvoiceModelList.size() > 0 ){
					
					registerInvoiceDtoList = new ArrayList<RegisterInvoiceDto>();
					
					for(RegisterInvoiceModel registerInvoiceModel : registerInvoiceModelList)
					{
						RegisterInvoiceDto registerInvoiceDto = new RegisterInvoiceDto();
						BeanUtils.copyProperties(registerInvoiceModel, registerInvoiceDto);
						registerInvoiceDtoList.add(registerInvoiceDto);
					}
					
					pagingDto = new PagingDto<RegisterInvoiceDto>();
					pagingDto.setTotals(totals);
					pagingDto.setResults(registerInvoiceDtoList);
				}
				
			}
		}catch(RpcException e){
			log.error(e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		return pagingDto;
	}
	
	public boolean validationParams(RegisterInvoiceParamDto params) throws RpcException{
		
		boolean flag = false;
		
		if(params != null){
			String buyerNo = params.getBuyerNo();
			String companyNo = params.getCompanyNo();
			Date startApplyDate = params.getStartApplyDate();
			Date endApplyDate = params.getEndApplyDate();
			Integer invoiceType = params.getInvoiceType();
			
			if(buyerNo == null || "".equals(buyerNo)){
				log.error("buyerNo参数不合法,请检查");
				throw new RpcException("财务辅助dubbo服务","buyerNo参数不合法,请检查");
			}
			if(companyNo == null || "".equals(companyNo)){
				log.error("companyNo参数不合法,请检查");
				throw new RpcException( "财务辅助dubbo服务","companyNo参数不合法,请检查");
			}
			if(invoiceType == null){
				log.error("invoiceType参数不合法,请检查");
				throw new RpcException("财务辅助dubbo服务","invoiceType参数不合法,请检查");
			}
			if(startApplyDate == null || endApplyDate == null){
				log.error("startApplyDate参数不合法,请检查");
				throw new RpcException("财务辅助dubbo服务","startApplyDate参数不合法,请检查");
			}
			if(startApplyDate.getTime() > endApplyDate.getTime()){
				log.error("开始时间不能大于结束时间,请检查");
				throw new RpcException("财务辅助dubbo服务","开始时间不能大于结束时间,请检查");
			}
			if(endApplyDate.getTime() < startApplyDate.getTime()){
				log.error("结束时间不能小于开始时间,请检查");
				throw new RpcException("财务辅助dubbo服务","结束时间不能小于开始时间,请检查");
			}
			
			flag = true;
		}else{
			log.error("[RegisterInvoiceParamDto == null] 参数不合法,请检查");
			throw new RpcException("财务辅助dubbo服务","获取发票登记失败");
		}
		return flag;
	}

	@Override
	public boolean updateBillUseFlagByBillNo(String billNo, Integer useFlag) throws RpcException{
		// TODO Auto-generated method stub
		if(billNo == null || "".equals(billNo)){
			log.error("[billNo == null] 参数不合法,请检查");
			throw new RpcException("财务辅助dubbo服务","修改发票状态失败");
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("billNo", billNo);
		params.put("useFlag", useFlag);
		
		try {
			int count = billCommonInvoiceRegisterService.updateBillStatus(params);
			
			if(count > 0){
				return true;
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			throw new RpcException("财务辅助dubbo服务","修改发票状态失败");
		}
		return false;
	}
}
