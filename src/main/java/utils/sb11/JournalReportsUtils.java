package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class JournalReportsUtils {
    public static int getTransID(String fromDate, String toDate, String accountType, String accountName, String transactionType, String remark){
        int transID = -1;
        fromDate = DateUtils.formatDate(fromDate,"dd/MM/yyyy","yyyy-MM-dd");
        toDate = DateUtils.formatDate(toDate,"dd/MM/yyyy","yyyy-MM-dd");
        String api = String.format("%saqs-agent-service/payment/list-transactions",environment.getSbpLoginURL());
        String json = String.format("{\n" +
                "    \"companyId\": 0,\n" +
                "    \"fromDate\": \"%s\",\n" +
                "    \"toDate\": \"%s\",\n" +
                "    \"type\": \"%s\",\n" +
                "    \"accountName\": \"%s\",\n" +
                "    \"typeId\": -1,\n" +
                "    \"transactionType\": \"%s\",\n" +
                "    \"paging\": {\n" +
                "        \"currentPage\": 1,\n" +
                "        \"pageSize\": 20\n" +
                "    },\n" +
                "    \"dateType\": \"PERFORMED\",\n" +
                "    \"ledgerGroupId\": -1,\n" +
                "    \"companyName\": \"All\",\n" +
                "    \"showAmountIn5Decimals\": false\n" +
                "}"
                ,fromDate,toDate, accountType,accountName, transactionType);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        JSONArray jsonArray = WSUtils.getPOSTJSONArrayWithDynamicHeaders(api,json,headersParam);
        if (Objects.nonNull(jsonArray)){
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.get("remark").equals(remark)){
                    transID = jsonObject.getInt("transactionId");
                }
            }
        }
        return transID;
    }
    public static void tickAuthorize(int transID){
        String api = String.format("%saqs-agent-service/payment/authorize?id=%d",environment.getSbpLoginURL(),transID);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        try {
            WSUtils.sendGETRequestDynamicHeaders(api,headersParam);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void tickAuthorize(String fromDate, String toDate, String accountType, String accountName, String transactionType, String remark){
        int transID = -1;
        try {
            transID = getTransID(fromDate, toDate, accountType, accountName, transactionType, remark);
        } catch (Exception e) {
            e.getMessage();
        }
        tickAuthorize(transID);
    }
}
