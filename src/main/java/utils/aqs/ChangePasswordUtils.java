package utils.aqs;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static testcases.BaseCaseAQS.environment;

public class ChangePasswordUtils {
    public static void changePassword(String oldPass, String newPass){
        String api = String.format("%saqs-agent-service/user/change-password",environment.getAqsLoginURL());
        String jsn = String.format("{\"oldPassword\":\"%s\",\"newPassword\":\"%s\"}",oldPass,newPass);
        String autho = String.format("Bearer  %s", AppUtils.token());
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        try {
            WSUtils.sendPOSTRequestDynamicHeaders(api,jsn,headersParam);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
