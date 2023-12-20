package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class CurrencyRateUtils {
    public static final String OANDA_URL = "https://fxds-public-exchange-rates-api.oanda.com/cc-api/currencies?base=%s&quote=%s&data_type=general_currency_pair&start_date=%s&end_date=%s";
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
            String fromDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT+7");
            jsonArr = getCurrencyRateJson(companyID, fromDate, fromDate);
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

    /**
     * Get currency rate of Oanda by API
     * @param currencyBase the currency we want to convert
     * @param currencyQuote the Base currency as 1
     * @return the opRate get from key: average_bid
     */
    public static String getOpRateOanda(String currencyBase, String currencyQuote, String fromDate, String toDate) {
        NumberFormat formatter = new DecimalFormat("#.######");
        JSONObject jsonObject = null;
        String opRate = null;
        try {
            jsonObject = getCurrencyRateJsonOANDA(currencyBase, currencyQuote, fromDate, toDate);
        } catch (Exception e) {
            System.out.println("Error in parsing Json Object: " + e.getMessage());
            return null;
        }
        if (Objects.nonNull(jsonObject)) {
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            JSONObject orderObj = jsonArray.getJSONObject(0);
            double curValue = orderObj.getDouble("average_bid");
            if (currencyBase.equalsIgnoreCase("IDR")) {
                curValue = curValue * 1000;
            }
            opRate = formatter.format(Math.round(curValue * 1000000d) / 1000000d);
        }
        return opRate;
    }

    private static JSONObject getCurrencyRateJsonOANDA(String currencyBase, String currencyQuote, String fromDate, String toDate) {
        String endpoint = String.format(OANDA_URL, currencyBase, currencyQuote, fromDate, toDate);
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONObjectWithDynamicHeaders(endpoint, headersParam);
    }
    public static List<String> getLstCurrencyCode(String companyID){
        List<String> lstCurCode = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            String fromDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT+7");
            jsonArray = getCurrencyRateJson(companyID, fromDate, fromDate);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArray)) {
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject orderObj = jsonArray.getJSONObject(i);
                    lstCurCode.add(i,orderObj.getString("currencyCode"));
                }
            }
        }
        return lstCurCode;
    }
}
