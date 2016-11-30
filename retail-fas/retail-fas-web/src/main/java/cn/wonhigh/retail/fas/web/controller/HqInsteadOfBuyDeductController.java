/**
 * title:HqInsteadOfBuyDeductController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:总部代采其他扣项
 * auther:user
 * date:2015-4-10 下午2:25:23
 */
package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

@Controller
@RequestMapping("other_deduction/hq_inSteadOf")
@ModuleVerify("30520013")
public class HqInsteadOfBuyDeductController extends OtherDeductionController{

}
