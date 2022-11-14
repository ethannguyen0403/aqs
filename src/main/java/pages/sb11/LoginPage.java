package pages.sb11;

import com.paltech.element.common.Button;
import com.paltech.element.common.TextBox;

public class LoginPage {
    public TextBox txtUsername = TextBox.xpath("//input[@formcontrolname='username']");
    public TextBox txtPassword = TextBox.xpath("//input[@formcontrolname='password']");
    public Button btnLogin = Button.xpath("//button[contains(@class,'btn-login')]");
    public Button btnCopyRight = Button.xpath("//em[contains(@class, 'far fa-copyright')]");

    public WelcomePage login(String username, String password){
        btnCopyRight.isDisplayed(2);
        btnCopyRight.click();
        txtUsername.sendKeys(username);
        txtPassword.sendKeys(password);
        btnLogin.click();
        txtUsername.isDisplayedShort(10);
        return new WelcomePage();
    }

}
