package cn.wonhigh.retail.fas.api.service;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.wonhigh.retail.fas.api.dto.PagingDto;
import cn.wonhigh.retail.fas.api.dto.RegisterInvoiceDto;
import cn.wonhigh.retail.fas.api.dto.RegisterInvoiceParamDto;

import com.yougou.logistics.base.common.exception.RpcException;

public class RegisterInvoiceManagerTest extends BaseTest {
	private Logger log = Logger.getLogger(getClass());

	@Resource
	private RegisterInvoiceApi registerInvoiceApi;
	
	@Test
	public void testQueryRegisterInvoice() throws RpcException{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			RegisterInvoiceParamDto params = new RegisterInvoiceParamDto();
			
			params.setBuyerNo("K0001");
			params.setCompanyNo("I0001");
			params.setStartApplyDate(dateFormat.parse("2014-12-10"));
			params.setEndApplyDate(dateFormat.parse("2015-01-27"));
			
			PagingDto<RegisterInvoiceDto> pagingDto = registerInvoiceApi.queryRegisterInvoiceList(params);
			
			if(pagingDto != null && pagingDto.getResults() != null){
				List<RegisterInvoiceDto> registerInvoiceDtoList = pagingDto.getResults();
				System.out.println(registerInvoiceDtoList.get(0).toString());
			}
			
			
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void testUpdateBillStatus() throws RpcException{
		
		String billNo = "IR20141217000002";
		Integer useFlag = 1;
		
		try {
			boolean flag = registerInvoiceApi.updateBillUseFlagByBillNo(billNo, useFlag);
			assertEquals(flag,true);
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		}
	}
	
	
}
