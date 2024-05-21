package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.CheckBox;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import common.SBPConstants;
import controls.Table;
import objects.Event;
import objects.Order;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import utils.sb11.StakeSizeGroupUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public DropDownBox ddGroupType = DropDownBox.xpath("//div[text()='Group Type']//following-sibling::select");
    public DropDownBox ddStakeSizeGroup = DropDownBox.xpath("//select[@formcontrolname='stakeSizeGroup']");

    public void filterResult(String sport, String smartType, String punterType, String betPlacedIn, String betCount, boolean isTodayEvent, String lrbRule, String liveNonLive, String currency, String stake, boolean isShow){
        if (!sport.isEmpty()){
            ddpSport.selectByVisibleText(sport);
        }
        if (!smartType.isEmpty()){
            ddpSmartType.selectByVisibleText(smartType);
        }
        if (!punterType.isEmpty()){
            ddpPunterType.selectByVisibleText(punterType);
        }
        if (!betPlacedIn.isEmpty()){
            ddpBetPlacedIN.selectByVisibleText(betPlacedIn);
        }
        if (!betCount.isEmpty()){
            ddpBetCount.selectByVisibleText(betCount);
        }
        if (!lrbRule.isEmpty()){
            ddpLRBRule.selectByVisibleText(lrbRule);
        }
        if (isTodayEvent){
            cbTodayEvent.click();
        }
        if (!liveNonLive.isEmpty()){
            ddpLiveNonLive.selectByVisibleText(liveNonLive);
        }
        if (!currency.isEmpty()){
            ddpCurrency.selectByVisibleText(currency);
        }
        if (!stake.isEmpty()){
            ddpStake.selectByVisibleText(stake);
        }
        if (isShow){
            btnShow.click();
            waitSpinnerDisappeared();
        }
    }
    public void filterResult(String sport, String stakeSizeGroup, String betPlaceIn, String betCount, String liveNonLive, boolean isShow){
        if (!sport.isEmpty()){
            ddpSport.selectByVisibleText(sport);
        }
        if (!stakeSizeGroup.isEmpty()){
            ddStakeSizeGroup.selectByVisibleText(stakeSizeGroup);
        }
        if (!betPlaceIn.isEmpty()){
            ddpBetPlacedIN.selectByVisibleText(betPlaceIn);
        }
        if (!betCount.isEmpty()){
            ddpBetCount.selectByVisibleText(betCount);
        }
        if (!liveNonLive.isEmpty()){
            ddpLiveNonLive.selectByVisibleText(liveNonLive);
        }
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

    public boolean isEventDisplayCorrect(Order order) {
        //wait for bet update in Monitor Bets page
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int indexAC = getACRowIndex(order.getAccountCode());
        if (indexAC == 0){
            System.err.println("AC "+ order.getAccountCode()+ " is not display");
            return false;
        }
        String eventName = Label.xpath(tblOrder.getxPathOfCell(1,tblOrder.getColumnIndexByName("Event"),indexAC,null)).getText();
        String eventNameExpect = order.getEvent().getHome() + " -vs- "+ order.getEvent().getAway();
        if (!eventName.contains(eventNameExpect)){
            System.err.println("Event "+ eventNameExpect + " is not display");
            return false;
        }
        return true;
    }

    public void showMasterByName(boolean show, String... masterName) {
        lblShowMaster.click();
        waitSpinnerDisappeared();
        for(String option: masterName){
            selectOptionOnFilter(option, true);
        }
        btnSetSelection.click();
        waitSpinnerDisappeared();
        if (show){
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

    public boolean isOrdersValidStake(String stakeValue) {
        int orders = tblOrder.getNumberOfRows(false,true);
        if (orders == 0){
            System.err.println("No record found");
            return false;
        }
        Double validStake = SBPConstants.MonitorBets.VALID_STAKE.get(stakeValue);
        for (int i = 0; i < orders; i++){
            String autualStake = tblOrder.getControlOfCell(1,tblOrder.getColumnIndexByName("Stake"),i+1,"span[2]").getText().replace(",","");
            if (!(Double.valueOf(autualStake) >= validStake)){
                 System.out.println("FAILED! "+ autualStake+" is not available");
                 return false;
            }
        }
        return true;
    }

    private boolean checkOrderDisplayCorrect(Order order) {
        int indexAC = getACRowIndex(order.getAccountCode());
        if (indexAC==0){
            System.err.println("FAILED! Account code is not shown");
            return false;
        }
        String eventActual = tblOrder.getControlOfCell(1,tblOrder.getColumnIndexByName("Event"),indexAC,null).getText();
        String eventExpect = order.getEvent().getHome() + " -vs- "+ order.getEvent().getAway();
        String selectionActual = tblOrder.getControlOfCell(1,tblOrder.getColumnIndexByName("Selection"),indexAC,null).getText();
        String hdpPointActual = tblOrder.getControlOfCell(1,tblOrder.getColumnIndexByName("HDP"),indexAC,"span[1]").getText();
        String stakeActual = tblOrder.getControlOfCell(1,tblOrder.getColumnIndexByName("Stake"),indexAC,"span[2]").getText().replace(",","");
        if (!eventActual.contains(eventExpect)){
            System.out.println("FAILED! Event display incorrect");
            return false;
        }
        if (!selectionActual.equals(order.getSelection())){
            System.out.println("FAILED! Selection display incorrect");
            return false;
        }
        if (!hdpPointActual.contains(String.valueOf(order.getHandicap()))){
            System.out.println("FAILED! HDP display incorrect");
            return false;
        }
        if (!stakeActual.equals(String.valueOf(order.getRequireStake()))){
            System.out.println("FAILED! Stake display incorrect");
            return false;
        }
        return true;
    }
    public boolean isOrderDisplayCorrect(Order order){
        if (tblOrder.getNumberOfRows(false,true)==0){
            System.out.println("No record found!");
            return false;
        } else {
            return checkOrderDisplayCorrect(order);
        }
    }

    public boolean isCurrencyDisplayCorrect(String currency) {
        List<String> lstCur = tblOrder.getColumn(tblOrder.getColumnIndexByName("Stake"),10,false);
        for (String cur: lstCur){
            cur = cur.split("\n")[2];
            if (!cur.equals(currency)){
                System.out.println(cur+" difference from "+currency);
                return false;
            }
        }
        return true;
    }
    public void goToGroupType(String groupType){
        ddGroupType.selectByVisibleText(groupType);
        waitSpinnerDisappeared();
    }

    public void verifyBetsShowInStakeSizeGroup() {
        List<String> lstGroupAc = ddStakeSizeGroup.getOptions();
        List<String> lstACColumn = tblOrder.getColumn(tblOrder.getColumnIndexByName("AC"),true);
        Set<String> lstGroupEx = new HashSet<>();
        Set<String> lstMemberAc = new HashSet<>();
        for (String groupName : lstACColumn){
            lstGroupEx.add(groupName.split("\n")[0]);
            lstMemberAc.add(groupName.split("\n")[1]);
        }
        //Verify Group Name display correct
        for (String groupName : lstGroupEx){
            if (!lstGroupAc.contains(groupName)){
                Assert.assertTrue(false,"FAILED! "+groupName+" is not exist");
            }
        }
        //Verify Member Name belongs to Group Name
        for (int i = 0; i < lstACColumn.size();i++){
            String groupName = lstACColumn.get(i).split("\n")[0];
            String memberAc = lstACColumn.get(i).split("\n")[1];
            List<String> lstMemberOfGroup = StakeSizeGroupUtils.getLstAccCode(groupName);
            if (!lstMemberOfGroup.contains(memberAc)){
                Assert.assertTrue(false,"FAILED! "+memberAc+" display incorrect in group: "+groupName);
            }
        }
    }
}
