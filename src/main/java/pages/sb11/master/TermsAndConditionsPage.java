package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.master.termsAndConditionsPopup.LogPopup;
import utils.sb11.ClientSystemUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TermsAndConditionsPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    DropDownBox ddClientBookie = DropDownBox.xpath("//div[text()='Client/Bookie']//following-sibling::div//select");
    public DropDownBox ddClientBookieList = DropDownBox.xpath("//div[contains(text(),'List')]//following-sibling::select");
    Button btnShow = Button.xpath("//button[text()='Show']");
    int clientBookieNamecol = 2;
    public Table tblData = Table.xpath("//app-terms-and-conditions//table",9);
    public void filter(String companyUnit, String clientBookieType, String clientBookieName){
        if (!companyUnit.isEmpty()){
            ddpCompanyUnit.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
            waitSpinnerDisappeared();
        }
        if (!clientBookieType.isEmpty()){
            ddClientBookie.selectByVisibleText(clientBookieType);
            waitSpinnerDisappeared();
        }
        if (!clientBookieName.isEmpty()){
            ddClientBookieList.selectByVisibleText(clientBookieName);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public void verifyClientListDropdownDisplay(String companyName) {
        List<String> lstClientEx = new ArrayList<>();
        lstClientEx.addAll(ClientSystemUtils.getLstClientName(true,companyName));
        lstClientEx.addAll(ClientSystemUtils.getLstClientName(false,companyName));
        Collections.sort(lstClientEx,String.CASE_INSENSITIVE_ORDER);
        List<String> lstClientAc = ddClientBookieList.getOptions();
        lstClientAc.remove("All");
        //Because there are some client check testing so client name dropdown in Page that will less than Client System page
        for (String client : lstClientAc){
            if (!lstClientEx.contains(client)){
                Assert.assertTrue(false,"FAILED! client "+client+" is not exist");
            }
        }
    }

    public boolean isClientNameDisplay(String clientName) {
        List<String> lstClientAc = tblData.getColumn(tblData.getColumnIndexByName("Client Name"),true);
        if (clientName.equals("All")){
            List<String> lstClientEx = ddClientBookieList.getOptions();
            lstClientEx.remove("All");
            if (lstClientAc.equals(lstClientAc)){
                return true;
            }
            System.out.println("FAILED! Client display incorrect");
            return false;
        } else {
            for (String clientDisplay : lstClientAc){
                if (clientDisplay.equals(clientName)){
                    return true;
                }
            }
            System.out.println("FAILED! "+clientName+" is not exist");
            return false;
        }
    }

    public void clickEdit(String clientBookieName) {
        tblData.getControlBasedValueOfDifferentColumnOnRow(clientBookieName,1,tblData.getColumnIndexByName("Client Name"),1,null,
                tblData.getColumnIndexByName("Edit"),"button",true,false).click();
    }

    public void verifyDataAfterClickingEdit(List<ArrayList<String>> lstBefore,String clientBookieName, String columnName) {
        Assert.assertEquals(tblData.getControlBasedValueOfDifferentColumnOnRow(clientBookieName,1,clientBookieNamecol,1,null,
                tblData.getColumnIndexByName(columnName),"textarea",true,false).getAttribute("value"),lstBefore.get(0).get(tblData.getColumnIndexByName(columnName)-1),
                "FAILED! "+columnName+" value display incorrect");
    }

    public void editBookieClient(String clientBookieName, String providerTerm, String downlineTerm, String downlinePayment, String saleIncharge, String comment, boolean save) {
        clickEdit(clientBookieName);
        int indexRow = tblData.getRowIndexContainValue(clientBookieName,clientBookieNamecol,null);
        if (!providerTerm.isEmpty()){
            TextBox txtProviderTerm = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Provider term and Payment term"),indexRow,"textarea"));
            txtProviderTerm.sendKeys(providerTerm);
        }
        if (!downlineTerm.isEmpty()){
            TextBox txtDownlineTerm = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Downline term in PT"),indexRow,"textarea"));
            txtDownlineTerm.sendKeys(downlineTerm);
        }
        if (!downlinePayment.isEmpty()){
            TextBox txtDownlinePayment = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Downline payment term"),indexRow,"textarea"));
            txtDownlinePayment.sendKeys(downlinePayment);
        }
        if (!saleIncharge.isEmpty()){
            TextBox txtSaleIncharge = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Sales Incharge"),indexRow,"textarea"));
            txtSaleIncharge.sendKeys(saleIncharge);
        }
        if (!comment.isEmpty()){
            TextBox txtComment = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Comment"),indexRow,"textarea"));
            txtComment.sendKeys(comment);
        }
        if (save){
            tblData.getControlBasedValueOfDifferentColumnOnRow(clientBookieName,1,clientBookieNamecol,1,null,
                    tblData.getColumnIndexByName("Edit"),"span/em[contains(@class,'save')]",true,false).click();
        } else {
            tblData.getControlBasedValueOfDifferentColumnOnRow(clientBookieName,1,clientBookieNamecol,1,null,
                    tblData.getColumnIndexByName("Edit"),"span/em[contains(@class,'text-danger')]",true,false).click();
        }
        waitSpinnerDisappeared();
    }

    public boolean isClientBookieEdited(String clientBookieName, String providerTerm, String downlineTerm, String downlinePayment, String saleIncharge, String comment, boolean save) {
        List<ArrayList<String>> lstBefore = tblData.getRowsWithoutHeader(1,true);
        editBookieClient(clientBookieName,providerTerm,downlineTerm,downlinePayment,saleIncharge,comment,save);
        waitSpinnerDisappeared();
        List<ArrayList<String>> lstAfter = tblData.getRowsWithoutHeader(1,true);
        return lstBefore.equals(lstAfter);
    }

    public void verifyEditButtonDisplay(String clientBookieName) {
        Assert.assertTrue(tblData.getControlBasedValueOfDifferentColumnOnRow(clientBookieName,1,clientBookieNamecol,1,null,
                tblData.getColumnIndexByName("Edit"),"span/em[contains(@class,'save')]",true,false).isDisplayed(),"FAILED! Save button is not displayed");
        Assert.assertTrue(tblData.getControlBasedValueOfDifferentColumnOnRow(clientBookieName,1,clientBookieNamecol,1,null,
                tblData.getColumnIndexByName("Edit"),"span/em[contains(@class,'text-danger')]",true,false).isDisplayed(),"FAILED! Close button is not displayed");
    }

    public void verifyTheScrollbarDisplay(String clientBookieName) {
        String newText = "Automation Testing\nAutomation Testing\nAutomation Testing\nAutomation Testing";
        int indexRow = tblData.getRowIndexContainValue(clientBookieName,clientBookieNamecol,null);
        clickEdit(clientBookieName);
        //Provider term and Payment term column
        TextBox txtProviderTerm = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Provider term and Payment term"),indexRow,"textarea"));
        txtProviderTerm.sendKeys(newText);
        Assert.assertTrue(Double.valueOf(txtProviderTerm.getAttribute("scrollTop")) > 0, "FAILED! Provider term and Payment term textbox does not have the scrollbar");
        //Downline term in PT column
        TextBox txtDownlineTerm = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Downline term in PT"),indexRow,"textarea"));
        txtDownlineTerm.sendKeys(newText);
        Assert.assertTrue(Double.valueOf(txtDownlineTerm.getAttribute("scrollTop")) > 0, "FAILED! Downline term in PT textbox does not have the scrollbar");
        //Downline payment term column
        TextBox txtDownlinePayment = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Downline payment term"),indexRow,"textarea"));
        txtDownlinePayment.sendKeys(newText);
        Assert.assertTrue(Double.valueOf(txtDownlinePayment.getAttribute("scrollTop")) > 0, "FAILED! Downline payment term textbox does not have the scrollbar");
        //Sales Incharge column
        TextBox txtSaleIncharge = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Sales Incharge"),indexRow,"textarea"));
        txtSaleIncharge.sendKeys(newText);
        Assert.assertTrue(Double.valueOf(txtSaleIncharge.getAttribute("scrollTop")) > 0, "FAILED! Sales Incharge textbox does not have the scrollbar");
        //Comment column
        TextBox txtComment = TextBox.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Comment"),indexRow,"textarea"));
        txtComment.sendKeys(newText);
        Assert.assertTrue(Double.valueOf(txtComment.getAttribute("scrollTop")) > 0, "FAILED! Comment textbox does not have the scrollbar");
    }

    public String getValueOfClientBookie(String clientBookieName, String colName) {
        return tblData.getControlBasedValueOfDifferentColumnOnRow(clientBookieName,1,clientBookieNamecol,1,null,
                tblData.getColumnIndexByName(colName),"div",true,false).getText().trim();
    }

    public LogPopup openLog(String clientBookieName) {
        tblData.getControlBasedValueOfDifferentColumnOnRow(clientBookieName,1,clientBookieNamecol,1,null,
                tblData.getColumnIndexByName("Log"),null,true,false).click();
        waitSpinnerDisappeared();
        return new LogPopup();
    }
}
