package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.pms.api.service.BillPurchaseOrderApi;

public class BillBuyBalanceApiManagerTest extends BaseTest {

	@Resource
	private BillBuyBalanceApi billBuyBalanceApiManager;
 
	@Resource 
	BillPurchaseOrderApi billPurchaseOrderApiManager;
	
	@Test
	public void testInsert() throws Exception {
		try {
			List<BillBalanceApiDto> list = new ArrayList<BillBalanceApiDto>(); 
			BillBalanceApiDto dto = new BillBalanceApiDto();
			dto.setId(UUIDGenerator.getUUID());
			dto.setBillNo("Test1320100001");
			dto.setBillType(1304);
			dto.setRefBillType(1301);
			dto.setRefBillNo("D141DA15050002");
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
			dto.setSendDate(DateUtil.getdate("2016-06-14"));
			dto.setReceiveDate(DateUtil.getdate("2016-06-14"));
			dto.setSendQty(120);
			dto.setReceiveQty(120);
			dto.setOrderNo("orderNo4");
			dto.setOrderUnitNo("C001");
			dto.setOrderUnitName("哈尔滨BL");
			dto.setOrderUnitNoFrom("C002");
			dto.setOrderUnitNameFrom("哈尔滨BS");
			// dto.setIsSplit(1);

			list.add(dto);

			billBuyBalanceApiManager.insert(list);
			// billSaleBalanceApiManager.insert(list);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	
	@Resource
	private BillSaleBalanceApiService billSaleBalanceApiService;
	
	@Test
	public void select_params_count_test(){
		
		String companyNo = "C0001";
		String paramCode = "AI_Register_Dlt_Create";
		String dtlValue = "1";
		boolean val = billSaleBalanceApiService.selectParamsCount(companyNo,paramCode,dtlValue) > 0; 
		
		Assert.assertFalse(val);
	}
	
	@Test
	public void testPms() throws Exception {
		try {
			cn.wonhigh.retail.pms.api.vo.BillPurchaseOrder order = billPurchaseOrderApiManager.getBillPurchaseOrder("B00BLCG150113");
			Assert.assertNotNull(order);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	
	
}
