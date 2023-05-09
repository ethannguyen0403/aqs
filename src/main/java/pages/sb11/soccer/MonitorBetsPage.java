package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.CheckBox;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;

public class MonitorBetsPage extends WelcomePage {
    int colAC = 2;
    int colStake = 6;
    int colT = 9;
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpSport = DropDownBox.id("sport");
    public DropDownBox ddpSmartType = DropDownBox.id("smartType");
    public DropDownBox ddpPunterType = DropDownBox.id("punterType");
    public DropDownBox ddpBetPlacedIN = DropDownBox.id("betPlacedIn");
    public DropDownBox ddpBetCount = DropDownBox.id("betCount");
    public DropDownBox ddpLRBRule = DropDownBox.id("lrbRule");
    public DropDownBox ddpLiveNonLive = DropDownBox.id("liveNonLive");
    public DropDownBox ddpCurrency = DropDownBox.id("currency");
    public DropDownBox ddpStake = DropDownBox.id("stake");
    public CheckBox cbTodayEvent = CheckBox.id("todayEvent");
    public Label lblTodayEvent = Label.xpath("//label[contains(text(),'Today Event(s)')]");
    public Label lblShowBetType = Label.xpath("//div[contains(text(),'Show Bet Types')]");
    public Label lblShowLeagues = Label.xpath("//div[contains(text(),'Show Leagues')]");
    public Label lblShowMaster = Label.xpath("//div[contains(text(),'Show Masters')]");
    public Label lblShowEvents = Label.xpath("//div[contains(text(),'Show Events')]");
    public Label lblShowTraders = Label.xpath("//div[contains(text(),'Show Traders')]");
    public Label lblResetAllFilters = Label.xpath("//span[contains(text(),'Reset All Filters')]");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public Table tblOrder = Table.xpath("//app-monitor-bets//table",11);

    public void filterResult(String sport, String smartType, String punterType, String betPlacedIn, String betCount, boolean isTodayEvent, String lrbRule, String liveNonLive, String currency, String stake, boolean isShow){
        ddpSport.selectByVisibleText(sport);
        ddpSmartType.selectByVisibleText(smartType);
        ddpPunterType.selectByVisibleText(punterType);
        ddpBetPlacedIN.selectByVisibleText(betPlacedIn);
        ddpBetCount.selectByVisibleText(betCount);
        ddpLRBRule.selectByVisibleText(lrbRule);
        if (isTodayEvent){
            cbTodayEvent.click();
        }
        ddpLiveNonLive.selectByVisibleText(liveNonLive);
        ddpCurrency.selectByVisibleText(currency);
        ddpStake.selectByVisibleText(stake);
        if (isShow){
            btnShow.click();
        }
    }

    public PerformanceByMonthPage openPerfByMonth (String accountCode){
        int index = getACRowIndex(accountCode);
        tblOrder.getControlOfCell(1,colAC,index,"span[1]").click();
        DriverManager.getDriver().switchToWindow();
        return new PerformanceByMonthPage();
    }

    public PendingBetsPage openPendingBets (String accountCode){
        int index = getACRowIndex(accountCode);
        tblOrder.getControlOfCell(1,colStake,index,"span[3]").click();
        DriverManager.getDriver().switchToWindow();
        return new PendingBetsPage();
    }

    public Last12DaysPerformancePage openLast12DaysPerf (String accountCode){
        int index = getACRowIndex(accountCode);
        tblOrder.getControlOfCell(1,colT,index,null).click();
        DriverManager.getDriver().switchToWindow();
        return new Last12DaysPerformancePage();
    }

    public int getACRowIndex(String accountCode){
        int i = 1;
        Label lblAC;
        while (true){
            lblAC = Label.xpath(tblOrder.getxPathOfCell(1,colAC,i,null));
            if(!lblAC.isDisplayed()) {
                System.out.println("Can NOT found the league "+accountCode+" in the table");
                return 0;
            }
            if(lblAC.getText().contains(accountCode)){
                System.out.println("Found the league "+accountCode+" in the table");
                return i;
            }
            i = i +1;
        }
    }
}
