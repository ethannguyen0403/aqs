package pages.sb11.user;

import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;

public class UserManagementPage extends WelcomePage {
    int colUsername = 2;
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Table tbUser = Table.xpath("//span[contains(text(),'User Management')]//following::table[1]",6);
    public TextBox txtSearch = TextBox.xpath("//input[contains(@placeholder, 'Search')]");

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
            lblUserName = Label.xpath(tbUser.getxPathOfCell(1,colUsername,i,null));
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
