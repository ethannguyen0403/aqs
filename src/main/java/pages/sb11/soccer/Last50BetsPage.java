package pages.sb11.soccer;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Label;
import controls.Table;


public class Last50BetsPage {
    public Label lblHeaderGroup = Label.xpath("//app-pending-bets//table[@aria-label='table']//thead//th");


    public int totalCol = 13;
    public int betTypeCol = 7;
    public int selectionCol = 8;
    public int HDPCol = 9;
    public Table tblLast50Bets = Table.xpath("//app-pending-bets//table", totalCol);


    public boolean verifyCellHaveNoBackgroundColor(int colIndex) {
    return verifyCellHaveNoBackgroundColor(tblLast50Bets.getControlOfCell(1, colIndex, 2, null));
    }

    public boolean verifyCellHaveNoBackgroundColor(BaseElement cell) {
        return cell.getAttribute("background-color") == null ? true : false;
    }

}
