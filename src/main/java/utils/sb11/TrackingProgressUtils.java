package utils.sb11;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
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
    public static JSONObject checkReportIsReady() {
        String url = String.format("%saqs-agent-service/job-tracking/check-report-is-ready", environment.getSbpLoginURL());
        String autho = String.format("Bearer %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONObjectWithDynamicHeaders(url, headersParam);
    }

    public static String getSpecificDate() {
        JSONObject jsonObject = null;
        String date = null;
        try {
            jsonObject = checkReportIsReady();
        } catch (Exception e) {
            e.getMessage();
        }
        if (!jsonObject.getString("accountCode").isEmpty()) {
            date = jsonObject.getString("jobDate");
        }
        return date;
    }

    /**
     * @param jobDate with format: yyyy-MM-dd
     * @return
     */
    public static JSONArray getAllDataByDate(String jobDate) {
        String url = String.format("%saqs-agent-service/job-tracking/status?jobDate=%s&provider=&timeZone=Asia/Singapore", environment.getSbpLoginURL(), jobDate);
        String autho = String.format("Bearer %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getGETJSONArraytWithDynamicHeaders(url, headersParam);
    }

    public static String getStatusOfIcon() {
        String specificDate = getSpecificDate();
        String status = "FINISHED";
        if (!Objects.nonNull(specificDate)) {
            return status;
        }
        JSONArray allData = null;
        try {
            allData = getAllDataByDate(specificDate);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(allData)) {
            for (int i = 0; i < allData.length(); i++) {
                JSONObject jsonObject = allData.getJSONObject(i);
                JSONObject summaryStatus = jsonObject.getJSONObject("summaryStatus");
                if (!summaryStatus.getString("status").equals("FINISHED")) {
                    status = "IN PROGRESS";
                }
            }
        }
        return status;
    }

    /**
     * @param provider
     * @return
     */
    public static List<String> getLineCodeByProvider(String provider) {
        List<String> lstEx = new ArrayList<>();
        provider = provider.toUpperCase();
        switch (provider) {
            case "BETISN":
                provider = "BET_ISN";
                break;
            case "FAIR999":
                provider = "MERITO";
                break;
        }
        String specificDate = getSpecificDate();
        if (!Objects.nonNull(specificDate)) {
            specificDate = DateUtils.getDate(0, "yyyy-MM-dd", SBPConstants.GMT_7);
        }
        JSONArray allData = null;
        try {
            allData = getAllDataByDate(specificDate);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(allData)) {
            for (int i = 0; i < allData.length(); i++) {
                JSONObject jsonObject = allData.getJSONObject(i);
                String providerAc = jsonObject.getString("providerCode");
                if (provider.equals(providerAc)) {
                    lstEx.add(jsonObject.getString("lineCode"));
                }
            }
        }
        return lstEx;
    }

    public static List<String> getLstStatusBallInStage(String date, String lineCode, String stageName) {
        List<String> lstStatus = new ArrayList<>();
        String jobDate = DateUtils.formatDate(date, "dd/MM/yyyy", "yyyy-MM-dd");
        JSONArray allData = TrackingProgressUtils.getAllDataByDate(jobDate);
        for (int i = 0; i < allData.length(); i++) {
            JSONObject lineCodeObject = allData.getJSONObject(i);
            String lineCodeAc = lineCodeObject.getString("lineCode");
            if (lineCode.equals(lineCodeAc)) {
                JSONArray listJobStage = lineCodeObject.getJSONArray("listJobStage");
                JSONObject jobStage = stageName.equals("Data Crawling") ? listJobStage.getJSONObject(0) : listJobStage.getJSONObject(1);
                JSONArray lstInfo = jobStage.getJSONArray("listJobInfo");
                for (int o = 0; o < lstInfo.length(); o++) {
                    JSONObject stepInfo = lstInfo.getJSONObject(o);
                    JSONObject lastedStatus = stepInfo.getJSONObject("lastedStatus");
                    lstStatus.add(lastedStatus.getString("status").replace("_", " ").trim());
                }
            }
        }
        return lstStatus;
    }

    public static List<String> getLstLineCodeByStatus(String date, String provider, String status) {
        List<String> lstLineCode = new ArrayList<>();
        provider = provider.toUpperCase();
        switch (provider) {
            case "BETISN":
                provider = "BET_ISN";
                break;
            case "FAIR999":
                provider = "MERITO";
                break;
        }
        String jobDate = DateUtils.formatDate(date, "dd/MM/yyyy", "yyyy-MM-dd");
        JSONArray allData = TrackingProgressUtils.getAllDataByDate(jobDate);
        for (int i = 0; i < allData.length(); i++) {
            JSONObject lineCodeObject = allData.getJSONObject(i);
            if (lineCodeObject.getString("providerCode").equals(provider)){
                JSONObject sumStatus = lineCodeObject.getJSONObject("summaryStatus");
                if (sumStatus.getString("status").replace("_", " ").trim().equals(status)) {
                    lstLineCode.add(lineCodeObject.getString("lineCode"));
                }
            }
        }
        return lstLineCode;
    }
}
