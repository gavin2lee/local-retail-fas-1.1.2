package cn.wonhigh.retail.fas.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.model.Holiday;


public class HolidayUtil {
	
	private static Map<Integer, List<Holiday>> holidaysMap = new HashMap<>();
	
	public static Map<Integer, List<Holiday>> getHolidayMap(){
		return holidaysMap;
	}
	
	public static Holiday getHoliday(Date date){
		if(null == date){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		List<Holiday> holidays = holidaysMap.get(year);
		if(!CollectionUtils.isEmpty(holidays)){
			// 法定节假日判断
			for (Holiday holiday : holidays) {
				Calendar startCal = Calendar.getInstance();
				Calendar endCal = Calendar.getInstance();
				startCal.setTime(holiday.getStartDate());
				endCal.setTime(holiday.getEndDate());
				if(cal.after(startCal) && cal.before(endCal)){
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
	
}
