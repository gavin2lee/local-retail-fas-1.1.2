package cn.wonhigh.retail.fas.web.vo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.NamedThreadLocal;

import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.utils.CommonUtil;

import com.yougou.logistics.base.common.model.SystemUser;

/**
 * @存放当前用户信息
 * @author wei.hj
 * @Date 2013-7-29
 * @version 0.1.0
 * @copyright yougou.com
 */
public class CurrentUser {

	//	private String currentDate10Str; // yyyy-MM-dd
	//	private String currentDate19Str; // yyyy-MM-dd HH:mm:ss

	private static NamedThreadLocal<SystemUser> userThreadLocal = new NamedThreadLocal<SystemUser>("userThreadLocal");

	//	public CurrentUser() {
	//		super();
	//	}

	public static void setCurrentUser(SystemUser user) {
		userThreadLocal.set(user);
	}

	public static SystemUser getCurrentUser() {
		return userThreadLocal.get();
	}

	public static void removeCurrentUer() {
		userThreadLocal.remove();
	}

	public String getCurrentDate10Str() {
		return CommonUtil.getCurrentDate();
	}

	public String getCurrentDate19Str() {
		return CommonUtil.getCurrentDateTime();
	}

	public static SystemUser getCurrentUser(HttpServletRequest req) {
//		SystemUser user = getCurrentUser();
//		if(null == user){
//			user = (SystemUser) req.getSession().getAttribute(PublicConstans.SESSION_USER);
//			userThreadLocal.set(user);
//		}
//		return user;
		SystemUser user = (SystemUser) req.getSession().getAttribute(PublicConstans.SESSION_USER);
		return user;
	}
}

