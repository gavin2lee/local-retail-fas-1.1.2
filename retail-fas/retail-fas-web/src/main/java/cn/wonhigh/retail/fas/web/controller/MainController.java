package cn.wonhigh.retail.fas.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转到主页的controller
 * @author liu.ms
 * @date 2014-6-11 下午2:08:18
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Controller
public class MainController {

	/**
	 * 跳转到页面
	 * @param req 请求对象
	 * @param model 保存对象
	 * @return 返回页面
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest req, Model model) {
		return "index";
	}
}
