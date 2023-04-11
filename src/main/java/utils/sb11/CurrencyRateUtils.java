package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class CurrencyRateUtils {
    private static JSONArray getCurrencyRateJson(String companyID, String fromDate, String toDate){
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        String api = environment.getSbpLoginURL() + String.format("aqs-agent-service/currency/company-currency-rate?pCompanyId=%s&pRateDateFrom=%s&pRateDateTo=%s",companyID,fromDate,toDate);
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONArraytWithDynamicHeaders(api, headersParam);
    }

    public static String getOpRate(String companyID, String currencyCode) {
        JSONArray jsonArr = null;
        String opRate = null;
        try {
            LocalDate currentDate = LocalDate.now();
            currentDate.getMonth();
            LocalDate firstDayOfMonthDate  = currentDate.withDayOfMonth(1);
            LocalDate lastDayOfMonthDate  = currentDate.withDayOfMonth(currentDate.getMonth().length(currentDate.isLeapYear()));
            String fromDate = String.valueOf(firstDayOfMonthDate);
            String toDate = String.valueOf(lastDayOfMonthDate);
            jsonArr = getCurrencyRateJson(companyID, fromDate, toDate);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("currencyCode").equals(currencyCode)) {
                        opRate =  (String.valueOf(orderObj.getDouble("opRate")));
                        return opRate;
                    }
                }
            }
        }
        return opRate;
    }
}
