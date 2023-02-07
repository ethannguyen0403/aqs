package pages.sb11.soccer;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DoubleUtils;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.popup.spp.SmartGroupPopup;

import java.util.List;

public class SPPPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    DropDownBox ddpReportBy = DropDownBox.xpath("//div[contains(@class,'container-fluid py-5 cbody')]//div[contains(@class,'card-body border')][1]/div[1]//select");
    DropDownBox ddpPunterType = DropDownBox.xpath("//div[contains(@class,'container-fluid py-5 cbody')]//div[contains(@class,'card-body border')][1]/div[2]//select");
    DropDownBox ddpSmartMaster = DropDownBox.xpath("//div[contains(@class,'container-fluid py-5 cbody')]//div[contains(@class,'card-body border')][1]/div[3]//select");
    DropDownBox ddpSmartAgent = DropDownBox.xpath("//div[contains(@class,'container-fluid py-5 cbody')]//div[contains(@class,'card-body border')][1]/div[4]//select");
    TextBox txtFromDate = TextBox.xpath("//div[contains(@class,'container-fluid py-5 cbody')]//div[contains(@class,'card-body border')][1]/div[5]//input");
    TextBox txtToDate = TextBox.xpath("//div[contains(@class,'container-fluid py-5 cbody')]//div[contains(@class,'card-body border')][1]/div[6]//input");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    Label lblSmartGroup = Label.xpath("//div[contains(@class,'container-fluid py-5 cbody')]//div[contains(@class,'card-body border')][2]/div[4]");
    int totalColumnNumber = 15;
    int colGroupCode = 2;
    public int colWL =12;
    Table tblReport = Table.xpath("//app-spp//div[contains(@class,'filter bg-white ')][1]/table", totalColumnNumber);
    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    /**
     * This function use to filter data in SPP page, use filter with the paramerter as below
     * @param reportBy
     * @param punterType
     * @param smartMaster
     * @param smartAgent
     * @param fromDate
     * @param toDate
     */
    public void filter(String reportBy, String punterType, String smartMaster, String smartAgent, String fromDate, String toDate){
        if(!reportBy.isEmpty())
            ddpReportBy.selectByVisibleText(reportBy);
        if(!punterType.isEmpty())
            ddpPunterType.selectByVisibleText(punterType);
        if(!smartMaster.isEmpty())
            ddpSmartMaster.selectByVisibleText(smartMaster);
        if(!smartAgent.isEmpty())
            ddpSmartAgent.selectByVisibleText(smartAgent);
        String currentDate = txtFromDate.getAttribute("value");
        if(!fromDate.isEmpty())
            if(!currentDate.equals(fromDate))
                txtFromDate.sendKeys(fromDate);
        currentDate = txtFromDate.getAttribute("value");
        if(!toDate.isEmpty())
            if(!currentDate.equals(toDate))
                txtToDate.sendKeys(fromDate);
        btnShow.click();
        waitSpinnerDisappeared();
    }

    /**
     * This function click on Smart Group lable in Filter section then filter the selected group
     * @param smartGroup
     */
    private void filterSmartGroup(String smartGroup) {
      SmartGroupPopup smartGroupPopup = clickSmartGroup();
      smartGroupPopup.setSmartGroup(smartGroup);
      btnShow.click();
      waitSpinnerDisappeared();
    }

    public List<String> getRowDataOfGroup(String groupName){
        Table tbGroup = getGroupTable(groupName);
        return tbGroup.getRows(true).get(0);
    }
    private SmartGroupPopup clickSmartGroup(){
        lblSmartGroup.click();
        return new SmartGroupPopup();
    }

    /**
     * This function get the table that contain the expected group name
     * @param groupName
     * @return Table
     */
    private Table getGroupTable(String groupName){
        int index = 1;
        Table tblGroup;
        String groupCode;
        while (true){
            tblGroup = Table.xpath("//app-spp//div[contains(@class,'filter bg-white ')]["+ index +"]/table",totalColumnNumber);
            if(!tblGroup.isDisplayed()){
                System.out.println("**DEBUG: Not found the table group " +groupName);
                return null;
            }
            groupCode = tblGroup.getControlOfCell(1,colGroupCode,1,null).getText().trim();
            if(groupCode.equals(groupName)){
                return tblGroup;
            }
        }
    }

    public boolean verifyAmountDataMatch(String actual, String expected){
        double actualDouble = Double.parseDouble(actual);
        String roundAcutal = String.format("%,.0f",DoubleUtils.roundUpWithTwoPlaces(actualDouble));
        return roundAcutal.equals(expected);
    }

}
