package com.center.dashboard.vo;

import lombok.Data;

/**
 * Created by WYKIM on 2017-07-27.
 */
@Data
public class BillingDataVO {
    private String tag;
    private String productCode;
    private String productName;
    private String totalDate;

    private double ec2; // AmazonEC2
    private double dt;	// AWSDataTransfer
    private double rds;	// AmazonRDS
    private double s3;	// AmazonS3
    private double dms;	// AWSDatabaseMigrationSvc
    private double r53;	// AmazonRoute53
    private double dc;	// AWSDirectConnect
    private double vpc;	// AmazonVPC
    private double cw;	// AmazonCloudWatch
    private double ct;	// AWSCloudTrail
    private double kms;	// awskms

    private double beforeCostExtra;
    private String beforeCostExtraStr;
    private double totalCost;
    private String totalCostStr;
}
