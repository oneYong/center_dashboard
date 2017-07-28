package com.center.dashboard.admin.service;

import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.vo.TotalUserVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by WYKIM on 2017-07-14.
 */
@Service
public class RUCService {
    private List<String> dateList;
    private List<String> cntryCodeList;
    private List<TotalUserVO> totalUserList;

    private String startDate;
    private String endDate;

    private String chartLabels;
    private String chartDatasets;

    private String[] colorList = {"'#ff6384'","'#36a2eb'","'#cc65fe'","'#36a2eb'","'#cc65fe'"
            ,"'#CCFF00'","'#CCFFCC'","'#FFFF99'","'#CCCC00'","'#CCCCCC'"

            ,"'#FFCC99'","'#CC9900'","'#CC99CC'","'#FF9999'","'#CC6600'"
            ,"'#CC66CC'","'#FF6699'","'#CC3300'","'#CC33CC'","'#FF3399'"

            ,"'#CC0000'","'#666600'","'#6666FF'","'#996666'","'#669933'"
            ,"'#6699CC'","'#6699FF'","'#9999FF'","'#9999CC'","'#66CC00'"

            ,"'#66CCCC'","'#66CCFF'","'#99CC99'","'#00CCCC'","'#33CCFF'"
            ,"'#000033'","'#000066'","'#000099'","'#330066'","'#EC7063'"

            ,"'#C39BD3'","'#9B59B6'","'#85C1E9'","'#2874A6'","'#76D7C4'"
            ,"'#148F77'","'#7DCEA0'","'#1E8449'","'#F7DC6F'","'#B7950B'"
    };

    @Autowired
    private DashBoardMapper dashBoardMapper;
    @Autowired
    private MakeChartService makeChartService;

    public String getChartLabels(){
        return this.chartLabels;
    }
    public String getChartDatasets(){
        return this.chartDatasets;
    }

    public String getServiceListToJson(String date) throws Exception{
        List<TotalUserVO> serviceList = dashBoardMapper.getServiceList_RUC(date);
        return new Gson().toJson(serviceList);
    }

    public void initStartEndDate(String startDate, String endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public void run(String startDate, String endDate) throws Exception{
        // 1. init
        initStartEndDate(startDate,endDate);
        dateList = dashBoardMapper.getCrtDate(startDate, endDate);
        cntryCodeList = dashBoardMapper.getCntryCode_RUC(startDate, endDate);
        totalUserList = dashBoardMapper.getTotalUser_RUC(startDate,endDate);

        chartLabels = makeChartService.makeChartLabels(dateList);
        chartDatasets = makeChartService.makeChartDatasets(dateList,cntryCodeList,totalUserList,colorList);
    }
}
