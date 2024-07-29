package pages.sb11.generalReports;

import com.beust.ah.A;
import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.DropDownList;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.winlossDetail.MemberTransactionsPopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WinLossDetailPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//app-winloss-detail//div[contains(@class,'card-header')]//span[contains(@class,'text-white')]");
    DropDownList ddProduct = DropDownList.xpath("//label[text()='Product']//following-sibling::div//div[@class='selected-list']","//following-sibling::div[contains(@class,'dropdown-list')]//li");
    DropDownList ddPortal = DropDownList.xpath("//label[text()='Portal']//following-sibling::div//div[@class='selected-list']","//following-sibling::div[contains(@class,'dropdown-list')]//li");
    DropDownBox ddTypeCurrency = DropDownBox.xpath("//label[text()='Type Currency']//following-sibling::select");
    TextBox txtFromDate= TextBox.name("from-date");
    TextBox txtToDate= TextBox.name("to-date");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    Button btnToday = Button.xpath("//span[text()='Today']//parent::button");
    Button btnYesterday = Button.xpath("//span[text()='Yesterday']//parent::button");
    Button btnLastWeek = Button.xpath("//span[text()='Last Week']//parent::button");
    Button btnShow = Button.xpath("//span[text()='Show']//parent::button");
    Button btnDownloadExport = Button.xpath("//span[text()='Download Export File']//parent::button");
    Table tblData = Table.xpath("//table",28);

    /**
     *
     * @param product
     * @param portal
     * @param typeCurrency
     * @param fromDate
     * @param toDate
     * @param buttonClick input Today,Yesterday, Last Week, Show
     */
    public void filter(String portal, String typeCurrency, String fromDate, String toDate, String buttonClick,String... product) {
        if (!(product.length==0)){
            selectProduct(product);
        }
        if (!portal.isEmpty()) {
            ddPortal.clickMenu(portal);
        }
        if (!typeCurrency.isEmpty()){
            ddTypeCurrency.selectByVisibleText(typeCurrency);
        }
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        }
        switch (buttonClick){
            case "Today":
                btnToday.click();
                break;
            case "Yesterday":
                btnYesterday.click();
                break;
            case "Last Week":
                btnLastWeek.click();
                break;
            case "Show":
                btnShow.click();
                break;
            default:
                System.err.println("Do not click any search button");
        }
        waitSpinnerDisappeared();
    }
    private void selectProduct(String... product){
        for (String product1 : product){
            if (!Label.xpath(String.format("//label[text()='Product']/following-sibling::div//label[text()='%s']/parent::li[contains(@class,'selected-item')]",product1)).isPresent()){
                ddProduct.clickMenu(product1);
            }
        }
    }

    public void verifyDataInBOSite(String env) {
        switch (env){
            case "stg":
                List<String> fairData = Arrays.asList("1","AN\nAN","FairExchange","Portal","HKD","14","44.99","44.99","-12.42","-12.42","0.00","0.00","-12.42","-12.42","0.00","0.00","0.00","0.00","0.00","0.00",
                        "3.29","3.29","0.00","0.00","3.29","3.29","1.67","1.67");
                List<String> funsData = Arrays.asList("2","FS","FunSport101","Portal","HKD","1","2.00","2.00","2.00","2.00","-0.02","-0.02","1.98","1.98","0.00","0.00","0.00","0.00","0.00","0.00",
                        "0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00");
                List<String> lolData = Arrays.asList("3","SO","LOLexch","Portal","HKD","10","57.76","57.76","3.42","3.42","-0.06","-0.06","3.36","3.36","0.00","0.00","0.00","0.00","0.00","0.00",
                        "0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00");
                List<String> totalData = Arrays.asList("Total","25","104.75","","-7.01","-7.01","-0.08","-0.08","-7.08","-7.08","0.00","0.00","0.00","0.00","0.00","0.00","3.29","3.29","0.00","0.00",
                        "3.29","3.29","1.67","1.67","","","","");
                List<ArrayList<String>> lstData = tblData.getRowsWithoutHeader(true);
                Assert.assertEquals(lstData.get(0),fairData,"FAILED! Fair Exchange data display incorrect");
                Assert.assertEquals(lstData.get(1),funsData,"FAILED! Fun Sport 101 data display incorrect");
                Assert.assertEquals(lstData.get(2),lolData,"FAILED! LOL data display incorrect");
                Assert.assertEquals(lstData.get(3),totalData,"FAILED! Total data display incorrect");
                break;
            case "pro":
                List<String> fairenterData = Arrays.asList("1","FA","Fairenter","Portal","HKD","4,001","18,958,126.87","18,958,126.87","1,613,245.75","1,613,245.75","-208,259.93","-208,259.93","1,404,985.82","1,404,985.82","-581,004.64","-581,004.64","44,698.62","44,698.62","-536,306.03","-536,306.03",
                        "112,449.17","112,449.17","27,091.76","27,091.76","139,540.93","139,540.93","-141,685.98","-141,685.98");
                List<String> fairExData = Arrays.asList("2","FB","FairExchange","Portal","HKD","3,249","6,808,063.60","6,808,063.60","388,901.70","388,901.70","-85,167.97","-85,167.97","303,733.73","303,733.73","55,734.63","55,734.63","14,486.34","14,486.34","70,220.97","70,220.97",
                        "-26,585.57","-26,585.57","2,526.72","2,526.72","-24,058.85","-24,058.85","-119,206.72","-119,206.72");
                List<String> funData = Arrays.asList("3","FS","FunSport","Portal","HKD","11,584","1,250,942.28","1,250,942.28","39,931.84","39,931.84","-17,850.11","-17,850.11","22,081.74","22,081.74","7,162.68","7,162.68","1,259.53","1,259.53","8,422.21","8,422.21",
                        "-2,912.11","-2,912.11","5,066.83","5,066.83","2,154.71","2,154.71","164.79","164.79");
                List<String> gloryData = Arrays.asList("4","G8","Glory88","Portal","HKD","141","8,711.63","8,711.63","-2,578.63","-2,578.63","19.13","19.13","-2,559.50","-2,559.50","0.00","0.00","0.00","0.00","0.00","0.00",
                        "0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00");
                List<String> ipl88Data = Arrays.asList("5","IP","IPL688","Portal","HKD","33","334,200.60","334,200.60","-41,522.82","-41,522.82","0.00","0.00","-41,522.82","-41,522.82","0.00","0.00","0.00","0.00","0.00","0.00",
                        "0.00","0.00","0.00","0.00","0.00","0.00","36,554.51","36,554.51");
                List<String> loLData = Arrays.asList("6","LO","LOLexch","Portal","HKD","74","2,652,647.57","2,652,647.57","207,804.18","207,804.18","-5,468.36","-5,468.36","202,335.81","202,335.81","0.00","0.00","0.00","0.00","0.00","0.00",
                        "0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00");
                List<String> satData = Arrays.asList("7","SA","SAT Sport","Portal","HKD","540,965","56,790,699.46","56,790,699.46","-2,770,218.87","-2,770,218.87","-78,225.77","-78,225.77","-2,848,444.64","-2,848,444.64","0.00","0.00","0.00","0.00","0.00","0.00",
                        "0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00");
                List<String> sunData = Arrays.asList("8","W2","Sunexch365","Portal","HKD","163","7,362,100.60","7,362,100.60","378,034.13","378,034.13","-16,986.40","-16,986.40","361,047.73","361,047.73","0.00","0.00","0.00","0.00","0.00","0.00",
                        "-251,214.24","-251,214.24","0.00","0.00","-251,214.24","-251,214.24","-10,676.87","-10,676.87");
                List<String> layData = Arrays.asList("9","W3","LayStars","Portal","HKD","1,640","1,982,188.85","1,982,188.85","-355,114.25","-355,114.25","-21,951.90","-21,951.90","-377,066.15","-377,066.15","88,754.78","88,754.78","7,330.18","7,330.18","96,084.96","96,084.96",
                        "90,757.96","90,757.96","2,176.11","2,176.11","92,934.07","92,934.07","3,876.34","3,876.34");
                List<String> winData = Arrays.asList("10","W5","WinFair24","Portal","HKD","586","867,527.54","867,527.54","-18,208.84","-18,208.84","-3,194.05","-3,194.05","-21,402.89","-21,402.89","25,247.67","25,247.67","789.94","789.94","26,037.61","26,037.61",
                        "-4,510.34","-4,510.34","61.14","61.14","-4,449.20","-4,449.20","0.00","0.00");
                List<String> proData = Arrays.asList("11","Y1","Pro4Odd","Portal","HKD","127","611,432.21","611,432.21","4,965.96","4,965.96","-3,427.20","-3,427.20","1,538.76","1,538.76","0.00","0.00","2,003.83","2,003.83","2,003.83","2,003.83",
                        "0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00");
                List<String> gameData = Arrays.asList("12","Y2","Game2Bet","Portal","HKD","2","714.62","714.62","-714.62","-714.62","0.00","0.00","-714.62","-714.62","571.70","571.70","0.00","0.00","571.70","571.70",
                        "0.00","0.00","0.13","0.13","0.13","0.13","0.00","0.00");
                List<String> totaLData = Arrays.asList("Total","562,565","97,627,355.83","","-555,474.48","-555,474.48","-440,512.56","-440,512.56","-995,987.04","-995,987.04","-403,533.20","-403,533.20","70,568.44","70,568.44","-332,964.76","-332,964.76","-82,015.14","-82,015.14","36,922.69","36,922.69",
                        "-45,092.45","-45,092.45","-230,973.93","-230,973.93","37,917.54","37,917.54","-193,056.38","-193,056.38");
                List<ArrayList<String>> listData = tblData.getRowsWithoutHeader(true);
                Assert.assertEquals(listData.get(0),fairenterData,"FAILED! Fairenter data display incorrect");
                Assert.assertEquals(listData.get(1),fairExData,"FAILED! FairExchange data display incorrect");
                Assert.assertEquals(listData.get(2),funData,"FAILED! FunSport data display incorrect");
                Assert.assertEquals(listData.get(3),gloryData,"FAILED! Glory88 data display incorrect");
                Assert.assertEquals(listData.get(4),ipl88Data,"FAILED! IPL688 data display incorrect");
                Assert.assertEquals(listData.get(5),loLData,"FAILED! LOL data display incorrect");
                Assert.assertEquals(listData.get(6),satData,"FAILED! SAT Sport data display incorrect");
                Assert.assertEquals(listData.get(7),sunData,"FAILED! Sunexch365 data display incorrect");
                Assert.assertEquals(listData.get(8),layData,"FAILED! LayStars data display incorrect");
                Assert.assertEquals(listData.get(9),winData,"FAILED! WinFair24 data display incorrect");
                Assert.assertEquals(listData.get(10),proData,"FAILED! Pro4Odd data display incorrect");
                Assert.assertEquals(listData.get(11),gameData,"FAILED! Game2Bet data display incorrect");
                Assert.assertEquals(listData.get(12),totaLData,"FAILED! Total data display incorrect");
                break;
            default:
                System.err.println("There is not this env");
        }

    }

    public MemberTransactionsPopup openMemberTransDialog(String brand, String level, String username) {
        int rowIndex = tblData.getRowIndexContainValue(username,tblData.getColumnIndexByName("User Name"),"span");
        String levelAc = Label.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Level"),rowIndex,null)).getText().trim();
        String brandAc = Label.xpath(tblData.getxPathOfCell(1,tblData.getColumnIndexByName("Brand"),rowIndex,null)).getText().trim();
        if (levelAc.equals(level) && brandAc.equals(brand)){
            tblData.getControlOfCell(1,tblData.getColumnIndexByName("Total Wager"),rowIndex,null).click();
            waitSpinnerDisappeared();
            waitSpinnerDisappeared();
        }
        return new MemberTransactionsPopup();
    }
}
