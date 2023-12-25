package pages.sb11.user;

import com.paltech.element.common.Button;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserActivityMonitoringPage extends WelcomePage {
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public TextBox txtUsername = TextBox.xpath("//div[contains(text(),'Username')]//following-sibling::div//input");
    public Button btnShow = Button.xpath("//button[text()='Show']");
    int colNum = 5;
    public Table tblUser = Table.xpath("//table",colNum);

    public void filter(String username, String fromDate, String toDate) {
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        txtUsername.sendKeys(username);
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public void verifyUsernameDisplay(String username){
        List<String> lstUser = tblUser.getColumn(tblUser.getColumnIndexByName("Username"),10,true);
        Assert.assertTrue(lstUser.contains(username),"FAILED! User name is not displayed");
    }

    public void verifyUserDataSortCorrect() {
        List<String> lstUser = tblUser.getColumn(tblUser.getColumnIndexByName("Username"),15,true);
        List<String> lstSort = new ArrayList<>();
        for (String user : lstUser){
            lstSort.add(user);
        }
        Collections.sort(lstSort);
        Assert.assertEquals(lstUser,lstSort,"FAILED! data table are not sorted by username alphabetically");
    }
}
