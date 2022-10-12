package controls;

import com.paltech.element.BaseElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


public class Row extends BaseElement {
    private String _xpath = null;
    public int timeOutInSecond = 5;

    private Row(By locator, String xpathExpression) {
        super(locator);
        this._xpath = xpathExpression;
    }

    public static Row xpath(String xpathExpression) {
        return new Row(By.xpath(xpathExpression), xpathExpression);
    }

    /**
     *
     * @param totalColumn total column in a table
     * @param isHeader true is header else row data
     * @return list of row value
     * [25/8/2020] Isabella update row 42 => add wildcard for the header because sometime header is /th and sometime is /td
     */
    public ArrayList<String> getRow(int totalColumn, boolean isHeader){
        ArrayList<String> lstRow = new ArrayList<String>();
        if (totalColumn < 1){
            return lstRow;
        }
        for (int i=1; i < (totalColumn + 1); i++){
            String cell_xpath = String.format("%s%s", this._xpath, "/td[%s]");
            if(isHeader){
                cell_xpath = String.format("%s%s", this._xpath, "/*[%s]");
            }
            cell_xpath = String.format(cell_xpath, i);
            Cell cell = Cell.xpath(cell_xpath);
            if (!cell.isDisplayedShort(timeOutInSecond)){
                if (!cell.isPresent(2)) {
                    return lstRow;
                }
            }

            lstRow.add(cell.getText(timeOutInSecond));
        }
        return lstRow;
    }

    public ArrayList<String> getHeaderRow(int totalColumn){
        ArrayList<String> lstRow = new ArrayList<String>();
        if (totalColumn < 1){
            return lstRow;
        }
        for (int i=1; i < (totalColumn + 1); i++){
            String cell_xpath = String.format("%s%s", this._xpath, "/th[%s]");
            cell_xpath = String.format(cell_xpath, i);
            Cell cell = Cell.xpath(cell_xpath);
            if (!cell.isDisplayedShort(timeOutInSecond)){
                if (!cell.isPresent(2)) {
                    return lstRow;
                }
            }
            lstRow.add(cell.getText(timeOutInSecond));
        }
        return lstRow;
    }

    /**
     * Getting list of WebElements within a cell of a row of a table
     * @param columnOrder  Column order within a table and the 1st column is 1
     * @param isHeader true if it is header
     * @param tagName within a cell of a table, there are usually a tag name after td tag such as p or span
     * @return List<WebElement>
     */
    public List<WebElement> getWebElementsOfCell(int columnOrder, boolean isHeader, String tagName){
        List<WebElement> lst = new ArrayList<WebElement>();
        String strColumnOrder = Integer.toString(columnOrder);
        String cellXpath;
        if(isHeader){
            cellXpath = String.format("%s%s",this._xpath, "/th[%s]");
            cellXpath = String.format(cellXpath, strColumnOrder);
        } else {
            if (tagName == null || tagName.isEmpty()){
                cellXpath = String.format("%s%s", this._xpath, "/td[%s]");
                cellXpath = String.format(cellXpath, strColumnOrder);
            } else {
                cellXpath = String.format("%s%s", this._xpath, "/td[%s]/%s");
                cellXpath = String.format(cellXpath, strColumnOrder, tagName);
            }
        }
        Cell cell = Cell.xpath(cellXpath);
        if (!cell.isDisplayed(2))
            return lst;
        return cell.getWebElements();
    }

    /**
     * Getting a date at date-picker
     * @param value
     * @param columnOrder
     * @return cell
     */
    public Cell getCellByName(String value, int columnOrder){
        String strColumnOrder = Integer.toString(columnOrder);
        String cellXpath = String.format("%s/td[%s]", this._xpath, strColumnOrder);

        Cell cell = Cell.xpath(cellXpath);
        if (!cell.isDisplayed(2))
            return null;
        if (cell.getText().equals(value))
            return cell;
        return null;
    }
}
