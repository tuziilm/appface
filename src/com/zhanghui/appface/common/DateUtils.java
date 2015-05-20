package com.zhanghui.appface.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public final static String DEFAULT_PATTERN="yyyy-MM-dd HH:mm:ss";
    public final static String SIMPLE_DATE_PATTERN="yyyy/MM/dd";
	public final static String nowDateString(String pattern){
		return DateFormatUtils.format(new Date(), pattern);
	}

    public final static String dateStringAboutNow(String pattern, int dateInterval){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, dateInterval);
        return DateFormatUtils.format(cal.getTime(), pattern);
    }

	public final static String yesterdayString(String pattern){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return DateFormatUtils.format(cal.getTime(), pattern);
	}

    public final static Date parse(String date, Date defDate){
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date, DEFAULT_PATTERN);
        } catch (ParseException e) {
            return defDate;
        }
    }

    public final static Date parse(String date) throws IllegalArgumentException{
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date, DEFAULT_PATTERN);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取输入字串表示的时间的前一天的结束时间和第二天的开始时间
     * @param time
     * @return
     * @throws IllegalArgumentException
     */
    public final static Tuple<Date, Date> dateInterval(String time) throws  ParseException{
        Date thisTime = org.apache.commons.lang3.time.DateUtils.parseDate(time, SIMPLE_DATE_PATTERN);
        Calendar cal = Calendar.getInstance();
        cal.setTime(thisTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MILLISECOND,-1);
        Date beginTime=cal.getTime();
        cal.add(Calendar.MILLISECOND, 1000 * 60 * 60 * 24  +1);
        Date endTime=cal.getTime();
        return Tuple.valueOf(beginTime, endTime);
    }
}
