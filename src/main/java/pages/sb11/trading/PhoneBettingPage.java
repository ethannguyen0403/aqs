package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import utils.sb11.accounting.CompanySetUpUtils;

import java.util.List;

import static common.SBPConstants.FINANCIAL_YEAR_LIST_NEW;

public class PhoneBettingPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//span[text()='Company Unit']//following::select[1]");
    public DropDownBox ddpFinancialYear = DropDownBox.xpath("//span[text()='Financial Year']//following::select[2]");
    public DropDownBox ddpSport = DropDownBox.id("sport");
    public TextBox txtFromDate = TextBox.xpath("//app-phone-betting-report//span[text() = 'From Date']//following::input[1]");
    public TextBox txtToDate = TextBox.xpath("//app-phone-betting-report//span[text() = 'To Date']//following::input[2]");
    public Label lblFromDate = Label.xpath("//app-phone-betting-report//span[text() = 'From Date']");
    public Label lblToDate = Label.xpath("//app-phone-betting-report//span[text() = 'To Date']");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");

    public void verifyUI() {
        System.out.println("Dropdown: Company Unit, Financial Year, Sports");
        List<String> lstCompany = CompanySetUpUtils.getListCompany();
        lstCompany.add(0,"All");
        Assert.assertEquals(ddpCompanyUnit.getOptions(), lstCompany,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertTrue(ddpSport.getOptions().contains("Soccer"),"Failed! Sport dropdown is not displayed!");
        System.out.println("Datetimepicker: From Date, To Date");
        Assert.assertEquals(lblFromDate.getText(),"From Date","Failed! From Date datetimepicker is not displayed!");
        Assert.assertEquals(lblToDate.getText(),"To Date","Failed! To Date datetimepicker is not displayed!");
        System.out.println("Button: Show button");
        Assert.assertEquals(btnShow.getText(),"SHOW","Failed! Show button is not displayed!");
    }
}
