package cn.wonhigh.retail.fas.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yougou.logistics.base.common.annotation.ModuleVerify;

/**
 * 收发货在途对账报表
 * @author ning.ly
 * @date  2015-04-24 10:10:28
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
@RequestMapping("/transfering_check_area")
@ModuleVerify("30510403")
public class TransferingCheckAreaController extends TransferingCheckController {}