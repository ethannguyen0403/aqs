package pages.ess;

import com.paltech.element.common.Button;
import controls.Table;

import java.util.List;

public class ManagerPage {
    public Table tbUserManagement = Table.xpath("//span[contains(text(),'User Management')]//following::table[1]",5);
    public Button btnCreate = Button.xpath("//button[contains(@class,'btn-success')]");

    public boolean isAccountDisplayed(String username){
        List<String> lstAccount = tbUserManagement.getColumn(2,false);
        int ttRowAccount = tbUserManagement.getNumberOfRows(false, false);
        for (int i=1 ; i < ttRowAccount; i++){
            if (lstAccount.get(i).contains(username)) {
                System.out.println(String.format("DEBUG! Found username %s in the list", username));
                return true;
            }
        }
        System.out.println(String.format("DEBUG! There is no username %s displayed in the list", username));
        return false;
    }
}
