package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.StakeSizeGroupPopup;
import utils.sb11.ClientSystemUtils;
import utils.sb11.CompanySetUpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StakeSizeGroupPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class, 'card-header')]//span");
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()='Company Unit']//following-sibling::select");
    public DropDownBox ddClient = DropDownBox.xpath("//div[text()='Client']//following-sibling::select");
    public Button btnShow = Button.xpath("//button[text()='Show']");
    Table tblData = Table.xpath("//app-stake-size-grouping//table",10);
    Button btnAddGroup = Button.xpath("//app-stake-size-grouping//button[contains(text(),'Add group')]");
    public void filter(String companyUnit, String clientName){
        if (!companyUnit.isEmpty()){
            ddCompanyUnit.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
        }
        if (!clientName.isEmpty()){
            ddClient.selectByVisibleText(clientName);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public void verifyLstClient(String companyUnitName) {
        List<String> lstClientEx = new ArrayList<>();
        lstClientEx.addAll(ClientSystemUtils.getLstClientName(true,companyUnitName));
        lstClientEx.addAll(ClientSystemUtils.getLstClientName(false,companyUnitName));
        Collections.sort(lstClientEx,String.CASE_INSENSITIVE_ORDER);
        lstClientEx.add(0,"All");
        Assert.assertEquals(ddClient.getOptions(),lstClientEx,"FAILED! Client list displays incorrect");
    }

    public double getLowestStakeRange(String companyName, String clientCode) {
        filter(companyName,clientCode);
        List<String> lstStakeRange = tblData.getColumn(tblData.getColumnIndexByName("Stake Range"),true);
        List<Double> lstStakeEx = new ArrayList<>();
        for (String stake : lstStakeRange){
            lstStakeEx.add(Double.valueOf(stake.split(" ")[1]));
        }
        Collections.sort(lstStakeEx);
        return lstStakeEx.get(0) - 1;
    }
    public StakeSizeGroupPopup openStakeSizeGroupNewPopup(){
        btnAddGroup.click();
        waitSpinnerDisappeared();
        return new StakeSizeGroupPopup();
    }
    public String addNewGroup(String companyName, String clientCode, String groupName, double stakeRangeFrom, double stakeRangeTo, boolean submit) {
        StakeSizeGroupPopup page = openStakeSizeGroupNewPopup();
        waitSpinnerDisappeared();
        return page.addNewGroup(companyName,clientCode,groupName,stakeRangeFrom,stakeRangeTo,submit);
    }

    public void verifyGroupDisplay(String clientCode, String groupName, String accountCurrency, String stakeRange, String dateUpdate, String updateBy) {
        int indexClient = tblData.getRowIndexContainValue(groupName,tblData.getColumnIndexByName("Stake Size Group Name"),"span");
        String clientCodeAc = Label.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Client"),indexClient,"span")).getText().trim();
        String accountCurAc = Label.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("CUR"),indexClient,"span")).getText().trim();
        String stakeRangeAc = Label.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Stake Range"),indexClient,"span")).getText().trim();
        String updateDateAc = Label.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Updated Date"),indexClient,"span")).getText().trim();
        String updateByAc = Label.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Updated By"),indexClient,"span")).getText().trim();
        Assert.assertEquals(clientCodeAc,clientCode,"FAILED! Client Name displays incorrect");
        Assert.assertEquals(accountCurAc,accountCurrency,"FAILED! Currency displays incorrect");
        Assert.assertEquals(stakeRangeAc,stakeRange,"FAILED! Stake range displays incorrect");
        Assert.assertTrue(updateDateAc.contains(dateUpdate),"FAILED! Update Date displays incorrect");
        Assert.assertEquals(updateByAc.toLowerCase(),updateBy.toLowerCase(),"FAILED! Update By's Name displays incorrect");
    }

    public void editFirstGroupNameOfClient(String clientCode, String groupName, double stakeRangeFrom, double stakeRangeTo, boolean submit) {
        tblData.getControlBasedValueOfDifferentColumnOnRow(clientCode,1,tblData.getColumnIndexByName("Client"),1,"span",
                tblData.getColumnIndexByName("Edit"),"span",true,false).click();
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
        StakeSizeGroupPopup page = new StakeSizeGroupPopup();
        page.fillFields("","",groupName,stakeRangeFrom,stakeRangeTo);
        page.btnSubmit.click();
        waitSpinnerDisappeared();
    }

    public void verifyDataTableIsSorted() {
        List<String> lstData = tblData.getColumn(tblData.getColumnIndexByName("Updated Date"),true);
        List<String> lstSorted = lstData;
        Collections.sort(lstSorted,Collections.reverseOrder());
        Assert.assertEquals(lstData,lstSorted,"FAILED! Data display incorrect");
    }

    public void verifyCompanyUnitDropdown() {
        List<String> lstAc = ddCompanyUnit.getOptions();
        List<String> lstEx = CompanySetUpUtils.getListCompany();
        lstEx.add(0,"All");
        Assert.assertEquals(lstAc,lstEx,"FAILED! Stake Size Group page display incorrect");
    }
}
