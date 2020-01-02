package com.lottery.mapper;

import com.lottery.common.base.PageBean;
import com.lottery.pojo.TLotteryDoubleColorBallInfoBean;
import com.lottery.pojo.TLotteryDoubleColorBallMainBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TLotteryMapper {


    //TODO 双色球--START=============================================================================================
    //主-新增
    int addDoubleColorBallMain(@Param(value = "list") List<TLotteryDoubleColorBallMainBean> list);

    //主-根据ID(期号)查询
    TLotteryDoubleColorBallMainBean queryDoubleColorBallMainByPrimaryKey(@Param("fPhaseNum") String fPhaseNum);

    //主：查询最新一条数据,用于定时任务执行条件
    TLotteryDoubleColorBallMainBean queryLatestDoubleColorBallMain();


    //主-分页查询
    Integer queryDoubleColorBallPageTotal(TLotteryDoubleColorBallMainBean tLotteryDoubleColorBallMain);
    List<TLotteryDoubleColorBallMainBean> queryDoubleColorBallPageList(
            @Param(value = "item") TLotteryDoubleColorBallMainBean tLotteryDoubleColorBallMain,
            @Param("pager")PageBean<TLotteryDoubleColorBallMainBean> pager);



    //子-新增
    int addDoubleColorBallInfo(@Param(value = "list") List<TLotteryDoubleColorBallInfoBean> list);
    //子-根据(期号)查询详情
    List<TLotteryDoubleColorBallInfoBean> queryDoubleColorBallInfoByfPhaseNum(@Param("fPhaseNum") String fPhaseNum);

    //预算 ballType 1-6：红1-红6；7：蓝
    //根据日期条件查询--历史count
    int queryDoubleHistoryCountByBallType(@Param(value = "startDate") String startDate,
                                         @Param(value = "endDate") String endDate);
    //根据日期条件查询--历史数据（红1...蓝）
    String queryDoubleHistoryDataByBallType(@Param(value = "ballType") Integer ballType,
                                            @Param(value = "startDate") String startDate,
                                            @Param(value = "endDate") String endDate);



    //TODO 双色球--START=============================================================================================






}
