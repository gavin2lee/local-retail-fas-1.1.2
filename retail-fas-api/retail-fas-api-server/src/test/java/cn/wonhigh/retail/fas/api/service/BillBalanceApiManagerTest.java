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

public class BillBalanceApiManagerTest extends BaseTest {

	// @Resource(name="billSaleBalanceApiManager")
	// private BillSaleBalanceApi billSaleBalanceApiManager;

	@Resource(name = "billBuyBalanceApiManager")
	private BillBuyBalanceApi billBuyBalanceApiManager;

	@Resource
	private PurchasePriceApiService purchasePriceApi;

	@Resource
	private RegionCostAccountingService regionCostAccountingService;

	@Test
	public void testInsert() throws Exception {
		try {
			List<BillBalanceApiDto> list = new ArrayList<BillBalanceApiDto>();

			BillBalanceApiDto dto = new BillBalanceApiDto();
			dto.setId(UUIDGenerator.getUUID());
			dto.setBillNo("Test1320100001");
			dto.setBillType(1301);
			dto.setRefBillType(1301);
			dto.setRefBillNo("1111111111");
			dto.setBizType(0);
			dto.setBrandNo("BS01");
			dto.setBrandName("百思图");
			dto.setBuyerNo("D0001");
			dto.setBuyerName("百丽鞋业（北京）有限公司");
			dto.setCategoryNo("010102");
			dto.setCategoryName("满帮");
			dto.setBillCost(new BigDecimal(230));
			dto.setItemNo("20141206192820");
			dto.setItemName("蓝色牛皮男鞋");
			dto.setTaxRate(new BigDecimal(0.17));
			dto.setSeason("20141017000020");
			dto.setYears("2011");
			dto.setSupplierNo("00B7");
			dto.setSupplierName("鹤山市新易高鞋业有限公司");
			dto.setStatus(0);
			dto.setReceiveStoreNo("sendStoreNo4");
			dto.setReceiveStoreName("sendStoreName4");
			dto.setSendDate(DateUtil.getdate("2014-12-20"));
			dto.setSendQty(120);
			dto.setReceiveQty(120);
			dto.setOrderNo("orderNo4");
			dto.setOrderUnitNo("C001");
			dto.setOrderUnitName("哈尔滨BL");
			dto.setOrderUnitNoFrom("C002");
			dto.setOrderUnitNameFrom("哈尔滨BS");
			// dto.setIsSplit(1);

			list.add(dto);

			// dto = new BillBalanceApiDto();
			// dto.setId("43333333333334f");
			// dto.setBillNo("Test130105");
			// dto.setBillType(1301);
			// dto.setRefBillType(1301);
			// dto.setBizType(0);
			// dto.setBrandNo("BS01");
			// dto.setBrandName("百思图");
			// dto.setBuyerNo("C0001");
			// dto.setBuyerName("百丽鞋业（沈阳）商贸有限公司");
			// dto.setCategoryNo("010102");
			// dto.setCategoryName("满帮");
			// dto.setBillCost(new BigDecimal(300));
			// dto.setItemNo("20141206192821");
			// dto.setItemName("绿色牛皮男鞋");
			// dto.setTaxRate(new BigDecimal(0.17));
			// dto.setSeason("20141017000020");
			// dto.setYears("2014");
			// dto.setSupplierNo("BS00B7");
			// dto.setSupplierName("鹤山市新易高鞋业有限公司");
			// dto.setStatus(0);
			// dto.setReceiveStoreNo("sendStoreNo4");
			// dto.setReceiveStoreName("sendStoreName4");
			// dto.setSendDate(DateUtil.getdate("2014-12-20"));
			// dto.setSendQty(10);
			// dto.setOrderNo("orderNo4");
			// // dto.setIsSplit(1);
			//
			// list.add(dto);

			billBuyBalanceApiManager.insert(list);
			// billSaleBalanceApiManager.insert(list);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	@Test
	public void testSelectBuyBalance() throws Exception {
		Map<String, Object> params = Maps.newHashMap();
		List<String> supplierNos = new ArrayList<String>();
		supplierNos.add("00BU");
		/*
		 * params.put("billType", "1301"); params.put("billNo",
		 * "R20150207000001_2");
		 * 
		 * params.put("categoryNo", "010201"); params.put("sendDateStart",
		 * "R20150207000001_2"); params.put("sendDateEnd", "R20150207000001_2");
		 * params.put("itemNo", "20141206192776"); params.put("buyerNo",
		 * "C0001");
		 */
		// params.put("supplierNos", supplierNos);
		// params.put("brandUnitNo", "BS");
		int count = billBuyBalanceApiManager.selectBillBuyBalanceCount(params);
		System.out.println("count:" + count);
		SimplePage page = new SimplePage(1, 1000, count);
		List<BillBuyBalance> list = billBuyBalanceApiManager.selectBillBuyBalanceInfo(page, null, null, params);
		for (BillBuyBalance obj : list) {
			System.out.println("BillNo:" + obj.getBillNo() + "-ItemName" + obj.getItemName() + "-brandUnitName:"
					+ obj.getBrandUnitName());
			/*
			 * System.out.println("billType:"+obj.getBillType()+"-ItemNo"+obj.
			 * getItemNo());
			 * System.out.println("brandNos:"+obj.getBrandNo()+"-categoryNo"
			 * +obj.getCategoryNo());
			 * System.out.println("buyerNo:"+obj.getBuyerNo
			 * ()+"-supplierNo"+obj.getSupplierNo());
			 */
		}
	}

	@Test
	public void testPms() throws Exception {

		PurchasePrice dto = purchasePriceApi.findBillPurchasePrice("20141205000001", "00BL",
				DateUtil.parseToDate("2015-6-02", "yyyy-MM-dd"));
		System.out.println(dto);
	}

	@Test
	public void testReceipt() throws Exception {
		BillBalanceApiDto dto = new BillBalanceApiDto();
		dto.setBillNo("TESTReceiptBillNo11");
		dto.setBillType(1304);
		dto.setBizType(2);
		dto.setRefBillNo("R20141210000089");
		dto.setItemNo("20141206248653dsd");
		dto.setReceiveQty(30);

		List<BillBalanceApiDto> list = new ArrayList<BillBalanceApiDto>();
		list.add(dto);
		billBuyBalanceApiManager.insert(list);
	}

	@Test
	public void testTransferIn() throws Exception {
		BillBalanceApiDto dto = new BillBalanceApiDto();
		dto.setBillNo("P20141118000011");
		dto.setBillType(1320);
		dto.setItemNo("20141105188109");
		dto.setReceiveQty(20);

		List<BillBalanceApiDto> list = new ArrayList<BillBalanceApiDto>();
		list.add(dto);
		billBuyBalanceApiManager.insert(list);
	}

	@Test
	public void testGetZonePrice() throws Exception {
		try {
			BigDecimal regionCost = regionCostAccountingService.findRegionCost("20141206192821", "C",
					DateUtil.parseToDate("2014-12-07", "yyyy-MM-dd"));
			System.out.println(regionCost);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
}
