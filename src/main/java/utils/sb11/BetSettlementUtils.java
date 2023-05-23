package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class BetSettlementUtils {

    public static JSONArray getOrderConfirmedListJson(String accountId, String sportId, Order order) throws IOException {
        if (sportId.equals(""))
        {
            sportId = "0";
        }
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-agent-service/trading/bet-settlement/list-order-confirmed";
        String jsn = String.format("{\n" +
                        "    \"fromDatePS7\":\"%s\",\n" +
                        "    \"status\": \"CONFIRMED\",\n" +
                        "    \"dateMode\": \"ALL_DATE\",\n" +
                        "    \"toDatePS7\": \"%s\",\n" +
                        "    \"accCodeStartWith\": \"\",\n" +
                        "    \"accCode\": \"%s\",\n" +
                        "    \"marketType\": \"\",\n" +
                        "    \"accType\": \"BET\",\n" +
                        "    \"bookieId\": \"\",\n" +
                        "    \"timeZone\": \"Asia/Bangkok\",\n" +
                        "    \"accountId\": \"%s\",\n" +
                        "    \"sportId\": \"%s\"\n" +
                        "  }\n"
                , order.getCreateDate(), order.getCreateDate(), order.getAccountCode(), accountId,sportId);
        return WSUtils.getPOSTJSONArrayWithDynamicHeaders(api, jsn, headersParam);
    }
    public static void sendManualBetSettleJson(String accountId, Order order, int betId, int wagerId, String sportId) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(order.getEventDate());
        long millis = date.getTime();
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-agent-service/trading/bet-settlement/settle?";
        String jsn = String.format("{\n" +
                        "  \"bets\": [\n" +
                        "    {\n" +
                        "      \"placeDate\": \"%s\",\n" +
                        "      \"eventDate\": \"%s\",\n" +
                        "      \"league\": \"Manual Bet Description\",\n" +
                        "      \"home\": \"\",\n" +
                        "      \"away\": \"\",\n" +
                        "      \"stake\": %s,\n" +
                        "      \"marketType\": \"MB\",\n" +
                        "      \"status\": \"SETTLED\",\n" +
                        "      \"selection\": \"%s\",\n" +
                        "      \"handicap\": 0,\n" +
                        "      \"live\": \"\",\n" +
                        "      \"liveScores\": \"\",\n" +
                        "      \"odds\": %s,\n" +
                        "      \"id\": %s,\n" +
                        "      \"eventId\": 0,\n" +
                        "      \"bookieId\": \"0\",\n" +
                        "      \"marketTypeName\": \"\",\n" +
                        "      \"winLose\": 5,\n" +
                        "      \"pt\": 0,\n" +
                        "      \"originalOdds\": %s,\n" +
                        "      \"oddsFormat\": \"%s\",\n" +
                        "      \"originalOddsFormat\": \"%s\",\n" +
                        "      \"source\": \"MB\",\n" +
                        "      \"wagerId\": %s,\n" +
                        "      \"sportId\": %s,\n" +
                        "      \"runs\": 0,\n" +
                        "      \"wtks\": 0,\n" +
                        "      \"betCreateType\": \"BET_ENTRY\",\n" +
                        "      \"type\": \"BACK\",\n" +
                        "      \"selected\": true\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"transactionDate\": %s,\n" +
                        "  \"sendMailRequest\": {\n" +
                        "    \"accountId\": %s,\n" +
                        "    \"filters\": [\n" +
                        "      {\n" +
                        "        \"id\": %s,\n" +
                        "        \"source\": \"MB\",\n" +
                        "        \"wagerId\": %s\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}"
                , order.getCreateDate() +"T16:59:00.000+00:00", order.getCreateDate() + "T16:59:00.000+00:00", order.getRequireStake(), order.getSelection(),
                order.getPrice(), betId, order.getPrice(), order.getOddType(), order.getOddType(), wagerId, sportId, millis, accountId, betId, wagerId);
        WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
    }

    public static int getConfirmedBetId(String accountId, String sportId, Order order) {
        JSONArray jsonArr = null;
        int betId = 0;
        try {
            jsonArr = getOrderConfirmedListJson(accountId, sportId, order);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("selection").equals(order.getSelection())) {
                        betId = orderObj.getInt("id");
                        return betId;
                    }
                }
            }
        }
        return betId;
    }

    public static int getConfirmedBetWagerId(String accountId, String sportId, Order order) {
        JSONArray jsonArr = null;
        int betId = 0;
        try {
            jsonArr = getOrderConfirmedListJson(accountId, sportId, order);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("selection").equals(order.getSelection())) {
                        betId = orderObj.getInt("wagerId");
                        return betId;
                    }
                }
            }
        }
        return betId;
    }
}
