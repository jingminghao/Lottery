package com.lottery.pojo;

import com.lottery.common.base.PageBean;

import java.io.Serializable;

import java.util.Date;

/**
*@Title
*@Description TODO 双色球主表
*@Param
*@Return
*@Author jingmh
*@Date 2019/9/25 15:31
*/
public class TLotteryDoubleColorBallMainBean implements Serializable {

    private static final long serialVersionUID = -5681013366790187172L;
    /**
     * 期号
     */
    private String fPhaseNum;

    /**
     * 名称
     */
    private String fPhaseName;

    /**
     * 红1
     */
    private String fRedOne;

    /**
     * 红2
     */
    private String fRedTwo;

    /**
     * 红3
     */
    private String fRedThree;

    /**
     * 红4
     */
    private String fRedFour;

    /**
     * 红5
     */
    private String fRedFive;

    /**
     * 红6
     */
    private String fRedSix;

    /**
     * 蓝1
     */
    private String fBlue;

    /**
     * 所有球，红区+蓝球
     * 03,12,14,17,23,27,07
     */
    private String fBalls;

    /**
     * 开奖日期
     */
    private String fLotteryTime;

    /**
     * 开奖日期星期几
     */
    private String fLotteryTimneWeek;

    /**
     * 总销售额（元）
     */
    private String fSales;

    /**
     * 奖池（元）
     */
    private String fPoolmoney;

    /**
     * 一等奖描述
     */
    private String fContent;

    /**
     * 操作时间
     */
    private Date fCreateTime;


    /**
     * 查询条件——开奖开始时间
     */
    private String fLotteryTimeStart;
    /**
     * 查询条件——开奖结束时间
     */
    private String fLotteryTimeEnd;

    /**
     * 序号
     */
    private String rowno;

    public String getfBalls() {
        return fBalls;
    }

    public void setfBalls(String fBalls) {
        this.fBalls = fBalls;
    }

    public String getRowno() {
        return rowno;
    }

    public void setRowno(String rowno) {
        this.rowno = rowno;
    }

    public String getfPhaseNum() {
        return fPhaseNum;
    }

    public void setfPhaseNum(String fPhaseNum) {
        this.fPhaseNum = fPhaseNum;
    }

    public String getfPhaseName() {
        return fPhaseName;
    }

    public void setfPhaseName(String fPhaseName) {
        this.fPhaseName = fPhaseName;
    }

    public String getfRedOne() {
        return fRedOne;
    }

    public void setfRedOne(String fRedOne) {
        this.fRedOne = fRedOne;
    }

    public String getfRedTwo() {
        return fRedTwo;
    }

    public void setfRedTwo(String fRedTwo) {
        this.fRedTwo = fRedTwo;
    }

    public String getfRedThree() {
        return fRedThree;
    }

    public void setfRedThree(String fRedThree) {
        this.fRedThree = fRedThree;
    }

    public String getfRedFour() {
        return fRedFour;
    }

    public void setfRedFour(String fRedFour) {
        this.fRedFour = fRedFour;
    }

    public String getfRedFive() {
        return fRedFive;
    }

    public void setfRedFive(String fRedFive) {
        this.fRedFive = fRedFive;
    }

    public String getfBlue() {
        return fBlue;
    }

    public void setfBlue(String fBlue) {
        this.fBlue = fBlue;
    }

    public String getfLotteryTime() {
        return fLotteryTime;
    }

    public void setfLotteryTime(String fLotteryTime) {
        this.fLotteryTime = fLotteryTime;
    }

    public String getfLotteryTimneWeek() {
        return fLotteryTimneWeek;
    }

    public void setfLotteryTimneWeek(String fLotteryTimneWeek) {
        this.fLotteryTimneWeek = fLotteryTimneWeek;
    }

    public String getfSales() {
        return fSales;
    }

    public void setfSales(String fSales) {
        this.fSales = fSales;
    }

    public String getfPoolmoney() {
        return fPoolmoney;
    }

    public void setfPoolmoney(String fPoolmoney) {
        this.fPoolmoney = fPoolmoney;
    }

    public String getfContent() {
        return fContent;
    }

    public void setfContent(String fContent) {
        this.fContent = fContent;
    }

    public Date getfCreateTime() {
        return fCreateTime;
    }

    public void setfCreateTime(Date fCreateTime) {
        this.fCreateTime = fCreateTime;
    }

    public String getfLotteryTimeStart() {
        return fLotteryTimeStart;
    }

    public void setfLotteryTimeStart(String fLotteryTimeStart) {
        this.fLotteryTimeStart = fLotteryTimeStart;
    }

    public String getfLotteryTimeEnd() {
        return fLotteryTimeEnd;
    }

    public void setfLotteryTimeEnd(String fLotteryTimeEnd) {
        this.fLotteryTimeEnd = fLotteryTimeEnd;
    }

    public String getfRedSix() {
        return fRedSix;
    }

    public void setfRedSix(String fRedSix) {
        this.fRedSix = fRedSix;
    }

    public TLotteryDoubleColorBallMainBean() {
    }

    @Override
    public String toString() {
        return "TLotteryDoubleColorBallMainBean{" +
                "fPhaseNum='" + fPhaseNum + '\'' +
                ", fPhaseName='" + fPhaseName + '\'' +
                ", fRedOne='" + fRedOne + '\'' +
                ", fRedTwo='" + fRedTwo + '\'' +
                ", fRedThree='" + fRedThree + '\'' +
                ", fRedFour='" + fRedFour + '\'' +
                ", fRedFive='" + fRedFive + '\'' +
                ", fRedSix='" + fRedSix + '\'' +
                ", fBlue='" + fBlue + '\'' +
                ", fBalls='" + fBalls + '\'' +
                ", fLotteryTime='" + fLotteryTime + '\'' +
                ", fLotteryTimneWeek='" + fLotteryTimneWeek + '\'' +
                ", fSales='" + fSales + '\'' +
                ", fPoolmoney='" + fPoolmoney + '\'' +
                ", fContent='" + fContent + '\'' +
                ", fCreateTime=" + fCreateTime +
                ", fLotteryTimeStart='" + fLotteryTimeStart + '\'' +
                ", fLotteryTimeEnd='" + fLotteryTimeEnd + '\'' +
                '}';
    }
}
