package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.BillShopBalanceDeductFooterDto;
import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDeductDto;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceDeductMapper;

import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

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
@Service("billShopBalanceDeductService")
class BillShopBalanceDeductServiceImpl extends BaseCrudServiceImpl implements BillShopBalanceDeductService {
    
	@Resource
    private BillShopBalanceDeductMapper billShopBalanceDeductMapper;
    
    @Override
    public BaseCrudMapper init() {
        return billShopBalanceDeductMapper;
    }

	@Override
	public int updateBalanceDeductBalanceNo(Map<String, Object> params) {
		return billShopBalanceDeductMapper.updateBalanceDeductBalanceNo(params);
	}

	@Override
	public BillShopBalanceDeduct getSumAmount(Map<String, Object> params) {
		return billShopBalanceDeductMapper.getSumAmount(params);
	}

	/**
	 * 获取费用金额汇总对象
	 * @param params 参数
	 * @return 费用金额汇总对象
	 */
	@Override
	public GatherBillShopBalanceDeductDto gatherBalanceDeduct(Map<String, Object> params) {
		return billShopBalanceDeductMapper.gatherBalanceDeduct(params);
	}

	/**
	 * 获取页脚汇总对象
	 * @param params 查询参数
	 * @return 页脚汇总对象
	 */
	@Override
	public BillShopBalanceDeductFooterDto getFooterDto(Map<String, Object> params) {
		List<BillShopBalanceDeductFooterDto> list = billShopBalanceDeductMapper.getFooterDto(params);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}