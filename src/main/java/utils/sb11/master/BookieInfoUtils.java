package utils.sb11.master;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class BookieInfoUtils {
    private static JSONObject getBookieJson(String bookieCode){
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() +"aqs-agent-service/bookie/bookies-list";
        String jsn = String.format("{\n" +
                        "    \"companyId\": 1,\n" +
                        "    \"searchBy\": \"%s\",\n" +
                        "    \"supportGroupId\": 0,\n" +
                        "    \"currencyCode\": \"All\",\n" +
                        "    \"status\": \"All\",\n" +
                        "    \"currentPage\": 1,\n" +
                        "    \"pageSize\": 20\n" +
                        "  }\n"
                , bookieCode);
        return WSUtils.getPOSTJSONObjectWithDynamicHeaders(api, jsn, headersParam);
    }

    public static String getBookieId(String bookieCode) {
        JSONObject jsonObject = null;
        String bookieId = null;
        try {
            jsonObject = getBookieJson(bookieCode);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonObject)) {
            JSONArray resultArr  = jsonObject.getJSONArray("data");
            if (resultArr.length() > 0) {
                for (int i = 0; i < resultArr.length(); i++) {
                    JSONObject orderObj = resultArr.getJSONObject(i);
                    if (orderObj.getString("bookieName").equals(bookieCode)) {
                        bookieId =  (String.valueOf(orderObj.getInt("bookieAccountId")));
                        return bookieId;
                    }
                }
            }
        }
        return bookieId;
    }

}
