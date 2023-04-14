package pages.sb11.sport;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;

public class OpenPricePage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public TextBox txtDate = TextBox.name("dp");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public Button btnShowLeagues = Button.xpath("//button[text()='Show Leagues']");
    public Button btnShow = Button.xpath("//button[text()='Show']");
    public Button btnSubmit = Button.xpath("//button[contains(text(),'Submit')]");
    public DropDownBox ddpLeague = DropDownBox.id("league");
    public Table tbOpenPrice = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[1]",13);

    public void filterResult(String date, String league, boolean isShow){
        if(!date.isEmpty()){
            dtpDate.selectDate(date,"dd/MM/yyyy");
            btnShowLeagues.click();
        }
        ddpLeague.selectByVisibleText(league);
        if (isShow){
            btnShow.click();
        }
    }
}
