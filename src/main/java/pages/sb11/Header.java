package pages.sb11;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.DropDownList;
import controls.sb11.HeaderMenuControl;
import org.openqa.selenium.support.PageFactory;
import pages.ess.ChangePasswordPopup;

import static common.ESSConstants.CHANGE_PASSWORD;

public class Header {
    public Icon icBell = Icon.xpath("//div[@id='navbarSupportedContent']//div[contains(@class,'fa-bell')]");
    public Label lblUserName = Label.xpath("//div[@id='navbarSupportedContent']//ul/li[2]/span");
    public Label lblChangePassword = Label.xpath("//div[@id='navbarSupportedContent']//span[contains(@class,'text-change-pass')]");
    public Button btnLogout = Button.xpath("//div[@id='navbarSupportedContent']//button");
    public Icon iconLogo = Icon.xpath("//span[contains(@class,'main-icon')]");
    public DropDownList ddlMenu = DropDownList.xpath("//div[contains(@class,'dropdown')]", "//ul[contains(@class,'dropdown-content ng-star-inserted')]/li");
    public HeaderMenuControl headerMenuControl = HeaderMenuControl.xpath("//div[@id='navbarTogglerDemo02']/ul[contains(@class,'navbar-nav')]");

    public LoginPage logout(){
        btnLogout.click();
        LoginPage loginPage = new LoginPage();
        loginPage.btnCopyRight.isDisplayed();
        return loginPage;
    }
    public ChangePasswordPopup openChangePasswordPopup(){
        lblChangePassword.click();
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
        return PageFactory.initElements(DriverManager.getDriver(), expectedPage);
    }

}
