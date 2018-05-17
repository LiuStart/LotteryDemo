package com.cn.cqsscfx.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/11.
 */

public class WeatherBean implements Serializable{
    //温度
    private String temp;
    //地点
    private String place;
    //desc
    private String desc;
    //更新时间
    private String updateTime;
    //湿度
    private String shidu;
    //pm
    private String pm;
    //良好

    public String getPmdesc() {
        return pmdesc;
    }

    public void setPmdesc(String pmdesc) {
        this.pmdesc = pmdesc;
    }

    private String pmdesc;
    //wind
    private String wind;
    //紫外线
    private String sundesc;
    //紫外线信息
    private String sunmsg;
    //感冒
    private String gm;
    //感冒信息
    private String gmmsg;
    //洗车
    private String xc;
    //洗车信息
    private String xcmsg;
    //穿衣
    private String cy;
    //穿衣信息
    private String cymsg;
    //运动
    private String yd;
    //级数
    private String windpower;

    public String getWindpower() {
        return windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getSundesc() {
        return sundesc;
    }

    public void setSundesc(String sundesc) {
        this.sundesc = sundesc;
    }

    public String getSunmsg() {
        return sunmsg;
    }

    public void setSunmsg(String sunmsg) {
        this.sunmsg = sunmsg;
    }

    public String getGm() {
        return gm;
    }

    public void setGm(String gm) {
        this.gm = gm;
    }

    public String getGmmsg() {
        return gmmsg;
    }

    public void setGmmsg(String gmmsg) {
        this.gmmsg = gmmsg;
    }

    public String getXc() {
        return xc;
    }

    public void setXc(String xc) {
        this.xc = xc;
    }

    public String getXcmsg() {
        return xcmsg;
    }

    public void setXcmsg(String xcmsg) {
        this.xcmsg = xcmsg;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public String getCymsg() {
        return cymsg;
    }

    public void setCymsg(String cymsg) {
        this.cymsg = cymsg;
    }

    public String getYd() {
        return yd;
    }

    public void setYd(String yd) {
        this.yd = yd;
    }

    public String getYdmsg() {
        return ydmsg;
    }

    public void setYdmsg(String ydmsg) {
        this.ydmsg = ydmsg;
    }

    //运动信息
    private String ydmsg;


}
