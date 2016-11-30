package cn.wonhigh.retail.fas.common.vo;
import java.net.URLDecoder;
import cn.wonhigh.retail.backend.security.Authorization;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * 
 * 获取当前用户
 * 
 * @author sun.wr
 * @date 2014-10-14 上午11:31:23
 * @version 0.1.0 
 * @copyright yougou.com
 */
public class CurrentUser {

	/**
	 * 获取当期用户。 由于已经在CurrentUserInterceptor设置，所以不需要传任何参数。
	 * @return user
	 */
	public static SystemUser getCurrentUser() {
		try {
			SystemUser user = Authorization.getUser();
			if (user != null){
				String userName = "";
				userName = URLDecoder.decode(user.getUsername(), "UTF-8");
				user.setUsername(userName);
			}
			return user;
		} catch (Exception e) {
			return null;
		}
		
	}
}
