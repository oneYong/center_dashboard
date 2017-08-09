package com.center.dashboard.util;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by WYKIM on 2017-08-02.
 */
public class CmmUtils {
    public static String doubleToMoneyString(double money){
        DecimalFormat dc = new DecimalFormat("###,###,###,###.##");
        return "$ " + dc.format(money);
    }

    public static<T> String getListToJson(List<T> list) throws Exception{
        return new Gson().toJson(list);
    }
}
