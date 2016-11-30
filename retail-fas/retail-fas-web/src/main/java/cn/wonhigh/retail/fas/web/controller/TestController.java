package cn.wonhigh.retail.fas.web.controller;
//package com.yougou.retail.fas.web.controller;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.yougou.logistics.base.jms.BelleMessageProducer;
//import com.yougou.retail.fas.common.model.SystemUser;
//import com.yougou.retail.fas.common.vo.TestDto;
//import com.yougou.retail.fas.manager.SystemSettingManager;
//
///**
// * 测试类
// * @author yuesheng.yin
// * @date 2014-6-6 上午11:28:16
// * @version 0.1.0 
// * @copyright yougou.com 
// */
//@Controller
//@RequestMapping("/test")
//public class TestController {
//
//	@Autowired
//	private SystemSettingManager systemSettingManager;
//	@Resource 
//	private BelleMessageProducer messageProducerTest;
//
//	/**
//	 * 跳转到树节点管理
//	 * @return 返回实体与页面
//	 */
//	@RequestMapping("/index")
//	public ModelAndView toSelectUserOrganiz() {
//		System.out.println("--------------------");
//		SystemUser su = systemSettingManager.querySystemUserById(141L);
//		System.out.println(su.getLoginName());
//		//ReadWriteLock locks;
//		TestDto dto = new TestDto();
//		dto.setName("test");
//		dto.setIndex(1);
//		messageProducerTest.send(dto);
//		return new ModelAndView("center");
//	}
//	
//	public static void main(String[] arg) {
//		System.out.println("ddd".getBytes().length);
//		System.getProperties().list(System.out);
//		/*Enumeration<Object> e = System.getProperties().elements();
//		for(;e.hasMoreElements();e.hasMoreElements()) {
//			System.out.println(e.nextElement().toString());
//			System.out.println("-----------------------");
//		}*/
//	}
//
//}
