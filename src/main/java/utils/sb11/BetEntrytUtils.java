package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return WSUtils.getGETJSONArraytWithDynamicHeaders(api,null,headers);
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

}
