package org.webdriver.patatiumappui.pageObjectConfig;

import com.esotericsoftware.yamlbeans.YamlReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.webdriver.patatiumappui.utils.Locator;
import org.webdriver.patatiumappui.utils.Log;

import java.io.*;
import java.util.*;

//通过解析xml文件自动生成对象库代码
public class PageObjectAutoCodeForYaml {
	Log log=new Log(this.getClass());
	static  String path="src/main/java/org/webdriver/patatiumappui/pageObjectConfig/UILibrary.yaml";
	public static void autoCode() throws Exception
	{
		File file = new File(path);
		if (!file.exists()) {
			throw new IOException("Can't find " + path);
		}
		YamlReader yamlReader=new YamlReader(new FileReader(file));
		Object yamlObject=yamlReader.read();
		Map yamlMap=(Map) yamlObject;
		ArrayList<HashMap<String,Object>> pages=(ArrayList<HashMap<String,Object>>)yamlMap.get("pages");//page列表
		for (int i=0;i<pages.size();i++)
		{
			HashMap<String,Object> pageNode=pages.get(i);//获取page节点
			HashMap<String,Object> pageElement=(HashMap<String,Object>)pageNode.get("page");
			//获取page节点的name属性值
			String pageName=pageElement.get("pageName").toString();
			System.out.println(pageName);
			//将pageName存储为数组
			String[] pageNameArray=pageName.split("\\.");
			System.out.println(pageNameArray);
			System.out.println(pageNameArray[0]);
			//获取要写入的page所属的类名
			String pageClassName=pageNameArray[4].toString();
			//获取对象库包名
			String packageName=pageNameArray[0].toString()+"."+pageNameArray[1].toString()+"."+pageNameArray[2].toString()+"."+pageNameArray[3].toString();
			//--自动编写对象库代码（XXPage.java）开始--
			StringBuffer sb=new StringBuffer("package "+packageName+";\n");
			sb.append("import java.io.IOException;\n");
			sb.append("import java.io.InputStream;\n");
			sb.append("import org.webdriver.patatiumappui.utils.BaseAction;\n");
			sb.append("import org.webdriver.patatiumappui.utils.Locator;\n");
			sb.append("import org.webdriver.patatiumappui.pageObjectConfig.PageObjectAutoCodeForYaml;");
			sb.append("//"+pageElement.get("desc")+"_对象库类\n");
			sb.append("public class "+ pageClassName+" extends BaseAction {\n");
			sb.append("//用于eclipse工程内运行查找对象库文件路径\n");
			sb.append("private String path=\"src/main/java/org/webdriver/patatiumappui/pageObjectConfig/UILibrary.yaml\";\n");
			sb.append(" public   "
					+ pageClassName
					+ "() {\n");
			sb.append("//工程内读取对象库文件\n	");
			sb.append("setXmlObjectPath(path);\n");
			sb.append("getLocatorMap();");
			sb.append("\n}");
			System.out.println(pageElement.get("desc"));
			List<HashMap<String,Object>> locators=(List<HashMap<String,Object>>)pageElement.get("locators");//获取locators列表
			for (int j=0;j<locators.size();j++)//遍历locators列表
			{
				//获取locator节点
				HashMap<String,Object> locatorNode=locators.get(j);
				String locatorName=locatorNode.get("name").toString();
                if (locatorNode.size()>3)
				{
					sb.append("\n/***\n"
							+ "* "+locatorNode.get("value")+"\n"
							+ "* @return\n"
							+ "* @throws IOException\n"
							+ "*/\n");
				} else
				{
					sb.append("\n");
				}
				sb.append("public  Locator "+locatorName+"() throws IOException\n {\n");
				sb.append("   Locator locator=getLocator(\""+locatorName+"\");\n");
				sb.append("   return locator;\n }\n");
		      }
			sb.append("}");
			//将自动生成的PageObject代码写入文件
			File pageObjectFile=new File("src/main/java/org/webdriver/patatiumappui/pageObject/"+pageClassName+".java");
			if(pageObjectFile.exists())
			{
				pageObjectFile.delete();;
			}
			try {
				FileWriter fileWriter=new FileWriter(pageObjectFile, false);
				BufferedWriter output = new BufferedWriter(fileWriter);
				output.write(sb.toString());
				output.flush();
				output.close();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			System.out.println(sb);
			Log log=new Log(PageObjectAutoCodeForYaml.class);
			log.info("自动生成对象库java代码成功");
		}

	}
	public static void autoCode2() throws Exception
	{
		File file = new File(path);
		if (!file.exists()) {
			throw new IOException("Can't find " + path);
		}
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		//对象库xml文件根节点
		Element root = document.getRootElement();
		//遍历根节点下的第一个节点（page节点）
		for(Iterator<?> i=root.elementIterator();i.hasNext();)
		{
			Element page=(Element)i.next();
			//获取page节点的name属性值
			String pageName=page.attribute(0).getValue();
			System.out.println(pageName);
			//将pageName存储为数组
			String[] pageNameArray=pageName.split("\\.");
			System.out.println(pageNameArray);
			System.out.println(pageNameArray[0]);
			//获取要写入的page所属的类名
			String pageClassName=pageNameArray[4].toString();
			//获取对象库包名
			String packageName=pageNameArray[0].toString()+"."+pageNameArray[1].toString()+"."+pageNameArray[2].toString()+"."+pageNameArray[3].toString();
			//--自动编写对象库代码（XXPage.java）开始--
			StringBuffer sb=new StringBuffer("package "+packageName+";\n");
			sb.append("import java.io.IOException;\n");
			sb.append("import java.io.InputStream;\n");
			sb.append("import org.webdriver.patatiumappui.utils.BaseAction;\n");
			sb.append("import org.webdriver.patatiumappui.utils.Locator;\n");
			sb.append("import org.webdriver.patatiumappui.pageObjectConfig.PageObjectAutoCode;");
			sb.append("//"+page.attribute(2).getValue()+"_对象库类\n");
			sb.append("public class "+ pageClassName+" extends BaseAction {\n");
			sb.append("//用于eclipse工程内运行查找对象库文件路径\n");
			sb.append("private String path=\"src/main/java/org/webdriver/patatiumappui/pageObjectConfig/UILibrary.xml\";\n");
			//sb.append("//用户打包成jar后查找对象库文件路径\n");
			//sb.append("private InputStream pathInputStream=PageObjectAutoCode.class.getClassLoader().getResourceAsStream(\"net/hk515/pageObjectConfig/UILibrary.xml\");	\n");
			sb.append(" public   "
					+ pageClassName
					+ "() {\n");
			sb.append("//工程内读取对象库文件\n	");
			sb.append("setXmlObjectPath(path);\n");
			sb.append("getLocatorMap();");
			sb.append("\n}");
			//sb.append("\n private String path=PageObjectAutoCode.class.getClassLoader().getResource(\"net/hk515/pageObjectConfig/UILibrary.xml\").getPath();");
			//遍历Page节点下的Locator节点
			for(Iterator<?> j=page.elementIterator();j.hasNext();)
			{
				//获取locaror节点
				Element locator =(Element)j.next();
				String locatorName=locator.getTextTrim();
				if(locator.attributeCount()>3)
				{sb.append("\n/***\n"
						+ "* "+locator.attribute(3).getValue()+"\n"
						+ "* @return\n"
						+ "* @throws IOException\n"
						+ "*/\n");
				}
				else
				{
					sb.append("\n");
				}
				sb.append("public  Locator "+locatorName+"() throws IOException\n {\n");
				//sb.append("   setXmlObjectPath(path);\n");
				sb.append("   Locator locator=getLocator(\""+locatorName+"\");\n");
				sb.append("   return locator;\n }\n");
			}
			sb.append("}");
			//将自动生成的PageObject代码写入文件
			File pageObjectFile=new File("src/main/java/org/webdriver/patatiumappui/pageObject/"+pageClassName+".java");
			if(pageObjectFile.exists())
			{
				pageObjectFile.delete();;
			}
			try {
				FileWriter fileWriter=new FileWriter(pageObjectFile, false);
				BufferedWriter output = new BufferedWriter(fileWriter);
				output.write(sb.toString());
				output.flush();
				output.close();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			System.out.println(sb);
			Log log=new Log(PageObjectAutoCodeForYaml.class);
			log.info("自动生成对象库java代码成功");
		}


	}
	public static void main(String[] args) throws Exception {
		// TODO 自动生成的方法存根
		PageObjectAutoCodeForYaml.autoCode();
	}

}
