package controls.sb11;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Label;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class HeaderMenuControl extends BaseElement {
    protected String _xpath = null;

    public HeaderMenuControl(By locator, String xpathExpression) {
        super(locator);
        this._xpath = xpathExpression;
    }

    public static HeaderMenuControl xpath(String xpathExpression) {
        return new HeaderMenuControl(By.xpath(xpathExpression),xpathExpression);
    }


    public void clickSubMenu(String menu, String subMenu){
        getSubMenuLabel(menu,subMenu).click();
    }
    public void clickSubMenu(String menu, String subMenu, String subSubMenu){
        getSubMenuLabel(menu,subMenu,subSubMenu).click();
    }

    private Label getSubMenuLabel(String menu, String subMenu){
        Label lblMainMenu  = Label.xpath(String.format("%s//span[text()='%s']",_xpath, menu));
        if(!lblMainMenu.isDisplayed()) {
            System.out.println("DEBUG! The menu " + menu + " does not display");
            return null;
        }
        lblMainMenu.click();
        lblMainMenu.moveAndHoverOnControl();
        Label lblSubMenu = Label.xpath(String.format("%s//span[text()='%s']/parent::a/following-sibling::ul//a[contains(text(),'%s')]",_xpath,menu,subMenu));
        if(!lblSubMenu.isDisplayed()){
            System.out.println("DEBUG! The sub menu "+subMenu+" under menu "+menu+" does not display");
            return null;
        }
        lblSubMenu.waitForElementToBePresent(lblSubMenu.getLocator());
        return lblSubMenu;
    }

    private Label getSubMenuLabel(String menu, String subMenu, String subOfSubMenu){
        Label lblMainMenu  = Label.xpath(String.format("%s//span[text()='%s']",_xpath, menu));
        if(!lblMainMenu.isDisplayed()) {
            System.out.println("DEBUG! The menu " + menu + " does not display");
            return null;
        }
        lblMainMenu.click();
        lblMainMenu.moveAndHoverOnControl();
        Label lblSubMenu = Label.xpath(String.format("%s//span[text()='%s']/parent::a/following-sibling::ul/li/a[contains(text(),'%s')]",_xpath,menu,subMenu));
        if(!lblSubMenu.isDisplayed()){
            System.out.println("DEBUG! The sub menu "+subMenu+" under menu "+menu+" does not display");
            return null;
        }
        lblSubMenu.moveAndHoverOnControl();
        Label lblSubOfSubMenu = Label.xpath(String.format("%s//span[text()='%s']/parent::a/following-sibling::ul/li/a[contains(text(),'%s')]/following-sibling::ul//a[text()='%s']",_xpath,menu,subMenu,subOfSubMenu));
        if(!lblSubOfSubMenu.isDisplayed()){
            System.out.println(String.format("DEBUG! The sub menu %s of %s under menu %s does not display",subOfSubMenu,subMenu,menu));
            return null;
        }
        lblSubOfSubMenu.moveAndHoverOnControl();
        return lblSubOfSubMenu;
    }



    public List<String> getListMenu(){
        int i = 1;
        Label lblMenu;
        List<String> lstMenu = new ArrayList<>();
        while (true) {
            lblMenu = Label.xpath(String.format("(%s//span)[%s]",_xpath, i));
            if (!lblMenu.isDisplayed()) {
                return lstMenu;
            }
            lstMenu.add(lblMenu.getText().trim());
            i = i + 1;
        }
    }
    public List<String> getListSubMenu(){
        int i = 1;
        Label lblSubMenu;
        List<String> lstSubMenu = new ArrayList<>();
        while (true) {
            lblSubMenu = Label.xpath(String.format("(//div[@id='navbarTogglerDemo02']//div[@class='dropdown-menu']//span)[%s]", i));
            if (!lblSubMenu.isPresent()) {
                return lstSubMenu;
            }
            lstSubMenu.add(lblSubMenu.getText().trim());
            i = i + 1;
        }
    }

    private int getMenuIndex(String menu) {
        int i = 1;
        Label lblMenu;
        Label.xpath(String.format("%s//li[%s]",_xpath, i));
        while (true) {
            lblMenu = Label.xpath(String.format("%s//li[%s]",_xpath, i));
            if (!lblMenu.isDisplayed()) {
                System.out.println("DEBUG! The menu " + menu + " does not display in the header menu");
                return 0;
            }
            if (lblMenu.getText().equals(menu)) {
                System.out.println("DEBUG! Found the menu " + menu);
                return i;
            }
            i = i + 1;
        }
    }
    private Label defineMenuControl(String menu){
        int index = getMenuIndex(menu);
        return  Label.xpath(String.format("//div[@id='navbarTogglerDemo02']/ul[contains(@class,'navbar-nav')]//li[%s]",index));
    }
    public boolean isSubmenuDisplay(String menu, String subMenu){
        Label lblSubMenu = getSubMenuLabel(menu, subMenu);
        if(lblSubMenu == null){
            System.out.println(subMenu+ " is not displayed!");
            return false;
        }else {
            System.out.println(subMenu+ " displayed!");
            return true;
        }
    }
}
