#PatatiumWebUi
### 简介

 **这是一个WebUI自动化测试框架，由<a href="http://www.webdriver.org">webdriver中文社区</a>创办人土豆(本人技术笔名)所创建,该web自动化测试框架是用java语言编写的，基于selenium webdriver 的开源自动化测试框架，该框架结合了testng,selenium,webdriver，jxl，jodd-http 等工具。该框架基于页面对象模型（POM）架构，实现了关键字驱动技术，数据驱动,无需掌握多少编程知识即可编写脚本，同时实现了数据与代码分离的功能：1、元素定位信息保存在对象库文件中 2、测试用例数据可以存储在excel中。从而实现，页面元素位置变化，无需改动脚本，只需修改对应的元素定位信息即可。
     该框架实现了检查点及用例失败自动截图功能，自动生成html测试报告及自动发送html邮件测试报告功能。
     目前框架还不是特别完善，还需要写一些脚本实现自动化；学习该框架需要熟悉一定的HTML 和java基础，后续可以考虑自动编码的实现。**
### 环境配置

1、jdk 1.8
2、idea/eclipse
3、maven
### 注意事项

工程项目编码需要设置为UTF-8否则会出现中文乱码情况
### API文档

<a href="http://www.webdriver.org/doc/patatiumwebui/api/">点击查看API文档</a>
### 实现的功能

 1、XML管理元素对象信息
 2、统一的操作API风格，action.操作(某个页面.某个元素（）)
 3、数据驱动
 4、关键字驱动
 5、用例失败自动截图并展示到报表中
 6、自动生成html报表，自动发送html邮件报告
 7、用例串行一次性执行多个浏览器，可用于兼容性测试
### Demo演示

### 1、对象库文件编写(文件名定义为UILibrary.xml)

```
<?xml version="1.0" encoding="UTF-8"?>
<!--整个对象库文件的根目录，管理整个项目的对象-->
<map>
    <!--管理一个页面的元素（webelement：input,select,textare,a,li等标签），一个page包含多个locator对象
    Pagename:page对象名字，格式：org.webdriver.patatiumwebui.PageObject.xxxPage;最后面那位才是真正的页面名字，前面的是java对象库路径；另外注意，页面名字是头个单词大写；例如主页：名字定义为 org.webdriver.patatiumwebui.HomePage
    Value：页面对象的URL，可不填。
    Desc:页面对象中文描述-->
    <page pagename="org.webdriver.patatiumwebui.pageObject.LoginPage" value="" desc="京东登录页面">
        <!--管理一个页面的元素（webelement：input,select,textare,a,li等标签），一个page包含多个locator对象
        Type：定位方式，包含id,name,class,linktext,xpath,css等，定位元素的时候灵活使用，一般可以统一用xpath
        代替id,name,class，linktext的定位方式。
        Timeout：元素加载时间，有些页面元素，可能要等待一段时间才能加载过来，为了查找元素的稳定性，需加等待时间。
        Value:元素定位信息，如果是id,name,class，linktext直接把网页元素对应的这些属性值写上即可，如果是xpath定位方式，
        需要填写正确的xpath语法格式。
        Desc:元素的描述，元素的中文描述信息-->
		<locator type="xpath" timeout="3" value="//input[@id='loginname']"  desc="用户名">用户名输入框</locator>
		<locator type="id" timeout="3" value="nloginpwd"  desc="密码">密码输入框</locator>
		<locator type="id" timeout="3" value="loginsubmit"  desc="登录">登录按钮</locator>
	</page>
</map>
```
对象库文件编写后，运行/src/main/java/org/webdriver/patatiumwebui/PageObjectConfig/PageObjectAutoCode.java 文件生成对象库java代码
### 2、公共action封装实例（业务操作）

```
package org.webdriver.patatiumwebui.action;

import org.webdriver.patatiumwebui.pageObject.LoginPage;
import org.webdriver.patatiumwebui.utils.ElementAction;
import org.webdriver.patatiumwebui.utils.TestBaseCase;

import java.io.IOException;

/**
 * Created by zhengshuheng on 2016/8/29 0029.
 */
public class LoginAction extends TestBaseCase{
    public LoginAction(String Url,String UserName,String PassWord) throws IOException
    {
        //此driver变量继承自TestBase变量
        LoginPage loginPage=new LoginPage();
        loginPage.open(Url);
        System.out.println(driver.getCurrentUrl());
        ElementAction action=new ElementAction();
        action.clear(loginPage.密码输入框());
        action.type(loginPage.用户名输入框(),UserName);
        action.clear(loginPage.密码输入框());
        action.type(loginPage.密码输入框(),PassWord);
        action.click(loginPage.登录按钮());
    }
}

```
公共Action代码放在src/main/java/org/webdriver/patatiumwebui/Action 包下
### 3、驱动数据来源实例

