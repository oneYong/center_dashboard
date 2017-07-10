package com.center.dashboard.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WYKIM on 2017-06-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EncryptionTest {


    @Test
    public void encryptionTest() throws Exception{
        System.out.println(Encryption.encryptPassword("1234","sbc"));
    }
}
