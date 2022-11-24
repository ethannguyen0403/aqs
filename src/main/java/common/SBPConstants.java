package common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SBPConstants {
    public final static String MASTER = "Master";
    public final static String BOOKIE_INFO = "Bookie Info";
    public final static String CLIENT_SYSTEM = "Client System";
    public final static String TRADING = "Trading";
    public final static String BET_ENTRY = "Bet Entry";
    public final static String CONFIRM_BETS = "Confirm Bets";
    public final static String PLACE_BET_SUCCESS_MSG ="The bet was placed successfully";
    public static final Map<String, String> MAKETTYPE= new HashMap<String, String>() {
        {
            put("HDP", "HDP");
            put("FullTime", "FT");
            put("HalfTime", "HT");
            put("InRunning", "IR");
            put("OverUnder", "OU");
            put("HDP", "HDP");

        }
    };
    public static class BookieInfoPage {

    }

}
