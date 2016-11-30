package cn.wonhigh.retail.fas.service;

import java.util.Map;

import cn.wonhigh.retail.fas.dal.database.NcFinishedGoodsMapper;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 请写出类的用途 
 * @author user
 * @date  2016-05-23 16:36:42
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("ncFinishedGoodsService")
class NcFinishedGoodsServiceImpl extends BaseCrudServiceImpl implements NcFinishedGoodsService {
    @Resource
    private NcFinishedGoodsMapper ncFinishedGoodsMapper;

    @Override
    public BaseCrudMapper init() {
        return ncFinishedGoodsMapper;
    }

	@Override
	public void deleteData(Map<String, Object> params) {
		ncFinishedGoodsMapper.deleteData(params);
	}
}