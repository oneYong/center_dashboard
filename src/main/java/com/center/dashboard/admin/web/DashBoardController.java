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
    private KICService kicService;

    @Autowired
    private AICService aicService;

    @Autowired
    private EICService eicService;

    @Autowired
    private RUCService rucService;

    @Autowired
    private AWSBillingService awsBillingService;

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

    @RequestMapping(value="/totalFaultByRegion", method = RequestMethod.GET)
    public String totalFaultByRegion(Model model, HttpSession session, HttpServletRequest request) throws Exception{
        String user_id = (String) request.getSession().getAttribute("user_id");

        if(user_id != null){
            return "admin/totalFaultByRegion";
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

    @RequestMapping(value="/totalFaultByRegion/totalFaultByGraph", method = RequestMethod.GET)
    public String totalFaultByGraph(Model model,@RequestParam(value = "startMonth", required = false) String startMonth, @RequestParam(value = "endMonth" , required = false) String endMonth) throws Exception{

        String standardDate = CmmDate.getTodayGMTDate().substring(0,4) + "." + CmmDate.getTodayGMTDate().substring(4,6)+ "." + CmmDate.getTodayGMTDate().substring(6);
        String standardMonth = CmmDate.getTodayGMTDate().substring(0,4) + "-" + CmmDate.getTodayGMTDate().substring(4,6);

        String paramMonth = CmmDate.getTodayGMTDate().substring(0,4) + "-" + CmmDate.getTodayGMTDate().substring(4,6);
        FaultDataVO faultDataVO = new FaultDataVO();
        faultDataVO.setMonthInfo(paramMonth);

        int faultTotalCount = 0;
        List<FaultDataVO> list = dashBoardMapper.getFaultCountByRegion(faultDataVO);
        for(int i = 0; i < list.size(); i++){
            FaultDataVO temp = list.get(i);
            faultTotalCount += temp.getFaultCount();
        }
        int faultDayDiff =  dashBoardMapper.getFaultDayDiff();

        model.addAttribute("faultDayDiff",faultDayDiff);
        model.addAttribute("standardDate",standardDate);
        model.addAttribute("standardMonth",standardMonth);
        model.addAttribute("faultList", CmmUtils.getListToJson(dashBoardMapper.getFaultList(new FaultDataVO())));
        model.addAttribute("faultCountByService", CmmUtils.getListToJson(dashBoardMapper.getFaultCountByService(faultDataVO)));
        model.addAttribute("faultCountByRegion", CmmUtils.getListToJson(dashBoardMapper.getFaultCountByRegion(faultDataVO)));
        model.addAttribute("faultTotalCount", faultTotalCount);
        return "admin/totalFaultByRegion/totalFaultByGraph";
    }

    @RequestMapping(value="/totalFaultByRegion/totalFaultByList", method = RequestMethod.GET)
    public String totalFaultByList(Model model,@RequestParam(value = "startMonth", required = false) String startMonth, @RequestParam(value = "endMonth" , required = false) String endMonth) throws Exception{

        FaultDataVO faultDataVO = new FaultDataVO();
        faultDataVO.setStartMonth(startMonth);
        faultDataVO.setEndMonth(endMonth);

        model.addAttribute("faultListMonthly", CmmUtils.getListToJson(dashBoardMapper.getFaultListMonthly(faultDataVO)));
        return "admin/totalFaultByRegion/totalFaultByList";
    }

    @RequestMapping(value="/totalFaultByRegion/totalFaultByList/getFaultList", method = RequestMethod.GET
            , produces = "application/json; charset=utf8")
    @ResponseBody
    public String getFaultList(FaultDataVO faultDataVO) throws Exception{

        return CmmUtils.getListToJson(dashBoardMapper.getFaultList(faultDataVO));
    }
}
