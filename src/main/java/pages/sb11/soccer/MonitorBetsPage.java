package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.CheckBox;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import objects.Event;
import org.openqa.selenium.support.Color;
import pages.sb11.WelcomePage;

public class MonitorBetsPage extends WelcomePage {
    public int colAC = 2;
    public int colStake = 6;
    int colT = 9;
    public Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
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
    public Button btnClearAll = Button.xpath("//button[text()='Clear All']");
    public Button btnSetSelection = Button.xpath("//button[text()='Set Selection']");

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
            waitSpinnerDisappeared();
        }
    }

    public PerformanceByMonthPage openPerfByMonth (String accountCode){
        int index = getACRowIndex(accountCode);
        tblOrder.getControlOfCell(1,colAC,index,"span[1]").click();
        waitSpinnerDisappeared();
        DriverManager.getDriver().switchToWindow();
        return new PerformanceByMonthPage();
    }

    public PendingBetsPage openPendingBets (String accountCode){
        int index = getACRowIndex(accountCode);
        tblOrder.getControlOfCell(1,colStake,index,"span[3]").click();
        DriverManager.getDriver().switchToWindow();
        waitSpinnerDisappeared();
        return new PendingBetsPage();
    }

    public Last12DaysPerformancePage openLast12DaysPerf (String accountCode){
        int index = getACRowIndex(accountCode);
        tblOrder.getControlOfCell(1,colT,index,null).click();
        waitSpinnerDisappeared();
        DriverManager.getDriver().switchToWindow();
        return new Last12DaysPerformancePage();
    }

    public int getACRowIndex(String accountCode){
        int i = 1;
        Label lblAC;
        while (true){
            lblAC = Label.xpath(tblOrder.getxPathOfCell(1,colAC,i,null));
            if(!lblAC.isDisplayed()) {
                System.out.println("Can NOT found AC "+accountCode+" in the table");
                return 0;
            }
            if(lblAC.getText().contains(accountCode)){
                System.out.println("Found AC "+accountCode+" in the table");
                return i;
            }
            i = i +1;
        }
    }

    public boolean isCheckBetsUpdateCorrect() {
        int firstNumOrder = tblOrder.getNumberOfRows(true);
        //Wait for Bets update in Monitor Bets page
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int secNumOder = tblOrder.getNumberOfRows(true);
        if (firstNumOrder < secNumOder){
            return true;
        }
        System.out.println("Do not have any bets");
        return true;
    }
    public boolean isCheckACDisplay(String accountCode){
        //wait for bet update in Monitor Bets page
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int indexAC = getACRowIndex(accountCode);
        if (indexAC == 0){
            System.err.println("AC "+ accountCode+ " is not display");
            return false;
        }
        return true;
    }

    public boolean isCheckBetDisplayCorrect(String accountCode, Event event) {
        //wait for bet update in Monitor Bets page
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int indexAC = getACRowIndex(accountCode);
        if (indexAC == 0){
            System.err.println("AC "+ accountCode+ " is not display");
            return false;
        }
        String eventName = Label.xpath(tblOrder.getxPathOfCell(1,tblOrder.getColumnIndexByName("Event"),indexAC,null)).getText();
        String eventNameExpect = event.getHome() + " -vs- "+ event.getAway();
        if (!eventName.contains(eventNameExpect)){
            System.err.println("Event "+ eventNameExpect + " is not display");
            return false;
        }
        return true;
    }

    public void showMasterByName(boolean show, String... masterName) {
        lblShowMaster.click();
        waitSpinnerDisappeared();
        btnClearAll.click();
        for(String option: masterName){
            selectOptionOnFilter(option, true);
        }
        btnSetSelection.click();
        waitSpinnerDisappeared();
        if (!show){
            btnShow.click();
            waitSpinnerDisappeared();
        }
    }

    public void selectOptionOnFilter(String optionName, boolean isChecked) {
        CheckBox chkOption = CheckBox.xpath(String.format("//th[.=\"%s\"]/preceding-sibling::th[1]/input", optionName));
        if (isChecked) {
            if (!chkOption.isSelected()) {
                chkOption.select();
            }
        } else {
            if (chkOption.isSelected())
                chkOption.deSelect();
        }
    }
    public void clickToCopyByAccountCode(String accountCode){
        int index = getACRowIndex(accountCode);
        tblOrder.getControlOfCell(1,tblOrder.getColumnIndexByName(""),index,"em").click();
        waitSpinnerDisappeared();
    }

    public String getReportByAccountCode(String accountCode) {
        int index = getACRowIndex(accountCode);
        return tblOrder.getControlOfCell(1,tblOrder.getColumnIndexByName("Report"),index,null).getText();
    }

    public String getBGColorByColumnName(String columnName, String accountCode) {
        int index = getACRowIndex(accountCode);
        String color = tblOrder.getControlOfCell(1,tblOrder.getColumnIndexByName(columnName),index,null).getColour();
        return Color.fromString(color).asHex().toUpperCase();
    }
    public void showBetType(boolean show, String... betType){
        lblShowBetType.click();
        waitSpinnerDisappeared();
        for(String option: betType){
            selectOptionOnFilter(option, true);
        }
        btnSetSelection.click();
        waitSpinnerDisappeared();
        if (show){
            btnShow.click();
            waitSpinnerDisappeared();
        }
    }
}
