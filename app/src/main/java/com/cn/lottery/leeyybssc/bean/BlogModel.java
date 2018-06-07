package com.cn.lottery.leeyybssc.bean;

/**
 * Created by Administrator on 2018/5/23.
 */

public class BlogModel {
    private String author;
    private String name;
    private String image;
    private String targetUrl;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "BlogModel{" +
                "author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                '}';
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
