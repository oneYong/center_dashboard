package com.center.dashboard.admin.service;

import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.CmmUtils;
import com.center.dashboard.vo.BillingDataVO;

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

    private String chartLabels = "";

    public String getDateAddCommaAWS(String date){
        return "'"+ date.substring(0,4) + "-" + date.substring(5,7) +"'";
    }

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

    public List<Double> getTotalCostList(String startDate, String endDate) throws Exception{
        List<Double> list = new ArrayList<>();
        List<BillingDataVO> billingDataVOList = dashBoardMapper.getAWSBillingTotalCost_CNS(CmmDate.getLastDayList(startDate, endDate));
        String tempLabels = "";
        for(int i = billingDataVOList.size()-1; i >= 0 ; i--){
            BillingDataVO billingDataVO = billingDataVOList.get(i);
            list.add(billingDataVO.getTotalCost());

            if(i == billingDataVOList.size()-1){
                tempLabels = getDateAddCommaAWS(billingDataVO.getTotalDate());
            }else{
                tempLabels += "," + getDateAddCommaAWS(billingDataVO.getTotalDate());
            }
        }
        chartLabels = tempLabels;

        return list;
    }

    public String getChartLabels() throws Exception {
        return chartLabels;
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

    public List<BillingDataVO> getProductList(String date) throws Exception{
        List<BillingDataVO> productListCns = dashBoardMapper.getAWSProductList_CNS(date);

        for(int i = 0; i < productListCns.size(); i++){
            BillingDataVO billingDataVO = productListCns.get(i);
            billingDataVO.setTotalCostStr(CmmUtils.doubleToMoneyString(billingDataVO.getTotalCost()));
        }

        return productListCns;
    }

    public List<BillingDataVO> getServiceList(String date) throws Exception{
        List<BillingDataVO> serviceListCns = dashBoardMapper.getAWSServiceList_CNS(date);

        for(int i = 0; i < serviceListCns.size(); i++){
            BillingDataVO billingDataVO = serviceListCns.get(i);
            billingDataVO.setTotalCostStr(CmmUtils.doubleToMoneyString(billingDataVO.getTotalCost()));
        }

        return serviceListCns;
    }

    public List<BillingDataVO> getMonthlyProductList(String startMonth, String endMonth) throws Exception {
        List<BillingDataVO> list = dashBoardMapper.getAWSMonthlyProductCost_CNS(startMonth,endMonth);

        for(int i = 0; i < list.size(); i++){
            BillingDataVO billingDataVO = list.get(i);
            billingDataVO.setTotalCostStr(CmmUtils.doubleToMoneyString(billingDataVO.getTotalCost()));
            billingDataVO.setBeforeCostExtraStr(CmmUtils.doubleToMoneyString(billingDataVO.getBeforeCostExtra()));
        }

        return list;
    }

}
