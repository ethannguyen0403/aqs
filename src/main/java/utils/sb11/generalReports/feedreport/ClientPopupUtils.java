package utils.sb11.generalReports.feedreport;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class ClientPopupUtils {
    private static JSONArray getLstInfor(){
        String api = environment.getSbpLoginURL() + "aqs-agent-service/feed-report/client-list?companyId=1";
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
    private static String getClientFeedId(String clientName) throws IOException {
        String clientFeedId = null;
        JSONArray jsonArray = null;
        jsonArray = getLstInfor();
        if (Objects.nonNull(jsonArray)){
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("clientName").equals(clientName)){
                    clientFeedId = jsonObject.getString("clientFeedId");
                }
            }
        }
        return clientFeedId;
    }
    public static void deleteClient(String clientName){
        String clientFeedId = null;
        try {
            clientFeedId = getClientFeedId(clientName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String api = String.format("%saqs-agent-service/feed-report/delete-client?clientFeedId=%s",environment.getSbpLoginURL(),clientFeedId);
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
