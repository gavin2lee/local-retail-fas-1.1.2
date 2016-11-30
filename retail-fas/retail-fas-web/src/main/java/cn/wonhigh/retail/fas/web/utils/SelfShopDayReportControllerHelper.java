package cn.wonhigh.retail.fas.web.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;

import cn.wonhigh.retail.fas.common.model.SaleOrderPayway;
import cn.wonhigh.retail.fas.web.controller.SelfShopDayReportController;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;

public abstract class SelfShopDayReportControllerHelper {
	private static Field[] fields = SaleOrderPayway.class.getDeclaredFields();// 声明静态变量，供对象共享
	private static Logger logger = Logger.getLogger(SelfShopDayReportControllerHelper.class);

	/**
	 * 不同业务查询使用接口实现
	 * 
	 * @author wangshimiao
	 * 
	 */
	public abstract Map<String, Object> getList(Map<String, Object> params)
			throws ManagerException;

	public abstract Map<String, Object> getExportList(Map<String, Object> params)
			throws ManagerException;

	public SaleOrderPayway getAllCount(List<SaleOrderPayway> list)
			throws ManagerException {
		SaleOrderPayway all = new SaleOrderPayway();
		this.initSaleOrderPayway(all);
		all.setTotal(list.size());
		try {
			for (SaleOrderPayway s : list) {
				for (Field field : fields) {
					String name = field.getName();
					field.setAccessible(true);// 私有属性设置可访问
					if (name.matches("p\\d+") || name.matches("s\\d+")
							|| name.matches("d\\d+")
							|| name.equals("totalAmount")
							|| name.equals("amount")
							|| name.equals("returnAmount")
							|| name.equals("actualReturnAmount")
							|| name.equals("diffAmount")
							|| name.equals("poundage") || name.equals("sum")) {
						// if(name.equals("returnAmount")){
						Method method = all.getClass().getMethod(
								"set" + name.substring(0, 1).toUpperCase()
										+ name.substring(1), BigDecimal.class);
						BigDecimal val = (BigDecimal) field.get(all);
						if (val != null && field.get(s) != null) {
							val = val.add((BigDecimal) field.get(s));
							method.invoke(all, val);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}

		return all;
	}

	protected SaleOrderPayway calculateError(SaleOrderPayway all) {
		all.setP01(all.getP01().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP03(all.getP03().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP04(all.getP04().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP05(all.getP05().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP06(all.getP06().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP08(all.getP08().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP09(all.getP09().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP10(all.getP10().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP11(all.getP11().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP12(all.getP12().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP13(all.getP13().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP14(all.getP14().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP15(all.getP15().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP16(all.getP16().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP17(all.getP17().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP18(all.getP18().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP19(all.getP19().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP20(all.getP20().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP20151010(all.getP20151010().setScale(2,
				BigDecimal.ROUND_HALF_DOWN));
		all.setP21(all.getP21().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP22(all.getP22().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP23(all.getP23().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP24(all.getP24().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP25(all.getP25().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP26(all.getP26().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP27(all.getP27().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP28(all.getP28().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP29(all.getP29().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP30(all.getP30().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP31(all.getP31().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP32(all.getP32().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP33(all.getP33().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP35(all.getP35().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP36(all.getP36().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP37(all.getP37().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		all.setP38(all.getP38().setScale(2, BigDecimal.ROUND_HALF_DOWN));
		return all;
	}

	protected List<SaleOrderPayway> getPaidinAmountForBrandByOutDate(
			List<SaleOrderPayway> dailyReportList, Map<String, BigDecimal> map) {
		for (SaleOrderPayway s : dailyReportList) {
			BigDecimal bg = map.get(s.getShopNo() + s.getOutDate());
			if (bg != null) {
				s.setS01(bg);
			} else {
				s.setS01(new BigDecimal(0d));
			}

		}
		return dailyReportList;
	}

	protected List<SaleOrderPayway> getPaidinAmountByOutDate(
			List<SaleOrderPayway> list, Map<String, BigDecimal> map) {
		for (SaleOrderPayway s : list) {
			BigDecimal bg = map.get(s.getShopNo() + s.getOutDate());
			if (bg != null) {
				s.setS01(bg);
				s.setD01(s.getS01().subtract(s.getP01()));
				s.setAmount(s.getAmount().add(s.getS01()));
				s.setDiffAmount(s.getDiffAmount().add(s.getS01()));
				s.setSum(s.getSum().add(s.getS01()));
			} else {
				s.setS01(new BigDecimal(0d));
				s.setD01(s.getP01().negate());
			}

		}
		return list;
	}

	/**
	 * 分品牌计算所有合计的实收、差异和总计
	 * 
	 * @param all
	 * @param list
	 * @return
	 * @throws ManagerException
	 */
	protected SaleOrderPayway getPaininAndDiff(List<SaleOrderPayway> list)
			throws ManagerException {
		SaleOrderPayway all = this.getAllCount(list);
		if (all.getTotal() > 0) {
			Field[] fields = SaleOrderPayway.class.getDeclaredFields();
			try {
				for (Field field : fields) {
					field.setAccessible(true);
					String name = field.getName();
					if (name.matches("s\\d+") || name.matches("d\\d+")
							|| name.equals("amount")
							|| name.equals("diffAmount")
							|| name.equals("returnAmount")
							|| name.equals("actualReturnAmount")
							|| name.equals("poundage") || name.equals("sum")) {
						BigDecimal val = (BigDecimal) field.get(all);
						Method method = SaleOrderPayway.class.getMethod(
								"set" + name.substring(0, 1).toUpperCase()
										+ name.substring(1), BigDecimal.class);
						method.invoke(all,
								val.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}
			} catch (Exception e) {
				throw new ManagerException(e.getMessage(), e);
			}
		}
		return all;
	}

	/**
	 * 小计误差处理
	 * 
	 * @param dailyReportList
	 * @return
	 * @throws ManagerException 
	 */
	protected List<SaleOrderPayway> dailyReportListByShopNo(
			List<SaleOrderPayway> dailyReportList) throws ManagerException {
		SaleOrderPayway temp = null;
		Map<String, SaleOrderPayway> map = new HashMap<String, SaleOrderPayway>();
		List<SaleOrderPayway> t = null;
		for (SaleOrderPayway saleOrderPayway : dailyReportList) {
			if (null == map.get(saleOrderPayway.getShopName()
					+ saleOrderPayway.getOutDate())) {
				dailyReportList = calculate(dailyReportList, t, map);
				t = new ArrayList<SaleOrderPayway>();
				temp = new SaleOrderPayway();
				this.initSaleOrderPayway(temp);
				temp.setShopNo("小计");
				temp.setShopName(saleOrderPayway.getShopName());
				map.put(saleOrderPayway.getShopName()
						+ saleOrderPayway.getOutDate(), temp);
			}
			this.sumToSaleOrderPayway(
					map.get(saleOrderPayway.getShopName()
							+ saleOrderPayway.getOutDate()), saleOrderPayway);
			t.add(saleOrderPayway);
		}
		dailyReportList = calculate(dailyReportList, t, map);
		return dailyReportList;
	}

	protected List<SaleOrderPayway> dailyReportListByBrand(
			List<SaleOrderPayway> dailyReportList) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SaleOrderPayway> list = new ArrayList<SaleOrderPayway>();
		for (SaleOrderPayway saleOrderPayway : dailyReportList) {
			if (null == map.get(saleOrderPayway.getShopNo()
					+ saleOrderPayway.getOutDate())) {
				getSaleOrderPaywayList(dailyReportList, list);
				list = new ArrayList<SaleOrderPayway>();
				map.put(saleOrderPayway.getShopNo()
						+ saleOrderPayway.getOutDate(), saleOrderPayway);
			}
			if (!"小计".equals(saleOrderPayway.getShopNo())) {
				list.add(saleOrderPayway);
			}
		}
		getSaleOrderPaywayList(dailyReportList, list);
		return dailyReportList;
	}

	private void getSaleOrderPaywayList(List<SaleOrderPayway> dailyReportList,
			List<SaleOrderPayway> list) {
		if (list == null || list.size() <= 0)
			return;
		// 计算店铺某天所有品牌销售额
		BigDecimal totalAmount = new BigDecimal(0d);
		for (SaleOrderPayway saleOrderPayway : list) {
			totalAmount = totalAmount.add(saleOrderPayway.getTotalAmount());
		}
		BigDecimal s01 = BigDecimal.valueOf(0d);
		BigDecimal s03 = BigDecimal.valueOf(0d);
		BigDecimal s04 = BigDecimal.valueOf(0d);
		BigDecimal s05 = BigDecimal.valueOf(0d);
		BigDecimal s06 = BigDecimal.valueOf(0d);
		BigDecimal s08 = BigDecimal.valueOf(0d);
		BigDecimal s09 = BigDecimal.valueOf(0d);
		BigDecimal s10 = BigDecimal.valueOf(0d);
		BigDecimal s11 = BigDecimal.valueOf(0d);
		BigDecimal s12 = BigDecimal.valueOf(0d);
		BigDecimal s13 = BigDecimal.valueOf(0d);
		BigDecimal s14 = BigDecimal.valueOf(0d);
		BigDecimal s15 = BigDecimal.valueOf(0d);
		BigDecimal s16 = BigDecimal.valueOf(0d);
		BigDecimal s17 = BigDecimal.valueOf(0d);
		BigDecimal s18 = BigDecimal.valueOf(0d);
		BigDecimal s19 = BigDecimal.valueOf(0d);
		BigDecimal s20 = BigDecimal.valueOf(0d);
		BigDecimal s20151010 = BigDecimal.valueOf(0d);
		BigDecimal s21 = BigDecimal.valueOf(0d);
		BigDecimal s22 = BigDecimal.valueOf(0d);
		BigDecimal s23 = BigDecimal.valueOf(0d);
		BigDecimal s24 = BigDecimal.valueOf(0d);
		BigDecimal s25 = BigDecimal.valueOf(0d);
		BigDecimal s26 = BigDecimal.valueOf(0d);
		BigDecimal s27 = BigDecimal.valueOf(0d);
		BigDecimal s28 = BigDecimal.valueOf(0d);
		BigDecimal s29 = BigDecimal.valueOf(0d);
		BigDecimal s30 = BigDecimal.valueOf(0d);
		BigDecimal s31 = BigDecimal.valueOf(0d);
		BigDecimal s32 = BigDecimal.valueOf(0d);
		BigDecimal s33 = BigDecimal.valueOf(0d);
		BigDecimal s35 = BigDecimal.valueOf(0d);
		BigDecimal s36 = BigDecimal.valueOf(0d);
		BigDecimal s37 = BigDecimal.valueOf(0d);
		BigDecimal s38 = BigDecimal.valueOf(0d);
		BigDecimal s999 = BigDecimal.valueOf(0d);
		BigDecimal amount = BigDecimal.valueOf(0d);
		BigDecimal returnAmount = BigDecimal.valueOf(0d);
		BigDecimal actualReturnAmount = BigDecimal.valueOf(0d);
		BigDecimal poundage = BigDecimal.valueOf(0d);
		int count = 0;
		for (SaleOrderPayway saleOrderPayway : list) {
			if (count == list.size() - 1)
				break;
			int i = dailyReportList.indexOf(saleOrderPayway);
			if (totalAmount.compareTo(BigDecimal.ZERO) != 0) {// 除数为正
				dailyReportList.get(i).setS01(
						saleOrderPayway
								.getS01()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s01 = s01.add(dailyReportList.get(i).getS01());
				dailyReportList.get(i).setS03(
						saleOrderPayway
								.getS03()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s03 = s03.add(dailyReportList.get(i).getS03());
				dailyReportList.get(i).setS04(
						saleOrderPayway
								.getS04()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s04 = s04.add(dailyReportList.get(i).getS04());
				dailyReportList.get(i).setS05(
						saleOrderPayway
								.getS05()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s05 = s05.add(dailyReportList.get(i).getS05());
				dailyReportList.get(i).setS06(
						saleOrderPayway
								.getS06()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s06 = s06.add(dailyReportList.get(i).getS06());
				dailyReportList.get(i).setS08(
						saleOrderPayway
								.getS08()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s08 = s08.add(dailyReportList.get(i).getS08());
				dailyReportList.get(i).setS09(
						saleOrderPayway
								.getS09()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s09 = s09.add(dailyReportList.get(i).getS09());
				dailyReportList.get(i).setS10(
						saleOrderPayway
								.getS10()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s10 = s10.add(dailyReportList.get(i).getS10());
				dailyReportList.get(i).setS11(
						saleOrderPayway
								.getS11()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s11 = s11.add(dailyReportList.get(i).getS11());
				dailyReportList.get(i).setS12(
						saleOrderPayway
								.getS12()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s12 = s12.add(dailyReportList.get(i).getS12());
				dailyReportList.get(i).setS13(
						saleOrderPayway
								.getS13()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s13 = s13.add(dailyReportList.get(i).getS13());
				dailyReportList.get(i).setS14(
						saleOrderPayway
								.getS14()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s14 = s14.add(dailyReportList.get(i).getS14());
				dailyReportList.get(i).setS15(
						saleOrderPayway
								.getS15()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s15 = s15.add(dailyReportList.get(i).getS15());
				dailyReportList.get(i).setS16(
						saleOrderPayway
								.getS16()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s16 = s16.add(dailyReportList.get(i).getS16());
				dailyReportList.get(i).setS17(
						saleOrderPayway
								.getS17()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s17 = s17.add(dailyReportList.get(i).getS17());
				dailyReportList.get(i).setS18(
						saleOrderPayway
								.getS18()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s18 = s18.add(dailyReportList.get(i).getS18());
				dailyReportList.get(i).setS19(
						saleOrderPayway
								.getS19()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s19 = s19.add(dailyReportList.get(i).getS19());
				dailyReportList.get(i).setS20(
						saleOrderPayway
								.getS20()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s20 = s20.add(dailyReportList.get(i).getS20());
				dailyReportList.get(i).setS20151010(
						saleOrderPayway
								.getS20151010()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s20151010 = s20151010
						.add(dailyReportList.get(i).getS20151010());
				dailyReportList.get(i).setS21(
						saleOrderPayway
								.getS21()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s21 = s21.add(dailyReportList.get(i).getS21());
				dailyReportList.get(i).setS22(
						saleOrderPayway
								.getS22()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s22 = s22.add(dailyReportList.get(i).getS22());
				dailyReportList.get(i).setS23(
						saleOrderPayway
								.getS23()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s23 = s23.add(dailyReportList.get(i).getS23());
				dailyReportList.get(i).setS24(
						saleOrderPayway
								.getS24()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s24 = s24.add(dailyReportList.get(i).getS24());
				dailyReportList.get(i).setS25(
						saleOrderPayway
								.getS25()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s25 = s25.add(dailyReportList.get(i).getS25());
				dailyReportList.get(i).setS26(
						saleOrderPayway
								.getS26()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s26 = s26.add(dailyReportList.get(i).getS26());
				dailyReportList.get(i).setS27(
						saleOrderPayway
								.getS27()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s27 = s27.add(dailyReportList.get(i).getS27());
				dailyReportList.get(i).setS28(
						saleOrderPayway
								.getS28()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s28 = s28.add(dailyReportList.get(i).getS28());
				dailyReportList.get(i).setS29(
						saleOrderPayway
								.getS29()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s29 = s29.add(dailyReportList.get(i).getS29());
				dailyReportList.get(i).setS30(
						saleOrderPayway
								.getS30()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s30 = s30.add(dailyReportList.get(i).getS30());
				dailyReportList.get(i).setS31(
						saleOrderPayway
								.getS31()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s31 = s31.add(dailyReportList.get(i).getS31());
				dailyReportList.get(i).setS32(
						saleOrderPayway
								.getS32()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s32 = s32.add(dailyReportList.get(i).getS32());
				dailyReportList.get(i).setS33(
						saleOrderPayway
								.getS33()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s33 = s33.add(dailyReportList.get(i).getS33());
				dailyReportList.get(i).setS35(
						saleOrderPayway
								.getS35()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s35 = s35.add(dailyReportList.get(i).getS35());
				dailyReportList.get(i).setS36(
						saleOrderPayway
								.getS36()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s36 = s36.add(dailyReportList.get(i).getS36());
				dailyReportList.get(i).setS37(
						saleOrderPayway
								.getS37()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s37 = s37.add(dailyReportList.get(i).getS37());
				dailyReportList.get(i).setS38(
						saleOrderPayway
								.getS38()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s38 = s38.add(dailyReportList.get(i).getS38());
				dailyReportList.get(i).setS999(
						saleOrderPayway
								.getS999()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				s999 = s999.add(dailyReportList.get(i).getS999());
				dailyReportList.get(i).setAmount(
						saleOrderPayway
								.getAmount()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN)
								.add(dailyReportList.get(i).getS01()));
				amount = amount.add(dailyReportList.get(i).getAmount());
				dailyReportList.get(i).setReturnAmount(
						saleOrderPayway
								.getReturnAmount()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				returnAmount = returnAmount.add(dailyReportList.get(i)
						.getReturnAmount());
				dailyReportList.get(i).setActualReturnAmount(
						saleOrderPayway
								.getActualReturnAmount()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				actualReturnAmount = actualReturnAmount.add(dailyReportList
						.get(i).getActualReturnAmount());
				dailyReportList.get(i).setD01(
						dailyReportList.get(i).getS01()
								.subtract(dailyReportList.get(i).getP01()));
				dailyReportList.get(i).setD03(
						dailyReportList.get(i).getS03()
								.subtract(dailyReportList.get(i).getP03()));
				dailyReportList.get(i).setD04(
						dailyReportList.get(i).getS04()
								.subtract(dailyReportList.get(i).getP04()));
				dailyReportList.get(i).setD05(
						dailyReportList.get(i).getS05()
								.subtract(dailyReportList.get(i).getP05()));
				dailyReportList.get(i).setD06(
						dailyReportList.get(i).getS06()
								.subtract(dailyReportList.get(i).getP06()));
				dailyReportList.get(i).setD08(
						dailyReportList.get(i).getS08()
								.subtract(dailyReportList.get(i).getP08()));
				dailyReportList.get(i).setD09(
						dailyReportList.get(i).getS09()
								.subtract(dailyReportList.get(i).getP09()));
				dailyReportList.get(i).setD10(
						dailyReportList.get(i).getS10()
								.subtract(dailyReportList.get(i).getP10()));
				dailyReportList.get(i).setD11(
						dailyReportList.get(i).getS11()
								.subtract(dailyReportList.get(i).getP11()));
				dailyReportList.get(i).setD12(
						dailyReportList.get(i).getS12()
								.subtract(dailyReportList.get(i).getP12()));
				dailyReportList.get(i).setD13(
						dailyReportList.get(i).getS13()
								.subtract(dailyReportList.get(i).getP13()));
				dailyReportList.get(i).setD14(
						dailyReportList.get(i).getS14()
								.subtract(dailyReportList.get(i).getP14()));
				dailyReportList.get(i).setD15(
						dailyReportList.get(i).getS15()
								.subtract(dailyReportList.get(i).getP15()));
				dailyReportList.get(i).setD16(
						dailyReportList.get(i).getS16()
								.subtract(dailyReportList.get(i).getP16()));
				dailyReportList.get(i).setD17(
						dailyReportList.get(i).getS17()
								.subtract(dailyReportList.get(i).getP17()));
				dailyReportList.get(i).setD18(
						dailyReportList.get(i).getS18()
								.subtract(dailyReportList.get(i).getP18()));
				dailyReportList.get(i).setD19(
						dailyReportList.get(i).getS19()
								.subtract(dailyReportList.get(i).getP19()));
				dailyReportList.get(i).setD20(
						dailyReportList.get(i).getS20()
								.subtract(dailyReportList.get(i).getP20()));
				dailyReportList.get(i)
						.setD20151010(
								dailyReportList
										.get(i)
										.getS20151010()
										.subtract(
												dailyReportList.get(i)
														.getP20151010()));
				dailyReportList.get(i).setD21(
						dailyReportList.get(i).getS21()
								.subtract(dailyReportList.get(i).getP21()));
				dailyReportList.get(i).setD22(
						dailyReportList.get(i).getS22()
								.subtract(dailyReportList.get(i).getP22()));
				dailyReportList.get(i).setD23(
						dailyReportList.get(i).getS23()
								.subtract(dailyReportList.get(i).getP23()));
				dailyReportList.get(i).setD24(
						dailyReportList.get(i).getS24()
								.subtract(dailyReportList.get(i).getP24()));
				dailyReportList.get(i).setD25(
						dailyReportList.get(i).getS25()
								.subtract(dailyReportList.get(i).getP25()));
				dailyReportList.get(i).setD26(
						dailyReportList.get(i).getS26()
								.subtract(dailyReportList.get(i).getP26()));
				dailyReportList.get(i).setD27(
						dailyReportList.get(i).getS27()
								.subtract(dailyReportList.get(i).getP27()));
				dailyReportList.get(i).setD28(
						dailyReportList.get(i).getS28()
								.subtract(dailyReportList.get(i).getP28()));
				dailyReportList.get(i).setD29(
						dailyReportList.get(i).getS29()
								.subtract(dailyReportList.get(i).getP29()));
				dailyReportList.get(i).setD30(
						dailyReportList.get(i).getS30()
								.subtract(dailyReportList.get(i).getP30()));
				dailyReportList.get(i).setD31(
						dailyReportList.get(i).getS31()
								.subtract(dailyReportList.get(i).getP31()));
				dailyReportList.get(i).setD32(
						dailyReportList.get(i).getS32()
								.subtract(dailyReportList.get(i).getP32()));
				dailyReportList.get(i).setD33(
						dailyReportList.get(i).getS33()
								.subtract(dailyReportList.get(i).getP33()));
				dailyReportList.get(i).setD35(
						dailyReportList.get(i).getS35()
								.subtract(dailyReportList.get(i).getP35()));
				dailyReportList.get(i).setD36(
						dailyReportList.get(i).getS36()
								.subtract(dailyReportList.get(i).getP36()));
				dailyReportList.get(i).setD37(
						dailyReportList.get(i).getS37()
								.subtract(dailyReportList.get(i).getP37()));
				dailyReportList.get(i).setD38(
						dailyReportList.get(i).getS38()
								.subtract(dailyReportList.get(i).getP38()));
				dailyReportList.get(i).setD999(
						dailyReportList.get(i).getS999()
								.subtract(dailyReportList.get(i).getP999()));
				dailyReportList.get(i)
						.setDiffAmount(
								dailyReportList
										.get(i)
										.getAmount()
										.subtract(
												dailyReportList.get(i)
														.getTotalAmount())
										.add(dailyReportList.get(i)
												.getReturnAmount())
										.add(dailyReportList.get(i)
												.getActualReturnAmount()));
				dailyReportList.get(i).setPoundage(
						dailyReportList
								.get(i)
								.getPoundage()
								.multiply(saleOrderPayway.getTotalAmount())
								.divide(totalAmount, 2,
										BigDecimal.ROUND_HALF_DOWN));
				poundage = poundage.add(dailyReportList.get(i).getPoundage());
				dailyReportList.get(i).setSum(
						dailyReportList
								.get(i)
								.getAmount()
								.subtract(dailyReportList.get(i).getPoundage())
								.add(dailyReportList.get(i)
										.getActualReturnAmount()));
			}
			count++;
		}
		// 误差处理
		SaleOrderPayway last = list.get(list.size() - 1);// 获取list最后一个实例
		BigDecimal s01Count = last.getS01();
		int i = dailyReportList.indexOf(last);
		dailyReportList.get(i).setP01(
				dailyReportList.get(i).getP01()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t01 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS01().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s01.compareTo(last.getS01().subtract(t01)) != 0) {
			dailyReportList.get(i).setS01(last.getS01().subtract(s01));// 表示有误差
		} else {
			dailyReportList.get(i).setS01(t01);
		}
		dailyReportList.get(i).setP03(
				dailyReportList.get(i).getP03()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t03 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS03().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s03.compareTo(last.getS03().subtract(t03)) != 0) {
			dailyReportList.get(i).setS03(last.getS03().subtract(s03));// 表示有误差
		} else {
			dailyReportList.get(i).setS03(t03);
		}
		dailyReportList.get(i).setP04(
				dailyReportList.get(i).getP04()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t04 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS04().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s04.compareTo(last.getS04().subtract(t04)) != 0) {
			dailyReportList.get(i).setS04(last.getS04().subtract(s04));// 表示有误差
		} else {
			dailyReportList.get(i).setS04(t04);
		}
		dailyReportList.get(i).setP05(
				dailyReportList.get(i).getP05()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t05 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS05().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s05.compareTo(last.getS05().subtract(t05)) != 0) {
			dailyReportList.get(i).setS05(last.getS05().subtract(s05));// 表示有误差
		} else {
			dailyReportList.get(i).setS05(t05);
		}
		dailyReportList.get(i).setP06(
				dailyReportList.get(i).getP06()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t06 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS06().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s06.compareTo(last.getS06().subtract(t06)) != 0) {
			dailyReportList.get(i).setS06(last.getS06().subtract(s06));// 表示有误差
		} else {
			dailyReportList.get(i).setS06(t06);
		}
		dailyReportList.get(i).setP08(
				dailyReportList.get(i).getP08()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t08 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS08().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s08.compareTo(last.getS08().subtract(t08)) != 0) {
			dailyReportList.get(i).setS08(last.getS08().subtract(s08));// 表示有误差
		} else {
			dailyReportList.get(i).setS08(t08);
		}
		dailyReportList.get(i).setP09(
				dailyReportList.get(i).getP09()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t09 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS09().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s09.compareTo(last.getS09().subtract(t09)) != 0) {
			dailyReportList.get(i).setS09(last.getS09().subtract(s09));// 表示有误差
		} else {
			dailyReportList.get(i).setS09(t09);
		}
		dailyReportList.get(i).setP10(
				dailyReportList.get(i).getP10()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t10 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS10().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s10.compareTo(last.getS10().subtract(t10)) != 0) {
			dailyReportList.get(i).setS10(last.getS10().subtract(s10));// 表示有误差
		} else {
			dailyReportList.get(i).setS10(t10);
		}
		dailyReportList.get(i).setP11(
				dailyReportList.get(i).getP11()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t11 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS11().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s11.compareTo(last.getS11().subtract(t11)) != 0) {
			dailyReportList.get(i).setS11(last.getS11().subtract(s11));// 表示有误差
		} else {
			dailyReportList.get(i).setS11(t11);
		}
		dailyReportList.get(i).setP12(
				dailyReportList.get(i).getP12()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t12 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS12().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s12.compareTo(last.getS12().subtract(t12)) != 0) {
			dailyReportList.get(i).setS12(last.getS12().subtract(s12));// 表示有误差
		} else {
			dailyReportList.get(i).setS12(t12);
		}
		dailyReportList.get(i).setP13(
				dailyReportList.get(i).getP13()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t13 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS13().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s13.compareTo(last.getS13().subtract(t13)) != 0) {
			dailyReportList.get(i).setS13(last.getS13().subtract(s13));// 表示有误差
		} else {
			dailyReportList.get(i).setS13(t13);
		}
		dailyReportList.get(i).setP14(
				dailyReportList.get(i).getP14()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t14 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS14().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s11.compareTo(last.getS14().subtract(t14)) != 0) {
			dailyReportList.get(i).setS14(last.getS14().subtract(s14));// 表示有误差
		} else {
			dailyReportList.get(i).setS14(t14);
		}
		dailyReportList.get(i).setP15(
				dailyReportList.get(i).getP15()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t15 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS15().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s11.compareTo(last.getS15().subtract(t15)) != 0) {
			dailyReportList.get(i).setS15(last.getS15().subtract(s15));// 表示有误差
		} else {
			dailyReportList.get(i).setS15(t15);
		}
		dailyReportList.get(i).setP16(
				dailyReportList.get(i).getP16()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t16 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS16().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s16.compareTo(last.getS16().subtract(t16)) != 0) {
			dailyReportList.get(i).setS16(last.getS16().subtract(s16));// 表示有误差
		} else {
			dailyReportList.get(i).setS16(t16);
		}
		dailyReportList.get(i).setP17(
				dailyReportList.get(i).getP17()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t17 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS17().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s17.compareTo(last.getS17().subtract(t17)) != 0) {
			dailyReportList.get(i).setS17(last.getS17().subtract(s17));// 表示有误差
		} else {
			dailyReportList.get(i).setS17(t17);
		}
		dailyReportList.get(i).setP18(
				dailyReportList.get(i).getP18()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t18 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS18().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s18.compareTo(last.getS18().subtract(t18)) != 0) {
			dailyReportList.get(i).setS18(last.getS18().subtract(s18));// 表示有误差
		} else {
			dailyReportList.get(i).setS18(t18);
		}
		dailyReportList.get(i).setP19(
				dailyReportList.get(i).getP19()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t19 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS19().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s19.compareTo(last.getS19().subtract(t19)) != 0) {
			dailyReportList.get(i).setS19(last.getS19().subtract(s19));// 表示有误差
		} else {
			dailyReportList.get(i).setS19(t19);
		}
		dailyReportList.get(i).setP20(
				dailyReportList.get(i).getP20()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t20 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS20().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s20.compareTo(last.getS20().subtract(t20)) != 0) {
			dailyReportList.get(i).setS20(last.getS20().subtract(s20));// 表示有误差
		} else {
			dailyReportList.get(i).setS20(t20);
		}
		dailyReportList.get(i).setP20151010(
				dailyReportList.get(i).getP20151010()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t20151010 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS20151010()
				.multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s20151010.compareTo(last.getS20151010().subtract(t20151010)) != 0) {
			dailyReportList.get(i).setS20151010(
					last.getS20151010().subtract(s20151010));// 表示有误差
		} else {
			dailyReportList.get(i).setS20151010(t20151010);
		}
		dailyReportList.get(i).setP21(
				dailyReportList.get(i).getP21()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t21 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS21().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s21.compareTo(last.getS21().subtract(t21)) != 0) {
			dailyReportList.get(i).setS21(last.getS21().subtract(s21));// 表示有误差
		} else {
			dailyReportList.get(i).setS21(t21);
		}
		dailyReportList.get(i).setP22(
				dailyReportList.get(i).getP22()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t22 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS22().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s22.compareTo(last.getS22().subtract(t22)) != 0) {
			dailyReportList.get(i).setS22(last.getS22().subtract(s22));// 表示有误差
		} else {
			dailyReportList.get(i).setS22(t22);
		}
		dailyReportList.get(i).setP23(
				dailyReportList.get(i).getP23()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t23 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS23().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s23.compareTo(last.getS23().subtract(t23)) != 0) {
			dailyReportList.get(i).setS23(last.getS23().subtract(s23));// 表示有误差
		} else {
			dailyReportList.get(i).setS23(t23);
		}
		dailyReportList.get(i).setP24(
				dailyReportList.get(i).getP24()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t24 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS24().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s24.compareTo(last.getS24().subtract(t24)) != 0) {
			dailyReportList.get(i).setS24(last.getS24().subtract(s24));// 表示有误差
		} else {
			dailyReportList.get(i).setS24(t24);
		}
		dailyReportList.get(i).setP25(
				dailyReportList.get(i).getP25()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t25 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS25().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s25.compareTo(last.getS25().subtract(t25)) != 0) {
			dailyReportList.get(i).setS25(last.getS25().subtract(s25));// 表示有误差
		} else {
			dailyReportList.get(i).setS25(t25);
		}
		dailyReportList.get(i).setP26(
				dailyReportList.get(i).getP26()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t26 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS26().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s26.compareTo(last.getS26().subtract(t26)) != 0) {
			dailyReportList.get(i).setS26(last.getS26().subtract(s26));// 表示有误差
		} else {
			dailyReportList.get(i).setS26(t26);
		}
		dailyReportList.get(i).setP27(
				dailyReportList.get(i).getP27()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t27 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS27().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s27.compareTo(last.getS27().subtract(t27)) != 0) {
			dailyReportList.get(i).setS27(last.getS27().subtract(s27));// 表示有误差
		} else {
			dailyReportList.get(i).setS27(t27);
		}
		dailyReportList.get(i).setP28(
				dailyReportList.get(i).getP28()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t28 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS28().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s28.compareTo(last.getS28().subtract(t28)) != 0) {
			dailyReportList.get(i).setS28(last.getS28().subtract(s28));// 表示有误差
		} else {
			dailyReportList.get(i).setS28(t28);
		}
		dailyReportList.get(i).setP29(
				dailyReportList.get(i).getP29()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t29 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS29().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s29.compareTo(last.getS29().subtract(t29)) != 0) {
			dailyReportList.get(i).setS29(last.getS29().subtract(s29));// 表示有误差
		} else {
			dailyReportList.get(i).setS29(t29);
		}
		dailyReportList.get(i).setP30(
				dailyReportList.get(i).getP30()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t30 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS30().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s30.compareTo(last.getS30().subtract(t30)) != 0) {
			dailyReportList.get(i).setS30(last.getS30().subtract(s30));// 表示有误差
		} else {
			dailyReportList.get(i).setS30(t30);
		}
		dailyReportList.get(i).setP31(
				dailyReportList.get(i).getP31()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t31 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS31().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s31.compareTo(last.getS31().subtract(t31)) != 0) {
			dailyReportList.get(i).setS31(last.getS31().subtract(s31));// 表示有误差
		} else {
			dailyReportList.get(i).setS31(t31);
		}
		dailyReportList.get(i).setP32(
				dailyReportList.get(i).getP32()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t32 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS32().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s32.compareTo(last.getS32().subtract(t32)) != 0) {
			dailyReportList.get(i).setS32(last.getS32().subtract(s32));// 表示有误差
		} else {
			dailyReportList.get(i).setS32(t32);
		}
		dailyReportList.get(i).setP33(
				dailyReportList.get(i).getP33()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t33 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS33().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s33.compareTo(last.getS33().subtract(t33)) != 0) {
			dailyReportList.get(i).setS33(last.getS33().subtract(s33));// 表示有误差
		} else {
			dailyReportList.get(i).setS33(t33);
		}
		dailyReportList.get(i).setP35(
				dailyReportList.get(i).getP35()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t35 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS35().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s35.compareTo(last.getS35().subtract(t35)) != 0) {
			dailyReportList.get(i).setS35(last.getS35().subtract(s35));// 表示有误差
		} else {
			dailyReportList.get(i).setS35(t35);
		}
		dailyReportList.get(i).setP36(
				dailyReportList.get(i).getP36()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t36 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS36().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s36.compareTo(last.getS36().subtract(t36)) != 0) {
			dailyReportList.get(i).setS36(last.getS36().subtract(s36));// 表示有误差
		} else {
			dailyReportList.get(i).setS36(t36);
		}
		dailyReportList.get(i).setP37(
				dailyReportList.get(i).getP37()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t37 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS37().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s37.compareTo(last.getS37().subtract(t37)) != 0) {
			dailyReportList.get(i).setS37(last.getS37().subtract(s37));// 表示有误差
		} else {
			dailyReportList.get(i).setS37(t37);
		}
		dailyReportList.get(i).setP38(
				dailyReportList.get(i).getP38()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t38 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS38().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s38.compareTo(last.getS38().subtract(t38)) != 0) {
			dailyReportList.get(i).setS38(last.getS38().subtract(s38));// 表示有误差
		} else {
			dailyReportList.get(i).setS38(t38);
		}
		dailyReportList.get(i).setP999(
				dailyReportList.get(i).getP999()
						.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal t999 = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getS999().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (s999.compareTo(last.getS999().subtract(t999)) != 0) {
			dailyReportList.get(i).setS999(last.getS999().subtract(s999));// 表示有误差
		} else {
			dailyReportList.get(i).setS999(t999);
		}
		BigDecimal tReturnAmount = totalAmount
				.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getReturnAmount()
				.multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (returnAmount.compareTo(last.getReturnAmount().subtract(
				tReturnAmount)) != 0) {
			dailyReportList.get(i).setReturnAmount(
					last.getReturnAmount().subtract(returnAmount));// 表示有误差
		} else {
			dailyReportList.get(i).setReturnAmount(tReturnAmount);
		}
		BigDecimal tActualReturnAmount = totalAmount.compareTo(BigDecimal
				.valueOf(0d)) == 0 ? BigDecimal.valueOf(0d) : last
				.getActualReturnAmount().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (actualReturnAmount.compareTo(last.getActualReturnAmount().subtract(
				tActualReturnAmount)) != 0) {
			dailyReportList.get(i).setActualReturnAmount(
					last.getActualReturnAmount().subtract(actualReturnAmount));// 表示有误差
		} else {
			dailyReportList.get(i).setActualReturnAmount(tActualReturnAmount);
		}
		BigDecimal tAmount = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getAmount().multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN)
				.add(last.getS01());
		if (amount.compareTo(last.getAmount().add(s01Count).subtract(tAmount)) != 0) {
			dailyReportList.get(i).setAmount(
					last.getAmount().add(s01Count).subtract(amount));// 表示有误差
		} else {
			dailyReportList.get(i).setAmount(tAmount);
		}
		dailyReportList.get(i).setD01(
				dailyReportList.get(i).getS01()
						.subtract(dailyReportList.get(i).getP01()));
		dailyReportList.get(i).setD03(
				dailyReportList.get(i).getS03()
						.subtract(dailyReportList.get(i).getP03()));
		dailyReportList.get(i).setD04(
				dailyReportList.get(i).getS04()
						.subtract(dailyReportList.get(i).getP04()));
		dailyReportList.get(i).setD05(
				dailyReportList.get(i).getS05()
						.subtract(dailyReportList.get(i).getP05()));
		dailyReportList.get(i).setD06(
				dailyReportList.get(i).getS06()
						.subtract(dailyReportList.get(i).getP06()));
		dailyReportList.get(i).setD08(
				dailyReportList.get(i).getS08()
						.subtract(dailyReportList.get(i).getP08()));
		dailyReportList.get(i).setD09(
				dailyReportList.get(i).getS09()
						.subtract(dailyReportList.get(i).getP09()));
		dailyReportList.get(i).setD10(
				dailyReportList.get(i).getS10()
						.subtract(dailyReportList.get(i).getP10()));
		dailyReportList.get(i).setD11(
				dailyReportList.get(i).getS11()
						.subtract(dailyReportList.get(i).getP11()));
		dailyReportList.get(i).setD12(
				dailyReportList.get(i).getS12()
						.subtract(dailyReportList.get(i).getP12()));
		dailyReportList.get(i).setD13(
				dailyReportList.get(i).getS13()
						.subtract(dailyReportList.get(i).getP13()));
		dailyReportList.get(i).setD14(
				dailyReportList.get(i).getS14()
						.subtract(dailyReportList.get(i).getP14()));
		dailyReportList.get(i).setD15(
				dailyReportList.get(i).getS15()
						.subtract(dailyReportList.get(i).getP15()));
		dailyReportList.get(i).setD16(
				dailyReportList.get(i).getS16()
						.subtract(dailyReportList.get(i).getP16()));
		dailyReportList.get(i).setD17(
				dailyReportList.get(i).getS17()
						.subtract(dailyReportList.get(i).getP17()));
		dailyReportList.get(i).setD18(
				dailyReportList.get(i).getS18()
						.subtract(dailyReportList.get(i).getP18()));
		dailyReportList.get(i).setD19(
				dailyReportList.get(i).getS19()
						.subtract(dailyReportList.get(i).getP19()));
		dailyReportList.get(i).setD20(
				dailyReportList.get(i).getS20()
						.subtract(dailyReportList.get(i).getP20()));
		dailyReportList.get(i).setD20151010(
				dailyReportList.get(i).getS20151010()
						.subtract(dailyReportList.get(i).getP20151010()));
		dailyReportList.get(i).setD21(
				dailyReportList.get(i).getS21()
						.subtract(dailyReportList.get(i).getP21()));
		dailyReportList.get(i).setD22(
				dailyReportList.get(i).getS22()
						.subtract(dailyReportList.get(i).getP22()));
		dailyReportList.get(i).setD23(
				dailyReportList.get(i).getS23()
						.subtract(dailyReportList.get(i).getP23()));
		dailyReportList.get(i).setD24(
				dailyReportList.get(i).getS24()
						.subtract(dailyReportList.get(i).getP24()));
		dailyReportList.get(i).setD25(
				dailyReportList.get(i).getS25()
						.subtract(dailyReportList.get(i).getP25()));
		dailyReportList.get(i).setD26(
				dailyReportList.get(i).getS26()
						.subtract(dailyReportList.get(i).getP26()));
		dailyReportList.get(i).setD27(
				dailyReportList.get(i).getS27()
						.subtract(dailyReportList.get(i).getP27()));
		dailyReportList.get(i).setD28(
				dailyReportList.get(i).getS28()
						.subtract(dailyReportList.get(i).getP28()));
		dailyReportList.get(i).setD29(
				dailyReportList.get(i).getS29()
						.subtract(dailyReportList.get(i).getP29()));
		dailyReportList.get(i).setD30(
				dailyReportList.get(i).getS30()
						.subtract(dailyReportList.get(i).getP30()));
		dailyReportList.get(i).setD31(
				dailyReportList.get(i).getS31()
						.subtract(dailyReportList.get(i).getP31()));
		dailyReportList.get(i).setD32(
				dailyReportList.get(i).getS32()
						.subtract(dailyReportList.get(i).getP32()));
		dailyReportList.get(i).setD33(
				dailyReportList.get(i).getS33()
						.subtract(dailyReportList.get(i).getP33()));
		dailyReportList.get(i).setD35(
				dailyReportList.get(i).getS35()
						.subtract(dailyReportList.get(i).getP35()));
		dailyReportList.get(i).setD36(
				dailyReportList.get(i).getS36()
						.subtract(dailyReportList.get(i).getP36()));
		dailyReportList.get(i).setD37(
				dailyReportList.get(i).getS37()
						.subtract(dailyReportList.get(i).getP37()));
		dailyReportList.get(i).setD38(
				dailyReportList.get(i).getS38()
						.subtract(dailyReportList.get(i).getP38()));
		dailyReportList.get(i).setD999(
				dailyReportList.get(i).getS999()
						.subtract(dailyReportList.get(i).getP999()));
		dailyReportList.get(i).setDiffAmount(
				dailyReportList.get(i).getAmount()
						.subtract(dailyReportList.get(i).getTotalAmount())
						.add(dailyReportList.get(i).getReturnAmount())
						.add(dailyReportList.get(i).getActualReturnAmount()));
		BigDecimal tPoundage = totalAmount.compareTo(BigDecimal.valueOf(0d)) == 0 ? BigDecimal
				.valueOf(0d) : last.getPoundage()
				.multiply(last.getTotalAmount())
				.divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN);
		if (poundage.compareTo(last.getPoundage().subtract(tPoundage)) != 0) {
			dailyReportList.get(i).setPoundage(
					last.getPoundage().subtract(poundage));// 表示有误差
		} else {
			dailyReportList.get(i).setPoundage(tPoundage);
		}
		dailyReportList.get(i).setSum(
				dailyReportList.get(i).getAmount()
						.subtract(dailyReportList.get(i).getPoundage())
						.add(dailyReportList.get(i).getActualReturnAmount()));
	}

	private void initSaleOrderPayway(SaleOrderPayway temp) throws ManagerException {
		for (Field field : fields) {
			this.setField(field, temp, BigDecimal.ZERO);
		}
	}

	private void sumToSaleOrderPayway(SaleOrderPayway mainSaleOrderPayway,
			SaleOrderPayway addSaleOrderPayway) throws ManagerException {
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getType().toString().endsWith("java.math.BigDecimal")) {
					BigDecimal mainVal = (BigDecimal) field
							.get(mainSaleOrderPayway);
					BigDecimal addVal = (BigDecimal) field
							.get(addSaleOrderPayway);
					if (mainVal != null && addVal != null) {
						this.setField(field, mainSaleOrderPayway,
								mainVal.add(addVal));
					}
				}
			}
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 给entity对象的field属性赋值val
	 * 
	 * @param field
	 * @param entity
	 * @param val
	 * @throws ManagerException 
	 */
	private void setField(Field field, Object entity, Object val) throws ManagerException {
		String name = field.getName();
		if (name.matches("p\\d+") || name.matches("s\\d+")
				|| name.matches("d\\d+") || name.equals("totalAmount")
				|| name.equals("amount") || name.equals("returnAmount")
				|| name.equals("actualReturnAmount")
				|| name.equals("diffAmount") || name.equals("poundage")
				|| name.equals("sum")) {
			try {
				Method method = SaleOrderPayway.class.getMethod(
						"set" + name.substring(0, 1).toUpperCase()
								+ name.substring(1), BigDecimal.class);
				method.invoke(entity, val);
			} catch (Exception e) {
				throw new ManagerException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 分品牌明细与所有品牌明细 小计误差处理函数
	 * 
	 * @param dailyReportList
	 *            所有店铺的明细
	 * @param t
	 *            包含同一店铺的明细
	 * @param map
	 *            同一店铺的不同支付方式合计值
	 * @return
	 * @throws ManagerException 
	 */
	private List<SaleOrderPayway> calculate(
			List<SaleOrderPayway> dailyReportList, List<SaleOrderPayway> t,
			Map<String, SaleOrderPayway> map) throws ManagerException {
		if (t == null || t.size() <= 0)
			return dailyReportList;
		try {
			int index = dailyReportList.indexOf(t.get(0));// 第一个位置
			BigDecimal[] s = new BigDecimal[37];
			// 初始化数组
			for (int k = 0; k < s.length; k++) {
				s[k] = BigDecimal.valueOf(0d);
			}
			for (int i = 0; i < t.size(); i++) {
				SaleOrderPayway temp = t.get(i);
				Class<? extends SaleOrderPayway> cls = temp.getClass();
				Field[] fields = cls.getDeclaredFields();
				int j = 0;
				for (Field field : fields) {
					field.setAccessible(true);
					String name = field.getName();
					if (name.matches("p\\d+")) {
						BigDecimal val = (BigDecimal) field.get(temp);
						Method method = cls.getMethod(
								"set" + name.substring(0, 1).toUpperCase()
										+ name.substring(1), BigDecimal.class);
						method.invoke(temp,
								val.setScale(2, BigDecimal.ROUND_HALF_UP));// 同一店铺不同支付方式数值统一为二位小数
						s[j] = s[j].add(val.setScale(2,
								BigDecimal.ROUND_HALF_UP));// 同一店铺纵向相加
						j++;
					}
				}
			}

			SaleOrderPayway ss = map.get(t.get(0).getShopName()
					+ t.get(0).getOutDate());// 同一店铺的各个支付方式总计
			Field[] fs = ss.getClass().getDeclaredFields();
			List<Field> l = new ArrayList<Field>();// 仅仅包含实收的数据
			for (Field f : fs) {
				if (f.getName().matches("p\\d+")) {
					l.add(f);
				}
			}
			for (int m = 0; m < s.length; m++) {
				Field field = l.get(m);
				field.setAccessible(true);
				String name = field.getName();
				Class<? extends SaleOrderPayway> cls = t.get(t.size() - 1)
						.getClass();
				if (s[m].compareTo((BigDecimal) l.get(m).get(ss)) != 0) {
					int n = 0;
					s[m] = (BigDecimal) l.get(m).get(ss);
					while (n < t.size() - 1) {
						s[m] = s[m].subtract((BigDecimal) l.get(m)
								.get(t.get(n)));
						n++;
					}
					Method method = cls.getMethod(
							"set" + name.substring(0, 1).toUpperCase()
									+ name.substring(1), BigDecimal.class);
					method.invoke(t.get(t.size() - 1), s[m]);
				}
			}
			for (SaleOrderPayway saleOrderPayway : t) {
				dailyReportList.set(index, saleOrderPayway);
				index++;
			}
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return dailyReportList;
	}

	/**
	 * list中的对象必须实现序列化接口 执行序列化和反序列化 进行深度拷贝
	 * 
	 * @param srcList
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	protected List<SaleOrderPayway> deepCopy(List<SaleOrderPayway> srcList) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(srcList);

			ByteArrayInputStream byteIn = new ByteArrayInputStream(
					byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			List<SaleOrderPayway> destList = (List<SaleOrderPayway>) in
					.readObject();
			return destList;
		} catch (ClassNotFoundException e) {
			logger.error("未找到指定类", e);
		} catch (IOException e) {
			logger.error("list深拷贝IO流异常", e);
		}
		return srcList;
	}

}
