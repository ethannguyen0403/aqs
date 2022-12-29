package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class AccountSearchUtils {
    private static JSONObject getAccountInfoJson(String accountCode){
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() +"aqs-agent-service/master/account-search/list";
        String jsn = String.format("{\n" +
                        "    \"company\": 1,\n" +
                        "    \"code\": \"%s\",\n" +
                        "    \"type\": \"CODE\"\n" +
                        "  }\n"
                , accountCode);
        return WSUtils.getPOSTJSONObjectWithDynamicHeaders(api, jsn, headersParam);
    }

    public static String getAccountId(String accountCode) {
        JSONObject jsonObj = null;
        String accountId = null;
        try {
            jsonObj = getAccountInfoJson(accountCode);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonObj)) {
            JSONObject accountObj  = jsonObj.getJSONObject("account");
            JSONArray resultArr = accountObj.toJSONArray(accountObj.names());
            if (resultArr.length() > 0) {
                for (int i = 0; i < resultArr.length(); i++) {
                        if(resultArr.get(i).equals(accountCode)){
                        accountId = (String.valueOf(resultArr.get(i-1)));
                        return accountId;
                    }
                }
            }
        }
        return accountId;
    }

}
