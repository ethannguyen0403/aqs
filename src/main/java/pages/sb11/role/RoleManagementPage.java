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
    private int colColumn = 1;
    private int colCheckBox = 2;

    public RoleManagementPage selectRole(String roleName) {
        int rowIndex = tbRole.getRowIndexContainValue(roleName, colColumn, null );
        BaseElement targetCell = tbRole.getControlBySameTextOnRow(roleName, 1, colColumn, rowIndex, null, false);
        targetCell.click();
        waitSpinnerDisappeared();
        return this;
    }

    public RoleManagementPage switchPermissions(String permissionName, boolean isStatus) {
       int index = getPermissionColumnIndex(permissionName);
        CheckBox targetToggle =
                CheckBox.xpath(tbPermissions.getControlxPathBasedValueOfDifferentColumnOnRow(permissionName, 1, colColumn, index, null,
                        colCheckBox, "input", false, false));
        targetToggle.scrollToThisControl(true);
        if (isStatus) {
            if (!targetToggle.isSelected())
                CheckBox.xpath(tbPermissions.getControlxPathBasedValueOfDifferentColumnOnRow(permissionName, 1, colColumn, index, null,
                                colCheckBox, "span", false, false)).jsClick();
        } else {
            if (targetToggle.isSelected())
                CheckBox.xpath(tbPermissions.getControlxPathBasedValueOfDifferentColumnOnRow(permissionName, 1, colColumn, index, null,
                        colCheckBox, "span", false, false)).jsClick();
        }
        return this;
    }
    private int getPermissionColumnIndex(String colName){
        Label lblColName;
        int i = 1;
        while (true){
            lblColName = Label.xpath(tbPermissions.getxPathOfCell(1,colColumn,i,null));
            if(!lblColName.isDisplayed()){
                System.out.println("DEBUG! Column "+colName+" does not dísplay in the table");
                return 0;
            }
            if(lblColName.getText().trim().equals(colName)){
                System.out.println("DEBUG! Found Column "+colName+" at row" + i);
                return i;
            }
            i++;
        }
    }
}
