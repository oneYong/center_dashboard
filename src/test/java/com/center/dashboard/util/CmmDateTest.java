package com.center.dashboard.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.java2d.cmm.kcms.CMM;

import java.util.Calendar;

/**
 * Created by WYKIM on 2017-06-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CmmDateTest {


    @Test
    public void Test() throws Exception{
        System.out.println(CmmDate.getAWeeksAgoGMTDate());
        System.out.println(CmmDate.getTodayGMTDate());


    }

    @Test
    public void CalendarTest() throws Exception{

        System.out.println(CmmDate.getLastDayList());
        System.out.println(CmmDate.getLastDayOneMonth(1));
        System.out.println(CmmDate.getLastDayOneMonth(2));
        System.out.println(CmmDate.getStartEndDay(CmmDate.getLastDayList().get(0)));

        System.out.println(CmmDate.getBeforeYearMonth("201707",1));
        System.out.println(CmmDate.getLastDayList("201706","201705"));

    }
}
