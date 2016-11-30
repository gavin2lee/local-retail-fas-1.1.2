package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yougou.logistics.base.common.annotation.ModuleVerify;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-27 16:17:53
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
@Controller
@RequestMapping("/balance_date_type1")
@ModuleVerify("30130001")
public class BalanceDateType1Controller extends BalanceDateController {}