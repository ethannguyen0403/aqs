package pages.sb11.soccer;

import com.paltech.element.common.Label;
import controls.Table;

public class LeaguePerformancePage {
    Label lblTitle = Label.xpath("//app-league-performance//div[contains(@class,'main-box-header')]//span[1]");
    Label lblTableHeaderInRange = Label.xpath("//app-league-performance/div/div[1]/div[2]/div/span");
    Label lblTableHeader1Month = Label.xpath("//app-league-performance/div/div[1]/div[3]/div/span");
    Label lblTableHeader3Months = Label.xpath("//app-league-performance/div/div[1]/div[4]/div/span");
    Label lblTableHeader6Months = Label.xpath("//app-league-performance/div/div[1]/div[5]/div/span");
    Label lblTableHeader1Year = Label.xpath("//app-league-performance/div/div[1]/div[6]/div/span");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public String getTableHeaderInRange(){
        return lblTableHeaderInRange.getText().trim();
    }

    public String getTableHeader1Month(){
        return lblTableHeader1Month.getText().trim();
    }

    public String getTableHeader3Months(){
        return lblTableHeader3Months.getText().trim();
    }

    public String getTableHeader6Months(){
        return lblTableHeader6Months.getText().trim();
    }

    public String getTableHeader1Year(){
        return lblTableHeader1Year.getText().trim();
    }

    public Table tblPBM = Table.xpath("//app-league-performance//table",12);
}
