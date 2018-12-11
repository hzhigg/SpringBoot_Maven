package com.demo.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

/**
 * 当前类被	BaseService类继承
 * 控制器和service层有用到时间格式化的直接调用getdatae()方法即可
 * @author hzhigg
 *
 */
public class DateUtil extends BaseUtils{
	
	/** 系统默认时间 */
	public static final String DEFAULT_TIME = "1900-01-01 00:00:00.000";
	/** 年-月-日 */
	public static final String D_DEFAULT = "yyyy-MM-dd";
	/** 年-月-日 时:分:秒 */
	public static final String D_FULL = "yyyy-MM-dd HH:mm:ss";
	/** 年月日 */
	public static final String D_DEFAULT_STR = "yyyyMMdd";
	/** 月日年 */
	public static final String D_DATE_STR = "MMddyyyy";
	/** 时:分:秒 */
	public static final String T_HHMMSS = "HH:mm:ss";
	/** 时:分 */
	public static final String T_HHMM = "HH:mm";
	/** 月/日/年 */
	public static final String MM_DD_YYYY = "MM/dd/yyyy";

	/**
	 * java8 线程安全格式化时间
	 * @return 默认时间格式返回 yyyy-MM-dd
	 */
	//public static final DateTimeFormatter FM_D_FULL=DateTimeFormatter.ofPattern(D_FULL);
	/**
	 * 返回yyyy-MM-dd HH:mm:ss 时间格式
	 */
	public static final ThreadLocal<SimpleDateFormat> FM_D_FULL = new ThreadLocal<SimpleDateFormat>(){

		@Override
		protected SimpleDateFormat initialValue() {
			// TODO Auto-generated method stub
			 return new SimpleDateFormat(D_FULL);
		}
		
	};
	
	/**
	 * java8 线程安全格式化时间
	 * @return 默认时间格式返回 yyyy-MM-dd
	 */
	//public static final DateTimeFormatter DEFAULT_SDF=DateTimeFormatter.ofPattern(D_DEFAULT);
	/**
	 * 默认时间格式返回 yyyy-MM-dd
	 */
	public static final ThreadLocal<SimpleDateFormat> DEFAULT_SDF=new ThreadLocal<SimpleDateFormat>(){

		@Override
		protected SimpleDateFormat initialValue() {
			// TODO Auto-generated method stub
			 return new SimpleDateFormat(D_DEFAULT);
		}
		
		
	};
	
	
	static String format()
	{
		return "2016-06-20 15:30:00";
	}

