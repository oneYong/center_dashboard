package com.center.dashboard.admin.service;


import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.ERegion;
import com.center.dashboard.vo.TotalUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wonyongkim on 2017. 3. 26..
 */
@Service
public class MakeChartService {

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
                                ,"''","''","''","''","''"
    };

    @Autowired
    private DashBoardMapper dashBoardMapper;

    public String getChartLabels(){
        return this.chartLabels;
    }
    public String getChartDatasets(){
        return this.chartDatasets;
    }


    public String getDateAddComma(String date){
        return "'"+ date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6)+"'";
    }

    public void initStartEndDate(String startDate, String endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void initData(ERegion eRegion) throws Exception {
        switch (eRegion){
            case KIC:
                        dateList = dashBoardMapper.getCrtDate_KIC(startDate, endDate);
                        cntryCodeList = dashBoardMapper.getCntryCode_KIC(CmmDate.getYesterdayGMTDate(), endDate);
                        totalUserList = dashBoardMapper.getTotalUser_KIC(startDate,endDate);
            case AIC:
            case EIC:
            case RUC:
        }
    }

    public void makeChartLabels(){
        int size = dateList.size();
        String tempLabels = "";
        for(int i = 0; i < size; i++){
            String date = dateList.get(i);
            if(i == 0){
                tempLabels = getDateAddComma(date);
            }else{
                tempLabels += "," + getDateAddComma(date);
            }
        }
        this.chartLabels = tempLabels;
    }




    public void makeChartDatasets(){

        String datasetTemplate = "{data:[%s],label:\'%s\',borderColor:%s,backgroundColor:%s,fill:false}";
        String tempDataSetTemplate = "[";

        // cntryCodeList size equal cntryNameList size
        int size = cntryCodeList.size();

        for(int i = 0; i < size; i++){
            String cntryCode = cntryCodeList.get(i);
            String cntryName = "";

            String tempData = "";
            int colorIndex = i % 50;

            // start data set ...
            for(int j = 0; j < dateList.size(); j++){
                String tempDate = dateList.get(j);
                boolean isHave = false;

                int index = 0;
                while(index < totalUserList.size()){
                    TotalUserVO totalUserVO = totalUserList.get(index);
                    if(cntryCode.equals(totalUserVO.getCntryCode())
                            && tempDate.equals(totalUserVO.getCrtDate())){
                        isHave = true;
                        cntryName = totalUserVO.getCntryName();
                        break;
                    }
                    index++;
                }

                if(j == 0){
                    if(isHave){
                        TotalUserVO tempTotalUserVO = totalUserList.get(index);
                        tempData = tempTotalUserVO.getTCnt();
                    }else{
                        tempData = "0";
                    }
                }else{
                    if(isHave){
                        TotalUserVO tempTotalUserVO = totalUserList.get(index);
                        tempData = tempData + "," + tempTotalUserVO.getTCnt();
                    }else{
                        tempData = tempData + "," + "0";
                    }
                }

            }
            // end data set ...
            String keyName = cntryCode +"(" + cntryName+")";
            if(i == 0){
                tempDataSetTemplate = tempDataSetTemplate + String.format(datasetTemplate,tempData,keyName,colorList[colorIndex],colorList[colorIndex]);
            }else{
                tempDataSetTemplate = tempDataSetTemplate + "," + String.format(datasetTemplate,tempData,keyName,colorList[colorIndex],colorList[colorIndex]);
            }

        }

        tempDataSetTemplate += "]";
        this.chartDatasets = tempDataSetTemplate;

    }

    public void run(ERegion eRegion, String startDate, String endDate) throws Exception{
        switch (eRegion){
            case KIC:
                // 1. init
                initStartEndDate(startDate,endDate);
                initData(eRegion);

                // 2. chart
                makeChartLabels();
                makeChartDatasets();
            case AIC:
            case EIC:
            case RUC:
        }
    }
}
