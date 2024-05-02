package pages.sb11.master;

import com.paltech.element.common.*;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.master.popup.EmailSendingHistoryPopup;
import utils.sb11.CompanySetUpUtils;

public class AddressBookPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpClient = DropDownBox.xpath("//div[contains(text(),'Client')]//following::select[1]");

    public TextBox txtAccountCode = TextBox.xpath("//div[contains(text(),'Account Code')]//following::input[1]");
    public Label lblAccountCode = Label.xpath("//div[contains(text(),'Account Code')]");

    public Button btnSearch = Button.xpath("//button[contains(text(),'Search')]");
    public Button btnHistory = Button.xpath("//button[contains(text(),'History')]");
    public Button btnSave = Button.xpath("//em[contains(@class,'fa-save')]");
    public Button btnCancel = Button.xpath("//em[contains(@class,'fa-times')]");

    public Table tbAddressBook = Table.xpath("//table",7);
    int colAccCode = 2;
    int colCUR = 3;
    int colTo = 4;
    int colCC = 5;
    int colBCC = 6;
    int colEdit = 7;

    public void filterAddress (String companyUnit, String clientCode,String accountCode){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        if (!clientCode.isEmpty())
            ddpClient.selectByVisibleText(clientCode);
        if (!accountCode.isEmpty())
            txtAccountCode.sendKeys(accountCode);
        btnSearch.click();
    }

    public void inputInfo (String toNameEmail, String ccNameEmail, String bccNameEmail){
        tbAddressBook.getControlOfCell(1,colEdit,1,null).click();
        if (!toNameEmail.isEmpty()) {
            TextBox txtTo = TextBox.xpath(tbAddressBook.getxPathOfCell(1,colTo,1,"textarea"));
            txtTo.sendKeys(toNameEmail);
        }
        if (!ccNameEmail.isEmpty()) {
            TextBox txtCC = TextBox.xpath(tbAddressBook.getxPathOfCell(1,colCC,1,"textarea"));
            txtCC.sendKeys(ccNameEmail);
        }
        if (!bccNameEmail.isEmpty()) {
            TextBox txtBCC = TextBox.xpath(tbAddressBook.getxPathOfCell(1,colBCC,1,"textarea"));
            txtBCC.sendKeys(bccNameEmail);
        }
        btnSave.click();
    }

    public void verifyInfo(String accountCode, String CUR, String toNameEmail, String ccNameEmail, String bccNameEmail){
        if (!accountCode.isEmpty()) {
            String accCode = tbAddressBook.getControlOfCell(1,colAccCode,1,null).getText();
            Assert.assertEquals(accountCode,accCode,"Failed! Account Code is displayed in correctly");
        }
        if (!CUR.isEmpty()) {
            String cur = tbAddressBook.getControlOfCell(1,colCUR,1,null).getText();
            Assert.assertEquals(CUR,cur,"Failed! CUR is displayed in correctly");
        }
        if (!toNameEmail.isEmpty()) {
            String to = tbAddressBook.getControlOfCell(1,colTo,1,null).getText();
            Assert.assertEquals(toNameEmail,to,"Failed! To (Name, Email) is displayed in correctly");
        }
        if (!ccNameEmail.isEmpty()) {
            String cc = tbAddressBook.getControlOfCell(1,colCC,1,null).getText();
            Assert.assertEquals(toNameEmail,cc,"Failed! CC (Name, Email) is displayed in correctly");
        }
        if (!bccNameEmail.isEmpty()) {
            String bcc = tbAddressBook.getControlOfCell(1,colBCC,1,null).getText();
            Assert.assertEquals(toNameEmail,bcc,"Failed! BCC (Name, Email) is displayed in correctly");
        }
    }

    public EmailSendingHistoryPopup openEmailSendingHistoryPopup(){
        btnHistory.click();
        //Wait for popup display
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new EmailSendingHistoryPopup();
    }

    public void verifyUI(String clientCode) {
        System.out.println("Dropdown: Company Unit, Client");
        Assert.assertEquals(ddpCompanyUnit.getOptions(), CompanySetUpUtils.getListCompany(),"Failed! Company Unit dropdown is not displayed!");
        Assert.assertTrue(ddpClient.getOptions().contains(clientCode),"Failed! Client dropdown is not displayed!");
        System.out.println("Textbox: Account Code");
        Assert.assertEquals(lblAccountCode.getText(),"Account Code","Failed! Account Code textbox is not displayed!");
        System.out.println("Button: Search, History");
        Assert.assertEquals(btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        Assert.assertEquals(btnHistory.getText(),"History","Failed! History button is not displayed!");
        System.out.println("Validate Address Book table is displayed with correctly header column");
        Assert.assertEquals(tbAddressBook.getHeaderNameOfRows(), SBPConstants.AddressBook.TABLE_HEADER,"Failed! Address Book table is displayed with incorrect header column");
    }
}
