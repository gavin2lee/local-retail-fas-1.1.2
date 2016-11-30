package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.IndependentShopMonthReport;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.manager.BaseCrudManager;

public interface IndependentShopMonthReportManager extends BaseCrudManager {

	public List<IndependentShopMonthReport> queryEachMonthShopReport(Map<String,Object> params) throws ManagerException;
}
