package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RetainedEarningsPage extends WelcomePage {
    private int totalCol = 3;
    private int colDes = 2;
    private int colAmount = 3;
    private int rowOrderAmount = 4;
    private int colDesLimit = 3;

    protected Label lblTitle = Label.xpath("//div[contains(@class, 'card-header')]//span");
    protected Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()= 'Company Unit']/parent::div//select");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//div[text()= 'Financial Year']/parent::div//select");
    Table tblTotal = Table.xpath("//app-retained-earnings//table[@id='my-table']", totalCol);
    public Button btnExportToExcel = Button.xpath("//button//i[contains(@class, 'fa-file-excel')]");
    public Button btnExportToPDF = Button.xpath("//button//em[contains(@class, 'fa-file-pdf')]");
    public Label lblAmountAreShow = Label.xpath("//app-aqs-bet//label[contains(text(), 'Amounts are shown in')]");
    public Label lblAmountRetainedEnding = Label.xpath("//td[contains(., 'Retained Earning Ending')]/following-sibling::td[1]");

    public String getTitle(){
        return lblTitle.getText();
    }

    public void filter(String companyUnit, String financialYears) {
        if (!companyUnit.isEmpty()) {
            ddCompanyUnit.selectByVisibleText(companyUnit);
        }
        if (!financialYears.isEmpty()) {
            ddFinancialYear.selectByVisibleText(financialYears);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public RetainedEarningsPage filterRetainedEarnings() {
        filter("", "");
        return this;
    }

    public boolean isAmountNumberCorrectFormat(String number){
        Pattern pattern = Pattern.compile("^\\d{1,3}([ ,]?\\d{3})*([.,]\\d+)?$");
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }

    public List<String> getDescriptionListValue() {
        return tblTotal.getColumn(tblTotal.getColumnIndexByName("Description"), 3, false);
    }

    public String getTotalRetained(){
        return tblTotal.getControlOfCell(1,2, rowOrderAmount, null).getText().trim();
    }

    public void exportExcel(){
        btnExportToExcel.scrollToTop();
        btnExportToExcel.click();
        waitSpinnerDisappeared();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportPDF(){
        btnExportToPDF.scrollToTop();
        btnExportToPDF.click();
        waitSpinnerDisappeared();
    }


    /**get amount by name of Description columns
     * @param amountDes: name of Description columns
     */
    public String getAmount(String amountDes) {
        int rowIndex = getDesRowIndex(amountDes);
        String amountDesValue = Label.xpath(
                tblTotal.getControlxPathBasedValueOfDifferentColumnOnRow(amountDes, 1, colDes, rowIndex, null, colAmount, null, false,
                        false)).getText().trim().replace(",","");
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

    public void verifyValueOfDes(Map<String, String> retainValueEx) {
        Assert.assertEquals(retainValueEx.get("Beginning Retained Earnings"),getAmount("Beginning Retained Earnings"),"FAILED! Beginning Retained Earnings amount displays incorrect.");
        Assert.assertEquals(retainValueEx.get("Net Income/Loss from Operation"),getAmount("Net Income/Loss from Operation"),"FAILED! Net Income/Loss from Operation amount displays incorrect.");
        Assert.assertEquals(retainValueEx.get("Dividends"),getAmount("Dividends"),"FAILED! Beginning Retained Earnings amount displays incorrect.");
    }
}
