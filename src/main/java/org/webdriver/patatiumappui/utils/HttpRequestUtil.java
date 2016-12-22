package org.webdriver.patatiumappui.utils;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.util.Map;

/**
 * Created by zhengshuheng on 2016/8/28.
 */
public class HttpRequestUtil {
    public Result doPost(String host,String url,String bodyJsonParams,Map<String, String> headerParams){
        Result result=new Result();
        long  responseTime=0;//响应时间
        long startTime=0;//开始请求时间
        long endTime=0;//结束请求时间
        String statusCode="";
        String responseText="";
        String requestHeader="";
        HttpRequest httpRequest= new HttpRequest().post(host+url);
        if (!headerParams.isEmpty())
        {
            for (Map.Entry<String,String> entry:headerParams.entrySet())
            {
                httpRequest.header(entry.getKey(),entry.getValue());
            }
        }
        //body参数
        if (!bodyJsonParams.isEmpty())
        {
            httpRequest.body(bodyJsonParams);
        }
        try {
            //开始发送post请求时间
            startTime=System.currentTimeMillis();
            HttpResponse httpResponse=httpRequest.send();
            //结束post请求时间
            endTime=System.currentTimeMillis();
            //响应时间
            responseTime=endTime-startTime;
            requestHeader=httpResponse.getHttpRequest().headers().toString();
            statusCode=Long.toString(httpResponse.statusCode());
            responseText=httpResponse.bodyText();
            //获取返回请求状态码
            result.setResult("statusCode",statusCode);
            //获取body响应字符串
            result.setResult("responseText",responseText);
            //请求开始时间
            result.setResult("startTime",startTime);
            //请求结束时间
            result.setResult("endTime",endTime);
            //获取响应时间
            result.setResult("responseTime",responseTime);
            //获取header头
            result.setResult("headerText",httpResponse.headers().toString());

        }catch (Exception e) {
            System.out.println("发送post请求失败！");
            e.printStackTrace();
        }
        return result;



    }
    /**
     * 完整post请求
     * @param  host 主机地址/根域名
     * @param url url地址
     * @param bodyParams body参数
     * @param headerParams header头参数
     * @param basicAuthentication  安全认证
     * @return 返回包含响应时间，响应状态码，响应字符串数据信息的Map
     */
    public Result doPost(String host,String url, Map<String,Object> bodyParams, Map<String, String> headerParams,Map<String,String> basicAuthentication)
    {

        //Map<String,Object> result=new HashMap<>();
        Result result=new Result();
        long  responseTime=0;//响应时间
        long startTime=0;//开始请求时间
        long endTime=0;//结束请求时间
        String statusCode="";
        String responseText="";
        String requestHeader="";
        HttpRequest httpRequest= new HttpRequest().post(host+url);
        //header头参数
        if (!headerParams.isEmpty())
        {
            for (Map.Entry<String,String> entry:headerParams.entrySet())
            {
                httpRequest.header(entry.getKey(),entry.getValue());
            }
        }
        //安全认证
        if (!basicAuthentication.isEmpty())
        {
            for (Map.Entry<String,String> entry:basicAuthentication.entrySet())
            {
                httpRequest.basicAuthentication(entry.getKey(),entry.getValue().toString());
            }
        }
        //body参数
        if (!bodyParams.isEmpty())
        {
            httpRequest.form(bodyParams);
        }
        try {
            //开始发送post请求时间
            startTime=System.currentTimeMillis();
            HttpResponse httpResponse=httpRequest.send();
            //结束post请求时间
            endTime=System.currentTimeMillis();
            //响应时间
            responseTime=endTime-startTime;
            requestHeader=httpResponse.getHttpRequest().headers().toString();
            statusCode=Long.toString(httpResponse.statusCode());
            responseText=httpResponse.bodyText();
            //获取返回请求状态码
            result.setResult("statusCode",statusCode);
            //获取body响应字符串
            result.setResult("responseText",responseText);
            //请求开始时间
            result.setResult("startTime",startTime);
            //请求结束时间
            result.setResult("endTime",endTime);
            //获取响应时间
            result.setResult("responseTime",responseTime);
            //获取header头
            result.setResult("headerText",httpResponse.headers().toString());

        }catch (Exception e) {
            System.out.println("发送post请求失败！");
            e.printStackTrace();
        }

        return result;

    }

