package com.center.dashboard.admin.web;

import com.center.dashboard.admin.service.*;
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
    private KICService kicService;

    @Autowired
    private AICService aicService;

    @Autowired
    private EICService eicService;

    @Autowired
    private RUCService rucService;

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
    public String totalUserByRegion(Model model, HttpSession session, HttpServletRequest request){
        String user_id = (String) request.getSession().getAttribute("user_id");
        String defaultStartDate = CmmDate.getAWeeksAgoGMTDate();
        String defaultEndDate = CmmDate.getYesterdayGMTDate();
        model.addAttribute("startDate",defaultStartDate);
        model.addAttribute("endDate",defaultEndDate);
        if(user_id != null){
            return "admin/totalUserByRegion";
        }else{
            return "admin/login";
        }
    }

    @RequestMapping(value="/totalUserByRegion/totalUserByKIC", method = RequestMethod.GET)
    public String totalUserByKIC(Model model,@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate" , required = false) String endDate) throws Exception{

        String pStartDate = CmmDate.getAWeeksAgoGMTDate();
        String pEndDate = CmmDate.getYesterdayGMTDate();

        if(!startDate.equals("") && startDate != null){
            pStartDate = startDate;
        }
        if(!endDate.equals("") && endDate != null){
            pEndDate = endDate;
        }

        kicService.run(pStartDate, pEndDate);
        model.addAttribute("kicLabel",kicService.getChartLabels());
        model.addAttribute("kicDatasets",kicService.getChartDatasets());

        return "admin/totalUserByRegion/totalUserByKIC";
    }

    @RequestMapping(value="/totalUserByRegion/totalUserByAIC", method = RequestMethod.GET)
    public String totalUserByAIC(Model model,@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate" , required = false) String endDate) throws Exception{

        String pStartDate = CmmDate.getAWeeksAgoGMTDate();
        String pEndDate = CmmDate.getYesterdayGMTDate();

        if(!startDate.equals("") && startDate != null){
            pStartDate = startDate;
        }
        if(!endDate.equals("") && endDate != null){
            pEndDate = endDate;
        }

        aicService.run( pStartDate, pEndDate);
        model.addAttribute("aicLabel",aicService.getChartLabels());
        model.addAttribute("aicDatasets",aicService.getChartDatasets());

        return "admin/totalUserByRegion/totalUserByAIC";
    }

    @RequestMapping(value="/totalUserByRegion/totalUserByEIC", method = RequestMethod.GET)
    public String totalUserByEIC(Model model,@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate" , required = false) String endDate) throws Exception{

        String pStartDate = CmmDate.getAWeeksAgoGMTDate();
        String pEndDate = CmmDate.getYesterdayGMTDate();

        if(!startDate.equals("") && startDate != null){
            pStartDate = startDate;
        }
        if(!endDate.equals("") && endDate != null){
            pEndDate = endDate;
        }

        eicService.run( pStartDate, pEndDate);
        model.addAttribute("eicLabel",eicService.getChartLabels());
        model.addAttribute("eicDatasets",eicService.getChartDatasets());

        return "admin/totalUserByRegion/totalUserByEIC";
    }

    @RequestMapping(value="/totalUserByRegion/totalUserByRUC", method = RequestMethod.GET)
    public String totalUserByRUC(Model model,@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate" , required = false) String endDate) throws Exception{

        String pStartDate = CmmDate.getAWeeksAgoGMTDate();
        String pEndDate = CmmDate.getYesterdayGMTDate();

        if(!startDate.equals("") && startDate != null){
            pStartDate = startDate;
        }
        if(!endDate.equals("") && endDate != null){
            pEndDate = endDate;
        }

        rucService.run( pStartDate, pEndDate);
        model.addAttribute("rucLabel",rucService.getChartLabels());
        model.addAttribute("rucDatasets",rucService.getChartDatasets());

        return "admin/totalUserByRegion/totalUserByRUC";
    }
}
