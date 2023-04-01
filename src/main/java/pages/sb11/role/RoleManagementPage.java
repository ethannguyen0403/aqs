package pages.sb11.role;

import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;

public class RoleManagementPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Table tbRole = Table.xpath("//span[contains(text(),'Role')]//following::table[1]",3);
    public Table tbPermissions = Table.xpath("//span[contains(text(),'Permission')]//following::table[2]",3);
}
