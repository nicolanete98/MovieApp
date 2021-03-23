package com.example.movieapp;
import java.io.Serializable;

public class ItemList implements Serializable {

    private String tit;
    private String des;
    private String img;

    public ItemList(String tit, String des, String img) {
        this.tit = tit;
        this.des = des;
        this.img = img;
    }
    public ItemList(){

    }
    public void setTit(String tit) {
        this.tit = tit;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTit() {
        return tit;
    }

    public String getDes() {
        return des;
    }

    public String getImg() {
        return img;
    }
}
