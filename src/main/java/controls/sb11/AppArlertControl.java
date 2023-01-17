package controls.sb11;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Label;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class AppArlertControl extends BaseElement {
    protected String _xpath = null;
    private Label lblSuccessMessage;
    private String lstAlertXpath ;

    public AppArlertControl(By locator, String xpathExpression) {
        super(locator);
        this._xpath = xpathExpression;
        lblSuccessMessage = Label.xpath(String.format("%s//div[contains(@class,'alert-success')]",_xpath));
        lstAlertXpath = String.format("%s/div",_xpath);
    }

    public static AppArlertControl xpath(String xpathExpression) {
        return new AppArlertControl(By.xpath(xpathExpression),xpathExpression);
    }

    public String getSuscessMessage(){
        return lblSuccessMessage.getText();
    }

    public List<String> getListSuccessMessage(){
        int index = 1;
        List<String> lstMessage = new ArrayList<>();
        while (true){
            Label lblSuccessMessageI = Label.xpath(String.format("%s[%s]//div[contains(@class,'alert-success')]",lstAlertXpath,index));
            if(!lblSuccessMessageI.isDisplayed(1))
                return lstMessage;
            lstMessage.add(lblSuccessMessageI.getText().trim());
            index = index +1;
        }

    }

}
