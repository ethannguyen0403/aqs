package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import objects.Order;
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

    public Table tbBookie = Table.xpath("//table",12);
    int colBookieCode = 4;

    public void filterBookie (String companyUnit, String bookieCode, String supportBy, String currency, String status){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        txtBookie.sendKeys(bookieCode);
        btnSearch.click();
        if (!supportBy.isEmpty())
            ddpSupportBy.selectByVisibleText(supportBy);
        if (!currency.isEmpty())
            ddpCurrency.selectByVisibleText(currency);
        if (!status.isEmpty())
            ddpStatus.selectByVisibleText(status);
        btnShow.click();
    }

    public boolean isBookieCodeExist(String bookieCode){
        int rowIndex = getBookieCodeRowIndex(bookieCode);
        String bookie = tbBookie.getControlOfCell(1,colBookieCode,rowIndex,null).getText();
        if (bookie.contains(bookieCode))
            return true;
        return false;
    }

    public void exportBookieList(){
        btnExportToExcel.scrollToTop();
        btnExportToExcel.click();
    }

    private int getBookieCodeRowIndex(String bookieCode){
        int i = 1;
        Label lblBookieCode;
        while (true){
            lblBookieCode = Label.xpath(tbBookie.getxPathOfCell(1,colBookieCode,i,null));
            if(!lblBookieCode.isDisplayed()){
                System.out.println("The bookie code "+bookieCode+" does not display in the list");
                return 0;
            }
            if(lblBookieCode.getText().contains(bookieCode))
                return i;
            i = i +1;
        }
    }
}
