package pages.sb11.master.termsAndConditionsPopup;

import com.paltech.element.common.Label;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

public class LogPopup extends WelcomePage {
    public Label lblTitle = Label.xpath("//app-terms-and-conditions-log//div[contains(@class,'header')]/h5");
    Table tblData = Table.xpath("//app-terms-and-conditions-log//table",6);

    public void isLogDisplay(String clientCode, String colName, String oldValue, String newValue, String modifiedBy, String modifiedTime) {
        int numberRow = tblData.getNumberOfRows(false,true);
        for (int i = 1; i <= numberRow;i++){
            String clientName = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Client Name"),i,null).getText().trim();
            String attribute = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Attribute"),i,null).getText().trim();
            String timeModified = tblData.getControlOfCell(1,tblData.getColumnIndexByName("Modified Time"),i,null).getText().trim();
            if (clientCode.equals(clientName) && colName.equals(attribute) && timeModified.contains(modifiedTime)){
                String from = tblData.getControlOfCell(1,tblData.getColumnIndexByName("From"),i,null).getText().trim();
                String to = tblData.getControlOfCell(1,tblData.getColumnIndexByName("To"),i,null).getText().trim();
                String modifyBy = tblData.getControlOfCell(1,tblData.getColumnIndexByName("To"),i,null).getText().trim();
                Assert.assertEquals(from,oldValue,"FAILED! Old Value display incorrect");
                Assert.assertEquals(to,newValue,"FAILED! Old Value display incorrect");
                Assert.assertEquals(modifyBy,modifiedBy,"FAILED! user display incorrect");
            }
        }
    }
}
