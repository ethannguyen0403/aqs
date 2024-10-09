package utils.sb11.user;

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

public class UserManagementUtils {
    public static int getUserID(String username){
        int userID;
        String api = String.format("%saqs-agent-service/user/list-all-user?type=123sbasia",environment.getSbpLoginURL());
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        JSONArray jsonArray = WSUtils.getGETJSONArraytWithDynamicHeaders(api,headers);
        if (Objects.nonNull(jsonArray)){
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject orderObj = jsonArray.getJSONObject(i);
                    if (orderObj.getString("username").equals(username)) {
                        userID =  orderObj.getInt("userId");
                        return userID;
                    }
                }
            }
        }
        return userID = -1;
    }

    /**
     *
     * @param status input "ACTIVE" or "CLOSED"
     * @param role input "3": Administrator
     *             "6": Accounts
     *             "40": one role
     */
    public static void editUserManagament(String username, String fullName, String status, String role, boolean permission){
        int userId = getUserID(username);
        String permiss = "";
        String removePer = "";
        if (permission){
            permiss = "\"HEAD_FINANCE\"";
        } else {
            removePer = "\"HEAD_FINANCE\"";
        }
        String api = String.format("%saqs-agent-service/user/addEdit?type=123sbasia",environment.getSbpLoginURL());
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_JSON);
            }
        };

        String jsn = String.format("{\"userId\":%s,\"fullName\":\"%s\",\"status\":\"%s\",\"password\":\"\",\"role\":\"%s\",\"permissions\":[%s],\"removingPermissions\":[%s]}",
                userId, fullName, status,role,permiss,removePer);
        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api,jsn,headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
