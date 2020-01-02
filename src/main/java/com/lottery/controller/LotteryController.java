package com.lottery.controller;

import com.lottery.common.base.*;
import com.lottery.mapper.TLotteryMapper;
import com.lottery.pojo.TLotteryDoubleColorBallInfoBean;
import com.lottery.pojo.TLotteryDoubleColorBallMainBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;


@Controller
public class LotteryController {

    private static final Logger logger =Logger.getLogger(LotteryController.class);

    @Autowired
    private TLotteryMapper tLotteryMapper;

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }



    @RequestMapping(value = "/doubleColorBall/list/page")
    public String doubleListPage(){
        return "lottery/double_color_ball_list";
    }

    /**
    *@Title doubleListData
    *@Description TODO 双色List
    *@Param [data]
    *@Return com.lottery.common.base.Res
    *@Author jingmh
    *@Date 2019/9/26 15:50
    */
    @RequestMapping(value = "/doubleColorBall/list", method = RequestMethod.POST)
    @ResponseBody
    public Layui doubleListData(TLotteryDoubleColorBallMainBean bean,PageBean<TLotteryDoubleColorBallMainBean> pager) throws MmoException {
        try {
            pager.getStart();
            int totalCount=tLotteryMapper.queryDoubleColorBallPageTotal(bean);
            List<TLotteryDoubleColorBallMainBean> list=tLotteryMapper.queryDoubleColorBallPageList(bean,pager);
            return Layui.data(totalCount, list);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询双色列表错误");
            throw new MmoException(Status.ERROR, "查询双色列表错误");
        }
    }

    /**
     *@Title doubleListData
     *@Description TODO 双色详情
     *@Param [data]
     *@Return com.lottery.common.base.Res
     *@Author jingmh
     *@Date 2019/9/26 15:50
     */
    @RequestMapping(value = "/doubleColorBall/info/page")
    public String doubleColotInfoPage(TLotteryDoubleColorBallMainBean bean, Model model) {
        model.addAttribute("fPhaseNum",bean.getfPhaseNum());
       return "lottery/double_color_ball_info";
    }

    @RequestMapping(value = "/doubleColorBall/info/data")
    @ResponseBody
    public Layui doubleColotInfoData(TLotteryDoubleColorBallMainBean bean) {
        try {
            List<TLotteryDoubleColorBallInfoBean> list=tLotteryMapper.queryDoubleColorBallInfoByfPhaseNum(bean.getfPhaseNum());
            return Layui.data(list.size(), list);
        }catch (Exception e){
            e.printStackTrace();
            return Layui.data(0, null);
        }
    }
    /**
    *@Title doubleColotPrestorePage
    *@Description TODO 双色预存
    *@Param [bean, model]
    *@Return java.lang.String
    *@Author jingmh
    *@Date 2019/10/10 9:50
    */
    @RequestMapping(value = "/doubleColorBall/prestore/page")
    public String doubleColotPrestorePage() {
        return "lottery/double_color_ball_prestore";
    }


    /**
     *@Title doubleColotPrestoreLastData
     *@Description TODO 双色最新数据
     *@Param [bean, model]
     *@Return java.lang.String
     *@Author jingmh
     *@Date 2019/10/10 9:50
     */
    @RequestMapping(value = "/doubleColorBall/last/data")
    @ResponseBody
    public TLotteryDoubleColorBallMainBean doubleColotPrestoreLastData() {
        return tLotteryMapper.queryLatestDoubleColorBallMain();
    }



}



