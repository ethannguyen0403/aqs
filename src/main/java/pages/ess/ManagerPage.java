package pages.ess;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import controls.Table;

import java.util.List;

public class ManagerPage {
    int colUsername = 2;
    public Table tbUserManagement = Table.xpath("//span[contains(text(),'User Management')]//following::table[1]",5);
    public Button btnCreate = Button.xpath("//button[contains(@class,'btn-success')]");

    public boolean isAccountDisplayed(String username){
        int index = getRowContainsUsername(username);
        if(index==0)
            return false;
        return true;
    }

    private int getRowContainsUsername(String username){
        int i = 1;
        Label lblUserName;
        while (true){
            lblUserName = Label.xpath(tbUserManagement.getxPathOfCell(1,colUsername,i,null));
            if(!lblUserName.isDisplayed()){
                System.out.println("The username "+username+" does not display in the list");
                return 0;
            }
            if(lblUserName.getText().contains(username))
                return i;
            i = i +1;
        }
    }
}
