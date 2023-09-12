package pages.sb11.sport;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.BLSettingsPopup;

import java.util.ArrayList;
import java.util.List;

public class BLSettingPage extends WelcomePage {
    public int colEventName = 4;
    int colEdit = 5;
    int colTV = 6;
    int colKP = 7;
    int colLiveRB = 8;
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public TextBox txtDate = TextBox.name("dp");
    public Label lblDate = Label.xpath("//label[(text()='Date')]");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public Button btnShowLeagues = Button.xpath("//app-bl-settings//button[contains(@class,'btn-show-league')]");
    public Button btnShow = Button.xpath("//button[text()='Show']");
    public DropDownBox ddpLeague = DropDownBox.id("sport");
    public DropDownBox ddpOrderBy = DropDownBox.id("betType");
    public Table tbBLSettings = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[1]",8);

    public void filterResult(String date, String league, String orderBy, boolean isShow){
        if(!date.isEmpty()){
            dtpDate.selectDate(date,"dd/MM/yyyy");
            btnShowLeagues.click();
        }
        ddpLeague.selectByVisibleText(league);
        ddpOrderBy.selectByVisibleText(orderBy);
        if (isShow){
            btnShow.click();
        }
    }

    public BLSettingsPopup openBLSettingPopup(String eventName){
//        int rowIndex = getRowContainsEvent(eventName);
        Button btnEdit = null;
        for (int i = 0; i <= tbBLSettings.getNumberOfRows(false,false);i++){
            if (!(tbBLSettings.getxPathOfCell(1,colEventName,i,null) == null)){
                String nameEvent = Label.xpath(tbBLSettings.getxPathOfCell(1,colEventName,i,null)).getText();
                if (nameEvent.equalsIgnoreCase(eventName)){
                    btnEdit = Button.xpath(tbBLSettings.getxPathOfCell(1,colEdit,i,null)+"/a");
                    break;
                }
            }
        }
        btnEdit.click();
//        tbBLSettings.getControlOfCell(1,colEdit, rowIndex,"a").click();
        return new BLSettingsPopup();
    }

    public boolean isLeagueExist(String leagueName){
        int i = 1;
        Label lblLeague;
        while (true){
            lblLeague = Label.xpath("//app-bl-settings//table/tbody/tr[1]");
            if(!lblLeague.isDisplayed()) {
                System.out.println("Can NOT found the league "+leagueName+" in the table");
                return false;
            }
            if(lblLeague.getText().equalsIgnoreCase(leagueName)){
                System.out.println("Found the league "+leagueName+" in the table");
                return true;
            }
            i = i +1;
        }
    }

    private int getRowContainsEvent(String eventName){
        int i = 2;
        Label lblEventName;
        while (true){
            lblEventName = Label.xpath(tbBLSettings.getxPathOfCell(1,colEventName,i,null));
            if(!lblEventName.isDisplayed()){
                System.out.println("The Event "+eventName+" does not display in the list");
                return 0;
            }
            if(lblEventName.getText().contains(eventName)){
                System.out.println("Found the league "+eventName+" in the table");
                return i;
            }
            i = i +1;
        }
    }

    public String getFirstLeague(){

        List<String> lstLeague = getListLeague();
        try {
            // 0 Select, 1 All => get league from index = 2
            return lstLeague.get(2);
        }catch (Exception e){
            System.out.println("There is NO League on day "+ txtDate.getText());
            return null;
        }
    }

    public List<String> getListLeague(){
        return ddpLeague.getOptions();
    }
    public String getFirstEventNameOfLeague(){
        String eventName = null;
        if (!(tbBLSettings.getxPathOfCell(1,colEventName,2,null) == null)){
            eventName = Label.xpath(tbBLSettings.getxPathOfCell(1,colEventName,2,null)).getText();
        } else {
            System.out.println("There is not event!");
        }
        return eventName;
    }

    public void isEventSettingDisplayCorrect(String eventName, String tvValue, String kpValue, String liveRBValue) {
        int rowIndex = getRowContainsEvent(eventName);
        String valueTV = Label.xpath(tbBLSettings.getxPathOfCell(1,colTV,rowIndex,null)).getText();
        String valueKP = Label.xpath(tbBLSettings.getxPathOfCell(1,colKP,rowIndex,null)).getText();
        String valueLiveRB = Label.xpath(tbBLSettings.getxPathOfCell(1,colLiveRB,2,null)).getText();
        Assert.assertTrue(valueTV.equalsIgnoreCase(tvValue),"TV value is not correct!");
        Assert.assertTrue(valueKP.equalsIgnoreCase(kpValue),"KP value is not correct!");
        Assert.assertTrue(valueLiveRB.equalsIgnoreCase(liveRBValue),"Live RB value is not correct!");
    }
}
