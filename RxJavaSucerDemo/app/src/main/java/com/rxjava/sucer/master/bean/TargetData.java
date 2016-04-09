package com.rxjava.sucer.master.bean;

/**
 * 目标数据
 * Created by sucer on 2016/4/9.
 */
public class TargetData {
    String name;
    String param1;

    public TargetData(String name, String param1) {

        this.name = name;
        this.param1 = param1;
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

    @Override
    public String toString() {
        return "TargetData{" +
                "name='" + name + '\'' +
                ", param1='" + param1 + '\'' +
                '}';
    }
}
