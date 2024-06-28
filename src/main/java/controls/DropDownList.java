package controls;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Label;
import org.openqa.selenium.By;


import java.util.ArrayList;
import java.util.List;

public class DropDownList extends BaseElement {
    protected String _xpath = null;
    protected String _childXpath="";
    public DropDownList(By locator, String xpathExpression, String childXpath) {
        super(locator);
        this._xpath = xpathExpression;
        this._childXpath = childXpath;
    }

    /**
     *
     * @param xpathParent is used for moving cursor of move and hover on this control to show the hidden items
     * @return DropDownBox
     */
    public static DropDownList xpath(String xpathParent,String childXpath) {
        return new DropDownList(By.xpath(xpathParent),xpathParent,childXpath);
    }
    public List<String> getMenuList(){
        List<String> lstOption = new ArrayList<>();
        int index = 1;
        Label subMenu;
        while (true){
            subMenu = Label.xpath(String.format("(%s%s)[%s]",this._xpath,this._childXpath, index));
            if(!subMenu.isDisplayed()){
                return lstOption;
            }
            lstOption.add(subMenu.getText());
            index += 1;
        }
    }

    public void clickMenu(String name){
        int index = 1;
//        if(!Label.xpath(String.format("%s%s[%s]",this._xpath,this._childXpath, index)).isDisplayed())
        this.click();
        Label subMenu;
        while (true){
            subMenu = Label.xpath(String.format("(%s%s)[%s]",this._xpath,this._childXpath, index));
            if(!subMenu.isDisplayed()){
                System.out.println(String.format("Error: There is no sub menu %s in the list", name));
                return;
            }
            if(subMenu.getText().trim().contains(name)){
                subMenu.click();
                break;
            }
            index += 1;
        }
        this.click();
    }
    public boolean isMenuActive(String name){
        int index = 1;
        this.click();
        Label subMenu;
        while (true){
            subMenu = Label.xpath(String.format("%s%s[%s]",this._xpath,this._childXpath, index));
            if(!subMenu.isDisplayed()){
                System.out.println(String.format("Error: There is no sub menu %s in the list", name));
                return false;
            }
            if(subMenu.getText().contains(name)){
                String atribute =subMenu.getAttribute("class");
                return atribute.contains("selected-tab");
            }
            index += 1;
        }
    }
}
