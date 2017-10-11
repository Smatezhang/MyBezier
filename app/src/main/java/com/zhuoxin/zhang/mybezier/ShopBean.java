package com.zhuoxin.zhang.mybezier;

/**
 * Created by Administrator on 2017/9/19.
 */

public class ShopBean {

    private String title;
    private String price;
    private int count;

    public ShopBean(String title, String price, int count) {
        this.title = title;
        this.price = price;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}