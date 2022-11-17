package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;

public class BetEntryPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    //label[contains(text(),'Company Unit')]
    public DropDownBox ddComp = DropDownBox.id("company-unit");
    public TextBox txtDate = TextBox.xpath("//div[@class='pl-3']/label[contains(text(),'Date')]//following::input[1]");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public DropDownBox ddLeague = DropDownBox.id("league");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public DropDownBox ddSearchBy = DropDownBox.xpath("//div[@class='pl-3']/label[contains(text(),'Search By')]//following::select[1]");
    public TextBox txtAccCode = TextBox.id("account-code");
    public Button btnSoccer = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Soccer')]");
    public Button btnCricket = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Cricket')]");
    public Button btnMixedSport = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Mixed Sports')]");

    public void placeBetSoccer (String league, String accCode){
        btnSoccer.click();
        ddLeague.selectByVisibleContainsText(league);
        btnShow.click();
        txtAccCode.sendKeys(accCode);
    }

    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
}
