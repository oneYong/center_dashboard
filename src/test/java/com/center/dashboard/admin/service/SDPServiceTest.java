package com.center.dashboard.admin.service;

import com.center.dashboard.util.CmmDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WYKIM on 2017-06-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Rollback(value=true)
public class SDPServiceTest {

    @Autowired
    private SDPService sdpService;

    @Test
    public void test_getTotalUser()throws Exception{
        String totalUser = sdpService.getTotalUser(CmmDate.getYesterdayGMTDate());
        System.out.println(totalUser);
    }

    @Test
    public void test_getTotalCost()throws Exception{
        String totalCost = sdpService.getTotalCost();
        System.out.println(totalCost);
    }

    @Test
    public void test_getRealCost()throws Exception{
        String totalCost = sdpService.getRealCost();
        System.out.println(totalCost);
    }

    @Test
    public void test_getDeviceCostByOne()throws Exception{
        String deviceCostByOne = sdpService.getDeviceCostByOne();
        System.out.println(deviceCostByOne);
    }
}
