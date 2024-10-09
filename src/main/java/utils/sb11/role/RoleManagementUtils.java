package utils.sb11.role;

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

public class RoleManagementUtils {
    private static int getRoleId (String roleName){
        int roleId = 0;
        String api = String.format("%saqs-agent-service/user/list-role?type=123sbasia",environment.getSbpLoginURL());
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        JSONArray jsonArray = WSUtils.getGETJSONArraytWithDynamicHeaders(api,headersParam);
        if(Objects.nonNull(jsonArray)){
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("roleName").equals(roleName)){
                    roleId = jsonObject.getInt("roleId");
                }
            }
        }
        return roleId;
    }
    private static int getPermissionId (String permissionName){
        int permissionId = 0;
        String api = String.format("%saqs-agent-service/role/all-permission?type=123sbasia",environment.getSbpLoginURL());
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        JSONArray jsonArray = WSUtils.getGETJSONArraytWithDynamicHeaders(api,headersParam);
        if(Objects.nonNull(jsonArray)){
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("permissionName").equals(permissionName)){
                    permissionId = jsonObject.getInt("permissionId");
                }
            }
        }
        return permissionId;
    }

    /**
     *
     * @param roleName
     * @param permissionName
     * @param status input INACTIVE or ACTIVE
     */
    public static void updateRolePermission(String roleName, String permissionName, String status){
        int roleId = 0;
        int permissionId = 0;
        try {
            roleId = getRoleId(roleName);
            permissionId = getPermissionId(permissionName);
        } catch (Exception e) {
            e.getMessage();
        }
        String api = String.format("%saqs-agent-service/role/update-role-permission",environment.getSbpLoginURL());
        String jsn = String.format("roleId=%d&permissionId=%d&status=%s",roleId,permissionId,status);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_FORM_URLENCODED);
            }
        };
        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api,jsn,headersParam);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
