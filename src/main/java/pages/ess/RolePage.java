package pages.ess;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import controls.Table;

import java.util.List;

public class RolePage {
    public Label lblRoles = Label.xpath("//div[contains(text(),'Roles')]");
    public Label lblPermissions = Label.xpath("//div[contains(text(),'Permissions')]");
    public Button btnCreate = Button.xpath("//button[contains(@class,'btn-success')]");
    public Table tbRoles = Table.xpath("//div[contains(text(),'Roles')]//following::table[1]",2);
    public Table tbPermissions = Table.xpath("//div[contains(text(),'Permissions')]//following::table[1]",3);

    public boolean isRoleDisplayed(String roleName){
        List<String> lstRole = tbRoles.getColumn(1,false);
        int ttRowRoles = tbRoles.getNumberOfRows(false, false);
        for (int i=0 ; i < ttRowRoles; i++){
            if (lstRole.get(i).contains(roleName)) {
                System.out.println(String.format("DEBUG! Found role %s in the list", roleName));
                return true;
            }
        }
        System.out.println(String.format("DEBUG! There is no role %s displayed in the list", roleName));
        return false;
    }
}
