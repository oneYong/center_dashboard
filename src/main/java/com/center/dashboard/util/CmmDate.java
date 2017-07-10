package com.center.dashboard.util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by WYKIM on 2017-06-29.
 */
public class CmmDate {

    public static String getTodayGMTDate(){
        Calendar cal = Calendar.getInstance();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(cal.getTime());
    }

    public static String getYesterdayGMTDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(cal.getTime());
    }

    public static String getBeforeYesterdayGMTDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(cal.getTime());
    }
}
