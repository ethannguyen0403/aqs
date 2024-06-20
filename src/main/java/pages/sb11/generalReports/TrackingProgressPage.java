package pages.sb11.generalReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.DropDownList;
import controls.Table;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.SkipException;
import pages.sb11.WelcomePage;
import utils.sb11.TrackingProgressUtils;

import java.util.*;

public class TrackingProgressPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class, 'card-header')]//span");
    public DropDownBox ddProvider = DropDownBox.xpath("//label[contains(text(),'Provider')]/parent::div//following-sibling::select");
    public DropDownList ddLineCode = DropDownList.xpath("//angular2-multiselect", "//li//label");
    public TextBox txtSpecificDate = TextBox.xpath("//label[contains(text(),'Specific Date')]/parent::div//following-sibling::div/input");
    public DateTimePicker dtpSpecificDate = DateTimePicker.xpath(txtSpecificDate, "//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    int colBetISN = 10;
    int colFair999 = 8;
    int colLineCode = 1;
    int stepBetISNNum = 8;
    int stepFair999NNum = 6;
    String tableXpath = "//h5[contains(text(),'%s')]//ancestor::table";
    public Label lblReminderMessage = Label.xpath("//span[contains(text(),'Job Process Reminders')]/parent::div/following-sibling::div/div");

    public void verifyDateAfterClickingTrackingBall(String status) {
        String dateEx = null;
        if (!Color.fromString(icTrackingProgress.getColour()).asHex().equals(SBPConstants.COLOR_ICON_TRACKING_PROGRESS.get(status))) {
            throw new SkipException("Status is difference");
        }
        switch (status) {
            case "FINISHED":
                dateEx = DateUtils.getDate(0, "dd/MM/yyyy", SBPConstants.GMT_7);
                Assert.assertEquals(txtSpecificDate.getAttribute("value"), dateEx, "FAILED! Specific Date displays incorrect.");
                break;
            case "IN PROGRESS":
                dateEx = DateUtils.formatDate(TrackingProgressUtils.getSpecificDate(), "yyyy-MM-dd", "dd/MM/yyyy");
                Assert.assertEquals(txtSpecificDate.getAttribute("value"), dateEx, "FAILED! Specific Date displays incorrect.");
                break;
            default:
                System.out.println("Status is not available");
        }

    }

    public void verifyLineCodeDisplayByProvider(String provider) {
        Table tblData = getTableByProvider(provider);
        List<String> lstLineCodeAc = tblData.getColumn(colLineCode, true);
        List<String> lstLineCodeEx = TrackingProgressUtils.getLineCodeByProvider(provider);
        Assert.assertEquals(lstLineCodeAc, lstLineCodeEx, String.format("FAILED! Line Code List of %s provider display incorrect", provider));
    }

    public void verifyLineCodeDropDownSorted() {
        List<String> lstLineCodeEx = new ArrayList<>();
        List<String> lstProvider = SBPConstants.TrackingProgress.PROVIDER_LIST;
        for (int i = 1; i < lstProvider.size(); i++) {
            List<String> lstLineCode = TrackingProgressUtils.getLineCodeByProvider(lstProvider.get(i));
            Collections.sort(lstLineCode, String.CASE_INSENSITIVE_ORDER);
            for (String lineCode : lstLineCode) {
                lstLineCodeEx.add(lineCode);
            }
        }
        List<String> lsLineCodeAc = ddLineCode.getMenuList();
        Assert.assertEquals(lsLineCodeAc, lstLineCodeEx, "FAILED! Line code drop down displays incorrect");
    }

    public void selectLineCode(String... lineCode) {
        ddLineCode.click();
        for (String lineCodeAc : lineCode) {
            ddLineCode.clickMenu(lineCodeAc);
        }
        ddLineCode.click();
    }

    public void verifyLineCodeWasSelected(String... lineCode) {
        for (String lineCodeAc : lineCode) {
            Assert.assertTrue(ddLineCode.getAttribute("innerText").contains(lineCodeAc), "FAILED! " + lineCodeAc + " was not selected");
        }
    }

    public void verifyLineCodeDisplaysInDropdown(List<String> lstEx) {
        List<String> lstLineCodeDropdown = ddLineCode.getMenuList();
        for (String lineCode : lstEx) {
            Assert.assertTrue(lstLineCodeDropdown.contains(lineCode), "FAILED! " + lineCode + " is not displayed");
        }
    }

    public void filter(String provider, String lineCode, String date) {
        if (!provider.isEmpty()) {
            ddProvider.selectByVisibleText(provider);
            waitSpinnerDisappeared();
        }
        if (!lineCode.isEmpty()) {
            ddLineCode.clickMenu(lineCode);
        }
        if (!date.isEmpty()) {
            dtpSpecificDate.selectDate(date, "dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public void verifyLineCodeData(String provider, String lineCodeName, String dataCrawlingStatus, String reportGeneratingStatus, String restartButtonStatus) {
        Table tblData = getTableByProvider(provider);
        int index = tblData.getRowIndexContainValue(lineCodeName, colLineCode, null);
        switch (provider) {
            case "Fair999":
                //Data Crawling Stage
                if (!dataCrawlingStatus.isEmpty()) {
                    String crawlingStatus = getStageStatus(provider, index, "Data Crawling");
                    Assert.assertEquals(crawlingStatus, dataCrawlingStatus, "FAILED! " + lineCodeName + " Data Crawling status is " + crawlingStatus + " instead of " + dataCrawlingStatus);
                }
                //Report Generating Stage
                if (!dataCrawlingStatus.isEmpty()) {
                    String reportStatus = getStageStatus(provider, index, "Report Generating");
                    Assert.assertEquals(reportStatus, reportGeneratingStatus, "FAILED! " + lineCodeName + " Data Crawling status is " + reportStatus + " instead of " + reportGeneratingStatus);
                }
                break;
            default:
                //Data Crawling Stage
                if (!dataCrawlingStatus.isEmpty()) {
                    String crawlingStatus = getStageStatus(provider, index, "Data Crawling");
                    Assert.assertEquals(dataCrawlingStatus, crawlingStatus, "FAILED! " + lineCodeName + " Data Crawling status is " + crawlingStatus + " instead of " + dataCrawlingStatus);
                }
                //Report Generating Stage
                if (!dataCrawlingStatus.isEmpty()) {
                    String reportStatus = getStageStatus(provider, index, "Report Generating");
                    Assert.assertEquals(reportStatus, reportGeneratingStatus, "FAILED! " + lineCodeName + " Data Crawling status is " + reportStatus + " instead of " + reportGeneratingStatus);
                }
                break;

        }
    }

    public String getStageStatus(String provider, int lineCodeIndex, String section) {
        Table tblData = getTableByProvider(provider);
        String stageStatus = null;
        if (provider.equals("Fair999")) {
            if (section.equals("Data Crawling")) {
                return getBallStatus(Button.xpath(tblData.getxPathOfCell(1, 2, lineCodeIndex, "button")).getAttribute("@Class"));
            } else {
                for (int i = 3; i <= 7; i++) {
                    String status = getBallStatus(Button.xpath(tblData.getxPathOfCell(1, i, lineCodeIndex, "button")).getAttribute("@Class"));
                    if (status.equals("IN PROGRESS")) {
                        return "IN PROGRESS";
                    }
                }
                return "FINISHED";
            }
        } else {
            if (section.equals("Data Crawling")) {
                for (int i = 2; i <= 4; i++) {
                    String status = getBallStatus(Button.xpath(tblData.getxPathOfCell(1, i, lineCodeIndex, "button")).getAttribute("@Class"));
                    if (status.equals("IN PROGRESS")) {
                        return "IN PROGRESS";
                    }
                }
                return "FINISHED";
            } else {
                for (int i = 5; i <= 9; i++) {
                    String status = getBallStatus(Button.xpath(tblData.getxPathOfCell(1, i, lineCodeIndex, "button")).getAttribute("@Class"));
                    if (status.equals("IN PROGRESS")) {
                        return "IN PROGRESS";
                    }
                }
                return "FINISHED";
            }
        }
    }

    private String getBallStatus(String classValue) {
        if (classValue.contains("btn-success")) {
            return "FINISHED";
        } else {
            return "IN PROGRESS";
        }
    }

    private Table getTableByProvider(String provider) {
        int colTable = provider.equals("Fair999") ? colFair999 : colBetISN;
        return Table.xpath(String.format(tableXpath, provider), colTable);
    }

    public boolean isDataLineCodeDisplay(String provider, String lineCode) {
        Table tblData = getTableByProvider(provider);
        int index = tblData.getRowIndexContainValue(lineCode, colLineCode, null);
        if (provider.equals("Fair999")) {
            for (int i = 2; i <= colFair999; i++) {
                if (!Button.xpath(tblData.getxPathOfCell(1, i, index, "button")).isDisplayed()) {
                    return false;
                }
            }
        } else {
            for (int i = 2; i <= colBetISN; i++) {
                if (!Button.xpath(tblData.getxPathOfCell(1, i, index, "button")).isDisplayed()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param jobStatus : NOT STARTED, IN PROGRESS, FINISHED, ERROR
     */
    public void verifyJobStatusSymbolDisplay(String jobStatus) {
        Label lblJobStatus = Label.xpath(String.format("//label[contains(text(),'%s')]", jobStatus));
        Button btnJobStatus = Button.xpath(String.format("//label[contains(text(),'%s')]/parent::div/following-sibling::button", jobStatus));
        Assert.assertTrue(lblJobStatus.isDisplayed(), String.format("FAILED! %s title is not displayed", jobStatus));
        Assert.assertEquals(Color.fromString(btnJobStatus.getColour()).asHex(), SBPConstants.COLOR_ICON_TRACKING_PROGRESS.get(jobStatus), String.format("FAILED! %s color button is not displayed", jobStatus));
    }

    public void verifyExistingLinesOfProviderDisplay(String provider) {
        List<String> lstLineCode = TrackingProgressUtils.getLineCodeByProvider(provider);
        for (String lineCode : lstLineCode) {
            Assert.assertTrue(isDataLineCodeDisplay(provider, lineCode), "FAILED! " + lineCode + " displays incorrect in provider " + provider);
        }
    }

    public void verifyProviderDisplay(String provider) {
        Table tblProvider = getTableByProvider(provider);
        Assert.assertTrue(tblProvider.isDisplayed());
    }

    public void verifyLineCodeOfProviderSorted(String provider) {
        Table tblProvider = getTableByProvider(provider);
        List<String> lstLineCodeAc = tblProvider.getColumn(colLineCode, true);
        List<String> lstLineCodeEx = lstLineCodeAc;
        Collections.sort(lstLineCodeEx, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(lstLineCodeAc, lstLineCodeEx, "FAILED! Line Codes sort incorrect");
    }

    public void verifyStepNumberOfStageDisplay(String provider, String stageName, int stepNumber) {
        Label lblStep = Label.xpath(String.format("//h5[contains(text(),'%s')]//ancestor::table/thead/tr[@class='sub']//span", provider));
        int stepNumberAc = 0;
        List<String> tooltipMes = new ArrayList<>();
        if (stageName.equals("Data Crawling Stage")) {
            switch (provider) {
                case "BetISN":
                    tooltipMes = SBPConstants.TrackingProgress.BETISN_DATA_CRAWLING_STEP_TOOLTIP_MESSAGE;
                    break;
                case "Pinnacle":
                    tooltipMes = SBPConstants.TrackingProgress.PINNACLE_DATA_CRAWLING_STEP_TOOLTIP_MESSAGE;
                    break;
                case "Fair999":
                    tooltipMes = SBPConstants.TrackingProgress.FAI999_DATA_CRAWLING_STEP_TOOLTIP_MESSAGE;
                    break;
                default:
                    System.err.println("FAILED! Provider is not existed");
            }
        } else {
            tooltipMes = SBPConstants.TrackingProgress.REPORT_GENERATING_STEP_TOOLTIP_MESSAGE;
        }
        for (int i = 1; i <= lblStep.getWebElements().size(); i++) {
            Label.xpath(String.format("(//h5[contains(text(),'%s')]//ancestor::table/thead/tr[@class='sub']//span)[%d]", provider, i)).moveAndHoverOnControl();
            //Wait for tooltip display
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String toolTipMesOfStep = Label.xpath(String.format("(//h5[contains(text(),'%s')]//ancestor::table/thead/tr[@class='sub']//span)[%d]/following::popover-container//p", provider, i)).getText().trim();
            if (tooltipMes.contains(toolTipMesOfStep)) {
                stepNumberAc++;
            }
        }
        Assert.assertEquals(stepNumberAc, stepNumber, "FAILED! " + provider + " does not have enough step in " + stageName + " section");
    }
    public String getToolTipMesOfSection(String section){
        Label.xpath(String.format("//span[contains(text(),'%s')]/preceding-sibling::i",section)).moveAndHoverOnControl();
        waitSpinnerDisappeared();
        return Label.xpath(String.format("//span[contains(text(),'%s')]/following-sibling::popover-container//p",section)).getText().trim();
    }
}
