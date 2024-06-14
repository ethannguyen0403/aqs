package pages.sb11.sport;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import static common.SBPConstants.*;

public class SoccerResultEntryPage extends WelcomePage {
    Label lblTitle = Label.xpath("(//div[contains(@class,'justify-content-between')]//div[@class='pt-2']//span)[2]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Button btnSoccer = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Soccer')]");
    public Button btnCricket = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Cricket')]");
    public Button btnShowLeagues = Button.xpath("//button[contains(text(),'Show Leagues')]");
    public Button btnShow = Button.xpath("//button[@class='btn btn-show']");
    public DropDownBox ddpType = DropDownBox.id("type");
    public DropDownBox ddpLeague = DropDownBox.id("sport");
    public DropDownBox ddpOrderBy = DropDownBox.id("betType");
    public DropDownBox ddpStatus = DropDownBox.id("status");
    public Label lblDate = Label.xpath("//label[contains(text(),'Date')]");
    public TextBox txtDateTime = TextBox.id("date");
    public DateTimePicker dtpDateTime = DateTimePicker.xpath(txtDateTime,"//bs-days-calendar-view");
    public Table tbResult = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[1]",12);
    public DropDownBox ddGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");
    public Label lblYellowcells = Label.xpath("//label[contains(text(),'Yellow cells')]");
    public Label lblUpdatenegative = Label.xpath("//label[contains(text(),'Update negative')]");

    public void goToSport(String sport){
        if(sport.equals("Soccer")){
            btnSoccer.click();
        }
        if(sport.equals("Cricket")){
            btnCricket.click();
        }
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
    }

    public void filterResult(String type, String date, String league, String orderBy, String status, boolean isShow){
        ddpType.selectByVisibleText(type);
        if(!date.isEmpty()){
            dtpDateTime.selectDate(date,"dd/MM/yyyy");
            btnShowLeagues.click();
        }
        ddpLeague.selectByVisibleText(league);
        ddpOrderBy.selectByVisibleText(orderBy);
        ddpStatus.selectByVisibleText(status);
        if (isShow){
            btnShow.click();
            waitSpinnerDisappeared();
        }
    }

    public void verifyUI() {
        System.out.println("Type, Date, Show League button, Leagues, Order By, Status and Show button");
        Assert.assertEquals(ddpType.getOptions(),TYPE_LIST,"Failed! Type dropdown is not displayed");
        Assert.assertEquals(lblDate.getText(),"Date","Failed! Date datetimepicker is not displayed");
        Assert.assertEquals(btnShowLeagues.getText(),"Show Leagues","Failed! Show Leagues button is not displayed");
        Assert.assertTrue(ddpLeague.getOptions().contains("All"),"Failed! League dropdown is not displayed");
        Assert.assertEquals(ddpOrderBy.getOptions(),ORDER_BY_LIST,"Failed! Order By dropdown is not displayed");
        Assert.assertEquals(ddpStatus.getOptions(),STATUS_LIST,"Failed! Status dropdown is not displayed");
        Assert.assertEquals(btnShow.getText(),"Show","Failed! Show button is not displayed");
        System.out.println("2 notes are displayed correctly");
        filterResult("Normal","","All","KOT","All",true);
        Assert.assertEquals(lblYellowcells.getText(), "Yellow cells are fields that 7M doesn't provide info.","Failed! Note 1 is not displayed");
        Assert.assertEquals(lblUpdatenegative.getText(), "Update negative score to VOID bet, update score from negative to positive number will be UNVOID.","Failed! Note 2 is not displayed");
        System.out.println("Result Entry table header columns are correctly display");
        Assert.assertEquals(tbResult.getHeaderNameOfRows(), ResultEntry.RESULT_SOCCER_TABLE_HEADER,"FAILED! Result table header is incorrect display");
    }
}
