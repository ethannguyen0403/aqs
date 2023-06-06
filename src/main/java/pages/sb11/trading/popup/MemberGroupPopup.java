package pages.sb11.trading.popup;

import com.paltech.element.common.Label;

public class MemberGroupPopup {
    Label lblTitle = Label.xpath("//app-smart-group-member-list/div/div[1]/span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public Label lblTitleMemberList = Label.xpath("//div[contains(@class,'pr-2')]//div[contains(@class,'card-header')]//span[1]");
    public Label lblTitleMemberUnAssigned = Label.xpath("//div[contains(@class,'member-unassigned')]//div[contains(@class,'card-header')]//span[1]");
    public Label lblTitleMemberSelected = Label.xpath("//div[contains(@class,'member-select')]//div[contains(@class,'card-header')]//span[1]");
}
