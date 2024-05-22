package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Label;
import controls.Table;
import org.openqa.selenium.WebDriver;

public class Last12DaysPerformancePage {
    Label lblTitle = Label.xpath("//app-smart-group-performance//div[contains(@class,'main-box-header')]//span[1]");
    Label lblTableHeader = Label.xpath("//app-smart-group-performance//div[(@class='p-2')]//span");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public String getTableHeader (){
        return lblTableHeader.getText().trim();
    }

    public Table tblPBM = Table.xpath("//app-smart-group-performance//table",12);
    public void closePopup(){
        DriverManager.getDriver().switchToWindow();
    }
}
