package com.center.dashboard.admin.service;


import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.ERegion;
import com.center.dashboard.vo.TotalUserVO;
import groovy.transform.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wonyongkim on 2017. 3. 26..
 */
@Service
public class MakeChartService {

    public String getDateAddComma(String date){
        return "'"+ date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6)+"'";
    }

    public String makeChartLabels(List<String> dateList){
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
        return tempLabels;
    }

    public String makeChartDatasets(List<String> dateList, List<String> cntryCodeList,
                                  List<TotalUserVO> totalUserList, String[] colorList){

        String datasetTemplate = "{data:[%s],label:\'%s\',borderColor:%s,backgroundColor:%s,fill:false}";
        String tempDataSetTemplate = "[";

        // cntryCodeList size equal cntryNameList size
        int size = cntryCodeList.size();

        for(int i = 0; i < size; i++){
            String cntryCode = cntryCodeList.get(i);
            String cntryName = "";

            String tempData = "";
            int colorIndex = i % colorList.length;

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
        return tempDataSetTemplate;

    }

}
