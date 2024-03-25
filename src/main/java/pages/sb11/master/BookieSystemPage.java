package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.Tab;
import controls.Table;
import pages.sb11.WelcomePage;

public class BookieSystemPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public Button btnSuper = Button.xpath("//span[text()='Super Section']");
    public Button btnMaster = Button.xpath("//span[text()='Master Section']");
    public Button btnAgent = Button.xpath("//span[text()='Agent Section']");

    public Table tbSuper = Table.xpath("//table",10);

    public BookieSuperPage goToSuper(){
        btnSuper.click();
        waitSpinnerDisappeared();
        return new BookieSuperPage();
    }
    public BookieMasterPage goToMaster(){
        btnMaster.click();
        waitSpinnerDisappeared();
        return new BookieMasterPage();
    }
    public BookieAgentPage goToAgent(){
        btnAgent.click();
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
        return new BookieAgentPage();
    }
}
