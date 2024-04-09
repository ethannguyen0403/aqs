package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.*;
import com.paltech.utils.DateUtils;
import controls.DateTimePicker;
import controls.Row;
import controls.Table;
import controls.sb11.AppArlertControl;
import controls.sb11.BBTTable;
import objects.Order;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public DropDownBox ddpLiveStatus = DropDownBox.xpath("//div[contains(@class, 'filter-more')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public Label lblFromDate = Label.xpath("//div[contains(text(),'From Date')]");
    public Label lblToDate = Label.xpath("//div[contains(text(),'To Date')]");
    public AppArlertControl alert = AppArlertControl.xpath("//app-alert");
    public Label lblNoRecord = Label.xpath("//span[text()='No record found']");

    public Button btnShowBetTypes = Button.xpath("//div[contains(text(),'Show Bet Types')]");
    public Button btnShowLeagues = Button.xpath("//div[contains(text(),'Show Leagues')]");
    public Button btnShowGroup = Button.xpath("//div[contains(text(),'Show Groups')]");
    public Button btnShowMaster = Button.xpath("//div[contains(text(),'Show Masters')]");
    public Button btnShowAgent = Button.xpath("//div[contains(text(),'Show Agents')]");
    public Button btnMoreFilter = Button.xpath("//div[contains(text(),'More Filters')]");
    public Button btnResetAllFilter = Button.xpath("//span[contains(text(),'Reset All Filters')]");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public Button btnShowMoreFilter = Button.xpath("//button[contains(@class,'btn-show-filter')]");
    public Button btnLeagues = Button.xpath("//app-bbt//div[text()='Show Leagues ']");
    public Button btnClearAll = Button.xpath("//app-bbt//button[text()='Clear All']");
    public Button btnSetSelection = Button.xpath("//app-filter-data//button[text()='Set Selection ']");
    public Label lblFirstGroupName = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//a)[1]");
    public Label lblFirstHomeName =  Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//thead)[1]");
    private Label lblFirstGroupHDP = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[2])[1]");
    private Label lblFirstGroupPrice = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[3])[1]");
    private Label lblFirstGroupLive = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[5])[1]");
    private Label lblFirstGroupNonLive = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[6])[1]");
    private Label lblFirstGroupPendingBet = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[7])[1]");
    public Label lblFirstGroupLast12Day = Label.xpath("(//app-league-table//table[@aria-describedby='home-table']//tbody//tr[1]//td[8])[1]");
    private Label lblFirstGroupS1 = Label.xpath("(//app-league-table//div[contains(@class,'league-time')]//span)[1]");
    private Label lblFirstGroupS12 = Label.xpath("(//app-league-table//div[contains(@class,'league-time')]//span)[4]");
    public Label lblEventStartTime = Label.xpath("//div[contains(@class, 'league-time') and not(contains(@class, 'flex-row-reverse'))]");
    public Icon iconCount = Icon.xpath("//span[contains(@class, 'count-ribbon')]");
    int totalColumnNumber = 8;
    public int colCur = 7;
    public int colPrice = 3;
    public int colStake = 4;
    public int colName = 1;
    public int colBetType = 2;
    public BBTTable tblBBT = new BBTTable("//table[contains(@class, 'table')]", totalColumnNumber);
    public Table tblFirstBBT = Table.xpath("(//app-bbt//table)[1]",totalColumnNumber);

    public void filter(String companyUnit, String sport, String smartType, String reportType, String fromDate, String toDate, String stake, String currency, String league){
        if (!companyUnit.isEmpty()){
            ddpCompanyUnit.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
        }
        if(!sport.isEmpty()){
            ddpSport.selectByVisibleText(sport);
            waitSpinnerDisappeared();
        }
        if(!smartType.isEmpty()){
            ddpSmartType.selectByVisibleText(smartType);
            waitSpinnerDisappeared();
        }
        if(!reportType.isEmpty()){
            ddpReportType.selectByVisibleText(reportType);
            waitSpinnerDisappeared();
        }
        if (!fromDate.isEmpty()) {
            dtpFromDate.selectDate(fromDate, "dd/MM/yyyy");
            waitSpinnerDisappeared();
            waitSpinnerDisappeared();
        }
        if (!toDate.isEmpty()) {
            dtpToDate.selectDate(toDate, "dd/MM/yyyy");
            waitSpinnerDisappeared();
            waitSpinnerDisappeared();
        }
        if(!stake.isEmpty()){
            ddpStake.selectByVisibleText(stake);
            waitSpinnerDisappeared();
        }
        if(!currency.isEmpty()){
            ddpCurrency.selectByVisibleText(currency);
            waitSpinnerDisappeared();
        }
        if(!league.isEmpty()){
            btnLeagues.click();
            waitSpinnerDisappeared();
            btnClearAll.click();
            filterLeague(league);
        }
        btnShow.click();
        waitSpinnerDisappeared();
        scrollToShowFullResults();
    }

    private void filterLeague(String leagueName) {
        Label lblSelectValue = Label.xpath(String.format("//table[@aria-label='group table']//span[text()=\"%s\"]//..//..//input",leagueName));
        lblSelectValue.click();
        btnSetSelection.click();
    }

    public List<String> getRowDataOfGroup(String groupName){
        return getGroupTable(groupName);
    }

    public int getNumberOfWindow(){
        return DriverManager.getDriver().getWindowHandles().size();
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
        Label lblFirst = Label.xpath(tblBBT.getSLinkXpath(1, "S1"));
        if (!lblFirst.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirst.click();
            waitSpinnerDisappeared();
            DriverManager.getDriver().switchToWindow();
            return new ReportS1Page();
        }
    }
    public ReportS1Page openReportSFirstGroup() {
        Label lblFirst = Label.xpath(tblBBT.getSLinkXpath(1, "S"));
        if (!lblFirst.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirst.click();
            waitSpinnerDisappeared();
            DriverManager.getDriver().switchToWindow();
            return new ReportS1Page();
        }
    }

    public ReportS12Page openReportS12FirstGroup() {
        Label lblFirst = Label.xpath(tblBBT.getSLinkXpath(1, "S12"));
        if (!lblFirst.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            lblFirst.click();
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

    public boolean verifyAllBetsIsOverUnder(List<String> betType) {
        if (betType == null) {
            Assert.assertTrue(false, "Bet Type List is null");
        }
        for (String bet : betType) {
            if (!bet.toUpperCase().contains("UNDER") && !bet.toUpperCase().contains("OVER")) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyAllCountIconsResetToAll() {
        List<WebElement> countIconList = iconCount.getWebElements();
        for (WebElement icon : countIconList) {
            if (!icon.getText().equalsIgnoreCase("All")) {
                return false;
            }
        }
        return countIconList.size() == 3 ? true : false;
    }

    public boolean verifyEventTimeDisplayCorrectWithTimeFilter(String fromDate, String toDate, List<String> dateList) {
        if (dateList == null) {
            System.out.println("Value list is empty");
            return false;
        }
        fromDate = fromDate + " 00:00";
        toDate = DateUtils.getDateAfterCurrentDate(1, "dd/MM/yyyy") + " 12:00";
        long fromDateL = convertToMilliSecond(fromDate, "dd/MM/yyyy HH:mm");
        long toDateL = convertToMilliSecond(toDate, "dd/MM/yyyy HH:mm");
        for (String date : dateList) {
            long actualDateL = convertToMilliSecond(date, "dd/MM/yy HH:mm");
            if (actualDateL < fromDateL || actualDateL > toDateL) {
                return false;
            }
        }
        return true;
    }

    private long convertToMilliSecond(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            System.out.println("Failed to parse date");
            return -1;
        }
    }

    public void scrollToShowFullResults(){
        // Scroll down to bottom to show full results
        int count = 7;
        while (count > 0){
            DriverManager.getDriver().executeJavascript("window.scrollTo(0, document.body.scrollHeight)");
            waitPageLoad();
            count --;
        }
        DriverManager.getDriver().executeJavascript("window.scrollTo(0, 0)");
    }

    public List<String> getListLeagueTime() {
        int index = 1;
        List<String> leaguesList = new ArrayList<>();
        while (true) {
            Label leagueName = Label.xpath(tblBBT.getLeagueTimeXpath(index));
            if (leagueName.isDisplayed()) {
                leaguesList.add(leagueName.getText());
                index++;
            }
            if (!leagueName.isDisplayed()) {
                return leaguesList;
            }
        }
    }

    public List<String> getListColOfAllBBTTable(int colOrder) {
        List<String> expectedList = new ArrayList<>();
        for (int i = 1; i <= tblBBT.getTableControl().getWebElements().size(); i++) {
            String cellValue = tblBBT.getTableControl(i).getControlOfCell(1, colOrder, 1, null).getText().trim();
            if (!cellValue.isEmpty())
                expectedList.add(cellValue);
        }
        return expectedList;
    }

    public  List<String> getStakeListOfAllEvents(){
        return getListColOfAllBBTTable(colStake);
    }

    public  List<String> getCurrencyListOfAllEvents(){
        return getListColOfAllBBTTable(colCur);
    }

    public  List<String> getBetTypeListOfAllEvents(){
        return getListColOfAllBBTTable(colBetType);
    }

    public List<String> getSmartGroupName(){
        return getListColOfAllBBTTable(colName);
    }

    public double convertStakeFilter(String stakeFilter) {
        switch (stakeFilter) {
            case "Above 1k":
                return 1000;
            case "Above 10k":
                return 10000;
            case "Above 50k":
                return 50000;
            case "Above 150k":
                return 150000;
            default:
                return 0;
        }
    }

    public boolean verifyAllStakeCorrectFilter(String filterStake, List<String> stakeEvent) {
        if(stakeEvent == null) return false;
        double minNumber = convertStakeFilter(filterStake);
        for (String stake : stakeEvent) {
            double stakeDou = Double.valueOf(stake.replaceAll(",", ""));
            if (stakeDou < minNumber)
                return false;
        }
        return true;
    }

    public List<String> getListAllLeaguesName() {
        int index = 1;
        List<String> leaguesList = new ArrayList<>();
        while (true) {
            Label leagueName = Label.xpath(tblBBT.getLeagueNameXpath(index));
            if (leagueName.isDisplayed()) {
                leaguesList.add(leagueName.getText());
                index++;
            }
            if (!leagueName.isDisplayed()) {
                System.out.println("NOT found table with League name: "+ leagueName);
                return leaguesList;
            }
        }
    }

    public boolean verifyAllElementOfListAreTheSame(String expectedValue, List<String> actualList){
        return new HashSet<>(actualList).size() == 1 && actualList.get(0).equalsIgnoreCase(expectedValue);
    }

    public boolean verifyFilterDisplayWithOption(String... options) {
        List<String> listFilter = getAllOptionNameFilter();
        if (listFilter.isEmpty() || listFilter == null) return false;
        if (!Arrays.asList(options).containsAll(listFilter)) return false;
        return true;
    }

    public List<String> getAllOptionNameFilter() {
        int indexOption = 1;
        List<String> optionsName = new ArrayList<>();
        while (true) {
            Label lblOption = Label.xpath(String.format("(//div[contains(@class,'card-columns')]//span)[%s]", indexOption));
            if (lblOption.isDisplayed()) {
                indexOption++;
                optionsName.add(lblOption.getText().trim());
            }
            if (!lblOption.isDisplayed()) {
                System.out.println("NOT Found value option label with index: " + indexOption);
                return optionsName;
            }
        }
    }

    public List<String> getSelectedOptionNameOfFilter(){
        List<String> optionsName = new ArrayList<>();
        int indexOption = 1;
        while (true){
            CheckBox chkOption = CheckBox.xpath(String.format("(//div[@class='card-columns']//input)[%s]", indexOption));
            Label lblOption = Label.xpath(String.format("(//div[@class='card-columns']//span)[%s]", indexOption));
            if(chkOption.isDisplayed()){
                indexOption++;
                if (chkOption.isSelected()){
                    optionsName.add(lblOption.getText().trim());
                }
            }
            if(!chkOption.isDisplayed() || chkOption.getWebElement()==null){
                return optionsName;
            }
        }
    }

    public boolean isOptionsFilterDisplay(List<String> optionsList) {
        if (optionsList == null) return false;
        for (String option : optionsList) {
            Label lblOption = Label.xpath(String.format("//div[contains(@class, 'card-columns')]//span[.=\"%s\"]", option));
            if (!lblOption.isDisplayed())
                return false;
        }
        return true;
    }

    public void verifyHomeAwayTeamNameCorrect(String homeName, String awayName) {
        int tableIndex = findTableIndexByTeam(homeName);
        Assert.assertTrue(tblBBT.getHomeTableControl(tableIndex).getHeaderNameOfRows().contains(homeName),
                String.format("FAILED! Home team: %s is not displayed on right table", homeName));
        Assert.assertTrue(tblBBT.getAwayTableControl(tableIndex).getHeaderNameOfRows().contains(awayName),
                String.format("FAILED! Away team: %s is not displayed on left table", awayName));
    }

    /**
     * @param isLeftTable define left or right table to find row Index
     * @return row index of bet base on variables of order object: stake and price
     * */
    public int findRowIndexOfTeamTable(Order order, boolean isLeftTable){
        int rowIndex = 1;
        int tableHomeIndex = findTableIndexByTeam(order.getHome());
        int tableIndex = isLeftTable ? tableHomeIndex : tableHomeIndex + 1;
        Table tblTeam = tblBBT.getTableControl(tableIndex);
        while(true){
           Label lblStake = Label.xpath(tblTeam.getxPathOfCell(1, colStake, rowIndex, null));
           Label lblOdds = Label.xpath(tblTeam.getxPathOfCell(1, colPrice, rowIndex, null));
           if(lblStake.isDisplayed()){
               if (Double.valueOf(lblStake.getText()) == order.getRequireStake() &&
                       lblOdds.getText().contains(String.valueOf(order.getPrice()))) {
                   return rowIndex;
               }
               rowIndex++;
           }
            if(!lblStake.isDisplayed()){
                System.out.println("NOT found the Row Index ");
                return -1;
            }
        }
    }

    public int findTableIndexByTeam(String teamName) {
        int index = 1;
        while (true) {
            Table tblTeam = tblBBT.getTableControl(index);
            if (tblTeam.isDisplayed()) {
                if (tblTeam.getHeaderNameOfRows().contains(teamName)) {
                    return index;
                }
                index++;
            }
            if (!tblTeam.isDisplayed()) {
                System.out.println("NOT found table with Team name: "+ teamName);
                return -1;
            }
        }
    }

    public void selectLeaguesFilter(String... leaguesName){
        btnLeagues.click();
        waitSpinnerDisappeared();
        btnClearAll.click();
        for(String option: leaguesName){
            selectOptionOnFilter(option, true);
        }
        btnSetSelection.click();
        waitSpinnerDisappeared();
        btnShow.click();
        waitSpinnerDisappeared();
        scrollToShowFullResults();
    }

    public void selectGroupsFilter(String... groupName){
        btnShowGroup.click();
        waitSpinnerDisappeared();
        btnClearAll.click();
        for(String option: groupName){
            selectOptionOnFilter(option, true);
        }
        btnSetSelection.click();
        waitSpinnerDisappeared();
        btnShow.click();
        waitSpinnerDisappeared();
        scrollToShowFullResults();
    }

    public void selectBetTypesFilter(String sportName, String... betType){
        ddpSport.selectByVisibleText(sportName);
        waitSpinnerDisappeared();
        btnShowBetTypes.click();
        btnClearAll.click();
        for(String option: betType){
            selectOptionOnFilter(option, true);
        }
        btnSetSelection.click();
        waitSpinnerDisappeared();
        btnShow.click();
        waitSpinnerDisappeared();
        scrollToShowFullResults();
    }

    public void selectSmartTypeFilter(String smartType, String... optionName) {
        ddpSmartType.selectByVisibleText(smartType);
        waitSpinnerDisappeared();
        Button btnSmartFilter;
        switch (smartType) {
            case "Group":
                btnSmartFilter = btnShowGroup;
                break;
            case "Master":
                btnSmartFilter = btnShowMaster;
                break;
            case "Agent":
                btnSmartFilter = btnShowAgent;
                break;
            default:
                System.out.println("NOT Found Show smart Type: '" + smartType + "' button");
                return;
        }
        btnSmartFilter.click();
        waitSpinnerDisappeared();
        btnClearAll.click();
        for(String option: optionName){
            selectOptionOnFilter(option, true);
        }
        btnSetSelection.click();
        waitSpinnerDisappeared();
        btnShow.click();
        waitSpinnerDisappeared();
        scrollToShowFullResults();
    }

    public void selectLiveNonLiveMoreFilter(String option){
        btnMoreFilter.click();
        if(!option.equalsIgnoreCase("All")){
            ddpLiveStatus.selectByVisibleText(option);
        }
        btnShowMoreFilter.click();
        waitSpinnerDisappeared();
    }

    public boolean verifyFirstGroupNameUnderTeamName() {
        int teamNameHeight = lblFirstHomeName.getWebElement().getRect().getHeight();
        int groupNameHeight = lblFirstGroupName.getWebElement().getRect().getHeight();
        return teamNameHeight > groupNameHeight;
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

    public void verifyOpenPriceDisplay(String teamName, String ft12HAHome, String ft12HAAway, String ft12Draw, String ftHDPHome, String ftHDPAway, String ftHDPPriceHome, String ftHDPPriceAway, String ftOUHDPHome, String ftOUHDPAway, String ftOUPriceHome, String ftOUPriceAway, boolean isHomeTeam) {
        if (isHomeTeam){
            Table tbHomeTeam = Table.xpath(String.format("//span[text()='%s']/ancestor::table",teamName),8);
            List<String> lstHeader = tbHomeTeam.getColumnNamesOfTable();
            String hdpPriceHome = lstHeader.get(4).split("\n")[0].replace("(","").replace(")","");
            String ouPriceHome = lstHeader.get(4).split("\n")[2].replace("(","").replace(")","");
            String hdpHDPAway = lstHeader.get(4).split("\n")[1];
            String ouHDPHome = lstHeader.get(4).split("\n")[3];
            Assert.assertEquals(ftHDPPriceHome,hdpPriceHome,"FAILED! FT HDP Price Home display incorrect!");
            Assert.assertEquals(ftOUPriceHome,ouPriceHome,"FAILED! FT Over Under Price Home display incorrect!");
            Assert.assertEquals(ftHDPAway,hdpHDPAway,"FAILED! FT HDP Away display incorrect!");
            Assert.assertEquals(ftOUHDPHome,ouHDPHome,"FAILED! FT Over Under HDP Home display incorrect!");
        } else {
            Table tbAwayTeam = Table.xpath(String.format("//span[text()='%s']/ancestor::table",teamName),8);
            List<String> lstHeader = tbAwayTeam.getColumnNamesOfTable();
            String haHome = lstHeader.get(1).split("\n")[0];
            String haAway = lstHeader.get(1).split("\n")[2];
            String ft1x2Draw = lstHeader.get(1).split("\n")[1];
            String hdpPriceAway = lstHeader.get(0).split("\n")[0].replace("(","").replace(")","");
            String hdpHDPHome = lstHeader.get(0).split("\n")[1];
            String ouPriceAway = lstHeader.get(0).split("\n")[2].replace("(","").replace(")","");
            String ouHDPAway = lstHeader.get(0).split("\n")[3];
            Assert.assertEquals(ft12HAHome,haHome,"FAILED! FT 1x2 H/A Home display incorrect!");
            Assert.assertEquals(ft12HAAway,haAway,"FAILED! 1x2 H/A Away display incorrect!");
            Assert.assertEquals(ft1x2Draw,ft12Draw,"FAILED! FT 1x2 Draw display incorrect!");
            Assert.assertEquals(ftHDPPriceAway,hdpPriceAway,"FAILED! FT HDP Price Away display incorrect!");
            Assert.assertEquals(ftHDPHome,hdpHDPHome,"FAILED! FT HDP HDP Home display incorrect!");
            Assert.assertEquals(ftOUPriceAway,ouPriceAway,"FAILED! FT OU Price Away display incorrect!");
            Assert.assertEquals(ftOUHDPAway,ouHDPAway,"FAILED! FT OU HDP Away display incorrect!");
        }
    }
}
