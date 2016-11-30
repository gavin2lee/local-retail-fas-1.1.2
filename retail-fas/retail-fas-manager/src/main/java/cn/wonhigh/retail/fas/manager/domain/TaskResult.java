package cn.wonhigh.retail.fas.manager.domain;

public class TaskResult implements ITaskResult {
	
	private Object value;
	
	public TaskResult(Object val){
		this.value = val;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}
}
