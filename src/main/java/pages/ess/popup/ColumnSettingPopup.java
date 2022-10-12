package pages.ess.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.CheckBox;
import com.paltech.element.common.Label;
import controls.Cell;
import controls.Table;

public class ColumnSettingPopup {
    public Label lblHeader = Label.xpath("//app-hide-column-dialog//div[@class='modal-header']");
    public Button btnSubmit = Button.xpath("//app-hide-column-dialog//button[contains(text(), 'Submit')]");
    public Button btnClear = Button.xpath("//app-hide-column-dialog//button[contains(text(), 'Clear')]");
    public Table tbColumns = Table.xpath("//app-hide-column-dialog//table",3);
    public CheckBox cbAll = CheckBox.name("checkAll");

    public void hideColumn(String name){
        Cell cellByName = tbColumns.getCellByName(name,false);
        String chbColumnPath = tbColumns.getControlxPathBasedValueOfDifferentColumnOnRow(cellByName.toString(), 1, 2, 2, null, 3, "input", false, false);
        CheckBox chb = CheckBox.xpath(chbColumnPath);
        chb.click();
    }
}
