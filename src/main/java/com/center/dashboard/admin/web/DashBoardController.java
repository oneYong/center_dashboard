package com.center.dashboard.admin.web;

import com.center.dashboard.admin.service.MakeChartService;
import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.mapper.UserMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.ERegion;
import com.center.dashboard.util.Encryption;
import com.center.dashboard.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WYKIM on 2017-06-19.
 */
@Controller
@RequestMapping(value="/web/admin")
public class DashBoardController {

    @Autowired
    private DashBoardMapper dashBoardMapper;

    @Autowired
    private MakeChartService makeChartService;

    private final double LIMIT_USER = 10000.0;

    @RequestMapping(value="/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpSession session, HttpServletRequest request){
        String user_id = (String) request.getSession().getAttribute("user_id");

        if(user_id != null){
            return "admin/index";
        }else{
            return "admin/login";
        }
    }

    @RequestMapping(value="/dashboard/totalUser", method = RequestMethod.GET)
    public String totalUser(Model model) throws Exception{
        int totalCnt = dashBoardMapper.getTotalCntByDate(CmmDate.getYesterdayGMTDate()).getTotalCnt();
        int beforeTotalCnt = dashBoardMapper.getTotalCntByDate(CmmDate.getBeforeYesterdayGMTDate()).getTotalCnt();
        int newTotalCnt = totalCnt - beforeTotalCnt;
        model.addAttribute("totalCnt",String.format("%.1f",totalCnt / LIMIT_USER));
        model.addAttribute("newTotalCnt",String.format("%.1f", newTotalCnt / LIMIT_USER));
        return "admin/dashboard/totalUser";
    }

    @RequestMapping(value="/totalUserByRegion", method = RequestMethod.GET)
    public String totalUserByRegion(HttpSession session, HttpServletRequest request){
        String user_id = (String) request.getSession().getAttribute("user_id");

        if(user_id != null){
            return "admin/totalUserByRegion";
        }else{
            return "admin/login";
        }
    }

    @RequestMapping(value="/totalUserByRegion/totalUserByKIC", method = RequestMethod.GET)
    public String totalUserByKIC(Model model) throws Exception{

        makeChartService.run(ERegion.KIC, CmmDate.getAWeeksAgoGMTDate(),CmmDate.getTodayGMTDate());
        model.addAttribute("kic_label",makeChartService.getChartLabels());
        model.addAttribute("kic_datasets",makeChartService.getChartDatasets());

        return "admin/totalUserByRegion/totalUserByKIC";
    }
}
