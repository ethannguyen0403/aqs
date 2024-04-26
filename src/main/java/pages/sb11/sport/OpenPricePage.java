package pages.sb11.sport;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.DropDownList;
import controls.Table;
import pages.sb11.WelcomePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenPricePage extends WelcomePage {
    int colEvent = 3;
    int colFT12 = 4;
    int colFTHDP = 5;
    int colFTOU = 6;
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public TextBox txtDate = TextBox.name("dp");
    public Label lblDate = Label.xpath("//label[(text()='Date')]");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public Button btnShowLeagues = Button.xpath("//button[text()='Show Leagues']");
    public Button btnShow = Button.xpath("//button[text()='Show']");
    public Button btnSubmit = Button.xpath("//button[contains(text(),'Submit')]");
    public Button btnLeague = Button.xpath("//div[contains(@class, 'col-league-width')]//button");
    public Button btnClose = Button.xpath("//em[@class='fas fa-times fa-2x']");
    public Button btnSetSelection = Button.xpath("//button[contains(@class, 'set-selection-btn')]");
    public Button btnSelectAll = Button.xpath("//button[contains(@class, 'select-all-btn')]");
    public DropDownBox ddpLeague = DropDownBox.id("league");
    public Table tbOpenPrice = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[1]",13);
    String cbLeagueXpath = "//label[contains(text(),'%s')]/preceding-sibling::input";

    public void filterResult(String date, String league, boolean isShow){
        if(!date.isEmpty()){
            dtpDate.selectDate(date,"dd/MM/yyyy");
            btnShowLeagues.click();
        }
       if(!league.isEmpty() && !league.equalsIgnoreCase("All")){
           btnLeague.click();
           filterLeague(league);
       }
       if(league.equalsIgnoreCase("All")){
           //select All leagues
           openLeagueFilter();
           btnSelectAll.click();
           btnSetSelection.click();
           waitSpinnerDisappeared();
       }
        if (isShow){
            btnShow.click();
            waitSpinnerDisappeared();
        }
    }
    public String showFirstLeague(String date){
        if(!date.isEmpty()){
            dtpDate.selectDate(date,"dd/MM/yyyy");
            btnShowLeagues.click();
            waitSpinnerDisappeared();
        }
        String league = getFirstLeague();
        selectLeague(league);
        btnSetSelection.click();
        btnShow.click();
        waitSpinnerDisappeared();
        return league;
    }
    public void openLeagueFilter(){
        btnLeague.click();
        try {
            Thread.sleep(1000);
        }catch (Exception e){
        }
    }
    public void selectLeague(String league){
       CheckBox cbLeague = CheckBox.xpath(String.format(cbLeagueXpath,league));
       cbLeague.click();
    }

    public List<String> getAllOptionNameFilter() {
        int indexOption = 1;
        List<String> optionsName = new ArrayList<>();
        while (true) {
            Label lblOption = Label.xpath(String.format("(//div[contains(@class,'list-item-filter')]//label)[%s]", indexOption));
            if (lblOption.isDisplayed()) {
                indexOption++;
                optionsName.add(lblOption.getText().trim());
            }
            if (!lblOption.isDisplayed()) {
                System.out.println("NOT Found value option label with index: " + indexOption);
                return optionsName;
            }
        }
    }

    private void filterLeague(String leagueName) {
        Label lblSelectValue = Label.xpath(String.format("//div[@class='list-item-filter']//label[contains(text(),\"%s\")]",leagueName));
        lblSelectValue.click();
        btnSetSelection.click();
    }

    public boolean isLeagueExist(String leagueName){
        waitSpinnerDisappeared();
        int i = 1;
        Label lblLeague;
        while (true){
            lblLeague = Label.xpath(String.format("//app-open-price//table/tbody/tr[%s]", i));
            if(!lblLeague.isDisplayed()) {
                System.out.println("Can NOT found the league "+leagueName+" in the table");
                return false;
            }
            if(lblLeague.getText().trim().equalsIgnoreCase(leagueName)){
                System.out.println("Found the league "+leagueName+" in the table");
                return true;
            }
            i++;
        }
    }

    public String getFirstLeague() {
        //      List<String> lstLeague = getListLeague();
        openLeagueFilter();
        try {
            // 0 Select, 1 All => get league from index = 2
            List<String> lstLeague = getAllOptionNameFilter();
            return lstLeague.get(0).trim();
        } catch (Exception e) {
            System.out.println("There is NO League on day " + txtDate.getText());
            return null;
        }
    }

    public List<String> getListLeague(){
        return ddpLeague.getOptions();
    }

    public void fillOpenPriceFirstEvent (Map<String, String> openPrice, boolean isSubmit){
        TextBox txtHAHome = TextBox.xpath("(//table//tbody/tr[2]//td[4]//input)[1]");
        TextBox txtHAAway = TextBox.xpath("(//table//tbody/tr[2]//td[4]//input)[2]");
        TextBox txt12Draw = TextBox.xpath("(//table//tbody/tr[2]//td[4]//input)[3]");
        DropDownList ddFTHDPHome  = DropDownList.xpath("(//table//tbody/tr[2]//td[5]//select)[1]","/option");
        DropDownList ddFTHDPAway  = DropDownList.xpath("(//table//tbody/tr[2]//td[5]//select)[2]","/option");
        TextBox txtHDPPriceHome = TextBox.xpath("(//table//tbody/tr[2]//td[5]//input)[1]");
        TextBox txtHDPPriceAway = TextBox.xpath("(//table//tbody/tr[2]//td[5]//input)[2]");
        DropDownList ddFTOUHome  = DropDownList.xpath("(//table//tbody/tr[2]//td[6]//select)[1]","/option");
        DropDownList ddFTOUAway  = DropDownList.xpath("(//table//tbody/tr[2]//td[6]//select)[2]","/option");
        TextBox txtOUPriceHome = TextBox.xpath("(//table//tbody/tr[2]//td[6]//input)[1]");
        TextBox txtOUPriceAway = TextBox.xpath("(//table//tbody/tr[2]//td[6]//input)[2]");

        txtHAHome.sendKeys(openPrice.get("ft12HAHome"));
        txtHAAway.sendKeys(openPrice.get("ft12HAAway"));
        txt12Draw.sendKeys(openPrice.get("ft12Draw"));
        ddFTHDPHome.clickMenu(openPrice.get("ftHDPHome"));
        ddFTHDPAway.clickMenu(openPrice.get("ftHDPAway"));
        txtHDPPriceHome.sendKeys(openPrice.get("ftHDPPriceHome"));
        txtHDPPriceAway.sendKeys(openPrice.get("ftHDPPriceAway"));
        ddFTOUHome.clickMenu(openPrice.get("ftOUHDPHome"));
        ddFTOUAway.clickMenu(openPrice.get("ftOUHDPAway"));
        txtOUPriceHome.sendKeys(openPrice.get("ftOUPriceHome"));
        txtOUPriceAway.sendKeys(openPrice.get("ftOUPriceAway"));
        if (isSubmit){
            btnSubmit.click();
            waitSpinnerDisappeared();
        }
    }
    public String getFirstEvent(){
        return Label.xpath(tbOpenPrice.getxPathOfCell(1,tbOpenPrice.getColumnIndexByName("Event"),2,null)).getText().trim();
    }

    public int getEventRowIndex(String eventName){
        int i = 1;
        Label lblEvent;
        while (true){
            lblEvent = Label.xpath(tbOpenPrice.getxPathOfCell(1,colEvent,i,null));
            if(!lblEvent.isDisplayed()) {
                System.out.println("Can NOT found the ledger name "+eventName+" in the table");
                return 0;
            }
            if (lblEvent.getText().contains(eventName))
                return i;
            i = i +1;
        }
    }
}
