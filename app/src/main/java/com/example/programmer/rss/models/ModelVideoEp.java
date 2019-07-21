package com.example.programmer.rss.models;

public class ModelVideoEp {
    private String url;
    private String name;
    private String descerption;
    private int img;

    public ModelVideoEp() {
    }

    public ModelVideoEp(int img) {
        this.img = img;
    }

    public ModelVideoEp(String url, String name, String descerption, int img) {
        this.url = url;
        this.name = name;
        this.descerption = descerption;
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescerption() {
        return descerption;
    }

    public void setDescerption(String descerption) {
        this.descerption = descerption;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

}
