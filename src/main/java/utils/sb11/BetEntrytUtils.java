package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class BetEntrytUtils {

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
        String betid= "";
        if(arr.length()>0) {
            for(int i =0; i < arr.length(); i ++) {
                if(orderIndex >= lstOrder.size())
                    return lstOrder;
                JSONObject orderObj = arr.getJSONObject(i);
                if(orderObj.getString("account").equals(lstOrder.get(orderIndex).getAccountCode())) {
                    lstOrder.get(orderIndex).setBetId(Long.toString(orderObj.getLong("betrefid")));
                    lstOrder.get(orderIndex).setOrderId(orderObj.getString("orderId"));
                    lstOrder.get(orderIndex).setCreateDate(orderObj.getString("entriDate"));
                    orderIndex = orderIndex +1;
                }
            }
        }
        return lstOrder;
    }

    public static void placeManualBetAPI(int companyId, String accountId, String sportId, Order order) throws IOException {
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
        WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
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
