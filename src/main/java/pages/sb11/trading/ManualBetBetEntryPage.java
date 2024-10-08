package pages.sb11.trading;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import controls.DateTimePicker;
import org.testng.Assert;
import pages.sb11.popup.ConfirmPopup;
import utils.sb11.accounting.CompanySetUpUtils;

public class ManualBetBetEntryPage extends BetEntryPage {
    Label lblTitle = Label.xpath("//app-bet-entry-mixed-sport//div[contains(@class,'main-box-header')]/div[1]/span");
    public DropDownBox ddCompany = DropDownBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Company Unit')]//following::select[1]");
    public TextBox txtAccCode = TextBox.xpath("//app-bet-entry-mixed-sport//input[@type='search']");
    public Button btnSearch = Button.xpath("//app-bet-entry-mixed-sport//button[contains(@class,'btn-success')]");
    public DropDownBox ddSport = DropDownBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Sport')]//following::select[1]");
    public TextBox txtDate = TextBox.name("dp");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container')]");
    public TextBox txtBetDescription = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Bet Description')]//following::textarea");
    public TextBox txtSelection = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Selection')]//following::input[1]");
    public TextBox txtBetType = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Bet type')]//following::input[1]");
    public TextBox txtOdds = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Odds')]//following::input[1]");
    public TextBox txtStake = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Stake')]//following::input[1]");
    public TextBox txtWinLose = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Win/Loss or Comm Amount')]//following::input[1]");
    public DropDownBox ddBetType = DropDownBox.xpath("//app-bet-entry-mixed-sport//div[contains(@class,'col-2')]//select");
    public DropDownBox ddOddType = DropDownBox.xpath("//app-bet-entry-mixed-sport//div[contains(@class,'col-7')]//select");
    public Button btnClear = Button.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'CLEAR')]");
    public Button btnPlaceBet = Button.xpath("//app-bet-entry-mixed-sport//button[contains(text(),'Place bet')]");
    public Label messageSuccess = Label.xpath("//app-alert//div[@class='message-box']");
    public Label lblDate = Label.xpath("//span[text()='Date']");
    public Label lblAccountCode = Label.xpath("//span[contains(text(),'Account Code')]");
    public Label lblClient = Label.xpath("//span[text()='Client']");
    public Label lblBalance = Label.xpath("//span[text()='Balance']");
    public Label lblBetDescription = Label.xpath("//span[text()='Bet Description']");
    public Label lblSelection = Label.xpath("//span[text()='Selection']");
    public Label lblBetType = Label.xpath("//span[text()='Bet type']");
    public Label lblOdds = Label.xpath("//span[text()='Odds']");
    public Label lblStake = Label.xpath("//span[text()='Stake']");
    public Label lblWinLoss = Label.xpath("//span[text()='Win/Loss or Comm Amount']");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    /**
     * To place manual bet
     * @param companyUnit
     * @param accCode
     * @param sport
     * @param date
     * @param betDescription
     * @param selection
     * @param betType
     * @param odds
     * @param stake
     * @param winLoss
     */

    public String placeManualBet(String companyUnit, String date, String accCode, String sport, String betDescription,
                                 String selection, String betType, String odds, String stake, String winLoss, boolean isConfirm){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ddCompany.selectByVisibleContainsText(companyUnit);
        txtAccCode.sendKeys(accCode);
        btnSearch.click();
        ddSport.selectByVisibleText(sport);
        String dateValue = txtDate.getAttribute("value").trim();
        if(!date.isEmpty()) {
            if (!date.equals(dateValue))
                dtpDate.selectDate(date, "dd/MM/yyyy");
        }
        txtBetDescription.sendKeys(betDescription);
        txtSelection.sendKeys(selection);
        txtBetType.sendKeys(betType);
        txtOdds.sendKeys(odds);
        txtStake.sendKeys(stake);
        txtWinLose.sendKeys(winLoss);
        btnPlaceBet.click();
        ConfirmPopup confirmPopup = new ConfirmPopup();
        confirmPopup.confirm(isConfirm);
        messageSuccess.isDisplayedShort(5);
        return messageSuccess.getText();
    }

    public void verifyUI() {
        System.out.println("Dropdown: Company Unit, Sport, Selection, OddsType");
        Assert.assertEquals(ddCompany.getOptions(), CompanySetUpUtils.getListCompany(),"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(ddSport.getOptions(), SBPConstants.BetEntryPage.SPORT_LIST,"Failed! Sport dropdown is not displayed!");
        Assert.assertEquals(ddBetType.getOptions(), SBPConstants.BetEntryPage.BET_TYPE,"Failed! Bet type dropdown is not displayed!");
        Assert.assertEquals(ddOddType.getOptions(), SBPConstants.BetEntryPage.ODD_TYPE,"Failed! Odd type dropdown is not displayed!");
        System.out.println("Textbox: Account Code, Bet Description, Selection, Bet type, Odds, Stake, WinLoss");
        Assert.assertTrue(lblAccountCode.getText().contains("Account Code"),"Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(lblClient.getText(),"Client","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(lblBalance.getText(),"Balance","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(lblBetDescription.getText(),"Bet Description","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(lblSelection.getText(),"Selection","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(lblBetType.getText(),"Bet type","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(lblOdds.getText(),"Odds","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(lblStake.getText(),"Stake","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(lblWinLoss.getText(),"Win/Loss or Comm Amount","Failed! Account Code textbox is not displayed!");
        System.out.println("Datetimepicker: Date");
        Assert.assertEquals(lblDate.getText(),"Date","Failed! Date datetimepicker is not displayed!");
        System.out.println("Button: Show button, Place Bet button");
        Assert.assertTrue(btnSearch.isDisplayed(),"Failed! Search button is not displayed!");
        Assert.assertTrue(btnPlaceBet.isEnabled(),"Failed! Place bet button is not displayed!");
    }
}