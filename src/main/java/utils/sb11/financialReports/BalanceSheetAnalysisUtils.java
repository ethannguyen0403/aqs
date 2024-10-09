package utils.sb11.financialReports;

import com.paltech.constant.Configs;
import com.paltech.utils.DoubleUtils;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.client;
import static testcases.BaseCaseAQS.environment;

public class BalanceSheetAnalysisUtils {
    private static JSONObject getBalanceSheetAnalysis(int year,int month,boolean afterCJE){
        String api = String.format("%saqs-agent-service/balance-sheet-analysis/get-balance-sheet-analysis",environment.getSbpLoginURL());
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String jsn = String.format("{\"companyId\":1," +
                "\"year\":%s," +
                "\"month\":%s," +
                "\"isAfterCJE\":%s," +
                "\"showAllBalance\":false," +
                "\"companyName\":\"Kastraki Limited\"," +
                "\"companyCurrency\":\"HKD\"}", year,month,afterCJE);
        return WSUtils.getPOSTJSONObjectWithDynamicHeaders(api, jsn, headers);
    }

    /**
     *
     * @param totalType input 6 type: totalDebit, totalCredit, totalDebitPrevMonth, totalCreditPrevMonth, totalDifDebit, totalDifCredit
     */
    public static double getSumCreDe(String accountType,String totalType, int year, int month, boolean isAfterCJE){
        JSONObject jsonOb = null;
        double sum = 0.0;
        int type = 0;
        switch (accountType){
            case "Asset":
                type = 0;
                break;
            case "Capital":
                type = 1;
                break;
            case "Liability":
                type = 2;
                break;
            default:
                System.out.println(accountType + " is not available");
        }
        try {
            jsonOb = getBalanceSheetAnalysis(year,month,isAfterCJE);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonOb)) {
            if (jsonOb.length() > 0) {
                JSONArray jsonArray = jsonOb.getJSONArray("balanceSheetAnalysisAccount");
                JSONObject jsonType = jsonArray.getJSONObject(type);
                JSONArray jsonGroup = jsonType.getJSONArray("ledgerGroups");
                for (int j = 0; j < jsonGroup.length(); j++){
                    JSONObject jsonParent = jsonGroup.getJSONObject(j);
                    JSONObject jsonTotal = jsonParent.getJSONObject("groupTotal");
                    sum = DoubleUtils.roundUpWithTwoPlaces(sum + DoubleUtils.roundUpWithTwoPlaces(Math.abs(jsonTotal.getDouble(totalType))));
                }
            }
        }
        return sum;
    }
}