	public static Long dateDiff(String startTime, String endTime,  String format, String str) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		// 获得两个时间的毫秒时间差异
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh + day * 24;// 计算差多少小时
			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
			System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"
					+ (min - day * 24 * 60) + "分钟" + sec + "秒。");
			System.out.println("hour=" + hour + ",min=" + min);
			if (str.equalsIgnoreCase("h")) {
				return hour;
			} else {
				return min;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str.equalsIgnoreCase("h")) {
			return hour;
		} else {
			return min;
		}
	}
    // 判断日期格式:yyyy-mm-dd
    public static boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }

	/**
	 * 将指定的时间格式转为Date对象
	 * @param stringDate
	 * @param pattern
	 * @return
	 */
	public static Date getDate(String stringDate,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		return date;
	}
	
	/**
	 * 将指定的时间格式转为Date对象
	 * @param stringDate
	 * @return
	 */
	public static Date getDate(String stringDate){
		if(StringUtils.isBlank(stringDate)){
			return null;
		}
		Date date = null;
		try {
			date = DEFAULT_SDF.get().parse(stringDate);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		return date;
	}
	
	/**
	 * 根据毫秒数转换成年月日
	 * @param m
	 * @return
	 */
	public static String getDateMillis(String m){
		if(StringUtils.isBlank(m)){
			return "";
		}
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(Long.valueOf(m));

		int year = calendar2.get(Calendar.YEAR);
		int month = calendar2.get(Calendar.MONTH);
		int day = calendar2.get(Calendar.DAY_OF_MONTH);

		return year+"-"+(month + 1)+"-"+day;
	}
	
	/**
	 * 按年计算返回日期
	 * @param date 源时间
	 * @param num +1加一年 -1减一年
	 * @return
	 */
	public String getYearCalculation(Date date,int num){
		if(ObjectUtils.isEmpty(date)){
			return null;
		}
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.YEAR, num);// 日期加1年
		return DEFAULT_SDF.get().format(rightNow.getTime());
	}
	
	
	/**
	 * 按月计算返回日期
	 * @param date 源时间
	 * @param num 
	 * @return
	 */
	public String getMonthCalculation(Date date,int num){
		if(ObjectUtils.isEmpty(date)){
			return null;
		}
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, num);// 日期加3个月
		return DEFAULT_SDF.get().format(rightNow.getTime());
	}
	
	/**
	 * 
	 * @title: getCurrentTime
	 * @description: 获取当前时间（年-月-日 时:分:秒）
	 *
	 * @return String
	 * @date 2017年12月7日 下午3:29:29
	 */
	public static String getCurrentTime () {
		return DEFAULT_SDF.get().format(new Date());
	}
	
	/**
	 * 
	 * @title: getDateTime
	 * @description: 获取某天的时间, 返回日期格式：（年-月-日）
	 * 
	 * @param num 整数 (负数 表示前几天；零 表示当天；正数 表示后几天)，如：-1
	 * @return String
	 * @date 2017年12月7日 下午3:29:29
	 */
	public final static String getDateTime ( int num ) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, num); // 得到前/后几天的时间
		long datetime = cal.getTimeInMillis();
		/*synchronized (DEFALUT_SDF.get()) {
			return DEFALUT_SDF.get().format(new Date(datetime));
		}*/
		return DEFAULT_SDF.get().format(new Date(datetime));
	}

	/**
	 * 
	 * @title: getDateTime
	 * @description: 获取默认当前时间
	 *
	 * @return String
	 * @date 2018年1月8日 上午9:35:31
	 */
	public static String getDateTime () {
		//SimpleDateFormat format = new SimpleDateFormat(D_DEFAULT);
		/*synchronized (format) {
			return format.format(new Date());
		}*/
		return DEFAULT_SDF.get().format(new Date());
	}
	
	/**
	 * 
	 * @title: getDateTime
	 * @description: 按指定格式，获取当前时间
	 *
	 * @param pattern 日期格式
	 * @return String
	 * @date 2018年1月8日 上午9:35:31
	 */
	public static String getDateTime ( String pattern ) {
		if (StringUtils.isBlank(pattern)) {
			return getDateTime();
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		synchronized (format) {
			return format.format(new Date());
		}
	}
	
	/**
	 * 
	 * @title: parse
	 * @description: 将日期字符串转换为Date对象
	 * 
	 * @param dateTime 日期字符串
	 * @return Date
	 * @throws ParseException
	 * @date 2017年12月7日 下午3:29:29
	 */
	public static Date parse ( String dateTime ) {
		try {
			return DEFAULT_SDF.get().parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 
	 * @title: parse
	 * @description: 将日期字符串转换为Date对象
	 * 
	 * @param dateTime 日期字符串
	 * @param patten 指定格式
	 * @return Date
	 * @throws ParseException
	 * @date 2017年12月7日 下午3:29:29
	 */
	public static Date parse ( String dateTime, SimpleDateFormat patten ) {
		try {
			return patten.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	/**
	 * 
	 * @title: daysBetween
	 * @description: 计算两个日期之间相差的天数
	 *
	 * @param smdate 开始时间
	 * @param bdate 结束时间
	 * @return int
	 * @throws ParseException
	 * @date 2017年12月25日 上午10:03:37
	 */
	public static int daysBetween ( Date smdate, Date bdate ) {
		try {
			SimpleDateFormat sdf =DEFAULT_SDF.get();
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 
	 * @title: daysBetween
	 * @description: 计算两个日期字符串相差的天数
	 *
	 * @param smdate 开始时间
	 * @param bdate 结束时间
	 * @return int
	 * @throws ParseException
	 * @date 2017年12月25日 上午10:01:45
	 */
	public static int daysBetween ( String smdate, String bdate ) throws ParseException {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(DEFAULT_SDF.get().parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(DEFAULT_SDF.get().parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static void main ( String[] args ) throws ParseException {
		System.out.println(DateUtil.getCurrentTime());
		System.out.println(DateUtil.getDateTime(30));
		System.out.println(DateUtil.getDateTime(D_FULL));
		System.out.println(DateUtil.parse(DateUtil.getDateTime(23)));
		System.out.println(DateUtil.getDateTime("MM/dd/yyyy"));
	}
}
