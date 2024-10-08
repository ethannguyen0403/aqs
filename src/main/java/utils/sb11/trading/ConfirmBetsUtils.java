package utils.sb11.trading;

import com.google.common.collect.Lists;
import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import common.SBPConstants;
import objects.Order;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static testcases.BaseCaseAQS.environment;

public class ConfirmBetsUtils {
    public static void confirmBetAPI(Order order){
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-agent-service/trading/confirm";
        String jsn = String.format("{\n" +
                        "    \"bets\": [\n" +
                        "        {\n" +
                        "            \"orderId\": %s\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"
                , order.getBetId());
        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
        }catch (IOException e){
            System.out.println("Exception: IOException occurs at sendGETRequestDynamicHeaders");}

    }
    public static void confirmBetAPI(List<Order> lstOrder){
        for (int i = 0; i < lstOrder.size(); i++){
            confirmBetAPI(lstOrder.get(i));
        }
    }
}
