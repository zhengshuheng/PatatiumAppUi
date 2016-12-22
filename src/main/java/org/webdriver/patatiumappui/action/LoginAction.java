package org.webdriver.patatiumappui.action;

import org.webdriver.patatiumappui.pageObject.LoginPage;
import org.webdriver.patatiumappui.utils.ElementAction;
import org.webdriver.patatiumappui.utils.TestBaseCase;

import java.io.IOException;

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
