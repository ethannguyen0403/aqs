package pages.sb11.soccer;

import com.paltech.element.common.Label;
import controls.Table;

public class LeaguePerformancePage {
    Label lblTitle = Label.xpath("//app-league-performance//div[contains(@class,'main-box-header')]//span[1]");
    Label lblTableHeaderInRange = Label.xpath("//app-league-performance/div/div[1]/div[2]/div/span");
    Label lblTableHeader1Month = Label.xpath("//app-league-performance/div/div[1]/div[2]/div/span");
    Label lblTableHeader3Months = Label.xpath("//app-league-performance/div/div[1]/div[3]/div/span");
    Label lblTableHeader6Months = Label.xpath("//app-league-performance/div/div[1]/div[4]/div/span");
    Label lblTableHeader1Year = Label.xpath("//app-league-performance/div/div[1]/div[5]/div/span");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public String getTableHeader(String tableName){
        switch (tableName){
            case "In Range":
                return lblTableHeaderInRange.getText().trim();
            case "1 Month":
                return lblTableHeader1Month.getText().trim();
            case "3 Months":
                return lblTableHeader3Months.getText().trim();
            case "6 Months":
                return lblTableHeader6Months.getText().trim();
            case "1 Year":
                return lblTableHeader1Year.getText().trim();
        }
        return tableName;
    }

    public Table tblPBM = Table.xpath("//app-league-performance//table",12);
}
