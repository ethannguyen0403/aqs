package pages.sb11.soccer;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;

public class MatchOddsLiabilityPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpSmartType = DropDownBox.xpath("//div[contains(text(),'Smart Type')]//following::select[1]");
    public CheckBox cbPTBets = CheckBox.xpath("//div[contains(text(),'Show Only')]//following::input[1]");
    public DropDownBox ddpLiveNonLive = DropDownBox.xpath("//div[contains(text(),'Live/NonLive')]//following::select[1]");
    public DropDownBox ddpStake = DropDownBox.xpath("//div[contains(text(),'Stake')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-days-calendar-view");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-days-calendar-view");

    public Label lblShowBetType = Label.xpath("//div[contains(text(),'Show Bet Types')]");
    public Label lblShowLeagues = Label.xpath("//div[contains(text(),'Show Leagues')]");
    public Label lblShowGroups = Label.xpath("//div[contains(text(),'Show Groups')]");
    public Label lblShowEvents = Label.xpath("//div[contains(text(),'Show Events')]");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public Table tblOrder = Table.xpath("//app-match-odds-liability//table",6);

    public void filterResult(String companyUnit, String smartType, boolean isPTBets, String liveNonLive, String fromDate, String toDate, String stake, boolean isShow){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpSmartType.selectByVisibleText(smartType);
        if (isPTBets){
            cbPTBets.click();
        }
        ddpLiveNonLive.selectByVisibleText(liveNonLive);
        if(!fromDate.isEmpty())
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        if(!toDate.isEmpty())
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        ddpStake.selectByVisibleText(stake);
        if (isShow){
            btnShow.click();
        }
    }
    public void showEvents (String eventName){
        lblShowEvents.click();
    }
    
}
