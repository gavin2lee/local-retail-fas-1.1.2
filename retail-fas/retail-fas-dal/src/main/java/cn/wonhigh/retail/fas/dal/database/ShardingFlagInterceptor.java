package cn.wonhigh.retail.fas.dal.database;

import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import cn.wonhigh.retail.backend.security.Authorization;

import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.ReflectHelper;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class ShardingFlagInterceptor  implements Interceptor {

	private String[] lstRegex = new String[]{"/\\*ignore_sharding_flag\\*/","sharding_flag(\\s)?=","coding_rule","brand_online"};
	
	private String[] lstOrganRegex = new String[]{"/\\*organ_type_no\\*/","/\\*belonger\\*/","/\\*sys_no\\*/"};
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		 SystemUser currUser = Authorization.getUser();
		 if(currUser != null){
			 StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		     BoundSql boundSql = statementHandler.getBoundSql();  
		     String sql = sqlReplaceShardingFlag(currUser, boundSql.getSql());
		     sql = sqlReplaceOrganType(currUser,sql);
		     ReflectHelper.setValueByFieldName(boundSql, "sql", sql);
		 }
		 return invocation.proceed();
	}

	/**
	 * 转换本部字段
	 * @param currUser
	 * @param sql
	 * @return
	 */
	private String sqlReplaceOrganType(SystemUser currUser, String sql) {
		if(StringUtils.startsWithIgnoreCase(sql, "UPDATE") || 
				StringUtils.startsWithIgnoreCase(sql, "DELETE") ||
					StringUtils.startsWithIgnoreCase(sql, "INSERT INTO") ||
				null == sql ){
			return sql;
		}
		String organTypeNo = currUser.getOrganTypeNo();
		for (String str : lstOrganRegex) {
			StringBuffer sb = new StringBuffer(256); 
			Pattern regex = Pattern.compile(str,Pattern.CASE_INSENSITIVE);
			Matcher m = regex.matcher(sql);
			while(m.find()){
				String matchStr = m.group();
				m.appendReplacement(sb, "AND ".concat(matchStr.substring(2, matchStr.length()-2)).concat(" = '").concat(organTypeNo).concat("'"));
			}
			m.appendTail(sb);
			sql = sb.toString();
		}
		return sql;
	}

	/**
	 * 转换分库字段
	 * @param sql
	 * @return sql
	 * @throws Exception 
	 */
	private String sqlReplaceShardingFlag(SystemUser currUser, String sql) throws Exception{
		if(StringUtils.indexOfIgnoreCase(sql, "INSERT INTO") >=0 ||
				null == sql ){
			return sql;
		}
		for (String str : lstRegex) {
			Pattern regex = Pattern.compile(str,Pattern.CASE_INSENSITIVE);
			Matcher m = regex.matcher(sql);
			if (m.find()){
				return sql;
			}
		}
		String organTypeNo = currUser.getOrganTypeNo();
		String currZone = Authorization.getCurrentZone();
		if(StringUtils.isBlank(currZone)){
			throw new Exception("获取当前选择大区信息出错!");
		}
		String shardingFlag = "'".concat(organTypeNo.concat("_").concat(currZone)).concat("'");
		String annotation = " /*#mycat:sql=select 1 from bill_shop_balance where sharding_flag= ".concat(shardingFlag).concat("*/");
		if(sql.indexOf("/*sharding_flag*/") != -1){
			return annotation.concat(sql); 
		}else{
			if(StringUtils.startsWithIgnoreCase(sql, "UPDATE")|| 
					StringUtils.startsWithIgnoreCase(sql, "DELETE")){
				return sql;
			}
		}
		return annotation.concat(sql);
	}
	
	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
		 
	}
	
}
