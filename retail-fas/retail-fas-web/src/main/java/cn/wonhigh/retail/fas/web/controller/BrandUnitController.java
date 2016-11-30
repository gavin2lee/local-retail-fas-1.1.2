package cn.wonhigh.retail.fas.web.controller;

import java.util.List;

import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.manager.BrandUnitManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2014-12-05 16:36:16
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
@Controller
@RequestMapping("/brand_unit")
public class BrandUnitController extends BaseCrudController<BrandUnit> {
    @Resource
    private BrandUnitManager brandUnitManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("brand_unit/",brandUnitManager);
    }
    
    /**
     * 查询品牌部
     * @return
     * @throws ManagerException
     */
    @RequestMapping("/getBrandUnit")
    @ResponseBody
    public List<BrandUnit> getJsonData() throws ManagerException{
    	return brandUnitManager.findByBiz(null, null);
    }
}