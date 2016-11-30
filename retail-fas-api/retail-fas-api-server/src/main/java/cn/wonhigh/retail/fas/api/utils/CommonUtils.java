package cn.wonhigh.retail.fas.api.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;

public class CommonUtils {

	/**
	 * 判断是否为体育单据
	 * @param shardingFlag
	 * @return
	 */
	public static boolean isPEByShardingFlag(String shardingFlag) {
		return StringUtils.isNotBlank(shardingFlag) && shardingFlag.indexOf("U010102")!= -1;
	}
	
	
	/**
	 * 判断是否需要配折
	 * @param shardingFlag
	 * @return
	 */
	public static boolean isNeedHanderPEValence(String companyNo,String hqCompanyNo, BillBalanceApiDto dto) {// 体育总部单据(到货单,验收单)
		int billType = dto.getBillType();
		String shardingFlag = dto.getShardingFlag();
		return  StringUtils.isNotBlank(shardingFlag) && shardingFlag.indexOf("U010102")!= -1 
				&&  hqCompanyNo.indexOf(companyNo)!= -1
				&&  (billType == BillTypeEnums.ASN.getRequestId().intValue() 
						|| billType == BillTypeEnums.RECEIPT.getRequestId().intValue());
	}
	
	/**
	 * 判断是否需要买卖方对调
	 * @param shardingFlag
	 * @return
	 */
	public static boolean isNeedChangePE(BillBalanceApiDto dto) {
		Integer billType = dto.getBillType();
		if( billType == null )
			throw new NullPointerException("bill type 不能为空");
		
		Integer bizType = dto.getBizType();
		String shardingFlag = dto.getShardingFlag();
		Integer isSplit = dto.getIsSplit();
		if( isSplit == null )
			return false;
		return StringUtils.isNotBlank(shardingFlag) && shardingFlag.indexOf("U010102")!= -1
				&& isSplit == 1 && 
				( (billType == BillTypeEnums.ASN.getRequestId().intValue() && (null != bizType && bizType == 4))
					|| (billType == BillTypeEnums.RETURNOWN.getRequestId().intValue()) );
	}
	
	/**
	 * 判断数组是否为空
	 * @param arr
	 * @return
	 */
	public static boolean arrayIsNull(Object[] arr) {
		if(arr != null && arr.length > 0) {
			return false;
		}
		return true;
	}
	/**
	 * 判断List是否为空
	 * @param list
	 * @return
	 */
	public static boolean listIsNull(List<Object> list) {
		if(list != null && list.size() > 0) {
			return false;
		}
		return true;
	}
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean strIsNull(String str) {
		if(str != null && !"".equals(str)) {
			return false;
		}
		return true;
	}
	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param plainText
	 *            String
	 * @return String
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5(String plainText) throws NoSuchAlgorithmException {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchAlgorithmException(e.getMessage(), e);
		}
	}
}
