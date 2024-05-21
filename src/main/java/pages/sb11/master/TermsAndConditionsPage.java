package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import java.util.List;

public class TermsAndConditionsPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    DropDownBox ddClientBookie = DropDownBox.xpath("//div[text()='Client/Bookie']//following-sibling::div//select");
    DropDownBox ddClientBookieList = DropDownBox.xpath("//div[contains(text(),'List')]//following-sibling::select");
    Button btnShow = Button.xpath("//button[text()='Show']");
    Table tblData = Table.xpath("//app-terms-and-conditions//table",9);
    public void filter(String companyUnit, String clientBookieType, String clientBookieName){
        if (!companyUnit.isEmpty()){
            ddpCompanyUnit.selectByVisibleText(companyUnit);
        }
        if (!clientBookieType.isEmpty()){
            ddClientBookie.selectByVisibleText(clientBookieType);
        }
        if (!clientBookieName.isEmpty()){
            ddClientBookieList.selectByVisibleText(clientBookieName);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public void verifyAllClientDisplay() {
        List<String> lstClientAc = tblData.getColumn(tblData.getColumnIndexByName("Client Name"),true);
        List<String> lstClientEx = ddClientBookieList.getOptions();
        for (String clientName : lstClientAc){
            if (!lstClientEx.contains(clientName)){
                Assert.assertTrue(false,"FAILED! "+clientName+" is not exist");
            }
        }
    }
}
