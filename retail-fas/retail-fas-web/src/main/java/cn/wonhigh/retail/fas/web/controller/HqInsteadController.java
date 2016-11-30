/**
 * title:HqInsteadController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:TODO
 * auther:user
 * date:2015-4-11 下午3:22:59
 */
package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

@Controller
@RequestMapping("hq_insteadOf_buy/hq/")
@ModuleVerify("30520014")
public class HqInsteadController extends HqInsteadOfBuyController{

}
