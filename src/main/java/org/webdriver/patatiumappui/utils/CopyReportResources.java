package org.webdriver.patatiumappui.utils;

import java.io.*;

/**
 * Created by zhengshuheng on 2016/8/18 0018.
 */
public class CopyReportResources {

     public  static void main(String[] args)
     {
         CopyReportResources copyReportResources=new CopyReportResources();
         copyReportResources.copyResources();
     }
     public void copyResources()
     {
         this.copyFile("src/main/resources/reportResources/back.gif", "test-output/back.gif");
         this.copyFile("src/main/resources/reportResources/close.gif", "test-output/close.gif");
         this.copyFile("src/main/resources/reportResources//logo1.png", "test-output/logo1.png");
         this.copyFile("src/main/resources/reportResources/Chart.js", "test-output/Chart.js");
         this.copyFile("src/main/resources/reportResources/jquery-1.7.1.min.js", "test-output/jquery-1.7.1.min.js");
         this.copyFile("src/main/resources/reportResources/loadxmldoc.js", "test-output/loadxmldoc.js");
         this.copyFile("src/main/resources/reportResources/report.css", "test-output/report.css");
         this.copyFile("src/main/resources/reportResources/report.js", "test-output/report.js");
         this.copyFile("src/main/resources/reportResources/imageshow.css", "test-output/imageshow.css");
     }
    /**
     * 复制图片及其他文件
     * @param sourceRelativePath 源文件相对路径
     * @param targetRelativePath 目标文件相对路径
     */
    private void copyFile(String sourceRelativePath,String targetRelativePath)
    {
        //读取流字节流
        FileInputStream fileInputStream=null;
        //写入流字节流
        OutputStream imageOutputStream=null;
        try {
             fileInputStream=new FileInputStream(new File(sourceRelativePath));
            //imageInputStream=CopyReportResources.class.getClassLoader().getResourceAsStream(sourceRelativePath);// 与复制的图片关联起来
            imageOutputStream=new FileOutputStream(targetRelativePath);// 与复制到的目的关联起来，这里的图片的名称可以与原来的相同，也可以不一样
            byte[] b = new byte[1024];// 定义字节数组，并指定长度
            int startbyte = -1;
            while ((startbyte= fileInputStream.read(b)) != -1) {// 读取
                imageOutputStream.write(b, 0, startbyte);// 写入，读多少写入多少，所以用 write(b,0,len)
            }
            System.out.println("复制文件成功");
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally{
            if (fileInputStream!=null) {

                try {
                    fileInputStream.close();

                } catch (IOException e2) {
                    // TODO: handle exception
                    System.out.println("读取流关闭失败");
                }
            }
            if (imageOutputStream!=null) {

                try {
                    imageOutputStream.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                    System.out.println("输出流关闭失败");
                }

            }
        }

    }
}
