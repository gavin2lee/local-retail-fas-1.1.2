/**
 * title:AreaPrivatePurchaseDeductController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:地区自购其他扣项
 * auther:user
 * date:2015-4-10 下午2:22:09
 */
package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

@Controller
@RequestMapping("other_deduction/area_purchase")
@ModuleVerify("30134004")
public class AreaPrivatePurchaseDeductController extends OtherDeductionController{

}
