package pages.sb11.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.Popup;
import com.paltech.element.common.TextBox;

import static common.ESSConstants.CHANGE_PASSWORD;
import static testcases.BaseCaseAQS.homePage;

public class ChangePasswordPopup {
    public Popup popup = Popup.xpath("//app-change-password");
    public Label lblTitle = Label.xpath("//app-change-password//h5");
    public TextBox txtCurrentPassword = TextBox.name("currentPass");
    public TextBox txtNewPassword = TextBox.name("newPass");
    public TextBox txtConfirmPassword = TextBox.name("confirmPass");
    public Button btnClose = Button.xpath("//button[contains(text(), 'Close')]");
    public Button btnUpdate = Button.xpath("//button[contains(text(), 'Update')]");
    public Label messageSuccess = Label.xpath("//p[contains(@class, 'text-success')]");

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