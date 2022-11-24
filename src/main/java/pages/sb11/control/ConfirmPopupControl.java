package pages.sb11.control;

import com.paltech.element.BaseElement;
import com.paltech.element.common.*;
import org.openqa.selenium.By;


public class ConfirmPopupControl extends BaseElement {
    //app-match-odd-bet-slip//div[contains(@class,'place-form ')]//div[contains(@class,'order-row')][1]
    private String _xpath;
    private Label lblTitle;
    private Label lblContentMessage;
    private Button btnYes;
    private Button btnNo;
    private ConfirmPopupControl(By locator, String xpathExpression) {
        super(locator);
        this._xpath = xpathExpression;
        lblTitle = Label.xpath(String.format("%s//div[contains(@class,'modal-header')]//h5[contains(@class,'modal-title')]",_xpath));
        lblContentMessage = Label.xpath(String.format("%s//div[contains(@class,'modal-body')]//h6",_xpath));
        btnNo = Button.xpath(String.format("%s//div[contains(@class,'modal-footer')]//button[contains(@class,'btn-cancel')]",_xpath));
        btnYes = Button.xpath(String.format("%s//div[contains(@class,'modal-footer')]//button[contains(@class,'btn-delete')]",_xpath));
    }

    public static ConfirmPopupControl xpath(String xpathExpression) {
        return new ConfirmPopupControl(By.xpath(xpathExpression), xpathExpression);
    }

    public String getTitle(){return lblTitle.getText().trim();}

    public String getContentMessage(){return lblContentMessage.getText().trim();}

    public void confirmYes(){btnYes.click();}

    public void confirmNo(){btnNo.click();}



}
