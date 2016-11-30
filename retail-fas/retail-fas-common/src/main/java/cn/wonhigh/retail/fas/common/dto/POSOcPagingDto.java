package cn.wonhigh.retail.fas.common.dto;


import java.io.Serializable;
import java.util.List;
/**
 * DESC: 分页对象DTO
 * @author liu.w
 * @date 2014-9-10 上午11:41:03
 * @version 0.1.0 
 * @copyright Wonhigh Information Technology (Shenzhen) Co.,Ltd.
 */
public class POSOcPagingDto<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//返回对象
	private List<T> result;
	//总记录数
	private Integer total;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
	
	
}
