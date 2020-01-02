package com.lottery.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottery.common.base.Res;
import com.lottery.common.base.Status;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
*@ClassName LotteryUtil
*@Description TODO 彩票Util
*@Author jingmh
*@Date 2019/9/23 17:34
*/
public class LotteryUtil {

    private static Logger logger = LoggerFactory.getLogger(LotteryUtil.class);

    //双色球 ;双色球官网数据从2013001(2013-01-01) 开始
    public static Res  getDoubleColorBall(String parmStr){

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(mediaType, parmStr);
        Request request = new Request.Builder()
                .url("http://www.cwl.gov.cn/cwl_admin/kjxx/findDrawNotice")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("referer", "http://www.cwl.gov.cn/")
                .addHeader("host", "www.cwl.gov.cn")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "a4da8c33-3db7-9034-e624-3ca68f443d81")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String cc=response.body().string();
                response.body().close();
                //Map result=JsonMapUtil.JsonStrTOMap(response.body().toString());
                logger.info("-----------------response.body().string():"+cc);
                Map mapTypes = JSON.parseObject(cc);
                String state=String.valueOf(mapTypes.get("state"));
                String message=String.valueOf(mapTypes.get("message"));

                JSONArray resultJsonArray= (JSONArray) mapTypes.get("result");
                System.out.println("------------"+resultJsonArray);

                logger.info("state:"+state+"  &message:"+message);



                for (Object obj : mapTypes.keySet()){
                    System.out.println("key为："+obj+"值为："+mapTypes.get(obj));
                }
                if (!StringUtils.equals("0", state)) {
                    return new Res(Status.SUCCESS,"操作失败", message);
                }
                return new Res(Status.SUCCESS,"操作成功", mapTypes);


            } else {
                return new Res(Status.SUCCESS,"请求服务失败,没有查到数据");

            }

        } catch (IOException e) {
            e.printStackTrace();
            return new Res(Status.ERROR,"请求服务错误");
        }

    }





    public static void main(String[] args) {
        //"{\"name\":\"ssq\",\"issueCount\":\"1\"}"
        Map<String, Object> map = new HashMap<String, Object>();
        //1.双色球 c
        map.put("name", "ssq");
        //最近多少期（最大100）
        //map.put("issueCount", "1");
        //按期号
        //map.put("issueStart", "2013001");
        //map.put("issueEnd", "2013002");
        //按时间 2013-01-01 官网最早
        map.put("dayStart", "2013-01-01");

        String currentDate=DateUtil.format(new Date());

        map.put("dayEnd", currentDate);
        map.put("pageNo", 1);//页码
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String mapString = objectMapper.writeValueAsString(map);
            LotteryUtil.getDoubleColorBall(mapString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }



   /* public static void main(String[] args) {
        String parmStr = "{\"dayStart\":\"2019-01-01\",\"pageNo\":1,\"name\":\"ssq\",\"dayEnd\":\"2019-12-19\"}";
        getDoubleColorBall(parmStr);
    }*/
}



