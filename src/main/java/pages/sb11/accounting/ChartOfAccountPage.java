package pages.sb11.accounting;

import com.paltech.element.common.Label;
import pages.sb11.WelcomePage;

public class ChartOfAccountPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
}
