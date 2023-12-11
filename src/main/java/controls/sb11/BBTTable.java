package controls.sb11;

import controls.Table;

public class BBTTable {
    private String _xpathTable;
    private String _xpathLeagueTime = "//div[contains(@class, 'league-time') and not(contains(@class, 'flex-row-reverse'))]//div[@class='font-weight-bold']";
    private String _xpathSLink = "//div[contains(@class, 'flex-row-reverse')]";
    private String _xpathLeagueName = "//div[contains(@class, 'header d-flex')]";
    private int _columnNumber;
    private String _rootXpathTable = "//div[contains(@class, 'table-contain')]";

    public BBTTable(String xpathTable, int columnNumber) {
        this._xpathTable = xpathTable;
        this._columnNumber = columnNumber;
    }

    public BBTTable(String rootXpathTable, String xpathTable, int columnNumber) {
        this._xpathTable = xpathTable;
        this._columnNumber = columnNumber;
        this._rootXpathTable = rootXpathTable;
    }

    public Table getTableControl(int tableIndex){
            return Table.xpath(String.format("(%s%s)[%s]", _rootXpathTable, _xpathTable, tableIndex), _columnNumber);
    }

    public Table getTableControl() {
        return Table.xpath(String.format("%s%s", _rootXpathTable, _xpathTable), _columnNumber);
    }
    public String getLeagueNameXpath(int tableIndex) {
        return String.format("(%s)[%s]%s", _rootXpathTable, tableIndex, _xpathLeagueName);
    }
    public String getSLinkXpath(int tableIndex, String SLinkName) {
        return String.format("(%s)[%s]%s//span[normalize-space()= '%s']", _rootXpathTable, tableIndex, _xpathSLink, SLinkName);
    }

    public String getLeagueTimeXpath(int tableIndex) {
        return String.format("(%s)[%s]%s", _rootXpathTable, tableIndex, _xpathLeagueTime);
    }

    public Table getHomeTableControl(int tableIndex){
        return Table.xpath(String.format("((%s)[%s]%s)[1]", _rootXpathTable, tableIndex, _xpathTable), _columnNumber);
    }

    public Table getAwayTableControl(int tableIndex){
        return Table.xpath(String.format("((%s)[%s]%s)[2]", _rootXpathTable, tableIndex, _xpathTable), _columnNumber);
    }
}
