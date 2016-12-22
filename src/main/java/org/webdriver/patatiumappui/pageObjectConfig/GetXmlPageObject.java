package org.webdriver.patatiumappui.pageObjectConfig;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GetXmlPageObject {

	public  static void getXmlPageObject()
	{
		WebDriver driver=new FirefoxDriver();
		driver.get("http://192.168.0.29:9020/hospital/load");
		String tString=driver.getPageSource();
		System.out.println(tString);
	}
	public static void main(String[] args)
	{
		// TODO 自动生成的方法存根
		GetXmlPageObject.getXmlPageObject();
	}

}
