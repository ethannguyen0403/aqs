package pages.sb11.generalReports.systemmonitoring;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Tab;
import com.paltech.utils.DateUtils;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.popup.ConfirmPopup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClosingJournalEntriesPage extends WelcomePage {
    public Tab tabActive = Tab.xpath("//a[@class='active']");
    public DropDownBox ddCompany = DropDownBox.xpath("//div[text()='Company Unit']//following-sibling::select");
    public DropDownBox ddMonth = DropDownBox.xpath("//div[text()='Month']//following-sibling::select");
    public Button btnPerformCJE = Button.xpath("//button[contains(text(),'Perform CJE')]");
    int colTable = 3;
    public Table tblClosing = Table.xpath("//table",colTable);

    public void verifyddMonthOption() {
        int curMonth = DateUtils.getMonth(SBPConstants.GMT_7);
        int curYear = DateUtils.getYear(SBPConstants.GMT_7);
        List<String> lstOptionEx = getOptionsOfMonthDropdown(curMonth,curYear);
        List<String> lstOptionAc = ddMonth.getOptions();
        Assert.assertEquals(lstOptionAc,lstOptionEx,"FAILED! Month dropdown display incorrect");
    }
    List<String> getOptionsOfMonthDropdown(int curMonth, int curYear){
        List<String> lstOption = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            lstOption.add(i,getPreMonthOfYear(curMonth,curYear,"yyyy - MMMM"));
            curMonth = curMonth-1;
            if (curMonth==0){
                curMonth = 12;
                curYear = curYear - 1;
            }
        }
        return lstOption;
    }
    public String getPreMonthOfYear(int month, int year, String format){
        SimpleDateFormat prev_day = new SimpleDateFormat("dd");
        Calendar calendar   = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.YEAR, year);

        calendar .add(Calendar.MONTH, -1);      //subtracting a month
        SimpleDateFormat prev_month = new SimpleDateFormat(format);

        return prev_month.format(new Date(calendar .getTimeInMillis()));
    }

    public void filter(String companyUnit, String month, boolean perform) {
        if (!companyUnit.isEmpty()){
            ddCompany.selectByVisibleText(companyUnit);
        }
        if (!month.isEmpty()){
            ddMonth.selectByVisibleText(month);
        }
        btnPerformCJE.click();
        ConfirmPopup confirmPopup = new ConfirmPopup();
        if (perform){
            confirmPopup.btnYes.click();
        } else {
            confirmPopup.btnNo.click();
        }
    }

    public boolean isRecordHistoryDisplay(String month, String username, String performedDate) {
        List<String> lstMonth = tblClosing.getColumn(tblClosing.getColumnIndexByName("Month"),5,true);
        List<String> lstPerformBy = tblClosing.getColumn(tblClosing.getColumnIndexByName("Performed By"),5,true);
        for (int i = 0; i < lstPerformBy.size();i++){
            if (lstPerformBy.get(i).equals(username) && lstMonth.get(i).equals(month)){
                String performAc = tblClosing.getControlOfCell(1,tblClosing.getColumnIndexByName("Performed Date"),i+1,null).getText();
                if (performAc.contains(performedDate)){
                    return true;
                }
            }
        }
        System.out.println("No Record "+username);
        return false;
    }
}
