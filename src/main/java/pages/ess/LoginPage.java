package pages.ess;

import com.paltech.element.common.Button;
import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;

public class LoginPage {
    public TextBox txtUsername = TextBox.xpath("//input[@formcontrolname='username']");
    public TextBox txtPassword = TextBox.xpath("//input[@formcontrolname='password']");
    public Icon icEyePwd = Icon.xpath("//input[@formcontrolname='password']//preceding-sibling::span//em[contains(@class,'fa-eye-slash')]");
    public TextBox txtCode = TextBox.xpath("//input[@formcontrolname='code']");
    public Icon icEyeCode = Icon.xpath("//input[@formcontrolname='code']//preceding-sibling::span//em[contains(@class,'fa-eye-slash')]");
    public Button btnLogin = Button.xpath("//button[contains(text(),'Login')]");
    public Button btnCopyRight = Button.xpath("//em[contains(@class, 'far fa-copyright')]");
    public Label tabHome = Label.xpath("//app-login-camouflage//div[contains(@class,'justify-content-center')]/button[1]");
    private Label tabEurope = Label.xpath("//app-login-camouflage//div[contains(@class,'justify-content-center')]/button[2]");
    private Label lblClick = Label.xpath("//app-login-camouflage//carousel//div[@class='carousel-inner']/slide[2]//div[contains(@class,'content-text content-col-3')]/div[5]//span");
    private void openLoginForm(){
        tabEurope.click();
        lblClick.click();
        txtUsername.isDisplayed(1);
    }

    private void openLoginFormOld(){
        btnCopyRight.isDisplayed(2);
        btnCopyRight.click();
    }
    public BetOrderPage login(String username,String password){
       // openLoginFormOld();
      openLoginForm();
        txtUsername.sendKeys(username);
        icEyePwd.click();
        txtPassword.sendKeys(password);
        if(txtCode.isDisplayed()){
            String today= DateUtils.getDate(0,"yyyyMd","GMT+7");
            String code = String.format("%s%s@))*",today,username);
            icEyeCode.click();
            txtCode.sendKeys(code);
        }
        btnLogin.click();
        BetOrderPage betOrderPage = new BetOrderPage();
        betOrderPage.lblBetOrders.waitForControlInvisible();
        return betOrderPage;
    }

}
