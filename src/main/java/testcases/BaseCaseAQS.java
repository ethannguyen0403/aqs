package testcases;
import com.paltech.driver.DriverManager;
import com.paltech.driver.DriverProperties;
import com.paltech.utils.DateUtils;
import com.paltech.utils.ScreenShotUtils;
import com.paltech.utils.StringUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.HarEntry;
import objects.Environment;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import pages.ess.BetOrderPage;
import pages.ess.HomePage;
import pages.sb11.WelcomePage;
import utils.testraildemo.APIClient;
import utils.testraildemo.APIException;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BaseCaseAQS {
    private static ApplicationContext context;
    public static DriverProperties driverProperties;
    public static Environment environment;
    public static ExtentTest logger;
    public static ExtentReports report;
    public static HomePage homePage;
    public static BetOrderPage betOrderPage;
    public static WelcomePage welcomePage;
    public static pages.ess.LoginPage loginAQSPage;
    public static pages.sb11.LoginPage loginSB11Page;
    public static BrowserMobProxy browserMobProxy;
    public static String PROJECT_ID="2";
    public static APIClient client;
    private static boolean isAddTestRailResult = false;
    private static  List<Long> lstCases= new ArrayList<>();
    public static String aqsLoginURL;
    public static String sb11LoginURL;

    @BeforeSuite(alwaysRun = true)
    public static void beforeSuite(ITestContext ctx) throws IOException, APIException {
        try{
            context = new ClassPathXmlApplicationContext("resources/settings/AQSSetting.xml");
            report = new ExtentReports("", true);
        } catch(Exception ex) {
            throw new NullPointerException(String.format("ERROR: Exception occurs beforeSuite by '%s'", ex.getMessage()));
        }
        ctx.getName();

        if(isAddTestRailResult) {
            System.out.println("Add New Test Run in TestRails" );
            client = new APIClient("https://paltech.testrail.io");
            client.setUser("tim.dang@pal.net.vn");
            client.setPassword("P@l332211");
            Map data = new HashMap();
            //data.put("suite_id",true);
            data.put("include_all", false);
            data.put("name", "Test Run of suite " + ctx.getName() +" on"+ DateUtils.getDateFollowingGMT("GMT+7","dd-MM-YYYY hh:mm:ss"));
            // data.put("milestone_id",1);

            JSONObject c = (JSONObject) client.sendPost("add_run/" + PROJECT_ID, data);
            c.get("id");
            Long suite_id = (Long) c.get("id");
            ctx.setAttribute("suiteId", suite_id);

            //data1.put("include_all", false);
            //  data1.put("suite_id",suite_id);
            //End add Run in TestRail
        }
    }

    @Parameters({"browser", "env"})
    @BeforeClass(alwaysRun = true)
    public void beforeClass(String browser, String env) {
        environment = (Environment) context.getBean(env);
        driverProperties = (DriverProperties) context.getBean(browser);
        System.out.println(String.format("RUNNING ON %s under the link %s", env.toUpperCase(), environment.getAqsLoginURL()));
        aqsLoginURL = environment.getAqsLoginURL();
        sb11LoginURL = environment.getSbpLoginURL();
    }

   @Parameters({"appname","username", "password", "isLogin","isProxy"})
    @BeforeMethod(alwaysRun = true)
    public static void beforeMethod(String appname,String username, String password, boolean isLogin, boolean isProxy, Method method, ITestResult resultI,ITestContext ctx) throws Exception {
       System.out.println("*** Map test case in script with test case in TestRail ***");
      if(isAddTestRailResult){
          Method m = method;
          if (m.isAnnotationPresent(TestRails.class)) {
              TestRails ta = m.getAnnotation(TestRails.class);
              ctx.setAttribute("caseId",ta.id());
          }
      }

        System.out.println("*****************************************Beginning TC's " + method.getName() +"****************************************************");
        logger = report.startTest(method.getName(), method.getClass().getName());
        driverProperties.setMethodName(method.getName());
        driverProperties.setIsProxy(isProxy);
       switch (appname){
           case "aqs":
               loginqas(username,password,isLogin);
               break;
           default:
               loginsb11(username,password,isLogin);
               break;
       }

        if (isProxy){
            browserMobProxy = driverProperties.getBrowserMobProxy();
        }
    }

    @AfterMethod(alwaysRun = true)
    public static void afterMethod(ITestResult result, ITestContext ctx) throws APIException, IOException {
       if(isAddTestRailResult) {
           String caseId = (String) ctx.getAttribute("caseId");
           Long suiteId = (Long) ctx.getAttribute("suiteId");
           Map data1 = new HashMap();

           // add test case for test run
           lstCases.add(Long.parseLong(caseId));
           data1.put("case_ids",lstCases);
           client.sendPost("update_run/" + suiteId, data1);
           //end add test case for test run
           //start add result for a test case
           Map data = new HashMap();
           if (result.isSuccess()) {
               data.put("status_id", 1);
           } else {
               data.put("status_id", 5);
               data.put("comment", result.getThrowable().toString());
           }
           client.sendPost("add_result_for_case/" + suiteId + "/" + caseId, data);
          /* if(!result.isSuccess()) {
               String imagePath = ScreenShotUtils.captureScreenshot(DriverManager.getDriver().getWebDriver(), "Image"+DateUtils.getMilliSeconds());
               client.sendPost("add_attachment_to_case/"+caseId,imagePath);
           }*/
           System.out.println("******** Done Add Result in Test Run in Testrail *********");
       }
        String testResult = "PASSED";
        if(!result.isSuccess()) {
            testResult = "FAILED";
            logger.log(LogStatus.FAIL, result.getThrowable());
            String srcBase64 = ScreenShotUtils.captureScreenshotWithBase64(DriverManager.getDriver().getWebDriver());
            result.setAttribute(result.getMethod().getMethodName(), srcBase64);
        }
        if (driverProperties.isProxy()){
            log("Info: Quitting BrowserMobProxy's port is " + browserMobProxy.getPort());
            browserMobProxy.stop();
        }
        DriverManager.quitAll();
        System.out.println("*****************************************Ending TC's name: " + result.getMethod().getMethodName() + " is " + testResult + " ********************************************");
    }

    @AfterSuite
    public static void tearDownSuite() {
        report.endTest(logger);
        report.flush();
        report.close();
    }

    protected static void log(String message) {
        logger.log(LogStatus.INFO, message);//For extentTest HTML report
        System.out.println(message);
        Reporter.log(message);
    }

    public static void logBug(String message) {
        logger.log(LogStatus.ERROR, message);
        System.err.println(message);
        Reporter.log(message);
    }

    protected boolean hasHTTPRespondedOK(){
        browserMobProxy.waitForQuiescence(1, 3, TimeUnit.SECONDS);
        List<HarEntry> entries = browserMobProxy.getHar().getLog().getEntries();
        for (HarEntry entry : entries) {
            if(entry.getResponse().getStatus() >= 400 && entry.getResponse().getStatus() != 423) { // skip 423 status due to sending a request in too short time
                log(String.format("ERROR URL: %s - STATUS CODE: %s", entry.getRequest().getUrl(), entry.getResponse().getStatus()));
                return false;
            }
        }
        return true;
    }

    public static void loginqas(String username, String password, boolean isLogin) throws Exception {
        createDriver(aqsLoginURL);
        loginAQSPage = new pages.ess.LoginPage();
        if (isLogin) {
            if (isLogin) {
                betOrderPage = loginAQSPage.login(username, StringUtils.decrypt(password));
            }
        }
    }

    public static void loginsb11(String username, String password, boolean isLogin) throws Exception {
        createDriver(sb11LoginURL);
        loginSB11Page = new pages.sb11.LoginPage();
        if (isLogin) {
            if (isLogin) {
                welcomePage = loginSB11Page.login(username, StringUtils.decrypt(password));
            }
        }
    }

    /**********************
     * Private methods
     *******************/
    private static void createDriver(String url) throws MalformedURLException, UnexpectedException {
        int count = 3;
        DriverManager.quitAll();
        while (count-- > 0){
            DriverManager.createWebDriver(driverProperties);
            DriverManager.getDriver().setLoadingTimeOut(100);
            DriverManager.getDriver().maximize();
            if (DriverManager.getDriver().getToAvoidTimeOut(url) || count==0) {
                log(String.format("RUNNING under the link %s", url));
                log(String.format("DEBUG: CREATED DRIVER SUCCESSFULLY with COUNT %s", count));
                System.out.println(String.format("Width x Height is %sx%s with MAP SIZE %s", DriverManager.getDriver().getWidth(), DriverManager.getDriver().getHeight(), DriverManager.driverMap.size()));
                break;
            } else {
                log("DEBUG: QUIT BROWSER DUE TO NOT CONNECTED");
                DriverManager.quitAll();
            }
        }
    }
}
