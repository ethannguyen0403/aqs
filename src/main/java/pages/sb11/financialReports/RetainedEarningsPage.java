package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RetainedEarningsPage extends WelcomePage {
    private int totalCol = 3;
    private int colDes = 2;
    private int colAmount = 3;
    private int rowOrderAmount = 4;
    private int colDesLimit = 3;

    protected Label lblTitle = Label.xpath("//div[contains(@class, 'card-header')]//span");
    protected Button btnShow = Button.xpath("//button[contains(@class, 'btn-success')]");
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()= 'Company Unit']/parent::div//select");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//div[text()= 'Financial Year']/parent::div//select");
    Table tblTotal = Table.xpath("//app-retained-earnings//table[@id='my-table']", totalCol);
    public Button btnExportToExcel = Button.xpath("//button//i[contains(@class, 'fa-file-excel')]");
    public Button btnExportToPDF = Button.xpath("//button//i[contains(@class, 'fa-file-pdf')]");
    public Label lblAmountAreShow = Label.xpath("//app-aqs-bet//label[contains(text(), 'Amounts are shown in')]");
    public Label lblAmountRetainedEnding = Label.xpath("//td[contains(., 'Retained Earning Ending')]/following-sibling::td[1]");

    public String getTitle(){
        return lblTitle.getText();
    }

    public void filterRetainedEarnings(String companyUnit, String financialYears) {
        if (!companyUnit.equals("")) {
            ddCompanyUnit.selectByVisibleText(companyUnit);
        }
        if (!financialYears.equals("")) {
            ddFinancialYear.selectByVisibleText(financialYears);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public RetainedEarningsPage filterRetainedEarnings() {
        filterRetainedEarnings("", "");
        return this;
    }

    public boolean isAmountNumberCorrectFormat(String number){
        Pattern pattern = Pattern.compile("^\\d{1,3}([ ,]?\\d{3})*([.,]\\d+)?$");
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }

    public List<String> getListOfCol(int colIndex, int limit) {
        return tblTotal.getColumn(colIndex, limit, false);
    }

    public List<String> getDescriptionListValue() {
        return getListOfCol(colDes, colDesLimit);

    }

    public String getTotalRetained(){
        return tblTotal.getControlOfCell(1,2, rowOrderAmount, null).getText().trim();
    }

    public RetainedEarningsPage exportExcel(){
        btnExportToExcel.scrollToTop();
        btnExportToExcel.click();
        return this;
    }

    public RetainedEarningsPage exportPDF(){
        btnExportToPDF.scrollToTop();
        btnExportToPDF.click();
        return this;
    }


    /**get amount by name of Description columns
     * @param amountDes: name of Description columns
     */
    public String getAmount(String amountDes) {
        int rowIndex = getDesRowIndex(amountDes);
        String amountDesValue = Label.xpath(
                tblTotal.getControlxPathBasedValueOfDifferentColumnOnRow(amountDes, 1, colDes, rowIndex, null, colAmount, null, false,
                        false)).getText().trim();
        if (amountDesValue.isEmpty() || amountDesValue.equals("")) {
            System.out.println("Can NOT found amount of Description name:  " + amountDes + " in the table");
        }
        return amountDesValue;
    }

    private int getDesRowIndex(String description) {
        int i = 1;
        Label lblDescription;
        while (true) {
            lblDescription = Label.xpath(tblTotal.getxPathOfCell(1, colDes, i, null));
            if (!lblDescription.isDisplayed()) {
                System.out.println("Can NOT found the description name " + description + " in the table");
                return -1;
            }
            if (lblDescription.getText().contains(description)) {
                System.out.println("Found the description name " + description + " in the table");
                return i;
            }
            i = i + 1;
        }
    }

}
