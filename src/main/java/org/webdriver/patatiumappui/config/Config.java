package org.webdriver.patatiumappui.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;


public class Config {
	public static String path="testng.xml";
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

		String Base_Url=args[0];
		String UserName=args[1];
		String PassWord=args[2];
		String driver=args[3];
		String Node_Url=args[4];
		String Recipients=args[5];
		String ReportUrl=args[6];
		String LogUrl=args[7];
		try {
			//Config.SetXML(args[0], args[1], args[2]);
			if (driver.equalsIgnoreCase("火狐浏览器")) {
				driver="FirefoxDriver";
				Node_Url=Node_Url+":3155";

			}
			else if (driver.equalsIgnoreCase("谷歌浏览器")) {
				driver="ChormeDriver";
				Node_Url=Node_Url+":3166";
			}
			else if(driver.equalsIgnoreCase("IE9浏览器")) {
				driver="InternetExplorerDriver-9";
				Node_Url=Node_Url+":3177";
			}
			else if(driver.equalsIgnoreCase("IE8浏览器")) {
				driver="InternetExplorerDriver-8";
				Node_Url=Node_Url+":3188";
			}
			else {
				driver="FirefoxDriver";
				Node_Url=Node_Url+":3155";
			}
			Config.SetXML(Base_Url, UserName, PassWord,driver,Node_Url,Recipients,ReportUrl,LogUrl);
			//Config.formatXMLFile(path);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public static void SetXML(String Base_Url,String UserName,String PassWord,String driver,String nodeURL,String Recipients,String ReportUrl,String LogUrl) throws IOException, DocumentException
	{

		File file = new File(path);
		if (!file.exists()) {
			throw new IOException("Can't find " + path);

		}
		SAXReader reader = new SAXReader();
		Document  document = reader.read(file);
		Element root = document.getRootElement();
		for (Iterator<?> i = root.elementIterator(); i.hasNext();)
		{
			Element page = (Element) i.next();
			if(page.attributeCount()>0)
			{
				if (page.attribute(0).getValue().equalsIgnoreCase("Base_Url"))
				{
					page.attribute(1).setValue(Base_Url);
					//System.out.println(page.attribute(1).getValue());
				}
				if (page.attribute(0).getValue().equalsIgnoreCase("UserName")) {
					page.attribute(1).setValue(UserName);
				}
				if (page.attribute(0).getValue().equalsIgnoreCase("PassWord")) {
					page.attribute(1).setValue(PassWord);
				}
				if (page.attribute(0).getValue().equalsIgnoreCase("driver")) {
					page.attribute(1).setValue(driver);
				}
				if (page.attribute(0).getValue().equalsIgnoreCase("nodeURL")) {
					page.attribute(1).setValue("http://"+nodeURL+"/wd/hub");
				}
				if (page.attribute(0).getValue().equalsIgnoreCase("Recipients"))
				{
					page.attribute(1).setValue(Recipients);
				}
				if (page.attribute(0).getValue().equalsIgnoreCase("ReportUrl"))
				{
					page.attribute(1).setValue(ReportUrl);
				}
				if (page.attribute(0).getValue().equalsIgnoreCase("LogUrl"))
				{
					page.attribute(1).setValue(LogUrl);
				}
				continue;
			}
			//continue;
		}
		if (driver.equalsIgnoreCase("FirefoxDriver")) {
			driver="火狐浏览器";

		}
		else if (driver.equalsIgnoreCase("ChormeDriver")) {
			driver="谷歌浏览器";
		}
		else if(driver.equalsIgnoreCase("InternetExplorerDriver-8")) {
			driver="IE8浏览器";
		}
		else if(driver.equalsIgnoreCase("InternetExplorerDriver-9")) {
			driver="IE9浏览器";
		}
		else {
			driver="火狐浏览器";
		}
		try{
			/** 格式化输出,类型IE浏览一样 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML编码 */
			format.setEncoding("gb2312");
			/** 将document中的内容写入文件中 */
			XMLWriter writer = new XMLWriter(new FileWriter(new File(path)),format);
			writer.write(document);
			writer.close();
			/** 执行成功,需返回1 */
			int returnValue = 1;
			System.out.println("设置配置环境："+Base_Url
					+ ";用户名:"+UserName
					+ "密码:"
					+ PassWord
					+"浏览器："
					+driver
					+"成功!");
			System.out.println("设置报表url:"+ReportUrl);
			System.out.println("设置报表日志:"+LogUrl);
			System.out.println("设置收件人地址："+Recipients);
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("设置配置环境："+Base_Url
					+ ";用户名:"+UserName
					+ "密码:"
					+ PassWord
					+"浏览器："
					+driver
					+"失败!");
		}


	}
}
