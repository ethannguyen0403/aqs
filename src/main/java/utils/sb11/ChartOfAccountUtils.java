package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.*;

import static testcases.BaseCaseAQS.environment;

public class ChartOfAccountUtils {
    private static JSONArray getLedgerGroupListJson(){
        String json = "ledgerGroupName=&companyId=1";
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = String.format("%saqs-agent-service/ledger-info/get-ledger-groups?%s",environment.getSbpLoginURL(),json);

        return WSUtils.getGETJSONArraytWithDynamicHeaders(api, headersParam);
    }

    private static JSONArray getParentGroupListJson(String ledgerGroupId){
        String json = String.format("ledgerGroupId=%s&parentAccountName=", ledgerGroupId);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = String.format("%saqs-agent-service/ledger-info/get-parent-accounts?%s",environment.getSbpLoginURL(),json);

        return WSUtils.getGETJSONArraytWithDynamicHeaders(api, headersParam);
    }

    private static JSONObject getLedgerAccountListJson(String parentId){
        String json = String.format("parentId=%s&ledgerName=&pageNo=1&recordsPerPage=30",parentId);
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = String.format("%saqs-agent-service/ledger-info/get-ledgers-by-parent?%s",environment.getSbpLoginURL(),json);

        return WSUtils.getGETJSONObjectWithDynamicHeaders(api, headersParam);
    }

    public static String getLedgerGroupId(String ledgerGroupName) {
        JSONArray jsonArr = null;
        String groupId = null;
        try {
            jsonArr = getLedgerGroupListJson();
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("ledgerGroupName").equals(ledgerGroupName)) {
                        groupId =  (String.valueOf(orderObj.getInt("ledgerGroupId")));
                        return groupId;
                    }
                }
            }
        }
        return groupId;
    }

    public static String getParentId(String ledgerGroupId, String parentName) {
        JSONArray jsonArr = null;
        String groupId = null;
        try {
            jsonArr = getParentGroupListJson(ledgerGroupId);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("parentAccountName").equals(parentName)) {
                        groupId =  (String.valueOf(orderObj.getInt("parentAccountId")));
                        return groupId;
                    }
                }
            }
        }
        return groupId;
    }

    public static String getLedgerAccountId(String parentId, String ledgerAccountName) {
        JSONObject jsonObj = null;
        String ledgerId = null;
        try {
            jsonObj = getLedgerAccountListJson(parentId);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonObj)) {
            JSONArray resultArr  = jsonObj.getJSONArray("listLedger");
            if (resultArr.length() > 0) {
                for (int i = 0; i < resultArr.length(); i++) {
                    JSONObject orderObj = resultArr.getJSONObject(i);
                    if (orderObj.getString("ledgerName").equals(ledgerAccountName)) {
                        ledgerId =  (String.valueOf(orderObj.getInt("ledgerId")));
                        return ledgerId;
                    }
                }
            }
        }
        return ledgerId;
    }

    public static String getLedgerType(String parentId, String ledgerAccountName) {
        JSONObject jsonObj = null;
        String ledgerType = null;
        try {
            jsonObj = getLedgerAccountListJson(parentId);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonObj)) {
            JSONArray resultArr  = jsonObj.getJSONArray("listLedger");
            if (resultArr.length() > 0) {
                for (int i = 0; i < resultArr.length(); i++) {
                    JSONObject orderObj = resultArr.getJSONObject(i);
                    if (orderObj.getString("ledgerName").equals(ledgerAccountName)) {
                        ledgerType =  orderObj.getString("ledgerType");
                        return ledgerType;
                    }
                }
            }
        }
        return ledgerType;
    }
    public static List<String> getLstLedgerGroup(){
        List<String> lstLedgerGroupName = new ArrayList<>();
        JSONArray jsonArr = null;
        try {
            jsonArr = getLedgerGroupListJson();
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    lstLedgerGroupName.add(orderObj.getString("ledgerGroupName"));
                }
            }
        }
        return lstLedgerGroupName;
    }
    public static List<String> getLstParentName(String ledgerGroupId){
        List<String> lstParent = new ArrayList<>();
        JSONArray jsonArr = null;
        try {
            jsonArr = getParentGroupListJson(ledgerGroupId);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    lstParent.add(orderObj.getString("parentAccountName"));
                }
            }
        }
        return lstParent;
    }

}