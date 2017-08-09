package com.center.dashboard.util;

import lombok.Data;

/**
 * Created by WYKIM on 2017-08-04.
 */
@Data
public class MegaAWSAccountBillingInfo {
    private int count;
    private long id;
    private String accountId;
    private String accountName;
    private String serviceName;
    private String regionName;
    private String tag;
    private String productCode;
    private String productName;
    private String totalDate;
    private double totalCost;
    private double beforeCostExtra;
    private double serviceTotalCost;
    private double serviceExtraTotalCost;
    private String totalCostStr;
    private String beforeCostExtraStr;
    private String totalMonthStr;
    private String totalWeekStr;
}
