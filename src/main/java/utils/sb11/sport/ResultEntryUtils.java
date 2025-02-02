package utils.sb11.sport;

import com.paltech.constant.Configs;
import com.paltech.driver.DriverManager;
import com.paltech.utils.WSUtils;
import objects.Event;
import objects.Order;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static testcases.BaseCaseAQS.environment;

public class ResultEntryUtils {
    public static void setResultCricket(Event event, String status, String result){
        int homeScore = 0;
        int awayScore = 0;
        switch (result){
            case "Home Win":
                homeScore = 1;
                break;
            case "Away Win":
                awayScore = 1;
                break;
            case "Draw":
                homeScore = 1;
                awayScore = 1;
                break;
            default:
                System.out.println("Do not have this result");
        }
        String api = environment.getSbpLoginURL() + "aqs-bet-entry/manual/event";
        String jsn = String.format("[\n" +
                        "    {\n" +
                        "        \"eventId\": %s,\n" +
                        "        \"homeTeamName\": \"%s\",\n" +
                        "        \"awayTeamName\": \"%s\",\n" +
                        "        \"eventStatus\": \"%s\",\n" +
                        "        \"scores\": [\n" +
                        "            {\n" +
                        "                \"scoreType\": \"FT_RESULT\",\n" +
                        "                \"periodId\": 100,\n" +
                        "                \"typeId\": 700,\n" +
                        "                \"generalKey\": \"cricketResult\",\n" +
                        "                \"inputType\": \"dropdown\",\n" +
                        "                \"homeScore\": %s,\n" +
                        "                \"awayScore\": %s\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"sportId\": 2,\n" +
                        "        \"cricketTeamBatFirstSelection\": \"NOT_SET\",\n" +
                        "        \"checked\": true\n" +
                        "    }\n" +
                        "]"
                , Integer.valueOf(event.getEventId()),event.getHome(),event.getAway(),status,homeScore,awayScore);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("authorization", autho);
                put("content-type", Configs.HEADER_JSON);
                put("accept","application/json, text/plain, */*");
                put("Accept-Encoding","gzip, deflate, br");
                put("x-auth-user-id","330");
            }
        };

        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
        }catch (IOException e){
            System.out.println("Exception: IOException occurs at sendGETRequestDynamicHeaders");}
    }
}
