package pages.sb11.generalReports.popup.winlossDetail;

import com.paltech.element.common.Label;
import controls.Table;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemberTransactionsPopup {
    public Label lblTitle = Label.xpath("//app-transaction-detail//div[contains(@class,'card-header')]//div[1]");
    String tabNameXpath = "//app-transaction-detail//tabset//span";
    Table tblData = Table.xpath("//app-transaction-detail//table",34);

    public void verifyBetsDisplay() {
        Assert.assertFalse(tblData.getNumberOfRows(false,true) == 0,"FAILED! Bets is not displayed");
    }

    public void verifyTabNameSort() {
        List<String> lstTabName = new ArrayList<>();
        Label tabName = Label.xpath(tabNameXpath);
        for (int i = 1; i <= tabName.getWebElements().size();i++){
            lstTabName.add(Label.xpath(String.format("(%s)[%d]",tabNameXpath,i)).getText().trim());
        }
        List<String> lstSorted = lstTabName;
        Collections.sort(lstSorted);
        Assert.assertEquals(lstTabName,lstSorted,"FAILED! Tab name sorted incorrect");
    }
}
