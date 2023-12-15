package pages.sb11.generalReports.popup.positionTakingReport;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;
import org.openqa.selenium.By;

public class AccountPopup {
    public Label lblTitle = Label.xpath("//modal-container//h5[contains(@class,'modal-title')]");
    int colNum = 8;
    public Table tblAccount = Table.xpath("//modal-container//table",colNum);
    Icon closeIcon = Icon.xpath("//span[contains(@class,'close-icon')]");
    String valueBookieTotalTillColumnXpath = "//tr[@class='total-hkd']//td[2]";
    public String getWinLoseValue(String accountCode){
        return tblAccount.getControlBasedValueOfDifferentColumnOnRow(accountCode,1,tblAccount.getColumnIndexByName("Account Code"),1,null,tblAccount.getColumnIndexByName("DR-Win/Loss"),
                null,false,false).getText();
    }
    private int getIndexBookieName(String bookieName) {
        Label lblBookie;
        int i = 1;
        while (true){
            lblBookie = Label.xpath(tblAccount.getxPathOfCell(1,tblAccount.getColumnIndexByName("Account Code"),i,null));
            if (!lblBookie.isDisplayed()){
                System.out.println("Bookie is not display!");
                return 0;
            }
            if (lblBookie.getText().equals(bookieName)){
                return i;
            }
            i = i + 1;
        }
    }
    public void clickToClosePopup(){
        if (closeIcon.isDisplayed()){
            closeIcon.click();
        }
    }

    public String getValueSelectDate(String accountCode, String selectDate) {
        colNum = DriverManager.getDriver().findElements(By.xpath("(//modal-container//table)[1]//thead//th")).size();
        tblAccount = Table.xpath("//modal-container//table",colNum);
        return tblAccount.getControlBasedValueOfDifferentColumnOnRow(accountCode,1,tblAccount.getColumnIndexByName("Account Code"),1,null,tblAccount.getColumnIndexByName(selectDate),
                null,false,false).getText();

    }
    public String getValueBookieTotalTillColumn(){
        return Label.xpath(valueBookieTotalTillColumnXpath).getText();
    }
}
