package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;

import com.google.common.collect.Maps;
import com.yougou.logistics.base.common.utils.SimplePage;

public class CopyOfBillBalanceApiManagerTest extends BaseTest {

	// @Resource(name="billSaleBalanceApiManager")
	// private BillSaleBalanceApi billSaleBalanceApiManager;

	@Resource
	private BillSaleBalanceApi billSaleBalanceApi;
	
	@Resource
	private WholesaleControlApi wholesaleControlApi;

	@Test
	public void testInsert() throws Exception {
		try {
			List<BillBalanceApiDto> list = new ArrayList<BillBalanceApiDto>();

			BillBalanceApiDto dto = new BillBalanceApiDto();
			dto.setId(UUIDGenerator.getUUID());
			dto.setBillNo("AA111");
			dto.setBillType(1335);
//			dto.setRefBillType(1337);
//			dto.setRefBillNo("PFDD222");
//			dto.setBillRebateAmount(new BigDecimal(200));
//			dto.setOtherPrice(new BigDecimal(100));
			dto.setBizType(30);
			dto.setBrandNo("BL01");
			dto.setBrandName("百思图");
			dto.setSalerNo("F0001");
			dto.setSalerName("陕西百丽鞋业有限公司");
			dto.setBuyerNo("FLZP12");
			dto.setBuyerName("武都胡力元");
			dto.setCategoryNo("010102");
			dto.setCategoryName("满帮");
			dto.setCost(new BigDecimal(200));
			dto.setTagPrice(new BigDecimal(200));
			
			dto.setItemNo("20141206002884");
			dto.setItemCode("B7L422H0100EP9");
			dto.setItemName("BL镜钢包架");
			dto.setTaxRate(new BigDecimal(0.17));
			dto.setSeason("20141017000020");
			dto.setYears("2011");
			dto.setSupplierNo("00B7");
			dto.setSupplierName("鹤山市新易高鞋业有限公司");
			dto.setStatus(5);
			dto.setReceiveStoreNo("sendStoreNo4");
			dto.setReceiveStoreName("sendStoreName4");
			dto.setSendDate(DateUtil.getdate("2016-11-01"));
			dto.setSendQty(5);
			dto.setReceiveQty(0);
			dto.setOrderNo("orderNo4");
			dto.setOrderUnitNo("C001");
			dto.setOrderUnitName("哈尔滨BL");
			dto.setOrderUnitNoFrom("C002");
			dto.setOrderUnitNameFrom("哈尔滨BS");
			// dto.setIsSplit(1);

			list.add(dto);

//			dto = new BillBalanceApiDto();
//			dto.setId(UUIDGenerator.getUUID());
//			dto.setBillNo("MM222");
//			dto.setBillType(1335);
////			dto.setRefBillType(1337);
////			dto.setRefBillNo("PFDD111");
//			dto.setBizType(30);
//			dto.setBrandNo("BS01");
//			dto.setBrandName("百思图");
//			dto.setSalerNo("F0001");
//			dto.setSalerName("陕西百丽鞋业有限公司");
//			dto.setBuyerNo("FLZP12");
//			dto.setBuyerName("武都胡力元");
//			dto.setCategoryNo("010102");
//			dto.setCategoryName("满帮");
//			dto.setCost(new BigDecimal(400));
//			dto.setItemNo("20141206192822");
//			dto.setItemCode("red111");
//			dto.setItemName("红色牛皮男鞋");
//			dto.setTaxRate(new BigDecimal(0.17));
//			dto.setSeason("20141017000020");
//			dto.setYears("2011");
//			dto.setSupplierNo("00B7");
//			dto.setSupplierName("鹤山市新易高鞋业有限公司");
//			dto.setStatus(5);
//			dto.setReceiveStoreNo("sendStoreNo4");
//			dto.setReceiveStoreName("sendStoreName4");
//			dto.setSendDate(DateUtil.getdate("2016-10-12"));
//			dto.setSendQty(2);
//			dto.setReceiveQty(0);
//			dto.setOrderNo("orderNo4");
//			dto.setOrderUnitNo("C001");
//			dto.setOrderUnitName("哈尔滨BL");
//			dto.setOrderUnitNoFrom("C002");
//			dto.setOrderUnitNameFrom("哈尔滨BS");
//			
//			list.add(dto);

			billSaleBalanceApi.insert(list);
//			wholesaleControlApi.changeWholesaleCost(list);
			// billSaleBalanceApiManager.insert(list);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}


}
