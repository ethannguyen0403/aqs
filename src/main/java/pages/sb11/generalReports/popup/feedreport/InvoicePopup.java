package pages.sb11.generalReports.popup.feedreport;

import com.paltech.element.common.Button;

public class InvoicePopup {
    public Button btnCancel = Button.xpath("//app-feed-invoice//button[text()='Cancel']");
    public Button btnSendEmail = Button.xpath("//app-feed-invoice//button[text()='Send Email']");

}
