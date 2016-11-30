package cn.wonhigh.retail.fas.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.BillBuyBalance;

import com.yougou.logistics.base.manager.BaseCrudManager;

public interface SpecifyItemCostManager extends BaseCrudManager {

	List<BillBuyBalance> setExtendsProperties(List<BillBuyBalance> list);

}
