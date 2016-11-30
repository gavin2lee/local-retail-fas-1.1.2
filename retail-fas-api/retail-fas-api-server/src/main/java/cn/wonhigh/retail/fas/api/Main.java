package cn.wonhigh.retail.fas.api;

import cn.wonhigh.retail.fas.api.config.DiamondLoaderContainer;

/**
 * 服务的入口类
 * 因dubbo自动扩展点无法扩展,故自行实现一个扩展类,预先启动diamond下载配置后启动Spring
 * @author wei.b
 * @date Jun 24, 2014 6:21:00 PM
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class Main{

	/**
	 * @param args dubbo扩展点名称 如:spring、jetty、log4j、logback<br/>
	 * 详细请查询 dubbo包中META-INF\dubbo\com.alibaba.dubbo.container.Container
	 * @see com.alibaba.dubbo.container.Container
	 */
	public static void main(String[] args) {
		new DiamondLoaderContainer().contextInitialized();
		com.alibaba.dubbo.container.Main.main(args);
	}

}
