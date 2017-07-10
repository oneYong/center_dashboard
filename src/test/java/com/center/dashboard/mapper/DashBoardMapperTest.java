package com.center.dashboard.mapper;

import com.center.dashboard.util.CmmDate;
import com.center.dashboard.vo.ChartDataVO;
import com.center.dashboard.vo.TotalDataVO;
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
public class DashBoardMapperTest {

    @Autowired
    private DashBoardMapper dashBoardMapper;

    @Test
    public void test_getTotalCntByDate() throws Exception{
        String yesterday = CmmDate.getYesterdayGMTDate();
        String bYesterday = CmmDate.getBeforeYesterdayGMTDate();

        int yesterdayCnt = dashBoardMapper.getTotalCntByDate(yesterday).getTotalCnt();
        int bYesterdayCnt = dashBoardMapper.getTotalCntByDate(bYesterday).getTotalCnt();

        System.out.println("yesterday is " +yesterday+" , before yesterday is "+bYesterday);
        System.out.println("yesterday cnt is " +yesterdayCnt+" , before yesterday cnt is "+bYesterdayCnt);
    }

    @Test
    public void test_getDefaultTotalCnt_KIC() throws Exception{
        List<ChartDataVO> chartDataVOList = dashBoardMapper.getDefaultTotalCnt_KIC();
        System.out.println(chartDataVOList);
    }

}
