package cn.wonhigh.retail.fas.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.service.ShopBalanceDateService;
import cn.wonhigh.retail.fas.service.ShopBrandService;
import cn.wonhigh.retail.fas.service.ShopService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author chen.mj
 * @date 2014-10-15 14:29:23
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("shopBalanceDateManager")
class ShopBalanceDateManagerImpl extends BaseCrudManagerImpl implements
		ShopBalanceDateManager {

	@Resource
	private ShopBalanceDateService shopBalanceDateService;

	@Resource
	private ShopService shopService;

	@Resource
	private ShopBrandService shopBrandService;

	private DateFormat df = new SimpleDateFormat("yyyyMM");
	
	private DateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
	
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(CashCheckManagerImpl.class);

	@Override
	public BaseCrudService init() {
		return shopBalanceDateService;
	}

	/**
	 * 查询指定店铺、指定结算月维护的结算期
	 * 
	 * @param shopNo
	 *            店铺编码
	 * @param month
	 *            结算月
	 * @return 结算期对象
	 */
	@Override
	public ShopBalanceDate findBalanceDate(String shopNo, String month)
			throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", shopNo);
			params.put("month", month);
			List<ShopBalanceDate> lstDate = shopBalanceDateService.findByBiz(
					null, params);
			if (lstDate == null || lstDate.size() == 0) {
				return null;
			}
			// 取出结算期列表中，未生成结算单的最小的结算期数据（如果全部已生成结算单，则取最后一条数据）
			for (int i = 0; i < lstDate.size(); i++) {
				ShopBalanceDate model = lstDate.get(i);
				if ((i + 1) == lstDate.size() || model.getBalanceFlag() == 1) {
					return model;
				}
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public ShopBalanceDate findAndConvertBalanceDate(String shopNo, String month)
			throws ManagerException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", shopNo);
			params.put("month", month);
			List<ShopBalanceDate> lstDate = shopBalanceDateService.findByBiz(
					null, params);
			if (lstDate == null || lstDate.size() == 0) {
				return null;
			}
			ShopBalanceDate result = new ShopBalanceDate();
			for (int i = 0; i < lstDate.size(); i++) {
				if (i == 0) {
					result.setBalanceStartDate(lstDate.get(i)
							.getBalanceStartDate());
				}
				if (i == lstDate.size() - 1) {
					result.setBalanceEndDate(lstDate.get(i).getBalanceEndDate());
				}
			}
			return result;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	public List<ShopBrand> getAllShopByCompanyNo(String companyNo,
			String brandNo) {

		List<ShopBrand> shopBrandList = null;
		try {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", companyNo);
			params.put("brandNo", brandNo);

			shopBrandList = shopBrandService.selectByOwnCondition(params);

			if (shopBrandList != null && shopBrandList.size() > 0) {
				return shopBrandList;
			} else {
				return null;
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return shopBrandList;
	}

	public Map<String, Object> insertBillBalanceDate(
			List<ShopBalanceDate> shopBalanceDateListDto) {

		Map<String, Object> obj = new HashMap<String, Object>();

		StringBuffer sbf = new StringBuffer();

		Date currentTime = new Date();
		for (ShopBalanceDate shopBalanceDate : shopBalanceDateListDto) {
			try {
				shopBalanceDate.setCreateTime(currentTime);
				shopBalanceDateService.add(shopBalanceDate);
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
				sbf.append("  shopNo --> " + shopBalanceDate.getShopNo()
						+ " , month ---> " + shopBalanceDate.getMonth() + " , "
						+ "  balanceStartDate ---> "
						+ shopBalanceDate.getBalanceStartDate()
						+ " , balanceEndDate ---> "
						+ shopBalanceDate.getBalanceEndDate());
				LOGGER.error(e.getMessage(),e);
				continue;
			}
		}

		if ("".equals(sbf.toString())) {
			obj.put("success", true);
		} else {

			String errorInfo = "以下店铺结算期生成问题 : <br />";
			errorInfo = errorInfo + "     <br /> " + sbf.toString();

			obj.put("errorInfo", errorInfo);
		}
		return obj;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public void generateBalanceDate(ShopBalanceDate shopBalanceDate,
			String currentMonth, String lastMonth) throws ManagerException {
		try {
			Map<String, Object> shopParams = new HashMap<String, Object>();
			shopParams.put("shopNo", shopBalanceDate.getShopNo());
			
			Shop shopInfo = shopService.selectSubsiInfo(shopParams);
			shopBalanceDate.setMallNo(shopInfo.getMallNo());
			shopBalanceDate.setMallName(shopInfo.getMallName());
			shopBalanceDate.setBsgroupsNo(shopInfo.getBsgroupsNo());
			shopBalanceDate.setBsgroupsName(shopInfo.getBsgroupsName());
			
			//上月结算期查询
			Map<String, Object> lastMonthParams = new HashMap<String, Object>();
			lastMonthParams.put("shopNo", shopBalanceDate.getShopNo());
			lastMonthParams.put("month", lastMonth);
			List<ShopBalanceDate> shopBalanceDateList = shopBalanceDateService
					.findByBiz(null, lastMonthParams);
			if (CollectionUtils.isEmpty(shopBalanceDateList)) {
				//上月未设置结算期，直接设置结算起止日
				String dateFirst = currentMonth + "01";
				Date generateDate = dayFormat.parse(dateFirst);
				//上月未设置结算期，直接设置结算起止日
				shopBalanceDate.setBalanceStartDate(getFirstDay(generateDate));
				shopBalanceDate.setBalanceEndDate(getLastDay(generateDate));
				shopBalanceDate.setMonth(currentMonth);
				shopBalanceDate.setUpdateUser(shopBalanceDate.getCreateUser());
				shopBalanceDate.setUpdateTime(shopBalanceDate.getCreateTime());
				shopBalanceDateService.add(shopBalanceDate);
			}else {
				//上月设置了结算期
				Date smallestStartDate = null;
				Date bigestEndDate = null;

				// 找出改结算期内最大的日期和最小日期
				for (ShopBalanceDate shopBalanceDateDto : shopBalanceDateList) {

					if (smallestStartDate == null) {
						smallestStartDate = shopBalanceDateDto.getBalanceStartDate();
					}
					if (bigestEndDate == null) {
						bigestEndDate = shopBalanceDateDto.getBalanceEndDate();
					}
					if (shopBalanceDateDto.getBalanceStartDate().getTime() < smallestStartDate.getTime()) {
						smallestStartDate = shopBalanceDateDto.getBalanceStartDate();
					}
					if (shopBalanceDateDto.getBalanceEndDate().getTime() > bigestEndDate.getTime()) {
						bigestEndDate = shopBalanceDateDto.getBalanceEndDate();
					}
				}

				Calendar c = Calendar.getInstance();
				bigestEndDate = appendDaysToDate(bigestEndDate, 1);
				bigestEndDate = appendMonthesToDate(bigestEndDate, -1);
				c.setTime(bigestEndDate);

				// 不是自然月
				if (smallestStartDate.getTime() != c.getTime().getTime()) {
					for (ShopBalanceDate shopBalanceDateDto : shopBalanceDateList) {
						Date _newStartDate = appendDaysToDate(shopBalanceDateDto.getBalanceEndDate(), 1);
						Date _newEndDate = appendMonthesToDate(shopBalanceDateDto.getBalanceEndDate(), 1);
						shopBalanceDateDto.setId(null);
						shopBalanceDateDto.setBalanceStartDate(_newStartDate);
						shopBalanceDateDto.setBalanceEndDate(_newEndDate);
						String newMonth = df.format(_newEndDate);
						shopBalanceDateDto.setMonth(newMonth);
						shopBalanceDateDto.setCreateUser(shopBalanceDate.getCreateUser());
						shopBalanceDateDto.setCreateTime(shopBalanceDate.getCreateTime());
						shopBalanceDateDto.setBalanceFlag(new Integer(1).byteValue()); // 1-未生成结算单
						shopBalanceDateDto.setBillalready(new Integer(1).byteValue()); 
						shopBalanceDateDto.setUpdateUser(shopBalanceDateDto.getCreateUser());
						shopBalanceDateDto.setUpdateTime(shopBalanceDateDto.getCreateTime());
						shopBalanceDateService.add(shopBalanceDateDto);
					}
				} else {
					for (ShopBalanceDate shopBalanceDateDto : shopBalanceDateList) {
						Date _newStartDate = appendMonthesToDate(shopBalanceDateDto.getBalanceStartDate(), 1);
						Date _newEndDate = appendDaysToDate(appendMonthesToDate(appendDaysToDate(shopBalanceDateDto.getBalanceEndDate(),1), 1), -1);

						shopBalanceDateDto.setId(null);
						shopBalanceDateDto.setBalanceStartDate(_newStartDate);
						shopBalanceDateDto.setBalanceEndDate(_newEndDate);
						String newMonth = df.format(_newEndDate);
						shopBalanceDateDto.setMonth(newMonth);
						shopBalanceDateDto.setCreateUser(shopBalanceDate.getCreateUser());
						shopBalanceDateDto.setCreateTime(shopBalanceDate.getCreateTime());
						shopBalanceDateDto.setBalanceFlag(new Integer(1).byteValue()); // 1-未生成结算单
						shopBalanceDateDto.setBillalready(new Integer(1).byteValue()); 
						shopBalanceDateDto.setUpdateUser(shopBalanceDateDto.getCreateUser());
						shopBalanceDateDto.setUpdateTime(shopBalanceDateDto.getCreateTime());
						shopBalanceDateService.add(shopBalanceDateDto);
					}
				}
			}
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, String> validationShopBalanceDate(
			ShopBalanceDate shopBalanceDate) throws ManagerException {
		try {
			Map<String, String> respInfo = new HashMap<String, String>();
			StringBuffer sbf = new StringBuffer();
			String strCondition = null;
			
			Map<String, Object> flag = new HashMap<String, Object>();
			String balanceStartDate = DateUtil.formatDateByFormat(
					shopBalanceDate.getBalanceStartDate(), "yyyy-MM-dd");
			String balanceEndDate = DateUtil.formatDateByFormat(
					shopBalanceDate.getBalanceEndDate(), "yyyy-MM-dd");
			
//			if (null != shopBalanceDate.getId()
//					&& !"".equals(shopBalanceDate.getId())) {
//				strCondition = " and id != ' " + shopBalanceDate.getId()+" ' ";
//			}
			
			flag.put("shopNo", shopBalanceDate.getShopNo());
//			flag.put("month", shopBalanceDate.getMonth());
//			sbf.append(" and balance_start_date between '")
//			.append(balanceStartDate).append("' and '")
//			.append(balanceEndDate).append("'");
			flag.put("balanceStartDate", balanceStartDate);
			flag.put("balanceEndDate", balanceEndDate);
			if (null != shopBalanceDate.getId()
					&& !"".equals(shopBalanceDate.getId())) {
				flag.put("id", shopBalanceDate.getId());
			}
			
//			if (strCondition != null) {
//				sbf.append(strCondition);
//			}
//			flag.put("queryCondition", sbf.toString());
			
			int count = shopBalanceDateService.findCount(flag);
			Date StartDate = null;
			Date EndDate = null;
			
			if (count > 0) {
				
				String month=null;
				List<ShopBalanceDate> shopBalanceDateList = shopBalanceDateService
						.findByBiz(null, flag);
				if (shopBalanceDateList != null && shopBalanceDateList.size() > 0) {
					for (ShopBalanceDate shopBalanceDateDto : shopBalanceDateList) {
						if (month == null) {
							month = shopBalanceDateDto.getMonth();
						}
				      } 
					}else {
					return null;
				}
				
				respInfo.put("error",
						"结算期不能有交叉,当前结算期的起始时间已存在在当月的其他结算期中<br />[店铺:"
								+ shopBalanceDate.getShopNo() + ",结算月:"
								+ month + "]");
//			} else {
//				sbf.setLength(0);
//				sbf.append(" and balance_end_date between '")
//				.append(balanceStartDate).append("' and '")
//				.append(balanceEndDate).append("'");
//				
//				if (strCondition != null) {
//					sbf.append(strCondition);
//				}
//				flag.put("queryCondition", sbf.toString());
//				count = shopBalanceDateService.findCount(flag);
//				if (count > 0) {
//					respInfo.put("error",
//							"结算期不能有交叉,当前结算期的起始时间已存在在当月的其他结算期中<br />[店铺:"
//									+ shopBalanceDate.getShopNo() + ",结算月:"
//									+ shopBalanceDate.getMonth() + "]");
//				} else {
//					sbf.setLength(0);
//					sbf.append(" and balance_start_date < '")
//					.append(balanceStartDate)
//					.append("' and balance_end_date > '")
//					.append(balanceEndDate).append("'");
//					
//					if (strCondition != null) {
//						sbf.append(strCondition);
//					}
//					flag.put("queryCondition", sbf.toString());
//					count = shopBalanceDateService.findCount(flag);
//					if (count > 0) {
//						respInfo.put("error",
//								"结算期不能有交叉,当前结算期的起始时间已存在在当月的其他结算期中<br />[店铺:"
//										+ shopBalanceDate.getShopNo() + ",结算月:"
//										+ shopBalanceDate.getMonth() + "]");
//					} 
			}else {
						respInfo.put("error", null);
					}
			return respInfo;
			
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * Is already generated balance date or not
	 * 
	 * @param shopNo
	 * @param month
	 * @return
	 * @throws ServiceException
	 */
	public boolean isAlreadyGeneratorBalanceDate(String shopNo, String month)
			throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", shopNo);
		params.put("month", month);

		int count = shopBalanceDateService.findCount(params);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 当月第一天
	 * 
	 * @return
	 */
	private Date getFirstDay(Date dateObj) {

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(dateObj);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);

		return gcLast.getTime();
	}

	/**
	 * 当月最后一天
	 * 
	 * @return
	 */
	private Date getLastDay(Date dateObj) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateObj);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	/**
	 * 指定日期上加天数
	 * 
	 * @param dateObj
	 * @param days
	 * @param dateFormat
	 * @return
	 */
	private Date appendDaysToDate(Date dateObj, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateObj);
		calendar.add(Calendar.DATE, days);
		Date theDate = calendar.getTime();
		return theDate;
	}

	public Date appendMonthesToDate(Date dateObj, int monthes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateObj);
		calendar.add(Calendar.MONTH, monthes);
		Date theDate = calendar.getTime();
		return theDate;
	}

	@Override
	public int findNewShopDateCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return shopBalanceDateService.findNewShopDateCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopBalanceDate> findNewShopDateByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return shopBalanceDateService.findNewShopDateByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectNoSetShopBalanceDateCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return shopBalanceDateService.selectNoSetShopBalanceDateCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopBalanceDate> selectNoSetShopBalanceDateByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ManagerException {
		try {
			return shopBalanceDateService.selectNoSetShopBalanceDateByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public int selectShopBalanceDatePartOfRightCount(Map<String, Object> params)
			throws ManagerException {
		try {
			return shopBalanceDateService.selectShopBalanceDatePartOfRightCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<ShopBalanceDate> selectShopBalanceDatePartOfRightByPage(
			SimplePage page, String sortColumn, String sortOrder,
			Map<String, Object> params) throws ManagerException {
		try {
			return shopBalanceDateService.selectShopBalanceDatePartOfRightByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

}