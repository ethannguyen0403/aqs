package pages.sb11.master;

import com.paltech.element.common.Label;
import pages.sb11.WelcomePage;

public class MissedAccountsPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public Label lblBetISN = Label.xpath("//div[contains(@class,'header-title')]//span[text()='BET ISN']");
    public Label lblPinnacle = Label.xpath("//div[contains(@class,'header-title')]//span[text()='PINNACLE']");
    public Label lblFair999 = Label.xpath("//div[contains(@class,'header-title')]//span[text()='Fair999']");
}
