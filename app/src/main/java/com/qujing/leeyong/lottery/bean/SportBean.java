package com.qujing.leeyong.lottery.bean;

/**
 * Created by Administrator on 2018/4/26.
 */

public class SportBean {
    private String team1Name;
    private String team2Name;
    private String team1;
    private String team2;
    private String team1Score;
    private String team2Score;
    private String sportTime;
    private String playOff;
    private String videoType;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(String team1Score) {
        this.team1Score = team1Score;
    }

    public String getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(String team2Score) {
        this.team2Score = team2Score;
    }

    public String getSportTime() {
        return sportTime;
    }

    public void setSportTime(String sportTime) {
        this.sportTime = sportTime;
    }

    public String getPlayOff() {
        return playOff;
    }

    public void setPlayOff(String playOff) {
        this.playOff = playOff;
    }

    @Override
    public String toString() {
        return "SportBean{" +
                "team1Name='" + team1Name + '\'' +
                ", team2Name='" + team2Name + '\'' +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", team1Score='" + team1Score + '\'' +
                ", team2Score='" + team2Score + '\'' +
                ", sportTime='" + sportTime + '\'' +
                ", playOff='" + playOff + '\'' +
                '}';
    }

    public SportBean(String team1Name,String team1,String team1Score,String team2Name,String team2,String team2Score,String sportTime,String playOff,String videoType,String status) {
        this.team1Name=team1Name;
        this.team1=team1;
        this.team1Score=team1Score;
        this.team2Name=team2Name;
        this.team2=team2;
        this.team2Score=team2Score;
        this.sportTime=sportTime;
        this.playOff=playOff;
        this.videoType=videoType;
        this.status=status;
    }
}
