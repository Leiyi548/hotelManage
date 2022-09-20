package com.guest.pojo.po;

public class Reserver {
    String reserver_name;//预订人姓名
    String reserver_tel;//预订人号码
    Integer eater_num;//就餐人数
    Integer desk_num;//就餐餐桌号
    public String getResever_name() {
        return reserver_name;
    }

    public void setResever_name(String resever_name) {
        this.reserver_name = resever_name;
    }

    public String getReserver_tel() {
        return reserver_tel;
    }

    public void setReserver_tel(String reserver_tel) {
        this.reserver_tel = reserver_tel;
    }

    public Integer getEater_num() {
        return eater_num;
    }

    public void setEater_num(Integer eater_num) {
        this.eater_num = eater_num;
    }

    public Integer getDesk_num() {
        return desk_num;
    }

    public void setDesk_num(Integer desk_num) {
        this.desk_num = desk_num;
    }

}
