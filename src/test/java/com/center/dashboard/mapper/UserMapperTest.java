package com.center.dashboard.mapper;

import com.center.dashboard.util.Encryption;
import com.center.dashboard.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WYKIM on 2017-06-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Rollback(value=true)
public class UserMapperTest {
    /*
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_selectUserLogin() throws Exception{
        String user_uid = "admin";
        String user_password = "admin";
        UserVO userVO = new UserVO();
        userVO.setUser_uid(user_uid);
        userVO.setUser_password(Encryption.encryptPassword(user_password, "sbc"));
        userVO.setUser_state("승인");	//'1:승인대기','2:승인반려','3:승인','4:비활성'

        UserVO selectuservo = userMapper.selectUserLogin(userVO);
        System.out.println(selectuservo);
    }
    */
}
