package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.BaseElement;
import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Row;
import controls.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import java.util.ArrayList;
import java.util.List;

public class BBGPhoneBettingPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpReportBy = DropDownBox.xpath("//div[contains(text(),'Report By')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.xpath("//div[contains(text(),'To Date')]/following::input");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");

    public Button btnShowBetTypes = Button.xpath("//button[contains(text(),' Show  Bet Types ')]");
    public Button btnShowLeagues = Button.xpath("//button[contains(text(),' Show  Leagues ')]");
    public Button btnHideLeagues = Button.xpath("//button[contains(text(),' Hide  Leagues ')]");
    public Button btnShowWinLose = Button.xpath("//button[contains(text(),' Show  Win/Lose ')]");
    public Button btnShow = Button.xpath("(//button[contains(text(),'Show')])[4]");
    public Table tblOrder = Table.xpath("//app-phone-betting//table",12);
    Button btnClearAll = Button.xpath("//span[contains(text(),'Clear All')]");
    Button btnSelectAll = Button.xpath("//span[contains(text(),'Select All')]");
    Button btnSetSelection = Button.xpath("//button[contains(text(),'Set Selection')]");
    String lblFormXpath = "//div[contains(@class,'header2')]//span[text()='%s']";
    String cbTypeXpath = "//label[contains(text(),'%s')]/input";
    String tableNameXpath = "(//div[contains(@class,'header2')])[%d]";

    public void filter(String companyUnit, String reportBy, String fromdate, String toDate, String betType, String league, String winlose){
        if (!companyUnit.isEmpty()){
            ddpCompanyUnit.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
        }
        if (!reportBy.isEmpty()){
            ddpReportBy.selectByVisibleText(reportBy);
            waitSpinnerDisappeared();
        }
        if (!fromdate.isEmpty()){
            dtpFromDate.selectDate(fromdate,"dd/MM/yyyy");
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
        if (!betType.isEmpty()){
            showBetType(betType);
            btnShow.click();
            waitSpinnerDisappeared();
        }
        if (!league.isEmpty()){
            showLeague(league);
            btnShow.click();
            waitSpinnerDisappeared();
        }
        if (!winlose.isEmpty()){
            showWinLose(winlose);
            btnShow.click();
            waitSpinnerDisappeared();
        }
    }

    private void showWinLose(String winlose) {
        btnShowWinLose.click();
        Label lblWinLoseForm = Label.xpath(String.format(lblFormXpath,"Win/Lose"));
        lblWinLoseForm.waitForElementToBePresent(lblWinLoseForm.getLocator());
        btnClearAll.click();
        CheckBox cbWinLose = CheckBox.xpath(String.format(cbTypeXpath,winlose));
        cbWinLose.click();
        btnSetSelection.click();
        waitSpinnerDisappeared();
    }

    private void showLeague(String league) {
        btnShowLeagues.click();
        Label lblLeagueForm = Label.xpath(String.format(lblFormXpath,"Leagues"));
        lblLeagueForm.waitForElementToBePresent(lblLeagueForm.getLocator());
        btnClearAll.click();
        CheckBox cbLeague = CheckBox.xpath(String.format(cbTypeXpath,league));
        cbLeague.scrollToThisControl(false);
        cbLeague.click();
        btnSetSelection.scrollToThisControl(false);
        btnSetSelection.click();
        waitSpinnerDisappeared();
    }

    private void showBetType(String betType) {
        btnShowBetTypes.click();
        Label lblBetTypeForm = Label.xpath(String.format(lblFormXpath,"Bet Types"));
        lblBetTypeForm.waitForElementToBePresent(lblBetTypeForm.getLocator());
        btnClearAll.click();
        CheckBox cbBetType = CheckBox.xpath(String.format(cbTypeXpath,betType));
        cbBetType.click();
        btnSetSelection.click();
        waitSpinnerDisappeared();
    }

    public void verifyShowBetTypeCorrect(String betType) {
        Table lstTable = Table.xpath("//table",12);
        for (int i = 1; i <= lstTable.getWebElements().size();i++){
            Table table = Table.xpath(String.format("(//table)[%d]",i),12);
            int numberRow = table.getNumberOfRows(false,true);
            String tableName = Label.xpath(String.format(tableNameXpath,i)).getText();
            for (int j = 1; j < numberRow;j++){
                String betTypeAc = table.getControlOfCell(1,table.getColumnIndexByName("Bet Type"),j,"span").getText().trim();
                Assert.assertEquals(betTypeAc,betType,"FAILED! Bet Type is row "+j+ " in "+tableName+" display incorrect");
            }
        }
    }
    public List<String> getLstLeague(){
        btnShowLeagues.click();
        Label lblLeagueForm = Label.xpath(String.format(lblFormXpath,"Leagues"));
        lblLeagueForm.waitForElementToBePresent(lblLeagueForm.getLocator());
        List<String> lstEx = new ArrayList<>();
        Label lblLeague = Label.xpath("//label");
        for (int i = 1; i <= lblLeague.getWebElements().size();i++){
            lstEx.add(Label.xpath(String.format("(//label)[%d]",i)).getText().trim());
        }
        btnHideLeagues.click();
        waitSpinnerDisappeared();
        return lstEx;
    }

    public void verifyShowLeagueNameCorrect(String leagueName) {
        Label lblLeague = Label.xpath("//div[contains(@class,'header2')]");
        for (int i = 1; i <= lblLeague.getWebElements().size();i++){
            String tableNameAc = Label.xpath(String.format(tableNameXpath,i)).getText().split("\n")[1];
            Assert.assertEquals(tableNameAc,leagueName,"FAILED! "+tableNameAc+" display incorrect");
        }
    }

    /**
     *
     * @param winLoseDraw input param: "Win","Lose","Draw"
     */
    public void verifyShowBetWinLoseCorrect(String winLoseDraw) {
        Table lstTable = Table.xpath("//table",12);
        for (int i = 1; i <= lstTable.getWebElements().size();i++){
            Table table = Table.xpath(String.format("(//table)[%d]",i),12);
            int numberRow = table.getNumberOfRows(false,true);
            String tableName = Label.xpath(String.format(tableNameXpath,i)).getText();
            for (int j = 1; j < numberRow;j++){
                String betTypeAc = table.getControlOfCell(1,table.getColumnIndexByName("Win/Lose"),j,"span").getText().trim().replace(",","");
                switch (winLoseDraw){
                    case "Win":
                        if (!(Double.valueOf(betTypeAc) > 0)){
                            Assert.assertFalse(true,"FAILED! Result is "+betTypeAc+ " in table "+tableName + " displays incorrect");
                        }
                        break;
                    case "Lose":
                        if (Double.valueOf(betTypeAc) > 0){
                            Assert.assertFalse(true,"FAILED! Result is "+betTypeAc+ " in table "+tableName + " displays incorrect");
                        }
                        break;
                    case "Draw":
                        if (!(Double.valueOf(betTypeAc) == 0.00)){
                            Assert.assertFalse(true,"FAILED! Result is "+betTypeAc+ " in table "+tableName + " displays incorrect");
                        }
                        break;
                    default:
                        System.err.println("Input wrongly parameter!");
                }
            }
        }
    }
}
