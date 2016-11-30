package cn.wonhigh.retail.fas.api.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.wonhigh.retail.fas.api.dto.HQRegionCostDto;
import cn.wonhigh.retail.fas.api.dto.HeadquarterCostDto;
import cn.wonhigh.retail.fas.api.dto.ItemZonePriceDto;
import cn.wonhigh.retail.fas.api.dto.RegionCostDto;
import cn.wonhigh.retail.fas.api.dto.UpdateCostApiDto;
import cn.wonhigh.retail.fas.common.model.CompanyBrandSettlePeriod;
import cn.wonhigh.retail.fas.common.model.CompanySettlePeriod;
import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.utils.DateUtil;

import com.yougou.logistics.base.common.exception.RpcException;

public class CostAccountingManagerTest extends BaseTest {
	
	@Resource
	private CostAccountingApi costAccountingManagerImplApi;
	
	@Test
	public void testGetHeadquarterCost()
	{
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			HeadquarterCostDto headquarterCostDto = new HeadquarterCostDto();
			headquarterCostDto.setItemNo("BLB3B31401200EY5");
			headquarterCostDto.setEffectiveDate(format.parse("2014-10-01"));
			
			BigDecimal headquarter_cost = costAccountingManagerImplApi.findHeadquarterCost(headquarterCostDto);
			
			System.out.println(headquarter_cost);
//			assertEquals("563.00",headquarter_cost.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testGetZoneAccountingCost()
	{
		try{
			List<String> itemNos = new ArrayList<String>();
			itemNos.add("20141105188162");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			RegionCostDto regionCostDto = new RegionCostDto();
			regionCostDto.setCompanyNo("C0001");
			regionCostDto.setItemNos(itemNos);
			regionCostDto.setEffectiveDate(format.parse("2014-11-01"));
			
			BigDecimal regionCost = costAccountingManagerImplApi.findRegionCost(regionCostDto);
			System.out.println(regionCost);
//			assertEquals(regionCost,regionCost);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetHqAndRegionCost()
	{
		try{
			RegionCostDto regionCostDto = new RegionCostDto();
			regionCostDto.setCompanyNo("C0001");
			regionCostDto.setItemNo("20141206200233");
			regionCostDto.setEffectiveDate(new Date());
			
			HQRegionCostDto regionCost = costAccountingManagerImplApi.findHQAndRegionCost(regionCostDto);
			
			System.out.println(regionCost.getHqPrice() + "   " + regionCost.getRegionPrice());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetCompanyAccountingPeriod()
	{
		try{
			CompanySettlePeriod companySettlePeriod = costAccountingManagerImplApi.findBalanceDate("K0001");

			System.out.println(companySettlePeriod.getCompanyName());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindRegionCostByOrderUnitDto()
	{
		try{

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			RegionCostDto regionCostDto = new RegionCostDto();
			regionCostDto.setOrderUnitNo("C040");
			regionCostDto.setItemNo("20141113240980");
			regionCostDto.setEffectiveDate(format.parse("2015-01-28"));
			BigDecimal regionCost = costAccountingManagerImplApi.findRegionCostByOrderUnitDto(regionCostDto);
			
			System.out.println(regionCost.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkCanUpdateFactoryPrice()
	{
		HeadquarterCostMaintain headqarterCostMaintain = new HeadquarterCostMaintain();
		headqarterCostMaintain.setItemNo("123566");
		headqarterCostMaintain.setSupplierNo("7889");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		try {
			headqarterCostMaintain.setEffectiveTime(df.parse("2014-09-11 11:51:30"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		try {
			boolean flag = costAccountingManagerImplApi.checkUpdateFactoryPrice(headqarterCostMaintain);
			assertEquals(true,flag);
		} catch (RpcException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetItemZonePrice()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String> items = new ArrayList<String>();
		items.add("20141105188162");
		items.add("20141105188097");
		
		try {
			RegionCostDto regionCostDto = new RegionCostDto();
			regionCostDto.setCompanyNo("C0001");
			regionCostDto.setItemNos(items);
			regionCostDto.setEffectiveDate(format.parse("2015-01-01"));
		
			List<ItemZonePriceDto> itemZonePriceDtos = costAccountingManagerImplApi.findRegionListCost(regionCostDto);
			for(ItemZonePriceDto itemZonePriceDto : itemZonePriceDtos)
			{
				System.out.println(itemZonePriceDto.getPrice() +" <---> "+itemZonePriceDto.getItemNo());
			}
			
		} catch (RpcException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testCheckHqCompany()
	{
		try {
			boolean result = costAccountingManagerImplApi.checkHQFunctionCompany("K0001");
			System.out.println(result);
			assertEquals(false, result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void testUpdateCost() {
		try {
			UpdateCostApiDto updateCostApiDto = new UpdateCostApiDto();
			updateCostApiDto.setCompanyNo("C0001");
			updateCostApiDto.setStartDate(DateUtil.getdate("2015-07-01"));
			updateCostApiDto.setEndDate(DateUtil.getdate("2015-07-31"));
			
			costAccountingManagerImplApi.updateCost(updateCostApiDto);
		} catch (RpcException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMultiBrandCompanySettleDate() {
		try {
			List<String> brandNos = new ArrayList<String>();
			brandNos.add("MP01");
			brandNos.add("BS01");
			brandNos.add("JP01");
			brandNos.add("FT01");
			
			CompanyBrandSettlePeriod period = costAccountingManagerImplApi.findCompanyBrandMaxSettleDate("C0001", brandNos);
			
			System.out.println("财务关账日：  " + period.getAccountSettleTime());
			System.out.println("销售关账日：  " + period.getSaleSettleTime());
		} catch (RpcException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
