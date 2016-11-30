package cn.wonhigh.retail.fas.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.FinancialCategoryCommonDto;
import cn.wonhigh.retail.fas.common.dto.FinancialCategoryDto;
import cn.wonhigh.retail.fas.common.model.FinancialCategory;
import cn.wonhigh.retail.fas.dal.database.FinancialCategoryMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 财务大类 
 * @author Administrator
 * @date  2014-12-23 10:38:39
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
@Service("financialCategoryService")
class FinancialCategoryServiceImpl extends BaseServiceImpl implements FinancialCategoryService {
    @Resource
    private FinancialCategoryMapper financialCategoryMapper;

    @Override
    public BaseCrudMapper init() {
        return financialCategoryMapper;
    }
    
    @Override
   	public int findRelationCount(Map<String, Object> params) throws ServiceException {
		return financialCategoryMapper.selectRelationCount(params, null);
   	}

   	@Override
   	public List<FinancialCategoryDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
   			Map<String, Object> params) throws ServiceException {
		return financialCategoryMapper.selectRelationByPage(page, sortColumn, sortOrder, params, null);
   	}

	@Override
	public List<FinancialCategory> selectCateInfoByCateNo(Map<String, Object> params) {
		return financialCategoryMapper.selectCateInfoByCateNo(params);
	}

	@Override
	public List<FinancialCategoryCommonDto> getAllCateInfo(Map<String, Object> params) {
		return financialCategoryMapper.getAllCateInfo(params);
	}
}