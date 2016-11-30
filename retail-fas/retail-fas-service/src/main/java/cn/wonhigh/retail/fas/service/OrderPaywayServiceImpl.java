package cn.wonhigh.retail.fas.service;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.OrderPayway;
import cn.wonhigh.retail.fas.dal.database.OrderPaywayMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:10:28
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
@Service("orderPaywayService")
class OrderPaywayServiceImpl extends BaseCrudServiceImpl implements OrderPaywayService {
    @Resource
    private OrderPaywayMapper orderPaywayMapper;

    @Override
    public BaseCrudMapper init() {
        return orderPaywayMapper;
    }

	@Override
	public List<OrderPayway> findByShopNo(String shopNo) {
		return orderPaywayMapper.findByShopNo(shopNo);
	}

	@Override
	public List<OrderPayway> queryAllPayWays() {
		return orderPaywayMapper.queryAllPayWays();
	}
}