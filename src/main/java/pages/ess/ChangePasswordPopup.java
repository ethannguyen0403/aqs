package pages.ess;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.Popup;
import com.paltech.element.common.TextBox;

import static common.ESSConstants.CHANGE_PASSWORD;
import static testcases.BaseCaseAQS.homePage;

public class ChangePasswordPopup {
    public Popup popup = Popup.xpath("//app-change-password");
    public Label lblTitle = Label.xpath("//app-change-password//h3");
    public TextBox txtCurrentPassword = TextBox.name("currentPass");
    public TextBox txtNewPassword = TextBox.name("newPass");
    public TextBox txtConfirmPassword = TextBox.name("confirmPass");
    public Button btnClose = Button.xpath("//button[contains(text(), 'Close')]");
    public Button btnUpdate = Button.xpath("//button[contains(text(), 'Update')]");
    public Label messageSuccess = Label.xpath("//p[contains(@class, 'text-success')]");

//    public ChangePasswordPage(By locator, String xpathExpression){
//        super(locator);
//        this._xPath = xpathExpression;
//        txtCurrentPassword = TextBox.xpath(String.format("%s//input[@name='currentPass']",this._xPath));
//        txtNewPassword = TextBox.xpath(String.format("%s//input[@name='newPass']",this._xPath));
//        txtConfirmPassword = TextBox.xpath(String.format("%s//input[@name='confirmPass']",this._xPath));
//        btnClose = Button.xpath(String.format("%s//button[@class='btn btn-light mr-auto']",this._xPath));
//        btnUpdate = Button.xpath(String.format("%s//button[@class='btn btn-primary']",this._xPath));
//    }

//    public static ChangePasswordPage xpath(String xpathExpression) {
//        return new ChangePasswordPage(By.xpath(xpathExpression), xpathExpression);
//    }

    public String changePassword(String oldPassword, String newPassword, String confirmPassword, boolean isClose) {
        txtCurrentPassword.sendKeys(oldPassword);
        txtNewPassword.sendKeys(newPassword);
        txtConfirmPassword.sendKeys(confirmPassword);
        btnUpdate.click();
        String message = messageSuccess.getText();
        if (isClose){
            btnClose.click();
            txtCurrentPassword.isDisplayedShort(1);
        }
        return message;
    }
}


