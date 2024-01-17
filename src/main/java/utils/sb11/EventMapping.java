package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import objects.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class EventMapping {
    private static JSONObject filterInfor(String date, String provider){
        String json = String.format("{\"date\":\"%s\",\"leagueName\":\"\",\"eventName\":\"\",\"providerLeagueName\":\"\",\"providerEventName\":\"\",\"filter\":true,\"unmap\":true,\"mapped\":true,\"sport\":\"Cricket\",\"provider\":\"%s\",\"providerEventDate\":\"%s\",\"timeZone\":\"Asia/Bangkok\"}", date, provider, date);
        String api = String.format("%saqs-agent-service/event-mapping/list",environment.getSbpLoginURL());
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return  WSUtils.getPOSTJSONObjectWithDynamicHeaders(api, json,headers);
    }
    /**
     * @param date should be format yyyy-MM-dd hh:mm:ss*/
    public static String getEventID(Event event, String provider, String date){
        JSONObject jsonObject = null;
        String eventID = null;
        try {
            jsonObject = filterInfor(date, provider);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonObject)) {
            if (jsonObject.length() > 0) {
                JSONArray jsonArrayEvent = jsonObject.getJSONArray("events");
                if(jsonArrayEvent.length()>0){
                    for (int i = 0; i < jsonArrayEvent.length(); i++) {
                        JSONObject eventObject = jsonArrayEvent.getJSONObject(i);
                        if(eventObject.getString("homeTeam").equals(event.getHome())){
                            eventID = String.valueOf(eventObject.getInt("eventId"));
                        }
                    }
                }

            }
        }
        return eventID;
    }
    public static Event getFirstProviderEvent(String provider, String date){
        JSONObject jsonObject = null;
        Event event = null;
        try {
            jsonObject = filterInfor(date, provider);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonObject)) {
            if (jsonObject.length() > 0) {
                JSONArray jsonArrayEvent = jsonObject.getJSONArray("lcEvents");
                JSONObject eventObject = jsonArrayEvent.getJSONObject(0);
                event = new Event.Builder()
                        .eventId(String.valueOf(eventObject.getInt("providerEventId")))
                        .home(eventObject.getString("homeTeam"))
                        .away(eventObject.getString("awayTeam"))
                        .leagueName(eventObject.getString("leagueName"))
                        .eventDate(eventObject.getString("matchDate"))
                        .build();
            }
        }
        return event;
    }

    public static void mappingEvent(String eventID, Event eventProvider, String provider, String sportName){
        String json = String.format("{\"eventId\":%s,\"providerEventId\":%s,\"homeTeam\":\"%s\",\"awayTeam\":\"%s\",\"league\":\"%s\",\"matchDate\":\"%s\",\"provider\":\"%s\",\"sport\":\"%s\"}",
                Integer.valueOf(eventID),Integer.valueOf(eventProvider.getEventId()),eventProvider.getHome(),eventProvider.getAway(),eventProvider.getLeagueName(),eventProvider.getEventDate(),provider,sportName);
        String api = String.format("%saqs-agent-service/event-mapping/map",environment.getSbpLoginURL());
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api, json,headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unMappingEvent(String eventID, Event eventProvider, String provider){
        String json = String.format("{\"eventId\":%s,\"providerEventId\":%s,\"provider\":\"%s\",\"mapMode\":\"AUTO\",\"type\":\"UNMAP\"}",
                Integer.valueOf(eventID),Integer.valueOf(eventProvider.getEventId()),provider);
        String api = String.format("%saqs-agent-service/event-mapping/unmap",environment.getSbpLoginURL());
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api, json,headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
