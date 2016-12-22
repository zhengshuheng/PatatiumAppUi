package org.webdriver.patatiumappui.pageObject;
import java.io.IOException;
import java.io.InputStream;
import org.webdriver.patatiumappui.utils.BaseAction;
import org.webdriver.patatiumappui.utils.Locator;
import org.webdriver.patatiumappui.pageObjectConfig.PageObjectAutoCode;//微信APP启动首页_对象库类
public class StartPage extends BaseAction {
//用于eclipse工程内运行查找对象库文件路径
private String path="src/main/java/org/webdriver/patatiumappui/pageObjectConfig/UILibrary.xml";
 public   StartPage() {
//工程内读取对象库文件
	setXmlObjectPath(path);
getLocatorMap();
}
/***
* 登录
* @return
* @throws IOException
*/
public  Locator 登录() throws IOException
 {
   Locator locator=getLocator("登录");
   return locator;
 }

/***
* 注册
* @return
* @throws IOException
*/
public  Locator 注册() throws IOException
 {
   Locator locator=getLocator("注册");
   return locator;
 }
}