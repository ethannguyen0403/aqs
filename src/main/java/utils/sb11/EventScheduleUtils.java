package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;
import static common.SBPConstants.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class EventScheduleUtils {

    /**
     * @param dateAPI should follow format yyyy-MM-dd
     * */
    public static void addEventByAPI(String awayId, String homeId, String leagueId, String dateAPI, String sportID, String status) {
        try {
            String bearerToken = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
            Map<String, String> headersParam = new HashMap<String, String>() {
                {
                    put("Authorization", bearerToken);
                    put("Content-Type", Configs.HEADER_JSON);
                }
            };
            String endPoint = String.format("%saqs-agent-service/event/event/add", environment.getSbpLoginURL());
            String jsonBody = buildJsonPayload(awayId, homeId, leagueId, dateAPI, sportID, status);
            WSUtils.sendPOSTRequestDynamicHeaders(endPoint, jsonBody, headersParam);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getEventID(String date, String leagueId){
        JSONObject jsonObject = null;
        String eventID = "";
        try {
            jsonObject = getEventByLeague(date, leagueId);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonObject)) {
            if (jsonObject.length() > 0) {
                   JSONArray jsonArrayEvent = jsonObject.getJSONArray("listEvents");
                   if(jsonArrayEvent.length()>0){
                       for (int i = 0; i < jsonArrayEvent.length(); i++) {
                           JSONObject eventObject = jsonArrayEvent.getJSONObject(i);
                           if(eventObject.getInt("leagueId")==Integer.valueOf(leagueId)){
                               JSONArray eventsArray = eventObject.getJSONArray("events");
                               eventID = String.valueOf(eventsArray.getJSONObject(0).getInt("eventId"));
                           }
                       }
                   }

            }
        }
        return eventID;
    }

    public static String getTeamID(String teamName, String leagueID) {
        JSONArray jsonArray = null;
        String teamID = "";
        try {
            jsonArray = getTeamList(leagueID);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArray)) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("teamName").equalsIgnoreCase(teamName)) {
                    teamID = String.valueOf(jsonObject.getInt("teamId"));
                }
            }
        }
        return teamID;
    }

    public static String getLeagueID(String leagueName, String sportID) {
        JSONArray jsonArray = null;
        String leagueID = "";
        try {
            jsonArray = getAllLeague(sportID);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArray)) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("leagueName").equalsIgnoreCase(leagueName)) {
                    leagueID = String.valueOf(jsonObject.getInt("leagueId"));
                }
            }
        }
        return leagueID;
    }

    public static void deleteEventByAPI(String eventId) {
        try {
            String json = String.format("eventId=%s", eventId);
            String url = String.format("%saqs-agent-service/event/delete/event?%s", environment.getSbpLoginURL(), json);
            String bearerToken = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
            Map<String, String> headersParam = new HashMap<String, String>() {
                {
                    put("Authorization", bearerToken);
                    put("Content-Type", Configs.HEADER_JSON);
                }
            };
            WSUtils.sendPOSTRequestDynamicHeaders(url, null, headersParam);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static JSONObject getEventByLeague(String date, String leagueId){
        String json = String.format("leagueIds=%s&seasonId=0&currentPage=1&pageSize=10&searchName=&searchBy=home&openDate=%s 2014:34:16&timeZone=Asia/Saigon", leagueId, date);
        json = json.replace(" ","%");
        String api = String.format("%saqs-agent-service/event/event/by-league?%s",environment.getSbpLoginURL(),json);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type",Configs.HEADER_FORM_URLENCODED);
            }
        };
        return  WSUtils.getPOSTJSONObjectWithDynamicHeaders(api, null,headers);
    }


    /**
     * Get all order of the event by API
     * @param date in format yyyy-MM-dd
     * @return the list api order
     */
    private static JSONArray getEventByDate(String date,String sportID){
        String json =  String.format("event-date=%s 2015:51:07&timeZone=Asia/Bangkok&sportId=%s",date,sportID);
        json = json.replace(" ","%");
        String api = String.format("%saqs-agent-service/event/league/event?%s",environment.getSbpLoginURL(),json);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type",Configs.HEADER_FORM_URLENCODED);
            }
        };
        return  WSUtils.getGETJSONArraytWithDynamicHeaders(api,headers);
    }

    /**
     * Get All league of a sport
     * @param sportID
     * @return the list api of League
     */
    private static JSONArray getAllLeague(String sportID){
        String api = String.format("%saqs-agent-service/event/all-league?sportId=%s",environment.getSbpLoginURL(),sportID);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type",Configs.HEADER_JSON);
            }
        };
        return  WSUtils.getGETJSONArraytWithDynamicHeaders(api,headers);
    }

    private static JSONArray getTeamList(String leagueID) {
        String api = String.format("%saqs-agent-service/event/team/list?leagueId=%s&seasonId=0", environment.getSbpLoginURL(), leagueID);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONArraytWithDynamicHeaders(api, headers);
    }

    private static String buildJsonPayload(String awayId, String homeId, String leagueId,String dateAPI, String sportID, String status){
        return String.format("[{\n" +
                "  \"away\": \"\",\n" +
                "  \"awayTeam\": \"%s\",\n" +
                "  \"eventId\": 0,\n" +
                "  \"home\": \"\",\n" +
                "  \"homeTeam\": \"%s\",\n" +
                "  \"leagueId\": %s,\n" +
                "  \"live\": false,\n" +
                "  \"liven\": false,\n" +
                "  \"livetv\": \"TV\",\n" +
                "  \"openDate\": \"%s\",\n" +
                "  \"startDate\": \"%s 10:00:00\",\n" +
                "  \"openDatel\": \"%sT06:45:27.885Z\",\n" +
                "  \"openTime\": \"10:00\",\n" +
                "  \"sportId\": %s,\n" +
                "  \"status\": \"%s\",\n" +
                "  \"idxValue\": 0,\n" +
                "  \"seasonId\": 0,\n" +
                "  \"userTz\": -7\n" +
                "}]", awayId, homeId, leagueId, dateAPI, dateAPI, dateAPI, sportID, status);
    }
}
