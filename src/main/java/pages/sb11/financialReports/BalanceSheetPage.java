package pages.sb11.financialReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.utils.DoubleUtils;
import common.SBPConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import utils.ExcelUtils;
import utils.sb11.ChartOfAccountUtils;

import java.util.*;

public class BalanceSheetPage extends WelcomePage {
    Label lblTitle = Label.xpath("//app-balance-sheet//div[contains(@class,'card-header main-box-header')]/span");
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()='Company Unit']/parent::div//select");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//div[text()='Financial Year']/parent::div//select");
    public DropDownBox ddMonth = DropDownBox.xpath("//div[text()='Month']/parent::div//select");
    public DropDownBox ddReport = DropDownBox.xpath("//div[text()='Report']/parent::div//select");
    Button btnShow = Button.name("btnShow");
    String lstDetailTypeXpath = "//tbody//tr[@class='total-row-bg']//td[@class='text-left']//span";
    public String titleSectionXpath = "//div[@class='col-6']//span[text()='%s']";
    String parentNameXpath = "//span[text()='%s']//ancestor::tr/following-sibling::tr//td[2]";
    String amountDetailTypeXpath = "//span[text()='%s']/parent::td/following-sibling::td/span";
    String amountParentAccountXpath = "//td[text()='%s']/following-sibling::td";
    String idParentAccountsXpath = "//span[text()='%s']//ancestor::tr/following-sibling::tr//td[1]";
    String valueParentAccountsXpath = "//span[text()='%s']//ancestor::tr/following-sibling::tr//td[3]";
    public Label lblValueTotalOfAsset = Label.xpath("//following::span[text()='Total Assets']/parent::div/following-sibling::div/span");
    public Label lblTotalOfAsset = Label.xpath("//div[contains(@class,'total-by-group-account')][2]");
    public Label lblValueTotalLiabilityCapital = Label.xpath("//span[contains(text(),'Total Liability and Capital')]/parent::div/following-sibling::div/span");
    public Label lblAmountAreShow = Label.xpath("//app-balance-sheet//label[contains(text(), 'Amounts are shown in')]");
    public Button btnExportToExcel = Button.xpath("//button//i[contains(@class, 'fa-file-excel')]");
    public Button btnExportToPDF = Button.xpath("//button//i[contains(@class, 'fa-file-pdf')]");
    @Override
    public String getTitlePage() { return lblTitle.getText().trim();}

    public void filter(String companyUnit, String financialYear, String month, String reportType) {
        if (!companyUnit.isEmpty()){
            ddCompanyUnit.selectByVisibleText(companyUnit);
        }
        if (!financialYear.isEmpty()){
            ddFinancialYear.selectByVisibleText(financialYear);
        }
        if (!month.isEmpty()){
            ddMonth.selectByVisibleText(month);
        }
        if (!reportType.isEmpty()){
            ddReport.selectByVisibleText(reportType);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public boolean isSectionDisplayCorrect(String sectionName){
        return Label.xpath(String.format(titleSectionXpath,sectionName)).isDisplayed();
    }

    public boolean isParentAccountsDisplayCorrect() {
        List<WebElement> lstDetailType = DriverManager.getDriver().findElements(By.xpath(lstDetailTypeXpath));
        List<String> lstLedgerName = ChartOfAccountUtils.getLstLedgerGroup();
        for(int i = 0; i < lstDetailType.size();i++){
            String detailTypeName = lstDetailType.get(i).getText();
            List<String> lstParentName = new ArrayList<>();
            if (lstLedgerName.contains(detailTypeName)){
                String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(detailTypeName);
                lstParentName = ChartOfAccountUtils.getLstParentName(ledgerGroupId);
                List<String> lstParentInBalance = getLstParentName(detailTypeName);

                for (int j = 0; j < lstParentInBalance.size();j++){
                    if (lstParentInBalance.get(j).contains("Inactive")){
                        System.out.println("Parent Account: "+lstParentInBalance.get(j));
                        break;
                    } else if (lstParentInBalance.contains("PL for Current Year - HKD") && detailTypeName.contains("Retained Earnings")){
                        System.out.println(detailTypeName + " display correct!");
                        break;
                    } else if (!lstParentName.contains(lstParentInBalance.get(j))){
                        System.out.println(lstParentInBalance.get(j)+" be grouded incorrect!");
                        return false;
                    }
                }
            } else {
                System.out.println(lstDetailType.get(i).getText()+" is detail type inactive");
            }
        }
        return true;
    }

    public List<String> getLstParentName(String detailTypeName){
        List<String> lstParent = new ArrayList<>();
        List<WebElement> lstParentName = DriverManager.getDriver().findElements(By.xpath(String.format(parentNameXpath,detailTypeName)));
        for (int i = 0; i < lstParentName.size();i++){
            lstParent.add(lstParentName.get(i).getText());
        }
        return lstParent;
    }
    public List<String> getLstIdParentAccount(String detailTypeName){
        List<String> lstParent = new ArrayList<>();
        List<WebElement> lstParentName = DriverManager.getDriver().findElements(By.xpath(String.format(idParentAccountsXpath,detailTypeName)));
        for (int i = 0; i < lstParentName.size();i++){
            lstParent.add(lstParentName.get(i).getText());
        }
        return lstParent;
    }
    public List<String> getLstParentValue(String detailTypeName){
        List<String> lstParent = new ArrayList<>();
        List<WebElement> lstParentName = DriverManager.getDriver().findElements(By.xpath(String.format(valueParentAccountsXpath,detailTypeName)));
        for (int i = 0; i < lstParentName.size();i++){
            lstParent.add(lstParentName.get(i).getText());
        }
        return lstParent;
    }

    public ArrayList<String> getDetailTypeNameByAccountType(String accountType){
        ArrayList<String> lstDetail = new ArrayList();
        List<WebElement> lstDetailType = new ArrayList<>();
        switch (accountType){
            case "ASSET":
                lstDetailType = DriverManager.getDriver().findElements(By.xpath(String.format(titleSectionXpath,accountType)
                        +"/parent::div/following-sibling::div[@class='asset-body']//td[@class='text-left']//span"));
                break;
            default:
                lstDetailType = DriverManager.getDriver().findElements(By.xpath(String.format(titleSectionXpath,accountType)
                        +"/parent::div/following-sibling::div[@class='liability-body']//td[@class='text-left']//span"));
        }
        for (int i = 0; i < lstDetailType.size();i++){
            lstDetail.add(lstDetailType.get(i).getText());
        }
        return lstDetail;
    }

    public boolean isDetailTypeSortCorrect(String accountType) {
        ArrayList<String> lstDetailTypeSort = getDetailTypeNameByAccountType(accountType);
        ArrayList<String> lstDetailType = lstDetailTypeSort;
        Collections.sort(lstDetailType, Collections.reverseOrder());
        if (!lstDetailType.equals(lstDetailTypeSort)){
            System.err.println(accountType+" sort incorrect!");
            return false;
        }
        return true;
    }

    public boolean isTotalAmountOfDetailType(String detailTypeName) {
        List<String> lstParentName = getLstParentName(detailTypeName);
        Double totalAmount = checkNegativeValue(getTotalAmount(detailTypeName));
        Double totalAmountOfParent = 0.00;
        for (int i = 0; i < lstParentName.size();i++){
            totalAmountOfParent = totalAmountOfParent + checkNegativeValue(getAmount(lstParentName.get(i)));
        }
        if (!totalAmount.equals(DoubleUtils.roundUpWithTwoPlaces(totalAmountOfParent))){
            System.err.println("Total Amount of "+detailTypeName+" incorrect");
            return false;
        }
        return true;
    }
    public Label getTotalAmount(String detailTypeName){
        return Label.xpath(String.format(amountDetailTypeXpath,detailTypeName));
    }
    private Label getAmount(String parentAccountName){
        return Label.xpath(String.format(amountParentAccountXpath,parentAccountName));
    }
    public boolean isTotalAmountDisplayCorrect(){
        ArrayList<String> lstDetail = getDetailTypeNameByAccountType("ASSET");
        lstDetail.addAll(getDetailTypeNameByAccountType("LIABILITY"));
        lstDetail.addAll(getDetailTypeNameByAccountType("CAPITAL"));
        for (int i = 0; i < lstDetail.size();i++){
            if (!isTotalAmountOfDetailType(lstDetail.get(i))){
                System.out.println(lstDetail.get(i)+" is wrong");
                return false;
            }
        }
        return true;
    }
    public String getTotalOfAllParent(String accountType){
        String totalAccountType = "";
        Double total= 0.00;
        ArrayList<String> lstDetail = getDetailTypeNameByAccountType(accountType);
        for (int i = 0; i < lstDetail.size();i++){
            total = total + checkNegativeValue(getTotalAmount(lstDetail.get(i)));
        }
        return String.format("%.2f",total);
    }
    public Double checkNegativeValue(Label lbl){
        Double numberValue = 0.00;
        if (lbl.getAttribute("Class").contains("negative")){
            numberValue = -Double.valueOf(lbl.getText().replace(",",""));
        } else {
            numberValue = Double.valueOf(lbl.getText().replace(",",""));
        }
        return numberValue;
    }
    public void checkValueCompareExcel(String downloadPath){
        String companyUnit = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",1,1);
        String namePage = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",1,2);
        String rangeTime = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",1,3);
        String titleAsset = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",1,6);
        String titleLiability = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",4,6);
        String assetDetailType = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",1,7);
        String valueAssetDetailType = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",3,7);
        String parentNumAsset = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",1,8);
        String parentNameAsset = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",2,8);
        String parentValueAsset = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",3,8);
        String liabilityDetailType = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",4,7);
        String valueLiabilityDetailType = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",6,7);
        String parentNumLia = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",4,8);
        String parentNameLia = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",5,8);
        String parentValueLia = ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Balance Sheet",6,8);

        Assert.assertEquals(companyUnit, SBPConstants.COMPANY_UNIT,"FAILED! Company unit displays incorrect");
        Assert.assertEquals(namePage, SBPConstants.BALANCE_SHEET,"FAILED! Page name displays incorrect");
        Assert.assertEquals(rangeTime, "01 December 2023 - 31 December 2023","FAILED! Range time displays incorrect");
        Assert.assertEquals(titleAsset, "ASSET","FAILED! Asset title displays incorrect");
        Assert.assertEquals(titleLiability, "LIABILITY","FAILED! Liability title displays incorrect");

        String assetDetailTypeUI = getDetailTypeNameByAccountType("ASSET").get(0);
        String valueAssetDetailTypeUI = getTotalAmount(assetDetailTypeUI).getText();
        String parentNumAssetUI = getLstIdParentAccount(assetDetailTypeUI).get(0);
        String parentNameAssetUI = getLstParentName(assetDetailTypeUI).get(0);
        String parentValueAssetUI = getLstParentValue(assetDetailTypeUI).get(0);
        String liabilityDetailTypeUI = getDetailTypeNameByAccountType("LIABILITY").get(0);
        String valueLiabilityDetailTypeUI = getTotalAmount(liabilityDetailTypeUI).getText();
        String parentNumLiaUI = getLstIdParentAccount(liabilityDetailTypeUI).get(0);
        String parentNameLiaUI = getLstParentName(liabilityDetailTypeUI).get(0);
        String parentValueLiaUI = getLstParentValue(liabilityDetailTypeUI).get(0);

        Assert.assertEquals(assetDetailType, assetDetailTypeUI,"FAILED! Asset detail type name displays incorrect");
        Assert.assertTrue(valueAssetDetailType.contains(valueAssetDetailTypeUI),"FAILED! Value asset detail typedisplays incorrect");
        Assert.assertEquals(parentNumAsset, parentNumAssetUI,"FAILED! parent number ID Asset displays incorrect");
        Assert.assertEquals(parentNameAsset, parentNameAssetUI,"FAILED! parent name asset displays incorrect");
        Assert.assertTrue(parentValueAsset.contains(parentValueAssetUI),"FAILED! parent value asset displays incorrect");
        Assert.assertEquals(liabilityDetailType, liabilityDetailTypeUI,"FAILED! Liability detail type name displays incorrect");
        Assert.assertTrue(valueLiabilityDetailType.contains(valueLiabilityDetailTypeUI),"FAILED! Value Liability detail typedisplays incorrect");
        Assert.assertEquals(parentNumLia, parentNumLiaUI,"FAILED! parent number ID Liability displays incorrect");
        Assert.assertEquals(parentNameLia, parentNameLiaUI,"FAILED! parent name Liability displays incorrect");
        Assert.assertTrue(parentValueLia.contains(parentValueLiaUI),"FAILED! parent value Liability displays incorrect");
    }

    public boolean isParentAccountDisplayCorrect(List<String> lstParentAccount, List<Map<String, String>> actualExcelData, List<String> columExcel, int indexExcelColumn) {
        for (int i = 0; i < lstParentAccount.size();i++){
            if (!lstParentAccount.get(i).equals(actualExcelData.get(i).get(columExcel.get(indexExcelColumn)))){
                System.out.println(lstParentAccount.get(i)+" is wrong");
                return false;
            }
        }
        return true;
    }
}
