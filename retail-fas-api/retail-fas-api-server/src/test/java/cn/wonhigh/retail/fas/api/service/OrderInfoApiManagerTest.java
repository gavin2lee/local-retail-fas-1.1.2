package cn.wonhigh.retail.fas.api.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;

import cn.wonhigh.retail.fas.api.dto.OrderInfoDto;

public class OrderInfoApiManagerTest extends BaseTest {
	@Resource
	private OrderInfoApi orderInfoManagerImplApi;

	@Test
	public void getStatus() {
		OrderInfoDto orderInfoDto = new OrderInfoDto();
		orderInfoDto.setBillType(99);
		orderInfoDto.setBillNo("EX98JP1508130001");
		Integer status = orderInfoManagerImplApi.getStatus(orderInfoDto);
		if(null!=status){
			assertEquals(Integer.valueOf(1), status);
			System.out.println("现金销售单已确认");
		}else{
			System.out.println("No source be found ..");
		}
		
		
		orderInfoDto.setBillType(1);
		orderInfoDto.setBillNo("CA02MP1510290002");
		if(null!=status){
			assertEquals(Integer.valueOf(1), status);
			System.out.println("其他支付方式销售单已确认");
		}else{
			System.out.println("No source be found ..");
		}
		
		if(status != null){
			System.out.println(status);
		}
	}

}
