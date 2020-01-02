package com.lottery.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 日期工具
 */
public class DateUtil {

	private static String defaultDatePattern = "yyyy-MM-dd";
	public static final String FULL_DATE_PATTERN="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_MILLS_PATTERN="yyyy-MM-dd HH:mm:ss:SSS";
	public static final Long ONE_SECOND = 1000l;
	public static final Long ONE_MIN = ONE_SECOND*60;
	public static final Long ONE_HOUR = ONE_MIN*60;
	public static final Long ONE_DAY = ONE_HOUR*24;

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return defaultDatePattern;
	}

	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parse(String strDate) throws ParseException {
		return StringUtils.isBlank(strDate) ? null : parse(strDate,
				getDatePattern());
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 */
	public static String format(Date date) {
		return date == null ? " " : format(date, getDatePattern());
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(Date date, String pattern) {
		return date == null ? " " : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(String strDate, String pattern)
			throws ParseException {
		return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(
				pattern).parse(strDate);
	}


	/**
	 * 获取月天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getLastDay(int year, int month) {
		int day = 1;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		int last = cal.getActualMaximum(Calendar.DATE);
		return last;
	}
	
	/**
	 * 获取星期几  日：1  ，1：2， --》6：7
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static int getWeekOFDay(int year,int month,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
//		int k1 = cal.get(Calendar.DAY_OF_MONTH);  
		cal.set(year, month , day);
		int col = cal.get(Calendar.DAY_OF_WEEK);
		return col;
	}
	
	/**
	 * 给指定时间增加天数
	 * @param target  目标日期
	 * @param day	增加天数
	 * @return 
	 */
	public static Date addDay(Date target,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTime();
	}
	
	/**
	 * 给指定时间减掉天数
	 * @param target  目标日期
	 * @param day	增加天数
	 * @return 
	 */
	public static Date minusDay(Date target,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)-day);
		return cal.getTime();
	}
	
	
	/**
	 * 给指定时间增加分钟
	 * @param target  目标日期
	 * @param day	增加天数
	 * @return 
	 */
	public static Date addMi(Date target,int mi){
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.add(Calendar.MINUTE, mi);
		return cal.getTime();
	}
	
	
	/**
	 * 给指定时间减掉分钟
	 * @param target  目标日期
	 * @param day	增加天数
	 * @return 
	 */
	public static Date minusMi(Date target,int mi){
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)-mi);
		return cal.getTime();
	}
	
	
	/**
	 * 给指定时间增加小时
	 * @param target  目标日期
	 * @param day	增加天数
	 * @return 
	 */
	public static Date addHH(Date target,int hh){
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.add(Calendar.HOUR_OF_DAY, hh);
		return cal.getTime();
	}
	
	/**
	 * 给指定时间减掉小时
	 * @param target  目标日期
	 * @param day	增加天数
	 * @return 
	 */
	public static Date minusHH(Date target,int hh){
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)-hh);
		return cal.getTime();
	}
	
	
	/**
	 * 给指定时间增加秒
	 * @param target  目标日期
	 * @param day	增加天数
	 * @return 
	 */
	public static Date addSS(Date target,int ss){
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.add(Calendar.SECOND, ss);
		return cal.getTime();
	}

	/**
	 * 给指定时间减掉秒
	 * @param target  目标日期
	 * @param day	增加天数
	 * @return 
	 */
	public static Date minusSS(Date target,int ss){
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.set(Calendar.SECOND, cal.get(Calendar.SECOND)-ss);
		return cal.getTime();
	}
	
	
	/**
	 * 当前时间
	 * @return
	 */
	public static Date now(){
		return new Date();
	}
	/**
	 * 当前时间字符
	 * @return
	 */
	public static String nowStr(){
		return format(now(), FULL_DATE_PATTERN);
	}
	/**
	 * 当前时间字符
	 * @param pattern
	 * @return
	 */
	public static String nowStr(String pattern){
		return format(now(), pattern);
	}
	
	
	/**
	 * 计算两时间字符相差的天数
	 * @param start 开始天数
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static String difDays(Long startLongTime,Long endLongTime){
		//计算差值
		Long difLongTime = startLongTime-endLongTime;
		long between=difLongTime/1000;//除以1000是为了转换成秒
		long day=between/(24*3600);
		long hour=between%(24*3600)/3600;
		long minute=between%3600/60;
		long second=between%60/60;
		return day+"天"+hour+"小时"+minute+"分"+second+"秒";
	}
	
	/**
	 * 计算两时间相差天数
	 * @param start
	 * @param end
	 * @return
	 */
	public static Double difDayOfDate(Date start,Date end){
		Long difTime = end.getTime()-start.getTime();
		Double dif = (difTime/1000d)/(24*3600);
		return dif;
	}
	
	
	/**
	 * 时间比较方法
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compare_date(String DATE1, String DATE2) {
		if(DATE1.length()<14){
			DATE1 +=  " 00:00:00";
		}
		if(DATE2.length()<14){
			DATE2 +=  " 00:00:00";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	
	
	public static void main(String[] args) {
		
		try {
			
			Date start = DateUtil.parse("2016-04-01 10:10:01", DateUtil.FULL_DATE_PATTERN);
			Date end = DateUtil.parse("2016-04-20 00:20:50", DateUtil.FULL_DATE_PATTERN);
			
			
			System.out.println((end.getTime()/1000)-(start.getTime()/1000));
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
