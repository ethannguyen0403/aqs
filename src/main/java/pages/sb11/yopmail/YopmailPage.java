package pages.sb11.yopmail;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.TextBox;
import org.openqa.selenium.Keys;

public class YopmailPage {
    public TextBox txtEmail = TextBox.xpath("//input[@class='ycptinput']");
    public YopmailMailBoxPage navigateMailBox(String emailAddress){
        txtEmail.waitForControlInvisible(5,1);
        txtEmail.sendKeys(emailAddress);
        txtEmail.type(false, Keys.ENTER);
        YopmailMailBoxPage yopmailMailBoxPage = new YopmailMailBoxPage();
        DriverManager.getDriver().switchToFrame(0);
        return yopmailMailBoxPage;
    }

}
