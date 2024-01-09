package utils.sb11;

import com.google.common.collect.Lists;
import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import objects.Event;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class BetEntrytUtils {

    public static String convertToDate(String date, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date  d = formatter.parse(date);
            return formatter.format(d);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }


    /**
     * Get all order of the event by API
     * @param eventid
     * @return the list api order
     */
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
        return WSUtils.getGETJSONArraytWithDynamicHeaders(api,headers);
    }

    /**
     * After place bet, there are info need to update to expected order like betID, OrderID, Create Date
     * @param lstOrder
     * @return the lstOrder with updated info
     */
    public static List<Order> setOrderIdBasedBetrefIDForListOrder (List<Order> lstOrder){
        String eventId = lstOrder.get(0).getEvent().getEventId();
        JSONArray arr = getOrderofEventJson(eventId);
        int orderIndex = 0;
        List<Order> lstOrderReverse = Lists.reverse(lstOrder);
        String betid= "";
        if(arr.length()>0) {
            for(int i =0; i < arr.length(); i ++) {
                if(orderIndex >= lstOrder.size())
                    return Lists.reverse(lstOrderReverse);
                JSONObject orderObj = arr.getJSONObject(i);
                if(orderObj.getString("account").equals(lstOrderReverse.get(orderIndex).getAccountCode())) {
                    lstOrderReverse.get(orderIndex).setBetId(Long.toString(orderObj.getLong("betrefid")));
                    lstOrderReverse.get(orderIndex).setOrderId(orderObj.getString("orderId"));
                    lstOrderReverse.get(orderIndex).setCreateDate(orderObj.getString("entriDate"));
                    orderIndex = orderIndex +1;
                }
            }
        }
        return lstOrder;
    }
    public static void placeBetAPI(Order order){
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-bet-entry/entry-bet/place";
        String jsn = String.format("{\"cloneOrderType\":\"NONE\",\"orders\":[{\"" +
                        "eventId\":%s," +
                        "\"marketName\":\"%s\"," +
                        "\"marketType\":\"%s\"," +
                        "\"fixtureStart\":\"%sT10:00:00.000+00:00\"," +
                        "\"selection\":\"%s\"," +
                        "\"clientMetadata\":\"\"," +
                        "\"fixtureHome\":\"%s\"," +
                        "\"fixtureCompetition\":\"%s\"," +
                        "\"agentName\":\"Auto-Account01\"," +
                        "\"fixtureAway\":\"%s\"," +
                        "\"fixtureSport\":\"%s\"," +
                        "\"stage\":\"%s\"," +
                        "\"handicap\":0," +
                        "\"odds\":%s," +
                        "\"oddsType\":\"%s\"," +
                        "\"stake\":%s," +
                        "\"betType\":\"%s\"}]}"
                , order.getEvent().getEventId(), order.getMarketName(), order.getMarketType(), order.getEvent().getEventDate(),order.getSelection(),order.getEvent().getHome(),order.getEvent().getLeagueName(),
                order.getEvent().getAway(),order.getEvent().getSportName(),order.getStage(),order.getOdds(),order.getOddType(),order.getRequireStake(),order.getBetType());
        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
        }catch (IOException e){
            System.out.println("Exception: IOException occurs at sendGETRequestDynamicHeaders");
        }
    }

    public static void placeManualBetAPI(int companyId, String accountId, String sportId, Order order) {
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-bet-entry/account/place-order";
        String jsn = String.format("{\n" +
                        "    \"companyId\": %s,\n" +
                        "    \"accountId\": \"%s\",\n" +
                        "    \"accountCode\": \"%s\",\n" +
                        "    \"commission\": 0,\n" +
                        "    \"winLose\": 5,\n" +
                        "    \"description\": \"Manual Bet Description\",\n" +
                        "    \"stake\": %s,\n" +
                        "    \"transactionDate\": \"%s\",\n" +
                        "    \"startDate\": \"%s\",\n" +
                        "    \"timeZone\": \"Asia/Bangkok\",\n" +
                        "    \"accountType\": \"\",\n" +
                        "    \"selection\": \"%s\",\n" +
                        "    \"marketType\": \"HDP\",\n" +
                        "    \"odds\": %s,\n" +
                        "    \"oddsType\": \"%s\",\n" +
                        "    \"type\": \"Back\",\n" +
                        "    \"sportId\": \"%s\"\n" +
                        "  }\n"
                , companyId, accountId, order.getAccountCode(), order.getRequireStake(),order.getCreateDate(),order.getEventDate(),order.getSelection(),order.getPrice(),order.getOddType(),sportId);
        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
        }catch (IOException e){
            System.out.println("Exception: IOException occurs at sendGETRequestDynamicHeaders");
        }
    }

    public static JSONArray getCompanyListJson() {
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = String.format("%saqs-agent-service/master/company/list",environment.getSbpLoginURL());

        return WSUtils.getGETJSONArraytWithDynamicHeaders(api, headersParam);
    }

    public static int getCompanyID(String companyName) {
        JSONArray jsonArr = null;
        int companyId = 0;
        try {
            jsonArr = getCompanyListJson();
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("companyName").equals(companyName)) {
                        companyId = orderObj.getInt("companyId");
                        return companyId;
                    }
                }
            }
        }
        return companyId;
    }
}
