package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import common.SBPConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class TrackingProgressUtils {
    public static JSONObject checkReportIsReady(){
        String url = String.format("%saqs-agent-service/job-tracking/check-report-is-ready",environment.getSbpLoginURL());
        String autho = String.format("Bearer %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONObjectWithDynamicHeaders(url,headersParam);
    }
    public static String getSpecificDate(){
        JSONObject jsonObject = null;
        String date = null;
        try {
            jsonObject = checkReportIsReady();
        } catch (Exception e){
            e.getMessage();
        }
        if (!jsonObject.getString("accountCode").isEmpty()){
            date = jsonObject.getString("jobDate");
        }
        return date;
    }

    /**
     *
     * @param jobDate with format: yyyy-MM-dd
     * @return
     */
    public static JSONArray getAllDataByDate(String jobDate){
        String url = String.format("%saqs-agent-service/job-tracking/status?jobDate=%s&provider=&timeZone=Asia/Singapore",environment.getSbpLoginURL(),jobDate);
        String autho = String.format("Bearer %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONArraytWithDynamicHeaders(url,headersParam);
    }
    public static String getStatusOfIcon(){
        String specificDate = getSpecificDate();
        String status = "FINISHED";
        if (Objects.nonNull(specificDate)){
            return status;
        }
        JSONArray allData = null;
        try {
            allData = getAllDataByDate(specificDate);
        } catch (Exception e){
            e.getMessage();
        }
        if (Objects.nonNull(allData)){
            for (int i = 0; i < allData.length(); i++){
                JSONObject jsonObject = allData.getJSONObject(i);
                JSONObject summaryStatus = jsonObject.getJSONObject("summaryStatus");
                if (!summaryStatus.getString("status").equals("FINISHED")){
                    status = "IN PROGRESS";
                }
            }
        }
        return status;
    }

    /**
     *
     * @param provider
     * @return
     */
    public static List<String> getLineCodeByProvider(String provider){
        List<String> lstEx = new ArrayList<>();
        provider = provider.toUpperCase();
        switch (provider){
            case "BETISN":
                provider = "BET_ISN";
                break;
            case "FAIR999":
                provider = "MERITO";
                break;
        }
        String specificDate = getSpecificDate();
        if (!Objects.nonNull(specificDate)){
            specificDate = DateUtils.getDate(0,"yyyy-MM-dd", SBPConstants.GMT_7);
        }
        JSONArray allData = null;
        try {
            allData = getAllDataByDate(specificDate);
        } catch (Exception e){
            e.getMessage();
        }
        if (Objects.nonNull(allData)){
            for (int i = 0; i < allData.length(); i++){
                JSONObject jsonObject = allData.getJSONObject(i);
                String providerAc = jsonObject.getString("providerCode");
                if (provider.equals(providerAc)){
                    lstEx.add(jsonObject.getString("lineCode"));
                }
            }
        }
        return lstEx;
    }
}
