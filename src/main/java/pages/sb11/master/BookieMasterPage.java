package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;

public class BookieMasterPage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpBookie = DropDownBox.xpath("//div[contains(text(),'Bookie')]//following::select[1]");
    public DropDownBox ddpSuper = DropDownBox.xpath("//div[contains(text(),'Super')]//following::select[1]");
    public DropDownBox ddpGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");

    public TextBox txtMasterCode = TextBox.xpath("//div[contains(text(),'Master Code')]//following::input[1]");

    public Button btnSearch = Button.xpath("//button[contains(text(),'Search')]");
    public Button btnMoreFilters = Button.xpath("//button[contains(text(),'More Filters')]");
    public Button btnAddMaster = Button.xpath("//button[contains(text(),'Add Master')]");

    public Label lblMasterCode = Label.xpath("//div[contains(text(),'Master Code')]");

    public Table tbMaster = Table.xpath("//table",10);
}
