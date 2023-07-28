package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;
import controls.DateTimePicker;
import controls.Row;
import controls.Table;
import pages.sb11.WelcomePage;

import java.util.ArrayList;
import java.util.List;

public class BBTPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpReportType = DropDownBox.xpath("//div[contains(text(),'Report Type')]//following::select[1]");
    public DropDownBox ddpSmartType = DropDownBox.xpath("//div[contains(text(),'Smart Type')]//following::select[1]");
    public DropDownBox ddpSport = DropDownBox.xpath("//div[contains(text(),'Sport')]//following::select[1]");
    public DropDownBox ddpStake = DropDownBox.xpath("//div[contains(text(),'Stake')]//following::select[1]");
    public DropDownBox ddpCurrency = DropDownBox.xpath("//div[contains(text(),'Currency')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-days-calendar-view");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-days-calendar-view");
    public Label lblFromDate = Label.xpath("//div[contains(text(),'From Date')]");
    public Label lblToDate = Label.xpath("//div[contains(text(),'To Date')]");

    public Button btnShowBetTypes = Button.xpath("//div[contains(text(),'Show Bet Types')]");
    public Button btnShowLeagues = Button.xpath("//div[contains(text(),'Show Leagues')]");
    public Button btnShowGroup = Button.xpath("//div[contains(text(),'Show Groups')]");
    public Button btnMoreFilter = Button.xpath("//div[contains(text(),'More Filters')]");
    public Button btnResetAllFilter = Button.xpath("//span[contains(text(),'Reset All Filters')]");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public Button btnLeagues = Button.xpath("//app-bbt//div[text()='Show Leagues ']");
    public Button btnClearAll = Button.xpath("//app-bbt//button[text()='Clear All']");
    public Button btnSetSelection = Button.xpath("//app-filter-data//button[text()='Set Selection ']");
    private Label lblFirstGroupName = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//a)[1]");
    private Label lblFirstGroupHDP = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[2])[1]");
    private Label lblFirstGroupPrice = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[3])[1]");
    private Label lblFirstGroupLive = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[5])[1]");
    private Label lblFirstGroupNonLive = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[6])[1]");
    private Label lblFirstGroupPendingBet = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[7])[1]");
    private Label lblFirstGroupLast12Day = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[8])[1]");
    private Label lblFirstGroupS1 = Label.xpath("(//app-league-table//div[contains(@class,'league-time')]//span)[1]");
    private Label lblFirstGroupS12 = Label.xpath("(//app-league-table//div[contains(@class,'league-time')]//span)[2]");
    int totalColumnNumber = 8;
    public Table tblBBT = Table.xpath("//app-bbt//table",totalColumnNumber);


    public void filter(String companyUnit, String sport, String smartType, String reportType, String fromDate, String toDate, String stake, String currency, String league){
        if (!companyUnit.isEmpty())
            ddpCompanyUnit.selectByVisibleText(companyUnit);
        if(!sport.isEmpty())
            ddpSport.selectByVisibleText(sport);
        if(!smartType.isEmpty())
            ddpSmartType.selectByVisibleText(smartType);
        if(!reportType.isEmpty())
            ddpReportType.selectByVisibleText(reportType);
        String currentDate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
//        String currentDate = txtFromDate.getAttribute("value");
        if(!fromDate.isEmpty())
            if(!currentDate.equals(fromDate))
                dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
                waitSpinnerDisappeared();
//        currentDate = txtToDate.getAttribute("value");
        if(!toDate.isEmpty())
            if(!currentDate.equals(toDate))
                dtpToDate.selectDate(toDate,"dd/MM/yyyy");
                waitSpinnerDisappeared();
        if(!stake.isEmpty())
            ddpStake.selectByVisibleText(stake);
        if(!currency.isEmpty())
            ddpCurrency.selectByVisibleText(currency);
        if(!league.isEmpty())
            btnLeagues.click();
            btnClearAll.click();
            filterLeague(league);
        btnShow.click();
        waitSpinnerDisappeared();
    }

    private void filterLeague(String leagueName) {
        Label lblSelectValue = Label.xpath(String.format("//table[@aria-label='group table']//span[text()=\"%s\"]//..//..//input",leagueName));
        lblSelectValue.click();
        btnSetSelection.click();
    }

    public List<String> getRowDataOfGroup(String groupName){
        return getGroupTable(groupName);
    }

    private List<String> getGroupTable(String groupName){
        int index = 1;
        Table tblGroup;
        String tableXpath;
        while (true){
            tableXpath ="//app-bbt//div[contains(@class,'filter bg-white')]["+ index +"]/table";
            tblGroup = Table.xpath(tableXpath,totalColumnNumber);
            if(!tblGroup.isDisplayed()){
                System.out.println("**DEBUG: Not found the table group " +groupName);
                return null;
            }
            int rowContainsGroupName = getRowContainsGroupName(tableXpath,1,groupName);
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
    private List<String> getDataRowOfAGroupName(String xpathTable, int rowIndex){
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

    public MonthPerformancePage openMonthPerformanceFirstGroup() {
        if (!lblFirstGroupName.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirstGroupName.click();
            DriverManager.getDriver().switchToWindow();
            waitSpinnerDisappeared();
            return new MonthPerformancePage();
        }
    }

    public Last50BetsPage openLast50BetsFirstGroup() {
        if (!lblFirstGroupHDP.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirstGroupHDP.click();
            DriverManager.getDriver().switchToWindow();
            waitSpinnerDisappeared();
            return new Last50BetsPage();
        }
    }

    public LeaguePerformancePage openLeaguePerformanceFirstGroup() {
        if (!lblFirstGroupPrice.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirstGroupPrice.click();
            DriverManager.getDriver().switchToWindow();
            waitSpinnerDisappeared();
            return new LeaguePerformancePage();
        }
    }

    public LiveLast50BetsPage openLiveLast50BetsFirstGroup() {
        if (!lblFirstGroupLive.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirstGroupLive.click();
            DriverManager.getDriver().switchToWindow();
            waitSpinnerDisappeared();
            return new LiveLast50BetsPage();
        }
    }

    public NonLiveLast50BetsPage openNonLiveLast50BetsFirstGroup() {
        if (!lblFirstGroupNonLive.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirstGroupNonLive.click();
            DriverManager.getDriver().switchToWindow();
            waitSpinnerDisappeared();
            return new NonLiveLast50BetsPage();
        }
    }

    public PendingBetsPage openPendingBetFirstGroup() {
        if (!lblFirstGroupPendingBet.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirstGroupPendingBet.click();
            DriverManager.getDriver().switchToWindow();
            waitSpinnerDisappeared();
            return new PendingBetsPage();
        }
    }

    public ReportS1Page openReportS1FirstGroup() {
        if (!lblFirstGroupS1.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirstGroupS1.click();
            DriverManager.getDriver().switchToWindow();
            waitSpinnerDisappeared();
            return new ReportS1Page();
        }
    }

    public ReportS12Page openReportS12FirstGroup() {
        if (!lblFirstGroupS12.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirstGroupS12.click();
            DriverManager.getDriver().switchToWindow();
            waitSpinnerDisappeared();
            return new ReportS12Page();
        }
    }

    public Last12DaysPerformancePage openLast12DayPerformanceFirstGroup() {
        if (!lblFirstGroupLast12Day.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirstGroupLast12Day.click();
            DriverManager.getDriver().switchToWindow();
            waitSpinnerDisappeared();
            return new Last12DaysPerformancePage();
        }
    }

    public List<String> getFirstRowGroupData() {
        List <String> lstData = new ArrayList<>();
        if (!lblFirstGroupName.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            Row firstGroupRow = Row.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr)[1]//td");
            for (int i = 0; i < firstGroupRow.getWebElements().size(); i++) {
                String xpathData = String.format("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr)[1]//td[%s]",i+1);
                Label lblDataCell = Label.xpath(xpathData);
                lstData.add(lblDataCell.getText());
            }
            return lstData;
        }
    }

}
