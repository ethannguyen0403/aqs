package testcases.aqstest;

import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ess.ChangePasswordPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.ESSConstants.*;

public class HeaderTest extends BaseCaseAQS {

    @TestRails(id = "461")
    @Test(groups = {"smoke"})
    public void HeaderTC_C461(){
        log("@title: Verify menu in header section is correctly display");
        log("Verify logo AQS is displayed");
        Assert.assertTrue(betOrderPage.iconLogo.isDisplayed(),"FAILED! Logo AQS is not displayed");
        log("Verify Role, Manager, Master Account, Account, AQS menu are displayed");
        Assert.assertEquals(betOrderPage.menuRole.getText(),HomePage.ROLE,"Failed! Role menu is not displayed");
        Assert.assertEquals(betOrderPage.menuManager.getText(),HomePage.MANAGER,"Failed! Manager menu is not displayed");
        Assert.assertEquals(betOrderPage.menuMasterAccount.getText(),HomePage.MASTERACCOUNT,"Failed! Master Account menu is not displayed");
        Assert.assertEquals(betOrderPage.menuAccount.getText(),HomePage.ACCOUNT,"Failed! Account menu is not displayed");
        Assert.assertEquals(betOrderPage.menuAQS.getText(),HomePage.AQS,"Failed! AQS menu is not displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "462")
    @Test(groups = {"smoke"})
    public void HeaderTC_C462(){
        log("@title: Verify Change Password menu display");
        log("@Step 1: Click on profile icon");
        betOrderPage.ddlMenu.clickMenu(CHANGE_PASSWORD);
        List<String> lstMenuHeader = betOrderPage.ddlMenu.getMenuList();
        log("Verify Change password icon display");
        Assert.assertEquals(lstMenuHeader.get(0), CHANGE_PASSWORD,"FAILED! Change Password menu is not displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "463")
    @Test(groups = {"smoke"})
    public void HeaderTC_C463(){
        log("@title: Verify Change Password popup display");
        log("@Step 1: Click on profile icon");
        log("@Step 2: Click on Change Password");
        betOrderPage.ddlMenu.clickMenu(CHANGE_PASSWORD);
        //Wait 2s for change password popup display
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ChangePasswordPopup changePasswordPage = new ChangePasswordPopup();
        log("Verify Change password popup display with correct info");
        Assert.assertTrue(changePasswordPage.popup.isDisplayed());
        Assert.assertEquals(changePasswordPage.lblTitle.getText(), CHANGE_PASSWORD,"Failed! Change password title is incorrect");
        Assert.assertTrue(changePasswordPage.txtCurrentPassword.isDisplayed(),"Failed! Current Password is not displayed");
        Assert.assertTrue(changePasswordPage.txtNewPassword.isDisplayed(),"Failed! New Password is not displayed");
        Assert.assertTrue(changePasswordPage.txtConfirmPassword.isDisplayed(),"Failed! Confirm Password is not displayed");
        Assert.assertEquals(changePasswordPage.btnClose.getText(),BTN_CLOSE,"FAILED! Close button does not display");
        Assert.assertEquals(changePasswordPage.btnUpdate.getText(),BTN_UPDATE,"FAILED! Update button does not display");
        log("INFO: Executed completely");
    }
    @TestRails(id = "464")
    @Test(groups = {"smoke"})
    @Parameters({"username","password"})
    public void HeaderTC_C464(String username, String password) throws Exception {
        log("@title: Verify Can change password success");
        log("@Step 1: Click on profile icon");
        log("@Step 2: Click on Change Password");
        log("@Step 3: Change password");
        String currentPass = StringUtils.decrypt(password);
        String newPass = "1234qwert";
        try {
        String messageSuccess = betOrderPage.changePassword(currentPass,newPass,newPass,true);

        log("Verify can change password");
        Assert.assertEquals(messageSuccess, ChangePassword.MESSAGE_SUCCESS, "FAILED! Success message when change password is incorrect");

        log("@Step 4: Re-login with new pw");
        loginAQSPage = betOrderPage.logout();
        betOrderPage = loginAQSPage.login(username, newPass);
        Assert.assertTrue(betOrderPage.profileIcon.isDisplayed(), String.format("ERROR: cannot login after change password for login account", username));
        log("INFO: Executed completely");
        }finally {
            /**Post-condition: re-update new password to old password */
            betOrderPage.changePassword(newPass,currentPass,currentPass,true);
        }

    }
}
