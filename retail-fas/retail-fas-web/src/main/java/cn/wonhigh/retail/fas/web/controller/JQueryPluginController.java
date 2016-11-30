package cn.wonhigh.retail.fas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JQueryPluginController {

	@RequestMapping("/plugin_page")
	public String pluginPage() {
		return "plugin_page/test";
	}
	
	@RequestMapping("/plugin_page2")
	public String pluginPage2() {
		return "plugin_page/test2";
	}
	
	@RequestMapping("/plugin_page/{path}")
	public String forwardPage(@PathVariable("path")String path) {
		return "/plugin_page/" + path;
	}
	
	@RequestMapping("/test_shop")
	public String testShop() {
		return "plugin_page/testshop";
	}
}
