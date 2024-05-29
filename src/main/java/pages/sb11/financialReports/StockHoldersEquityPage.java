package pages.sb11.financialReports;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;


public class StockHoldersEquityPage extends WelcomePage {

    public final static String CAPITAL_ISSUED = "Capital Issued";
    public final static String RETAINED_EARNING = "Retained Earnings";
    DropDownBox ddbCompany = DropDownBox.xpath("//div[contains(text(),'Company')]/following::select[1]");
    DropDownBox ddbFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]/following::select[1]");
    Button btnShow = Button.name("btnShow");
    public Button btnExportExcel = Button.xpath("//button//*[contains(@class, \"far fa-file-excel\")]");
    public Button btnExportPDF = Button.xpath("//button//*[contains(@class, \"far fa-file-pdf\")]");
    public Label lblAmountAreShow = Label.xpath("//app-aqs-bet//label[contains(text(), 'Amounts are shown in')]");
    public Label lblAmountTotalStock = Label.xpath("//td[contains(., \"Total Stockholder's Equity\")]/following-sibling::td[1]");
    public Label lblTotalStock = Label.xpath("//span[contains(., \"Total Stockholder's Equity\")]");
    public int colDes = 2;
    public int colAmount = 3;


    public Table tblStakeholder = Table.xpath("//app-aqs-bet//table", 3);

    public void filter(String companyUnit, String financialYears) {
        if (!companyUnit.equals("")) {
            ddbCompany.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
        }
        if (!financialYears.equals("")) {
            ddbFinancialYear.selectByVisibleText(financialYears);
        }
        btnShow.click();
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
    }

    public boolean verifyLabelIsBold(BaseElement element){
        String fontWeight = element.getWebElement().getCssValue("font-weight");
        return fontWeight.equals("bold") || fontWeight.equals("700");
    }

    public String getAmount(String amountDes) {
        int rowIndex = getDesRowIndex(amountDes);
        String amountDesValue = Label.xpath(
                tblStakeholder.getControlxPathBasedValueOfDifferentColumnOnRow(amountDes, 1, colDes, rowIndex, null, colAmount, "span", false,
                        false)).getText().trim();
        if (amountDesValue.isEmpty() || amountDesValue.equals("")) {
            System.out.println("Can NOT found amount of Description name:  " + amountDes + " in the table");
        }
        return amountDesValue;
    }

    public int getDesRowIndex(String description) {
        int i = 1;
        Label lblDescription;
        while (true) {
            lblDescription = Label.xpath(tblStakeholder.getxPathOfCell(1, colDes, i, null));
            if (!lblDescription.isDisplayed()) {
                System.out.println("Can NOT found the description name " + description + " in the table");
                return -1;
            }
            if (lblDescription.getText().trim().equalsIgnoreCase(description)) {
                System.out.println("Found the description name " + description + " in the table");
                return i;
            }
            i = i + 1;
        }
    }

    public void exportFile(String type) {
        if (type.equals("Excel")){
            btnExportExcel.click();
        } else {
            btnExportPDF.click();
        }
        waitSpinnerDisappeared();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
