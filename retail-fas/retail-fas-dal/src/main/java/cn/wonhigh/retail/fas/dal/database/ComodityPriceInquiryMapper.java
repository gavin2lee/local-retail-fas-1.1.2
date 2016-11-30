package cn.wonhigh.retail.fas.dal.database;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.PurchasePrice;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

public interface ComodityPriceInquiryMapper extends BaseCrudMapper {
	PurchasePrice getBalancePurchasePrice(@Param("params")Map<String,Object> params) throws ServiceException;
}
