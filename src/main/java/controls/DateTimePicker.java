package controls;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import org.openqa.selenium.By;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateTimePicker extends BaseElement {
    static String _xpath = null;
    private Button btnPrevious;
    private Button btnNext;
    private Button btnCurrent;
    private Label lblMonth;
    private Label lblMonthYear;
    private Label lblYear;
    private Table tblCalender;
    private Table tblCalenderMonth;
    private TextBox txtDate;


    private DateTimePicker(TextBox txtBox, By locator, String xpath) {
        super( locator);
        txtDate = txtBox;
        _xpath = xpath;
        lblMonth = Label.xpath(String.format("%s//%s", _xpath, "button[@class='current ng-star-inserted']"));
        lblYear = Label.xpath(String.format("%s//%s", _xpath, "button[@class='current']"));
        btnPrevious = Button.xpath(String.format("%s//%s", _xpath, "button[@class='previous']"));
        btnNext = Button.xpath(String.format("%s//%s", _xpath, "button[@class='next']"));
        btnCurrent = Button.xpath(String.format("%s//%s", _xpath, "button[@class='current']"));
        lblMonthYear = Label.xpath(String.format("%s//%s", _xpath, "th[@class='datepicker-switch']"));
        tblCalender = Table.xpath(String.format("%s//%s", _xpath, "table[contains(@class, 'days weeks')]"), 7);
        tblCalenderMonth =Table.xpath(String.format("%s//%s", _xpath, "table[@class='months']"), 3);
    }

    public static DateTimePicker xpath(TextBox txtBox, String xpathExpression) {
        return new DateTimePicker(txtBox, By.xpath(xpathExpression), xpathExpression);
    }


    /**
     * Clicking a date of an early month
     * @param month must be less than 0
     * 			how many early months are but depending on the current month of the picker
     * 			e.g. if this picker is Jan 2018, 2 early month is Nov 2017
     * @param date it should be 1 to 28
     */
    public void previousMonthWithDate(int month, String date){
        if (month > 0 || date.isEmpty() || Integer.parseInt(date) > 31 ){
            logEndAction(String.format("Error: Date parameter '%s' is empty || month %s  is more than 0 || date %s is > 31", month, date, date));
            return;
        }
        logStartAction(String.format("click date '%s' on Calender", date));
        txtDate.isDisplayed();
        txtDate.isInvisible(2);
        txtDate.click();
        if (month == 0) {
            currentMonthWithDate(date);
        } else {
            while(month < 0){
                this.btnPrevious.click();
                month++;
            }
            this.clickDay(date, true);
        }
    }

    /**
     * Clicking on a date on the current month of the picker
     * @param date it should be 1 to 28
     */
    public void currentMonthWithDate(String date) {
        logStartAction(String.format("click date '%s' of current month on Calender", date));
        txtDate.click();
        this.clickDay(date, true);
    }

    /**
     * Clicking a date of a later month but it depends on the picker
     * @param month how many next months are but depending on the current month of the picker
     *              e.g. if this picker is Jan 2018, 2 later month is March 2018
     * @param date it should be 1 to 28
     */
    public void nextMonthWithDate(int month, String date){
        if (month < 1 || date.isEmpty() || Integer.parseInt(date) > 31 ){
            logEndAction(String.format("Error: Date parameter '%s' is empty || month %s  is less than 1 || date %s is > 31", month, date, date));
            return;
        }
        logStartAction(String.format("click date '%s' on Calender", date));
        txtDate.click();
        if (month == 0) {
            currentMonthWithDate(date);
        } else {
            while(month > 0) {
                if (this.btnNext.isClickable(2)){
                    this.btnNext.click();
                }
                month--;
            }
            this.clickDay(date, true);
        }
    }

    /*******************
     * Private methods
     *******************/
    private void clickDate(String date, String btnName){
        Cell e = tblCalender.getCellByName(date, false);
        if (e != null){
            e.click();
            logEndAction(String.format("clicked date '%s' on Calendar", date));
        } else {
            logEndAction(String.format("cannot click %s button on on Calendar because Cell is null", btnName));
        }
    }
    private void clickDay(String name, boolean isMoved){
        if(name.isEmpty()){
            logEndAction("Error: Month or date parameter is empty");
            return ;
        }
        String rowXpath = String.format("%s%s", tblCalender._xpathTable, "/tbody/tr[%s]");
        int i = 1;
        int rows = 6;//tblCalenderDate.getNumberOfRows(false,isMoved);
        while(i <= rows) {
            Row row = Row.xpath(String.format(rowXpath, i));
            if(!row.isDisplayed()){
                return ;
            }
            for (int j=1; j <= tblCalender._columnNumber+1; j ++){
                Cell e = row.getCellByName(name, j);
                if(Objects.nonNull(e)) {

                    if (Label.xpath(e.getLocator().toString().split("By.xpath: ")[1] + "//span[@class='is-other-month']").isDisplayed()) {
                        continue;
                    }
                    //Label lbl = Label.xpath(e.getLocator().toString().split("By.xpath: ")[1]+"//span");
                    if (Label.xpath(e.getLocator().toString().split("By.xpath: ")[1] + "//span").isDisplayed()) {
                        e.click();
                        logEndAction(String.format("clicked date '%s' on Calendar", name));
                        return;
                    }
                }
            }

            // moving to next row after getting the row in case row of this table are hidden visible view
            if (isMoved){
                row.scrollDownInDistance();
            }
            i += 1;
        }
        logEndAction(String.format("Debug: There is no value '%s' you want", name));
        return ;
    }

    public void selectDate(String input,String format)
    {
        txtDate.click();
        String currentYear = lblYear.getText().trim();
        String currentMonth = lblMonth.getText().trim();
        SimpleDateFormat sd = new SimpleDateFormat(format);

        try {
            Date date = sd.parse(input);
            sd = new SimpleDateFormat("YYYY");
            String y =  sd.format(date);
            sd = new SimpleDateFormat("MMMM");
            String mm =  sd.format(date);
            sd = new SimpleDateFormat("d");
            String dd =  sd.format(date);
            // click year
            if(!currentYear.equals(y)){
                lblYear.click();
                clickDate(y,"");
            }
            //
            if(!currentMonth.equals(mm))
            {
                lblMonth.click();
                clickMonth(mm);
            }
            clickDay(dd,false);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    private void clickMonth(String month){
        Cell e = tblCalenderMonth.getCellByName(month, false);
        if (e != null){
            e.click();
            logEndAction(String.format("clicked date '%s' on Calendar", month));
        } else {
            logEndAction(String.format("cannot click %s button on on Calendar because Cell is null", month));
        }
    }

}
