package pages.sb11.generalReports;

import com.paltech.element.common.Label;
import pages.sb11.WelcomePage;

public class WinLossDetailPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//app-winloss-detail//div[contains(@class,'card-header')]//span[contains(@class,'text-white')]");

}
