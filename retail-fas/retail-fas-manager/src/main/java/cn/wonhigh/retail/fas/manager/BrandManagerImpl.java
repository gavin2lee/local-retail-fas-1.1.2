package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.vo.TreeJson;
import cn.wonhigh.retail.fas.service.BrandService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
@Service("brandManager")
class BrandManagerImpl extends BaseCrudManagerImpl implements BrandManager {
    @Resource
    private BrandService brandService;

    @Override
    public BaseCrudService init() {
        return brandService;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<TreeJson> selectBrandWithBrandUnit(
			Map<String, Object> params) throws ManagerException {
		try {
			//返回结果
			List<TreeJson> resultList = new ArrayList<TreeJson>();
			//查询结果集
			List<Brand> tempList = brandService.selectBrandWithBrandUnit(params);
			//临时分组
			Map<String, Object> brandUnitMap = new LinkedHashMap<String, Object>();
			
			//品牌按品牌部分组
			for(Brand brand : tempList) {
				String brandUnitNo = brand.getBrandUnitNo();
				String brandUnitName = brand.getBrandUnitName();
				if(brandUnitMap.get(brandUnitNo) == null) {
					//临时分组新增组
					brandUnitMap.put(brandUnitNo, new ArrayList<Map<String, Object>>());
					//返回结果新增组
					TreeJson brandRoot = new TreeJson();
					brandRoot.setId(brandUnitNo);
					brandRoot.setText(brandUnitName);
					brandRoot.setState("closed");
					resultList.add(brandRoot);
				}
				
				TreeJson brandLeaf = new TreeJson();
				brandLeaf.setId(brand.getBrandNo());
				brandLeaf.setText(brand.getName());
				ArrayList.class.cast(brandUnitMap.get(brandUnitNo)).add(brandLeaf);
				
			}
			
			//将临时分组内容添加至对应的返回结果集中
			for(TreeJson treeJson : resultList) {
				treeJson.setChildren(ArrayList.class.cast(brandUnitMap.get(treeJson.getId())));
			}
			
			return resultList;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}