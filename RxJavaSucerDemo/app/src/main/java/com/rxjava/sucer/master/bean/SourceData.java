package com.rxjava.sucer.master.bean;

/**
 * 原始数据
 * Created by sucer on 2016/4/9.
 */
public class SourceData {
    String name;
    String param1;
    String param2;
    String param3;

    public SourceData(String name, String param1, String param2, String param3) {

        this.name = name;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

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

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    @Override
    public String toString() {
        return "SourceData{" +
                "name='" + name + '\'' +
                ", param1='" + param1 + '\'' +
                ", param2='" + param2 + '\'' +
                ", param3='" + param3 + '\'' +
                '}';
    }
}
