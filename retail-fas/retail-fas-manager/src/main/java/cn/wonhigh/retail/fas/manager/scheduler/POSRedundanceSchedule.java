package cn.wonhigh.retail.fas.manager.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.backend.core.SpringContext;
import cn.wonhigh.retail.fas.common.constans.CommonConstants;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.service.ZoneInfoService;
import cn.wonhigh.retail.mdm.common.model.OrganType;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.vo.scheduler.RemoteJobInvokeParamsDto;

/**
 * POS单据字段冗余
 * 
 * @author xia.j1
 * 
 */
@Service
@ManagedResource(objectName = CommonConstants.SYS_NAME + "POSRedundance", description = StringUtils.EMPTY)
public class POSRedundanceSchedule extends RedundanceSchedule {
	@Resource
	private ZoneInfoService zoneInfoService;
 

	private List<String> getSharedingInfo() throws ServiceException, RpcException{
		List<ZoneInfo> zones = zoneInfoService.findByBiz(null, null);
		String[] organTypes = new String[]{"U010101","U010102","U010105"}; //organTypeApi.queryList(null);
		List<String> lst = new ArrayList<>();
		lst.add("U010105_B");
		
//		for (String unit : organTypes) {
//			if( unit.equalsIgnoreCase("U010105"))
//				continue;
//			for (ZoneInfo zone : zones) {		
//				if(zone.getZoneNo().equalsIgnoreCase("B"))
//					continue;
//				String val = String.format("%s_%s",unit,zone.getZoneNo().toUpperCase());
//				lst.add(val);
//			}
//		}
		return lst;
	}
	
	@Override
	public void run(RemoteJobInvokeParamsDto params) {
		
		
		// 按mycat分库启动线程
		List<String> dataBaseList = null;
		try {
			dataBaseList = getSharedingInfo();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return;
		}
		if( dataBaseList.size() == 0 )
			return;
		CountDownLatch signal = new CountDownLatch(dataBaseList.size());		
		for (String shardingFlag : dataBaseList) {
			ProcessPosBillTask task =  SpringContext.getBean(ProcessPosBillTask.class);
			
			task.run(shardingFlag,signal);
		}
		
		try {
			signal.await();
		} catch (InterruptedException e) {
			log.error(e.getMessage(),e);
		}
	} 

}
