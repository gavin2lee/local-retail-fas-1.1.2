package cn.wonhigh.retail.fas.manager.domain;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.manager.BaroquePurchaseRegionCostManager;

@Component("tagPriceUpdateTask")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TagPriceUpdateTask extends TaskBase implements Runnable,ITask{
	
	private TagPriceArgument args;
	private String userName;
	
	@Resource
	private BaroquePurchaseRegionCostManager baroquePurchaseRegionCostManager;
	
	@Override
	public ITaskResult start(ITaskArgs args) {
		this.args = (TagPriceArgument)args;
		this.userName= Authorization.getUser().getLoginName();
		
		super.ticket = UUIDGenerator.getUUID();
		ITaskResult result = new TaskResult(super.ticket);
		super.updateStatus(1, 2, super.START, "牌价开始更新!");
		Thread thread = new Thread(this);
		thread.start();
		return result;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		Thread.currentThread().setName(name + "&" + userName);
		try {
			baroquePurchaseRegionCostManager.updateTagPrice(this.args);
			super.updateStatus(2, 2, super.SUCCESS, "牌价更新成功!");
		} catch (Exception e) {
			super.updateStatus(1, 2, super.FAILED, "牌价更新失败!");
			logger.error(e.getMessage(), e);
		}
	}
}
