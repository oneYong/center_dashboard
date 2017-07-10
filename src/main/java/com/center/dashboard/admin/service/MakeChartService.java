package com.center.dashboard.admin.service;


import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.ERegion;
import com.center.dashboard.vo.ChartDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wonyongkim on 2017. 3. 26..
 */
@Service
public class MakeChartService {

    private String labels;
    private Map<String,String> cntryValue;

    private String chartLabels;
    private String chartDatasets;
    private String[] colorList = {"'#ff6384'","'#36a2eb'","'#cc65fe'","'#36a2eb'","'#cc65fe'"
                                 ,"'#ff6384'","'#36a2eb'","'#cc65fe'","'#36a2eb'","'#cc65fe'"};

    @Autowired
    private DashBoardMapper dashBoardMapper;

    public String getChartLabels(){
        return this.chartLabels;
    }

    public String getChartDatasets(){
        return this.chartDatasets;
    }

    public String getLabels(){
        return this.labels;
    }

    public Map<String,String> getCntryValue(){
        return this.cntryValue;
    }

    public String getDate(String date){
        return "'"+ date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6)+"'";
    }

    public void makeLabels(List<ChartDataVO> chartDataVOList){
        this.labels = null;
        for(int i = 0; i < chartDataVOList.size(); i++){
            ChartDataVO chartDataVO = chartDataVOList.get(i);
            if(i == 0) {
                labels = getDate(chartDataVO.getCrtDate());
            }else{
                labels += "," + getDate(chartDataVO.getCrtDate());
            }
        }

    }

    public void makeCntryValue(List<ChartDataVO> chartDataVOList){
        this.cntryValue = new TreeMap<>();

        // tcnt list init
        ChartDataVO tempVO = chartDataVOList.get(0);
        String[] tempCntryCodeList = tempVO.getCntryCode().split(",");
        String[] tempCntryNameList = tempVO.getCntryName().split(",");

        int cntrySize = tempCntryCodeList.length;
        String[] tCntList = new String[cntrySize];

        for(int i = 0; i < cntrySize; i++){
            tCntList[i] = new String("");
        }


        // tcnt list set1
        for(int i = 0; i < chartDataVOList.size(); i++){
            ChartDataVO chartDataVO = chartDataVOList.get(i);
            String[] tempCntList = chartDataVO.getTCnt().split(",");

            if(i == 0){
                for(int j = 0; j < cntrySize; j++){
                    tCntList[j] = tempCntList[j];
                }
            }else{
                for(int j = 0; j < cntrySize; j++){
                    tCntList[j] = tCntList[j] + "," + tempCntList[j];
                }
            }
        }

        for(int i = 0; i < cntrySize; i++){
            String keyName = tempCntryCodeList[i]+"("+tempCntryNameList[i]+")";
            cntryValue.put(keyName,tCntList[i]);
        }

    }

    public void makeChartInfo(){
        this.chartLabels = this.labels;
        String datasetTemplate = "{data:[%s],label:\"%s\",borderColor:%s,backgroundColor:%s,fill:false}";
        String tempDataSetTemplate = "";

        Set key = cntryValue.keySet();
        int index = 0;
        for (Iterator iterator = key.iterator(); iterator.hasNext();) {
            String keyName = (String) iterator.next();
            String valueName = (String) cntryValue.get(keyName);

            int colorIndex = index % 10;
            if(index == 0){
                tempDataSetTemplate = String.format(datasetTemplate,valueName,keyName,colorList[colorIndex],colorList[colorIndex]);
            }else{
                tempDataSetTemplate = tempDataSetTemplate + "," + String.format(datasetTemplate,valueName,keyName,colorList[colorIndex],colorList[colorIndex]);
            }

            index++;
        }

        this.chartDatasets = tempDataSetTemplate;

    }

    public void run(ERegion eRegion) throws Exception{
        switch (eRegion){
            case KIC:
                List<ChartDataVO> chartDataVOList = dashBoardMapper.getDefaultTotalCnt_KIC();
                // 1. makeLabels
                makeLabels(chartDataVOList);
                // 2. makeCntryValue
                makeCntryValue(chartDataVOList);
                // 3. makeChartInfo
                makeChartInfo();
            case AIC:
            case EIC:
            case RUC:
        }
    }
}
