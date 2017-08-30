package com.center.dashboard.admin.web;

import com.center.dashboard.admin.service.*;
import com.center.dashboard.mapper.DashBoardMapper;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.CmmUtils;
import com.center.dashboard.vo.FaultDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by WYKIM on 2017-06-19.
 */
@Controller
@RequestMapping(value="/web/admin")
public class TotalUserController {

    @Autowired
    private KICService kicService;

    @Autowired
    private AICService aicService;

    @Autowired
    private EICService eicService;

    @Autowired
    private RUCService rucService;

    @RequestMapping(value="/totalUserByRegion", method = RequestMethod.GET)
    public String totalUserByRegion(Model model, HttpSession session, HttpServletRequest request){
        String user_id = (String) request.getSession().getAttribute("user_id");
        String defaultStartDate = CmmDate.getBeforeYearMonth(2);
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
        model.addAttribute("kicServiceList",kicService.getServiceListToJson(pEndDate));

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
        model.addAttribute("aicServiceList",aicService.getServiceListToJson(pEndDate));

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
        model.addAttribute("eicServiceList",eicService.getServiceListToJson(pEndDate));

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
        model.addAttribute("rucServiceList",rucService.getServiceListToJson(pEndDate));
        return "admin/totalUserByRegion/totalUserByRUC";
    }

}
