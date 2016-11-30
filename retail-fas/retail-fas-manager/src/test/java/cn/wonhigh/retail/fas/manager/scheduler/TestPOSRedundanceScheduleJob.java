package cn.wonhigh.retail.fas.manager.scheduler;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yougou.logistics.base.common.vo.scheduler.RemoteJobInvokeParamsDto;

/**
 * TODO: 增加描述
 * 
 * @author sun.wr
 * @date 2014-8-6 上午10:38:35
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {  
		"classpath:META-INF/spring-fas-manager.xml"  
		,"classpath*:META-INF/spring-mdm-api.xml" 
		,"classpath*:META-INF/mdm-api-dubbo-ref.xml" 
		,"classpath*:META-INF/spring-backend-cache.xml"
})
public class TestPOSRedundanceScheduleJob {

	@Resource
	private POSRedundanceSchedule job;
	
	@Resource
	private AssignHQAndRegionCostScheduler assignHQAndRegionCostScheduler;

	@Test
	public void testPOSRedundanceSchedule() throws Exception {
		job.executeJobWithParams(null,null,null,null);
	}
	
	
	@Resource
	AssignItemCostScheduler assignItemCostScheduler;
	
	@Test
	public void test_assign_item_cost_with_company(){
		RemoteJobInvokeParamsDto params = new RemoteJobInvokeParamsDto();
		params.addParam("companyNo", "C0001");
		assignItemCostScheduler.run(params);
	}
	
	@Test
	public void test_assign_item_cost(){
		RemoteJobInvokeParamsDto params = new RemoteJobInvokeParamsDto(); 
		assignItemCostScheduler.run(params);
	}
	
	@Test
	public void test_assign_header_cost_witch_company(){
		RemoteJobInvokeParamsDto params = new RemoteJobInvokeParamsDto();
		params.addParam("companyNo", "C0001");
		assignHQAndRegionCostScheduler.run(params);
	}
	
	@Test
	public void test_assign_header_cost(){ 
		RemoteJobInvokeParamsDto params = new RemoteJobInvokeParamsDto();
		assignHQAndRegionCostScheduler.run(params);
	}

}
