package utils.aqs;

import com.paltech.utils.WSUtils;
import org.json.JSONObject;

import java.util.Map;

import static testcases.BaseCaseAQS.environment;

public class PlaceOrderUtils {
        private static JSONObject prepareOrderJson(String orderID, Map<String, String> headers){
        String api = String.format("%saqs-gateway/v3/orders/prepare/%s",environment.getAqsLoginURL(),orderID);
        String jsn = String.format("{\n" +
                "    \"orderId\": \"%s\",\n" +
                "    \"hitterId\": \"jo\",\n" +
                "    \"orderMappings\": [\n" +
                "        {\n" +
                "            \"book\": \"IBCBET\",\n" +
                "            \"tournament\": \"Argentina Torneo Federal A\",\n" +
                "            \"homeTeam\": \"Sarmiento Resistencia\",\n" +
                "            \"awayTeam\": \"Juventud Antoniana\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2022-09-09T18:45:00Z\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SINGBET\",\n" +
                "            \"tournament\": \"Argentina Torneo Federal A\",\n" +
                "            \"homeTeam\": \"Sarmiento Resistencia\",\n" +
                "            \"awayTeam\": \"Juventud Antoniana\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2022-09-09T18:45:00Z\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SBOBET\",\n" +
                "            \"tournament\": \"Argentina Torneo Federal A\",\n" +
                "            \"homeTeam\": \"Sarmiento Resistencia\",\n" +
                "            \"awayTeam\": \"Juventud Antoniana\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2022-09-09T18:45:00Z\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"translation\": {\n" +
                "        \"language\": \"\",\n" +
                "        \"tournament\": \"\",\n" +
                "        \"homeTeam\": \"\",\n" +
                "        \"awayTeam\": \"\"\n" +
                "    }\n" +
                "}",orderID);
        return  WSUtils.getPUTJSONObjectWithDynamicHeaders(api, jsn,headers);
    }
    private static JSONObject placeOverUnderOrderJson(String orderID, Map<String,String> headers){
        String api = String.format("%saqs-gateway/v3/orders/%s",environment.getAqsLoginURL(),orderID);
        String jsn = String.format("{\n" +
                "    \"orderId\": \"%s\",\n" +
                "    \"createdTimeUtc\": \"\",\n" +
                "    \"phase\": \"InRunning\",\n" +
                "    \"scoreType\": \"Goals\",\n" +
                "    \"stage\": \"FullTime\",\n" +
                "    \"marketType\": \"OverUnder\",\n" +
                "    \"hitterId\": \"jo\",\n" +
                "    \"stake\": 1000000.0,\n" +
                "    \"orderMappings\": [\n" +
                "        {\n" +
                "            \"book\": \"IBCBET\",\n" +
                "            \"tournament\": \"Argentina Torneo Federal A\",\n" +
                "            \"homeTeam\": \"Sarmiento Resistencia\",\n" +
                "            \"awayTeam\": \"Juventud Antoniana\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2022-09-09T18:45:00Z\",\n" +
                "            \"choice\": \"Under\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SINGBET\",\n" +
                "            \"tournament\": \"Argentina Torneo Federal A\",\n" +
                "            \"homeTeam\": \"Sarmiento Resistencia\",\n" +
                "            \"awayTeam\": \"Juventud Antoniana\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2022-09-09T18:45:00Z\",\n" +
                "            \"choice\": \"Under\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SBOBET\",\n" +
                "            \"tournament\": \"Argentina Torneo Federal A\",\n" +
                "            \"homeTeam\": \"Sarmiento Resistencia\",\n" +
                "            \"awayTeam\": \"Juventud Antoniana\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2022-09-09T18:45:00Z\",\n" +
                "            \"choice\": \"Under\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"orderLines\": [\n" +
                "        {\n" +
                "            \"handicap\": 2.5,\n" +
                "            \"price\": 2.15\n" +
                "        },\n" +
                "        {\n" +
                "            \"handicap\": -2.25,\n" +
                "            \"price\": 1.25\n" +
                "        }\n" +
                "    ],\n" +
                "    \"translation\": {\n" +
                "        \"language\": \"\",\n" +
                "        \"tournament\": \"\",\n" +
                "        \"homeTeam\": \"\",\n" +
                "        \"awayTeam\": \"\"\n" +
                "    }\n" +
                "}",orderID);
        return  WSUtils.getPUTJSONObjectWithDynamicHeaders(api, jsn,headers);
    }
    private static JSONObject placeHandicapOrderJson(String orderID, Map<String,String> headers){
        String api = String.format("%saqs-gateway/v3/orders/%s",environment.getAqsLoginURL(),orderID);
        String jsn = String.format("{\n" +
                "    \"orderId\": \"%s\",\n" +
                "    \"createdTimeUtc\": \"\",\n" +
                "    \"phase\": \"InRunning\",\n" +
                "    \"scoreType\": \"Goals\",\n" +
                "    \"stage\": \"FullTime\",\n" +
                "    \"marketType\": \"AsianHandicap\",\n" +
                "    \"hitterId\": \"jo\",\n" +
                "    \"stake\": 1000000.0,\n" +
                "    \"orderMappings\": [\n" +
                "        {\n" +
                "            \"book\": \"IBCBET\",\n" +
                "            \"tournament\": \"Australia Victorian Division 1\",\n" +
                "            \"homeTeam\": \"Brunswick Juventus\",\n" +
                "            \"awayTeam\": \"Kingston City\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2022-08-05T10:30:00Z\",\n" +
                "            \"choice\": \"Kingston City\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SINGBET\",\n" +
                "            \"tournament\": \"Australia Victorian Division 1\",\n" +
                "            \"homeTeam\": \"Brunswick Juventus\",\n" +
                "            \"awayTeam\": \"Kingston City\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2022-08-05T10:30:00Z\",\n" +
                "            \"choice\": \"Kingston City\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SBOBET\",\n" +
                "            \"tournament\": \"Australia Victorian Division 1\",\n" +
                "            \"homeTeam\": \"Brunswick Juventus\",\n" +
                "            \"awayTeam\": \"Kingston City\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2022-08-05T10:30:00Z\",\n" +
                "            \"choice\": \"Kingston City\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"orderLines\": [\n" +
                "        {\n" +
                "            \"handicap\": 2.5,\n" +
                "            \"price\": 2.15\n" +
                "        },\n" +
                "        {\n" +
                "            \"handicap\": -2.25,\n" +
                "            \"price\": 1.25\n" +
                "        }\n" +
                "    ],\n" +
                "    \"translation\": {\n" +
                "        \"language\": \"\",\n" +
                "        \"tournament\": \"\",\n" +
                "        \"homeTeam\": \"\",\n" +
                "        \"awayTeam\": \"\"\n" +
                "    }\n" +
                "}",orderID);
        return  WSUtils.getPUTJSONObjectWithDynamicHeaders(api, jsn,headers);
    }
    private static JSONObject updatePriceJson(String orderID, Map<String,String> headers){
        String api = String.format("%s/aqs-gateway/v3/orders/%s/updatePrice",environment.getAqsLoginURL(),orderID);
        String jsn = String.format("{\n" +
                "    \"orderId\": \"%s\",\n" +
                "    \"hitterId\": \"jo\",\n" +
                "    \"orderMappings\": [\n" +
                "        {\n" +
                "            \"book\": \"IBCBET\",\n" +
                "            \"tournament\": \"Friendly Internationals\",\n" +
                "            \"homeTeam\": \"El Salvador women's(U20)\",\n" +
                "            \"awayTeam\": \"Panama Women's(U20)\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2021-09-14T21:00:00Z\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SINGBET\",\n" +
                "            \"tournament\": \"Friendly Internationals\",\n" +
                "            \"homeTeam\": \"El Salvador women's(U20)\",\n" +
                "            \"awayTeam\": \"Panama Women's(U20)\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2021-09-14T21:00:00Z\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SBOBET\",\n" +
                "            \"tournament\": \"Friendly Internationals\",\n" +
                "            \"homeTeam\": \"El Salvador women's(U20)\",\n" +
                "            \"awayTeam\": \"Panama Women's(U20)\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2021-09-14T21:00:00Z\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"orderLines\": [\n" +
                "        {\n" +
                "            \"handicap\": 2.5,\n" +
                "            \"price\": 1.15\n" +
                "        },\n" +
                "        {\n" +
                "            \"handicap\": -2.25,\n" +
                "            \"price\": 1.15\n" +
                "        }\n" +
                "    ],\n" +
                "    \"translation\": {\n" +
                "        \"language\": \"\",\n" +
                "        \"tournament\": \"\",\n" +
                "        \"homeTeam\": \"\",\n" +
                "        \"awayTeam\": \"\"\n" +
                "    }\n" +
                "}",orderID);
        return  WSUtils.getPOSTJSONObjectWithDynamicHeaders(api, jsn,headers);
    }
    private static JSONObject cancelOrderJson(String orderID, Map<String,String> headers){
        String api = String.format("%s/aqs-gateway/v3/orders/%s/cancel",environment.getAqsLoginURL(),orderID);
        String jsn = String.format("{\n" +
                "    \"orderId\": \"%s\",\n" +
                "    \"hitterId\": \"jo\",\n" +
                "    \"orderMappings\": [\n" +
                "        {\n" +
                "            \"book\": \"IBCBET\",\n" +
                "            \"tournament\": \"Friendly Internationals\",\n" +
                "            \"homeTeam\": \"El Salvador women's(U20)\",\n" +
                "            \"awayTeam\": \"Panama Women's(U20)\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2021-09-14T21:00:00Z\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SINGBET\",\n" +
                "            \"tournament\": \"Friendly Internationals\",\n" +
                "            \"homeTeam\": \"El Salvador women's(U20)\",\n" +
                "            \"awayTeam\": \"Panama Women's(U20)\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2021-09-14T21:00:00Z\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"book\": \"SBOBET\",\n" +
                "            \"tournament\": \"Friendly Internationals\",\n" +
                "            \"homeTeam\": \"El Salvador women's(U20)\",\n" +
                "            \"awayTeam\": \"Panama Women's(U20)\",\n" +
                "            \"scheduledKickOffTimeUtc\": \"2021-09-14T21:00:00Z\",\n" +
                "            \"liveHomeScore\": 0,\n" +
                "            \"liveAwayScore\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"orderLines\": [\n" +
                "        {\n" +
                "            \"handicap\": 2.5,\n" +
                "            \"price\": 1.15\n" +
                "        },\n" +
                "        {\n" +
                "            \"handicap\": -2.25,\n" +
                "            \"price\": 1.15\n" +
                "        }\n" +
                "    ],\n" +
                "    \"translation\": {\n" +
                "        \"language\": \"\",\n" +
                "        \"tournament\": \"\",\n" +
                "        \"homeTeam\": \"\",\n" +
                "        \"awayTeam\": \"\"\n" +
                "    }\n" +
                "}",orderID);
        return  WSUtils.getPOSTJSONObjectWithDynamicHeaders(api, jsn,headers);
    }
    public static void prepareOrder(String orderID, Map<String,String> headers ){
        prepareOrderJson(orderID,headers);
    }
    public static void cancelOrder(String orderID, Map<String,String> headers ){
        cancelOrderJson(orderID,headers);
    }
}
