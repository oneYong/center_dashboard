package com.center.dashboard.admin.service;

import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.vo.BillingDataVO;
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
public class AWSBillingService {

    @Autowired
    private DashBoardMapper dashBoardMapper;

    public String getTotalCost()throws Exception{
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCost_CNS(CmmDate.getLastDayList());
        DecimalFormat dc = new DecimalFormat("###,###,###,###.##");
        double totalCost = 0;

        for(int i = 0; i < billingDataVOList.size(); i++){
            BillingDataVO billingDataVO = billingDataVOList.get(i);
            double temp = billingDataVO.getTotalCost();
            totalCost += temp;
        }

        return dc.format(totalCost);
    }

    public String getBeforeMonthTotalCost(int gap) throws Exception{
        String lastDay = CmmDate.getLastDayOneMonth(gap);
        List<String> list = new ArrayList<>();
        list.add(lastDay);

        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCost_CNS(list);
        DecimalFormat dc = new DecimalFormat("###,###,###,###.##");
        double totalCost = billingDataVOList.get(0).getTotalCost();

        return dc.format(totalCost);
    }

    public String getBeforeMonth(int gap) throws Exception{
        String beforeMonth = CmmDate.getBeforeYearMonth(gap);
        int month = Integer.parseInt(beforeMonth.substring(4,6));
        return String.valueOf(month);
    }

}
