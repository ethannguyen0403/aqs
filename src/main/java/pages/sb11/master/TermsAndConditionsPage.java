package pages.sb11.master;

import com.paltech.element.common.DropDownBox;
import pages.sb11.WelcomePage;

public class TermsAndConditionsPage extends WelcomePage {
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");

}
