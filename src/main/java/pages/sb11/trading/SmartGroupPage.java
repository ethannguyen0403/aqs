package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.AgentGroupReportPopup;
import pages.sb11.trading.popup.MemberGroupPopup;
import pages.sb11.trading.popup.SmartGroupReportPopup;

public class SmartGroupPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpMaster = DropDownBox.xpath("//div[contains(text(),'Master')]//following::select[1]");
    public DropDownBox ddpOrderBy = DropDownBox.xpath("//div[contains(text(),'Order By')]//following::select[1]");
    public DropDownBox ddpGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");
    public TextBox txtGroupCode = TextBox.xpath("//div[contains(text(),'Group Code')]//following::input[1]");
    public Button btnSearch = Button.xpath("//button[contains(@class,'btn-outline-secondary')]");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Button btnAddGroup = Button.xpath("//button[contains(text(),'Add Group')]");
    public Button btnMoreFilters = Button.xpath("//button[contains(text(),'More Filters')]");
    public Table tbSmart = Table.xpath("//app-smart-group//table",14);
    int colGroup = 4;
    int colMember = 12;

    public void filterSmartGroup (String masterCode, String orderBy, String groupCode){
        ddpMaster.selectByVisibleText(masterCode);
        ddpOrderBy.selectByVisibleText(orderBy);
        txtGroupCode.sendKeys(groupCode);
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public SmartGroupReportPopup openSmartGroupReport(String groupCode){
        int rowIndex = getGroupRowIndex(groupCode);
        tbSmart.getControlOfCell(1,colGroup,rowIndex,null).click();
        return new SmartGroupReportPopup();
    }

    public MemberGroupPopup openMemberGroupPopup(String groupCode){
        int rowIndex = getGroupRowIndex(groupCode);
        tbSmart.getControlOfCell(1,colMember,rowIndex,null).click();
        return new MemberGroupPopup();
    }

    private int getGroupRowIndex(String groupCode){
        int i = 1;
        Label lblGroup;
        while (true){
            lblGroup = Label.xpath(tbSmart.getxPathOfCell(1,colGroup,i+1,null));
            if(!lblGroup.isDisplayed()) {
                System.out.println("Can NOT found the Group code "+groupCode+" in the table");
                return 0;
            }
            if(lblGroup.getText().contains(groupCode)){
                System.out.println("Found the Group code "+groupCode+" in the table");
                return i+1;
            }
            i = i +1;
        }
    }
}
