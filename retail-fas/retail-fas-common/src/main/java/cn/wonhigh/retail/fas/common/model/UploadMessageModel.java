package cn.wonhigh.retail.fas.common.model;

/**
 * TODO: 导入execl时，记录每行数据是否正确的对象
 * 
 * @author 杨勇
 * @date 2014-5-12 下午2:32:28
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class UploadMessageModel {

	private String name;
	
	private String message;
	
	private boolean flag;
	
	public UploadMessageModel() {}

	public UploadMessageModel(String name, String message, boolean flag) {
		super();
		this.name = name;
		this.message = message;
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
