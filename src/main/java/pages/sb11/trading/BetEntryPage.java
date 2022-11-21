package pages.sb11.trading;
import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import pages.sb11.Header;

public class BetEntryPage extends Header {
    private Label lblTitle = Label.xpath("//app-bet-entry//div[contains(@class,'card-header')]//span");
    private Label lblGoto = Label.xpath("//app-bet-entry//div[contains(@class,'card-body')]//div[1]");
    private Button btnSoccer = Button.xpath("//app-bet-entry//div[contains(@class,'card-body')]//div[2]");
    private Button btnCricket = Button.xpath("//app-bet-entry//div[contains(@class,'card-body')]//div[3]");
    private Button btnMixedSports = Button.xpath("//app-bet-entry//div[contains(@class,'card-body')]//div[4]");

    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public SoccerBetEntryPage goToSoccer(){
        btnSoccer.click();
        return new SoccerBetEntryPage();
    }
    public CricketBetEntryPage goToCricket(){
        btnCricket.click();
        return new CricketBetEntryPage();
    }
    public ManualBetBetEntryPage goToMixedSports(){
        btnMixedSports.click();
        return new ManualBetBetEntryPage();
    }
}
