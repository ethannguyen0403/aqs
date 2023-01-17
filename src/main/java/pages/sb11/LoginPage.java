package pages.sb11;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;

public class LoginPage {
    public TextBox txtUsername = TextBox.xpath("//input[@formcontrolname='username']");
    public TextBox txtPassword = TextBox.xpath("//input[@formcontrolname='password']");
    public TextBox txtCode = TextBox.xpath("//input[@formcontrolname='code']");
    public Button btnLogin = Button.xpath("//button[contains(@class,'btn-login')]");
    public Button btnCopyRight = Button.xpath("//em[contains(@class, 'far fa-copyright')]");
    private Label tabEurope = Label.xpath("//app-login-camouflage//div[contains(@class,'top-panel')]//ul/li/span[text()='EUROPE']");
    private Label lblClick = Label.xpath("//app-login-camouflage//div[contains(@class,'main-content')]/div/div/div[5]//span");
    private void openLoginForm(){
        tabEurope.click();
        lblClick.waitForControlInvisible(1,1);
        lblClick.click();
        txtUsername.isDisplayed(1);
    }
    private void openLoginFormOld(){
       btnCopyRight.isDisplayed(2);
        btnCopyRight.click();
    }

    public WelcomePage login(String username, String password){
      //openLoginFormOld();
        openLoginForm();
        txtUsername.sendKeys(username);
        txtPassword.sendKeys(password);
        if(txtCode.isDisplayed()){
            String today= DateUtils.getDate(0,"yyyyMd","GMT+7");
            String code = String.format("%s%s@))*",today,username);
            txtCode.sendKeys(code);
        }
        btnLogin.click();
        txtUsername.isDisplayedShort(10);
        return new WelcomePage();
    }

}
