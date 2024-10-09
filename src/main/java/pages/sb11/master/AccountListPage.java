package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.master.popup.UpdatePTPopup;
import utils.sb11.accounting.CompanySetUpUtils;

import static common.SBPConstants.KASTRAKI_LIMITED;

public class AccountListPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpType = DropDownBox.xpath("//div[contains(text(),'Type')]//following::select[1]");
    public DropDownBox ddpClient = DropDownBox.xpath("//div[contains(text(),'Client')]//following::select[1]");
    public DropDownBox ddpBookie = DropDownBox.xpath("//div[contains(text(),'Bookie')]//following::select[1]");
    public DropDownBox ddpCUR = DropDownBox.xpath("//div[contains(text(),'CUR')]//following::select[1]");
    public DropDownBox ddpStatus = DropDownBox.xpath("//div[contains(text(),'Status')]//following::select[1]");
    public DropDownBox ddpCreationType = DropDownBox.xpath("//div[contains(text(),'Creation Type')]//following::select[1]");
    public TextBox txtAccountCode = TextBox.xpath("//div[contains(text(),'Account Code')]//following::input[1]");
    public Label lblAccountCode = Label.xpath("//div[contains(text(),'Account Code')]");

    public Table tbAccountList = Table.xpath("//table",23);
    int colEditPT = 19;
    int colSocLive = 9;
    int colSocNonLive = 10;
    int colBasketLive = 11;
    int colBasketNonLive = 12;
    int colFBLive = 13;
    int colFBNonLive = 14;
    int colTenLive = 15;
    int colTenNonLive = 16;
    int colOtherLive = 17;
    int colOtherNonLive = 18;
    public Button btnSearch = Button.xpath("//button[contains(text(),'Search')]");

    public void filterAccount (String companyUnit, String type, String clientBookie, String currency, String status, String creationType, String accountCode){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpType.selectByVisibleText(type);
        if (type == "Client"){
            if (!clientBookie.isEmpty())
                ddpClient.selectByVisibleText(clientBookie);
        } else {
            if (!clientBookie.isEmpty())
                ddpBookie.selectByVisibleText(clientBookie);
        }
        if (!currency.isEmpty())
            ddpCUR.selectByVisibleText(currency);
        if (!status.isEmpty())
            ddpStatus.selectByVisibleText(status);
        if (!creationType.isEmpty())
            ddpCreationType.selectByVisibleText(creationType);
        txtAccountCode.sendKeys(accountCode);
        btnSearch.click();
    }

    public UpdatePTPopup openUpdatePTPopup(){
        tbAccountList.getControlOfCell(1,colEditPT,1,null).click();
        waitPageLoad();
        return new UpdatePTPopup();
    }

    public void verifyPT (String sport, String livePT, String nonLivePT){
        switch (sport){
            case "Soccer":
                if (!livePT.isEmpty()) {
                    String livePTSoccer = String.format("%.1f",tbAccountList.getControlOfCell(1,colSocLive,1,null).getText());
                    Assert.assertEquals(livePT,livePTSoccer,"Failed! Soccer Live PT is not matched!");
                }
                if (!nonLivePT.isEmpty()) {
                    String nonLivePTSoccer = String.format("%.1f",tbAccountList.getControlOfCell(1, colSocLive, 1, null).getText());
                    Assert.assertEquals(nonLivePT, nonLivePTSoccer, "Failed! Soccer Non Live PT is not matched!");
                }
                return;
            case "Tennis":
                if (!livePT.isEmpty()) {
                    String livePTTennis = String.format("%.1f",tbAccountList.getControlOfCell(1,colTenLive,1,null).getText());
                    Assert.assertEquals(livePT,livePTTennis,"Failed! Tennis Live PT is not matched!");
                }
                if (!nonLivePT.isEmpty()) {
                    String nonLivePTSoccer = String.format("%.1f",tbAccountList.getControlOfCell(1, colTenNonLive, 1, null).getText());
                    Assert.assertEquals(nonLivePT, nonLivePTSoccer, "Failed! Tennis Non Live PT is not matched!");
                }
                return;
            case "Basketball":
                if (!livePT.isEmpty()) {
                    String livePTBasket = String.format("%.1f",tbAccountList.getControlOfCell(1,colBasketLive,1,null).getText());
                    Assert.assertEquals(livePT,livePTBasket,"Failed! Basketball Live PT is not matched!");
                }
                if (!nonLivePT.isEmpty()) {
                    String nonLivePTBasket = String.format("%.1f",tbAccountList.getControlOfCell(1, colBasketNonLive, 1, null).getText());
                    Assert.assertEquals(nonLivePT, nonLivePTBasket, "Failed! Basketball Non Live PT is not matched!");
                }
                return;
            case "Football":
                if (!livePT.isEmpty()) {
                    String livePTFootball = String.format("%.1f",tbAccountList.getControlOfCell(1,colFBLive,1,null).getText());
                    Assert.assertEquals(livePT,livePTFootball,"Failed! Football Live PT is not matched!");
                }
                if (!nonLivePT.isEmpty()) {
                    String nonLivePTFootball = String.format("%.1f",tbAccountList.getControlOfCell(1, colFBNonLive, 1, null).getText());
                    Assert.assertEquals(nonLivePT, nonLivePTFootball, "Failed! Football Non Live PT is not matched!");
                }
                return;
            case "Others":
                if (!livePT.isEmpty()) {
                    String livePTOthers = String.format("%.1f",tbAccountList.getControlOfCell(1,colOtherLive,1,null).getText());
                    Assert.assertEquals(livePT,livePTOthers,"Failed! Others Live PT is not matched!");
                }
                if (!nonLivePT.isEmpty()) {
                    String nonLivePTOthers = String.format("%.1f",tbAccountList.getControlOfCell(1, colOtherNonLive, 1, null).getText());
                    Assert.assertEquals(nonLivePT, nonLivePTOthers, "Failed! Others Non Live PT is not matched!");
                }
                return;
        }
    }


    public void verifyUI() {
        System.out.println("Dropdown: Company Unit, Type, Client, CUR, Status, Creation Type");
        Assert.assertEquals(ddpCompanyUnit.getOptions(), CompanySetUpUtils.getListCompany(),"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(ddpType.getOptions(), SBPConstants.AccountList.TYPE_LIST,"Failed! Type dropdown is not displayed!");
        Assert.assertEquals(ddpCUR.getOptions(), SBPConstants.AccountList.CURRENCY_LIST,"Failed! CUR dropdown is not displayed!");
        Assert.assertEquals(ddpStatus.getOptions(), SBPConstants.AccountList.STATUS_LIST,"Failed! Status dropdown is not displayed!");
        Assert.assertEquals(ddpCreationType.getOptions(), SBPConstants.AccountList.CREATION_TYPE_LIST,"Failed! Creation Type dropdown is not displayed!");
        System.out.println("Textbox: Account Code");
        Assert.assertEquals(lblAccountCode.getText(),"Account Code","Failed! Account Code textbox is not displayed!");
        System.out.println("Button: Search");
        Assert.assertEquals(btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        System.out.println("Validate Account List table is displayed with correctly header column");
        filterAccount(KASTRAKI_LIMITED,"Client","QA Client (No.121 QA Client)","","","","");
        Assert.assertEquals(tbAccountList.getHeaderNameOfRows(), SBPConstants.AccountList.TABLE_HEADER,"Failed! Account List table is displayed incorrectly header column");
    }
}
