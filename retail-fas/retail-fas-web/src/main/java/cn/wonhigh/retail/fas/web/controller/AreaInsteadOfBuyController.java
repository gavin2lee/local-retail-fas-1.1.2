/**
 * title:AreaInsteadOfBuyController.java
 * package:cn.wonhigh.retail.fas.web.controller
 * description:总部代采入库(地区应付)
 * auther:user
 * date:2015-4-11 下午3:16:22
 */
package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

@Controller
@RequestMapping("hq_insteadOf_buy/area/")
@ModuleVerify("30161002")
public class AreaInsteadOfBuyController extends HqInsteadOfBuyController{

}
