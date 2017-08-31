package com.center.dashboard.mapper;

import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.CmmUtils;
import com.center.dashboard.util.EServiceCode;
import com.center.dashboard.util.EServiceName;
import com.center.dashboard.vo.BillingDataVO;
import com.center.dashboard.vo.FaultDataVO;
import com.center.dashboard.vo.ParamVO;
import com.center.dashboard.vo.TotalUserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
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
    public void test_getCrtDate_KIC() throws Exception{
    }

    @Test
    public void test_getCntryCode_KIC() throws Exception{
        List<String> cntryCodeList = dashBoardMapper.getCntryCode_KIC(CmmDate.getAWeeksAgoGMTDate(),CmmDate.getTodayGMTDate());
        System.out.println(cntryCodeList);
    }
    @Test
    public void test_getTotalUser_KIC() throws Exception{
        List<TotalUserVO> totalUserList = dashBoardMapper.getTotalUser_KIC(CmmDate.getAWeeksAgoGMTDate(),CmmDate.getTodayGMTDate());
        System.out.println(totalUserList);
    }

    @Test
    public void test_getCrtDate_AIC() throws Exception{

    }

    @Test
    public void test_getCntryCode_AIC() throws Exception{
        List<String> cntryCodeList = dashBoardMapper.getCntryCode_AIC(CmmDate.getAWeeksAgoGMTDate(),CmmDate.getTodayGMTDate());
        System.out.println(cntryCodeList);
    }
    @Test
    public void test_getTotalUser_AIC() throws Exception{
        List<TotalUserVO> totalUserList = dashBoardMapper.getTotalUser_AIC(CmmDate.getAWeeksAgoGMTDate(),CmmDate.getTodayGMTDate());
        System.out.println(totalUserList);
    }

    @Test
    public void test_getServiceList() throws Exception{
        List<TotalUserVO> serviceList_kic = dashBoardMapper.getServiceList_KIC(CmmDate.getYesterdayGMTDate());
        List<TotalUserVO> serviceList_aic = dashBoardMapper.getServiceList_AIC(CmmDate.getYesterdayGMTDate());
        List<TotalUserVO> serviceList_eic = dashBoardMapper.getServiceList_EIC(CmmDate.getYesterdayGMTDate());
        List<TotalUserVO> serviceList_ruc = dashBoardMapper.getServiceList_RUC(CmmDate.getYesterdayGMTDate());


        System.out.println(serviceList_kic);
        System.out.println(serviceList_aic);
        System.out.println(serviceList_eic);
        System.out.println(serviceList_ruc);
    }

    @Test
    public void test_getAWSBillingTotalCost_CNS() throws Exception{

        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCost_CNS(CmmDate.getLastDayList());
        System.out.println(billingDataVOList);
    }

    @Test
    public void test_getServiceProductList_CNS() throws Exception{

        List<BillingDataVO> productListCns = dashBoardMapper.getAWSProductList_CNS("2017-07-31");
        List<BillingDataVO> serviceListCns = dashBoardMapper.getAWSServiceList_CNS("2017-07-31");

        for(int i = 0; i < productListCns.size(); i++){
            BillingDataVO billingDataVO = productListCns.get(i);
            billingDataVO.setTotalCostStr(CmmUtils.doubleToMoneyString(billingDataVO.getTotalCost()));
        }

        for(int i = 0; i < serviceListCns.size(); i++){
            BillingDataVO billingDataVO = serviceListCns.get(i);
            billingDataVO.setTotalCostStr(CmmUtils.doubleToMoneyString(billingDataVO.getTotalCost()));
        }
        System.out.println(productListCns);
        System.out.println(serviceListCns);
    }

    @Test
    public void test_getAWSMonthlyProductCost_CNS() throws Exception{
        List<BillingDataVO> list = dashBoardMapper.getAWSMonthlyProductCost_CNS("201707","201708");
        for(int i = 0; i < list.size(); i++){
            BillingDataVO billingDataVO = list.get(i);
            billingDataVO.setTotalCostStr(CmmUtils.doubleToMoneyString(billingDataVO.getTotalCost()));
            billingDataVO.setBeforeCostExtraStr(CmmUtils.doubleToMoneyString(billingDataVO.getBeforeCostExtra()));
        }

        System.out.println(list);
    }

    @Test
    public void test_getFault1() throws Exception{
        FaultDataVO faultDataVO = new FaultDataVO();
        faultDataVO.setMonthInfo("2017-08");
        List<FaultDataVO> list1 = dashBoardMapper.getFaultCountByService(faultDataVO);
        List<FaultDataVO> list2 = dashBoardMapper.getFaultCountByRegion(faultDataVO);
        System.out.println(list1);
        System.out.println(list2);
    }

    @Test
    public void test_getFault2() throws Exception{
        FaultDataVO faultDataVO = new FaultDataVO();
        List<FaultDataVO> list1 = dashBoardMapper.getFaultList(faultDataVO);


        FaultDataVO faultDataVO2 = new FaultDataVO();
        faultDataVO2.setMonthInfo("2017-08");
        List<FaultDataVO> list2 = dashBoardMapper.getFaultList(faultDataVO2);

        FaultDataVO faultDataVO3 = new FaultDataVO();
        faultDataVO3.setFaultInfoId(14);
        List<FaultDataVO> list3 = dashBoardMapper.getFaultList(faultDataVO3);


        FaultDataVO faultDataVO4 = new FaultDataVO();
        faultDataVO4.setRegion("RUC");
        List<FaultDataVO> list4 = dashBoardMapper.getFaultList(faultDataVO4);

        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
        System.out.println(list4);

    }

    @Test
    public void test_getFault3() throws Exception{
        FaultDataVO faultDataVO = new FaultDataVO();
        faultDataVO.setStartMonth("2016-08");
        faultDataVO.setEndMonth("2017-08");
        List<FaultDataVO> list1 = dashBoardMapper.getFaultListMonthly(faultDataVO);


        System.out.println(list1);


    }

    @Test
    public void test_현재전체사용자() throws Exception{
        System.out.println(dashBoardMapper.getTotalUserByService(CmmDate.getYesterdayGMTDate(), EServiceCode.SDP.toString()));


    }

    @Test
    public void test_누적및실시간비용() throws Exception{
        // 누적
        ParamVO paramVO = new ParamVO();
        paramVO.setDateList(CmmDate.getLastDayList());
        paramVO.setServiceName("%"+EServiceName.SDP.toString()+"%");
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCostByService_CNS(paramVO);

        System.out.println(billingDataVOList);

        // 실시간(어제)
        ParamVO paramVO1 = new ParamVO();
        List<String> list = new ArrayList<>();
        String date = CmmDate.getYesterdayGMTDate();
        date = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6);
        list.add(date);

        paramVO1.setDateList(list);
        paramVO1.setServiceName("%"+EServiceName.SDP.toString()+"%");
        List<BillingDataVO> billingDataVOList1 = dashBoardMapper.getAWSBillingTotalCostByService_CNS(paramVO1);

        System.out.println(billingDataVOList1);
    }

    @Test
    public void test_기기당단가() throws Exception{
        // 전월비용
        ParamVO paramVO = new ParamVO();
        List<String> list = new ArrayList<>();
        String date = CmmDate.getLastDayList().get(1);
        list.add(date);
        paramVO.setDateList(list);
        paramVO.setServiceName("%"+EServiceName.SDP.toString()+"%");
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCostByService_CNS(paramVO);

        System.out.println(billingDataVOList);

        //전월 유저
        String date2 = CmmDate.getLastDayList().get(1).replace("-","");
        System.out.println(date2);
        int totalUser = dashBoardMapper.getTotalUserByService(date2,EServiceCode.SDP.toString());
        double totalCost = billingDataVOList.get(0).getTotalCost();
        System.out.println("전월 비용: " + totalCost);
        System.out.println("전월 유저: " + totalUser);
        System.out.println("기기당 단가: " + (totalCost * 1150 )/totalUser);

    }

    @Test
    public void test_지역별나라리스트() throws Exception {
        List<TotalUserVO> totalUserVOList = dashBoardMapper.getNationListByRegion(CmmDate.getYesterdayGMTDate(),EServiceCode.SDP.toString());
        System.out.println(totalUserVOList);
    }

}
