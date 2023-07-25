package pages.sb11.soccer.popup.bbg;

import com.paltech.element.common.Label;

public class BBGLastDaysPerformacesPopup {
    public Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span");
    public Label lblLast1Month = Label.xpath("//table[1]//preceding::div[1]");
    public String getTitlePage (){return lblTitle.getText().trim();}
}
