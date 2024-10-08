package utils.sb11.generalReports.feedreport;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class ProviderPopupUtils {
    private static JSONArray getLstInfor(){
        String api = environment.getSbpLoginURL() + "aqs-agent-service/feed-report/provider-list?companyId=1";
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_FORM_URLENCODED);
            }
        };
        return WSUtils.getGETJSONArraytWithDynamicHeaders(api,headers);
    }
    private static String getProviderFeedId(String providerName) throws IOException {
        String providerFeedId = null;
        JSONArray jsonArray = null;
        jsonArray = getLstInfor();
        if (Objects.nonNull(jsonArray)){
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("providerName").equals(providerName)){
                    providerFeedId = jsonObject.getString("providerFeedId");
                }
            }
        }
        return providerFeedId;
    }
    public static void deleteProvider(String providerName){
        String providerFeedId = null;
        try {
            providerFeedId = getProviderFeedId(providerName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String api = String.format("%saqs-agent-service/feed-report/delete-provider?providerFeedId=%s",environment.getSbpLoginURL(),providerFeedId);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_FORM_URLENCODED);
            }
        };
        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api,"",headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
