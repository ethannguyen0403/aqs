package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.*;
import com.paltech.utils.DateUtils;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.Row;
import controls.Table;
import objects.Order;
import org.json.JSONObject;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.controls.BetByGroupTableControl;
import pages.sb11.soccer.popup.bbg.BBGLastDaysPerformacesPopup;
import pages.sb11.soccer.popup.bbg.BetByTeamPricePopup;
import utils.sb11.AccountSearchUtils;
import utils.sb11.CompanySetUpUtils;

import java.util.*;

import static common.SBPConstants.*;

public class BBGPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
    private String xPathBetByGroupTableControl = "//app-bets-by-group-table//div[contains(@class,'table-contain')]";
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpReportType = DropDownBox.xpath("//div[contains(text(),'Report Type')]//following::select[1]");
    public DropDownBox ddpSmartType = DropDownBox.xpath("//div[contains(text(),'Smart Type')]//following::select[1]");
    public DropDownBox ddpSport = DropDownBox.xpath("//div[contains(text(),'Sport')]//following::select[1]");
    public DropDownBox ddpWinLose = DropDownBox.xpath("//div[contains(text(),'Win/Lose')]//following::select[1]");
    public DropDownBox ddpStake = DropDownBox.xpath("//div[contains(text(),'Stake')]//following::select[1]");
    public DropDownBox ddpCurrency = DropDownBox.xpath("//div[contains(text(),'Currency')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public Label lblFromDate = Label.xpath("//div[contains(text(),'From Date')]");
    public Label lblToDate = Label.xpath("//div[contains(text(),'To Date')]");

    public Button btnShowBetTypes = Button.xpath("//div[contains(text(),'Show Bet Types')]");
    public Button btnHideBetTypes = Button.xpath("//div[contains(text(),'Hide Bet Types')]");
    public Button btnShowLeagues = Button.xpath("//div[contains(text(),'Show Leagues')]");
    public Button btnHideLeagues = Button.xpath("//div[contains(text(),'Hide Leagues')]");
    public Button btnShowGroup = Button.xpath("//div[contains(text(),'Show Groups')]");
    public Button btnHideGroup = Button.xpath("//div[contains(text(),'Hide Groups')]");
    public Button btnShowEvent = Button.xpath("//div[contains(text(),'Show Events')]");
    public Button btnHideEvent = Button.xpath("//div[contains(text(),'Hide Events')]");
    public Button btnResetAllFilter = Button.xpath("//span[contains(text(),'Reset All Filters')]");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    BetByGroupTableControl  firstBetByGroupTableControl = BetByGroupTableControl.xpath(String.format("%s[%d]", xPathBetByGroupTableControl, 1));

    int totalColumnNumber = 13;
    int colCur = 7;
    public Table tblBBG = Table.xpath("//app-bbg//table",totalColumnNumber);
    public Label lblFirstAccount = Label.xpath("(//app-bbg//table//tbody//tr//td[2])[1]");
    public Button btnSetSelection = Button.xpath("//button[contains(text(),'Set Selection')]");
    String tableXpath = "//table[contains(@aria-label,'bbg-table')]";
    public DropDownBox ddpGroupType = DropDownBox.xpath("//label[contains(text(),'Group Type')]/parent::div//following-sibling::div/select");
    String tblStakeSizeGroupXpath = "//div[contains(text(),'%s')]//ancestor::div[contains(@class,'header')]/following-sibling::div/div/table";
    public DropDownBox ddpStakeSizeGroup = DropDownBox.xpath("//div[contains(text(),'Stake Size Group')]//following-sibling::select[1]");
    Label lblNoRecord = Label.xpath("//span[contains(text(),'No record found')]");

    public void filter(String sport, String companyUnit, String smartType, String reportType, String fromDate, String toDate, String stake, String currency){
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
        if(!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        if(!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
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
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public void filter(String sport, String companyUnit, String stakeSizeGroup, String reportType, String fromDate, String toDate){
        if (!companyUnit.isEmpty()){
            ddpCompanyUnit.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
        }
        if(!sport.isEmpty()){
            ddpSport.selectByVisibleText(sport);
            waitSpinnerDisappeared();
        }
        if(!stakeSizeGroup.isEmpty()){
            ddpStakeSizeGroup.selectByVisibleText(stakeSizeGroup);
            waitSpinnerDisappeared();
        }
        if(!reportType.isEmpty()){
            ddpReportType.selectByVisibleText(reportType);
            waitSpinnerDisappeared();
        }
        if(!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        if(!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    private void openFilterAdvance(String nameFilter){
        switch (nameFilter){
            case "Bet Types":
                if (btnShowBetTypes.isDisplayed()){
                    btnShowBetTypes.click();
                }
                break;
            case "Leagues":
                if (btnShowLeagues.isDisplayed()){
                    btnShowLeagues.click();
                }
                break;
            case "Group":
                if (btnShowGroup.isDisplayed()){
                    btnShowGroup.click();
                }
                break;
            case "Events":
                if (btnShowEvent.isDisplayed()){
                    btnShowEvent.click();
                }
                break;
            default:
                System.err.println("Input wrongly filter name");
                break;
        }
    }
    /**
     *
     * @param nameFilter input Bet Types, Leagues, Group, Event
     * @param name input type, league, group, event
     */
    public void filterAdvance(String nameFilter, String name){
        openFilterAdvance(nameFilter);
        waitSpinnerDisappeared();
        CheckBox cbName;
        if (nameFilter.equals("Events")){
            String nameHome = name.split(" -vs- ")[0];
            cbName = CheckBox.xpath(String.format("//span[text()='%s']/preceding::td/input",nameHome));
        } else {
            cbName = CheckBox.xpath(String.format("//div[contains(@class,'card-columns')]//span[text()='%s']//preceding::input[1]",name));
        }
        cbName.jsClick();
        btnSetSelection.click();
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public BetByTeamPricePopup clickPrice(String account){
        BetByGroupTableControl bbgTable = findAccount(account);
        bbgTable.clickPriceColumn(account);
        return new BetByTeamPricePopup();
    }
    public String getFristSmartGroupName() {
       return firstBetByGroupTableControl.getSmartGroupName();
    }
    public String  getFristSmartGroupCurrency() {
        return firstBetByGroupTableControl.getSmartGroupCurr();
    }

    public BetByTeamPricePopup clickFirstPriceCell(){
       firstBetByGroupTableControl.clickPriceColumn("");
       DriverManager.getDriver().switchToWindow();
       return new BetByTeamPricePopup();
    }
    public BBGLastDaysPerformacesPopup clickFirstTraderCell(){
        firstBetByGroupTableControl.clickTraderColumn("");
        DriverManager.getDriver().switchToWindow();
        return new BBGLastDaysPerformacesPopup();
    }
    private BetByGroupTableControl findAccount(String account){
        int i = 1;
        BetByGroupTableControl betByGroupTableControl;
        while (true){
            betByGroupTableControl = BetByGroupTableControl.xpath(String.format("%s[%d]", xPathBetByGroupTableControl, i));
            if(!betByGroupTableControl.isDisplayed())
                return null;
            if(betByGroupTableControl.getRowIndexContainAccount(account)!= 0)
                return betByGroupTableControl;
            i = i+1;
        }
    }
    public List<String> getFirstRowGroupData() {
        List <String> lstData = new ArrayList<>();
        if (!lblFirstAccount.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            Row firstGroupRow = Row.xpath("(//app-bbg//table//tbody//tr)[1]/td");
            for (int i = 0; i < firstGroupRow.getWebElements().size(); i++) {
                String xpathData = String.format("(//app-bbg//table//tbody//tr)[1]//td[%s]",i+1);
                Label lblDataCell = Label.xpath(xpathData);
                lstData.add(lblDataCell.getText());
            }
            return lstData;
        }
    }

    public boolean verifyAccBelongToKastraki(String firstAcc) {
        JSONObject jsonObject = AccountSearchUtils.getAccountInfoJson(firstAcc);
        if (Objects.nonNull(jsonObject)){
            return true;
        }
        return false;
    }

    /**
     *
     * @param stake input in stake dropdown except ALl
     */
    public void verifyAllBetsShowWithStake(String stake) {
        if (!lblNoRecord.isDisplayed()){
            double stakeEx = Double.valueOf(stake.replace("Above ","").replace("K","000"));
            Table table = Table.xpath(tableXpath,12);
            int indexCol = table.getColumnIndexByName("Stake");
            for (int i = 1;i <= table.getWebElements().size();i++){
                String groupName = Label.xpath(String.format("(//div[contains(@class,'header')]/div/div)[%d]",i)).getText().trim();
                Table tblData = Table.xpath(String.format("(%s)[%d]",tableXpath,i),12);
                tblData.scrollToThisControl(false);
                Row row = Row.xpath(String.format("(%s)[%d]//tbody//div//tr[contains(@class,'d-flex')]",tableXpath,i));
                for (int j = 1; j <= row.getWebElements().size();j++){
                    Label lblStake = Label.xpath(String.format("(%s)[%d]//tbody//div//tr[contains(@class,'d-flex')][%d]/td[%d]",tableXpath,i,j,indexCol));
                    if (Double.valueOf(lblStake.getText().trim().replace(",","")) < stakeEx){
                        Assert.assertTrue(false,"FAILED! "+lblStake.getText()+" is in "+groupName+" displays incorrect");
                    }
                }
            }
        }
    }

    /**
     *
     * @param nameFilter input Bet Types, Leagues, Group, Events
     * @return
     */
    public List<String> getLstNameInAdvanceFilter(String nameFilter){
        openFilterAdvance(nameFilter);
        List<String> lstEx = new ArrayList<>();
        Label lblFilter = Label.xpath(String.format("//div[contains(@class,'list-bet-types')]//div[contains(text(),'%s')]",nameFilter));
        lblFilter.waitForElementToBePresent(lblFilter.getLocator());
        Label lstName;
        if (nameFilter.equals("Events")){
            lstName = Label.xpath(String.format("//div[contains(@class,'list-bet-types')]//table/tbody/tr",nameFilter));
            for (int i = 1;i <= lstName.getWebElements().size();i++){
                String home = Label.xpath(String.format("((//div[contains(@class,'list-bet-types')]//table/tbody/tr)[%d]//span)[1]",i)).getText().trim();
                String away = Label.xpath(String.format("((//div[contains(@class,'list-bet-types')]//table/tbody/tr)[%d]//span)[3]",i)).getText().trim();
                lstEx.add(home+" -vs- "+away);
            }
        } else {
            lstName = Label.xpath(String.format("//div[contains(text(),'%s')]/following-sibling::div//table//tbody/tr",nameFilter));
            for (int i = 1;i <= lstName.getWebElements().size();i++){
                lstEx.add(Label.xpath(String.format("(//div[contains(text(),'%s')]/following-sibling::div//table//tbody/tr)[%d]",nameFilter,i)).getText().trim());
            }
        }
        return lstEx;
    }

    /**
     *
     * @param colName input Bet Types, Event
     * @param lstBetsType
     */
    public void verifyBetsShowCorrectByColumnName(String colName, List<String> lstBetsType) {
        Table table = Table.xpath(tableXpath,12);
        int indexCol = table.getColumnIndexByName(colName);
        for (int i = 1;i <= table.getWebElements().size();i++){
            String groupName = Label.xpath(String.format("(//div[contains(@class,'header')]/div/div)[%d]",i)).getText().trim();
            Table tblData = Table.xpath(String.format("(%s)[%d]",tableXpath,i),12);
            tblData.scrollToThisControl(false);
            Row row = Row.xpath(String.format("(%s)[%d]//tbody//div//tr[contains(@class,'d-flex')]",tableXpath,i));
            for (int j = 1; j <= row.getWebElements().size();j++){
                Label lblRow;
                switch (colName){
                    case "Event":
                        lblRow = Label.xpath(String.format("(%s)[%d]//tbody//div//tr[contains(@class,'d-flex')][%d]/td[%d]//tbody/tr",tableXpath,i,j,indexCol));
                        break;
                    default:
                        lblRow = Label.xpath(String.format("(%s)[%d]//tbody//div//tr[contains(@class,'d-flex')][%d]/td[%d]",tableXpath,i,j,indexCol));
                        break;
                }
                if (!lstBetsType.contains(lblRow.getText().trim())){
                    Assert.assertTrue(false,"FAILED! "+lblRow+" is in "+groupName+" display incorrect.");
                }
            }
        }
    }

    public boolean isOrderDisplayCorrect(Order order, String groupName) {
        String tblXpath = "//div[contains(@class,'header')]/div/div[contains(text(),'%s')]//following::table[%d]";
        Table tblData = Table.xpath(String.format(tblXpath,groupName,1),12);
        int indexAccountCol = tblData.getColumnIndexByName("Account");
        int indexBetTypeCol = tblData.getColumnIndexByName("Bet Type");
        int indexHPDCol = tblData.getColumnIndexByName("HDP");
        int indexPriceCol = tblData.getColumnIndexByName("Price");
        int indexStakeCol = tblData.getColumnIndexByName("Stake");
        int indexSelectionCol = tblData.getColumnIndexByName("Selection");
        Row row = Row.xpath(String.format("%s//tbody//div//tr[contains(@class,'d-flex')]",String.format(tblXpath,groupName,1)));
        int numberRow = row.getWebElements().size();
        for (int i = 1; i <= numberRow;i++){
            String lblAccountCode = Label.xpath(String.format("%s//tbody//div//tr[contains(@class,'d-flex')][%d]//td[%d]",String.format(tblXpath,groupName,1),i,indexAccountCol)).getText().trim();
            String lblSelection = Label.xpath(String.format("%s//tbody//div//tr[contains(@class,'d-flex')][%d]//td[%d]",String.format(tblXpath,groupName,1),i,indexSelectionCol)).getText().trim();
            if (lblAccountCode.equals(order.getAccountCode()) && lblSelection.equals(order.getSelection())){
                String lblBetType = Label.xpath(String.format("%s//tbody//div//tr[contains(@class,'d-flex')][%d]//td[%d]",String.format(tblXpath,groupName,1),i,indexBetTypeCol)).getText().trim();
                String lblHdp = Label.xpath(String.format("%s//tbody//div//tr[contains(@class,'d-flex')][%d]//td[%d]",String.format(tblXpath,groupName,1),i,indexHPDCol)).getText().trim();
                String lblPrice = Label.xpath(String.format("%s//tbody//div//tr[contains(@class,'d-flex')][%d]//td[%d]",String.format(tblXpath,groupName,1),i,indexPriceCol)).getText().trim();
                String lblStake = Label.xpath(String.format("%s//tbody//div//tr[contains(@class,'d-flex')][%d]//td[%d]",String.format(tblXpath,groupName,1),i,indexStakeCol)).getText().trim();
                if (lblBetType.equals(order.getMarketType()) && lblHdp.contains(String.valueOf(order.getHandicap())) && lblPrice.contains(String.format("%.3f",order.getPrice())) && lblStake.equals(String.format("%.2f",order.getRequireStake()))){
                    return true;
                }
            }
        }
        System.err.println("FAILED! Order is not displayed");
        return false;
    }
    public void verifyUI(){
        System.out.println("Company Unit, Report By, Punter Type, Sport, From Date, To Date and Show button");
        Assert.assertEquals(ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        List<String> lstCompany = CompanySetUpUtils.getListCompany();
        lstCompany.add(0,"All");
        Assert.assertEquals(ddpCompanyUnit.getOptions(),lstCompany,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(ddpSmartType.getOptions(), SBPConstants.BBGPage.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(ddpReportType.getOptions(), SBPConstants.BBGPage.REPORT_TYPE_LIST,"Failed! Report Type dropdown is not displayed");
        Assert.assertEquals(ddpWinLose.getOptions(), SBPConstants.BBGPage.WIN_LOSE_TYPE_LIST,"Failed! Win/Lose dropdown is not displayed");
        Assert.assertEquals(lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        System.out.println("Show Tax Amount, Show Bet Types, Show Leagues, Smart Group, Order By Win%, Reset All Filters and More Filters");
        Assert.assertTrue(btnShowBetTypes.getText().contains("Show Bet Types"),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(btnShowLeagues.getText().contains("Show Leagues"),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(btnShowGroup.getText().contains("Show Groups"),"Failed! Show Group button is not displayed");
        Assert.assertEquals(btnResetAllFilter.getText(),"Reset All Filters","Failed! Reset button is not displayed");
        Assert.assertTrue(btnShowEvent.getText().contains("Show Events"),"Failed! Show Events button is not displayed");
        Assert.assertEquals(btnShow.getText(),"SHOW","Failed! Show button is not displayed");
    }

    public void verifyDefaultFilter() {
        Assert.assertEquals(ddpCompanyUnit.getFirstSelectedOption(),"All");
        Assert.assertEquals(ddpSmartType.getFirstSelectedOption(),"Group");
        Assert.assertEquals(ddpReportType.getFirstSelectedOption(),"Pending Bets");
        Assert.assertEquals(ddpStake.getFirstSelectedOption(),"All");
        Assert.assertEquals(ddpCurrency.getFirstSelectedOption(),"All");
        String date = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        Assert.assertEquals(txtFromDate.getAttribute("value"),date);
        Assert.assertEquals(txtToDate.getAttribute("value"),date);
    }

    public void verifySelectedGroupDisplay(String groupEx) {
        Assert.assertTrue(Label.xpath("//app-bets-by-group-table//div[contains(@class,'header')]/div/div").getText().trim().contains(groupEx),"FAILED! "+groupEx+" group is not displayed");
    }
    public void goToGroupType(String groupTypeName){
        ddpGroupType.selectByVisibleText(groupTypeName);
        waitSpinnerDisappeared();
    }
    public boolean isBetDisplay(Order order, String groupName, boolean isSmartGroup){
        int colNumber = isSmartGroup ? 13 : 10;
        String tableXpath = String.format(tblStakeSizeGroupXpath,groupName);
        Table tblData = Table.xpath(tableXpath,colNumber);
        int rowNum = tblData.getNumberOfRows(false,true);
        for (int i = 1; i <= rowNum; i++){
            String accountCode = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Account"),i,null).getText().trim();
            if (accountCode.equals(order.getAccountCode())){
                String betTypeAc = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Bet Type"),i,null).getText().trim();
                String selectionAc = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Selection"),i,null).getText().trim();
                String handicapAc = tblData.getControlOfCell(1,tblData.getColumnIndexByName("HDP"),i,"span").getText().trim();
                String priceAc = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Price"),i,null).getText().trim();
                String stakeAc = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Stake"),i,null).getText().trim();
                if (betTypeAc.equals(order.getBetType())
                    && selectionAc.equals(order.getSelection())
                    && handicapAc.equals(String.format("%.2f",order.getHandicap()))
                    && priceAc.equals(String.format("%.3f",order.getPrice(),order.getOddType()))
                    && stakeAc.equals(String.format("%.2f",order.getRequireStake()))){
                    return true;
                }
            }
        }
        System.out.println("FAILED! Bet is not displayed");
        return false;
    }
}
