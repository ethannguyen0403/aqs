package pages.ess;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Label;
import org.openqa.selenium.support.PageFactory;
import pages.ess.components.Header;

public class HomePage extends Header {
    Label lblSpin = Label.xpath("//div[contains(@class,'la-ball-clip-rotate')]");
    public <T> T activeMenu(String menu, Class<T> expectedPage) {
        clickMenuHeader(menu);
        // add wait method if to wait data complete loaded
        return PageFactory.initElements(DriverManager.getDriver(), expectedPage);
    }
    private void clickMenuHeader(String menuName){
        String menu = menuName.toUpperCase();
        switch (menu){
            case "ROLE":
                menuRole.click();
                return;
            case "MANAGER":
                menuManager.click();
                return;
            case "MASTER ACCOUNT":
                menuMasterAccount.click();
                return;
            case "ACCOUNT":
                menuAccount.click();
                return;
            default:
                menuAQS.click();
                return;
        }
    }
    public String changePassword(String oldPassword, String newPassword, String confirmPassword, boolean isClose){
        ChangePasswordPopup changePasswordPopup = openChangePasswordPopup();
        return changePasswordPopup.changePassword(oldPassword,newPassword,confirmPassword,isClose);
    }

    public void waitSpinLoad(){
        lblSpin.isDisplayed(3);
    }
}
