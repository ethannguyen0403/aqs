package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.*;
import com.paltech.utils.DoubleUtils;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.popup.PTPerformancePopup;
import pages.sb11.soccer.popup.spp.SmartGroupPopup;

import java.util.ArrayList;
import java.util.List;

public class SPPPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public DropDownBox ddSport = DropDownBox.xpath("//div[contains(text(),'Sport')]//following::select[1]");
    public DropDownBox ddpReportBy = DropDownBox.xpath("//div[contains(text(),'Report By')]//following::select[1]");
    public DropDownBox ddpPunterType = DropDownBox.xpath("//div[contains(text(),'Punter Type')]//following::select[1]");
    public DropDownBox ddpSmartMaster = DropDownBox.xpath("//div[contains(text(),'Smart Master')]//following::select[1]");
    public DropDownBox ddpSmartAgent = DropDownBox.xpath("//div[contains(text(),'Smart Agent')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.xpath("//div[contains(@class,'container-fluid py-5 cbody')]//div[contains(@class,'card-body border')][1]/div[7]//input");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public CheckBox cbShowTaxAmount = CheckBox.id("defaultCheck1");
    public Button btnShowBetTypes = Button.xpath("//label[contains(text(),'Show Bet Types')]");
    public Button btnShowLeagues = Button.xpath("//label[contains(text(),'Show Leagues')]");
    public Button btnSmartGroup = Button.xpath("//label[contains(text(),'Smart Group')]");
    public Button btnReset = Button.xpath("//div[contains(text(),'Reset All Filters')]");
    public Button btnMoreFilters = Button.xpath("//button[contains(text(),'More Filters')]");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Label lblSmartGroup = Label.xpath("//div[contains(@class,'container-fluid py-5 cbody')]//div[contains(@class,'card-body border')][2]/div[4]");
    public Table tblSPP = Table.xpath("//app-spp//table",15);
    public Table tblSPPTax = Table.xpath("//app-spp//table",17);
    public Label lblFromDate = Label.xpath("//div[contains(text(),'From Date')]");
    public Label lblToDate = Label.xpath("//div[contains(text(),'To Date')]");
    public Label lblShowTaxAmount = Label.xpath("//label[contains(text(),'Show Tax Amount')]");
    public Button btnSetSelection = Button.xpath("//button[contains(@class, 'set-selection')]");
    public Button btnClearAll = Button.xpath("//button[.='Clear All']");
    int totalColumnNumber = 15;
    int colGroupCode = 2;
    public int colWins = 6;
    public int colMB = 9;
    public int colAVgStake = 10;
    public int colTurnOver = 11;
    public int colWL =12;
    public int colWLPercent = 14;
    int colMP = 3;
    public int colPT = 4;

    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    /**
     * This function use to filter data in SPP page, use filter with the paramerter as below
     * @param reportBy
     * @param punterType
     * @param smartMaster
     * @param smartAgent
     * @param fromDate
     * @param toDate
     */
    public void filter(String sport, String reportBy, String punterType, String smartMaster, String smartAgent, String fromDate, String toDate) {
        waitPageLoad();
        if (!sport.isEmpty())
            ddSport.selectByVisibleText(sport);
        if(!reportBy.isEmpty())
            ddpReportBy.selectByVisibleText(reportBy);
        if(!punterType.isEmpty())
            ddpPunterType.selectByVisibleText(punterType);
        if(!smartMaster.isEmpty())
            ddpSmartMaster.selectByVisibleText(smartMaster);
        if(!smartAgent.isEmpty())
            ddpSmartAgent.selectByVisibleText(smartAgent);
        String currentDate = txtFromDate.getAttribute("value");
        if(!fromDate.isEmpty())
            if(!currentDate.equals(fromDate))
                dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        currentDate = txtToDate.getAttribute("value");
        if(!toDate.isEmpty())
            if(!currentDate.equals(toDate))
                dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        btnShow.click();
        waitSpinnerDisappeared();
    }



    public void selectShowBetTypes(String... betTypes){
        btnShowBetTypes.click();
        waitSpinnerDisappeared();
        btnClearAll.click();
        for(String option: betTypes){
            selectOptionOnFilter(option, true);
        }
        btnSetSelection.click();
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public void selectSmartGroup(String... groupName){
        btnSmartGroup.click();
        waitSpinnerDisappeared();
        btnClearAll.click();
        for(String option: groupName){
            selectOptionOnFilter(option, true);
        }
        btnSetSelection.click();
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public void selectOptionOnFilter(String optionName, boolean isChecked) {
        CheckBox chkOption = CheckBox.xpath(String.format("//div[contains(.,\"%s\") and contains(@class, 'flex-row list-per-item')]//input", optionName));
        if (isChecked) {
            if (!chkOption.isSelected()) {
                chkOption.select();
            }
        } else {
            if (chkOption.isSelected())
                chkOption.deSelect();
        }
    }

    public LeaguePerformancePage openLeaguePerformance(String groupName){
        String tableXpath = String.format("//th[contains(text(),'%s')]//ancestor::table",groupName);
        Table tblSPP1 = Table.xpath(tableXpath,15);
        int rowIndex = getRowContainsGroupName(tableXpath,colGroupCode,groupName);
        tblSPP1.getControlOfCellSPP(1,colGroupCode, rowIndex,null).click();
        DriverManager.getDriver().switchToWindow();
        return new LeaguePerformancePage();
    }

    public PerformanceByMonthPage openPerfByMonth (String groupName){
        String tableXpath = String.format("//th[contains(text(),'%s')]//ancestor::table",groupName);
        Table tblSPP1 = Table.xpath(tableXpath,15);
        int rowIndex = getRowContainsGroupName(tableXpath,colGroupCode,groupName);
        tblSPP1.getControlOfCellSPP(1,colMP, rowIndex,null).click();
        DriverManager.getDriver().switchToWindow();
        waitPageLoad();
        return new PerformanceByMonthPage();
    }

    public PTPerformancePopup openAccountPTPerf (String groupName){
        String tableXpath = String.format("//th[contains(text(),'%s')]//ancestor::table",groupName);
        Table tblSPP1 = Table.xpath(tableXpath,15);
        int rowIndex = getRowContainsGroupName(tableXpath,colGroupCode,groupName);
        tblSPP1.getControlOfCellSPP(1,colPT, rowIndex,null).click();
        return new PTPerformancePopup();
    }

    /**
     * This function click on Smart Group lable in Filter section then filter the selected group
     * @param smartGroup
     */
    private void filterSmartGroup(String smartGroup) {
      SmartGroupPopup smartGroupPopup = clickSmartGroup();
      smartGroupPopup.setSmartGroup(smartGroup);
      btnShow.click();
      waitSpinnerDisappeared();
    }

    public List<String> getRowDataOfGroup(String groupName){
       return getGroupTable(groupName);
    }

    private SmartGroupPopup clickSmartGroup(){
        lblSmartGroup.click();
        return new SmartGroupPopup();
    }

    /* Start This is work account to get a data in a row of table. Because table element on this page is different (//table/tbody/tr/th)
    so we have to write other function to cover this case.
     */
    private List<String> getGroupTable(String groupName){
        int index = 1;
        Table tblGroup;
        String groupCode;
        String tableXpath;
        while (true){
            tableXpath ="//app-spp//div[contains(@class,'filter bg-white')]["+ index +"]/table";
            tblGroup = Table.xpath(tableXpath,totalColumnNumber);
            if(!tblGroup.isDisplayed()){
                System.out.println("**DEBUG: Not found the table group " +groupName);
                return null;
            }
            int rowContainsGroupName = getRowContainsGroupName(tableXpath,colGroupCode,groupName);
            if(rowContainsGroupName != 0){
                return getDataRowOfAGroupName(tableXpath,rowContainsGroupName);
            }
            index = index +1;
        }
    }

    // handle for get a row index contains the value
    private int getRowContainsGroupName(String tblTableXpath,int colIndex,String groupName){
        String cellXpath;
        int rowIndex = 1;
        String groupCode;
        while (true){
            cellXpath = String.format("%s%s", tblTableXpath, String.format("//tbody[1]/tr[%s]/th[%s]", rowIndex, colIndex));
            Label lblCel = Label.xpath(cellXpath);
            if(!lblCel.isDisplayed())
                return 0;
            groupCode = lblCel.getText().trim();
            if(groupCode.equals(groupName)){
                return rowIndex;
            }
            rowIndex = rowIndex +1;
        }
    }

    // handle for get a data on input row
    private List<String> getDataRowOfAGroupName(String xpathTable,int rowIndex){
        List<String> lstRowData = new ArrayList<>();
        String cellXpath;
        int i = 1;
        while(true) {
            cellXpath = String.format("%s%s", xpathTable, String.format("//tbody[1]/tr[%s]/th[%s]", rowIndex, i));
            Label lblCel = Label.xpath(cellXpath);
            if(!lblCel.isDisplayed())
                return lstRowData;
            lstRowData.add(lblCel.getText().trim());
            i = i +1;
        }
    }

   /* End handle for Table  */

    public boolean verifyAmountDataMatch(String actual, String expected){
        double actualDouble = Double.parseDouble(actual);
        String roundAcutal = String.format("%,.0f",DoubleUtils.roundUpWithTwoPlaces(actualDouble));
        return roundAcutal.equals(expected);
    }

    public int calculateAvg(List<Double> stakeList) {
        if (stakeList == null) return -1;
        int avgStake = 0;
        for (Double stake : stakeList) {
            avgStake += stake;
        }
        return Math.round(avgStake / stakeList.size());
    }

    public int calculateTotal(List<Double> stakeList){
        if(stakeList==null)return -1;
        int totalStake = 0;
        for (Double stake : stakeList) {
            totalStake += stake;
        }
        return Math.round(totalStake);
    }
}
