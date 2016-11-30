package cn.wonhigh.retail.fas.api.service;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import cn.wonhigh.retail.fas.api.manager.SupplierRateSetApiManagerImpl;
import cn.wonhigh.retail.fas.common.dto.SupplierRateSetDto;
import com.yougou.logistics.base.common.exception.RpcException;

public class SupplierRateSetApiTest  extends BaseTest {
	@Resource
	private SupplierRateSetApiManagerImpl supplierRateSetApiManagerImpl;
	
	@Test
	public void testFindSupplierRateSetSingle(){
		try {
			String supplierNo="1001";
	
			SupplierRateSetDto item = this.supplierRateSetApiManagerImpl.getSupplierRateSet(supplierNo);

//			for(SupplierRateSetDto item : supplierRateSets){
//				System.out.println(item.getId()+" , "+item.getSupplierNo()+" , "+item.getCurrencyName());
//			}
			System.out.println(item.getId()+" , "+item.getSupplierNo()+" , "+item.getCurrencyName());
			
		} catch (RpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	@Test
//	public void testFindSupplierRateSetList(){
//		try {
//			List<String> supplierNo=new List<String>();
//		    
//	
//			SupplierRateSetDto item = this.supplierRateSetApiManagerImpl.getSupplierRateSet(supplierNo);
//
////			for(SupplierRateSetDto item : supplierRateSets){
////				System.out.println(item.getId()+" , "+item.getSupplierNo()+" , "+item.getCurrencyName());
////			}
//			System.out.println(item.getId()+" , "+item.getSupplierNo()+" , "+item.getCurrencyName());
//			
//		} catch (RpcException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//	}
}
