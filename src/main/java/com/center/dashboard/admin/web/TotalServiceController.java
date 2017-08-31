package com.center.dashboard.admin.web;

import com.center.dashboard.admin.service.AWSBillingService;
import com.center.dashboard.admin.service.SDPService;
import com.center.dashboard.admin.service.THINQService;
import com.center.dashboard.util.CmmDate;
import com.center.dashboard.util.CmmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by WYKIM on 2017-06-19.
 */
@Controller
@RequestMapping(value="/web/admin")
public class TotalServiceController {


    @Autowired
    private SDPService sdpService;

    @Autowired
    private THINQService thinqService;


    @RequestMapping(value="/totalSDPByService", method = RequestMethod.GET)
    public String totalSDPByService(Model model, HttpSession session, HttpServletRequest request) throws Exception{
        String user_id = (String) request.getSession().getAttribute("user_id");
        String yesterDay = CmmDate.getYesterdayGMTDate().substring(0,6);
        String endDate = yesterDay.substring(0,4) + yesterDay.substring(4,6);
        String startDate = CmmDate.getBeforeYearMonth(yesterDay,5).substring(0,6);
        startDate = startDate.substring(0,4) + startDate.substring(4,6);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);

        if(user_id != null){
            return "admin/totalSDPByService";
        }else{
            return "admin/login";
        }
    }

    @RequestMapping(value="/totalTHINQByService", method = RequestMethod.GET)
    public String totalTHINQByService(Model model, HttpSession session, HttpServletRequest request) throws Exception{
        String user_id = (String) request.getSession().getAttribute("user_id");
        String yesterDay = CmmDate.getYesterdayGMTDate().substring(0,6);
        String endDate = yesterDay.substring(0,4) + yesterDay.substring(4,6);
        String startDate = CmmDate.getBeforeYearMonth(yesterDay,5).substring(0,6);
        startDate = startDate.substring(0,4) + startDate.substring(4,6);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);

        if(user_id != null){
            return "admin/totalTHINQByService";
        }else{
            return "admin/login";
        }
    }

    @RequestMapping(value="/totalSDPByService/totalSDPByGraph", method = RequestMethod.GET)
    public String totalSDPByGraph(Model model,@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate" , required = false) String endDate) throws Exception{

        String yesterDay = CmmDate.getYesterdayGMTDate().substring(0,6);
        String pEndDate = yesterDay.substring(0,4) + yesterDay.substring(4,6);
        String pStartDate = CmmDate.getBeforeYearMonth(yesterDay,5).substring(0,6);
        pStartDate = pStartDate.substring(0,4) + pStartDate.substring(4,6);

        if(!startDate.equals("") && startDate != null){
            pStartDate = startDate;
        }
        if(!endDate.equals("") && endDate != null){
            pEndDate = endDate;
        }

        // first totalCostList...
        model.addAttribute("sdpGraphDatasets",sdpService.getTotalCostList(pStartDate, pEndDate));
        model.addAttribute("sdpGraphLabel",sdpService.getChartLabels());
        model.addAttribute("sdpGraphNationList", CmmUtils.getListToJson(sdpService.getNationListByRegion(CmmDate.getYesterdayGMTDate())));


        return "admin/totalSDPByService/totalSDPByGraph";
    }

    @RequestMapping(value="/totalTHINQByService/totalTHINQByGraph", method = RequestMethod.GET)
    public String totalTHINQByGraph(Model model,@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate" , required = false) String endDate) throws Exception{

        String yesterDay = CmmDate.getYesterdayGMTDate().substring(0,6);
        String pEndDate = yesterDay.substring(0,4) + yesterDay.substring(4,6);
        String pStartDate = CmmDate.getBeforeYearMonth(yesterDay,5).substring(0,6);
        pStartDate = pStartDate.substring(0,4) + pStartDate.substring(4,6);

        if(!startDate.equals("") && startDate != null){
            pStartDate = startDate;
        }
        if(!endDate.equals("") && endDate != null){
            pEndDate = endDate;
        }

        // first totalCostList...
        model.addAttribute("thinqGraphDatasets",thinqService.getTotalCostList(pStartDate, pEndDate));
        model.addAttribute("thinqGraphLabel",thinqService.getChartLabels());
        model.addAttribute("thinqGraphNationList", CmmUtils.getListToJson(thinqService.getNationListByRegion(CmmDate.getYesterdayGMTDate())));


        return "admin/totalTHINQByService/totalTHINQByGraph";
    }



}
