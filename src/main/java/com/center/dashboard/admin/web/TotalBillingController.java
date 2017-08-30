package com.center.dashboard.admin.web;

import com.center.dashboard.admin.service.AWSBillingService;
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
public class TotalBillingController {


    @Autowired
    private AWSBillingService awsBillingService;


    @RequestMapping(value="/totalBillingByAWS", method = RequestMethod.GET)
    public String totalBillingByAWS(Model model, HttpSession session, HttpServletRequest request) throws Exception{
        String user_id = (String) request.getSession().getAttribute("user_id");
        String yesterDay = CmmDate.getYesterdayGMTDate().substring(0,6);
        String endDate = yesterDay.substring(0,4) + yesterDay.substring(4,6);
        String startDate = CmmDate.getBeforeYearMonth(yesterDay,5).substring(0,6);
        startDate = startDate.substring(0,4) + startDate.substring(4,6);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);

        if(user_id != null){
            return "admin/totalBillingByAWS";
        }else{
            return "admin/login";
        }
    }

    @RequestMapping(value="/totalBillingByAWS/totalBillingByGraph", method = RequestMethod.GET)
    public String totalBillingByGraph(Model model,@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate" , required = false) String endDate) throws Exception{

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

        String pYesterDate = CmmDate.getYesterdayGMTDate().substring(0,4) + "-" + CmmDate.getYesterdayGMTDate().substring(4,6) + "-" + CmmDate.getYesterdayGMTDate().substring(6);
        // first totalCostList...
        model.addAttribute("awsGraphDatasets",awsBillingService.getTotalCostList(startDate, endDate));
        model.addAttribute("awsGraphLabel",awsBillingService.getChartLabels());
        model.addAttribute("awsGraphServiceList", CmmUtils.getListToJson(awsBillingService.getServiceList(pYesterDate)));
        model.addAttribute("awsGraphProductList", CmmUtils.getListToJson(awsBillingService.getProductList(pYesterDate)));

        return "admin/totalBillingByAWS/totalBillingByGraph";
    }

    @RequestMapping(value="/totalBillingByAWS/totalBillingByList", method = RequestMethod.GET)
    public String totalBillingByList(Model model,@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate" , required = false) String endDate) throws Exception{

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

        model.addAttribute("awsListMonthlyProductList", CmmUtils.getListToJson(awsBillingService.getMonthlyProductList(pStartDate,pEndDate)));

        return "admin/totalBillingByAWS/totalBillingByList";
    }


}
