package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class BetSettlementUtils {

    public static JSONArray getOrderConfirmedListJson(String accountId, String sportId, Order order) throws IOException {
        if (sportId.equals(""))
        {
            sportId = "0";
        }
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-bet-entry/account/place-order";
        String jsn = String.format("{\n" +
                        "    \"fromDatePS7\":\"%s\",\n" +
                        "    \"status\": \"CONFIRMED\",\n" +
                        "    \"dateMode\": \"ALL_DATE\",\n" +
                        "    \"toDatePS7\": \"%s\",\n" +
                        "    \"accCodeStartWith\": \"\",\n" +
                        "    \"accCode\": \"%s\",\n" +
                        "    \"marketType\": \"\",\n" +
                        "    \"accType\": \"BET\",\n" +
                        "    \"bookieId\": \"\",\n" +
                        "    \"timeZone\": \"Asia/Bangkok\",\n" +
                        "    \"accountId\": \"%s\",\n" +
                        "    \"sportId\": \"%s\"\n" +
                        "  }\n"
                , order.getCreateDate(), order.getCreateDate(), order.getAccountCode(), accountId,sportId);
        return WSUtils.getPOSTJSONArrayWithDynamicHeaders(api, jsn, headersParam);
    }

    public static int getConfirmedBetId(String accountId, String sportId, Order order) {
        JSONArray jsonArr = null;
        int betId = 0;
        try {
            jsonArr = getOrderConfirmedListJson(accountId, sportId, order);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("selection").equals(order.getSelection())) {
                        betId = orderObj.getInt("id");
                        return betId;
                    }
                }
            }
        }
        return betId;
    }
}
