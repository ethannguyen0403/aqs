package pages.sb11.financialReports.popup.cashflowStatement;

import com.paltech.element.BaseElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TransactionDetailsPopup {

    public double getTotalValue(int colIndex){
        double total = 0;
        BaseElement lblValue = new BaseElement(By.xpath(String.format("//tr[contains(@class, 'total-col')]/td[%s]", colIndex)));
        for (WebElement ele: lblValue.getWebElements()){
            total = total + Double.valueOf(ele.getText().trim().replace(",", ""));
        }
        return total;
    }
}
