package com.center.dashboard.admin.service;

import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.EServiceCode;
import com.center.dashboard.util.EServiceName;
import com.center.dashboard.vo.BillingDataVO;
import com.center.dashboard.vo.ParamVO;
import com.center.dashboard.vo.TotalUserVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYKIM on 2017-07-14.
 */
@Service
public class SDPService {
    @Autowired
    private DashBoardMapper dashBoardMapper;

    // date 에 해당하는 SDP 현재 사용자를 구함
    public String getTotalUser(String date) throws Exception {
        DecimalFormat dc = new DecimalFormat("###,###,###,###");
        int totalUser = dashBoardMapper.getTotalUserByService(date, EServiceCode.SDP.toString());
        return dc.format(totalUser);
    }

    // 당해년도 1월 1일 부터 전날까지의 비용의 합
    public String getTotalCost() throws Exception {
        DecimalFormat dc = new DecimalFormat("###,###,###,###.##");
        ParamVO paramVO = new ParamVO();
        paramVO.setDateList(CmmDate.getLastDayList());
        paramVO.setServiceName("%"+ EServiceName.SDP.toString()+"%");
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCostByService_CNS(paramVO);
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
        paramVO.setServiceName("%"+EServiceName.SDP.toString()+"%");
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCostByService_CNS(paramVO);

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
        paramVO.setServiceName("%"+EServiceName.SDP.toString()+"%");
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCostByService_CNS(paramVO);


        //전월 유저
        String date2 = CmmDate.getLastDayList().get(1).replace("-","");
        System.out.println(date2);
        int totalUser = dashBoardMapper.getActTotalUserByService(date2,EServiceCode.SDP.toString());
        double totalCost = billingDataVOList.get(0).getTotalCost();

        double deviceCostByOne =  (totalCost * 1150) / totalUser;
        return dc.format(deviceCostByOne);
    }


}
