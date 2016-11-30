package cn.wonhigh.retail.fas.web.controller;

import cn.wonhigh.retail.fas.common.model.CostComputeRule;
import cn.wonhigh.retail.fas.manager.CostComputeRuleManager;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

/**
 * 参考价格计算规则
 * @author user
 * @date  2015-06-09 14:48:18
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
@RequestMapping("/base_setting/cost_compute_rule")
@ModuleVerify("30100019")
public class CostComputeRuleController extends BaseController<CostComputeRule> {
    @Resource
    private CostComputeRuleManager costComputeRuleManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/cost_compute_rule/",costComputeRuleManager);
    }
}