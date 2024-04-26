package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Label;


public class ReportS1Page {
    public Label lblTitlePage = Label.xpath("//app-s//div[contains(@class,'card-header')]//span");
    public String s1URL = "https://sportzstats.net/cricket/statistics?tab=S1&betfairEventId=&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZGVudGl0eSI6InNwb3J0ei5kZXZAbWFpbC5jb20iLCJyb2xlTmFtZSI6IkFETUlOIiwiaWF0IjoxNjkyMTg5ODQ2LCJleHAiOjI2MzgyNjk4NDZ9.jsKgfy7nIY8TL0aNUfM5cVm1ikhrV46jKejuYTLzqmw";

    public String getCurrentURL(){
        return DriverManager.getDriver().getCurrentUrl();
    }
}
