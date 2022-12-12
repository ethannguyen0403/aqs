package pages.sb11.trading.controls;

import com.paltech.element.BaseElement;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.RadioButton;
import com.paltech.element.common.TextBox;
import org.openqa.selenium.By;


public class OrderRowControl extends BaseElement {
    //app-match-odd-bet-slip//div[contains(@class,'place-form ')]//div[contains(@class,'order-row')][1]
    private String _xpath;
    public Label lblHandicap;
    public RadioButton rbNegativeHdp;
    public Label lblNegativeHdp;
    public RadioButton rbPositiveHdp;
    public Label lblPositiveHdp;
    public DropDownBox ddbHdpRate;
    public TextBox txtPrice;
    public DropDownBox ddbOddType;
    public DropDownBox ddbBetType;
    public TextBox txtLiveScoreHome;
    public TextBox txtLiveScoreAway;
    public  TextBox txtStake;

    private OrderRowControl(By locator, String xpathExpression) {
        super(locator);
        this._xpath = xpathExpression;
        lblHandicap = Label.xpath(String.format("%s/label",_xpath));
        rbNegativeHdp = RadioButton.xpath(String.format("%s//input[contains(@id,'neghdp')]",_xpath));
        lblNegativeHdp = Label.xpath(String.format("%s//label[contains(@for,'neghdp')]",_xpath));
        rbPositiveHdp = RadioButton.xpath(String.format("%s//input[contains(@id,'poshdp')]",_xpath));
        lblPositiveHdp = Label.xpath(String.format("%s//label[contains(@for,'poshdp')]",_xpath));
        ddbHdpRate = DropDownBox.xpath(String.format("%s/div/div[1]/div[3]/select",_xpath));
        txtPrice= TextBox.xpath(String.format("%s/div/div[1]/div[4]/input",_xpath));
        ddbOddType = DropDownBox.xpath(String.format("%s//div[@id='odd-type-selector']/select",_xpath));
        ddbBetType= DropDownBox.xpath(String.format("%s//div[@id='bet-type-selector']/select",_xpath));
        txtLiveScoreHome= TextBox.xpath(String.format("%s/div/div[2]/div[1]/input",_xpath));
        txtLiveScoreAway= TextBox.xpath(String.format("%s/div/div[2]/div[2]/input",_xpath));
        txtStake= TextBox.xpath(String.format("%s/div/div[2]/div[3]/input",_xpath));
    }

    public static OrderRowControl xpath(String xpathExpression) {
        return new OrderRowControl(By.xpath(xpathExpression), xpathExpression);
    }

    public void inputInfo(boolean isNegativeHdp,double hdpRate, double price, String oddsType, String betType, int liveHomeScore, int liveAwayScore, double stake){
        if(isNegativeHdp)
            rbNegativeHdp.click();
        else rbPositiveHdp.click();
        ddbHdpRate.selectByVisibleText(String.format("%.2f",hdpRate));
        txtPrice.sendKeys(String.format("%.3f",price));
        ddbOddType.selectByVisibleText(oddsType);
        ddbBetType.selectByVisibleText(betType);
        txtLiveScoreHome.sendKeys(String.format("%d",liveHomeScore));
        txtLiveScoreAway.sendKeys(String.format("%d",liveAwayScore));
        txtStake.sendKeys(String.format("%.2f",stake));
    }



}
