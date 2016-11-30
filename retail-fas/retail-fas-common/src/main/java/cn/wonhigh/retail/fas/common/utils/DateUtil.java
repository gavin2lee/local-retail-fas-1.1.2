package cn.wonhigh.retail.fas.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 
 * 日期时间工具类
 * 
 * @author xian.yq
 * @date 2013-8-7 上午10:26:41
 * @version 0.1.0 
 * @copyright yougou.com
 */
public class DateUtil {
	
    public static String formatDate(java.util.Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    public static String formatDateByFormat(java.util.Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static java.util.Date parseDate(java.sql.Date date) {
        return date;
    }

    public static java.sql.Date parseSqlDate(java.util.Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        } else {
            return null;
        }
    }

    public static String format(java.util.Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                java.text.DateFormat df = new java.text.SimpleDateFormat(format);
                result = df.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String format(java.util.Date date) {
    	if(null!=date){
    		return format(date, "yyyy/MM/dd");
    	}
       return null;
    }

    public static String format1(java.util.Date date) {
    	if(null!=date){
    		 return format(date, "yyyy-MM-dd");
    	}
        return null;
    }

    public static int getYear(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.YEAR);
    }

    public static int getMonth(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.MONTH) + 1;
    }

    public static int getDay(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.DAY_OF_MONTH);
    }

    public static int getHour(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.MINUTE);
    }

    public static int getSecond(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.SECOND);
    }

    public static long getMillis(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static int getWeek(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    public static String getDate(java.util.Date date) {
        return format(date, "yyyy/MM/dd");
    }

    public static String getDate(java.util.Date date,String formatStr) {
        return format(date, formatStr);
    }


    public static String getTime(java.util.Date date) {
        return format(date, "HH:mm:ss");
    }

    public static String getDateTime(java.util.Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 日期月份相加减
     * @param date 具体的时间
     * @param month 相加减的月份
     * @return 处理后的时间
     */
    public static Date addMonth(Date date, int month) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, month);
    	return calendar.getTime();
    }
    
    /**
     * 日期相加
     * @param date Date
     * @param day int
     * @return Date
     */
    public static java.util.Date addDate(java.util.Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }
    
    /**
     * 日期相减
     * @param date Date
     * @param date1 Date
     * @return int
     */
    public static int diffDate(java.util.Date date, java.util.Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }
    
    /**
     * 日期相减(返回秒值)
     * @param date Date
     * @param date1 Date
     * @return int
     * @author 
     */
    public static Long diffDateTime(java.util.Date date, java.util.Date date1) {
        return (Long) ((getMillis(date) - getMillis(date1))/1000);
    }

    public static java.util.Date getdate(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    public static java.util.Date getdate1(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date);
    }
    
    public static java.util.Date getMaxTimeByStringDate(String date) throws Exception {
    	String maxTime = date + " 23:59:59";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.parse(maxTime);
    }
    /**
     * 获得当前时间
     * @return
     * @throws ServiceException 
     */
    public static Date getCurrentDateTime() throws Exception
    {
    	Date date=new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    	
        String result = DateUtil.getDateTime(date);
		return sdf.parse(result);
    }
    
    public static String getCurrentDateTimeToStr() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(getCurrentDateTime());
	}
    public static String getCurrentDateTimeToStr2() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(getCurrentDateTime());
	}

    public static Long getWmsupdateDateTime() {
		Calendar cl=Calendar.getInstance();
		
		return cl.getTimeInMillis();
	}
    
    public static Integer getLeftSeconds(String date)throws Exception{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date condition = sdf.parse(date);
    	long n = condition.getTime();
    	long s = sdf.parse(getCurrentDateTimeToStr2()).getTime();
//    	System.out.println("开始时间:"+date+"-->"+(int)((s-n)/1000));
    	return (int)((s-n)/1000);
    }
    
    /**
     * 获得时间戳
     * @return
     * @throws Exception
     */
    public static String getTime(){
    	Date date=new Date();
    	return String.valueOf(date.getTime());
    }
    
	/** 判断日期格式是否正确 */	
	public static boolean validateDate(String dateString){
		//使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
		Pattern p = Pattern.compile("\\d{4}+[-]\\d{2}+[-]\\d{2}+");
		Matcher m = p.matcher(dateString);
		if(!m.matches()){	
			return false;
			} 
 		//得到年月日
		String[] array = dateString.split("-");
		int year = Integer.valueOf(array[0]);
		int month = Integer.valueOf(array[1]);
		int day = Integer.valueOf(array[2]);
		
		if(month<1 || month>12){	
			return false;
			}
		int[] monthLengths = new int[]{0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(isLeapYear(year)){
			monthLengths[2] = 29;
		}else{
			monthLengths[2] = 28;
		}
		int monthLength = monthLengths[month];
		if(day<1 || day>monthLength){
			return false;	
		}
		return true;
	}
    
	/** 是否是闰年 */
	private static boolean isLeapYear(int year){
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ;
	}
	
	/**
	 * 将时间格式的字符串，转换为时间
	 * 
	 * @param str 时间格式的字符串
	 * @param pattern 转换格式
	 * @return java.util.Date
	 */
	public static Date parseToDate(String str, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * 将时间格式的字符串，转换为时间
	 * 
	 * @param str 时间格式的字符串
	 * @param pattern 转换格式
	 * @return java.util.Date
	 */
	public static Date parseToDateWithThrowException(String str, String pattern) throws ParseException{
		DateFormat df = new SimpleDateFormat(pattern);
		return df.parse(str);
	}	
	
	public static Date getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
       return cal.getTime();  
    }   
	
	public static Date getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.YEAR, year);  
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);  
       return cal.getTime();  
    }   

	public static String getLastDayOfMonthStr(int year, int month) {     
		Calendar cal = Calendar.getInstance();   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);     
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.add(Calendar.DAY_OF_MONTH,-1); 
        return sdf.format(cal.getTime());
    }  
	
	public static String getFirstDayOfMonthStr(int year, int month) {     
        Calendar cal = Calendar.getInstance();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.YEAR, year);  
        cal.set(Calendar.MONTH, month-1);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE)); 
        return sdf.format(cal.getTime());
    } 
	
	public static String addDate(String date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(parseToDate(date, "yyyy-MM-dd"));
        c.add(Calendar.DATE, day);
        return format1(c.getTime());
    }

	/** 
     * 返回当月最后一天的日期 
     */  
    public static Date getLastDayOfMonth(Date date) {  
        Calendar calendar = convert(date);  
        calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));  
        return calendar.getTime();  
    }  
  
    /** 
     * 返回当月最后一天的日期 
     */  
    public static Date getFirstDayOfMonth(Date date) {  
        Calendar calendar = convert(date);  
        calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));  
        return calendar.getTime();  
    }  
  
    /** 
     * 将日期转换为日历 
     * @param date 日期 
     * @return 日历 
     */  
    private static Calendar convert(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar;  
    }  
    
	public static void main(String[] args) {
		try {
//			System.out.println(addDate("2015-02-28", 1));
		   	 
//			Calendar cal=Calendar.getInstance();
//			cal.set(Calendar.YEAR, 2015);     
//	        cal.set(Calendar.MONTH, 6);     
//	        System.out.println(cal.getTime().toString());
//	        
//	        cal.add(Calendar.MONTH, -1);   
//		    cal.getActualMaximum(Calendar.DAY_OF_MONTH);//2月的最后一天
//		    System.out.println(cal.getTime().toString());
		    
	    	System.out.println(getLastDayOfMonth(2015,7).toString());
	    	System.out.println(getFirstDayOfMonth(2015,7).toString());
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
}
