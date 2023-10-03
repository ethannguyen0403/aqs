package pages.sb11.role;


import com.paltech.element.BaseElement;
import com.paltech.element.common.CheckBox;
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


    private String convertXpath(BaseElement element){
        return element.getLocator().toString().trim().replace("By.xpath: ", "");
    }

    public RoleManagementPage selectRoleInRoleTable(String roleName) {
        Label targetCell = Label.xpath(String.format(convertXpath(tbRole)+ "//*[contains(text(), '%s')]", roleName));
        targetCell.click();
        waitSpinnerDisappeared();
        return this;
    }

    public RoleManagementPage switchPermissions(String permissionName, boolean isOn) {
        CheckBox targetToggle =
                CheckBox.xpath(String.format(convertXpath(tbPermissions) + "//*[contains(text(), '%s')]/following-sibling::td[1]//input",
                        permissionName));
        targetToggle.scrollToThisControl(false);
        if (isOn) {
            if (!targetToggle.isSelected())
                targetToggle.click();
        } else {
            if (targetToggle.isSelected())
                targetToggle.click();
        }
        return this;
    }
}
