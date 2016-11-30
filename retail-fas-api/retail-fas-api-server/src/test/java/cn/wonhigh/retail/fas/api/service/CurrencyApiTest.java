package cn.wonhigh.retail.fas.api.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.wonhigh.retail.fas.api.dto.CurrencyDto;
import cn.wonhigh.retail.fas.api.dto.WholesaleControlApiDto;
import cn.wonhigh.retail.fas.api.manager.CurrencyManagerImpl;
import cn.wonhigh.retail.fas.api.manager.WholesaleControlApiManagerImpl;

import com.yougou.logistics.base.common.exception.RpcException;

public class CurrencyApiTest extends BaseTest{
	
	@Resource
	private CurrencyManagerImpl currencyManagerImpl;
	
	@Resource
	private WholesaleControlApiManagerImpl wholesaleControlApiManagerImpl;
	
	@Test
	public void testFindAllCurrency(){
		
		try {
			List<CurrencyDto> currencyList = this.currencyManagerImpl.findAllCurrency();

			for(CurrencyDto currencyDto : currencyList){
				System.out.println(currencyDto.getId()+" , "+currencyDto.getCurrencyCode()+" , "+currencyDto.getCurrencyName()+" , "+currencyDto.getCurrencySymbol());
			}
			
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testFindAllCurrency1(){
		
		try {
			WholesaleControlApiDto dto = wholesaleControlApiManagerImpl.getCheckMessage("SO20150124000011");
			System.out.println(dto.getBillNo());
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
