package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.ParamDtl;
import cn.wonhigh.retail.fas.manager.ParamDtlManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-21 10:32:05
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
@RequestMapping("/param_dtl")
public class ParamDtlController extends BaseCrudController<ParamDtl> {
    @Resource
    private ParamDtlManager paramDtlManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("param_dtl/",paramDtlManager);
    }
    
    @RequestMapping(value = "/query_param_dtl")
  	@ResponseBody
  	public List<ParamDtl> querySettleCategoryDtl(@RequestParam("paramCode")String paramCode, 
  			HttpServletRequest request) throws ManagerException {
  		Map<String, Object> params = new HashMap<String, Object>();
  		params.put("paramCode", paramCode);
  		List<ParamDtl> list = this.paramDtlManager.findByBiz(null, params);
  		return list;
      }
}