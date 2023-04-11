package pages.sb11.sport.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;

public class CreateCricketSeasonPopup {
    Label lblTitle = Label.xpath("//app-dialog-add-seasion//div[contains(@class,'modal-title')]");
    public String getTitlePage (){return lblTitle.getText().trim();}
    public TextBox txtSeasonName = TextBox.xpath("//app-dialog-add-seasion//span[contains(text(),'Season Name')]//following::input[1]");
    public TextBox txtStartDate = TextBox.xpath("//app-dialog-add-seasion//span[contains(text(),'Start Date')]//following::input[1]");
    public TextBox txtEndDate = TextBox.xpath("//app-dialog-add-seasion//span[contains(text(),'End Date')]//following::input[1]");
    public DateTimePicker dtpStartDate = DateTimePicker.xpath(txtStartDate,"//bs-days-calendar-view");
    public DateTimePicker dtpEndDate = DateTimePicker.xpath(txtEndDate,"//bs-days-calendar-view");
    public Button btnSubmit = Button.xpath("//app-dialog-add-seasion//button[contains(text(),'Submit')]");
    public Button btnClear = Button.xpath("//app-dialog-add-seasion//span[contains(text(),'Clear')]");
    public Button btnClose = Button.xpath("//app-dialog-add-seasion//span[contains(@class,'close-icon')]");
    public void close(){btnClose.click();}

    public void addSeason (String seasonName, String startDate, String endDate, boolean isSubmit){
        txtSeasonName.sendKeys(seasonName);
        if(!startDate.isEmpty()){
            dtpStartDate.selectDate(startDate,"d/MM/yyyy");
        }
        if(!endDate.isEmpty()) {
            dtpEndDate.selectDate(endDate, "d/MM/yyyy");
        }
        if (isSubmit){
            btnSubmit.click();
        }
    }
}
