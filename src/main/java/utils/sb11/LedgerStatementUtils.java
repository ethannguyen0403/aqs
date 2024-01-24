package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class LedgerStatementUtils {
    private static JSONArray getInfor(int companyID, String ledgerType, String fromDate, String toDate, int ledgerGroupId){
        JSONArray jsonArrayInfor = new JSONArray();
        String api = environment.getSbpLoginURL() + "aqs-agent-service/ledger-summary/ledger-summary-per-group";
        String jsn = String.format("{\n" +
                "    \"companyId\": %s,\n" +
                "    \"ledgerType\": \"%s\",\n" +
                "    \"fromDate\": \"%s\",\n" +
                "    \"toDate\": \"%s\",\n" +
                "    \"pageRequest\": {\n" +
                "        \"pageNo\": 1,\n" +
                "        \"recordsPerPage\": 50\n" +
                "    },\n" +
                "    \"ledgerGroupId\": %s,\n" +
                "    \"isAfterCJE\": true\n" +
                "}"
        ,companyID,ledgerType,fromDate,toDate,ledgerGroupId);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        JSONObject jsonObject = WSUtils.getPOSTJSONObjectWithDynamicHeaders(api,jsn,headers);
        if (Objects.nonNull(jsonObject)){
            JSONArray jsonArray = jsonObject.getJSONArray("ledgerGroupsSummary");
            JSONObject jsonObj = jsonArray.getJSONObject(0);
            jsonArrayInfor =  jsonObj.getJSONArray("ledgerSummary");
        }
        return jsonArrayInfor;
    }
    public static Double getValueOfAccount(int companyID, String ledgerType, String fromDate, String toDate, int ledgerGroupId, String ledgerAccountName, String valueEx){
        Double value = null;
        JSONArray jsonArray = getInfor(companyID,ledgerType,fromDate,toDate,ledgerGroupId);
        if (Objects.nonNull(jsonArray)){
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("ledgerAccountName").equals(ledgerAccountName)){
                    value = jsonObject.getDouble(valueEx);
                }
            }
        }
        return value;
    }
}
