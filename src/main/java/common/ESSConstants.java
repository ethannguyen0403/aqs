package common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ESSConstants {
    public final static String NO_RECORD_FOUND = "No record found";
    public final static String NO_RECORDS_FOUND = "No records found.";
    public final static String DASH_YYYY_MM_DD = "yyyy-MM-dd";
    public final static String DASH_DD_MM_YYYY = "dd-MM-yyyy";
    public final static String SLASH_DD_MM_YYYY = "dd/MM/yyyy";
    public final static String SLASH_YYYY_MM_DD = "yyyy/MM/dd";
    public final static String SLASH_MM_YYYY = "MM/yyyy";
    public final static String GMT_FOUR = "GMT-4";
    public final static String GMT_IST = "IST";
    public final static String CHANGE_PASSWORD = "Change Password";
    public final static String BTN_CLOSE = "Close";
    public final static String BTN_UPDATE = "Update";
    public final static String LOGOUT = "Logout";

    public static class BetOrderPage {
        public final static String CONFIRM ="Confirm";
        public final static String PENDING ="Pending";
        public final static String CANCELLED ="Cancelled";
        public final static String CANCEL ="Cancel";
        public final static String BET ="BET";
        public final static String BETS ="Bets";
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "Selection", "Action", "Market","Event Date","Event English","Event Chinese","Agent - Hitter","Bookie - OrderId");

    }
    public static class HomePage {
        public final static String NORECORD = "No record";
        public final static String ROLE = "Role";
        public final static String MANAGER = "Manager";
        public final static String MASTERACCOUNT = "Master Account";
        public final static String ACCOUNT = "Account";
        public final static String AQS = "AQS";
        public static final Map<String, String> EN_US= new HashMap<String, String>()
        {
            {
                put("AsianHandicap", "HDP");
                put("FullTime", "FT");
                put("HalfTime", "HT");
                put("InRunning", "IR");
                put("OverUnder", "OU");
                put("HDP", "HDP");

            }
        };
    }

    public static class LoginPage {
        public final static String LOGIN_TO_YOUR_ACCOUNT = "LOGIN TO YOUR ACCOUNT";
    }
    public static class OrderLog {
        public final static List<String> TABLE_HEADER = Arrays.asList("#", "Market", "Phase", "Event Date","Event","Selection","Agent - Hitter - Trader","Bookie - OrderId","Action Date","Action","Update By");
        public final static List<String> TABLE_HEADER_BET_LIST = Arrays.asList("#", "Account Code", "Bet Type", "Selection", "HDP", "Live", "Price", "Stake", "CUR", "Entry Date", "Betref Id");
    }
    public static class RolePage {
        public final static String ROLES = "Roles";
        public final static String PERMISSIONS = "Permissions";
        public final static List<String> PERMISSION_LIST = Arrays.asList("AQS View Only", "Account List", "Bet Order", "Delete Bet","Enable Bet List","Enable Copy to PS7","Enable Pending/Confirm/Cancel"
        ,"Master Account","Place Bet","Role","See All Orders","See My Own Orders","See User Management","View Cricket Orders","View Pending/Confirm/Cancel","View Soccer Orders");
        public final static List<String> ROLE_LIST = Arrays.asList("Administrator","Agent","Cricket Trader","Runner","Trader","Trader Soccer");
    }

    public static class ChangePassword {
        public final static String MESSAGE_SUCCESS = "Change password successfully.";
    }

}
