package com.center.dashboard.mapper;

import com.center.dashboard.vo.ChartDataVO;
import com.center.dashboard.vo.TotalDataVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by WYKIM on 2017-06-20.
 */
@Mapper
public interface DashBoardMapper {
    TotalDataVO getTotalCntByDate(String date) throws Exception;
    List<ChartDataVO> getDefaultTotalCnt_KIC() throws Exception;
}