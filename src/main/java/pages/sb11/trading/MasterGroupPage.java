package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.MasterGroupClientListPopup;
import pages.sb11.trading.popup.MasterGroupListPopup;
import pages.sb11.trading.popup.MasterGroupReportPopup;

public class MasterGroupPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCurrency = DropDownBox.xpath("//div[contains(text(),'Currency')]//following::select[1]");
    public DropDownBox ddpOrderBy = DropDownBox.xpath("//div[contains(text(),'Order By')]//following::select[1]");
    public DropDownBox ddpGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");
    public TextBox txtMasterCode = TextBox.xpath("//div[contains(text(),'Master Code')]//following::input[1]");
    public Button btnSearch = Button.xpath("//button[contains(@class,'btn-outline-secondary')]");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Button btnAddMaster = Button.xpath("//button[contains(text(),'Add Master')]");
    public Table tbMaster = Table.xpath("//app-master-group//table",10);
    int colMaster = 3;
    int colGroup = 8;
    int colCL = 9;

    public void filterMaster (String currency, String orderBy, String masterCode){
        if (!currency.isEmpty())
            ddpCurrency.selectByVisibleText(currency);
        ddpOrderBy.selectByVisibleText(orderBy);
        txtMasterCode.sendKeys(masterCode);
        btnShow.click();
    }

    public MasterGroupReportPopup openMasterGroupReport(String masterCode){
        int rowIndex = getMasterRowIndex(masterCode);
        tbMaster.getControlOfCell(1,colMaster,rowIndex,null).click();
        return new MasterGroupReportPopup();
    }

    public MasterGroupListPopup openGroupListPopup(String masterCode){
        int rowIndex = getMasterRowIndex(masterCode);
        tbMaster.getControlOfCell(1,colGroup,rowIndex,null).click();
        return new MasterGroupListPopup();
    }

    public MasterGroupClientListPopup openMasterGroupClientList(String masterCode){
        int rowIndex = getMasterRowIndex(masterCode);
        tbMaster.getControlOfCell(1,colCL,rowIndex,null).click();
        return new MasterGroupClientListPopup();
    }

    public String getGroupAmount(String masterCode){
        int rowIndex = getMasterRowIndex(masterCode);
        return tbMaster.getControlOfCell(1,colGroup,rowIndex,null).getText();
    }

    private int getMasterRowIndex(String masterCode){
        int i = 1;
        Label lblMaster;
        while (true){
            lblMaster = Label.xpath(tbMaster.getxPathOfCell(1,colMaster,i,null));
            if(!lblMaster.isDisplayed()) {
                System.out.println("Can NOT found the Master code "+masterCode+" in the table");
                return 0;
            }
            if(lblMaster.getText().contains(masterCode)){
                System.out.println("Found the Master code "+masterCode+" in the table");
                return i;
            }
            i = i +1;
        }
    }
}
