package pages.sb11.soccer;

import com.paltech.element.common.Label;
import controls.Table;

public class PerformanceByMonthPage {
    Label lblTitle = Label.xpath("//app-performance-by-month//div[contains(@class,'main-box-header')]//span[1]");
    Label lblTableHeader = Label.xpath("//app-performance-by-month//div[(@class='p-2')]//span");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public String getTableHeader (){
        return lblTableHeader.getText().trim();
    }

    public Table tblPBM = Table.xpath("//app-performance-by-month//table",12);
}
