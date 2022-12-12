package common;

import java.util.HashMap;
import java.util.Map;

public class SBPConstants {
    public final static String MASTER = "Master";
    public final static String BOOKIE_INFO = "Bookie Info";
    public final static String CLIENT_SYSTEM = "Client System";
    public final static String TRADING = "Trading";
    public final static String BET_ENTRY = "Bet Entry";
    public final static String SPORT= "Sport";
    public final static String GENERAL_REPORTS = "General Reports";
    public final static String CLIENT_STATEMENT = "Client Statement";
    public final static String EVENT_SCHEDULE = "Event Schedule";
    public final static String CONFIRM_BETS = "Confirm Bets";
    public final static String PLACE_BET_SUCCESS_MSG ="The bet was placed successfully";
    public static final Map<String, String> CRICKET_MARKET_TYPE_BET_LIST= new HashMap<String, String>()
    {
        {
            put("1X2", "Match Betting");
            put("Match-HDP", "Match Handicap");
            put("OU", "Over/Under");
            put("DNB", "DrawNoBet");

        }
    };

    public static final Map<String, String> SOCCER_MARKET_TYPE_BET_LIST= new HashMap<String, String>()
    {
        {
            put("HDP", "Full Time Handicap");
        }
    };

    public static final Map<String, String> SOCCER_MORE_MARKET_TYPE_BET_LIST= new HashMap<String, String>()
    {
        {
            put("Full Time - 1x2", "1x2");
        }
    };
    public static class BetEntryPage {
        public final static String MESSAGE_SUCCESS_MANUAL_BET ="Place Successful !";
    }

}
