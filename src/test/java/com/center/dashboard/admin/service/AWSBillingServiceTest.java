package com.center.dashboard.admin.service;

import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.CmmUtils;
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
public class AWSBillingServiceTest {

    @Autowired
    private AWSBillingService awsBillingService;

    @Test
    public void test_getTotalCost() throws Exception {
        System.out.println(awsBillingService.getTotalCost());
    }

    @Test
    public void test_getBeforeMonthTotalCost() throws Exception {
        System.out.println(awsBillingService.getBeforeMonthTotalCost(2));
    }

    @Test
    public void test_getBeforeMonth() throws Exception {
        System.out.println(awsBillingService.getBeforeMonth(7));
    }

    @Test
    public void test() throws Exception {
        System.out.println(awsBillingService.getTotalCostList("201702","201707"));

    }

    @Test
    public void test_productServiceList() throws Exception {
        System.out.println(CmmUtils.getListToJson(awsBillingService.getProductList("2017-07-31")));
        System.out.println(CmmUtils.getListToJson(awsBillingService.getServiceList("2017-07-31")));
    }
}