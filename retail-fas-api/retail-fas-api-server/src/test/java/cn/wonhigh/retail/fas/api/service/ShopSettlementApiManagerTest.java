package cn.wonhigh.retail.fas.api.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.wonhigh.retail.fas.api.dto.MallDeductionSettingDto;
import cn.wonhigh.retail.fas.api.dto.ShopBalanceDateDto;
import cn.wonhigh.retail.fas.api.dto.ShopBankInfoDto;
import cn.wonhigh.retail.fas.api.dto.ShopCardInfoDto;

import com.yougou.logistics.base.common.exception.RpcException;

public class ShopSettlementApiManagerTest extends BaseTest {
	
	@Resource
	private ShopSettlementApi shopSettlementManagerImplApi;
	
	@Test
	public void getAllShopBalanceDateInfo()
	{
		 try {
			ShopBalanceDateDto shopBalanceDate = shopSettlementManagerImplApi.getAllShopBalanceDateInfo("BLCA01");
			
			if(shopBalanceDate != null){
				assertEquals("201411",shopBalanceDate.getMonth());
			}else{
				System.out.println("No source be found ..");
			}
			
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAllMallDeductionSettingInfo()
	{
		try {
			List<MallDeductionSettingDto> list = shopSettlementManagerImplApi.getAllMallDeductionSettingInfo("CA01BS");
			
			if(list != null && list.size() > 0){
				System.out.println("--------------"+list.get(0).getCode());
				assertEquals("001",list.get(0).getCode());
			}else{
				System.out.println("No source be found ..");
			}
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getShopBankInfo(){
		try{
			
			ShopCardInfoDto shopModel = shopSettlementManagerImplApi.getShopBankInfo("STCT21");
			System.out.println(shopModel.getTerminalNo()+" , "+shopModel.getCreditCardAccount()+","+shopModel.getDepositAccount());
		} catch (RpcException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getShopBankInfoDtl(){
		try {
			ShopBankInfoDto shopModel = shopSettlementManagerImplApi.getShopBankInfoDtl("CA01BL");
			System.out.println(shopModel.getDepositAccountList());
			System.out.println(shopModel.getTerminalAccountList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getShopBalanceEndDate()
	{
		 try {
			Date endDate = shopSettlementManagerImplApi.getShopBalanceEndDate("CA01BS");
			System.out.println("--------------- .."+endDate);
		} catch (RpcException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getInsidePurchaseBalanceEndDate()
	{
		 try {
			Date endDate = shopSettlementManagerImplApi.getInsidePurchaseBalanceEndDate("CA01BS");
			System.out.println("--------------- .."+endDate);
		} catch (RpcException e) {
			e.printStackTrace();
		}
	}
}