    /**
     * 不带安全认证参数post请求
     * @param url url地址
     * @param bodyParams body参数
     * @param headerParams 头参数
     * @return 返回包含响应时间，响应状态码，响应字符串数据信息的Map
     */
    public Result dopost(String host,String url, Map<String,Object> bodyParams, Map<String, String> headerParams)
    {
        //Map<String,Object> result=new HashMap<>();
        String requestHeader="";
        Result result=new Result();
        long  responseTime=0;//响应时间
        long startTime=0;//开始请求时间
        long endTime=0;//结束请求时间
        String statusCode="";
        String responseText="";
        HttpRequest httpRequest= new HttpRequest().post(host+url);
        //header头参数
        if (!headerParams.isEmpty())
        {
            for (Map.Entry<String,String> entry:headerParams.entrySet())
            {
                httpRequest.header(entry.getKey(),entry.getValue());
            }
        }
        //body参数
        if (!bodyParams.isEmpty())
        {
            httpRequest.form(bodyParams);
        }
        try {
            startTime=System.currentTimeMillis();
            HttpResponse httpResponse=httpRequest.send();
            endTime=System.currentTimeMillis();
            responseTime=endTime-startTime;
            responseText=httpResponse.bodyText();
            statusCode=Long.toString(httpResponse.statusCode());
            //获取请求头
            requestHeader=httpResponse.getHttpRequest().headers().toString();
            //获取返回请求状态码
            result.setResult("statusCode",statusCode);
            //获取响应字符串
            result.setResult("responseText",responseText);
            //请求开始时间
            result.setResult("startTime",startTime);
            //请求结束时间
            result.setResult("endTime",endTime);
            //获取响应时间
            result.setResult("responseTime",responseTime);
            //获取响应header头
            result.setResult("headerText",httpResponse.headers().toString());

        }catch (Exception e) {
            System.out.println("发送post请求失败！");
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 不带安全认证/头参数post请求(只需填写body参数)
     * @param url url地址
     * @param host 主机地址/根域名
     * @param bodyParams body请求参数
     * @return  返回包含响应时间，响应状态码，响应字符串数据信息的Map
     */
    public Result dopost(String host,String url, Map<String,Object> bodyParams)
    {
        //Map<String,Object> result=new HashMap<>();
        String requestHeader="";
        Result result=new Result();
        long  responseTime=0;//响应时间
        long startTime=0;//开始请求时间
        long endTime=0;//结束请求时间
        HttpRequest httpRequest= new HttpRequest().post(host+url);
        String statusCode="";
        String responseText="";
        //body参数
        if (!bodyParams.isEmpty())
        {
            httpRequest.form(bodyParams);
        }
        try {
            startTime=System.currentTimeMillis();
            HttpResponse httpResponse=httpRequest.send();
            endTime=System.currentTimeMillis();
            responseTime=endTime-startTime;
            responseText=httpResponse.bodyText();
            statusCode=Long.toString(httpResponse.statusCode());
            //获取请求头
            requestHeader=httpResponse.getHttpRequest().headers().toString();
            //获取返回请求状态码
            result.setResult("statusCode",httpResponse.statusCode());
            //获取响应字符串
            result.setResult("responseText",httpResponse.bodyText());
            //请求开始时间
            result.setResult("startTime",startTime);
            //请求结束时间
            result.setResult("endTime",endTime);
            //获取响应时间
            result.setResult("responseTime",responseTime);

        }catch (Exception e) {
            System.out.println("发送post请求失败！");
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 不带任何参数的post请求
     * @param url url地址+
     * @param  host 主机地址/根域名
     * @return 返回包含响应时间，响应状态码，响应字符串数据信息的Map
     */
    public Result dopost(String host,String url)
    {
        //Map<String,Object> result=new HashMap<>();
        String bodyParams="";
        Result result=new Result();
        long  responseTime=0;//响应时间
        long startTime=0;//开始请求时间
        long endTime=0;//结束请求时间
        HttpRequest httpRequest= new HttpRequest().post(host+url);
        String statusCode="";
        String responseText="";
        String responseHeader="";
        String requestHeader="";
        try {
            startTime=System.currentTimeMillis();
            HttpResponse httpResponse=httpRequest.send();
            endTime=System.currentTimeMillis();
            //请求开始时间
            result.setResult("startTime",startTime);
            //请求结束时间
            result.setResult("endTime",endTime);
            //获取响应时间
            responseTime=endTime-startTime;
            //获取响应字符串
            responseText=httpResponse.bodyText();
            //获取返回请求状态码
            statusCode=Long.toString(httpResponse.statusCode());
            //获取响应header头
            responseHeader=httpResponse.headers().toString();
            //获取请求头
            requestHeader=httpResponse.getHttpRequest().headers().toString();
            result.setResult("statusCode",statusCode);
            result.setResult("responseText",responseText);
            result.setResult("responseTime",responseTime);
            result.setResult("headerText",responseHeader);
            bodyParams=httpRequest.bodyText();

        }catch (Exception e) {
            System.out.println("发送post请求失败！");
            e.printStackTrace();
        }
        return result;

    }

    /**
     * get请求
     * @param url url地址
     * @param  host 主机地址/根域名
     * @param param 参数
     * @return
     */
    public  Result doGet(String host,String url,Map<String,String>param)
    {
        //Map<String,Object> result=new HashMap<>();
        Result result=new Result();
        long responseTime=0;
        long startTime=0;
        long endTime=0;
        startTime=System.currentTimeMillis();
        HttpResponse httpResponse= HttpRequest.get(host+url).query(param).send();
        String statusCode="";
        String responseText="";
        String responseHeader="";
        String requestHeader="";
        endTime=System.currentTimeMillis();
        //获取响应时间
        responseTime=endTime-startTime;
        //获取响应字符串
        responseText=httpResponse.bodyText();
        //获取返回请求状态码
        statusCode=Long.toString(httpResponse.statusCode());
        //获取响应头
        responseHeader=httpResponse.headers().toString();
        //获取请求头
        requestHeader=httpResponse.getHttpRequest().headers().toString();
        result.setResult("statusCode",statusCode);

        result.setResult("responseText",responseText);
        //请求开始时间
        result.setResult("startTime",startTime);
        //请求结束时间
        result.setResult("endTime",endTime);

        result.setResult("responseTime",responseTime);
        //获取header头
        result.setResult("headerText",responseHeader);
        return result;

    }

}
