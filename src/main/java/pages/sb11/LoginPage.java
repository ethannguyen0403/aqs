package pages.sb11;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;
import common.SBPConstants;

public class LoginPage {
    public TextBox txtUsername = TextBox.xpath("//input[@formcontrolname='username']");
    public TextBox txtPassword = TextBox.xpath("//input[@formcontrolname='password']");
    public TextBox txtCode = TextBox.xpath("//input[@formcontrolname='code']");
    public Button btnLogin = Button.xpath("//button[contains(@class,'btn-login')]");
    public Button btnCopyRight = Button.xpath("//em[contains(@class, 'far fa-copyright')]");
    Label tabEurope = Label.xpath("//app-login-camouflage//div[contains(@class,'top-panel')]//ul/li/span[text()='EUROPE']");
    Label lblClick = Label.xpath("//app-login-camouflage//div[contains(@class,'main-content')]/div/div/div[5]//span");
    private void openLoginForm(){
        tabEurope.click();
        lblClick.waitForControlInvisible(1,1);
        lblClick.click();
        txtUsername.isDisplayed(1);
    }

    public WelcomePage login(String username, String password){
        openLoginForm();
        txtUsername.sendKeys(username);
        txtPassword.sendKeys(password);
        if(txtCode.isDisplayed()){
            String today= DateUtils.getDate(0,"yyyyMd", SBPConstants.GMT_8);
            String code = String.format("%s%s@))*",today,username);
            txtCode.sendKeys(code);
        }
        btnLogin.click();
        txtUsername.isDisplayedShort(10);
        WelcomePage welcomePage = new WelcomePage();
        welcomePage.waitSpinnerDisappeared();
        return welcomePage;
    }
}
