/**
 * title:AreaInsteadOfBuyDuductController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:总部代采(地区应付其他扣项)
 * auther:user
 * date:2015-4-11 下午2:14:33
 */
package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

@Controller
@RequestMapping("other_deduction/area_inSteadOf")
@ModuleVerify("30161001")
public class AreaInsteadOfBuyDuductController extends OtherDeductionController{

}
