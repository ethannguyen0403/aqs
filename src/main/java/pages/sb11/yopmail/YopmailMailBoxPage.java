package pages.sb11.yopmail;

import com.paltech.element.common.Label;
import controls.Table;

import java.util.ArrayList;
import java.util.List;

public class YopmailMailBoxPage {
    private Label lblMailTitle = Label.xpath("//body//div[@id='mail']");
    private Label lblNameClient = Label.xpath("//body//div[@id='mail']/div[1]/div[1]");
    private  Label lblPleaseFindEnclosed = Label.xpath("//body//div[@id='mail']/div[1]/div[2]");
    private   Table tblReport = Table.xpath("//body//div[@id='mail']/div[1]//table[1]",9);
    private Label lblTherefore = Label.xpath("//body//div[@id='mail']/div[1]//table[2]//tr[1]");
    private Label lblThisAmountShallbeKIVToTheNextPeriod = Label.xpath("//body//div[@id='mail']/div[1]//table[2]//tr[1]");

    public ArrayList<String> getInfo(){
        lblMailTitle.waitForControlInvisible(5,1);
        ArrayList<String> lstInfo = new ArrayList<>();
        lstInfo.add(0,lblMailTitle.getText());
        lstInfo.add(lblNameClient.getText());
        lstInfo.add(lblPleaseFindEnclosed.getText());
        lstInfo.add(lblTherefore.getText());
        lstInfo.add(lblThisAmountShallbeKIVToTheNextPeriod.getText());
        return lstInfo;
    }

    public ArrayList<String> getbetListHeader(){

        return tblReport.getHeaderList();
    }
    public List<ArrayList<String>> getbetListInfo(){
        return tblReport.getRows(false);
    }




}
