package cn.wonhigh.retail.fas.api.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.wonhigh.retail.fas.api.dto.InsideBizTypeDto;
import cn.wonhigh.retail.fas.api.dto.InsideBizTypeParamDto;
import cn.wonhigh.retail.fas.api.dto.PagingDto;

import com.yougou.logistics.base.common.exception.RpcException;

public class InsideBizTypeTest extends BaseTest {
	private Logger log = Logger.getLogger(getClass());
	@Resource
	private InsideBizTypeApi insideBizTypeApi;
	
	@Test
	public void testQueryInsideBizType() throws RpcException{
		
		try {
			InsideBizTypeParamDto params = new InsideBizTypeParamDto();
			
//			params.setCompanyNo("C0001");
//			params.setShopNo("EJ02ZH");
//			
//			PagingDto<InsideBizTypeDto> pagingDto = insideBizTypeApi.queryInsideBizTypeList(params);
//			
//			if(pagingDto != null && pagingDto.getResults() != null){
//				List<InsideBizTypeDto> registerInvoiceDtoList = pagingDto.getResults();
//				System.out.println(registerInvoiceDtoList.get(0).toString());
//			}
			
			params.setCompanyNo("C0001");
			params.setBizTypeCode("0101");
//			params.setShopNo("EJ02ZH");
//			
			PagingDto<InsideBizTypeDto> pagingDto = insideBizTypeApi.queryClientList(params);
			
			if(pagingDto != null && pagingDto.getResults() != null){
				List<InsideBizTypeDto> registerInvoiceDtoList = pagingDto.getResults();
				System.out.println(registerInvoiceDtoList.get(0).toString());
			}
			
			
		} catch (RpcException e) {

			log.error(e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);			
		}
	}
	
}
