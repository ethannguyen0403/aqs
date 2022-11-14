package objects;

/**
 * @author Isabella.Huynh
 * @created Nov/9/2019
 */
public class Environment {
    private String aqsLoginURL;
    private String sbpLoginURL;
    public String getAqsLoginURL() {
        return aqsLoginURL;
    }
    public void setAqsLoginURL(String val) {
        this.aqsLoginURL = val;
    }
    public String getSbpLoginURL() {
        return sbpLoginURL;
    }
    public void setSbpLoginURL(String val) {
        this.sbpLoginURL = val;
    }
}
