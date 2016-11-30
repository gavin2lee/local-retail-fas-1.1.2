package cn.wonhigh.retail.fas.web.utils;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.manager.BillShopBalanceManager;

import com.yougou.logistics.base.manager.BaseCrudManager;

/**
 * 商场结算-明细数据处理类
 * 
 * @author yang.y
 */
@SuppressWarnings("rawtypes")
public abstract class AbstactBillDtlHandler {

	/** Manager类 */
	private BaseCrudManager manager;
	
	/** 导出的列集合 */
	private List<Map> columnsList;
	
	/** 导出参数集合 */
	private Map<String,Object> params;
	
	private BillShopBalanceManager billShopBalanceManager;
	
	public BaseCrudManager getManager() {
		return manager;
	}

	public void setManager(BaseCrudManager manager) {
		this.manager = manager;
	}

	public List<Map> getColumnsList() {
		return columnsList;
	}

	public void setColumnsList(List<Map> columnsList) {
		this.columnsList = columnsList;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	public BillShopBalanceManager getBillShopBalanceManager() {
		return billShopBalanceManager;
	}

	public void setBillShopBalanceManager(BillShopBalanceManager billShopBalanceManager) {
		this.billShopBalanceManager = billShopBalanceManager;
	}

	/**
	 * Builder模式构建对象属性
	 * @param manager BaseCrudManager
	 * @return 当前对象
	 */
	public AbstactBillDtlHandler buildManager(BaseCrudManager manager) {
		this.manager = manager;
		return this;
	}
	
	/**
	 * Builder模式构建对象属性
	 * @param columnsList List<Map>
	 * @return 当前对象
	 */
	public AbstactBillDtlHandler buildColumnsList(List<Map> columnsList) {
		this.columnsList = columnsList;
		return this;
	}
	
	/**
	 * Builder模式构建对象属性
	 * @param params Map<String,Object>
	 * @return 当前对象
	 */
	public AbstactBillDtlHandler buildParams(Map<String,Object> params) {
		this.params = params;
		return this;
	}
	
	/**
	 * Builder模式构建对象属性
	 * @param billShopBalanceManager BillShopBalanceManager
	 * @return 当前对象
	 */
	public AbstactBillDtlHandler buildBillShopBalanceManager(BillShopBalanceManager billShopBalanceManager) {
		this.billShopBalanceManager = billShopBalanceManager;
		return this;
	}
	
	/**
	 * 构建导出数据
	 * @return 导出数据集合
	 * @throws Exception 异常
	 */
	public abstract List<Map> bulidExportData() throws Exception;
}
