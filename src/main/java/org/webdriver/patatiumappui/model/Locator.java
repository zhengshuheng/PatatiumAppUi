package org.webdriver.patatiumappui.model;

import java.io.Serializable;

/**
 * Created by zhengshuheng on 2016/12/20.
 */
public class Locator implements Serializable {
    private String name;
    private String desc;
    private String type;
    private String value;
    private int timeOut;

    @Override
    public String toString() {
        return "Locator{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", timeOut=" + timeOut +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public Locator() {
    }
}
