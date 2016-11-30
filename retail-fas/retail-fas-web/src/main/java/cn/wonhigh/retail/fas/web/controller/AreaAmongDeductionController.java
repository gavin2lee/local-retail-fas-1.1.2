/**
 * title:AreaAmongDeductionController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:地区间其他扣项
 * auther:user
 * date:2015-4-10 下午2:13:01
 */
package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

@Controller
@RequestMapping("other_deduction/area_among")
@ModuleVerify("30133008")
public class AreaAmongDeductionController extends OtherDeductionController{

}
