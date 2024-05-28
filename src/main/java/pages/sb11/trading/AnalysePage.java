package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import pages.sb11.WelcomePage;
import pages.sb11.trading.analyse.CreateNewLinePopup;

public class AnalysePage extends WelcomePage {
    public Label lblTitle = Label.xpath("//app-analyse//div[contains(@class,'main-box-header')]/div[1]/span");
    public Button btnCreateManage = Button.xpath("//span[text()='Create/Manage Lines']//parent::button");
    DropDownBox ddLevel = DropDownBox.xpath("//app-analyse//div[text()='Level']//following-sibling::select");
    DropDownBox ddSport = DropDownBox.xpath("//app-analyse//div[text()='Sport']//following-sibling::select");
    private TextBox txtFromDate = TextBox.xpath("//app-analyse//input[@name='fromDate']");
    private DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    private TextBox txtToDate = TextBox.xpath("//app-analyse//input[@name='toDate']");
    private DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//app-analyse//button[text()='Show']");

    public CreateNewLinePopup openCreateNewLinePopup(){
        btnCreateManage.click();
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
        return new CreateNewLinePopup();
    }
    public void filter(String level, String line, String sport, String fromDate, String toDate){
        if (!level.isEmpty()){
            ddLevel.selectByVisibleText(level);
            waitSpinnerDisappeared();
        }
        if (!line.isEmpty()){
            selectMasterAgent(line);
        }
        if (!sport.isEmpty()){
            ddSport.selectByVisibleText(sport);
        }
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    private void selectMasterAgent(String masterAgent){
        Button btnMasterAgent = Button.xpath(String.format("//app-analyse//angular2-multiselect"));
        btnMasterAgent.click();
        waitSpinnerDisappeared();
        Label lblMasterAgent = Label.xpath(String.format("//app-analyse//angular2-multiselect//ul//label[text()='%s']",masterAgent));
        lblMasterAgent.scrollToThisControl(true);
        waitSpinnerDisappeared();
        lblMasterAgent.click();
        waitSpinnerDisappeared();
        btnMasterAgent.click();
    }
}
