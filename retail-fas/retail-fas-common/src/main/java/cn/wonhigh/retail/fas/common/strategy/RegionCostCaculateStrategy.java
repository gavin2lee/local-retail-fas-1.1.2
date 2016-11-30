package cn.wonhigh.retail.fas.common.strategy;

import cn.wonhigh.retail.fas.common.dto.RegionCostCaculatorDto;

public interface RegionCostCaculateStrategy {
	RegionCostCaculatorDto process(RegionCostCaculatorDto t) throws Exception;
}
