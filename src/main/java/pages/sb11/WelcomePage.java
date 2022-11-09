package pages.sb11;


import com.paltech.element.common.Label;

public class WelcomePage extends Header{
    private Label lblTitle = Label.xpath("//span[@class='text-white']");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

}
