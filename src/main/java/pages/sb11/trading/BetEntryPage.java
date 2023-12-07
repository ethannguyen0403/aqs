package pages.sb11.trading;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import objects.Event;
import objects.Order;
import org.openqa.selenium.support.PageFactory;
import pages.sb11.Header;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.ess.popup.ColumnSettingPopup;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.BetSlipPopup;
import utils.sb11.BetEntrytUtils;
import utils.sb11.GetSoccerEventUtils;

import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.BET_ENTRY;
import static common.SBPConstants.TRADING;
import static org.apache.commons.lang3.BooleanUtils.and;

public class BetEntryPage extends WelcomePage {
    Label lblTitle = Label.xpath("//app-bet-entry-header//div[contains(@class,'main-box-header')]/div[1]/span");
//    public Button btnSoccer = Button.xpath("//app-bet-entry//span[contains(text(),'Soccer')]");
    public Button btnSoccer = Button.name("btnSoccer");
    public Button btnBasketball = Button.name("btnBasketball");
    public Button btnTennis = Button.name("btnTennis");
    public Button btnCricket = Button.name("btnCricket");
    public Button btnMixedSport = Button.name("btnMixedSports");
    private DropDownBox ddbSport = DropDownBox.id("navigate-page");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public SoccerBetEntryPage goToSoccer(){
        btnSoccer.click();
        waitSpinnerDisappeared();
        return new SoccerBetEntryPage();
    }

    public BasketballBetEntryPage goToBasketball(){
        btnBasketball.click();
        waitPageLoad();
        return new BasketballBetEntryPage();
    }
    public TennisBetEntryPage goToTennis(){
        btnTennis.click();
        waitPageLoad();
        return new TennisBetEntryPage();
    }
    public CricketBetEntryPage goToCricket(){
        btnCricket.click();
        waitPageLoad();
        return new CricketBetEntryPage();
    }
    public ManualBetBetEntryPage goToMixedSports(){
        btnMixedSport.click();
        waitPageLoad();
        return new ManualBetBetEntryPage();
    }
    public <T> T goToSport(String sport,  Class<T> expectedPage) {
        ddbSport.selectByVisibleText(sport);
        return PageFactory.initElements(DriverManager.getDriver(), expectedPage);
    }

    public List<Order> placeSoccerBet(String companyUnit,String sportName,String league,int dateNo, String type, boolean isFullTime, boolean isNegativeHdp,double hdpPoint, double price,
                               String oddsType, String betType, int liveHomeScore, int liveAwayScore,double requireStake,String accountCode,
                               String accountCurrency,String marketType, boolean isCopySPBS7SameOdds, boolean isCopySPBPS7MinusOdds) {
        String date = String.format(DateUtils.getDate(dateNo,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(dateNo,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sportName,league);
        SoccerBetEntryPage soccerBetEntryPage = goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,eventInfo.getLeagueName());
        List<Order> lstOrder = new ArrayList<>();
        String stage = "FT";
        if(!isFullTime)
            stage = "HT";
        Order order = new Order.Builder()
                .sport(sportName).isNegativeHdp(isNegativeHdp).hdpPoint(hdpPoint).price(price).requireStake(requireStake)
                .oddType(oddsType).betType(betType).liveHomeScore(liveHomeScore).liveAwayScore(liveAwayScore).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType(marketType)
                .stage(stage)
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),isFullTime,type,lstOrder,isCopySPBS7SameOdds,isCopySPBPS7MinusOdds,true);
       return BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
    }
    public List<Order> placeMoreSoccerBet(String companyUnit,String sportName,String league,int dateNo, String type, boolean isFullTime, boolean isNegativeHdp,double hdpPoint, double price,
                               String oddsType, String betType, int liveHomeScore, int liveAwayScore,double requireStake,String accountCode,
                               String accountCurrency,String marketType, boolean isCopySPBS7SameOdds, boolean isCopySPBPS7MinusOdds) {
        String date = String.format(DateUtils.getDate(dateNo,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(dateNo,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sportName,league);
        SoccerBetEntryPage soccerBetEntryPage = goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,eventInfo.getLeagueName());
        List<Order> lstOrder = new ArrayList<>();
        String stage = "Full Time";
        if(!isFullTime)
            stage = "Haft Time";
        Order order = new Order.Builder()
                .sport(sportName).isNegativeHdp(isNegativeHdp).hdpPoint(hdpPoint).price(price).requireStake(requireStake)
                .oddType(oddsType).betType(betType).liveHomeScore(liveHomeScore).liveAwayScore(liveAwayScore).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType(marketType)
                .stage(stage)
                .selection(type)
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeMoreBet(order,isCopySPBS7SameOdds,isCopySPBPS7MinusOdds,true);
       return BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
    }

}
