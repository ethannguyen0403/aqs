package pages.sb11.trading.popup;

import com.paltech.element.common.Label;
import org.testng.Assert;

public class MasterGroupClientListPopup {
    Label lblTitle = Label.xpath("//app-client-list//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Label lblMaster = Label.xpath("//app-client-list//span[contains(@class,'text-truncate')]");

    public void isMasterGroupClientListPopupDisplay(String masterCode) {
        Assert.assertTrue(getTitlePage().contains("Client List"),"Failed! Client List page is not displayed!");
        Assert.assertEquals(lblMaster.getText(),masterCode,"Failed! Master Code is displayed incorrectly!");
    }
}
