package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.master.bookiesystempopup.MasterListPopup;
import pages.sb11.master.clientsystempopup.MemberListPopup;

public class BookieSuperPage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpBookie = DropDownBox.xpath("//div[contains(text(),'Bookie')]//following::select[1]");
    public DropDownBox ddpGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");

    public TextBox txtSuperCode = TextBox.xpath("//div[contains(text(),'Super Code')]//following::input[1]");

    public Button btnSearch = Button.xpath("//button[contains(text(),'Search')]");
    public Button btnMoreFilters = Button.xpath("//button[contains(text(),'More Filters')]");
    public Button btnAddSuper = Button.xpath("//button[contains(text(),'Add Super')]");

    public Label lblSuperCode = Label.xpath("//div[contains(text(),'Super Code')]");

    public Table tbSuper = Table.xpath("//table",10);
    Label lblSpin = Label.xpath("//div[contains(@class,'la-ball-clip-rotate')]");
    int colSuperCode = 4;
    int colMaster = 10;

    public void filterSuperCode (String companyUnit, String bookieCode, String superCode){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpBookie.selectByVisibleText(bookieCode);
        if (!superCode.isEmpty())
            txtSuperCode.sendKeys(superCode);
        btnSearch.click();
    }

    public MasterListPopup openMasterList(String superCode){
        int rowIndex = getSuperCodeRowIndex(superCode);
        tbSuper.getControlOfCell(1,colMaster,rowIndex,null).click();
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
        return new MasterListPopup();
    }

    private int getSuperCodeRowIndex(String superCode){
        int i = 1;
        Label lblSuperCode;
        while (true){
            lblSuperCode = Label.xpath(tbSuper.getxPathOfCell(1,colSuperCode,i,null));
            if(!lblSuperCode.isDisplayed()){
                System.out.println("The Super Code "+superCode+" does not display in the list");
                return 0;
            }
            if(lblSuperCode.getText().contains(superCode))
                return i;
            i = i +1;
        }
    }
    public void waitSpinnerDisappeared() {
        while(lblSpin.isDisplayed()) {
            lblSpin.waitForControlInvisible();
        }
    }
}
