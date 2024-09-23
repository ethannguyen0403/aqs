package pages.sb11.soccer;

import com.paltech.element.common.*;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import utils.sb11.CompanySetUpUtils;

import java.util.List;

import static common.SBPConstants.COMPANY_UNIT_LIST_ALL;
import static common.SBPConstants.STAKE_LIST;

public class HandicapLiabilityPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpSmartType = DropDownBox.xpath("//div[contains(text(),'Smart Type')]//following::select[1]");
    public CheckBox cbPTBets = CheckBox.xpath("//div[contains(text(),'Show Only')]//following::input[1]");
    public DropDownBox ddpLiveNonLive = DropDownBox.xpath("//div[contains(text(),'Live/NonLive')]//following::select[1]");
    public DropDownBox ddpStake = DropDownBox.xpath("//div[contains(text(),'Stake')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public Label lblPTBets = Label.xpath("//span[contains(text(),'PT-Bets')]");
    public Label lblFromDate = Label.xpath("//label[contains(text(),'From Date')]");
    public Label lblToDate = Label.xpath("//label[contains(text(),'To Date')]");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-days-calendar-view");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-days-calendar-view");

    public Label lblShowBetType = Label.xpath("//div[contains(text(),'Show Bet Types')]");
    public Label lblShowLeagues = Label.xpath("//div[contains(text(),'Show Leagues')]");
    public Label lblShowGroups = Label.xpath("//div[contains(text(),'Show Groups')]");
    public Label lblShowEvents = Label.xpath("//div[contains(text(),'Show Events')]");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Button btnShowGroups = Button.xpath("//div[contains(text(),'Show Groups')]");
    public Button btnSetSelection = Button.xpath("//button[contains(text(),'Set Selection')]");
    public Button btnClearAll = Button.xpath("//button[text()='Clear All']");
    public Table tblOrder = Table.xpath("//app-handicap-liability//table",15);
    public Table tbOrderByGroup;
    int colEvent = 1;


    public void filterResult(String companyUnit, String smartType, boolean isPTBets, String liveNonLive, String fromDate, String toDate, String stake, boolean isShow){
        lblTitle.click();
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpSmartType.selectByVisibleText(smartType);
        if (isPTBets){
            cbPTBets.click();
        }
        ddpLiveNonLive.selectByVisibleText(liveNonLive);
        if(!fromDate.isEmpty())
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        if(!toDate.isEmpty())
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        ddpStake.selectByVisibleText(stake);
        if (isShow){
            btnShow.click();
        }
        waitSpinnerDisappeared();
    }

    public void filterGroups(String groupCode, boolean show){
        btnShowGroups.click();
        waitSpinnerDisappeared();
        CheckBox cbGroup = CheckBox.xpath("//div[contains(@class,'card-columns')]//span[text()='"+groupCode+"']//preceding::input[1]");
        cbGroup.jsClick();
        btnSetSelection.click();
        if (show){
            btnShow.click();
            waitSpinnerDisappeared();
        }
    }

    public boolean isOrderExist (List<Order> lstOrder, String groupCode){
        int rowIndex = getRowWithEventName(groupCode,lstOrder.get(0).getEvent().getHome(),lstOrder.get(0).getEvent().getAway());
        switch (rowIndex){
            case 0:
                return false;
            default:
                return true;
        }
    }

    public void defineTableBasedOnGroups (String groupCode){
        tbOrderByGroup = Table.xpath("//span[contains(text(),'" + groupCode + "')]//following::table[1]",6);
    }

    public int getRowWithEventName(String groupCode, String homeTeam, String awayTeam){
        int i = 1;
        Label lblEventName;
        defineTableBasedOnGroups(groupCode);
        while (true){
            lblEventName = Label.xpath(tbOrderByGroup.getxPathOfCell(1,colEvent,i,null));
            if(!lblEventName.isDisplayed()){
                System.out.println("Event "+ homeTeam + " vs " + awayTeam + " does not display in the list");
                return 0;
            }
            if(lblEventName.getText().contains(homeTeam) && (lblEventName.getText().contains(awayTeam)))
                return i;
            i = i +1;
        }
    }

    public void verifyUI() {
        System.out.println("Company Unit, Smart Type, Show Only PT-Bets,Live/NonLive, From Date, To Date, Stake");
        List<String> lstCompany = CompanySetUpUtils.getListCompany();
        lstCompany.add(0,"All");
        Assert.assertEquals(ddpCompanyUnit.getOptions(),lstCompany,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(ddpSmartType.getOptions(), SBPConstants.MatchOddsLiability.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(lblPTBets.getText(),"PT-Bets","Failed! PT Bets checkbox is not displayed");
        Assert.assertEquals(lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        Assert.assertEquals(ddpStake.getOptions(),STAKE_LIST,"Failed! Stake dropdown is not displayed");
        System.out.println("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertEquals(lblShowBetType.getText(),"Show Bet Types\nAll","Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(lblShowLeagues.getText().contains("Show Leagues"),"Failed! Show Leagues button is not displayed");
        Assert.assertEquals(lblShowGroups.getText(),"Show Groups\nAll","Failed! Show Groups button is not displayed");
        Assert.assertEquals(lblShowEvents.getText(),"Show Events\nAll","Failed! Show Events button is not displayed");
        Assert.assertEquals(btnShow.getText(),"SHOW","Failed! Show button is not displayed");
        System.out.println("Event table header columns is correctly display");
        Assert.assertEquals(tblOrder.getHeaderNameOfRows(), SBPConstants.HandicapLiability.TABLE_HEADER,"FAILED! Handicap Liability Bets table header is incorrect display");
    }
    public void showLeagues(boolean show, boolean filterAll, String... leagueName) {
        lblShowLeagues.click();
        //Wait for showing list because there is not loading icon
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        btnClearAll.click();
        if (!filterAll){
            for(String option: leagueName){
                selectOptionOnFilter(option, true);
            }
        }
        btnSetSelection.click();
        waitSpinnerDisappeared();
        if (show){
            btnShow.click();
            waitSpinnerDisappeared();
        }
    }
    public void selectOptionOnFilter(String optionName, boolean isChecked) {
        CheckBox chkOption = CheckBox.xpath(String.format("//th[.=\"%s\"]/preceding-sibling::th[1]/input", optionName));
        if (isChecked) {
            if (!chkOption.isSelected()) {
                chkOption.select();
            }
        } else {
            if (chkOption.isSelected())
                chkOption.deSelect();
        }
    }
}
