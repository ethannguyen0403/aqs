package objects;

/**
 * @author Isabella.Huynh
 * @created Nov/9/2019
 */
public class Environment {
    private  String loginURL;
    private  String dashboardURL;
    private  String wsURL;
    private  String sosURL;
    public String getLoginURL() {
        return loginURL;
    }
    public void setLoginURL(String val) {
        this.loginURL = val;
    }
    public String getDashboardURL() {
        return dashboardURL;
    }
    public void setDashboardURL(String val) {
        this.dashboardURL = val;
    }
    public String getWsURL() {
        return wsURL;
    }
    public void setWsURL(String val) {
        this.wsURL = val;
    }
    public String getSosURL() {
        return sosURL;
    }
    public void setSosURL(String val) {
        this.sosURL = val;
    }
}
