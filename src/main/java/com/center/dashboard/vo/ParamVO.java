package com.center.dashboard.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by WYKIM on 2017-06-20.
 */
@Data
public class ParamVO {
    private List<String> dateList;
    private String serviceCode;
    private String serviceName;
}
