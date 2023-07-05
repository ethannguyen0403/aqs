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

    public Button btnSuper = Button.xpath("//div[contains(@routerlink,'super')]");
    public Button btnMaster = Button.xpath("//div[contains(@routerlink,'master')]");
    public Button btnAgent = Button.xpath("//div[contains(@routerlink,'agent')]");

    public Table tbSuper = Table.xpath("//table",10);

    public BookieSuperPage goToSuper(){
        btnSuper.click();
        waitPageLoad();
        return new BookieSuperPage();
    }
    public BookieMasterPage goToMaster(){
        btnMaster.click();
        waitPageLoad();
        return new BookieMasterPage();
    }
    public BookieAgentPage goToAgent(){
        btnAgent.click();
        waitPageLoad();
        return new BookieAgentPage();
    }
}
