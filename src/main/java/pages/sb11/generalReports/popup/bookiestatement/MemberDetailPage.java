package pages.sb11.generalReports.popup.bookiestatement;

import controls.Table;

public class MemberDetailPage {
    int colTotalMember = 11;
    public Table tblMemberDetail = Table.xpath("(//app-table-member-detail/div//table)[1]",colTotalMember);

}
