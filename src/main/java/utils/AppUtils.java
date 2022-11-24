package utils;

import com.paltech.constant.Configs;
import com.paltech.driver.DriverManager;
import com.paltech.driver.LocalStorage;
import com.paltech.driver.SessionStorage;
import com.paltech.utils.WSUtils;
import objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static testcases.BaseCaseAQS.environment;

public class AppUtils {
    public static String token(){SessionStorage sessionStorage = DriverManager.getDriver().getSessionStorage();
       return  sessionStorage.getItemFromSessionStorage("token-user");
    }
    public static String tokenfromLocalStorage(String key){
        LocalStorage localStorage = DriverManager.getDriver().getLocalStorage();
        return  localStorage.getItemFromLocalStorage(key);
    }

}