1、在src/main/resources/data下创建loginData.xls文件
编写如下内容
![输入图片说明](http://git.oschina.net/uploads/images/2016/0829/123627_cb6607c8_482055.png "在这里输入图片标题")
### 4、测试用例编写

普通测试用例：
```
        @Test(description="登录成功测试")
	@Parameters({"BaseUrl"})//读取testng.xml参数
	public void login(String BaseUrl) throws IOException
	{
		//调用登录方法，需输入正确的用户名和密码
		LoginAction loginAction=new LoginAction(BaseUrl+"/new/login.aspx","11111","abc123");
		action.sleep(2);
		//设置检查点
		Assertion.VerityTextPresentPrecision("jd_1111","输入正确的用户名和密码，验证是否成功进入主页");
		//设置用例断言，判断用例是否失败
		Assertion.VerityError();
	}
```
数据驱动测试用例：
```
       //数据驱动案例--start
	@DataProvider(name="longinData")
	public Object[][] loginData()
	{
		//读取登录用例测试数据
		String filePath="src/main/resources/data/loginData.xls";
		//读取第一个sheet，第2行到第5行-第2到第4列之间的数据
		return ExcelReadUtil.case_data_excel(0, 1, 4, 1, 3,filePath);
	}
	@Test(description="登录失败用例",dataProvider = "longinData")
	public void loginFail (String userName,String password,String message) throws IOException, DocumentException {
		//代替testng参数化的方法
		String BaseUrl= XmlReadUtil.getTestngParametersValue("testng.xml","BaseUrl");
		//调用登录方法
		LoginAction loginAction=new LoginAction(BaseUrl+"/new/login.aspx",userName,password);
		action.sleep(1);
		//设置检查点
		Assertion.VerityTextPresent(message,"验证是否出现预期的错误提示信息:"+message);
		//设置断言
		Assertion.VerityError();
	}
	//数据驱动案例--end
```
测试用例代码放在src/test/java 包下
### 5、testng.xml配置

普通串行执行配置（只用一个浏览器跑用例）
```
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Suite" >
	<parameter name="driver" value="FirefoxDriver" /> <!--测试浏览器：支持火狐，谷歌，IE-->
	<parameter name="nodeURL" value="" /> <!--selenium grid分布式运行node节点url，如不用分布式运行，则留空-->
	<parameter name="BaseUrl" value="https://passport.jd.com" />  <!-- 测试系统基础Url-->
	<parameter name="UserName" value="" /> <!-- 系统登录用户名-->
	<parameter name="PassWord" value="" />  <!-- 系统登录密码-->
    <parameter name="smtpUserName" value="" />  <!-- 测试报告邮件发送：smtp身份证验证-->
    <parameter name="smtpPassWord" value="" />  <!-- 测试报告邮件发送：smtp身份证验证-->
    <parameter name="smtpHost" value="" />  <!-- 测试报告邮件发送：smtp主机地址-->
    <parameter name="smtpPort" value="" />  <!-- 测试报告邮件发送：smtp主机端口-->
    <parameter name="mailTitle" value="Webdriver中文社区-自动化测试报告" />  <!-- 测试报告邮件发送：邮件标题-->
    <parameter name="logUrl" value="" />  <!-- 测试报告邮件发送：用例运行日志url-->
    <parameter name="reportUrl" value="" />  <!-- 测试报告邮件发送：完整测试报告url-->
	<parameter name="recipients" value="" /> <!-- 测试报告邮件发送：收件人，多个用,号隔开-->
    <parameter name="reportTitle" value="Webdriver中文社区-自动化测试报告" />  <!--测试报告标题-->
	<listeners><!-- 监听器设置-->
        <listener class-name="org.webdriver.patatiumwebui.utils.TestListener"></listener>
        <listener class-name="org.webdriver.patatiumwebui.utils.TestReport"></listener>
    </listeners>
     <test name="登录失败测试用例：数据驱动"> <!-- 测试用例描述-->
    <classes>
      <class name="LoginTest">
      	     <methods >
                   <include name="loginFail" />
             </methods>
       </class>
    </classes>
  </test> <!-- Test -->
    <test name="登录成功测试用例">
        <classes>
            <class name="LoginTest">
                <methods >
                    <include name="login" />
                </methods>
            </class>
        </classes>
    </test> <!-- Test -->
</suite> <!-- Suite -->

```
串行执行多个浏览器配置

```
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Suite">
	<parameter name="driver" value="FirefoxDriver" /> <!--测试浏览器：支持火狐，谷歌，IE-->
	<parameter name="nodeURL" value="" /> <!--selenium grid分布式运行node节点url，如不用分布式运行，则留空-->
	<parameter name="BaseUrl" value="https://passport.jd.com" />  <!-- 测试系统基础Url-->
	<parameter name="UserName" value="" /> <!-- 系统登录用户名-->
	<parameter name="PassWord" value="" />  <!-- 系统登录密码-->
    <parameter name="smtpUserName" value="trexliushao@163.com" />  <!-- 测试报告邮件发送：smtp身份证验证-->
    <parameter name="smtpPassWord" value="test123456" />  <!-- 测试报告邮件发送：smtp身份证验证-->
    <parameter name="smtpHost" value="" />  <!-- 测试报告邮件发送：smtp主机地址-->
    <parameter name="smtpPort" value="" />  <!-- 测试报告邮件发送：smtp主机端口-->
    <parameter name="mailTitle" value="Webdriver中文社区-自动化测试报告" />  <!-- 测试报告邮件发送：邮件标题-->
    <parameter name="logUrl" value="" />  <!-- 测试报告邮件发送：用例运行日志url-->
    <parameter name="reportUrl" value="" />  <!-- 测试报告邮件发送：完整测试报告url-->
	<parameter name="recipients" value="" /> <!-- 测试报告邮件发送：收件人，多个用,号隔开-->
    <parameter name="reportTitle" value="Webdriver中文社区-自动化测试报告" />  <!--测试报告标题-->
	<listeners><!-- 监听器设置-->
        <listener class-name="org.webdriver.patatiumwebui.utils.TestListener"></listener>
        <listener class-name="org.webdriver.patatiumwebui.utils.TestReport"></listener>
    </listeners>
     <test name="登录失败测试用例：数据驱动"> <!-- 测试用例描述-->
         <parameter name="driver" value="FirefoxDriver" /> <!--测试浏览器：支持火狐，谷歌，IE--><!--在test下配置浏览器信息，可以实现每个test执行不同的浏览器，从而实现一次性串行执行多个浏览器的效果，用于兼容性测试-->
         <parameter name="nodeURL" value="http://192.168.0.178:3155/wd/hub" /> <!--selenium grid分布式运行node节点url，如不用分布式运行，则留空-->
       <classes>
      <class name="LoginTest">
      	     <methods >
                   <include name="loginFail" />
             </methods>
       </class>
    </classes>
  </test> <!-- Test -->
    <test name="登录成功测试用例">
        <parameter name="driver" value="ChormeDriver" /> <!--测试浏览器：支持火狐，谷歌，IE-->
        <parameter name="nodeURL" value="http://192.168.0.178:3166/wd/hub" /> <!--selenium grid分布式运行node节点url，如不用分布式运行，则留空-->
        <classes>
            <class name="LoginTest">
                <methods >
                    <include name="login" />
                </methods>
            </class>
        </classes>
    </test> <!-- Test -->
</suite> <!-- Suite -->
```
testng.xml放在项目根目录下面。
### 6、执行用例

IDE：在IDE集成开发环境下右键testng.xml使用testng运行
Maven:执行mvn clean ;mvn test 命令
Jenkins：1、checkout 项目代码 2、指定pom.xml文件  3、执行mvn clean ;mvn test 命令
### 7、查看测试报告及日志文件

用例执行完毕，会自动发送邮件报告及生成测试报告文件；用例失败会自动截图并将其展示在报告中，也可以将用例失败堆栈信息显示到报表中
测试报告文件生成在项目根目录下test-out目录下report.html文件
报告展示如下：
![输入图片说明](http://git.oschina.net/uploads/images/2016/0829/135306_b9ddfe80_482055.jpeg "在这里输入图片标题")
邮件展示如下：
![输入图片说明](http://git.oschina.net/uploads/images/2016/0829/151927_71ac2b3d_482055.png "在这里输入图片标题")
日志文件展示如下：
![输入图片说明](http://git.oschina.net/uploads/images/2016/0829/140857_d1524893_482055.png "在这里输入图片标题")

### Xpath 详解：

注：可通过火狐浏览器安装,firebug,firepath插件校验xpath的正确性
先举个xpah例子://div[@id=’abc’]/form/div/input/span
//：从匹配选择的当前节点，选择文档中的节点，不考虑它的具体位置，例如：//div[@name=‘abc’]
查找页面中name属性为abc的div标签
/：从根节点选取元素，例如：/html/body/div[@id='myModalex'] 
可以是文档最根节点开始查找元素，也可以是配陪得节点为根节点往下找
例如：//*[@id='loginForm']/div[1]/label
@：@表示属性 属性可以用and,or运算符
例如：//label[@class='col-sm-2 control-label' and @for='userName'] 在定位中，如果一个属性还不能精确定位某个元素那么则可以再组合增加一个元素，使定位达到唯一性
Text（）：通过元素的文本值查找元素，例：//h2[text()='webdriver中文社区']
Contains();//input[contains(@id,'nt')] 模糊匹配，查找id包含nt的input标签
//h2[contains(text(),'webdriver中文社区')] 查找文本值包含webdriver中文社区的元素
//灵活使用案例：
查找元素
```
<span class=”cde”>八佰伴</span>
<span class=”cde”>嘎嘎嘎</span>
<div id=”abc”>
   <form>
            <div>
                   <input>
                      <span class=”cde”>八佰伴</span>
                   </input>
            </div>
   </form>
<div>
```
分析：该元素，没有唯一性的id，name等标签，并且层级多，上一级也没有唯一性的东西，只能从上上上级开始查找元素。但是从上上级查找元素，xpath的层级多，定位信息复杂，那么有没有办法优化精简呢？答案是肯定的，利用//可以大幅优化精简xpath表达式
方案一：//div[@id=’abc’]/form/div/input/span
方案二：//*[@id=’abc’]/form/div/input/span[@class=’cde’]
方案三：//span[@class=’cde’][2]
方案四：//div[@id=’abc’]//span[@class=’cde’]--此方法最简洁，结构也最清晰，也最稳定

综上xpath定位原则，元素id,name属性优先使用，其次是class等其他，1、在当前节点没有id,name等属性确定元素唯一性的时候，往上找，通过当前节点父亲，祖父，祖父的父亲，祖父的祖父等节点查找当前元素。2、一个元素属性不足够定位当前元素的时候，可以通过and运算符，组合属性来定位使之达到唯一性，尽可能的缩短xpath层级，使xpath定位更稳定。

### Firebug使用：

例：
定位用户名输入框可以用//*[@id=’userName’]表示查找当前页面下，id属性为’userName’的所有元素，相当于id定位方式。*代表所有元素。
![输入图片说明](http://git.oschina.net/uploads/images/2016/0829/141110_462d5c6d_482055.png "在这里输入图片标题")
#License
```
GPL V2.0
```
### 如果您觉得这个产品对您有用，您可以捐助下我，让我有理由继续下去，非常感谢。

![输入图片说明](http://git.oschina.net/uploads/images/2016/0829/144253_111773ec_482055.jpeg "在这里输入图片标题")
<h3>您也可以加入webdriver中文社区交流QQ群： 471137382，欢迎给我提建议和bug。
或者给我邮件：609958331@qq.com
现在微信捐助无法得知捐助人昵称，欢迎添加我的个人微信:zhengshuheng002</h3>
#非常感谢以下朋友的帮助
<table>
 <therd>
     <tr>
         <td>名字</td>
         <td>金额</td>
         <td>方式</td>
         <td>说明</td>
     </tr>
 </therd>
  <tbody>
    <tr>
      <td>月波</td>
      <td>8.8</td>
      <td>支付宝</td>
      <td>郑工，希望你空闲时间多发表自动化测试的文章</td>
    </tr>
      <tr>
      <td>Jeff</td>
      <td>66.66</td>
      <td>微信</td>
      <td>支持你!好榜样！</td>
    </tr>
  </tbody>
</table>
