package pages.ess;

import com.paltech.element.common.Label;

public class AccountInfoPage {
    public Label lblAccName = Label.xpath("//td[contains(text(),'Account Name:')]");
    public Label lblFullName = Label.xpath("//td[contains(text(),'Full Name:')]");
    public Label lblRole = Label.xpath("//td[contains(text(),'Role:')]");
}
