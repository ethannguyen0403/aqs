package pages.sb11.generalReports.popup.bookiestatement;

import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;

import java.util.Objects;

public class BookieAgentSummaryPopup {
    int colTable = 10;
    public int colAgentCode = 2;
    public Table tblAgentSummary = Table.xpath("//app-agent-summary//table",colTable);
    public Icon closeIcon = Icon.xpath("//span[@class='cursor-pointer close-icon ml-3']");

    public BookieAgentDetailPopup openAgentDetailPopup(String agentCode){
        int indexMS = tblAgentSummary.getRowIndexContainValue(agentCode,colAgentCode,"a");
        tblAgentSummary.getControlOfCell(1,colAgentCode,indexMS,"a").click();
        //Wait for popup display
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new BookieAgentDetailPopup();
    }
}
