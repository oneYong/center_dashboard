package com.center.dashboard.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.java2d.cmm.kcms.CMM;

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
}
