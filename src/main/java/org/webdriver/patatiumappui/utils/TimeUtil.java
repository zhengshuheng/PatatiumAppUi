package org.webdriver.patatiumappui.utils;

import org.openqa.selenium.JavascriptExecutor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhengshuheng on 2016/8/28.
 */
public class TimeUtil extends TestBaseCase {
    /**
     * 时间控件不可编辑处理
     * @param inputName
     * @param time
     * @param timeFormat
     */
    public void timeWidgetMange(String inputName,String time,String timeFormat)
    {
        String date=formatDate(time, timeFormat);
        String js="$(function(){$(\"input[name='"
                + inputName
                +"']\""
                + ").removeAttr('readonly');"
                + "$(\"input[name='"
                + inputName
                +"']\""
                + ").val(\""
                + date
                + "\");"
                + "})";
        ((JavascriptExecutor) driver).executeScript(js);
        System.out.println(js);
    }
    /**
     * 按指定格式格式化时间
     * @param date 日期类型
     * @param format 格式化表达式
     * @return 返回格式化时间字符串
     */
    public  static String formatDate(Date date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        System.out.println(formatter.format(date).toString());
        return formatter.format(date).toString();

    }
    /**
     * 按指定格式格式化时间
     * @param date 字符串日期
     * @param format 格式化表达式
     * @return 返回格式化时间字符串
     */
    public  static String formatDate(String date,String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format);
        String sss = null;
        try {
            sss = sdf2.format(sdf.parse(date));
            //System.out.println(sss);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sss;
    }
    /**
     * 把时间戳转换成日期格式
     * @param date 时间戳
     * @return  返回yyyy-MM-dd格式时间
     */
    public  static String formatDate(long date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(formatter.format(date));
        return formatter.format(date);
    }
    /**
     * 把时间戳转换成日期格式
     * @param date 时间戳
     * @return  返回yyyy-MM-dd格式时间
     */
    public  static String formatTime(long date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
        System.out.println(formatter.format(date));
        return formatter.format(date);
    }

    /**
     * 获取两个时间之间相差的天数
     * @param start_time
     * @param end_time
     * @return
     */
    public  static long getDaySub(String start_time,String end_time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long day=0;
        try {
            long start_timeStamp=sdf.parse(start_time).getTime();
            long end_timeStamp=sdf.parse(end_time).getTime();
            day=(end_timeStamp-start_timeStamp)/(24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  day;


    }
    public  static  void main(String[] args)
    {
        System.out.print(TimeUtil.getDaySub("2016-08-25","2016-08-28"));
       System.out.println(TimeUtil.formatDate("2016/10/12","yyyy/MM/DD"));
    }
}
