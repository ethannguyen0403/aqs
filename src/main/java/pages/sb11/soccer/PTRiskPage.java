package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import objects.Order;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.popup.PTRiskBetListPopup;

import java.util.LinkedHashMap;
import java.util.Map;

public class PTRiskPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span");
    public DropDownBox ddpCompanyUnit = DropDownBox.id("typeSelected");
    public DropDownBox ddpReportType = DropDownBox.id("typeSelected1");
    public DropDownBox ddpLiveNonLive = DropDownBox.id("betSelected1");
    public TextBox txtFromDate = TextBox.xpath("//div[contains(text(),'From Date')]/following-sibling::div//input");
    public TextBox txtToDate = TextBox.xpath("//div[contains(text(),'To Date')]/following-sibling::div//input");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate, "//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate, "//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");

    public DropDownBox ddpOrderBy = DropDownBox.id("typeSelected3");
    public DropDownBox ddpSport = DropDownBox.id("sportSelect");
    public Button btnBetTypes = Button.xpath("//app-pt-risk-control//button[text()=' Bet Types ']");
    public Button btnClient = Button.xpath("//app-pt-risk-control//button[text()=' Clients ']");
    public Button btnLeagues = Button.xpath("//app-pt-risk-control//button[text()=' Leagues ']");
    public Button btnEvents = Button.xpath("//app-pt-risk-control//button[text()=' Events ']");
    public Button btnSmartMaster = Button.xpath("//app-pt-risk-control//button[text()=' Smart Master ']");
    public Button btnSmartAgent = Button.xpath("//app-pt-risk-control//button[text()=' Smart Agent ']");
    public Button btnClearAll = Button.xpath("//div[@class='modal-content']//div[@class='group-btn']//span[text()='Clear All']");
    public Button btnSetSelection = Button.xpath("//div[@class='modal-content']//div[@class='group-btn']//button[text()=' Set Selection ']");
    public Label lblFilterList = Label.xpath("//div[@class='modal-content']//div[@class='list-item-filter']//label[%s]");
    public Button btnShow = Button.xpath("//app-pt-risk-control//button[text()='SHOW']");
    public Button btnCopy = Button.xpath("//app-pt-risk-control//button[text()='Copy Report ']");
    public Label messageSuccess = Label.xpath("(//div[contains(@class, 'message-box')]//span)[3]");
    public Label lblNoRecord = Label.xpath("//span[contains(@class,'no-record')]");

    @Override
    public String getTitlePage() {
        return this.lblTitle.getText().trim();
    }

    public void filter(String clientCode, String companyUnit, String sport, String reportType, String liveNonlive, String fromDate, String toDate, String leagueName) {
        if (!companyUnit.isEmpty()) {
            ddpCompanyUnit.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
        }
        if (!sport.isEmpty()) {
            ddpSport.selectByVisibleText(sport);
            waitSpinnerDisappeared();
        }
        if (!reportType.isEmpty()) {
            ddpReportType.selectByVisibleText(reportType);
            waitSpinnerDisappeared();
        }
        if (!liveNonlive.isEmpty()) {
            ddpLiveNonLive.selectByVisibleText(liveNonlive);
            waitSpinnerDisappeared();
        }
        if (!fromDate.isEmpty()) {
            dtpFromDate.waitForControlInvisible();
            dtpFromDate.selectDate(fromDate, "dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        if (!toDate.isEmpty()) {
            dtpToDate.waitForControlInvisible();
            dtpToDate.selectDate(toDate, "dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        if (!leagueName.isEmpty()) {
            checkLeagueDisplay(companyUnit, sport, reportType, fromDate, toDate, leagueName);
            filterLeague(leagueName);
        }
        if (!clientCode.isEmpty()) {
            btnClient.click();
            btnClearAll.click();
            filterClient(clientCode);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    private void checkLeagueDisplay(String companyUnit, String sport, String reportType, String fromDate, String toDate, String leagueName) {
        int i = 1;
        while (i < 10) {
            btnLeagues.click();
            waitSpinnerDisappeared();
            btnClearAll.waitForControlInvisible();
            waitSpinnerDisappeared();
            btnClearAll.click();
            Label lblSelectValue = Label.xpath(String.format("//div[@class='modal-content']//div[@class='list-item-filter']//div//label[contains(text(),\"%s\")]", leagueName));
            if (!lblSelectValue.isDisplayed()) {
                DriverManager.getDriver().refresh();
                waitSpinnerDisappeared();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                filter("", companyUnit, sport, reportType, "", fromDate, toDate, "");
                i = i + 1;
                continue;
            }
            break;
        }
    }

    private void filterClient(String clientCode) {
        if (clientCode.isEmpty())
            return;
        int i = 1;
        String out[] = clientCode.split("-");
        clientCode = out[1].replace(" ", "");
        while (true) {
            Label lblSelectValue = Label.xpath(String.format("//div[@class='modal-content']//div[@class='list-item-filter']//div[%s]//label[1]", i));
            if (!lblSelectValue.isDisplayed()) {
                System.out.println("Cannot find out client in list of Client: " + clientCode);
                break;
            }
            if (lblSelectValue.getText().replace(" ", "").equalsIgnoreCase(clientCode)) {
                lblSelectValue.click();
                btnSetSelection.click();
                waitSpinnerDisappeared();
                break;
            }
            i = i + 1;
        }
    }

    private void filterLeague(String leagueName) {
        btnLeagues.click();
        waitSpinnerDisappeared();
        btnClearAll.waitForControlInvisible();
        waitSpinnerDisappeared();
        btnClearAll.click();
        Label lblSelectValue = Label.xpath(String.format("//div[@class='modal-content']//div[@class='list-item-filter']//div//label[contains(text(),\"%s\")]", leagueName));
        if (!lblSelectValue.isDisplayed()) {
            System.out.println(String.format("%s still not display", leagueName));
        }
        if (lblSelectValue.getText().equalsIgnoreCase(leagueName)) {
            lblSelectValue.click();
            btnSetSelection.click();
            waitSpinnerDisappeared();
        }
    }

    public void filterSmartMaster(String masterName) {
        btnSmartMaster.click();
        waitSpinnerDisappeared();
        btnClearAll.click();
        int i = 1;
        while (true) {
            Label lblSelectValue = Label.xpath(String.format("//div[@class='modal-content']//div[@class='list-item-filter']//div[%s]//label[1]", i));
            if (!lblSelectValue.isDisplayed()) {
                System.out.println("Cannot find out league in list of Leagues: " + masterName);
                break;
            }
            if (lblSelectValue.getText().equalsIgnoreCase(masterName)) {
                lblSelectValue.click();
                btnSetSelection.click();
                break;
            }
            i = i + 1;
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public PTRiskBetListPopup openBetList(String homeName) {
        Label homeNameXpath = Label.xpath(String.format("//app-pt-risk-control//th[@id='team-infor']//div[text()='%s']", homeName));
        homeNameXpath.click();
        waitSpinnerDisappeared();
        return new PTRiskBetListPopup();
    }


    public boolean isForecastCorrect(String fullWinVal, String haflWinVal, String fullLoseVal, boolean isHDP) {
        boolean forecastCorrect = false;
        int rowTotalScoreCol = 13;
        if (isHDP) {
            int rowHDPIndex = 2;
            for (int i = 1; i < rowTotalScoreCol; i++) {
                Label cellValueXpath = Label.xpath(String.format("//app-pt-risk-control//th[@id='team-infor']//..//..//tr[%s]//td[%s]//span", rowHDPIndex, i));
                String cellValue = cellValueXpath.getText();
                try {
                    if (i >= 1 && i <= 8) {
                        Assert.assertEquals(fullWinVal, cellValue);
                        forecastCorrect = true;
                    }
                    if (i == 9) {
                        Assert.assertEquals(haflWinVal, cellValue);
                        forecastCorrect = true;
                    }
                    if (i > 9) {
                        Assert.assertEquals(fullLoseVal, cellValue);
                        forecastCorrect = true;
                    }
                } catch (Exception e) {
                    forecastCorrect = false;
                }
            }
        } else {
            //TODO handle for type Over/Under
        }
        return forecastCorrect;
    }

    public boolean isBetTypeBasketIs1X2() {
        btnBetTypes.click();
        waitSpinnerDisappeared();
        Label lblValueList = Label.xpath("//div[@class='modal-content']//div[@class='list-item-filter']//div//label");
        boolean is1Item = lblValueList.getWebElements().size() == 1 ? true : false;
        int index = 1;
        while (is1Item) {
            Label lblValue = Label.xpath(String.format("//div[@class='modal-content']//div[@class='list-item-filter']//div//label[%s]", index));
            if (lblValue.isDisplayed()) {
                return lblValue.getText().equalsIgnoreCase("1x2") ? true : false;
            }
            if (!lblValue.isDisplayed()) {
                System.out.println("NOT Found item");
                return false;
            }
        }
        return is1Item;
    }

    public Map<String, String> getEntriesValueOfTableSport(String leagueName, String teamName, String indexHeaderRow, String indexValueRow) {
        String tableXpath = getXpathPTSportLeagueTable(leagueName, teamName);
        Map<String, String> entries = new LinkedHashMap<>();
        int i = 1;
        while (true) {
            Label primaryCell = Label.xpath(tableXpath + String.format("/tr[%s]//td[%s]", indexHeaderRow, i));
            Label valueCell = Label.xpath(tableXpath + String.format("/tr[%s]//td[%s]", indexValueRow, i));
            if (primaryCell.isDisplayed() && valueCell.isDisplayed()) {
                entries.put(primaryCell.getText().trim(), valueCell.getText().trim());
                i++;
            }
            if (!primaryCell.isDisplayed() || !valueCell.isDisplayed()) {
                System.out.println("NOT Found the value return Map entry");
                return entries;
            }
        }
    }

    public String getXpathPTSportLeagueTable(String leagueName, String teamName) {
        int tableLeagueIndex = findTableSportIndex(leagueName);
        int indexTeamTable = 1;
        while (true) {
            Label lblCellValue = Label.xpath(String.format("//div[@class='bet-list']/table[%s]//table[%s]//th", tableLeagueIndex, indexTeamTable));
            if (lblCellValue.getText().contains(teamName)) {
                System.out.println("Found table of League Name: " + leagueName + ", team Name: " + teamName);
                break;
            }
            indexTeamTable++;
            if (!lblCellValue.isDisplayed()) {
                System.out.println("NOT Found table of League Name: " + leagueName + ", team Name: " + teamName);
                indexTeamTable = -1;
                break;
            }
        }
        return String.format("//div[@class='bet-list']/table[%s]//table[%s]", indexTeamTable, tableLeagueIndex);
    }


    public int findTableSportIndex(String leagueName) {
        int index = 1;
        while (true) {
            Label lblCellName = Label.xpath(String.format("//div[@class='bet-list']/table[%s]/tr", index));
            if (lblCellName.getText().equalsIgnoreCase(leagueName)) {
                System.out.println("Found table of League Name: " + leagueName);
                return index;
            }
            index++;
            if (!lblCellName.isDisplayed()) {
                System.out.println("NOT Found table of League Name: " + leagueName);
                return -1;
            }
        }
    }

    public boolean verifyRemoveBet(Order order) {
        Label homeNameXpath = Label.xpath(String.format("//app-pt-risk-control//th[@id='team-infor']//div[text()=\"%s\"]", order.getEvent().getHome()));
        if (!homeNameXpath.isDisplayed()) {
            return true;
        } else {
            PTRiskBetListPopup ptRiskBetListPopup = openBetList(order.getEvent().getHome());
            if (!ptRiskBetListPopup.verifyOrder(order)) {
                return true;
            }
        }
        return false;
    }

    public void verifyUI() {
        System.out.println("Company Unit, Report By, Live/NonLive, From Date, To Date, Order By, Bet Types, Leagues, Events, Clients, Smart Master, Smart Agent, Show button and Copy Report");
        Assert.assertTrue(ddpCompanyUnit.isDisplayed(), "Company Unit dropdown box is not displayed!");
        Assert.assertTrue(ddpReportType.isDisplayed(), "Report Type dropdown box is not displayed!");
        Assert.assertTrue(ddpLiveNonLive.isDisplayed(), "Live/Non Live dropdown box is not displayed!");
        Assert.assertTrue(txtFromDate.isDisplayed(), "From Date is not displayed!");
        Assert.assertTrue(txtToDate.isDisplayed(), "To Date is not displayed!");
        Assert.assertTrue(ddpOrderBy.isDisplayed(), "Live/Non Live dropdown box is not displayed!");
        Assert.assertTrue(btnBetTypes.isDisplayed(), "Bet Types dropdown is not displayed!");
        Assert.assertTrue(btnLeagues.isDisplayed(), "Leagues dropdown is not displayed!");
        Assert.assertTrue(btnEvents.isDisplayed(), "Events dropdown is not displayed!");
        Assert.assertTrue(btnClient.isDisplayed(), "Clients dropdown is not displayed!");
        Assert.assertTrue(btnSmartMaster.isDisplayed(), "Smart Master dropdown is not displayed!");
        Assert.assertTrue(btnSmartAgent.isDisplayed(), "Smart Agent dropdown is not displayed!");
    }
}
