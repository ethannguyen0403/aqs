package utils.testraildemo;

import com.relevantcodes.extentreports.ExtentReports;
import org.json.simple.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TestRailManager  {
    public static String TEST_RUN_ID = "1";
    public static String TESTRAIL_USERNAME = "huonghuynh90@gmail.com";
    public static String TESTRAIL_PASSWORD = "P@l332211";
    public static String RAILS_ENGINE_URL = "https://merito1.testrail.io/";
    public static final int TEST_CASE_PASSED_STATUS = 1;
    public static final int TEST_CASE_FAILED_STATUS = 5;
    public static APIClient client;
    public final static String PROJECT_ID="1";
    public static void addRun() throws APIException, IOException {
       /* client  = new APIClient(RAILS_ENGINE_URL);
        client.setUser(TESTRAIL_USERNAME);
        client.setPassword(TESTRAIL_PASSWORD);
        Map data = new HashMap();
        //data.put("suite_id",true);
        data.put("include_all",true);
        data.put("name","Test Run "+System.currentTimeMillis());
        JSONObject c = null;
        c = (JSONObject)client.sendPost("add_run/"+PROJECT_ID,data);
        Long suite_id = (Long)c.get("id");
        ctx.setAttribute("suiteId",suite_id);
        try{
            context = new ClassPathXmlApplicationContext("resources/settings/APISetting.xml");
            report = new ExtentReports("", true);
        } catch(Exception ex) {
            throw new NullPointerException(String.format("ERROR: Exception occurs beforeSuite by '%s'", ex.getMessage()));
        }*/
    }

    public static void addResultForTestCase(String testCaseID, int status, String error) throws APIException, IOException {
        String testRunId = TEST_RUN_ID;
        APIClient client = new APIClient(RAILS_ENGINE_URL);
        client.setUser(TESTRAIL_USERNAME);
        client.setPassword(TESTRAIL_PASSWORD);
        Map data = new HashMap();
        data.put("status_id",status);
        data.put("comment","Test Executed - Status update automatically form Selenium test automation tool");
        client.sendPost("add_result_for_case/"+testRunId+"/"+testCaseID+"",data);

    }
}
