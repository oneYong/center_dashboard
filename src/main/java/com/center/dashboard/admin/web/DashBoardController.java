package com.center.dashboard.admin.web;

import com.center.dashboard.admin.service.*;
import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.mapper.UserMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.CmmUtils;
import com.center.dashboard.util.ERegion;
import com.center.dashboard.util.Encryption;
import com.center.dashboard.vo.FaultDataVO;
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
import java.util.List;
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
    private AWSBillingService awsBillingService;

    @Autowired
    private SDPService sdpService;

    @Autowired
    private THINQService thinqService;


    private final double LIMIT_USER = 10000.0;

    @RequestMapping(value="/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model, HttpSession session, HttpServletRequest request){
        String user_id = (String) request.getSession().getAttribute("user_id");

        String standardDate = CmmDate.getTodayGMTDate().substring(0,4) + "." + CmmDate.getTodayGMTDate().substring(4,6)+ "." + CmmDate.getTodayGMTDate().substring(6);

        model.addAttribute("standardDate",standardDate);

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

    @RequestMapping(value="/dashboard/totalBilling", method = RequestMethod.GET)
    public String totalBilling(Model model) throws Exception{
        String title = CmmDate.getStartEndDay(CmmDate.getLastDayList().get(0));
        String totalCost = awsBillingService.getTotalCost();
        String oneMonth = awsBillingService.getBeforeMonth(1);
        String twoMonth = awsBillingService.getBeforeMonth(2);
        String oneMonthCost = awsBillingService.getBeforeMonthTotalCost(1);
        String twoMonthCost = awsBillingService.getBeforeMonthTotalCost(2);

        model.addAttribute("title",title);
        model.addAttribute("totalCost","$ "+totalCost);
        model.addAttribute("oneMonth",oneMonth);
        model.addAttribute("twoMonth",twoMonth);
        model.addAttribute("oneMonthCost","$ "+oneMonthCost);
        model.addAttribute("twoMonthCost","$ "+twoMonthCost);

        return "admin/dashboard/totalBilling";
    }

    @RequestMapping(value="/dashboard/totalFault", method = RequestMethod.GET)
    public String totalFault(Model model) throws Exception{
        int faultDayDiff =  dashBoardMapper.getFaultDayDiff();
        model.addAttribute("faultDayDiff",faultDayDiff);
        return "admin/dashboard/totalFault";
    }

    @RequestMapping(value="/dashboard/totalSDP", method = RequestMethod.GET)
    public String totalSDP(Model model) throws Exception{
        String totalCostTitle = "01.01 ~ " + CmmDate.getYesterdayGMTDate().substring(4,6)+ "." + CmmDate.getYesterdayGMTDate().substring(6);
        String realCostTitle = CmmDate.getYesterdayGMTDate().substring(4,6)+ "." +"01 ~ " + CmmDate.getYesterdayGMTDate().substring(4,6)+ "." + CmmDate.getYesterdayGMTDate().substring(6);

        String totalUser = sdpService.getTotalUser(CmmDate.getYesterdayGMTDate());
        String realCost = sdpService.getRealCost();
        String totalCost = sdpService.getTotalCost();
        String deviceCostByOne = sdpService.getDeviceCostByOne();

        model.addAttribute("totalUser",totalUser);
        model.addAttribute("realCost",realCost);
        model.addAttribute("totalCost",totalCost);
        model.addAttribute("deviceCostByOne",deviceCostByOne);

        model.addAttribute("totalCostTitle",totalCostTitle);
        model.addAttribute("realCostTitle",realCostTitle);

        return "admin/dashboard/totalSDP";
    }

    @RequestMapping(value="/dashboard/totalTHINQ", method = RequestMethod.GET)
    public String totalTHINQ(Model model) throws Exception{
        String totalCostTitle = "01.01 ~ " + CmmDate.getYesterdayGMTDate().substring(4,6)+ "." + CmmDate.getYesterdayGMTDate().substring(6);
        String realCostTitle = CmmDate.getYesterdayGMTDate().substring(4,6)+ "." +"01 ~ " + CmmDate.getYesterdayGMTDate().substring(4,6)+ "." + CmmDate.getYesterdayGMTDate().substring(6);

        String totalUser = thinqService.getTotalUser(CmmDate.getYesterdayGMTDate());
        String realCost = thinqService.getRealCost();
        String totalCost = thinqService.getTotalCost();
        String deviceCostByOne = thinqService.getDeviceCostByOne();

        model.addAttribute("totalUser",totalUser);
        model.addAttribute("realCost",realCost);
        model.addAttribute("totalCost",totalCost);
        model.addAttribute("deviceCostByOne",deviceCostByOne);

        model.addAttribute("totalCostTitle",totalCostTitle);
        model.addAttribute("realCostTitle",realCostTitle);

        return "admin/dashboard/totalTHINQ";
    }

}
