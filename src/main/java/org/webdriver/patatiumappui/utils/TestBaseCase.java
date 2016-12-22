package org.webdriver.patatiumappui.utils;
import org.testng.annotations.*;
import org.webdriver.patatiumappui.utils.Log;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestBaseCase {
	public static AndroidDriver driver;
	//方法描述
	public static String description;
	public Log log=new Log(this.getClass().getSuperclass());
	@BeforeSuite
	@Parameters({"driverName","nodeURL","appName","deviceName","sdkVersion","appMainPackage","appActivity","platformName"})
	public void  setup( String driverName,String nodeURL,String appName,String deviceName,String sdkVersion,String appMainPackage,String appActivity,String platformName) throws MalformedURLException {
		log.info("------------------开始执行测试---------------");
		//启动appium server
		ElementAction action=new ElementAction();
		log.info("通过cmd命令启动appium server");
		try {
			String cmd="appium -a " +
					nodeURL.split(":")[0]+
					"  -p "+nodeURL.split(":")[1];
			System.out.println(cmd);
			action.executeCmd(cmd);
			action.sleep(10);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(nodeURL.equals("")||nodeURL.isEmpty())
		{
			log.error("appium url 没有设置");
		}
		else {
			log.info("读取xml配置：Mobile Driver:"+driverName+"；Appium Server:"+"http://"+nodeURL+"/wd/hub");
			try {
				//Process process=Runtime.getRuntime().exec("cmd.exe /c appium");
				//System.out.println(process.toString());
				//this.driver=setRemoteDriver(driverName, nodeURL, appName, deviceName, sdkVersion, appMainPackage, appActivity,platformName);
				 driver=setRemoteDriver(driverName, nodeURL, appName, deviceName, sdkVersion, appMainPackage, appActivity,platformName);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.error("appium环境配置失败");
			}
		}

	}

	@AfterSuite
	public void tearDown() throws IOException {
		this.driver.quit();
		ElementAction action=new ElementAction();
		log.info("关闭appium server");
		action.executeCmd("taskkill /f/im cmd.exe");
		log.info("-------------结束测试，并关闭退出driver及appium server-------------");
	}
	/**
	 *
	 * @param driverName driverName
	 * @param nodeURL   appium URL
	 * @param appName   app文件名
	 * @param deviceName 设备名字
	 * @param sdkVersion 安卓版本
	 * @param appMainPackage app主包名
	 * @param appActivity app主类名
	 * @param platformName 设备平台    真机：Android AVD虚拟设备：Android Emulator
	 * @return
	 * @throws MalformedURLException
	 */
	private AndroidDriver setRemoteDriver(String driverName,String nodeURL,String appName,String deviceName,String sdkVersion,String appMainPackage,String appActivity,String platformName) throws MalformedURLException
	{
		//获取app路径
		System.out.println(System.getProperty("user.dir"));
		File classRootPath=new File(System.getProperty("user.dir"));
		File appDir=new File(classRootPath,"apps");
		File app =new File(appDir,appName);
		switch (driverName)
		{
			case "AndroidDriver" :
				//设置自动化相关参数
				DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
				desiredCapabilities.setCapability("platformName",platformName);
				desiredCapabilities.setCapability("deviceName",deviceName );
				desiredCapabilities.setCapability("platformVersion", sdkVersion);
				//设置app路径
				desiredCapabilities.setCapability("app", app.getAbsolutePath());
				//设置app主包名和主类名
				desiredCapabilities.setCapability("appMainPackage", appMainPackage);
				desiredCapabilities.setCapability("appActivity", appActivity);
				//设置支持中文输入
				desiredCapabilities.setCapability("unicodeKeyboard", "True");
				desiredCapabilities.setCapability("resetKeyboard", "True");
				desiredCapabilities.setCapability("noSign", "True");
				//初始化driver
				driver= new AndroidDriver(new URL("http://"+nodeURL+"/wd/hub"), desiredCapabilities);
				break;
			default:
				//获取app路径
				File classRootPath2=new File(System.getProperty("usr.dir"));
				File appDir2=new File(classRootPath2,"apps");
				File app2 =new File(appDir2,appName);
				//设置自动化相关参数
				DesiredCapabilities desiredCapabilities2=new DesiredCapabilities();
				desiredCapabilities2.setCapability("platformName", "Android");
				desiredCapabilities2.setCapability("deviceName",deviceName );
				desiredCapabilities2.setCapability("platformVersion", sdkVersion);
				//设置app路径
				desiredCapabilities2.setCapability("app", app2.getAbsolutePath());
				//设置app主包名和主类名
				desiredCapabilities2.setCapability("appMainPackage", appMainPackage);
				desiredCapabilities2.setCapability("appActivity", appActivity);
				//设置支持中文输入
				desiredCapabilities2.setCapability("unicodeKeyboard", "True");
				desiredCapabilities2.setCapability("resetKeyboard", "True");
				desiredCapabilities2.setCapability("noSign", "True");
				//初始化driver
				driver= new AndroidDriver(new URL("http://"+nodeURL+"/wd/hub"), desiredCapabilities2);
				break;
		}
		return driver;
	}
	public static void main(String args[])
	{
		WebDriver driver2=new FirefoxDriver();
		driver2.get("http://www.baidu.com");
	}


}
