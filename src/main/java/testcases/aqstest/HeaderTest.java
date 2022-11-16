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

    /**
     * @title: Verify menu in header section is correctly display
     * @steps:   1. Login the page
     * @expect: Verify
     * - logo: AQS
     * - Role, Manager,Account,AQS
     */
    @TestRails(id = "461")
    @Test(groups = {"smoke"})
    public void HeaderTC_001(){
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

    /**
     * @title: Verify Change Password menu display
     * @steps:   1. Login the page
     * 2. Click on profile icon
     * @expect: Verify Change password icon display
     */
    @TestRails(id = "462")
    @Test(groups = {"smoke"})
    public void HeaderTC_002(){
        log("@title: Verify Change Password menu display");
        log("@Step 1: Click on profile icon");
        betOrderPage.ddlMenu.clickMenu(CHANGE_PASSWORD);
        List<String> lstMenuHeader = betOrderPage.ddlMenu.getMenuList();
        log("Verify Change password icon display");
        Assert.assertEquals(lstMenuHeader.get(0), CHANGE_PASSWORD,"FAILED! Change Password menu is not displayed");
        log("INFO: Executed completely");
    }

    /**
     * @title: Verify Change Password popup display
     * @steps:   1. Login the page
     * 2. Click on profile icon
     * 3. Click on Change Password
     * @expect: Verify Change password popup display with correct info
     * - Change Password label and Login_Lable
     * - Current Password label + textbox
     * - New Password + Textbox + Hint icon
     * - Confirm Password + Textbox
     * Update and Close button
     */
    @TestRails(id = "463")
    @Test(groups = {"smoke"})
    public void HeaderTC_003(){
        log("@title: Verify Change Password menu display");
        log("@Step 1: Click on profile icon");
        log("@Step 2: Click on Change Password");
        betOrderPage.ddlMenu.clickMenu(CHANGE_PASSWORD);
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
    /**
     * @title: Verify Can change password success
     * @steps:   1. Login the page
     * 2. Click on profile icon
     * 3. Click on Change Password
     * 4. Change password
     * 5. Re-login with new pw
     * @expect: Verify can change password
     * Verify can login account with new pw
     */
    @TestRails(id = "464")
    @Test(groups = {"smoke"})
    @Parameters({"username","password"})
    public void HeaderTC_004(String username, String password) throws Exception {
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
