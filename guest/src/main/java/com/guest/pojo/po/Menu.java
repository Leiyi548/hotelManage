package com.guest.pojo.po;

public class Menu {
    String dish_id;//菜编号
    String dish_name;//菜名
    float dish_price;//菜价

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public float getDish_price() {
        return dish_price;
    }

    public void setDish_price(float dish_price) {
        this.dish_price = dish_price;
    }
}
