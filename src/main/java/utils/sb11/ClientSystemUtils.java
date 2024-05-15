package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class ClientSystemUtils {
    private static JSONArray getClientListJson(String clientCode, boolean withSuper, String companyName){
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() +"aqs-agent-service/master/client-list";
        int companyId = BetEntrytUtils.getCompanyID(companyName);
        String jsn = String.format("{\n" +
                        "    \"withSuper\": %s,\n" +
                        "    \"companyId\": %s,\n" +
                        "    \"sortBy\": \"clientName\",\n" +
                        "    \"status\": \"\",\n" +
                        "    \"name\": \"%s\",\n" +
                        "    \"currencyCode\": null\n" +
                        "  }\n"
                , withSuper, companyId, clientCode);
        return WSUtils.getPOSTJSONArrayWithDynamicHeaders(api, jsn, headersParam);
    }

    public static String getClientId(String clientCode,boolean withSuper, String companyName) {
        JSONArray jsonArr = null;
        String clientId = null;
        try {
            jsonArr = getClientListJson(clientCode,withSuper,companyName);
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
    public static List<String> getLstClientName(boolean withSuper, String companyName){
        JSONArray jsonArr = null;
        List<String> lstClientName = new ArrayList<>();
        try {
            jsonArr = getClientListJson("",withSuper,companyName);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    lstClientName.add(orderObj.getString("name"));
                }
            }
        }
        return lstClientName;
    }
}
