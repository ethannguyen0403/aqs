package utils.sb11.trading;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class StakeSizeGroupUtils {
    private static JSONArray getListGroupJSON(){
        JSONArray jsonArray = new JSONArray();
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        String api = String.format("%saqs-agent-service/stake-group/group/list?companyId=&clientId=",environment.getSbpLoginURL());
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        JSONObject jsonObject = WSUtils.getGETJSONObjectWithDynamicHeaders(api, headersParam) ;
        if (Objects.nonNull(jsonObject)) {
            jsonArray = jsonObject.getJSONArray("data");
        }
        return jsonArray;
    }
    public static int getGroupId(String groupName){
        int groupId = 0;
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = getListGroupJSON();
        } catch (Exception e) {
            e.getMessage();
        }
        if (jsonArray.length() > 0){
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("groupName").equals(groupName)){
                    groupId = jsonObject.getInt("groupId");
                }
            }
        }
        return groupId;
    }
    public static List<String> getLstAccCode(String groupName){
        List<String> lstAccCode = new ArrayList<>();
        int groupId = getGroupId(groupName);
        String url = String.format("%saqs-agent-service/stake-group/group/%d/accounts?accountCode=",environment.getSbpLoginURL(),groupId);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        JSONObject jsonObject = WSUtils.getGETJSONObjectWithDynamicHeaders(url,headersParam);
        if (Objects.nonNull(jsonObject)){
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsObject = jsonArray.getJSONObject(i);
                    lstAccCode.add(jsObject.getString("accountCode"));
                }
            }
        }
        return lstAccCode;
    }
}
