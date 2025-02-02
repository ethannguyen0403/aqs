package pages.sb11.master.termsAndConditionsPopup;

import com.paltech.element.common.Label;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

public class LogPopup extends WelcomePage {
    public Label lblTitle = Label.xpath("//app-terms-and-conditions-log//div[contains(@class,'header')]/h5");
    Table tblData = Table.xpath("//app-terms-and-conditions-log//table",6);

    public boolean isLogDisplay(String colName, String oldValue, String newValue, String modifiedBy, String modifiedTime) {
        int numberRow = tblData.getNumberOfRows(false,true);
        String timeModified = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Modified Time"),numberRow,null).getText().trim();
        String attribute = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Attribute"),numberRow,null).getText().trim();
        String from = tblData.getControlOfCell(1,tblData.getColumnIndexByName("From"),numberRow,null).getText().trim();
        String to = tblData.getControlOfCell(1,tblData.getColumnIndexByName("To"),numberRow,null).getText().trim();
        String modifyBy = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Modified By"),numberRow,null).getText().trim();
        if (timeModified.contains(modifiedTime) && colName.equals(attribute) && from.equals(oldValue) && to.equals(newValue) && modifyBy.equals(modifiedBy)){
            return true;
        }
        System.out.println("FAILED! Log is not displayed");
        return false;
    }
}
