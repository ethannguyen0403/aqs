package pages.sb11.master;

import com.paltech.element.common.Label;
import pages.sb11.WelcomePage;

public class ClientSystemPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");

    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
}
