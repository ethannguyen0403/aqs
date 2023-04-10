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
    public final static String TRADING = "Trading";
    public final static String ACCOUNTING = "Accounting";
    public final static String ROLE = "Role";
    public final static String USER = "User";
    public final static String CHART_OF_ACCOUNT = "Chart Of Account";
    public final static String GENERAL_REPORTS = "General Reports";
    public final static String SOCCER = "Soccer";
    public final static String ROLE_MANAGEMENT = "Role Management";
    public final static String TRADING_PERMISSION = "Trading Permission";
    public final static String LEAGUE_SEASON_TEAM_INFO = "League/Season/Team Info";
    public final static String USER_MANAGEMENT = "User Management";
    public final static String PT_RISK_CONTROL = "PT Risk Control";
    public final static String LEDGER_STATEMENT = "Ledger Statement";
    public final static String BOOKIE_STATEMENT = "Bookie Statement";
    public final static String JOURNAL_ENTRIES = "Journal Entries";
    public final static String CURRENCY_RATES = "Currency Rates";
    public final static String BET_ENTRY = "Bet Entry";
    public final static String RESULT_ENTRY = "Result Entry";
    public final static String SPORT= "Sport";
    public final static String CLIENT_STATEMENT = "Client Statement";
    public final static String SPP = "SPP";
    public final static String EVENT_SCHEDULE = "Event Schedule";
    public final static String CONFIRM_BETS = "Confirm Bets";
    public final static String BET_SETTLEMENT = "Bet Settlement";
    public final static String PLACE_BET_SUCCESS_MSG ="The bet was placed successfully";
    public final static String FINANCIAL_YEAR = "Year 2022-2023";
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

//    public static final Map<String, Double> CURRENCY_RATE= new HashMap<String, Double>()
//    {
//        {
//            put("AUD", 0.573028);
//            put("GBP", 1.0);
//            put("HKD",0.106288);
//            put("INR",0.010019);
//        }
//    };

    public static final Map<String, String> SOCCER_MARKET_TYPE_BET_LIST= new HashMap<String, String>()
    {
        {
            put("HDP", "Full Time Handicap");
        }
    };

    public static class BetEntryPage {
        public final static String MESSAGE_SUCCESS_MANUAL_BET ="Place Successful !";
    }

    public static class BetSettlement{
        public final static List<String> LST_MESSAGE_SETTLE_SENT_MAIL = Arrays.asList("Bet(s) is settled successfully.",
                "Statement Email has been sent to your mail box.");
        public final static List<String> BET_LIST_STATEMENT_EMAIL = Arrays.asList("Bet List [Current]",
                "Description", "Selection", "HDP", "Live", "Prive","Stake", "Win/Lose","Type","Date");
    }

    public static class AccountType {
        public final static String LEDGER = "Ledger";
        public final static String BOOKIE = "Bookie";
        public final static String CLIENT = "Client";
    }

    public static class TradingPermission{
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "i", "Username", "Auto-assigned All", "Permission", "Customers");
    }

    public static class LeagueSeasonTeamInfo{
        public final static List<String> LEAGUE_TABLE_HEADER = Arrays.asList("#", "i", "C", "League Name", "IsMain", "IsCup", "Edit", "");
        public final static List<String> SEASON_TABLE_HEADER = Arrays.asList("#", "i", "Season Name", "Start Date", "End Date", "Edit", "");
        public final static List<String> TEAM_TABLE_HEADER = Arrays.asList("#", "i", "Team Name", "Country", "Edit", "", "Under League");
    }

    public static class ResultEntry{
        public final static List<String> RESULT_SOCCER_TABLE_HEADER = Arrays.asList("#", "i", "Time", "Event", "", "Status", "HT Score", "FT Score", "HT Corner", "FT Corner", "HT Card", "FT Card");
    }

}
