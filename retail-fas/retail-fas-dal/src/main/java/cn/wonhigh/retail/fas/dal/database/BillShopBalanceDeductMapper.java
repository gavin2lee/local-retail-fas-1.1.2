package cn.wonhigh.retail.fas.dal.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDeductFooterDto;
import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDeductDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-27 19:22:11
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
public interface BillShopBalanceDeductMapper extends BaseCrudMapper {
	
	public <ModelType> int deleteBalanceNoForModel(ModelType record);
	
	public <ModelType> int updateBalanceNoForModel(ModelType record);
	
	public  int updateBalanceDeductBalanceNo(@Param("params")Map<String,Object> params);
	
	public  BillShopBalanceDeduct  getSumAmount(@Param("params")Map<String,Object> params);

	public GatherBillShopBalanceDeductDto gatherBalanceDeduct(@Param("params")Map<String, Object> params);

	/**
	 * 获取页脚汇总对象
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	public List<BillShopBalanceDeductFooterDto> getFooterDto(@Param("params")Map<String, Object> params); 
	
}