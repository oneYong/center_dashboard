package com.center.dashboard.mapper;

import com.center.dashboard.vo.BillingDataVO;
import com.center.dashboard.vo.FaultDataVO;
import com.center.dashboard.vo.TotalUserVO;
import com.center.dashboard.vo.TotalDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WYKIM on 2017-06-20.
 */
@Mapper
public interface DashBoardMapper {
    // 해당 날짜에 해당하는 회원수
    TotalDataVO getTotalCntByDate(@Param("date") String date) throws Exception;

    // 시작 날짜와 마지막 날짜 사이의 해당하는 회원수 데이터
    List<String> getCrtDate(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;
    List<String> getCntryCode_KIC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;
    List<TotalUserVO> getTotalUser_KIC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;


    List<String> getCntryCode_AIC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;
    List<TotalUserVO> getTotalUser_AIC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;

    List<String> getCntryCode_EIC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;
    List<TotalUserVO> getTotalUser_EIC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;


    List<String> getCntryCode_RUC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;
    List<TotalUserVO> getTotalUser_RUC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;

    // 해당 날짜에 해당하는 서비스 리스트
    List<TotalUserVO> getServiceList_KIC(@Param("date") String date) throws Exception;
    List<TotalUserVO> getServiceList_AIC(@Param("date") String date) throws Exception;
    List<TotalUserVO> getServiceList_EIC(@Param("date") String date) throws Exception;
    List<TotalUserVO> getServiceList_RUC(@Param("date") String date) throws Exception;

    // AWS TotalCost
    List<BillingDataVO> getAWSBillingTotalCost_CNS(@Param("dateList") List<String> dateList) throws Exception;

    List<BillingDataVO> getAWSServiceList_CNS(@Param("date") String date) throws Exception;
    List<BillingDataVO> getAWSProductList_CNS(@Param("date") String date) throws Exception;

    List<BillingDataVO> getAWSMonthlyProductCost_CNS(@Param("startMonth") String startMonth, @Param("endMonth") String endMonth) throws Exception;

    List<FaultDataVO> getFaultCountByService(FaultDataVO faultDataVO) throws Exception;
    List<FaultDataVO> getFaultCountByRegion(FaultDataVO faultDataVO) throws Exception;
    List<FaultDataVO> getFaultList(FaultDataVO faultDataVO) throws Exception;
    List<FaultDataVO> getFaultListMonthly(FaultDataVO faultDataVO) throws Exception;
}