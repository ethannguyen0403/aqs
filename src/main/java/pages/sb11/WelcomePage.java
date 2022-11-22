package pages.sb11;


import com.paltech.element.common.Label;

public class WelcomePage extends Header{
    Label lblSpin = Label.xpath("//div[contains(@class,'la-ball-clip-rotate')]");
    private Label lblTitle = Label.xpath("//span[@class='text-white']");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public void waitPageLoad(){
        lblSpin.isDisplayed(3);
    }
}
