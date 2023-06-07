package pages.sb11.soccer.popup.coa;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import org.openqa.selenium.By;

public class DeletePopup extends BaseElement  {

    private String _xpath;
    private Label lblTitle;
    private Label lblContentMessage;
    private Button btnYes;
    private Button btnNo;

    private DeletePopup(By locator, String xpathExpression) {
        super(locator);
        this._xpath = xpathExpression;
        lblTitle = Label.xpath(String.format("%s//h5[contains(@class,'modal-title')]",_xpath));
        lblContentMessage = Label.xpath(String.format("%s//div[contains(@class,'modal-body')]//h6",_xpath));
        btnNo = Button.xpath(String.format("%s//button[contains(text(),'No')]",_xpath));
        btnYes = Button.xpath(String.format("%s//button[contains(text(),'Yes')]",_xpath));
    }

    public static DeletePopup xpath(String xpathExpression) {
        return new DeletePopup(By.xpath(xpathExpression), xpathExpression);
    }

    public String getTitle(){return lblTitle.getText().trim();}

    public String getContentMessage(){return lblContentMessage.getText().trim();}

    public void confirmYes(){
        btnYes.waitForElementToBePresent(btnYes.getLocator());
        btnYes.click();}

    public void confirmNo(){btnNo.click();}
}
