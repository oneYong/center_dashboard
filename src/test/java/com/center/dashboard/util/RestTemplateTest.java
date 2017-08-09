package com.center.dashboard.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created by WYKIM on 2017-06-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RestTemplateTest {


    @Test
    public void Test() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://52.41.107.22/api/billing/selectAccountBillingInfo.do?userId=lge_cloudcenter_infra&passwd=!dlatl00&year=2017&month=08&accountId=160945176187", String.class);


        System.out.println(result);

    }


}
