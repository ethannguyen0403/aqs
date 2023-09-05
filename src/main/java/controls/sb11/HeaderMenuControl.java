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

    private Label getSubMenuLabel(String menu, String subMenu){
        Label lblMainMenu  = Label.xpath(String.format("%s//span[text()='%s']",_xpath, menu));
        if(!lblMainMenu.isDisplayed()) {
            System.out.println("DEBUG! The menu " + menu + " does not display");
            return null;
        }
        lblMainMenu.moveToTheControl();
//        lblMainMenu.click();
        Label lblSubMenu =Label.xpath(String.format("%s//div[contains(@class,'dropdown-menu')]//span[contains(text(),'%s')]",_xpath,subMenu));
        if(!lblSubMenu.isDisplayed()){
            System.out.println("DEBUG! The sub menu "+subMenu+" under menu "+menu+" does not display");
            return null;
        }
        return lblSubMenu;
        /*
        int index = getMenuIndex(menu);
        Label lblMenu = Label.xpath(String.format("%s//li[%s]",_xpath,index));
        lblMenu.click();
        int i = 1;
        Label lblSubMenu ;
        while (true){
            lblSubMenu = Label.xpath(String.format("%s//li[%s]//div[contains(@class,'dropdown-menu')]//a[%s]",_xpath,index,i));
            if(!lblSubMenu.isDisplayed()){
                System.out.println("DEBUG! The sub menu "+subMenu+" does not display");
                return null;
            }
            if(lblSubMenu.getText().equals(subMenu)){
                return lblSubMenu;
            }
            i = i+1;
        }*/
    }



    public List<String> getListMenu(){
        int i = 1;
        Label lblMenu;
        List<String> lstMenu = new ArrayList<>();
        while (true) {
            lblMenu = Label.xpath(String.format("%s//li[%s]",_xpath, i));
            if (!lblMenu.isDisplayed()) {
                return lstMenu;
            }
            lstMenu.add(lblMenu.getText().trim());
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

}
