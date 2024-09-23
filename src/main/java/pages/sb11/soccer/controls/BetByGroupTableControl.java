package pages.sb11.soccer.controls;

import com.paltech.element.BaseElement;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.RadioButton;
import com.paltech.element.common.TextBox;
import controls.Table;
import org.openqa.selenium.By;


public class BetByGroupTableControl extends BaseElement {
    //app-bets-by-group-table//div[contains(@class,'table-contain')][1]
    private String _xpath;
    public Label lblTitle;
    public Table tblBGG;
    int totalCol = 13;
    int accountCol = 2;
    int betDateCol = 3;
    int eventCol = 4;
    int priceCol = 9;
    int traderCol = 12;
    private BetByGroupTableControl(By locator, String xpathExpression) {
        super(locator);
        this._xpath = xpathExpression;
        lblTitle = Label.xpath(String.format("%s//div[contains(@class,'header')]//div[contains(@class,'font-weight-bold')]",_xpath));

        tblBGG = Table.xpath(String.format("%s//table[@aria-label='bbg-table']",_xpath),totalCol);
    }

    public static BetByGroupTableControl xpath(String xpathExpression) {
        return new BetByGroupTableControl(By.xpath(xpathExpression), xpathExpression);
    }

    public void clickPriceColumn(String account){
        // click on Price cell of the first row if not input account
        if (account.isEmpty()){
            tblBGG.getControlOfCell(1,priceCol,1,null).click();
        } else {
            int rowIndex = tblBGG.getRowIndexContainValue(account,accountCol,null);
            tblBGG.getControlOfCell(1,priceCol,rowIndex,null).click();
        }
    }
    public void clickTraderColumn(String account){
        // click on Trader cell of the first row if not input account
        if (account.isEmpty()){
            tblBGG.getControlOfCell(1,traderCol,1,null).click();
        } else {
            int rowIndex = tblBGG.getRowIndexContainValue(account,accountCol,null);
            tblBGG.getControlOfCell(1,traderCol,rowIndex,null).click();
        }
    }

    public int getRowIndexContainAccount(String account){
        return tblBGG.getRowIndexContainValue(account, accountCol,null);
    }

    public String getSmartGroupName() {
        String title = lblTitle.getText();
        return title.split(" -")[0];
    }

    public String getSmartGroupCurr(){
        String title = lblTitle.getText();
        return title.split("- ")[1];
    }



}
