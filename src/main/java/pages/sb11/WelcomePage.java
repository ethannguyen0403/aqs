package pages.sb11;


import com.paltech.element.common.Label;
import controls.sb11.AppArlertControl;

public class WelcomePage extends Header{
    Label lblSpin = Label.xpath("//div[contains(@class,'la-ball-clip-rotate')]");
    private Label lblTitle = Label.xpath("//span[@class='text-white']");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public AppArlertControl appArlertControl = AppArlertControl.xpath("//app-alert//div[@class='message-box']");

    public void waitPageLoad(){
        lblSpin.isDisplayed(3);
    }

    public String getSuccessMessage(){
        return appArlertControl.getSuscessMessage();
    }
}
