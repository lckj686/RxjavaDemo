package com.rxjava.sucer.master.bean;

/**
 * 中间数据
 * Created by sucer on 2016/4/9.
 */
public class MiddleData {
    String name;
    String param1;
    String param2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public MiddleData(String name, String param1, String param2) {

        this.name = name;
        this.param1 = param1;
        this.param2 = param2;
    }

    @Override
    public String toString() {
        return "MiddleData{" +
                "name='" + name + '\'' +
                ", param1='" + param1 + '\'' +
                ", param2='" + param2 + '\'' +
                '}';
    }
}
