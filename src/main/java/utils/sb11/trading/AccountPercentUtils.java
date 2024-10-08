package utils.sb11.trading;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import common.SBPConstants;
import utils.AppUtils;
import utils.sb11.master.AccountSearchUtils;
import utils.sb11.master.ClientSystemUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static testcases.BaseCaseAQS.environment;

public class AccountPercentUtils {
    public static void setAccountPercentAPI(String accountCode, String clientCode, String superMasterCode, Double percent) throws IOException {
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        String clientId = ClientSystemUtils.getClientId(clientCode,true, SBPConstants.KASTRAKI_LIMITED);
        String superClientCode = superMasterCode + clientCode;
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-agent-service/account-percent/update-account-percent";
        String jsn = String.format("{\n" +
                        "    \"accountId\": \"%s\",\n" +
                        "    \"accountCode\": \"%s\",\n" +
                        "    \"createdBy\": \"qa\",\n" +
                        "    \"createdDate\": \"2022-12-22T07:48:59.000+00:00\",\n" +
                        "    \"modifiedDate\": \"2022-12-22T07:48:59.000+00:00\",\n" +
                        "    \"currencyCode\": \"HKD\",\n" +
                        "    \"clientId\": \"%s\",\n" +
                        "    \"clientName\": \"%s\",\n" +
                        "    \"ptMarketPercent\": %s,\n" +
                        "    \"originalPtMarketPercent\": null,\n" +
                        "    \"isEnableEdit\": true\n" +
                        "  }\n"
                , accountId, accountCode, clientId, superClientCode, percent);
        WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
    }

}
