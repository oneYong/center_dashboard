package com.center.dashboard.vo;

import lombok.Data;

/**
 * Created by WYKIM on 2017-06-20.
 */
@Data
public class UserVO {
    private String user_id;
    private String user_uid;
    private String user_password;
    private String user_name;
    private String user_level;
    private String user_state;
    private String user_email;
    private String user_hp;
    private int company_id = 0;
    private String company_name;
    private String usergroup_id;
}
