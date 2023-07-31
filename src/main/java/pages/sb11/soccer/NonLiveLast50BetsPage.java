package pages.sb11.soccer;

import com.paltech.element.common.Label;

public class NonLiveLast50BetsPage {
    public Label lblSummaryTableHeader = Label.xpath("//app-last-n-bets//div[@class='summary-table']//thead//th[@class='header']");
    public Label lblDetailTableHeader = Label.xpath("//app-last-n-bets//div[@class='detail-table']/div/div");

}
