#PatatiumAppUi
<h1>更新<h1>
  <h3>20161222增加对YAML管理对象库的支持</h3>
   对象库编写支持YAML支持，格式如下：
```
pages:
    - page:
       pageName: org.webdriver.patatiumappui.pageObject.StartPage
       value: "www.baidu.com"
       desc: "微信APP启动首页"
       locators:
          - {type: "id",timeout: "3",value: "com.tencent.mm:id/c72",desc: "登录",name: "登录"}
          - {type: "id",timeout: "3",value: "com.tencent.mm:id/c71",desc: "注册",name: "注册"}
    - page:
        pageName: org.webdriver.patatiumappui.pageObject.LoginPage
        value: ""
        desc: "微信App登录页面"
        locators:
          - {type: "id",timeout: "3" , value: "com.tencent.mm:id/b9i", desc: "使用其他方式登录",name: "使用其他方式登录"}
          - {type: "xpath",timeout: "3" ,value: "//android.widget.EditText[@text='QQ号/微信号/Email']", desc: "账号",name: "账号输入框"}
          - {type: "xpath",timeout: "3" ,value: "//android.widget.EditText[@NAF='1']", desc: "密码",name: "密码输入框"}
          - {type: "id",timeout: "3" ,value: "com.tencent.mm:id/b8z", desc: "登录",name: "登录按钮"}
          - {type: "id",timeout: "3" ,value: "com.tencent.mm:id/bl3", desc: "失败提示信息确认信息",name: "登录失败提示信息"}
          - {type: "id",timeout: "3" ,value: "com.tencent.mm:id/a_r", desc: "失败提示信息确认按钮",name: "登录失败确认按钮"}
```
编写完后运行PageObjectAutoCodeForYaml.java 类
<h1>简介</h1>
这是一个AppUi自动化测试框架，由webdriver中文社区创办人土豆(本人技术笔名)所创建,该APP自动化测试框架是用java语言编写的，基于selenium webdriver Appium的开源自动化测试框架，该框架结合了testng,selenium,webdriver，Appium,jxl，jodd-http 等工具。该框架基于页面对象模型（POM）架构，实现了关键字驱动技术，数据驱动,无需掌握多少编程知识即可编写脚本，同时实现了数据与代码分离的功能：1、元素定位信息保存在对象库文件中 2、测试用例数据可以存储在excel中。从而实现，页面元素位置变化，无需改动脚本，只需修改对应的元素定位信息即可。
该框架实现了检查点及用例失败自动截图功能，自动生成html测试报告及自动发送html邮件测试报告功能。
目前框架还不是特别完善，还需要写一些脚本实现自动化；学习该框架需要熟悉一定的安卓APP 和java基础，后续可以考虑自动编码的实现
<h1>主要功能</h1>
1、实现关键字驱动技术，编写用例简单
2、实现数据驱动技术，减少用例代码
3、支持元素对象库管理，页面元素信息与代码分离
3、支持检查点、用例断言设置
4、检查点失败截图，一个检查点失败不影响用例后续执行
5、用例失败自动截图
6、用例之间依赖少，可以自由组合测试用例执行
7、支持安卓系统常用触摸操作
8、支持APP控件常用操作
9、用例集执行完毕自动生成简介美观的html报告
10、用例执行完毕自动发送详实的html邮件报告，可拓展为有失败用例才发送。
<h1>Api文档</h1>
<a href="http://www.webdriver.org/doc/patatiumappui/api/index.html" target="_blank">点击查看api文档</a>
<h1>环境配置</h1>
1、JDK1.8
2、IDEA\Eclipse
3、Android SDK 具体安装参考：<a href="http://www.webdriver.org/article-52-1.html" target="_blank">http://www.webdriver.org/article-52-1.html</a>
4、Maven
5、一台安卓手机或者安卓模拟器，推荐夜神安卓模拟器，下载地址：<a href="http://www.yeshen.com/" target="_blank">http://www.yeshen.com/</a>
6、Appium Server端，下载地址：<a href="http://pan.baidu.com/s/1jIxzSfO" target="_blank">http://pan.baidu.com/s/1jIxzSfO</a>
<h1>注意事项</h1>
工程项目编码需要设置成UTF-8否则会出现中文乱码情况
<h1>Demo演示</h1>
Demo演示视频地址：<a href="http://v.youku.com/v_show/id_XMTcxMTY1MzE0NA==.html?beta&" target="_blank">http://v.youku.com/v_show/id_XMTcxMTY1MzE0NA==.html?beta&</a>
<h3>一、创建对象库</h3>
1、通过Android SDK工具 uiautomatorviewer.bat 获取app元素定位信息，具体使用参考：<a href="http://www.webdriver.org/article-53-1.html" target="_blank">http://www.webdriver.org/article-53-1.html</a>
2、UILibrary.xml 对象库文件编写
```
<?xml version="1.0" encoding="UTF-8"?>
<!--整个对象库文件的根目录，管理整个项目的对象-->
<map>
    <!--管理一个页面的元素（webelement：input,select,textare,a,li等标签），一个page包含多个locator对象
    Pagename:page对象名字，格式：org.webdriver.patatiumappui.pageObject.xxxPage;最后面那位才是真正的页面名字，前面的是java对象库路径；
    另外注意，页面名字是头个单词大写；例如主页：名字定义为 org.webdriver.patatiumappui.pageObject.HomePage
    Value：页面对象的URL，可不填。
    Desc:页面对象中文描述-->
    <page pagename="org.webdriver.patatiumappui.pageObject.StartPage" value="" desc="微信APP启动首页">
        <!--管理一个页面的元素（webelement：input,select,textare,a,li等标签），一个page包含多个locator对象
        Type：定位方式，包含id,name,class,linktext,xpath,css等，定位元素的时候灵活使用，一般可以统一用xpath
        代替id,name,class，linktext的定位方式。
        Timeout：元素加载时间，有些页面元素，可能要等待一段时间才能加载过来，为了查找元素的稳定性，需加等待时间。
        Value:元素定位信息，如果是id,name,class，linktext直接把网页元素对应的这些属性值写上即可，如果是xpath定位方式，
        需要填写正确的xpath语法格式。
        Desc:元素的描述，元素的中文描述信息-->
		<locator type="id" timeout="3" value="com.tencent.mm:id/c4k"  desc="登录">登录</locator>
		<locator type="id" timeout="3" value="com.tencent.mm:id/cuh"  desc="注册">注册</locator>
	</page>
	<page pagename="org.webdriver.patatiumappui.pageObject.LoginPage" value="" desc="微信App登录页面">
	   <locator type="id" timeout="3" value="com.tencent.mm:id/b6c"  desc="使用其他方式登录">使用其他方式登录</locator>
		<locator type="id" timeout="3" value="com.tencent.mm:id/b5r"  desc="账号">账号输入框</locator>
		<locator type="id" timeout="3" value="com.tencent.mm:id/b5s"  desc="密码">密码输入框</locator>
		<locator type="id" timeout="3" value="com.tencent.mm:id/b5t"  desc="登录">登录按钮</locator>
		<locator type="id" timeout="3" value="com.tencent.mm:id/avt"  desc="失败提示信息确认按钮">登录失败提示信息</locator>
		<locator type="id" timeout="3" value="com.tencent.mm:id/bim"  desc="失败提示信息确认按钮">登录失败确认按钮</locator>
    </page>
</map>
```
编写完后，运行/src/main/java/org/webdriver/patatiumappui/PageObjectConfig/PageObjectAutoCode.java 文件生成对象库java代码
<h3>二、公共action封装实例（业务操作）</h3>
```
/**
 * Created by zhengshuheng on 2016/9/2 0002.
 */
public class LoginAction extends TestBaseCase {
    public  LoginAction(String username,String password) throws IOException {
        ElementAction action=new ElementAction();
        LoginPage loginPage=new LoginPage();
        action.click(loginPage.账号输入框());
        action.clear(loginPage.账号输入框());
        action.type(loginPage.账号输入框(),username);
        action.click(loginPage.密码输入框());
        action.clear(loginPage.密码输入框());
        action.type(loginPage.密码输入框(),password);
        action.sleep(1);
        action.click(loginPage.登录按钮());
    }
}

```
<h3>三、驱动数据来源实例</h3>
在src/main/resources/data下创建loginData.xls文件
![输入图片说明](http://git.oschina.net/uploads/images/2016/0903/210055_8e091e1d_482055.png "在这里输入图片标题")
<h3>四、测试用例编写</h3>
```
/**
 * Created by zhengshuheng on 2016/9/2 0002.
 */
public class LoginTest extends TestBaseCase {
    ElementAction action=new ElementAction();
    @BeforeClass
    public  void  beforeclass() throws IOException {
        StartPage startPage=new StartPage();
        action.click(startPage.登录());
        LoginPage loginPage=new LoginPage();
        action.sleep(2);
        action.click(loginPage.使用其他方式登录());
        action.sleep(2);
    }
    @Test(description = "登录测试")
    public  void login() throws IOException {
        //调用登录方法(需填写正确的用户名和密码)
        new LoginAction("655433", "gg");
        action.sleep(5);
        //设置检查点
        Assertion.VerityTextPresent("通讯录","验证是否登录成功！");
        //设置断言 。判断用例是否失败
        Assertion.VerityError();
    }
    //数据驱动案例--start
    @DataProvider(name="longinData")
    public Object[][] loginData()
    {
        //读取登录用例测试数据
        String filePath="src/main/resources/data/loginData.xls";
        //读取第一个sheet，第2行到第5行-第2到第4列之间的数据
        return ExcelReadUtil.case_data_excel(0, 1, 2, 1, 3,filePath);
    }
    @Test(description="登录失败用例:数据驱动例子",dataProvider = "longinData")
    public void loginFail (String userName,String password,String message) throws IOException, DocumentException {
        //调用登录方法
        new  LoginAction(userName,password);
        LoginPage loginPage=new LoginPage();
        action.sleep(4);
        log.info("登录失败信息："+action.getText(loginPage.登录失败提示信息()));
        Assertion.VerityCationString(action.getText(loginPage.登录失败提示信息()),message,"验证是否出现预期的错误提示信息:"+message);
        action.click(loginPage.登录失败确认按钮());
        //设置断言
        Assertion.VerityError();
    }
}

```
<h3>五、Testng.xml配置</h3>
tesng.xml需配置app主包名，主类名，SDK版本，Device Name等信息
Device Name：在cmd命令下通过adb devices 获取
![输入图片说明](http://git.oschina.net/uploads/images/2016/0903/210836_afcc8ce0_482055.png "在这里输入图片标题")
App 主包名，主类名等在Appium Server GUI 获取
![输入图片说明](http://git.oschina.net/uploads/images/2016/0903/211315_02b35374_482055.png "在这里输入图片标题")
App安装包apk文件放在项目根目录下apps目录里，tesng.xml需指定运行apk文件名
```
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Suite" >
    <parameter name="driverName" value="AndroidDriver" />   <!--driver驱动：安卓，IOS-->
    <parameter name="nodeURL" value="127.0.0.1:4723" /> <!--appium-server 地址-->
    <parameter name="appName" value="weixin_861.apk" />  <!--app包名字-->
    <parameter name="platformName" value="Android" /> <!--app运行平台:安卓，IOS-->
    <parameter name="deviceName" value="127.0.0.1:62001" /> <!--手机或者虚拟机设备名字-->
    <parameter name="sdkVersion" value="6.0" /><!--安卓,IOS SDK版本-->
    <parameter name="appMainPackage" value="com.tencent.mm" /><!--app主包名-->
    <parameter name="appActivity" value="com.tencent.mm.ui.LauncherUI" /> <!--app 主类名-->
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
        <listener class-name="org.webdriver.patatiumappui.utils.TestListener"/>
        <listener class-name="org.webdriver.patatiumappui.utils.TestReport"/>
    </listeners>
    <test name="登录测试">
        <classes>
            <class name="LoginTest">
                <methods>
                    <include name="login"/>
                </methods>
            </class>
        </classes>
    </test>
    <test name="登录失败测试：数据驱动">
        <classes>
            <class name="LoginTest">
                <methods>
                    <include name="loginFail"/>
                </methods>
            </class>
        </classes>
    </test>
</suite> <!-- Suite -->
```
<h3>六、执行用例</h3>
IDE：在IDE集成开发环境下右键testng.xml使用testng运行
Maven:执行mvn clean ;mvn test 命令
Jenkins：1、checkout 项目代码 2、指定pom.xml文件 3、执行mvn clean ;mvn test 命令
<h3>七、查看测试报告及日志文件</h3>
用例执行完毕，会自动发送邮件报告及生成测试报告文件；用例失败会自动截图并将其展示在报告中，也可以将用例失败堆栈信息显示到报表中
测试报告文件生成在项目根目录下test-out目录下report.html文件
报告展示如下：<a href="http://www.webdriver.org/doc/patatiumappui/report/report.html" target="_blank">点击查看在线报告</a>
![输入图片说明](http://git.oschina.net/uploads/images/2016/0903/212710_e5185410_482055.png "在这里输入图片标题")
测试日志展示如下：
![输入图片说明](http://git.oschina.net/uploads/images/2016/0903/212830_2ff8aa61_482055.png "在这里输入图片标题")
测试邮件展示如下：
![输入图片说明](http://git.oschina.net/uploads/images/2016/0903/213029_66ca45e5_482055.png "在这里输入图片标题")
#License
GPL V2.0
<h2>如果您觉得这个产品对您有用，您可以捐助下我，让我有理由继续下去，非常感谢。</h2>
![输入图片说明](http://git.oschina.net/uploads/images/2016/0829/144253_111773ec_482055.jpeg "在这里输入图片标题")
<h2>您可以加我个人QQ:609958331，,也可以加入webdriver中文社区交流QQ群： 471137382，欢迎给我提建议和bug。 或者给我邮件：609958331@qq.com 现在微信捐助无法得知捐助人昵称，欢迎添加我的个人微信:zhengshuheng002</h2>
