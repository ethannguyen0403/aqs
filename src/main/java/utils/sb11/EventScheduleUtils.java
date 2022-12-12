package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import objects.Event;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.SBPConstants.SPORT_MAP;
import static testcases.BaseCaseAQS.environment;

public class EventScheduleUtils {

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
        return  WSUtils.getGETJSONArraytWithDynamicHeaders(api,null,headers);
    }

    /**
     * Get All league of a sport
     * @param sportID
     * @return the list api of League
     */
    private static JSONArray getAllLeague(String sportID){
        String api = String.format("%saqs-agent-service/event/league/event?sportId=%s",environment.getSbpLoginURL(),sportID);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type",Configs.HEADER_FORM_URLENCODED);
            }
        };
        return  WSUtils.getGETJSONArraytWithDynamicHeaders(api,null,headers);
    }




}
