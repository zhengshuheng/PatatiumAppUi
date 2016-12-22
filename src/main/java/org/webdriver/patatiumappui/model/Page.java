package org.webdriver.patatiumappui.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by zhengshuheng on 2016/12/20.
 */
public class Page implements Serializable{
    private String pageName;
    private String pageDesc;
    private String value;
    private HashMap<String,Locator>locators;

    @Override
    public String toString() {
        return "Page{" +
                "pageName='" + pageName + '\'' +
                ", pageDesc='" + pageDesc + '\'' +
                ", value='" + value + '\'' +
                ", locators=" + locators +
                '}';
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageDesc() {
        return pageDesc;
    }

    public void setPageDesc(String pageDesc) {
        this.pageDesc = pageDesc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HashMap<String, Locator> getLocators() {
        return locators;
    }

    public void setLocators(HashMap<String, Locator> locators) {
        this.locators = locators;
    }

    public Page() {
    }
}
