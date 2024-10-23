package pages.sb11.soccer.popup.bbg;

import com.paltech.element.common.Label;
import pages.sb11.WelcomePage;

public class BBGLastDaysPerformacesPopup extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span");
    public Label lblLast1Month = Label.xpath("//table[1]//preceding::div[1]");
    public Label lblNoRecord = Label.xpath("//td[contains(text(),'No record found')]");
    public String getTitlePage (){return lblTitle.getText().trim();}
}
