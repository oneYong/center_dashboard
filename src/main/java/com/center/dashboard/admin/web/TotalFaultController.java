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
public class TotalFaultController {

    @Autowired
    private DashBoardMapper dashBoardMapper;


    @RequestMapping(value="/totalFaultByRegion", method = RequestMethod.GET)
    public String totalFaultByRegion(Model model, HttpSession session, HttpServletRequest request) throws Exception{
        String user_id = (String) request.getSession().getAttribute("user_id");

        if(user_id != null){
            return "admin/totalFaultByRegion";
        }else{
            return "admin/login";
        }
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
