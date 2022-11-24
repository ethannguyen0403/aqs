package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import objects.Event;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;
import utils.aqs.GetOrdersUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class GetSoccerEventUtils {

    private static JSONObject getEventsAPIJson(String fromDate, String toDate, String sport) throws UnsupportedEncodingException {
        String json =  String.format("fromDate=%s 2012:00:00&toDate=%s 2011:59:59&timeZone=Asia/Bangkok&sportName=%s",fromDate,toDate,sport);
        json = json.replace(" ","%");
    //    json = java.net.URLEncoder.encode(json,"UTF-8");
        String api = String.format("%saqs-bet-entry/entry-bet/event-date?%s",environment.getSbpLoginURL(),json);
    //    WSUtils.getGETJSONArrayWithCookies(api, Configs.HEADER_JSON_CHARSET, DriverManager.getDriver().getCookies().toString(),Configs.HEADER_JSON);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
  //      WSUtils.getPOSTJSONObjectWithCookiesHasHeader(api, Configs.HEADER_FORM_URLENCODED, null, null,"","Authorization",autho);
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type",Configs.HEADER_FORM_URLENCODED);
            }
        };
        return  WSUtils.getPOSTJSONObjectWithDynamicHeaders(api,null,headers);

    }

    private static JSONArray getOrderofEventJson(String eventid){
        String api = String.format("%saqs-bet-entry/entry-bet/bet-log?eventId=%s",environment.getSbpLoginURL(),eventid);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type",Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONArraytWithDynamicHeaders(api,null,headers);
    }

    /*public static Order setOrderIdBasedBetrefID (Order order,String eventid){
        JSONArray arr = getOrderofEventJson(eventid);
        String betid= "";
        if(arr.length()>0) {
            for(int i =0; i < arr.length(); i ++) {
                JSONObject orderObj = arr.getJSONObject(i);
                betid = Long.toString(orderObj.getLong("betrefid"));
                if(betid.equals(order.getBetId())){
                    order.setOrderId(orderObj.getString("orderId"));
                    break;
                }
            }
        }
        return order;
    }*/
    public static List<Order> setOrderIdBasedBetrefIDForListOrder (List<Order> lstOrder,String eventid){
        JSONArray arr = getOrderofEventJson(eventid);
        int orderIndex = 0;
        String betid= "";
        if(arr.length()>0) {
            for(int i =0; i < arr.length(); i ++) {
                if(orderIndex >= lstOrder.size())
                    return lstOrder;
                JSONObject orderObj = arr.getJSONObject(i);
                betid = Long.toString(orderObj.getLong("betrefid"));
                if(betid.equals(lstOrder.get(orderIndex).getBetId())){
                    lstOrder.get(orderIndex).setOrderId(orderObj.getString("orderId"));
                    orderIndex = orderIndex +1;
                }
            }
        }
        return lstOrder;
    }

    public static Event getFirstEvent(String fromDate, String toDate, String sport, String league) {
        JSONObject jsonObject = null;
        try {
            jsonObject = getEventsAPIJson(fromDate, toDate, sport);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(Objects.nonNull(jsonObject)){
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
                        .build();
            }

        }
        return null;
    }

    public static Order getOrderInfoById(List <Order> lsOrder,String orderId){
        for(Order order:lsOrder){
            if(order.getOrderId().equals(orderId))
                return order;
        }
        return null;
    }

}
