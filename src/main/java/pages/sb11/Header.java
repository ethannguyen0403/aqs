package pages.sb11;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.DropDownList;
import controls.sb11.HeaderMenuControl;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import pages.sb11.popup.ChangePasswordPopup;

import java.util.List;

public class Header {
    public Icon icBell = Icon.xpath("//div[@id='navbarSupportedContent']//div[contains(@class,'fa-bell')]");
    public Label lblUserName = Label.xpath("//div[@id='navbarSupportedContent']//ul/li[3]/span");
    public Label lblChangePassword = Label.xpath("//div[@id='navbarSupportedContent']//span[contains(@class,'text-change-pass')]");
    public Button btnLogout = Button.xpath("//div[@id='navbarSupportedContent']//button[text()='Logout']");
    public Icon iconLogo = Icon.xpath("//span[contains(@class,'main-icon')]");
    public DropDownList ddlMenu = DropDownList.xpath("//div[contains(@class,'dropdown')]", "//ul[contains(@class,'dropdown-content ng-star-inserted')]/li");
    public HeaderMenuControl headerMenuControl = HeaderMenuControl.xpath("//div[@id='navbarTogglerDemo02']/ul[contains(@class,'navbar-nav')]");
    Label lblSpin = Label.xpath("//div[contains(@class,'la-ball-clip-rotate')]");
    public void waitPageLoad(){
        lblSpin.waitForControlInvisible();
    }
    public void waitSpinnerDisappeared() {
        lblSpin.waitForControlInvisible();
    }
    public LoginPage logout(){
        btnLogout.click();
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.tabEurope.isDisplayed(),"FAILED! Camouflag page is not displayed");
        return loginPage;
    }
    public pages.sb11.popup.ChangePasswordPopup openChangePasswordPopup(){
        lblChangePassword.click();
        waitPageLoad();
        return new ChangePasswordPopup();
    }

    /**
     * Click on the menu in header to navigate to the expected page
     * @param menu: main menu: ex Role, User, Sport, or Master ....
     * @param submenu The submenu beloging to main menu
     * @param expectedPage the expected page
     * @param <T>
     * @return
     */
    public <T> T navigatePage(String menu, String submenu, Class<T> expectedPage) {
        headerMenuControl.clickSubMenu(menu,submenu);
        lblUserName.moveAndHoverOnControl();
        waitSpinnerDisappeared();
        return PageFactory.initElements(DriverManager.getDriver(), expectedPage);
    }

    public boolean isPageDisplayCorrect(String menu, String subMenu){
        return headerMenuControl.isSubmenuDisplay(menu,subMenu);
    }

}
