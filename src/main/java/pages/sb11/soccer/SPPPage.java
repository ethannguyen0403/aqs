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

import java.util.ArrayList;
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
                dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        currentDate = txtToDate.getAttribute("value");
        if(!toDate.isEmpty())
            if(!currentDate.equals(toDate))
                dtpToDate.selectDate(toDate,"dd/MM/yyyy");
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
       return getGroupTable(groupName);
    }

    private SmartGroupPopup clickSmartGroup(){
        lblSmartGroup.click();
        return new SmartGroupPopup();
    }

    /* Start This is work account to get a data in a row of table. Because table element on this page is different (//table/tbody/tr/th)
    so we have to write other function to cover this case.
     */
    private List<String> getGroupTable(String groupName){
        int index = 1;
        Table tblGroup;
        String groupCode;
        String tableXpath;
        while (true){
            tableXpath ="//app-spp//div[contains(@class,'filter bg-white')]["+ index +"]/table";
            tblGroup = Table.xpath(tableXpath,totalColumnNumber);
            if(!tblGroup.isDisplayed()){
                System.out.println("**DEBUG: Not found the table group " +groupName);
                return null;
            }
            int rowContainsGroupName = getRowContainsGroupName(tableXpath,colGroupCode,groupName);
            if(rowContainsGroupName != 0){
                return getDataRowOfAGroupName(tableXpath,rowContainsGroupName);
            }
            index = index +1;
        }
    }

    // handle for get a row index contains the value
    private int getRowContainsGroupName(String tblTableXpath,int colIndex,String groupName){
        String cellXpath;
        int rowIndex = 1;
        String groupCode;
        while (true){
            cellXpath = String.format("%s%s", tblTableXpath, String.format("//tbody[1]/tr[%s]/th[%s]", rowIndex, colIndex));
            Label lblCel = Label.xpath(cellXpath);
            if(!lblCel.isDisplayed())
                return 0;
            groupCode = lblCel.getText().trim();
            if(groupCode.equals(groupName)){
                return rowIndex;
            }
            rowIndex = rowIndex +1;
        }
    }

    // handle for get a data on input row
    private List<String> getDataRowOfAGroupName(String xpathTable,int rowIndex){
        List<String> lstRowData = new ArrayList<>();
        String cellXpath;
        int i = 1;
        while(true) {
            cellXpath = String.format("%s%s", xpathTable, String.format("//tbody[1]/tr[%s]/th[%s]", rowIndex, i));
            Label lblCel = Label.xpath(cellXpath);
            if(!lblCel.isDisplayed())
                return lstRowData;
            lstRowData.add(lblCel.getText().trim());
            i = i +1;
        }
    }

   /* End handle for Table  */

    public boolean verifyAmountDataMatch(String actual, String expected){
        double actualDouble = Double.parseDouble(actual);
        String roundAcutal = String.format("%,.0f",DoubleUtils.roundUpWithTwoPlaces(actualDouble));
        return roundAcutal.equals(expected);
    }

}
