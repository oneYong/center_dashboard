package com.center.dashboard.admin.service;

import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.ERegion;
import com.center.dashboard.vo.TotalUserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by WYKIM on 2017-06-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Rollback(value=true)
public class MakeChartServiceTest {

    @Autowired
    private MakeChartService makeChartService;

    @Autowired
    private DashBoardMapper dashBoardMapper;

    @Test
    public void test_getDate() throws Exception{
        //System.out.println(makeChartService.getDate("20170611"));
    }


    @Test
    public void test_run() throws Exception{
       // String labels = makeChartService.makeChartLabelsAWS(CmmDate.getLastDayList("201702","201707"));
       // System.out.println(labels);
    }
}
