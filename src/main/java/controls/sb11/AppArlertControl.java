package controls.sb11;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Label;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class AppArlertControl extends BaseElement {
    protected String _xpath = null;
    private Label lblSuccessMessage;

    public AppArlertControl(By locator, String xpathExpression) {
        super(locator);
        this._xpath = xpathExpression;
        lblSuccessMessage = Label.xpath(String.format("%s//div[contains(@class,'alert-success')]",_xpath));
    }

    public static AppArlertControl xpath(String xpathExpression) {
        return new AppArlertControl(By.xpath(xpathExpression),xpathExpression);
    }
    public String getSuscessMessage(){
        return lblSuccessMessage.getText();
    }

}
