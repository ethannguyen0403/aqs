package pages.sb11.soccer.popup;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import controls.Row;
import controls.Table;
import objects.Order;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.PTRiskPage;

import java.util.ArrayList;
import java.util.List;

public class PTRiskBetListPopup extends WelcomePage {
    private int totalCol = 12;
    private int colOderID = 1;
    private int colAccountCode = 3;
    public int colPTPercent = 4;
    public int colHDP = 7;
    public int colPrice = 9;
    public int colStake = 10;
    public int colCUR = 11;
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    Button btnClose = Button.xpath("//div[@class='pt-risk-modal']//span[contains(@class,'close-icon')]");
    Table tblBetList = Table.xpath("//tab[contains(@class,'active')]//table[@aria-label='group table']",totalCol);
    String lblTabxPath = "//div[@class='pt-risk-modal']//span[text()='%s']";
    Label lblHandicap = Label.xpath("//div[@class='pt-risk-modal']//span[text()='Handicap']");
    Label lblOverUnder = Label.xpath("//div[@class='pt-risk-modal']//span[text()='Over Under']");
    Label lblHalftime = Label.xpath("//div[@class='pt-risk-modal']//span[text()='Half-time']");
    String rowHandicapXpath = "//tab[contains(@class,'active')]//table[@aria-label='group table']//tbody/tr";
    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public void activeTab(String tabName){
        Label.xpath(String.format(lblTabxPath,tabName)).click();
    }

    public String getBetListCellValue(String accountCode, int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        Label lblAccountCode;
        int i = 1;
        while (true){
            lblCellValue = Label.xpath(tblBetList.getxPathOfCell(1,colIndex,i,null));
            lblAccountCode = Label.xpath(tblBetList.getxPathOfCell(1,colAccountCode,i,"span"));
            if(!lblCellValue.isDisplayed()){
                System.out.println("There's no value display in the Bet List table");
                return null;
            }
            if(lblAccountCode.getText().equalsIgnoreCase(accountCode)){
                returnValue = lblCellValue.getText();
                return returnValue;
            }
            i = i+1;
        }
    }

    public boolean verifyOrder(Order order){
        List<String> lstAccountCode = tblBetList.getColumn(colAccountCode,100,true);
        for (int i = 0; i < lstAccountCode.size();i++){
            String betID = tblBetList.getControlOfCell(1,colOderID,i+1,"div").getText();
            if (lstAccountCode.get(i).equals(order.getAccountCode()) && betID.equals(order.getBetId())){
                Assert.assertTrue(tblBetList.getControlOfCell(1,colHDP,i+1,"span").getText().contains(Double.toString(order.getHdpPoint())),
                        "FAILED! HDP is incorrect!");
                Assert.assertTrue(tblBetList.getControlOfCell(1,colPrice,i+1,"span").getText().contains(Double.toString(order.getPrice())),
                        "FAILED! Price is incorrect!");
                Assert.assertTrue(tblBetList.getControlOfCell(1,colStake,i+1,"span").getText().contains(Double.toString(order.getRequireStake())),
                        "FAILED! Stake is incorrect!");
                Assert.assertTrue(tblBetList.getControlOfCell(1,colCUR,i+1,"span").getText().contains(order.getAccountCurrency()),
                        "FAILED! Currency is incorrect!");
                return true;
            }
        }
        System.err.println("Bet ID do not display");
        return false;
    }
    public boolean verifyOrderHafttime(Order order){
        List<WebElement> lstRow = DriverManager.getDriver().findElements(By.xpath(rowHandicapXpath));
        for (int i = 0; i < lstRow.size();i++){
            if (lstRow.get(i).getText().contains(order.getBetId())){
                Assert.assertTrue(lstRow.get(i).getText().contains(order.getAccountCode()),
                        "FAILED! Account Code is incorrect!");
                Assert.assertTrue(lstRow.get(i).getText().contains(Double.toString(order.getHdpPoint())),
                        "FAILED! HDP is incorrect!");
                Assert.assertTrue(lstRow.get(i).getText().contains(Double.toString(order.getPrice())),
                        "FAILED! Price is incorrect!");
                Assert.assertTrue(lstRow.get(i).getText().contains(Double.toString(order.getRequireStake())),
                        "FAILED! Stake is incorrect!");
                Assert.assertTrue(lstRow.get(i).getText().contains(order.getAccountCurrency()),
                        "FAILED! Currency is incorrect!");
                return true;
            }
        }
        return false;
    }

    public PTRiskPage closeBetListPopup() {
        btnClose.click();
        return new PTRiskPage();
    }


}
