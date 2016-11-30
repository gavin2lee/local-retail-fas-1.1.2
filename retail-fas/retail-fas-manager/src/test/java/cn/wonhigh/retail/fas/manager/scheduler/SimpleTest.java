package cn.wonhigh.retail.fas.manager.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.wonhigh.retail.fas.common.model.BillSaleBalance;

 
public class SimpleTest {
	@Test
	public void test_object() throws Exception {
	  BillSaleBalance item = new BillSaleBalance();
	  //item.setId("J");
	  String t = item.getId();
	  
	  Map<String,Object> map = new HashMap<>();
	  
	  map.put("J",item.getId());
	  System.out.println(t);
	}
}
