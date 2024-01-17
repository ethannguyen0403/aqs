package pages.sb11.restapi;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestAPI {
    /**
     * @param eventDate: format yyyy-MM-dd
     */
    public static JSONArray sendRequestByEventDate(String eventDate){
        String api = String.format("https://aqsapi.beatus88.com/aqs-api/v1/cricket-pnl?eventDate=%s",eventDate);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONArraytWithDynamicHeaders(api,headersParam);
    }
    public static JSONArray sendRequestByAccountCode(String eventDate, String accountCode){
        String api = String.format("https://aqsapi.beatus88.com/aqs-api/v1/cricket-pnl?eventDate=%s&accountCode=%s",eventDate,accountCode);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONArraytWithDynamicHeaders(api,headersParam);
    }

    public static boolean isBetSettleDisplay(JSONArray jsonArray, Order order) {
        if (Objects.nonNull(jsonArray)){
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject orderObj = jsonArray.getJSONObject(i);
                    if (orderObj.getString("eventId").equals(order.getEvent().getEventId()) && orderObj.getString("accountCode").equals(order.getAccountCode())
                    && orderObj.getString("home").equals(order.getEvent().getHome()) && orderObj.getString("away").equals(order.getEvent().getAway())) {
                        return true;
                    }
                }
            }
        }
        System.out.println("FAILED! Bet have been settle that does not display");
        return false;
    }
    /**
     * @param homeOrAwayWL: add value homeWinLose or awayWinLose
     */
    public static String getWinLose(JSONArray jsonArray, Order order, String homeOrAwayWL) {
        String winlose = null;
        if (Objects.nonNull(jsonArray)){
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject orderObj = jsonArray.getJSONObject(i);
                    if (orderObj.getString("eventId").equals(order.getEvent().getEventId()) && orderObj.getString("accountCode").equals(order.getAccountCode())
                            && orderObj.getString("home").equals(order.getEvent().getHome()) && orderObj.getString("away").equals(order.getEvent().getAway())) {
                        winlose = String.valueOf(orderObj.getDouble(homeOrAwayWL));
                    }
                }
            }
        }
        return winlose;
    }
}
