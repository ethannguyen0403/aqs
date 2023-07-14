package pages.sb11.master.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;

public class UpdatePTPopup {
    Label lblTitle = Label.xpath("//app-update-pt-percent//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public TextBox txtSoccerLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Soccer')]//following::input[1]");
    public TextBox txtSoccerNonLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Soccer')]//following::input[2]");
    public TextBox txtTennisLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Tennis')]//following::input[1]");
    public TextBox txtTennisNonLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Tennis')]//following::input[2]");
    public TextBox txtBasketballLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Basketball')]//following::input[1]");
    public TextBox txtBasketballNonLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Basketball')]//following::input[2]");
    public TextBox txtFootballLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Football')]//following::input[1]");
    public TextBox txtFootballNonLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Football')]//following::input[2]");
    public TextBox txtOthersLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Others')]//following::input[1]");
    public TextBox txtOthersNonLive = TextBox.xpath("//app-update-pt-percent//td[contains(text(),'Others')]//following::input[2]");

    public Button btnSubmit = Button.xpath("//app-update-pt-percent//button[contains(text(),'Submit')]");

    public void updatePT(String sport, String livePT, String nonLivePT, boolean isSubmit){
        switch (sport){
            case "Soccer":
                if (!livePT.isEmpty())
                    txtSoccerLive.sendKeys(livePT);
                if (!nonLivePT.isEmpty())
                    txtSoccerNonLive.sendKeys(nonLivePT);
                return;
            case "Tennis":
                if (!livePT.isEmpty())
                    txtTennisLive.sendKeys(livePT);
                if (!nonLivePT.isEmpty())
                    txtTennisNonLive.sendKeys(nonLivePT);
                return;
            case "Basketball":
                if (!livePT.isEmpty())
                    txtBasketballLive.sendKeys(livePT);
                if (!nonLivePT.isEmpty())
                    txtBasketballNonLive.sendKeys(nonLivePT);
                return;
            case "Football":
                if (!livePT.isEmpty())
                    txtFootballLive.sendKeys(livePT);
                if (!nonLivePT.isEmpty())
                    txtFootballNonLive.sendKeys(nonLivePT);
                return;
            case "Others":
                if (!livePT.isEmpty())
                    txtOthersLive.sendKeys(livePT);
                if (!nonLivePT.isEmpty())
                    txtOthersNonLive.sendKeys(nonLivePT);
                return;
        }
        if(isSubmit)
            btnSubmit.click();
    }
}
