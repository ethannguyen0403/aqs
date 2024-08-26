package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import pages.sb11.WelcomePage;

public class TransactionVerificationPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpWebsite = DropDownBox.xpath("//div[contains(text(),'Website')]//following::select[1]");
    public DropDownBox ddpSiteLogin = DropDownBox.xpath("//div[contains(text(),'Site Login')]//following::select[1]");
    public TextBox txtDate = TextBox.name("fromDate");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-datepicker-container");
    public Label lblDate = Label.xpath("//div[contains(text(),'Date')]");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");

    public void filterTxn (String website, String siteLogin, String date){
        ddpWebsite.selectByVisibleText(website);
        waitSpinnerDisappeared();
        ddpSiteLogin.selectByVisibleText(siteLogin);
        if(!date.isEmpty()){
            dtpDate.selectDate(date,"dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
}
