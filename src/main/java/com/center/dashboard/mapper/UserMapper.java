package com.center.dashboard.mapper;

import com.center.dashboard.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by WYKIM on 2017-06-20.
 */
@Mapper
public interface UserMapper {
    UserVO selectUserLogin(UserVO userVO) throws Exception;
}
