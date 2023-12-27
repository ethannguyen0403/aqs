package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.driver.DriverManager;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static testcases.BaseCaseAQS.environment;

public class CompanySetUpUtils {
    public static JSONArray getCompanySetupJson(){
        String api = environment.getSbpLoginURL() + "aqs-agent-service/company-set-up/list";
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONArraytWithDynamicHeaders(api, headersParam);
    }
    public static String getCurrency(String companyUnitName){
        String currencyCU = null;
        JSONArray jsonArray = getCompanySetupJson();
        if (!jsonArray.isEmpty()){
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("companyName").equals(companyUnitName)){
                    currencyCU = obj.getString("currencyCode");
                }
            }
        }
        return currencyCU;
    }
}
