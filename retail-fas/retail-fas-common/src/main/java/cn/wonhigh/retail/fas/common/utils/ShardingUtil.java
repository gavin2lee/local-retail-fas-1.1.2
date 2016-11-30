package cn.wonhigh.retail.fas.common.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.backend.security.DataAccessEnum;

import com.yougou.logistics.base.common.model.SystemUser;

/**
 * TODO: 增加描述
 * 
 * @author 王孟
 * @date 2015-8-6 上午10:31:42
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class ShardingUtil {
	
	public static boolean isPEByShardingFlag(String shardingFlag) {
		return StringUtils.isNotBlank(shardingFlag) && shardingFlag.indexOf("U010102")!= -1;
	}
	
	public static boolean isShoes() {
		SystemUser currUser = Authorization.getUser();
		if(currUser != null){
			String organTypeNo = currUser.getOrganTypeNo();
			if(organTypeNo.indexOf("U010101")!= -1){//鞋
				 return true;
			}
			return false;
		}
		return false;
	}
	
	public static boolean isPE() {
		SystemUser currUser = Authorization.getUser();
		if(currUser != null){
			String organTypeNo = currUser.getOrganTypeNo();
			if(organTypeNo.indexOf("U010102")!= -1){//体
				 return true;
			}
			return false;
		}
		return false;
	}
	
	public static boolean isBLK(){
		SystemUser currUser = Authorization.getUser();
		if(currUser != null){
			String organTypeNo = currUser.getOrganTypeNo();
			if(organTypeNo.indexOf("U010105")!= -1){//巴洛克
				 return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * 根据大区获取分库字段
	 * @param companyNo
	 * @return
	 */
	public static String getShardingFlagByZoneNo(String zoneNo) {
		SystemUser currUser = Authorization.getUser();
		if(currUser != null){
			String organTypeNo = currUser.getOrganTypeNo();
			if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){//集团总部
				 return "0_Z";
			}
			if(StringUtils.isNotBlank(zoneNo)){
				return organTypeNo.concat("_").concat(zoneNo);
			}
		}
		return "";
	}
	
	/**
	 * 根据公司获取分库字段
	 * @param companyNo
	 * @return
	 */
	public static String getShardingFlagByCompanyNo(String companyNo) {
		SystemUser currUser = Authorization.getUser();
		if(currUser != null){
			String organTypeNo = currUser.getOrganTypeNo();
			if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){//集团总部
				 return "0_Z";
			}
			if(StringUtils.isNotBlank(companyNo)){
				return organTypeNo.concat("_").concat(companyNo.substring(0,1));
			}
		}
		return "";
	}
	
	/**
	 * 根据本部和大区获取分库字段
	 * @param companyNo
	 * @param organTypeNo
	 * @return
	 */
	public static String getShardingFlag(String companyNo, String organTypeNo) {
		if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){//集团总部
			 return "0_Z";
		}
		if(StringUtils.isNotBlank(companyNo) && StringUtils.isNotBlank(organTypeNo)){
			return organTypeNo.concat("_").concat(companyNo.substring(0,1));
		}
		return "";
	}

	/**
	 * 根据当前用户信息获取分库字段(用作多选查询过滤)('U010101-C','U010101-D')
	 * @return
	 */
	public static String getMultiShardingFlag() {
		SystemUser currUser = Authorization.getUser();
		if(currUser != null){
			String organTypeNo = currUser.getOrganTypeNo();
			if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){//集团总部
				 return "('0_Z')";
			}
			List<String> lstZone = Authorization.getAccessData(DataAccessEnum.ZONE);
			if(!CollectionUtils.isEmpty(lstZone)){
				StringBuffer sb = new StringBuffer();
				for (String zoneNo : lstZone) {
					sb.append("'").append(organTypeNo).append("_").append(zoneNo).append("',");
				}
				return "(".concat(sb.deleteCharAt(sb.length()-1).toString()).concat(")");
			}
		}
		return "";
	}
	
	/**
	 * 根据当前用户信息获取分库字段
	 * @return
	 */
	public static String getShardingFlag() {
		SystemUser currUser = Authorization.getUser();
		if(currUser != null){
			String organTypeNo = currUser.getOrganTypeNo();
			if(StringUtils.isBlank(organTypeNo) || "0".equals(organTypeNo)){//集团总部
				 return "0_Z";
			}
			return organTypeNo.concat("_").concat(Authorization.getCurrentZone());
		}
		return "";
	}
}
