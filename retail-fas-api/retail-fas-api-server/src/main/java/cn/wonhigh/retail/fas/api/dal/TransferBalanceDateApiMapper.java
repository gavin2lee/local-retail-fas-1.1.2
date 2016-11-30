/**
 * title:TransferBalanceDateApiMapper.java
 * package:cn.wonhigh.retail.fas.api.dal
 * description:TODO
 * auther:user
 * date:2016-7-25 下午3:14:27
 */
package cn.wonhigh.retail.fas.api.dal;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.model.TransferBalanceDate;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 
 */
public interface TransferBalanceDateApiMapper extends BaseCrudMapper {

	/**
	 * @param params
	 * @return
	 */
	TransferBalanceDate selectTransferBalanceDate(@Param("params")Map<String, Object> params);

}
