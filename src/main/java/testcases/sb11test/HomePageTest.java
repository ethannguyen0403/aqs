package testcases.sb11test;

import com.paltech.utils.StringUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.popup.ChangePasswordPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.ESSConstants.*;

public class HomePageTest extends BaseCaseAQS {

    @Test(groups = {"regression","ethan6.0"})
    @Parameters("username")
    @TestRails(id = "2061")
    public void Homepage_TC_2061(){
        log("@title: Validate menu in header section is correctly display");
        log("@Step 1: Login with valid account");
        log(" Validate logo: 123sbasia display");
        Assert.assertTrue(welcomePage.iconLogo.isDisplayed(),"FAILED! Profile icon is not displayed");
        log("Validate menu header display as: Role, User, Sport,  Soccer, Accounting, Trading, Master, General Reports, Invoice");
        Assert.assertEquals(welcomePage.headerMenuControl.getListMenu(), SBPConstants.TABLE_HEADER,"FAILED! Header menu is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @Parameters("username")
    @TestRails(id = "2062")
    public void Homepage_TC_2062(){
        log("@title: Validate Change Password popup display");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Change Password");
        ChangePasswordPopup changePasswordPopup = welcomePage.openChangePasswordPopup();

        log("Validate Change password popup display with correct info");
        Assert.assertTrue(changePasswordPopup.popup.isDisplayed());
        Assert.assertEquals(changePasswordPopup.lblTitle.getText(), CHANGE_PASSWORD,"Failed! Change password title is incorrect");
        Assert.assertTrue(changePasswordPopup.txtCurrentPassword.isDisplayed(),"Failed! Current Password is not displayed");
        Assert.assertTrue(changePasswordPopup.txtNewPassword.isDisplayed(),"Failed! New Password is not displayed");
        Assert.assertTrue(changePasswordPopup.txtConfirmPassword.isDisplayed(),"Failed! Confirm Password is not displayed");
        Assert.assertEquals(changePasswordPopup.btnClose.getText(),BTN_CLOSE,"FAILED! Close button does not display");
        Assert.assertEquals(changePasswordPopup.btnUpdate.getText(),BTN_UPDATE,"FAILED! Update button does not display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @Parameters({"username","password"})
    @TestRails(id = "2063")
    public void Homepage_TC_2063(String username, String password) throws Exception {
        log("@title: Validate that changing password is worked correctly");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Change Password");
        String currentPass = StringUtils.decrypt(password);
        String newPass = "1234qwert";
        try {
            String messageSuccess = welcomePage.changePassword(currentPass,newPass,newPass,true);

            log("Verify can change password");
            Assert.assertEquals(messageSuccess, ChangePassword.MESSAGE_SUCCESS, "FAILED! Success message when change password is incorrect");

            log("@Step 4: Re-login with new pw");
            loginSB11Page = welcomePage.logout();
            welcomePage = loginSB11Page.login(username, newPass);
            Assert.assertTrue(welcomePage.iconLogo.isDisplayed(), String.format("ERROR: cannot login after change password for login account", username));
            log("INFO: Executed completely");
        }finally {
            /**Post-condition: re-update new password to old password */
            welcomePage.changePassword(newPass,currentPass,currentPass,true);
        }
        log("INFO: Executed completely");
    }
}
