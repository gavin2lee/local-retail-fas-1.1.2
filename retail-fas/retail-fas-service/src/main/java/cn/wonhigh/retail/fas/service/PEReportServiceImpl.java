package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.PEAPDto;
import cn.wonhigh.retail.fas.common.model.Holiday;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.HolidayUtil;
import cn.wonhigh.retail.fas.dal.database.PEReportMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("peReportService")
class PEReportServiceImpl implements PEReportService {

	@Resource
	private PEReportMapper peReportMapper;
	
	@Resource
	private HolidayService holidayService;
	
	@Override
	public int findAPAgingCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return peReportMapper.findAPAgingCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<PEAPDto> findAPAgingByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return peReportMapper.findAPAgingByPage(page,sortColumn,sortOrder,params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<PEAPDto> findAPAgingFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return peReportMapper.findAPAgingFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int findAPPlanCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return peReportMapper.findAPPlanCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private void setDueAmount(Map<String, Object> map, PEAPDto peapDto, String type){
		if(null == peapDto.getDueDate()){
			map.put("NULL", peapDto.getAllAmount());
		}else{
			String dueDate = DateUtil.format1(peapDto.getDueDate());
			String dueWeekDate = peapDto.getDueWeekDate();
			String dueMonthDate = peapDto.getDueMonthDate();
			if("day".equals(type)){
				map.put(dueDate, peapDto.getAllAmount());
			}else if("week".equals(type)){
				map.put(dueWeekDate, peapDto.getAllAmount());
			}else if("month".equals(type)){
				map.put(dueMonthDate, peapDto.getAllAmount());
			}
		}
	}
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public List<Map<String, Object>> findAPPlanByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			String type = String.valueOf(params.get("sumType"));
			List<PEAPDto> pageCondition = peReportMapper.findPlanPageCondition(page,params);
			params.put("listItem", pageCondition);
			List<PEAPDto> planList = peReportMapper.findAPPlanListByPage(page,sortColumn,sortOrder,params);
			params.put("listItem", "");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for (PEAPDto peapDto : planList) {
				String buyerNo = peapDto.getBuyerNo();
				String buyerName = peapDto.getBuyerName();
				String salerNo = peapDto.getSalerNo();
				String salerName = peapDto.getSalerName();
				BigDecimal allAmount = peapDto.getAllAmount();
				String key = buyerNo.concat("_").concat(salerNo);
				if(null == resultMap.get(key)){
					Map<String, Object> newMap = new HashMap<>();
					newMap.put("buyerName", buyerName);
					newMap.put("salerName", salerName);
					newMap.put("buyerNo", buyerNo);
					newMap.put("salerNo", salerNo);
					newMap.put("allAmount", allAmount);
					setDueAmount(newMap,peapDto,type);
					resultMap.put(key, newMap);
				}else{
					Map<String, Object> oldMap = (Map<String, Object>) resultMap.get(key);
					oldMap.put("allAmount", allAmount.add((BigDecimal) oldMap.get("allAmount")));
					setDueAmount(oldMap,peapDto,type);
				}
			}
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (Entry<String, Object> entry : resultMap.entrySet()) {
				Map<String, Object> map = (Map<String, Object>) entry.getValue();
				resultList.add(map);
			}
			return resultList;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Map<String, Object>> findAPPlanFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			String type = String.valueOf(params.get("sumType"));
			List<PEAPDto> planFooterList = peReportMapper.findAPPlanFooter(params);
			Map<String, Object> newMap = new HashMap<>();
			newMap.put("buyerName", "合计");
			BigDecimal allAmount = new BigDecimal(0);
			for (PEAPDto peapDto : planFooterList) {
				setDueAmount(newMap,peapDto,type);
				allAmount = allAmount.add(peapDto.getAllAmount());
			}
			List<Map<String, Object>> resultList = new ArrayList<>();
			newMap.put("allAmount", allAmount);
			resultList.add(newMap);
			return resultList;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Map<String, Object>> findAPPlanColumn(Map<String, Object> params)
			throws ServiceException {
		try {
			String type = String.valueOf(params.get("sumType"));
			List<PEAPDto> planFooterList = peReportMapper.findAPPlanColumn(params);
			List<Map<String, Object>> resultList = new ArrayList<>();
			Map<Integer, List<Holiday>> holidaysMap = this.getHolidayMap();
			boolean showDue = false;
			if(null != params.get("isShowDue") && "true".equals(String.valueOf(params.get("isShowDue")))){
				showDue = true;
			}
			for (PEAPDto peapDto : planFooterList) {
				Map<String, Object> newMap = new HashMap<>();
				if(null == peapDto || null == peapDto.getDueDate()){
					newMap.put("field", "NULL");
					newMap.put("title", "到期日为空");
				}else{
					if("day".equals(type)){
						newMap.put("field", DateUtil.format1(peapDto.getDueDate()));
						if(showDue){
							newMap.put("title", DateUtil.format1(peapDto.getDueDate()));
						}else{
							Holiday holiday = this.getHoliday(holidaysMap,peapDto.getDueDate());
							if(null != holiday && StringUtils.isNotBlank(holiday.getName())){
								newMap.put("title", DateUtil.format1(holiday.getLastWorkDate()).concat("(").concat(holiday.getName()).concat(")"));
							}else{
								newMap.put("title", DateUtil.format1(peapDto.getDueDate()));
							}
						}
						
					}else if("week".equals(type)){
						newMap.put("field", peapDto.getDueWeekDate());
						newMap.put("title", peapDto.getDueWeekDate());
					}else if("month".equals(type)){
						newMap.put("field", peapDto.getDueMonthDate());
						newMap.put("title", peapDto.getDueMonthDate());
					}
				}
				resultList.add(newMap);
			}
			return resultList;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private Holiday getHoliday(Map<Integer, List<Holiday>> holidaysMap,
			Date dueDate) {
		if(null == dueDate){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dueDate);
		int year = cal.get(Calendar.YEAR);
		List<Holiday> holidays = holidaysMap.get(year);
		if(!CollectionUtils.isEmpty(holidays)){
			// 法定节假日判断
			for (Holiday holiday : holidays) {
				Calendar startCal = Calendar.getInstance();
				Calendar endCal = Calendar.getInstance();
				startCal.setTime(holiday.getStartDate());
				endCal.setTime(holiday.getEndDate());
				if((cal.after(startCal) && cal.before(endCal)) || cal.compareTo(startCal)==0 || cal.compareTo(endCal)==0){
					holiday.setName(holiday.getName().concat("提前"));
					return holiday;
				}
			}
		}
		// 双休判断
		int weekDay  = cal.get(Calendar.DAY_OF_WEEK);
		Holiday Holiday = new Holiday();
		if(weekDay == 0){// 周日
			Holiday.setName("周日提前");
			Holiday.setWeekend(true);
			cal.add(Calendar.DAY_OF_YEAR, -2);
			Holiday.setLastWorkDate(cal.getTime());
		}else if(weekDay == 6){// 周六
			Holiday.setName("周六提前");
			Holiday.setWeekend(true);
			cal.add(Calendar.DAY_OF_YEAR, -1);
			Holiday.setLastWorkDate(cal.getTime());
		}else{
			Holiday.setWeekend(false);
			Holiday.setLastWorkDate(cal.getTime());
		}
		return Holiday;
	}

	private Map<Integer, List<Holiday>> getHolidayMap() throws ServiceException {
		Map<Integer, List<Holiday>> holidayMap = HolidayUtil.getHolidayMap();
		holidayMap = new HashMap<Integer, List<Holiday>>();
		List<Holiday> lstHoliday = holidayService.findByBiz(null, null);
		for (Holiday holiday : lstHoliday) {
			int year = holiday.getYear();
			if(null == holidayMap.get(year)){
				List<Holiday> newList = new ArrayList<>();
				newList.add(holiday);
				holidayMap.put(new Integer(year), newList);
			}else{
				List<Holiday> oldList = holidayMap.get(year);
				oldList.add(holiday);
			}
		}
		return holidayMap;
	}

	@Override
	public int findReportSendCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return peReportMapper.findReportSendCount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<PEAPDto> findReportSendByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			List<PEAPDto> pageCondition = peReportMapper.findSendPageCondition(page,params);
			params.put("listItem", pageCondition);
			List<PEAPDto> sendList = peReportMapper.findSendListByPage(page,sortColumn,sortOrder,params);
			params.put("listItem", "");
			return sendList;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<PEAPDto> findReportSendFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return peReportMapper.findReportSendFooter(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}