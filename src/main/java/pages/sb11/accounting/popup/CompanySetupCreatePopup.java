package pages.sb11.accounting.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.TextBox;

public class CompanySetupCreatePopup {

    public  TextBox txtCompanyName = TextBox.xpath("//app-create-parent-account//span[text()='Company Name']/following::input[1]");
    public  TextBox txtCompanyAddress = TextBox.xpath("//app-create-parent-account//span[text()='Company Address']/following::input[1]");
    public DropDownBox ddlFirstMonth = DropDownBox.xpath("//app-create-parent-account//span[text()='Accounting Period - First month']/following::select[1]");
    public DropDownBox ddlClosingMonth = DropDownBox.xpath("//app-create-parent-account//span[text()='Accounting Period - Closing month']/following::select[1]");
    public DropDownBox ddlCurrency = DropDownBox.xpath("//app-create-parent-account//span[text()='Currency']/following::select[1]");
    public  Button btnClear = Button.xpath("//app-create-parent-account//span[text()='Clear']");
    public  Button btnSubmit = Button.xpath("//app-create-parent-account//button[@class='btn btn-success']");

    public CompanySetupCreatePopup(){
    }

    public CompanySetupCreatePopup(boolean isWaitLoad) {
        if (isWaitLoad) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

}
