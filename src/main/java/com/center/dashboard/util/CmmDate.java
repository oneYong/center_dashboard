package com.center.dashboard.util;

import java.text.SimpleDateFormat;
import java.util.*;

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

    public static String getAWeeksAgoGMTDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(cal.getTime());
    }

    public static String getBeforeYearMonth(int month){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-month);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(cal.getTime());
    }

    public static String getBeforeYearMonth(String month, int gap)throws Exception{
        Date date = new SimpleDateFormat("yyyyMMdd").parse(month+"02");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,-gap);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(cal.getTime());
    }

    private static String getLastDay(int year,int month){
        // JAN 0, FEB 1 ... DEC 11
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,1);

        int lastday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String strYear = String.valueOf(year);
        String strMonth = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
        String strDay = String.valueOf(lastday);

        return strYear + "-" + strMonth + "-" + strDay;
    }

    public static String getLastDayOneMonth(int gap){
        String day = getBeforeYearMonth(gap);
        int year = Integer.parseInt(day.substring(0,4));
        int month = Integer.parseInt(day.substring(4,6));

        return getLastDay(year,month);
    }

    public static List<String> getLastDayList(){
        List<String> lastDayList = new ArrayList<>();

        String yesterday = getYesterdayGMTDate();

        String lastDay = yesterday.substring(0,4) + "-" + yesterday.substring(4,6) + "-" + yesterday.substring(6);
        lastDayList.add(lastDay);

        int year = Integer.parseInt(yesterday.substring(0,4));
        int month = Integer.parseInt(yesterday.substring(4,6));
        for(int i = month-1; i > 0; i--){
            String temp = getLastDay(year,i);
            lastDayList.add(temp);
        }

        return lastDayList;

    }

    public static List<String> getLastDayList(String startMonth, String endMonth)throws Exception{
        List<String> lastDayList = new ArrayList<>();

        if(startMonth.equals(endMonth)){
            int year = Integer.parseInt(startMonth.substring(0,4));
            int month = Integer.parseInt(startMonth.substring(4,6));
            lastDayList.add(getLastDay(year,month));

            return lastDayList;
        }


        while(true){
            int year = Integer.parseInt(endMonth.substring(0,4));
            int month = Integer.parseInt(endMonth.substring(4,6));
            lastDayList.add(getLastDay(year,month));

            if(startMonth.equals(endMonth))break;

            endMonth = getBeforeYearMonth(endMonth,1).substring(0,6);
        }

        return lastDayList;
    }

    public static String getStartEndDay(String day){
        String startDay = day.substring(0,4) + ".01.01";
        String endDay = day;

        return startDay + " ~ " + endDay;
    }




}
