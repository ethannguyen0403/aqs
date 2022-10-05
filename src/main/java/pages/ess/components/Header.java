package pages.ess.components;

import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.DropDownList;
import pages.ess.ChangePasswordPopup;
import pages.ess.LoginPage;

import static common.ESSConstants.CHANGE_PASSWORD;
import static common.ESSConstants.LOGOUT;

public class Header {
    public Label lblUserName = Label.xpath("//div[@id='navbarSupportedContent']//ul/li[1]/span");
    public Icon iconLogo = Icon.xpath("//span[contains(@class,'main-icon')]");
    public Label menuRole = Label.xpath("//a[contains(@href, '#/aqs/role-management')]");
    public Label menuManager = Label.xpath("//a[contains(@href, '#/aqs/user-management')]");
    public Label menuMasterAccount = Label.xpath("//a[contains(@href, '#/aqs/master-account')]");
    public Label menuAccount = Label.xpath("//a[contains(@href, '#/aqs/account')]");
    public Label menuAQS = Label.xpath("//a[contains(@href, '#/aqs/bet-orders')]");
    public DropDownList ddlMenu = DropDownList.xpath("//div[contains(@class,'dropdown')]", "//ul[contains(@class,'dropdown-content ng-star-inserted')]/li");
    public Icon profileIcon = Icon.xpath("//div[@class='d-block dropdown']");

    public LoginPage logout(){
        ddlMenu.clickMenu(LOGOUT);
        LoginPage loginPage = new LoginPage();
        loginPage.btnCopyRight.isDisplayed();
        return loginPage;
    }
    public ChangePasswordPopup openChangePasswordPopup(){
        ddlMenu.clickMenu(CHANGE_PASSWORD);
        return new ChangePasswordPopup();
    }

    public boolean isMenuActive(Label menu){
       return menu.getAttribute("class").contains("active");
    }
}
