package com.center.dashboard.admin.service;

import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.EServiceCode;
import com.center.dashboard.util.EServiceName;
import com.center.dashboard.vo.BillingDataVO;
import com.center.dashboard.vo.ParamVO;
import com.center.dashboard.vo.TotalUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYKIM on 2017-07-14.
 */
@Service
public class THINQService {
    @Autowired
    private DashBoardMapper dashBoardMapper;

    private String chartLabels = "";

    public String getChartLabels() throws Exception {
        return chartLabels;
    }

    // chart 필요한 리스트
    public List<Double> getTotalCostList(String startDate, String endDate) throws Exception{
        List<Double> list = new ArrayList<>();
        ParamVO paramVO = new ParamVO();
        paramVO.setDateList(CmmDate.getLastDayList(startDate, endDate));
        paramVO.setServiceName("%"+ EServiceName.THINQ.toString()+"%");
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCostByService_MEGA(paramVO);
        String tempLabels = "";

        for(int i = billingDataVOList.size()-1; i >= 0 ; i--){
            BillingDataVO billingDataVO = billingDataVOList.get(i);
            list.add(billingDataVO.getTotalCost());

            tempLabels = getString(billingDataVOList, tempLabels, i, billingDataVO);
        }

        chartLabels = tempLabels;

        return list;
    }

        private String getString(List<BillingDataVO> billingDataVOList, String tempLabels, int i, BillingDataVO billingDataVO) {
            if(i == billingDataVOList.size()-1){
                tempLabels = billingDataVO.getTotalDate();
            }else{
                tempLabels += "," + billingDataVO.getTotalDate();
            }
            return tempLabels;
        }

    // Nation List
    public List<TotalUserVO> getNationListByRegion(String date) throws Exception {
        return dashBoardMapper.getNationListByRegion(date,EServiceCode.THINQ.toString());
    }

    // date 에 해당하는 SDP 현재 사용자를 구함
    public String getTotalUser(String date) throws Exception {
        DecimalFormat dc = new DecimalFormat("###,###,###,###");
        int totalUser = dashBoardMapper.getTotalUserByService(date, EServiceCode.THINQ.toString());
        return dc.format(totalUser);
    }

    // 당해년도 1월 1일 부터 전날까지의 비용의 합
    public String getTotalCost() throws Exception {
        DecimalFormat dc = new DecimalFormat("###,###,###,###.##");
        ParamVO paramVO = new ParamVO();
        paramVO.setDateList(CmmDate.getLastDayList());
        paramVO.setServiceName("%"+ EServiceName.THINQ.toString()+"%");
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCostByService_MEGA(paramVO);
        double totalCost = 0;
        for(int i = 0; i < billingDataVOList.size(); i++){
            BillingDataVO billingDataVO = billingDataVOList.get(i);
            totalCost += billingDataVO.getTotalCost();
        }

        return dc.format(totalCost);
    }

    // 전날 비용
    public String getRealCost() throws Exception {
        DecimalFormat dc = new DecimalFormat("###,###,###,###.##");
        ParamVO paramVO = new ParamVO();
        List<String> list = new ArrayList<>();
        String date = CmmDate.getYesterdayGMTDate();
        date = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6);
        list.add(date);

        paramVO.setDateList(list);
        paramVO.setServiceName("%"+EServiceName.THINQ.toString()+"%");
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCostByService_MEGA(paramVO);

        double totalCost = billingDataVOList.get(0).getTotalCost();
        return dc.format(totalCost);
    }

    // 기기당 단가 ( 전월 비용 / 전월 월 로그인 사용자 )
    public String getDeviceCostByOne() throws Exception {
        DecimalFormat dc = new DecimalFormat("###,###,###,###.##");
        // 전월비용
        ParamVO paramVO = new ParamVO();
        List<String> list = new ArrayList<>();
        String date = CmmDate.getLastDayList().get(1);
        list.add(date);
        paramVO.setDateList(list);
        paramVO.setServiceName("%"+EServiceName.THINQ.toString()+"%");
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCostByService_MEGA(paramVO);


        //전월 유저
        String date2 = CmmDate.getLastDayList().get(1).replace("-","");
        System.out.println(date2);
        int totalUser = dashBoardMapper.getActTotalUserByService(date2,EServiceCode.THINQ.toString());
        double totalCost = billingDataVOList.get(0).getTotalCost();

        double deviceCostByOne =  (totalCost * 1150) / totalUser;
        return dc.format(deviceCostByOne);
    }


}
