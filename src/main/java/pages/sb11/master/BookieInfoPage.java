package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.Header;
import pages.sb11.WelcomePage;

public class BookieInfoPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[text()='Company Unit']//following::select[1]");
    public DropDownBox ddpSupportBy = DropDownBox.xpath("//div[text()='Support By']//following::select[1]");
    public DropDownBox ddpCurrency = DropDownBox.xpath("//div[text()='Currency']//following::select[1]");
    public DropDownBox ddpStatus = DropDownBox.xpath("//div[text()='Status']//following::select[1]");

    public TextBox txtBookie = TextBox.name("searchBy");
    public Label lblBookie = Label.xpath("//div[text()='Bookie']");

    public Button btnSearch = Button.xpath("//button[text()='Search']");
    public Button btnShow = Button.xpath("//button[text()='Show']");
    public Button btnAddBookie = Button.xpath("//button[contains(@class,'btn-outline-success')]");
    public Button btnExportToExcel = Button.xpath("//button[contains(text(),'Export To Excel')]");

    public Table tbBookie = Table.xpath("//table",10);
}
