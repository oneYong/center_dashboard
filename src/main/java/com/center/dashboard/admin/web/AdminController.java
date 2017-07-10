package com.center.dashboard.admin.web;

import com.center.dashboard.mapper.UserMapper;
import com.center.dashboard.util.Encryption;
import com.center.dashboard.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by WYKIM on 2017-06-19.
 */
@Controller
@RequestMapping(value="/web/admin")
public class AdminController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(){
        return "admin/login";
    }

    @RequestMapping(value = "/json/loginProcess", method = RequestMethod.POST )
    @ResponseBody
    public Map<String,Object> loginProcess(@RequestParam("userId") String id,
                                       @RequestParam("password") String password,
                                       HttpSession session, HttpServletResponse response,
                                       HttpServletRequest request) throws Exception
    {
        Map<String, Object> map = new HashMap<>();

        try {
            String user_uid = id;
            String user_password = password;
            UserVO userVO = new UserVO();
            userVO.setUser_uid(user_uid);
            userVO.setUser_password(Encryption.encryptPassword(user_password, "sbc"));
            userVO.setUser_state("승인");	//'1:승인대기','2:승인반려','3:승인','4:비활성'

            UserVO selectuservo = userMapper.selectUserLogin(userVO);

            if(selectuservo != null){
                //로그인 세션
                request.getSession().setAttribute("user_id",selectuservo.getUser_id());
                request.getSession().setAttribute("user_uid",selectuservo.getUser_uid());
                request.getSession().setAttribute("user_name",selectuservo.getUser_name());
                request.getSession().setAttribute("user_level",selectuservo.getUser_level());

                map.put("code", "200");
                //map.put("msg", messageSource.getMessage("succuess.common.msg"));
                map.put("msg", "로그인 성공 하였습니다.");
                map.put("result", selectuservo);

            }else{
                //로그인 실패
                map.put("code", "400");
                //map.put("msg", messageSource.getMessage("fail.common.msg"));
                map.put("msg", "등록된 아이디 또는 패스워드가\n정확하지 않습니다.\n확인 후 다시 로그인 해주십시오.");
            }
        }catch(Exception e){
            map.put("code", "400");
            map.put("msg", "fail");
        }
        return map;
    }

    @RequestMapping("/logout")
    public void loginProcess(Model model, HttpSession session, HttpServletResponse response) throws Exception
    {
        session.invalidate();
        response.sendRedirect("login");
    }

}
