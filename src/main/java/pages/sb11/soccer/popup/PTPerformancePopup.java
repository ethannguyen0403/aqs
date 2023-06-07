package pages.sb11.soccer.popup;

import com.paltech.element.common.Label;
import controls.Table;

public class PTPerformancePopup {
    Label lblTitle = Label.xpath("//app-account-pt-percent//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Table tblPTPerf = Table.xpath("//app-account-pt-percent//table",8);
    int colPT = 4;
    int colAccount = 2;

    public boolean isGroupNameDisplayed(String groupName){
        int index = getRowContainsGroupName(groupName);
        if(index==0)
            return false;
        return true;
    }

    public boolean isAccountPTMatched(String accountName, String PT){
        int index = getRowContainsAccountName(accountName);
        String pt = tblPTPerf.getControlOfCell(1,colPT, index,null).getText();
        if (pt.contains(PT))
            return true;
        return false;
    }

    private int getRowContainsGroupName(String groupName){
        int i = 1;
        Label lblGroupName;
        while (true){
            lblGroupName = Label.xpath(tblPTPerf.getxPathOfCell(1,1,i,null));
            if(!lblGroupName.isDisplayed()){
                System.out.println("The Group Name "+groupName+" does not display in the list");
                return 0;
            }
            if(lblGroupName.getText().contains(groupName))
                return i;
            i = i +1;
        }
    }

    private int getRowContainsAccountName(String accountName){
        int i = 2;
        Label lblAccountName;
        while (true){
            lblAccountName = Label.xpath(tblPTPerf.getxPathOfCell(1,colAccount,i,null));
            if(!lblAccountName.isDisplayed()){
                System.out.println("The Account Name "+accountName+" does not display in the list");
                return 0;
            }
            if(lblAccountName.getText().contains(accountName))
                return i;
            i = i +1;
        }
    }
}
