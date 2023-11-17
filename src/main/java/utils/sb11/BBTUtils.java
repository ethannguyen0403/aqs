package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class BBTUtils {

    public static JSONObject getAvailableLeaguesJson(String companyId, String sportId, String reportType, String fromDate, String toDate) {
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-agent-service/bbt/data";
        String jsn = String.format("{\"sport\":%s,\"companyId\":%s,\"smartType\":\"GROUP\",\"reportType\":\"%s\",\"fromDate\":\"%s\",\"toDate\":\"%s\"," +
                        "\"stake\":\"All\",\"currency\":\"All\",\"betType\":[],\"leagueId\":[],\"smartGroupId\":[],\"live\":\"All\",\"pageSize\":5,\"pageNum\":1,\"timeZone\":\"Asia/Bangkok\"" +
                        ",\"requestFrom\":\"BBT\"}"
                , sportId, companyId, reportType, fromDate, toDate);
        return WSUtils.getPOSTJSONObjectWithDynamicHeaders(api, jsn, headersParam);
    }

    public static List<String> getListAvailableLeagueBBT(String companyId, String sportId, String reportType, String fromDate, String toDate) {
        List<String> lstLeagues = new ArrayList<>();
        JSONObject jsonObj = null;
        try {
            jsonObj = getAvailableLeaguesJson(companyId, sportId, reportType, fromDate, toDate);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonObj)) {
            JSONArray jsonArr = jsonObj.getJSONArray("data");
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    lstLeagues.add(orderObj.getString("leagueName"));
                }
                return lstLeagues;
            }
        }
        return lstLeagues;
    }



}
