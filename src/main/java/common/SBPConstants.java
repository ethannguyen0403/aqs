package common;

import utils.sb11.CurrencyRateUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SBPConstants {

    public final static String MASTER = "Master";
    public final static String BOOKIE_INFO = "Bookie Info";
    public final static String CLIENT_SYSTEM = "Client System";
    public final static String BOOKIE_SYSTEM = "Bookie System";
    public final static String ACCOUNT_SEARCH = "Account Search";
    public final static String ACCOUNT_LIST = "Account List";
    public final static String MISSED_ACCOUNTS = "Missed Accounts";
    public final static String AUTO_CREATED_ACCOUNTS = "Auto-created Accounts";
    public final static String ADDRESS_BOOK = "Address Book";
    public final static String TRADING = "Trading";
    public final static String ACCOUNTING = "Accounting";
    public final static String ROLE = "Role";
    public final static String USER = "User";
    public final static String CHART_OF_ACCOUNT = "Chart Of Account";
    public final static String GENERAL_REPORTS = "General Reports";
    public final static String SOCCER = "Soccer";
    public final static String INVOICE = "Invoice";
    public final static String INVOICES = "Invoices";
    public final static String ROLE_MANAGEMENT = "Role Management";
    public final static String TRADING_PERMISSION = "Trading Permission";
    public final static String LEAGUE_SEASON_TEAM_INFO = "League/Season/Team Info";
    public final static String USER_MANAGEMENT = "User Management";
    public final static String PT_RISK_CONTROL = "PT Risk Control";
    public final static String LEDGER_STATEMENT = "Ledger Statement";
    public final static String BOOKIE_STATEMENT = "Bookie Statement";
    public final static String JOURNAL_ENTRIES = "Journal Entries";
    public final static String JOURNAL_REPORTS = "Journal Reports";
    public final static String SMART_SYSTEM = "Smart System";
    public final static String CURRENCY_RATES = "Currency Rates";
    public final static String BET_ENTRY = "Bet Entry";
    public final static String TRANSACTION_VERIFICATION = "Transaction Verification";
    public final static String RESULT_ENTRY = "Result Entry";
    public final static String ACCOUNT_PERCENT = "Account Percent";
    public final static String BALANCE_CURRENT = "Balance [Current]";
    public final static String SPORT= "Sport";
    public final static String CLIENT_STATEMENT = "Client Statement";
    public final static String SPP = "SPP";
    public final static String BBT = "BBT (Bets By Team)";
    public final static String BBG = "BBG (Bets By Group)";
    public final static String EVENT_SCHEDULE = "Event Schedule";
    public final static String CONFIRM_BETS = "Confirm Bets";
    public final static String BET_SETTLEMENT = "Bet Settlement";
    public final static String EVENT_MAPPING = "Event Mapping";
    public final static String OPEN_PRICE = "Open Price";
    public final static String BL_SETTINGS = "BL Settings";
    public final static String PHONE_BETTING = "Phone Betting";
    public final static String MONITOR_BETS = "Monitor Bets";
    public final static String MATCH_ODDS_LIABILITY = "1x2 Liability";
    public final static String HANDICAP_LIABILITY = "Handicap Liability";
    public final static String HANDICAP_CORNER_LIABILITY = "Handicap Corner Liability";
    public final static String OVER_UNDER_LIABILITY = "Over/Under Liability";
    public final static String OVER_UNDER_CORNER_LIABILITY = "Over/Under Corner Liability";
    public final static String BBG_PHONE_BETTING = "BBG - Phone Betting";

    public final static String PLACE_BET_SUCCESS_MSG ="The bet was placed successfully";
    public final static String FINANCIAL_YEAR = "Year 2023-2024";
    public final static String COMPANY_UNIT = "Kastraki Limited";
    public final static String CLIENT_CREDIT_ACC = "ClientCredit-AutoQC";
    public final static String CLIENT_DEBIT_ACC = "ClientDebit-AutoQC";
    public final static String LEDGER_GROUP_NAME_INCOME = "QA Ledger Group Income";
    public final static String LEDGER_GROUP_NAME_EXPENDITURE = "QA Ledger Group Expenditure";
    public final static String LEDGER_GROUP_NAME_LIABILITY = "QA Ledger Group Liability";
    public final static String LEDGER_GROUP_NAME_ASSET = "QA Ledger Group Asset";
    public final static String LEDGER_GROUP_NAME_CAPITAL = "QA Ledger Group Capital";
    public final static String LEDGER_ASSET_CREDIT_ACC = "050.000.000.000 - AutoAssetCredit";
    public final static String LEDGER_ASSET_DEBIT_ACC = "055.000.000.000 - AutoAssetDebit";
    public final static String LEDGER_LIABILITY_CREDIT_ACC = "040.000.000.000 - AutoLiabilityCredit";
    public final static String LEDGER_LIABILITY_DEBIT_ACC = "044.000.000.000 - AutoLiabilityDebit";
    public final static String LEDGER_CAPITAL_CREDIT_ACC = "030.000.000.000 - AutoCapitalCredit";
    public final static String LEDGER_CAPITAL_DEBIT_ACC = "033.000.000.000 - AutoCapitalDebit";
    public final static String LEDGER_INCOME_CREDIT_ACC = "002.000.000.000 - AutoIncomeCredit";
    public final static String LEDGER_INCOME_DEBIT_ACC = "002.200.000.000 - AutoIncomeDebit";
    public final static String LEDGER_EXPENDITURE_CREDIT_ACC = "010.000.000.000 - AutoExpenditureCredit";
    public final static String LEDGER_EXPENDITURE_DEBIT_ACC = "011.000.000.000 - AutoExpenditureDebit";
    public final static String LEDGER_PARENT_NAME_ASSET = "QA Ledger Group Asset";
    public final static String LEDGER_PARENT_NAME_EXPENDITURE = "QA Ledger Group Expenditure";
    public final static String LEDGER_PARENT_NAME_LIABILITY = "QA Ledger Group Liability";
    public final static String LEDGER_PARENT_NAME_INCOME = "QA Ledger Group Income";
    public final static String LEDGER_PARENT_NAME_CAPITAL = "QA Ledger Group Capital";
    public final static List<String> TABLE_HEADER = Arrays.asList("Role", "User", "Sport", "Soccer","Accounting","Trading","Master","General Reports","Invoice","Financial Reports");
    public final static List<String> COMPANY_UNIT_LIST = Arrays.asList("Kastraki Limited", "SK1122", "IB 01", "Fair");
    public final static List<String> COMPANY_UNIT_LIST_ALL = Arrays.asList("All","Kastraki Limited", "SK1122", "IB 01", "Fair");
    public final static List<String> FINANCIAL_YEAR_LIST = Arrays.asList("Year 2020-2021","Year 2021-2022","Year 2022-2023","Year 2023-2024");
    public final static List<String> COUNTRY_LIST = Arrays.asList("All", "Afghanistan","Africa","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antigua and Barbuda","Argentina","Armenia","Aruba","Asia","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia-Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei Darussalam","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde Islands","Cayman Islands","Central African Republic","Chad","Chile","China","Chinese Taipei","Colombia","Comoros Island","Congo","Cook Islands","Costa Rica","Croatia","Cuba","Curacao","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Dubai","East Timor","Ecuador","Egypt","El Salvador","England","Equatorial Guinea","Eritrea","Estonia","Eswatini","Ethiopia","EuroCup","Europe","Faroe Islands","FIFA","Fiji","Finland","France","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Grenada","Guadeloupe","Guam","Guatemala","Guinea","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","International","Iran","Iraq","Ireland Republic","Israel","Israeli-Palestinian","Italy","Ivory Cost","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Korea North","Korea South","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macao","Macedonia FYR","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Mauritania","Mauritius","Mexico","Moldova","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","North America","North Ireland","Norway","Oceania","Oman","Other","Others","Pakistan","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Romania","Russia","Rwanda","Samoa","San Marino","Saudi Arabia","Scotland","Senegal","Serbia","Serbia and Montenegro","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South America","Spain","Sri Lanka","St. Kitts and Nevis","St. Lucia","St. Vincent and the Grenadines","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Tahiti","Tajikistan","Tanzania","Thailand","Togo","Tonga","Trinidad And Tobago","Tunisia","Turkey","Turkmenistan","UEFA","Uganda","Ukraine","United Arab Emirates","United Kingdom","Uruguay","US Virgin Islands","USA","Uzbekistan","Vanuatu","Venezuela","Vietnam","Wales","World","WorldCup","Yemen","Yugoslavia","Zambia","Zanzibar","Zimbabwe");
    public final static List<String> TYPE_LIST = Arrays.asList("Normal","Account");
    public final static List<String> ORDER_BY_LIST = Arrays.asList("KOT","League");
    public final static List<String> STATUS_LIST = Arrays.asList("All","Pending","Settled","Void All","Void FT Only");
    public final static List<String> SPORT_LIST = Arrays.asList("Soccer","Cricket","Basketball","Tennis","American Football","Ice Hockey");
    public final static List<String> SPORT_LIST_ALL = Arrays.asList("All","Soccer","Cricket","Basketball","Tennis","American Football","Ice Hockey");
    public final static List<String> PROVIDER_LIST = Arrays.asList("Pinnacle","Bet ISN","PS7","Fair999");
    public final static List<String> LIVE_NONLIVE_LIST = Arrays.asList("ALL","Live","Non-Live");
    public final static List<String> CURRENCY_LIST = Arrays.asList("ALL","AUD","CAD","CNY","AED","EUR","HKD","INR","IDR","JPY","MYR","KPW","PKR","PHP","SGD","ZAR","KRW","LKR","GBP","TWD","THB","USD","VND");
    public final static List<String> STAKE_LIST_ALL = Arrays.asList("ALL","Above 1K","Above 10K","Above 50K","Above 100K","Above 150K");
    public final static List<String> STAKE_LIST = Arrays.asList("All","Above 1K","Above 10K","Above 50K","Above 100K","Above 150K");
    public final static String GMT_7 = "GMT +7";
    public static final Map<String, String> CRICKET_MARKET_TYPE_BET_LIST= new HashMap<String, String>()
    {
        {
            put("1X2", "Match Betting");
            put("Match-HDP", "Match Handicap");
            put("OU", "Over/Under");
            put("DNB", "DrawNoBet");

        }
    };
    public static final Map<String, String> SPORT_MAP= new HashMap<String, String>()
    {
        {
            put("Soccer", "1");
            put("Cricket", "2");

        }
    };

    public static final Map<String, String> SPORT_SIGN_MAP= new HashMap<String, String>()
    {
        {
            put("Soccer", "SOC");
            put("Cricket", "CRI");
            put("MB", "SOC");
        }
    };

    public static final Map<String, String> SOCCER_MARKET_TYPE_BET_LIST= new HashMap<String, String>()
    {
        {
            put("HDP", "Full Time Handicap");
        }
    };

    public static class BetEntryPage {
        public final static String MESSAGE_SUCCESS_MANUAL_BET ="Place Successful !";
        public final static List<String> TABLE_HEADER = Arrays.asList("Time", "Event", "Full Time", "Half Time"," ", "HDP", "Home", "Away", "GOAL", "Over", "Under", "HDP", "Home", "Away", "GOAL", "Over", "Under", "More", "SPB");
        public final static List<String> SPORT_LIST = Arrays.asList("Soccer","Cricket");
        public final static List<String> BET_TYPE = Arrays.asList("Back","Lay");
        public final static List<String> ODD_TYPE = Arrays.asList("HK","ID","MY","EU");
    }

    public static class BetSettlement{
        public final static List<String> LST_MESSAGE_SETTLE_SENT_MAIL = Arrays.asList("Bet(s) is settled successfully.",
                "Statement Email has been sent to your mail box.");
        public final static List<String> BET_LIST_STATEMENT_EMAIL = Arrays.asList("Bet List [Current]",
                "Description", "Selection", "HDP", "Live", "Prive","Stake", "Win/Lose","Type","Date");
        public final static List<String> STATUS_LIST = Arrays.asList("Confirmed","Settled");
        public final static List<String> MATCH_DATE = Arrays.asList("[All Dates]","Specific Date");
    }

    public static class AccountType {
        public final static String LEDGER = "Ledger";
        public final static String BOOKIE = "Bookie";
        public final static String CLIENT = "Client";
    }

    public static class TradingPermission{
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "i", "Username", "Auto-assigned All", "Permission", "Customers");
    }

    public static class AutoCreatedAccounts{
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "Account Code", "Bookie", "Client", "Created Date");
    }

    public static class LedgerStatement{
        public final static List<String> ACCOUNT_TYPE = Arrays.asList("All","Asset","Income","Liability","Expenditure","Capital");
        public final static List<String> TABLE_HEADER = Arrays.asList(" ", "Amounts are shown in Original Currency"," ", "Amounts are shown in HKD", "#", "Ledger", "CUR", "Credit/Debit", "Running Bal.", "Running Bal. [CT]"," ", "Credit/Debit", "Running Bal.");
    }

    public static class BookieInfo{
        public final static List<String> STATUS_LIST = Arrays.asList("All", "Active","Closed","In-active");
        public final static List<String> CURRENCY_LIST = Arrays.asList("All","AUD","CAD","CNY","AED","EUR","HKD","INR","IDR","JPY","MYR","KPW","PKR","PHP","SGD","ZAR","KRW","LKR","GBP","TWD","THB","USD","VND");
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "i", "Code", "Bookie Name", "CUR", "Support By","Edit","","","# InCharge","# Groups");
    }

    public static class BookieSystem{
        public final static List<String> STATUS_LIST = Arrays.asList("All", "Active","Closed","In-active");
        public final static List<String> CURRENCY_LIST = Arrays.asList("All","AUD","CAD","CNY","AED","EUR","HKD","INR","IDR","JPY","MYR","KPW","PKR","PHP","SGD","ZAR","KRW","LKR","GBP","TWD","THB","USD","VND");
        public final static List<String> TABLE_HEADER_SUPER = Arrays.asList("#", "i", "Bookie", "Super Code", "CUR", "Client","Support By","Edit","","# Master");
        public final static List<String> TABLE_HEADER_MASTER = Arrays.asList("#", "i", "Bookie", "Master Code", "CUR", "Client","Support By","Edit","","# Agent");
        public final static List<String> TABLE_HEADER_AGENT = Arrays.asList("#", "i", "Bookie", "Agent Code", "CUR", "Client","Type","Support By","Edit","","# Members");
        public final static List<String> GO_TO_LIST = Arrays.asList("Super","Master","Agent");
    }

    public static class LeagueSeasonTeamInfo{
        public final static List<String> LEAGUE_TABLE_HEADER = Arrays.asList("#", "i", "C", "League Name", "IsMain", "IsCup", "Edit", "");
        public final static List<String> SEASON_TABLE_HEADER = Arrays.asList("#", "i", "Season Name", "Start Date", "End Date", "Edit", "");
        public final static List<String> TEAM_TABLE_HEADER = Arrays.asList("#", "i", "Team Name", "Country", "Edit", "", "Under League");
        public final static List<String> SOCCER_TYPE = Arrays.asList("All","Is Main League", "Is Cup League", "Normal");
    }

    public static class ResultEntry{
        public final static List<String> RESULT_SOCCER_TABLE_HEADER = Arrays.asList("#", "i", "Time", "Event", "", "Status", "HT Score", "FT Score", "HT Corner", "FT Corner", "HT Card", "FT Card");
        public final static List<String> RESULT_CRICKET_TABLE_HEADER = Arrays.asList("#", "i", "Time", "Event", "", "Status", "Team To Bat First", "Home Team", "Away Team", "IsHdp FB", "Runs", "Wtks", "Runs", "Wtks");

    }

    public static class EventMapping{
        public final static List<String> EVENT_TABLE_HEADER = Arrays.asList("Date", "League", "Event","","","","","");
        public final static List<String> PROVIDER_EVENT_TABLE_HEADER = Arrays.asList("", "Date", "League", "Event","","","","");
        public final static List<String> MAPPED_LIST_TABLE_HEADER = Arrays.asList("i", "Match Date", "League", "Event", "Provider Match Date", "Provider League", "Provider Event", "Provider", "Created Date", "Created By", "Unmap","","","","","","","","","","","");
    }

    public static class OpenPrice{
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "Time\ni", "Event", "FT - 1x2\nH/A\nDraw", "FT - Handicap - OP\nHDP\nPrice", "FT - Over/Under - OP\nHDP\nPrice","");
    }

    public static class AccountSearch{
        public final static List<String> TYPE_LIST = Arrays.asList("Account Code","Account Id");
    }

    public static class AccountList{
        public final static List<String> TYPE_LIST = Arrays.asList("Client","Bookie");
        public final static List<String> CURRENCY_LIST = Arrays.asList("[All]","AUD","CAD","CNY","AED","EUR","HKD","INR","IDR","JPY","MYR","KPW","PKR","PHP","SGD","ZAR","KRW","LKR","GBP","TWD","THB","USD","VND");
        public final static List<String> STATUS_LIST = Arrays.asList("[All]", "Active","Closed","In-Active");
        public final static List<String> CREATION_TYPE_LIST = Arrays.asList("[All]", "Manual","System");
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "i", "Account Code","Bookie","Client","CUR","Edit","Credit Limit","SL","SNL","BL","BNL","FL","FNL","TL","TNL","OL","ONL","Edit PT","");
    }

    public static class BLSettings{
        public final static List<String> TABLE_HEADER = Arrays.asList("#","i", "Time","Event", "Edit", "TV", "KP", "Live RB");
    }

    public static class AddressBook{
        public final static List<String> TABLE_HEADER = Arrays.asList("#","Account Code","CUR","To (Name, Email)","CC (Name, Email)","BCC (Name, Email)","Edit");
    }

    public static class ClientSystem{
        public final static List<String> STATUS_LIST = Arrays.asList("All", "Active","Closed","In-active");
        public final static List<String> CURRENCY_LIST = Arrays.asList("All","AUD","CAD","CNY","AED","EUR","HKD","INR","IDR","JPY","MYR","KPW","PKR","PHP","SGD","ZAR","KRW","LKR","GBP","TWD","THB","USD","VND");
        public final static List<String> CLIENT_LIST = Arrays.asList("With Super","Without Super");
        public final static List<String> TABLE_HEADER_CLIENT = Arrays.asList("#","i", "Client Name","CUR","Edit","Created Date");
        public final static List<String> TABLE_HEADER_SUPER_MASTER = Arrays.asList("Super Master","CUR","Edit","","#Master","#Agents","#Memb","#Comm","#Ledger","List");
    }

    public static class TransactionVerification{
        public final static List<String> WEBSITE = Arrays.asList("Pinnacle","BetISN","Fair999");
    }

    public static class AccountPercent{
        public final static List<String> TYPE_LIST = Arrays.asList("[All]","With Percent","Without Percent");
        public final static List<String> TABLE_HEADER = Arrays.asList("#","i","Account Code","Actual WinLoss %","CUR","Client Name");
    }

    public static class MonitorBets{
        public final static List<String> TABLE_HEADER = Arrays.asList("Info", "AC", "Event", "Selection","HDP","Stake","L","NL","T","Report","");
        public final static List<String> SPORT_LIST = Arrays.asList("All","Soccer","Cricket","Basketball","Tennis","American Football","Ice Hockey");
        public final static List<String> SMART_TYPE_LIST = Arrays.asList("Master","Group");
        public final static List<String> PUNTER_TYPE_LIST = Arrays.asList("Smart Punter","Normal Punter");
        public final static List<String> BET_PLACED_IN = Arrays.asList("Last 5 Min","Last 10 Min","Last 15 Min","Last 1 Hour","Last 3 Hour", "Last 6 Hour", "Last 9 Hour", "Last 12 Hour","All Hours");
        public final static List<String> BET_COUNT = Arrays.asList("Last 10 Bets","Last 50 Bets","Last 100 Bets","Last 200 Bets","Last 300 Bets");
    }

    public static class MatchOddsLiability{
        public final static List<String> TABLE_HEADER = Arrays.asList("Event Date", "Event", "Win %", "Home", "Draw", "Away");
        public final static List<String> SMART_TYPE_LIST = Arrays.asList("Group","Master");
    }

    public static class HandicapLiability{
        public final static List<String> TABLE_HEADER = Arrays.asList("Event", "Win %", "6 - 0", "5 - 0", "4 - 0", "3 - 0","2 - 0","1 - 0","DNB","0 - 1","0 - 2","0 - 3","0 - 4","0 - 5","0 - 6");
    }

    public static class HandicapCornerLiability{
        public final static List<String> TABLE_HEADER = Arrays.asList("Event", "Win %", "6 - 0", "5 - 0", "4 - 0", "3 - 0","2 - 0","1 - 0","DNB","0 - 1","0 - 2","0 - 3","0 - 4","0 - 5","0 - 6");
    }

    public static class OverUnderLiability{
        public final static List<String> TABLE_HEADER = Arrays.asList("Event", "Win %", "G - 0", "G - 1", "G - 2", "G - 3", "G - 4", "G - 5", "G - 6", "G - 7", "G - 8", "G - 9", "G - 10");
    }

    public static class OverUnderCornerLiability{
        public final static List<String> TABLE_HEADER = Arrays.asList("Event", "Win %", "G - 0", "G - 1", "G - 2", "G - 3", "G - 4", "G - 5", "G - 6", "G - 7", "G - 8", "G - 9", "G - 10");
    }

    public static class BBGPhoneBetting{
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "Account Code", "Bet Date", "Bet Type", "Selection", "HDP", "Live", "Price", "Stake", "Win/Lose", "CUR", "Trader");
    }

    public static class SPPPage{
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "Group Code","MP","PT%","Bets","Wins","Lose","Draw","MB","Avg Stake","Turnover","W/L","W/L [HKD]","Win%","CUR");
        public final static List<String> TABLE_HEADER_WITH_TAX = Arrays.asList("#", "Group Code","MP","PT%","Bets","Wins","Lose","Draw","MB","Avg Stake","Turnover","W/L","Tax","Net W/L","W/L [HKD]","Win%","CUR");
    }

    public static class BBTPage{
        public final static List<String> SMART_TYPE_LIST = Arrays.asList("Group","Master","Agent");
        public final static List<String> REPORT_TYPE_LIST = Arrays.asList("Pending Bets","Settled Bets");
    }

    public static class BBGPage{
        public final static List<String> SMART_TYPE_LIST = Arrays.asList("Group","Master","Agent");
        public final static List<String> REPORT_TYPE_LIST = Arrays.asList("Pending Bets","Settled Bets");
        public final static List<String> WIN_LOSE_TYPE_LIST = Arrays.asList("All", "Win Bets", "Lose Bets", "Draw Bets");
    }

    public static class CurrencyRates{
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "i","Currency Name","CUR","OP Rate");
    }

    public static class EventSchedule{
        public final static List<String> TABLE_HEADER_LEAGUE_LIST = Arrays.asList("D","Home Team","Away Team","Time","Live","N","Tv","Status");
        public final static List<String> TABLE_HEADER_SCHEDULE_LIST = Arrays.asList("#", "i","Date","Home Team","Away Team","Time","Live","N","Tv","Status","Action");
    }

    public static class ChartOfAccount{
        public final static List<String> TABLE_HEADER_DETAIL = Arrays.asList("#","Chart Code","Detail Type Name","Account Type","");
        public final static List<String> TABLE_HEADER_PARENT = Arrays.asList("#", "Parent Account Number","Parent Account Name", "");
        public final static List<String> TABLE_HEADER_SUB_ACCOUNT = Arrays.asList("#", "Sub-Account Number","Sub-Account Name","Client Ledger PM","CUR","");
    }

    public static class JournalEntries{
        public final static List<String> TYPE_LIST = Arrays.asList("Client","Bookie","Ledger");
        public final static List<String> LEVEL_LIST = Arrays.asList("[Choose one]","Super","Master","Agent","Player");
        public final static List<String> CURRENCY_LIST = Arrays.asList("[All]","AED","AUD","CAD","CNY","EUR","GBP","HKD","IDR","INR","JPY","KPW","KRW","LKR","MYR","PHP","PKR","SGD","THB","TWD","USD","VND","ZAR");
        public final static List<String> TRANSACTION_TYPE_LIST = Arrays.asList("[Choose One]","Payment Bookie","Payment Client","Payment Feed","Payment Operational","Payment Other","Payment Provider",
                "Received Bookie","Received Client","Received Comm/Rebate","Received Dividend/Share","Received Feed","Received Other","Contra Bookie","Contra Client","Contra Bookie Client","Contra CUR");
    }

    public static class JournalReports{
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "Transaction Type","Transaction ID","Transaction Date","Created By","Created Date","Memo/Description","Account Name",
                "Account Type","CUR","Foreign Debit","Foreign Credit","Debit in HKD","Credit in HKD");
        public final static List<String> DATE_TYPE = Arrays.asList("Created Date","Transaction Date");
        public final static List<String> TRANSACTION_TYPE_LIST = Arrays.asList("[All]","Payment Bookie","Payment Client","Payment Feed","Payment Operational","Payment Other","Payment Provider",
                "Received Bookie","Received Client","Received Comm/Rebate","Received Dividend/Share","Received Feed","Received Other","Contra Bookie","Contra Client","Contra Bookie Client","Contra CUR");
        public final static List<String> ACCOUNT_TYPE = Arrays.asList("Client","Bookie","Ledger","All");
    }

    public static class ConfirmBets{
        public final static List<String> STATUS_LIST = Arrays.asList("Pending","Confirmed");
        public final static List<String> SPORT_LIST = Arrays.asList("All","Soccer","Cricket","Basketball","Tennis","American Football","Ice Hockey");
        public final static List<String> DATE_TYPE_LIST = Arrays.asList("All Dates","Specific Date");
        public final static List<String> BET_TYPE_LIST = Arrays.asList("All");
        public final static List<String> TABLE_HEADER_ORDER = Arrays.asList("#","Event Date","Country","League","Event","Bet Date","Selection","Hdp","Live","Odds","B/L","Stake","BT","Trad","","","");
        public final static List<String> TABLE_HEADER_PENDING = Arrays.asList("Pending Accounts");
        public final static List<String> TABLE_HEADER_CONFIRMED = Arrays.asList("Confirmed Accounts");
    }

}



