package cn.wonhigh.retail.fas.common.utils;
import java.util.Random;

import com.yougou.logistics.base.common.utils.UUIDHexGenerator;

/**
 * 生成UUID工具类
 * 
 * @author dai.xw
 * 
 */
public class UUIDGenerator {
	public UUIDGenerator() {
	}

	public static String getUUID() {
		return UUIDHexGenerator.generate();
//		UUID uuid = UUID.randomUUID();
//		String str = uuid.toString();
//		String temp = str.replaceAll("-", "");
//		// 去掉"-"符号
//		return temp;
	}

	// 获得指定数量的UUID
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

	/**
	 * 生成激活验证码,从时间戳取出
	 * 
	 * @Title: buildNumber
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param length
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @author zhu.b
	 * @date 2011-5-23 下午09:48:29
	 * @throws
	 */
	public static String generateNumber(int length) {
		long time = System.currentTimeMillis();

		Random random = new Random();
		StringBuffer buffer = new StringBuffer().append(time);

		String result = "";

		if (buffer.length() >= length) {
			buffer = new StringBuffer();
			for (int i = 0; i < length; i++) {
				buffer.append(random.nextInt(9));
			}
		} else {
			for (int i = 0; i < (length - buffer.length()); i++) {
				buffer.append(random.nextInt(9));
			}
		}

		result = buffer.toString();
		return result;
	}
}
