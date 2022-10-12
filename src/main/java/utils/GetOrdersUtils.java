package utils;

import com.paltech.constant.Configs;
import com.paltech.constant.Helper;
import com.paltech.driver.DriverManager;
import com.paltech.driver.SessionStorage;
import com.paltech.utils.WSUtils;
import objects.Environment;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static testcases.BaseCaseAQS.environment;

public class GetOrdersUtils {
    private static String token(){
        SessionStorage sessionStorage = DriverManager.getDriver().getSessionStorage();
       return  sessionStorage.getItemFromSessionStorage("token-user");
    }
    private static JSONObject getOrdersAPIJson(String fromDate, String toDate, String sport){
        String api = String.format("%saqs-agent-service/api/getOrders?fromDate=%s&toDate=%s&sport=%s",environment.getWsURL(),fromDate,toDate,sport);
    //    WSUtils.getGETJSONArrayWithCookies(api, Configs.HEADER_JSON_CHARSET, DriverManager.getDriver().getCookies().toString(),Configs.HEADER_JSON);
        String autho = String.format("Bearer  %s",token());
       // WSUtils.getPOSTJSONObjectWithCookiesHasHeader(api, Configs.HEADER_FORM_URLENCODED, null, null,"","Authorization",autho);
        return  WSUtils.getPOSTJSONObjectWithCookiesHasHeader(api, Configs.HEADER_FORM_URLENCODED, null, null,"","Authorization",autho);
    }

    public static List<Order> getOrderByStatus(String fromDate, String toDate, String sport, String status)
    {
        JSONObject jsonObject = getOrdersAPIJson(fromDate, toDate, sport);
        String _status = status.toUpperCase();
        List<Order> lstOrder = new ArrayList<>();
        if(jsonObject.has(_status))
        {
            JSONArray resultArr = jsonObject.getJSONArray(_status);
            if(resultArr.length()>0){
                for(int i =0; i < resultArr.length(); i ++){
                    JSONObject orderObj = resultArr.getJSONObject(i);
                    lstOrder.add(new Order.Builder()
                            .selection(orderObj.getString("selection"))
                            .marketType(orderObj.getString("marketType"))
                            .eventDate(orderObj.getString("eventDate"))
                            .competitionName(orderObj.getString("competition"))
                            .orderId(orderObj.getString("orderId"))
                            .bookie(orderObj.getString("bookie"))
                            .hitter(orderObj.getString("hitter"))
                            .marketName(orderObj.getString("marketName"))
                            .operator(orderObj.getString("operator"))
                            .stage(orderObj.getString("stage"))
                            .phase(orderObj.getString("phase"))
                            .requireStake(orderObj.getDouble("requestStake"))
                            .agentName(orderObj.getString("agentName"))
                            .away(orderObj.getString("away"))
                             .home(orderObj.getString("home"))
                            .clientMetadata(orderObj.getString("clientMetadata"))
                            .fixtureDisplayName(orderObj.getString("fixtureDisplayName"))
                            .build());
                }
            }
        }
        return lstOrder;
    }

}
