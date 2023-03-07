package pages.sb11.soccer;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.popup.PTRiskBetListPopup;

public class PTRiskPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    DropDownBox ddpCompanyUnit = DropDownBox.id("typeSelected");
    DropDownBox ddpReportType = DropDownBox.id("typeSelected1");
    DropDownBox ddpLiveNonLive = DropDownBox.id("betSelected1");
    TextBox txtFromDate = TextBox.name("fromDate");
    TextBox txtToDate = TextBox.name("toDate");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");

    DropDownBox ddpOrderBy = DropDownBox.id("typeSelected3");
    Button btnClient = Button.xpath("//app-pt-risk-control//div[@class='row pl-4']//button[text()=' Clients ']");
    Button btnLeagues = Button.xpath("//app-pt-risk-control//div[@class='row pl-4']//button[text()=' Leagues ']");
    Button btnClearAll = Button.xpath("//div[@class='modal-content']//div[@class='group-btn']//span[text()='Clear All']");
    Button btnSetSelection = Button.xpath("//div[@class='modal-content']//div[@class='group-btn']//button[text()=' Set Selection ']");
    Label lblFilterList = Label.xpath("//div[@class='modal-content']//div[@class='list-item-filter']//label[%s]");
    Button btnShow = Button.xpath("//app-pt-risk-control//button[text()='Show']");
    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public void filter(String clientCode, String companyUnit, String reportType, String liveNonlive, String fromDate, String toDate, String leagueName) throws InterruptedException {
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpReportType.selectByVisibleText(reportType);
        waitSpinnerDisappeared();
        ddpLiveNonLive.selectByVisibleText(liveNonlive);
        if(!fromDate.isEmpty())
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        if(!toDate.isEmpty())
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        btnLeagues.click();
        btnClearAll.click();
        filterLeague(leagueName);
        btnClient.click();
        btnClearAll.click();
        filterClient(clientCode);
        btnShow.click();
        waitSpinnerDisappeared();
    }

    private void filterClient(String clientCode) {
        int i = 1;
        String out[] = clientCode.split("-");
        clientCode = out[1].replace(" ","");
        while (true) {
            Label lblSelectValue = Label.xpath(String.format("//div[@class='modal-content']//div[@class='list-item-filter']//div[%s]//label[1]",i));
            if (!lblSelectValue.isDisplayed()) {
                System.out.println("Cannot find out client in list of Client: " + clientCode);
                break;
            }
            if(lblSelectValue.getText().replace(" ","").equalsIgnoreCase(clientCode)) {
                lblSelectValue.click();
                btnSetSelection.click();
                break;
            }
            i = i + 1;
        }
    }

    private void filterLeague(String leagueName) {
        int i = 1;
        while (true) {
            Label lblSelectValue = Label.xpath(String.format("//div[@class='modal-content']//div[@class='list-item-filter']//div[%s]//label[1]",i));
            if (!lblSelectValue.isDisplayed()) {
                System.out.println("Cannot find out league in list of Leagues: " + leagueName);
                break;
            }
            if(lblSelectValue.getText().equalsIgnoreCase(leagueName)) {
                lblSelectValue.click();
                btnSetSelection.click();
                break;
            }
            i = i + 1;
        }
    }

    public PTRiskBetListPopup openBetList(String homeName) throws InterruptedException {
        Label homeNameXpath = Label.xpath(String.format("//app-pt-risk-control//th[@id='team-infor']//div[text()='%s']",homeName));
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
                Label cellValueXpath = Label.xpath(String.format("//app-pt-risk-control//th[@id='team-infor']//..//..//tr[%s]//td[%s]//span",rowHDPIndex,i));
                String cellValue = cellValueXpath.getText();
                try {
                    if (i >= 1 && i <= 8) {
                        Assert.assertEquals(fullWinVal,cellValue);
                        forecastCorrect = true;
                    }
                    if (i == 9){
                        Assert.assertEquals(haflWinVal,cellValue);
                        forecastCorrect = true;
                    }
                    if (i > 9) {
                        Assert.assertEquals(fullLoseVal,cellValue);
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
}
