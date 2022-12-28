package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientSystemUtils {
    private static JSONArray getClientListJson(String clientCode){
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = "https://ess.beatus88.com/aqs-agent-service/master/client-list";
        String jsn = String.format("{\n" +
                        "    \"withSuper\": true,\n" +
                        "    \"companyId\": 1,\n" +
                        "    \"sortBy\": \"clientName\",\n" +
                        "    \"status\": \"\",\n" +
                        "    \"name\": \"%s\",\n" +
                        "    \"currencyCode\": null\n" +
                        "  }\n"
                , clientCode);
        return WSUtils.getPOSTJSONArrayWithDynamicHeaders(api, jsn, headersParam);
    }

    public static String getClientId(String clientCode) {
        JSONArray jsonArr = null;
        String clientId = null;
        try {
            jsonArr = getClientListJson(clientCode);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("name").equals(clientCode)) {
                        clientId =  (String.valueOf(orderObj.getInt("id")));
                        return clientId;
                    }
                }
            }
        }
        return clientId;
    }

}
