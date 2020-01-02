package com.lottery.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottery.common.base.Res;
import com.lottery.common.base.Status;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.LotteryUtil;
import com.lottery.mapper.TLotteryMapper;
import com.lottery.pojo.TLotteryDoubleColorBallInfoBean;
import com.lottery.pojo.TLotteryDoubleColorBallMainBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@PropertySource("classpath:lottery_service.properties")
public class LotteryTask {
    private Logger logger = LoggerFactory.getLogger(LotteryTask.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    private TLotteryMapper tLotteryMapper;
    /**
    *@Title doubleColorTask
    *@Description TODO  双色球
    *@Param []
    *@Return void
    *@Author jingmh
    *@Date 2019/9/23 16:35
    */
    @Scheduled(cron="${lottery.double.color.ball.task.cron}")
    public void doubleColorTask(){
        //1.查询最新条记录，如果数据，开始时间默认： 2013-01-01 （官网最早）
        TLotteryDoubleColorBallMainBean lastLotteryMainBean =tLotteryMapper.queryLatestDoubleColorBallMain();

        Map<String, Object> map = new HashMap<String, Object>();
        //1.双色球
        map.put("name", "ssq");
        //map.put("issueCount", "1"); //最近多少期（最大100）
        //按期号
        //map.put("issueStart", "2013001");
        //map.put("issueEnd", "2013002");
        //按时间 2013-01-01 官网最早
        String startDate="2013-01-01";
        if(lastLotteryMainBean != null){
            startDate=lastLotteryMainBean.getfLotteryTime();
        }
        map.put("dayStart", startDate);
        String currentDate= DateUtil.format(new Date());
        System.out.println(currentDate);
        map.put("dayEnd", currentDate);
        //初始化页码 1
        map.put("pageNo", 1);//页码

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String mapString = objectMapper.writeValueAsString(map);
            Res res= LotteryUtil.getDoubleColorBall(mapString);
            System.out.println("res:"+JSONObject.toJSONString(res));
            if(Status.SUCCESS == res.getStatus() && res.getData() != null ){
                Map resMap= (Map) res.getData();
                String state=String.valueOf(resMap.get("state"));
                System.out.println("state = " + state);
                if(StringUtils.equals("0",state) && resMap.get("pageCount") !=null  ){
                    //有数据
                    int pageCount= Integer.valueOf(String.valueOf(resMap.get("pageCount")));

                    System.out.println("全部=================================pageCount:"+pageCount);
                    for (int i=1;i<=pageCount;i++){
                        System.out.println("开始=================================i:"+i);

                        map.put("pageNo", i);//页码
                        mapString = objectMapper.writeValueAsString(map);
                        Res resResult= LotteryUtil.getDoubleColorBall(mapString);
                        Map mapResult= (Map) resResult.getData();

                        //组装插入数据 主表、子表
                        List<TLotteryDoubleColorBallMainBean> mainList=new ArrayList<>();
                        List<TLotteryDoubleColorBallInfoBean> infoList=new ArrayList<>();

                        if(mapResult.get("result") != null){

                            JSONArray resultJsonArray= (JSONArray) mapResult.get("result");
                            for(int j=0; j<resultJsonArray.size(); j++){
                                JSONObject resultObject = resultJsonArray.getJSONObject(j);

                                TLotteryDoubleColorBallMainBean lotteryMain=new TLotteryDoubleColorBallMainBean();
                                String fPhaseNum= (String) resultObject.get("code");
                                lotteryMain.setfPhaseNum(fPhaseNum); // 期号
                                lotteryMain.setfPhaseName( (String) resultObject.get("name") ); //名称
                                String dateStr= (String) resultObject.get("date");
                                String fLotteryTime=dateStr.substring(0,dateStr.lastIndexOf("("));
                                lotteryMain.setfLotteryTime(fLotteryTime); //开奖日期
                                lotteryMain.setfLotteryTimneWeek( (String) resultObject.get("week") ); //周几
                                lotteryMain.setfSales( (String) resultObject.get("sales") ); //总销售额（元）
                                lotteryMain.setfPoolmoney((String) resultObject.get("poolmoney") ); //奖池（元）
                                lotteryMain.setfContent( (String) resultObject.get("content") ); //一等奖描述

                                String redStr =(String) resultObject.get("red");
                                lotteryMain.setfRedOne(redStr.split(",")[0]); //红1
                                lotteryMain.setfRedTwo(redStr.split(",")[1]); //红2
                                lotteryMain.setfRedThree(redStr.split(",")[2]); //红3
                                lotteryMain.setfRedFour(redStr.split(",")[3]); //红4
                                lotteryMain.setfRedFive(redStr.split(",")[4]); //红5
                                lotteryMain.setfRedSix(redStr.split(",")[5]); //红6
                                String blueBall=(String) resultObject.get("blue");
                                lotteryMain.setfBlue(blueBall); //篮球
                                //所有球
                                lotteryMain.setfBalls(redStr+","+blueBall);
                                mainList.add(lotteryMain);
                                //子表

                                JSONArray prizegradesArray= (JSONArray) resultObject.get("prizegrades");

                                for(int k=0; k<prizegradesArray.size(); k++){
                                    JSONObject prizegradesBean =prizegradesArray.getJSONObject(k);
                                    String typemoney= (String) prizegradesBean.get("typemoney");
                                    String typenum= (String) prizegradesBean.get("typenum");
                                    if(StringUtils.isNotBlank(typemoney) && StringUtils.isNotBlank(typenum) ){
                                        TLotteryDoubleColorBallInfoBean lotteryInfo = new TLotteryDoubleColorBallInfoBean();
                                        Integer type= (Integer) prizegradesBean.get("type");
                                        String typeStr=type.toString();
                                        lotteryInfo.setfId(fPhaseNum+"-"+typeStr);//子表主键
                                        lotteryInfo.setfPhaseNum(fPhaseNum);//期号
                                        lotteryInfo.setfType(String.valueOf(typeStr));//几等奖
                                        lotteryInfo.setfTypeMoney(typemoney);
                                        lotteryInfo.setfTypeNum(typenum);
                                        //塞入
                                        infoList.add(lotteryInfo);
                                    }
                                }
                            }
                            //插入
                            tLotteryMapper.addDoubleColorBallMain(mainList);
                            tLotteryMapper.addDoubleColorBallInfo(infoList);
                        }


                        System.out.println("结束=================================i:"+i);
                    }



                }else{
                    logger.error("双色球定时任务执行请求官网失败，resMap:"+ JSONObject.toJSONString(resMap));
                }




            }


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        System.out.println("-------------------HELLO");
    }


    public static void main(String[] args) {
        String dateStr="2019-09-24(二)";
        String fLotteryTime=dateStr.substring(0,dateStr.lastIndexOf("("));
        System.out.println(fLotteryTime);
    }

}
