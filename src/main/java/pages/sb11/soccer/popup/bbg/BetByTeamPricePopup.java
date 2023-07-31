package pages.sb11.soccer.popup.bbg;

import com.paltech.element.common.Label;

public class BetByTeamPricePopup {
    public Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span");
    public Label lblLast1Month = Label.xpath("//table[1]//preceding::div[1]");
    public Label lblLast3Month = Label.xpath("//table[2]//preceding::div[1]");
    public String getTitlePage (){return lblTitle.getText().trim();}
}
