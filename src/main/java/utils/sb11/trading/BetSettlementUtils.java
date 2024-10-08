package utils.sb11.trading;

import com.paltech.constant.Configs;
import com.paltech.utils.DateUtils;
import com.paltech.utils.WSUtils;
import common.SBPConstants;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;
import utils.sb11.master.AccountSearchUtils;

import java.io.IOException;
import java.util.*;

import static common.SBPConstants.GMT_7;
import static common.SBPConstants.SPORT_ID_MAP;
import static testcases.BaseCaseAQS.environment;

public class BetSettlementUtils {

    public static JSONArray getSettledListJson(String toDate, String fromDate, String accountCode, String accountId, String sportID) {
        String bearerToken = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", bearerToken);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String endPoint = environment.getSbpLoginURL() + "aqs-agent-service/trading/bet-settlement/list-order-settled";
        String payLoad = buildJsonPayload(toDate, fromDate, "SETTLED", accountCode, accountId, sportID);
        return WSUtils.getPOSTJSONArrayWithDynamicHeaders(endPoint, payLoad, headersParam);
    }

    public static JSONArray getOrderConfirmedListJson(String accountId, Order order) throws IOException {
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
                        "    \"dateMode\": \"SPECIFIC_DATE\",\n" +
                        "    \"toDatePS7\": \"%s\",\n" +
                        "    \"accCodeStartWith\": \"\",\n" +
                        "    \"accCode\": \"%s\",\n" +
                        "    \"marketType\": \"\",\n" +
                        "    \"accType\": \"BET\",\n" +
                        "    \"bookieId\": \"\",\n" +
                        "    \"timeZone\": \"Asia/Bangkok\",\n" +
                        "    \"accountId\": \"%s\",\n" +
                        "    \"sportId\": 0\n" +
                        "  }\n"
                , String.format("%s 12:00:00", order.getCreateDate()), String.format("%s 12:00:00", order.getCreateDate()), order.getAccountCode(), accountId);
        return WSUtils.getPOSTJSONArrayWithDynamicHeaders(api, jsn, headersParam);
    }

    public static void sendManualBetSettleJson(String accountId, Order order, int betId, int wagerId, String sportId) {
        try {
            String transDate = DateUtils.getDate(0, "yyyy-MM-dd", SBPConstants.GMT_7);
            String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
            Map<String, String> headersParam = new HashMap<String, String>() {
                {
                    put("Authorization", autho);
                    put("Content-Type", Configs.HEADER_JSON);
                }
            };
            int handicap = 0;
            if (!(order.getMarketType() == null)) {
                if (order.getMarketType().equals("OverUnder")) {
                    handicap = 1;
                }
            }

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
                            "      \"handicap\": %s,\n" +
                            "      \"live\": false,\n" +
                            "      \"liveScores\": \"\",\n" +
                            "      \"odds\": %s,\n" +
                            "      \"id\": %s,\n" +
                            "      \"eventId\": 0,\n" +
                            "      \"bookieId\": \"0\",\n" +
                            "      \"marketTypeName\": \"Manual Bet\",\n" +
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
                            "      \"settlementDate\": null,\n" +
                            "      \"selected\": true\n" +
                            "    }\n" +
                            "  ],\n" +
                            "  \"transactionDate\": \"%s\",\n" +
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
                    , order.getCreateDate() + "T16:59:00.000+00:00", order.getCreateDate() + "T16:59:00.000+00:00", order.getRequireStake(), order.getSelection(), handicap,
                    order.getPrice(), betId, order.getPrice(), order.getOddType(), order.getOddType(), wagerId, sportId, transDate, accountId, betId, wagerId);

            WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sendManualBetSettleJson(String accountCode, String sport, Order order) {
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        int betId = 0;
        int wagerId = 0;
        int i = 0;
        while (i < 5) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            betId = BetSettlementUtils.getConfirmedBetId(accountId, SPORT_ID_MAP.get(sport), order);
            wagerId = BetSettlementUtils.getConfirmedBetWagerId(accountId, SPORT_ID_MAP.get(sport), order);
            if (!(betId == 0) || !(wagerId == 0)) {
                sendManualBetSettleJson(accountId, order, betId, wagerId, SPORT_ID_MAP.get(sport));
                break;
            }
            i++;
        }
        sendManualBetSettleJson(accountId, order, betId, wagerId, SPORT_ID_MAP.get(sport));
    }

    public static void sendBetSettleAPI(Order order) {
        try {
            String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
            Map<String, String> headersParam = new HashMap<String, String>() {
                {
                    put("Authorization", autho);
                    put("Content-Type", Configs.HEADER_JSON);
                }
            };
            String api = environment.getSbpLoginURL() + "aqs-agent-service/trading/bet-settlement/settle?";
            String jsn = String.format("{\n" +
                            "    \"bets\": [\n" +
                            "        {\n" +
                            "            \"marketType\": \"%s\",\n" +
                            "            \"id\": %s,\n" +
                            "            \"winLose\": %s,\n" +
                            "            \"source\": \"PS7\"\n" +
                            "        }\n" +
                            "    ],\n" +
                            "    \"transactionDate\": \"%s\",\n" +
                            "    \"sendMailRequest\": {\n" +
                            "        \"accountId\": %s\n" +
                            "    }\n" +
                            "}"
                    , order.getMarketType(), order.getBetId(), order.getWinLose(), DateUtils.getDate(0,"yyyy-MM-dd",GMT_7),AccountSearchUtils.getAccountId(order.getAccountCode()));

            WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sendBetSettleAPI(List<Order> lstOrder) {
        for (int i = 0; i < lstOrder.size(); i++) {
            sendBetSettleAPI(lstOrder.get(i));
        }
    }

    private static String buildJsonPayload(String fromDatePS7, String toDatePS7, String status, String accountCode, String accountId, String sportID) {
        return String.format("{\n" +
                "  \"fromDatePS7\": \"%s\",\n" +
                "  \"toDatePS7\": \"%s\",\n" +
                "  \"status\": \"%s\",\n" +
                "  \"dateMode\": \"SPECIFIC_DATE\",\n" +
                "  \"accCodeStartWith\": \"\",\n" +
                "  \"accCode\": \"%s\",\n" +
                "  \"marketType\": \"\",\n" +
                "  \"accType\": \"BET\",\n" +
                "  \"bookieId\": \"\",\n" +
                "  \"timeZone\": \"Asia/Saigon\",\n" +
                "  \"accountId\": %s,\n" +
                "  \"sportId\": %s\n" +
                "}", fromDatePS7, toDatePS7, status, accountCode, accountId, sportID);
    }

    public static List<Double> getListDoubleOfSettledBestJson(String keyValue, String toDate, String fromDate, String accountCode,
                                                              String accountId, String sportID) {
        JSONArray jsonArr = null;
        List<Double> listStake = new ArrayList<>();
        try {
            jsonArr = getSettledListJson(toDate, fromDate, accountCode, accountId, sportID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    listStake.add(orderObj.getDouble(keyValue));
                }
            }
        }
        return listStake;
    }

    public static int getConfirmedBetId(String accountId, String sportId, Order order) {
        JSONArray jsonArr = null;
        int betId = 0;
        try {
            jsonArr = waitOrderConfirmedListDisplay(accountId, order);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("selection").equals(order.getSelection())) {
                        return orderObj.getInt("id");
                    }
                }
            }
        }
        return betId;
    }

    private static JSONArray waitOrderConfirmedListDisplay(String accountId, Order order) {
        JSONArray jsonArr = null;
        int i = 0;
        while (i < 3) {
            try {
                jsonArr = getOrderConfirmedListJson(accountId, order);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (jsonArr.length() == 0) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i = i + 1;
                continue;
            } else if (jsonArr.length() > 0) {
                for (int j = 0; j < jsonArr.length(); j++) {
                    JSONObject orderObj = jsonArr.getJSONObject(j);
                    if (orderObj.getString("selection").equals(order.getSelection())) {
                        break;
                    }
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i = i + 1;
                continue;
            }
            break;
        }
        return jsonArr;
    }

    public static int getConfirmedBetWagerId(String accountId, String sportId, Order order) {
        JSONArray jsonArr = null;
        int betId = 0;
        try {
            jsonArr = getOrderConfirmedListJson(accountId, order);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("selection").equals(order.getSelection())) {
                        return orderObj.getInt("wagerId");
                    }
                }
            }
        }
        return betId;
    }

    public static void waitForBetIsUpdate(int timeSecond) {
        // Thread sleep to wait for bet is updated in Db
        try {
            Thread.sleep(timeSecond * 1000);
        } catch (Exception e) {
        }
    }

    public static Order getOrderInDayByAccountCode(String accountCode, String date, String sport) {
        JSONArray jsonArr = null;
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        String sportID = SBPConstants.SPORT_ID_MAP.get(sport);
        String apiDate = DateUtils.formatDate(date, "dd/MM/yyyy", "yyyy-MM-dd");
        String apiToDate = DateUtils.getDate(0, "yyyy-MM-dd", SBPConstants.GMT_7);
        try {
            jsonArr = getSettledListJson(apiDate, apiToDate, accountCode, accountId, sportID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                JSONObject orderObj = jsonArr.getJSONObject(0);
                Order order = new Order.Builder()
                        .accountCode(accountCode)
                        .home(orderObj.getString("home"))
                        .away(orderObj.getString("away"))
                        .betType(orderObj.getString("type"))
                        .odds(orderObj.getDouble("odds"))
                        .selection(orderObj.getString("selection"))
                        .requireStake(orderObj.getDouble("stake"))
                        .winLose(orderObj.getDouble("winLose"))
                        .handicap(orderObj.getDouble("handicap"))
                        .orderId(String.valueOf(orderObj.getInt("id")))
                        .oddType(orderObj.getString("originalOddsFormat"))
                        .price(orderObj.getDouble("originalOdds"))
                        .marketType(orderObj.getString("marketType"))
                        .build();
                return order;
            }
        }
        return null;
    }

}
