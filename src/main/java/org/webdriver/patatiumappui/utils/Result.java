package org.webdriver.patatiumappui.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengshuheng on 2016/8/28.
 */
public class Result {
    private Map<String,Object> result=new HashMap<String, Object>();
    public  void setResult(String key,Object value)
    {
        this.result.put(key,value);
    }
    public  String getBody()
    {
        return  this.result.get("responseText").toString();
    }
    public  String getStateCode()
    {
        return this.result.get("statusCode").toString();
    }
    public  long getResponseTime()
    {
        return Long.valueOf(this.result.get("responseTime").toString());
    }
    public  String getHeader()
    {
        return this.result.get("headerText").toString();
    }
    public  String  getStartTime(){return  TimeUtil.formatTime(Long.valueOf(this.result.get("startTime").toString()));};
    public  String  getEndTime(){return  TimeUtil.formatTime(Long.valueOf(this.result.get("endTime").toString()));};
}
