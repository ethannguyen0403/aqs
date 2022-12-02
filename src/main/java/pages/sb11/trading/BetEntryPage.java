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
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public Button btnSoccer = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Soccer')]");
    public Button btnCricket = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Cricket')]");
    public Button btnMixedSport = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Mixed Sports')]");
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
        return new CricketBetEntryPage();
    }
    public ManualBetBetEntryPage goToMixedSports(){
        btnMixedSport.click();
        return new ManualBetBetEntryPage();
    }
    public <T> T goToSport(String sport,  Class<T> expectedPage) {
        ddbSport.selectByVisibleText(sport);
        return PageFactory.initElements(DriverManager.getDriver(), expectedPage);
    }

}
