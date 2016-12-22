package org.webdriver.patatiumappui.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.webdriver.patatiumappui.utils.Locator.ByType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class BaseAction extends TestBaseCase{

	protected HashMap<String,Locator>  locatorMap;
	public String path=null;
	public InputStream path_inputStream_1;
	public InputStream path_inputStream_2;
	Log log=new Log(this.getClass());

	public  void setXmlObjectPath(String path)
	{
		this.path=path;
	}
	public  void setXmlObjectPathForLocator(InputStream path_inputStream)
	{
		this.path_inputStream_1=path_inputStream;
	}
	public  void setXmlObjectPathForPageURL(InputStream path_inputStream)
	{
		this.path_inputStream_2=path_inputStream;
	}
	/**
	 * 构造方法，创建创建BasePageOpera对象时，需要初始化的信息.传递相关参数
	 * this.getClass().getCanonicalName() 获取page类路径，也就是xml文档中的pageName
	 * @throws Exception
	 */
	public  BaseAction()
	{


	}
	public void getLocatorMap()
	{
		XmlReadUtil xmlReadUtil=new XmlReadUtil();
		YamlReadUtil yamlReadUtil=new YamlReadUtil();
		try {
			if((path==null||path.isEmpty()))
			{locatorMap = xmlReadUtil.readXMLDocument(path_inputStream_1, this.getClass().getCanonicalName());}
			else {
				if (path.contains(".xml"))
				{
					locatorMap = xmlReadUtil.readXMLDocument(path, this.getClass().getCanonicalName());
				}else if (path.contains(".yaml")) {
					locatorMap=yamlReadUtil.getLocatorMap(path,this.getClass().getCanonicalName());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static By getBy (ByType byType,Locator locator)
	{
		switch(byType)
		{
			case id:
				return By.id(locator.getElement());
			case cssSelector:
				return By.cssSelector(locator.getElement());
			case name:
				return By.name(locator.getElement());
			case xpath:
				return By.xpath(locator.getElement());
			case className:
				return By.className(locator.getElement());
			case tagName:
				return By.tagName(locator.getElement());
			case linkText:
				return By.linkText(locator.getElement());
			case partialLinkText:
				return By.partialLinkText(locator.getElement());
			//return null也可以放到switch外面
			default:
				return null;
		}


	}

	/**
	 * 从对象库获取定位信息
	 * @param locatorName 对象库名字
	 * @return
	 * @throws IOException
	 */
	public  Locator getLocator(String locatorName)
	{
		Locator locator;
		/**
		 * 在对象库通过对象名字查找定位信息
		 */
		locator=locatorMap.get(locatorName);
		/**
		 * 加入对象库，找不到该定位信息，就创建一个定位信息
		 */
		if(locator==null)
		{
			log.error("没有找到"+locatorName+"页面元素");
		}
		return locator;

	}

	public String getPageURL()
	{
		String pageURL=null;
		try {
			if(path==null||path.isEmpty())
			{pageURL=XmlReadUtil.getXmlPageURL(path_inputStream_1, this.getClass().getCanonicalName());}
			else {
				pageURL=XmlReadUtil.getXmlPageURL(path, this.getClass().getCanonicalName());
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return pageURL;
	}

	/**
	 * 打开浏览器
	 * @param url
	 */
	public void open(String url)
	{
		driver.navigate().to(url);
		log.info("打开浏览器，访问"+url+"网址!");

	}
	/***
	 * 关闭浏览器窗口
	 */
	public void close()
	{
		driver.close();
		log.info("关闭浏览器窗口");
	}
	/**
	 * 退出浏览器
	 */
	public void quit()
	{
		driver.quit();
		log.info("退出浏览器");
	}
	/**
	 * 浏览器前进
	 */
	public void forward()
	{
		driver.navigate().forward();
		log.info("浏览器前进");
	}
	/**
	 * 浏览器后退
	 */
	public void back()
	{
		driver.navigate().back();
		log.info("浏览器后退");
	}
	/**
	 * 刷新浏览器
	 */
	public void refresh()
	{
		driver.navigate().refresh();
		log.info("浏览器刷新");
	}
	public WebElement findElement( final Locator locator) throws IOException
	{
		/**
		 * 查找某个元素等待几秒
		 */
		Waitformax(Integer.valueOf(locator.getWaitSec()));
		WebElement webElement;
		webElement=getElement(locator);
		return webElement;


	}
	public void Waitformax(int t)
	{
		driver.manage().timeouts().implicitlyWait(t,TimeUnit.SECONDS);
	}
	/**
	 * 通过定位信息获取元素
	 * @param locator
	 * @return
	 * @throws NoSuchElementException
	 */
	public WebElement getElement(Locator locator)
	{
		/**
		 * locator.getElement(),获取对象库对象定位信息
		 */
		//locator=getLocator(locatorMap.get(key));
		WebElement webElement;
		switch (locator.getType())
		{
			case xpath :
				//log.info("find element By xpath");
				webElement=driver.findElement(By.xpath(locator.getElement()));
				/**
				 * 出现找不到元素的时候，记录日志文件
				 */
				break;
			case id:
				//log.info("find element By xpath");
				webElement=driver.findElement(By.id(locator.getElement()));
				break;
			case cssSelector:
				//log.info("find element By cssSelector");
				webElement=driver.findElement(By.cssSelector(locator.getElement()));
				break;
			case name:
				//log.info("find element By name");
				webElement=driver.findElement(By.name(locator.getElement()));
				break;
			case className:
				//log.info("find element By className");
				webElement=driver.findElement(By.className(locator.getElement()));
				break;
			case linkText:
				//log.info("find element By linkText");
				webElement=driver.findElement(By.linkText(locator.getElement()));
				break;
			case partialLinkText:
				//log.info("find element By partialLinkText");
				webElement=driver.findElement(By.partialLinkText(locator.getElement()));
				break;
			case tagName:
				//log.info("find element By tagName");
				webElement=driver.findElement(By.partialLinkText(locator.getElement()));
				break;
			default :
				//log.info("find element By xpath");
				webElement=driver.findElement(By.xpath(locator.getElement()));
				break;

		}
		return webElement;
	}


}
