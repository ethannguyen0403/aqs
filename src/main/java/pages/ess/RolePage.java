package pages.ess;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import controls.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RolePage {
    public Label lblRoles = Label.xpath("//div[contains(text(),'Roles')]");
    public Label lblPermissions = Label.xpath("//div[contains(text(),'Permissions')]");
    public Button btnCreate = Button.xpath("//button[contains(@class,'btn-success')]");
    public Table tbRoles = Table.xpath("//div[contains(text(),'Roles')]//following::table[1]",2);
    public Table tbPermissions = Table.xpath("//div[contains(text(),'Permissions')]//following::table[1]",3);
    int colPermissionName = 1;
    int colPermissionCb = 2;
    int colRolName = 1;

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
    public List<String> getPermissionList(){
        List<String> listPermission = new ArrayList<>();
        Label lbl;
        int i =1;
        while (true){
            lbl = Label.xpath(tbPermissions.getxPathOfCell(1,colPermissionName, i,null));
            if(!lbl.isDisplayed())
                return listPermission;
            listPermission.add(lbl.getText().trim());
            i++;
        }
    }
    public List<String> getRoleList(){
        List<String> lstRole = new ArrayList<>();
        Label lbl;
        int i =1;
        while (true){
            lbl = Label.xpath(tbRoles.getxPathOfCell(1,colRolName , i,"span"));
            if(!lbl.isDisplayed())
                return lstRole;
            lstRole.add(lbl.getText().trim());
            i++;
        }
    }
}
