package org.webdriver.patatiumappui.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Log
{
    private final Class<?> clazz;
    private  Logger logger;
    /**
     *
     * @param clazz 获取当前类
     */
    public Log(Class<?> clazz)
    {
        this.clazz=clazz;
        //Logger.getLogger的方法是调用的是LogManager.getLogger()方法，所以这两个方法都是返回logger
        this.logger=Logger.getLogger(this.clazz);
        Log.initlog4j();

    }
    //初始化log4j，设置log4j的配置文件log4j.Properties
    private static  void initlog4j()
    {
        //创建Propderties对象
        Properties prop=new Properties();
	   /*Log4j建议只使用四个级别，优先级从高到低分别是ERROR、WARN、INFO、DEBUG
	   这里定义能显示到的最低级别,若定义到INFO级别,则看不到DEBUG级别的信息了~!级别后面是输出端*/
        prop.setProperty("log4j.rootLogger", "INFO,CONSOLE,E,F");
        prop.setProperty("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender");
        prop.setProperty("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
        prop.setProperty("log4j.appender.CONSOLE.layout.ConversionPattern", "[%d{YYYY-MM-dd HH:mm:ss,SSS}] %-5p %c %m%n");

        String src="test-output/log";
        //设置日期格式
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期
        String date=dateFormat.format(new Date()).toString();

        File dir = new File(src+"/"+date);
        if (!dir.exists())
        {dir.mkdirs();}
        String filepath=dir.getAbsolutePath()+"/"+"log_"+date+".log";

        prop.setProperty("log4j.appender.E","org.apache.log4j.FileAppender");
        prop.setProperty("log4j.appender.E.file",filepath);
        prop.setProperty("log4j.appender.E.layout","org.apache.log4j.PatternLayout");
        prop.setProperty("log4j.appender.E.layout.ConversionPattern", "[%d{YYYY-MM-dd HH:mm:ss,SSS}] %-5p %c %m%n");

        prop.setProperty("log4j.appender.F","org.apache.log4j.FileAppender");
        //String src="test-output/log";
        //设置日期格式
        //SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期
        //String date=dateFormat.format(new Date()).toString();
        //设置时间格式
        //SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HHmmss");
        //获取当前时间
        //String time=timeFormat.format(new Date()).toString();
        //File dir = new File(src+"/"+date);
        // if (!dir.exists())
        // {dir.mkdirs();}
        String filepathHtml=dir.getAbsolutePath()+"/"+"log_"+date+".html";
        prop.setProperty("log4j.appender.F.file",filepathHtml);
        prop.setProperty("log4j.appender.F.layout","org.apache.log4j.HTMLLayout");
        //prop.setProperty("log4j.appender.F.layout.ConversionPattern", "[%d{YYYY-MM-dd HH:mm:ss,SSS}] %-5p %c %m%n");

        PropertyConfigurator.configure(prop);
    }
    public  void info(String message)
    {
        logger.info(message);
    }
    public void warn(String message)
    {
        logger.warn(message);
    }
    public void error(String message)
    {
        logger.error(message);
    }
    public void debug(String message)
    {
        logger.debug(message);
    }

}

