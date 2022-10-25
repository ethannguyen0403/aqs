package pages.ess.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.CheckBox;
import com.paltech.element.common.Label;
import controls.Table;

import java.util.List;

public class ColumnSettingPopup {
    public Label lblHeader = Label.xpath("//app-hide-column-dialog//div[@class='modal-header']");
    public Button btnSubmit = Button.xpath("//app-hide-column-dialog//button[contains(text(), 'Submit')]");
    public Button btnClear = Button.xpath("//app-hide-column-dialog//button[contains(text(), 'Clear')]");
    public Table tbColumns = Table.xpath("//app-hide-column-dialog//table",3);
    public CheckBox cbAll = CheckBox.name("checkAll");
    private int colColumns = 1;
    private int colCheckbox = 2;

    public void selectColumn(String colName){
        CheckBox cb;
        if(colName.equalsIgnoreCase("all")){
            cb = cbAll;
        }
        else {
            int index = getColumnIndex(colName);
            cb = CheckBox.xpath(tbColumns.getControlxPathBasedValueOfDifferentColumnOnRow(colName,1,colColumns,index,null,colCheckbox,"input",false,false));
        }
       cb.click();
    }

    private int getColumnIndex(String colName){
        Label lblColName;
        int i = 1;
        while (true){
            lblColName = Label.xpath(tbColumns.getxPathOfCell(1,colColumns,i,null));
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

    public void hideListColumn(List<String> lstColumn){
        for (String col:lstColumn             ) {
            selectColumn(col);
        }
        btnSubmit.click();
    }
    public void hideColumn(String column){
        selectColumn(column);
        btnSubmit.click();
    }
}
