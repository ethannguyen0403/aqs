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

public class FeedReportUtils {
    private static JSONArray getFeedReport(String fromDate, String toDate){
        String api = environment.getSbpLoginURL() + "aqs-agent-service/feed-report/feed-report";
        fromDate = DateUtils.formatDate(fromDate,"dd/MM/yyyy","yyyy-MM-dd");
        toDate = DateUtils.formatDate(toDate,"dd/MM/yyyy","yyyy-MM-dd");
        String jsn = String.format("{\"fromDate\":\"%s\",\"toDate\":\"%s\",\"timeZone\":\"Asia/Bangkok\",\"companyId\":1}",fromDate,toDate);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headers = new HashMap<String, String>()
        {
            {
                put("Authorization",autho) ;
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getPOSTJSONArrayWithDynamicHeaders(api,jsn,headers);
    }
    public static String getSumProvider(String fromDate, String toDate) throws IOException {
        JSONArray jsonArray;
        Double sumProvider = 0.00;
        jsonArray = getFeedReport(fromDate,toDate);
        if (Objects.nonNull(jsonArray)){
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                sumProvider = sumProvider + jsonObject.getDouble("providerAmountHkd");
            }
        }
        return String.format("%.2f",sumProvider);
    }
    public static String getSumClient(String fromDate, String toDate) throws IOException {
        JSONArray jsonArray;
        Double sumClient = 0.00;
        jsonArray = getFeedReport(fromDate,toDate);
        if (Objects.nonNull(jsonArray)){
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray jsnClient = jsonObject.getJSONArray("clientData");
                for (int j = 0; j < jsnClient.length();j++){
                    JSONObject jsnData = jsnClient.getJSONObject(j);
                    sumClient = sumClient + jsnData.getDouble("clientAmountHkd");
                }
            }
        }
        return String.format("%.2f",sumClient);
    }
    /**
     * @param columnName has 2 optional: Payment and Payment[HKD]
     * */
    public static String getPaymentValue(String providerName, String clientName, String columnName, String fromDate, String toDate){
        JSONArray jsonArray = getFeedReport(fromDate,toDate);
        String value = null;
        String column = null;
        switch (columnName){
            case "Payment":
                column = "Amount";
                break;
            case "Payment[HKD]":
                column = "AmountHkd";
                break;
            default:
                System.out.println(columnName +" is not exist");
        }
        if (Objects.nonNull(jsonArray)){
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("providerName").equals(providerName)){
                    value = String.format("%.2f",jsonObject.getDouble("provider"+column));
                    if (!clientName.isEmpty()){
                        JSONArray jsnClient = jsonObject.getJSONArray("clientData");
                        for (int j = 0; j < jsnClient.length();j++){
                            JSONObject jsnData = jsnClient.getJSONObject(j);
                            if (jsnData.getString("clientName").equals(clientName)){
                                value = String.format("%.2f",jsnData.getDouble("client"+column));
                            }
                        }
                    }
                }
            }
        }
        return value;
    }
}
