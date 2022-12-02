package pages.ess;

import com.paltech.element.common.Button;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;

public class LoginPage {
    public TextBox txtUsername = TextBox.xpath("//input[@formcontrolname='username']");
    public TextBox txtPassword = TextBox.xpath("//input[@formcontrolname='password']");
    public TextBox txtCode = TextBox.xpath("//input[@formcontrolname='code']");
    public Button btnLogin = Button.xpath("//button[contains(@class,'btn-login')]");

    public Button btnCopyRight = Button.xpath("//em[contains(@class, 'far fa-copyright')]");

    public BetOrderPage login(String username,String password){
        btnCopyRight.isDisplayed(2);
        btnCopyRight.click();
        txtUsername.sendKeys(username);
        txtPassword.sendKeys(password);
        if(txtCode.isDisplayed()){
            String today= DateUtils.getDate(0,"yyyyMMd","GMT+7");
            System.out.println(today);
            String code = String.format("%s%s@))*",today,username);
            txtCode.sendKeys(code);
        }
        btnLogin.click();
        txtUsername.isDisplayedShort(10);
        return new BetOrderPage();
    }

}
