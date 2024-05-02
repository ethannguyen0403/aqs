package pages.sb11.accounting;

import com.paltech.element.common.*;
import common.SBPConstants;
import controls.Table;
import objects.Order;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.control.ConfirmPopupControl;
import pages.sb11.soccer.popup.coa.CreateDetailTypePopup;
import pages.sb11.soccer.popup.coa.CreateParentAccountPopup;
import pages.sb11.soccer.popup.coa.CreateSubAccountPopup;
import pages.sb11.soccer.popup.coa.DeletePopup;
import utils.sb11.CompanySetUpUtils;

public class ChartOfAccountPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'modal-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//label[contains(text(),'Company Unit')]//following::select[1]");
    public TextBox txtDetailTypeName = TextBox.xpath("//label[contains(text(),'Detail Type Name')]//following::input[1]");
    public Label lblDetailTypeName = Label.xpath("//label[contains(text(),'Detail Type Name')]");
    public Button btnSearchDetail = Button.xpath("//span[contains(text(),'Accounting')]//following::button[contains(@class,'btn-confirm')][1]");
    public Button btnSearchParent = Button.xpath("//span[contains(text(),'Parent')]//following::button[contains(@class,'btn-confirm')][1]");
    public Button btnSearchSubAcc = Button.xpath("//span[contains(text(),'Sub')]//following::button[contains(@class,'btn-confirm')][1]");
    public Button btnAddDetail = Button.xpath("//span[contains(text(),'Accounting')]//parent::div/button");
    public Button btnAddParent = Button.xpath("//span[contains(text(),'Parent')]//following-sibling::button");
    public Button btnAddSubAcc = Button.xpath("//span[contains(text(),'Sub')]//following-sibling::button");

    public TextBox txtParentAccName = TextBox.xpath("//span[contains(text(),'Parent Account Name')]//following::input[1]");
    public Label lblParentAccName = Label.xpath("//span[contains(text(),'Parent Account Name')]");
    public TextBox txtSubAccName = TextBox.xpath("//span[contains(text(),'Sub-Account Name')]//following::input[1]");
    public Label lblSubAccName = Label.xpath("//span[contains(text(),'Sub-Account Name')]");

    public Table tbDetail = Table.xpath("//span[contains(text(),'Accounting')]//following::table[1]",5);
    public Table tbParent = Table.xpath("//span[contains(text(),'Parent')]//following::table[1]",4);
    public Table tbSubAcc = Table.xpath("//span[contains(text(),'Sub')]//following::table[1]",6);

    int colDetailType = 3;
    int colParentAcc = 3;
    int colSubAcc = 3;
    int colDelete = 5;

    public void filterDetail(String companyUnit, String detailTypeName){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        txtDetailTypeName.sendKeys(detailTypeName);
        btnSearchDetail.click();
    }

    public void filterParent(String companyUnit, String detailTypeName, String parentAccName){
        filterDetail(companyUnit,detailTypeName);
        txtParentAccName.sendKeys(parentAccName);
        btnSearchParent.click();
    }

    public void filterSubAcc(String companyUnit, String detailTypeName, String parentAccName, String subAcc){
        filterParent(companyUnit, detailTypeName, parentAccName);
        txtSubAccName.sendKeys(parentAccName);
        btnSearchSubAcc.click();
    }

    public CreateDetailTypePopup openCreateDetailTypePopup() {
        btnAddDetail.jsClick();
        return new CreateDetailTypePopup();
    }

    public CreateParentAccountPopup openCreateParentAccPopup() {
        btnAddParent.click();
        return new CreateParentAccountPopup();
    }

    public CreateSubAccountPopup openCreateSubAccPopup() {
        btnAddSubAcc.click();
        return new CreateSubAccountPopup();
    }

    public boolean isDetailTypeDisplayed(String detailTypeName){
        int index = getRowContainsDetailTypeName(detailTypeName);
        if(index==0)
            return false;
        return true;
    }

    public boolean isParentAccountDisplayed(String parentAccount){
        int index = getRowContainsParentAccount(parentAccount);
        if(index==0)
            return false;
        return true;
    }

    public boolean isSubAccountDisplayed(String subAccount){
        int index = getRowContainsSubAccount(subAccount);
        if(index==0)
            return false;
        return true;
    }

    public void deleteDetail(String companyUnit, String detailTypeName){
        filterDetail(companyUnit,detailTypeName);
        int rowIndex = getRowContainsDetailTypeName(detailTypeName);
        Icon.xpath(tbDetail.getxPathOfCell(1,colDelete,rowIndex,"em")).click();
        DeletePopup deletePopup = DeletePopup.xpath("//app-confirm");
        deletePopup.confirmYes();
        waitPageLoad();
    }

    public void deleteParent(String companyUnit, String detailTypeName, String parentAccount){
        filterParent(companyUnit,detailTypeName,parentAccount);
        int rowIndex = getRowContainsParentAccount(parentAccount);
        Icon.xpath(tbParent.getxPathOfCell(1,colDelete,rowIndex,"en")).click();
        DeletePopup deletePopup = DeletePopup.xpath("//app-confirm");
        deletePopup.confirmYes();
        waitPageLoad();
    }

    public void deleteSubAcc(String companyUnit, String detailTypeName, String parentAccount, String subAcc){
        filterSubAcc(companyUnit,detailTypeName,parentAccount,subAcc);
        int rowIndex =getRowContainsSubAccount(subAcc);
        Icon.xpath(tbSubAcc.getxPathOfCell(1,colDelete,rowIndex,"en")).click();
        DeletePopup deletePopup = DeletePopup.xpath("//app-confirm");
        deletePopup.confirmYes();
        waitPageLoad();
    }

    private int getRowContainsDetailTypeName(String detailTypeName){
        int i = 1;
        Label lblDetailTypeName;
        while (true){
            lblDetailTypeName = Label.xpath(tbDetail.getxPathOfCell(1,colDetailType,i,null));
            if(!lblDetailTypeName.isDisplayed()){
                System.out.println("Detail Type Name "+detailTypeName+" does not display in the list");
                return 0;
            }
            if(lblDetailTypeName.getText().contains(detailTypeName))
                return i;
            i = i +1;
        }
    }

    private int getRowContainsParentAccount(String parentAccount){
        int i = 1;
        Label lblParentAccount;
        while (true){
            lblParentAccount = Label.xpath(tbParent.getxPathOfCell(1,colParentAcc,i,null));
            if(!lblParentAccount.isDisplayed()){
                System.out.println("Parent Account "+parentAccount+" does not display in the list");
                return 0;
            }
            if(lblParentAccount.getText().contains(parentAccount))
                return i;
            i = i +1;
        }
    }

    private int getRowContainsSubAccount(String subAccount){
        int i = 1;
        Label lblSubAccount;
        while (true){
            lblSubAccount = Label.xpath(tbSubAcc.getxPathOfCell(1,colSubAcc,i,null));
            if(!lblSubAccount.isDisplayed()){
                System.out.println("Sub Account "+subAccount+" does not display in the list");
                return 0;
            }
            if(lblSubAccount.getText().contains(subAccount))
                return i;
            i = i +1;
        }
    }

    public void verifyUI() {
        System.out.println("Detail Type table: Company Unit, Detail Type Name, Search button and Add button");
        Assert.assertEquals(ddpCompanyUnit.getOptions(), CompanySetUpUtils.getListCompany(),"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(lblDetailTypeName.getText(),"Detail Type Name","Failed! Detail Type Name search box is not displayed!");
        Assert.assertTrue(btnSearchDetail.isDisplayed(),"Failed! Button Search Detail is not displayed!");
        Assert.assertTrue(btnAddDetail.isDisplayed(),"Failed! Button Add Detail is not displayed!");
        System.out.println("Parent Account List: Parent Account Name, Search Button and Add button");
        Assert.assertEquals(lblParentAccName.getText(),"Parent Account Name","Failed! Parent Account Name search box is not displayed!");
        Assert.assertTrue(btnSearchParent.isDisplayed(),"Failed! Button Search Parent is not displayed!");
        Assert.assertTrue(btnAddParent.isDisplayed(),"Failed! Button Add Parent is not displayed!");
        System.out.println("Sub-Account List: Sub-Account Name, Search button and Add button");
        Assert.assertEquals(lblSubAccName.getText(),"Sub-Account Name","Failed! Sub-Account Name search box is not displayed!");
        Assert.assertTrue(btnSearchSubAcc.isDisplayed(),"Failed! Button Search Sub-Account is not displayed!");
        Assert.assertTrue(btnAddSubAcc.isDisplayed(),"Failed! Button Add Sub-Account is not displayed!");
        System.out.println("Detail Type, Parent Account List, Sub-Account List table header columns is correctly display");
        Assert.assertEquals(tbDetail.getHeaderNameOfRows(), SBPConstants.ChartOfAccount.TABLE_HEADER_DETAIL,"Failed! Detail Type table header is displayed incorrectly!");
        Assert.assertEquals(tbParent.getHeaderNameOfRows(), SBPConstants.ChartOfAccount.TABLE_HEADER_PARENT,"Failed! Parent Account List table header is displayed incorrectly!");
        Assert.assertEquals(tbSubAcc.getHeaderNameOfRows(), SBPConstants.ChartOfAccount.TABLE_HEADER_SUB_ACCOUNT,"Failed! Sub-Account table header is displayed incorrectly!");
    }
}
