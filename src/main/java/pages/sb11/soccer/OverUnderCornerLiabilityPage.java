package pages.sb11.soccer;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import pages.sb11.WelcomePage;

public class OverUnderCornerLiabilityPage extends WelcomePage {
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
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-days-calendar-view");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-days-calendar-view");
    public DropDownBox ddpSport = DropDownBox.xpath("//div[contains(text(),'Sport')]//following::select[1]");
    public Label lblPTBets = Label.xpath("//span[contains(text(),'PT-Bets')]");
    public Label lblFromDate = Label.xpath("//div[contains(text(),'From Date')]");
    public Label lblToDate = Label.xpath("//div[contains(text(),'To Date')]");

    public Label lblShowBetType = Label.xpath("//div[contains(text(),'Show Bet Types')]");
    public Label lblShowLeagues = Label.xpath("//div[contains(text(),'Show Leagues')]");
    public Label lblShowGroups = Label.xpath("//div[contains(text(),'Show Groups')]");
    public Label lblShowEvents = Label.xpath("//div[contains(text(),'Show Events')]");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public Button btnShowGroups = Button.xpath("//div[contains(text(),'Show Groups')]");
    public Button btnSetSelection = Button.xpath("//button[contains(text(),'Set Selection')]");
    public Table tblOrder = Table.xpath("//app-over-under-corner-liability//table",6);

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

    public void filterGroups(String groupCode){
        CheckBox cbGroup = CheckBox.xpath("//div[contains(@class,'card-columns')]//span[text()='"+groupCode+"']//preceding::input[1]");
        btnShowGroups.click();
        waitSpinnerDisappeared();
        cbGroup.click();
        btnSetSelection.click();
        btnShow.click();
    }

    public boolean isOrderExist (Order order, String groupCode){
        int rowIndex = getRowWithEventName(groupCode,order.getHome(),order.getAway());
        while(true){
            if(!tblOrder.getControlOfCell(1,colEvent,rowIndex,null).isDisplayed()) {
                System.out.println("Not found order in the table");
                return false;
            }
            String eventName = tblOrder.getControlOfCell(1,colEvent,rowIndex,null).getText();
            if(eventName.contains(order.getHome()) && (eventName.contains(order.getAway())))
                return true;
            rowIndex = rowIndex + 1;
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
}
