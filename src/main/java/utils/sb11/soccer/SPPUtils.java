package utils.sb11.soccer;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import static common.SBPConstants.*;
import static testcases.BaseCaseAQS.environment;

import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SPPUtils {
    private static JSONObject getData (String fromDate, String toDate, String sport){
        fromDate = DateUtils.formatDate(fromDate,"dd/MM/yyyy","yyyy-MM-dd");
        toDate = DateUtils.formatDate(toDate,"dd/MM/yyyy","yyyy-MM-dd");
        String sportID = SPORT_ID_MAP.get(sport);
        String api = String.format("%saqs-agent-service/smart-system/smart-group",environment.getSbpLoginURL());
        String jsn = String.format("{\"masterGroupId\":0,\"agentGroupId\":0,\"sortBy\":\"WIN_PERCENT\",\"fromDate\":\"%s\",\"toDate\":\"%s\",\"leagues\":[],\"betTypes\":[],\"winLose\":\"\",\"live\":\"\",\"topPerformance\":\"\",\"minBet\":\"\",\"sport\":%s,\"ptModeView\":\"player\"}",
                fromDate, toDate, sportID);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        return WSUtils.getPOSTJSONObjectWithDynamicHeaders(api,jsn,headersParam);
    }
    public static int getWinLose(String groupCode, String fromDate, String toDate, String sport){
        JSONObject allData = null;
        try {
            allData = getData(fromDate,toDate,sport);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(allData)){
            JSONArray jsnArrayNega = allData.getJSONArray("listNegative");
            if (Objects.nonNull(jsnArrayNega)){
                for (int i = 0; i < jsnArrayNega.length(); i++){
                    JSONObject groupData = jsnArrayNega.getJSONObject(i);
                    if (groupData.getString("groupCode").equals(groupCode)){
                        return (int) Math.round(groupData.getDouble("winLose"));
                    }
                }
            }
            JSONArray jsnArrayPosi = allData.getJSONArray("listPositive");
            if (Objects.nonNull(jsnArrayPosi)){
                for (int i = 0; i < jsnArrayPosi.length(); i++){
                    JSONObject groupData = jsnArrayPosi.getJSONObject(i);
                    if (groupData.getString("groupCode").equals(groupCode)){
                        return (int) Math.round(groupData.getDouble("winLose"));
                    }
                }
            }
        }
        return 0;
    }
}