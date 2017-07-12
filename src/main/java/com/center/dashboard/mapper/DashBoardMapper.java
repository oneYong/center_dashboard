package com.center.dashboard.mapper;

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
    TotalDataVO getTotalCntByDate(@Param("date") String date) throws Exception;
    List<String> getCrtDate_KIC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;
    List<String> getCntryCode_KIC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;
    List<TotalUserVO> getTotalUser_KIC(@Param("startDate") String startDate,@Param("endDate") String endDate ) throws Exception;
}