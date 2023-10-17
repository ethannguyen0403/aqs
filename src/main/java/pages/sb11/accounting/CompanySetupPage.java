package pages.sb11.accounting;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.accounting.popup.CompanySetupCreatePopup;


public class CompanySetupPage extends WelcomePage {

    public Label lblTitle = Label.xpath("//app-company-set-up//div[contains(@class,'modal-header')]");
    Button btnCreate = Button.xpath("//app-company-set-up//button[contains(@class, 'btn-success')]");

    public CompanySetupCreatePopup openCreatePopup(){
        btnCreate.click();
        return new CompanySetupCreatePopup(true);
    }


}
