package controls.sb11;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Label;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class AppArlertControl extends BaseElement {
    protected String _xpath = null;
    private Label lblSuccessMessage;
    private Label lblWarningMessage;
    private String lstAlertXpath ;

    public AppArlertControl(By locator, String xpathExpression) {
        super(locator);
        this._xpath = xpathExpression;
        lblSuccessMessage = Label.xpath(String.format("%s//div[contains(@class,'alert-success')]/span",_xpath));
        lblWarningMessage = Label.xpath(String.format("%s//div[contains(@class,'alert-danger')]/span",_xpath));
        lstAlertXpath = String.format("%s/div",_xpath);
    }

    public static AppArlertControl xpath(String xpathExpression) {
        return new AppArlertControl(By.xpath(xpathExpression),xpathExpression);
    }

    public String getSuscessMessage(){
        lblSuccessMessage.isDisplayedShort(5);
        return lblSuccessMessage.getText();
    }

    public String getWarningMessage(){
        return lblWarningMessage.getText();
    }

    public List<String> getListSuccessMessage(){
        int index = 1;
        List<String> lstMessage = new ArrayList<>();
        while (true){
            Label lblSuccessMessageI = Label.xpath(String.format("%s[%s]//div[contains(@class,'alert-success')]/span",lstAlertXpath,index));
            if(!lblSuccessMessageI.isDisplayed(1))
                return lstMessage;
            lstMessage.add(lblSuccessMessageI.getText().trim());
            index = index +1;
        }

    }

}
