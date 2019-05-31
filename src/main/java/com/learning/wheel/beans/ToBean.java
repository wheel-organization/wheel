package com.learning.wheel.beans;

import java.util.List;
import java.util.Map;

/**
 * @author hangwei
 * @date 2019/5/14 下午 3:08
 */
public class ToBean {
    private String name;
    private int age;
    private String address;
    private String idno;
    private double money;
    private List<String> freid;
    private Map<String,Integer> freidMap;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public List<String> getFreid() {
        return freid;
    }

    public void setFreid(List<String> freid) {
        this.freid = freid;
    }

    public Map<String, Integer> getFreidMap() {
        return freidMap;
    }

    public void setFreidMap(Map<String, Integer> freidMap) {
        this.freidMap = freidMap;
    }

}
