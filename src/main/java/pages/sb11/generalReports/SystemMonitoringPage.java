package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Label;
import com.paltech.element.common.Tab;
import org.openqa.selenium.support.PageFactory;
import pages.sb11.WelcomePage;

public class SystemMonitoringPage extends WelcomePage {
    String tabNameXpath = "//ul[contains(@class,'site-menu')]//li//a[text()='%s']";
    Label lblTitle = Label.xpath("//app-system-monitoring//span[contains(@class,'text-white')]");
    public <T> T goToTabName(String tabName, Class<T> expectedTab) {
        lblTitle.moveAndHoverOnControl();
        clickToTabName(tabName);
        //wait for page display
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return PageFactory.initElements(DriverManager.getDriver(), expectedTab);
    }
    public void clickToTabName(String tabName){
        Tab tabPage = Tab.xpath(String.format(tabNameXpath,tabName));
        if (!tabPage.isDisplayed()){
            System.out.println(tabName+" is not displayed!");
        }
        tabPage.click();
    }
}
