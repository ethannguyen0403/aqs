package pages.sb11.soccer.popup.spp;

import com.paltech.element.common.Button;
import com.paltech.element.common.CheckBox;
import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.PTRiskPage;

public class SmartGroupPopup{
    private Label lblTitle = Label.xpath("//div[contains(@class,'smart-group-model')]//h5");
    private Button btnSetSelection = Button.xpath("//div[contains(@class,'smart-group-model')]//button[contains(@class,'set-selection')]");
    private Label lblSelectAll = Label.xpath("//div[contains(@class,'smart-group-model')]//button[contains(@class,'select ')]");
    private Label lblClear = Label.xpath("//div[contains(@class,'smart-group-model')]//button[contains(@class,'clear')]");
    String xpathSmartGroupItem = "//div[contains(@class,'smart-group-model')]//div[contains(@class,'modal-body')]//div[contains(@class,'list-item-filter')]/div[%s]/label";
    public void setSmartGroup(String smartGroup){
        int index = getSmartGroupIndex(smartGroup);
        if(index ==0) {
            System.out.println("DEBUG! Not found the group "+smartGroup+" in the list");
            return;
        }
        CheckBox cb = CheckBox.xpath(String.format(xpathSmartGroupItem,index));
        cb.scrollToThisControl(false);
        cb.click();
        btnSetSelection.click();

    }

    private int getSmartGroupIndex(String smartGroup){

        int index = 1;
        Label lblsmartGroupName;
        while(true){
            lblsmartGroupName = Label.xpath(String.format(xpathSmartGroupItem,index));
            if(!lblsmartGroupName.isDisplayed())
                return 0;
            if(lblsmartGroupName.getText().trim().equals(smartGroup))
                return index;
            index = index +1;
        }

    }
}
