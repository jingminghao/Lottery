package com.lottery.pojo;

import java.io.Serializable;
import java.util.Date;


/**
*@ClassName TLotteryDoubleColorBallInfo
*@Description TODO 双色球子表
*@Author jingmh
*@Date 2019/9/25 15:48
*/
public class TLotteryDoubleColorBallInfoBean implements Serializable {


    private static final long serialVersionUID = -1662109502084166207L;
    /**
     * 主键
     */
    private String fId;

    /**
     * 期号（主表id）
     */
    private String fPhaseNum;

    /**
     * x等奖
     */
    private String fType;

    /**
     * 中奖金额(元)
     */
    private String fTypeMoney;

    /**
     * 中奖人数
     */
    private String fTypeNum;

    /**
     * 操作时间
     */
    private Date fCreateTime;

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getfPhaseNum() {
        return fPhaseNum;
    }

    public void setfPhaseNum(String fPhaseNum) {
        this.fPhaseNum = fPhaseNum;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getfTypeMoney() {
        return fTypeMoney;
    }

    public void setfTypeMoney(String fTypeMoney) {
        this.fTypeMoney = fTypeMoney;
    }

    public String getfTypeNum() {
        return fTypeNum;
    }

    public void setfTypeNum(String fTypeNum) {
        this.fTypeNum = fTypeNum;
    }

    public Date getfCreateTime() {
        return fCreateTime;
    }

    public void setfCreateTime(Date fCreateTime) {
        this.fCreateTime = fCreateTime;
    }

    public TLotteryDoubleColorBallInfoBean() {
    }

    @Override
    public String toString() {
        return "TLotteryDoubleColorBallInfoBean{" +
                "fId='" + fId + '\'' +
                ", fPhaseNum='" + fPhaseNum + '\'' +
                ", fType='" + fType + '\'' +
                ", fTypeMoney='" + fTypeMoney + '\'' +
                ", fTypeNum='" + fTypeNum + '\'' +
                ", fCreateTime=" + fCreateTime +
                '}';
    }
}