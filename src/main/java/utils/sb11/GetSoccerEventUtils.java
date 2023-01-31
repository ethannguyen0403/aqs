package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import objects.Event;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class GetSoccerEventUtils {

    private static JSONObject getEventsAPIJson(String fromDate, String toDate, String sport) throws UnsupportedEncodingException {
        String json =  String.format("fromDate=%s 2012:00:00&toDate=%s 2011:59:59&timeZone=Asia/Bangkok&sportName=%s",fromDate,toDate,sport);
        json = json.replace(" ","%");
        String api = String.format("%saqs-bet-entry/entry-bet/event-date?%s",environment.getSbpLoginURL(),json);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type",Configs.HEADER_FORM_URLENCODED);
            }
        };
        return  WSUtils.getPOSTJSONObjectWithDynamicHeaders(api,null,headers);
    }

    public static Event getFirstEvent(String fromDate, String toDate, String sport, String league) {
        JSONObject jsonObject = null;
        try {
            jsonObject = getEventsAPIJson(fromDate, toDate, sport);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(Objects.nonNull(jsonObject)){
            if(!jsonObject.has(league))
                return null;
            JSONArray resultArr  = jsonObject.getJSONArray(league);
            if(resultArr.length()>0) {
                JSONObject orderObj = resultArr.getJSONObject(0);
                return new Event.Builder()
                        .leagueName(league)
                        .home(orderObj.getString("homeTeamName"))
                        .away(orderObj.getString("awayTeamName"))
                        .eventDate(orderObj.getString("eventDate"))
                        .startDate(orderObj.getString("startDate"))
                        .openTime(orderObj.getString("openTime"))
                        .eventId(Long.toString(orderObj.getLong("eventId")))
                        .sportName(sport)
                        .leagueName(league)
                        .build();
            }
        }
        return null;
    }

    public static Event getRandomEvent(String fromDate, String toDate, String sport, String league) {
        Random rd = new Random();
        JSONObject jsonObject = null;
        try {
            jsonObject = getEventsAPIJson(fromDate, toDate, sport);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(Objects.nonNull(jsonObject)){
            if(!jsonObject.has(league))
                return null;
            JSONArray resultArr  = jsonObject.getJSONArray(league);
            int arrLength = resultArr.length();
            if(resultArr.length()>0) {
                int numb = rd.nextInt(arrLength-0)+0;
                JSONObject orderObj = resultArr.getJSONObject(numb);
                return new Event.Builder()
                        .leagueName(league)
                        .home(orderObj.getString("homeTeamName"))
                        .away(orderObj.getString("awayTeamName"))
                        .eventDate(orderObj.getString("eventDate"))
                        .startDate(orderObj.getString("startDate"))
                        .openTime(orderObj.getString("openTime"))
                        .eventId(Long.toString(orderObj.getLong("eventId")))
                        .sportName(sport)
                        .leagueName(league)
                        .build();
            }
        }
        return null;
    }

    public static Event setEventID(Event event) {
        JSONObject jsonObject = null;
        String date = DateUtils.formatDate(event.getEventDate(),"dd/MM/yyyy","yyyy-MM-dd");
        try {
            jsonObject = getEventsAPIJson(date, date, event.getSportName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(Objects.nonNull(jsonObject)){
            JSONArray resultArr  = jsonObject.getJSONArray(event.getLeagueName());
            if(resultArr.length()>0) {
                for(int i =0; i < resultArr.length(); i ++) {
                    JSONObject orderObj = resultArr.getJSONObject(i);
                    if(orderObj.getString("homeTeamName").equals(event.getHome()) && orderObj.getString("awayTeamName").equals(event.getAway())){
                        event.setEventId(Long.toString(orderObj.getLong("eventId")));
                        event.setEventDate(orderObj.getString("startDate"));
                        return event;
                    }
                }
            }
        }
        return event;
    }

}
