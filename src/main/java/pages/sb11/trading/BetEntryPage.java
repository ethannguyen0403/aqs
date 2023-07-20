package pages.sb11.trading;

import com.paltech.driver.DriverManager;
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

import static org.apache.commons.lang3.BooleanUtils.and;

public class BetEntryPage extends WelcomePage {
    Label lblTitle = Label.xpath("//app-bet-entry-header//div[contains(@class,'main-box-header')]/div[1]/span");
//    public Button btnSoccer = Button.xpath("//app-bet-entry//span[contains(text(),'Soccer')]");
    public Button btnSoccer = Button.name("btnSoccer");
    public Button btnCricket = Button.name("btnCricket");
    public Button btnMixedSport = Button.name("btnMixedSports");
    private DropDownBox ddbSport = DropDownBox.id("navigate-page");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public SoccerBetEntryPage goToSoccer(){
        btnSoccer.click();
        waitPageLoad();
        return new SoccerBetEntryPage();
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

}
