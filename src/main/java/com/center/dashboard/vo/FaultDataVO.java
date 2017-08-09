package com.center.dashboard.vo;

import lombok.Data;

/**
 * Created by WYKIM on 2017-07-27.
 */
@Data
public class FaultDataVO {
    private int faultInfoId;
    private String serviceName;
    private String region;
    private String description;
    private String grade;
    private String monthInfo;
    private String faultMinute;
    private String comment;
    private String registerDate;

    private int faultCount;

    private int monthCnt1;
    private int monthCnt2;
    private int monthCnt3;
    private int monthCnt4;
    private int monthCnt5;
    private int monthCnt6;
    private int monthCnt7;
    private int monthCnt8;
    private int monthCnt9;
    private int monthCnt10;
    private int monthCnt11;
    private int monthCnt12;
    private int monthCnt13;

    private int monthMinute1;
    private int monthMinute2;
    private int monthMinute3;
    private int monthMinute4;
    private int monthMinute5;
    private int monthMinute6;
    private int monthMinute7;
    private int monthMinute8;
    private int monthMinute9;
    private int monthMinute10;
    private int monthMinute11;
    private int monthMinute12;
    private int monthMinute13;

    private String startMonth;
    private String endMonth;
}
