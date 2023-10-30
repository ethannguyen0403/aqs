package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static testcases.BaseCaseAQS.environment;

public class AccountListUtils {

    public static void setAccountListPTAPI(String accountId , String percent, boolean isLive, SportName... sport) throws
            IOException {
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-agent-service/master/account/update-pt-percent";
        String jsn = buildBodyJson(accountId,percent, isLive, sport);
        WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
    }

    private static String buildBodyJson(String accountId, String percent, boolean isLive, SportName... sportNames) {
        StringBuilder builderSport = new StringBuilder();
        String liveOrNon = isLive ? "Live" : "NonLive";
        builderSport = builderSport.append("\"soccerLive\": 0,\n");
        for (int i = 0; i < sportNames.length; i++) {
            builderSport = builderSport.append(String.format("\"%s%s\": %s,\n", sportNames[i].name(), liveOrNon, percent));
        }
        String jsn = String.format("{\n" +
                        "  \"accountIds\": [\n" +
                        "    %s\n" +
                        "  ],\n" +
                        "  \"updateBy\": \"AutoQC\",\n" +
                        "  \"content\": {\n" +
                        "    \"ptpercentValid\": true\n" +
                        " %s }\n" +
                        "}"
                , accountId, builderSport);
        return jsn;
    }

    public enum SportName {
        soccer, basketball, football, tennis, others;
    }
}
