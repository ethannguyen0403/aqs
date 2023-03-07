package pages.sb11.soccer.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.PTRiskPage;

public class PTRiskBetListPopup extends WelcomePage {
    private int totalCol = 12;
    private int colAccountCode = 3;
    public int colPTPercent = 4;
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    Button btnClose = Button.xpath("//div[@class='pt-risk-modal']//span[contains(@class,'close-icon')]");
    Table tblBetList = Table.xpath("//table[@aria-label='group table']",totalCol);
    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }


    public String getBetListCellValue(String accountCode, int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        Label lblAccountCode;
        int i = 1;
        while (true){
            lblCellValue = Label.xpath(tblBetList.getxPathOfCell(1,colIndex,i,null));
            lblAccountCode = Label.xpath(tblBetList.getxPathOfCell(1,colAccountCode,i,"span"));
            if(!lblCellValue.isDisplayed()){
                System.out.println("There's no value display in the Bet List table");
                return null;
            }
            if(lblAccountCode.getText().equalsIgnoreCase(accountCode)){
                returnValue = lblCellValue.getText();
                return returnValue;
            }
            i = i+1;
        }
    }

    public PTRiskPage closeBetListPopup() {
        btnClose.click();
        return new PTRiskPage();
    }
}
