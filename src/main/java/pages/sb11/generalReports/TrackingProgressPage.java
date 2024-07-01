package pages.sb11.generalReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;

import static common.SBPConstants.*;

import controls.DateTimePicker;
import controls.DropDownList;
import controls.Row;
import controls.Table;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.SkipException;
import pages.sb11.WelcomePage;
import pages.sb11.popup.ConfirmPopup;
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
    public Label lblContentError = Label.xpath("//bs-tooltip-container//div[contains(@class,'tooltip-inner')]");

    public void verifyDateAfterClickingTrackingBall(String status) {
        String dateEx = null;
        if (!Color.fromString(icTrackingProgress.getColour()).asHex().equals(COLOR_ICON_TRACKING_PROGRESS.get(status))) {
            throw new SkipException("Status is difference");
        }
        switch (status) {
            case "FINISHED":
                dateEx = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
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
        List<String> lstProvider = TrackingProgress.PROVIDER_LIST;
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
        for (String lineCodeAc : lineCode) {
            ddLineCode.clickMenu(lineCodeAc);
        }
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

    private String convertColorToStatus(String colorCode) {
        colorCode = Color.fromString(colorCode).asHex();
        return STATUS_ICON_TRACKING_PROGRESS_BY_COLOR.get(colorCode);
    }

    private Table getTableByProvider(String provider) {
        int colTable = provider.equals("Fair999") ? colFair999 : colBetISN;
        return Table.xpath(String.format(tableXpath, provider), colTable);
    }

    public boolean isDataLineCodeDisplay(String provider, String lineCode) {
        Table tblData = getTableByProvider(provider);
        int stepNum = provider.equals("Fair999") ? colFair999 : colBetISN;
        int index = tblData.getRowIndexContainValue(lineCode, colLineCode, null);
        for (int i = 2; i <= stepNum; i++) {
            if (!Button.xpath(tblData.getxPathOfCell(1, i, index, "button")).isDisplayed()) {
                return false;
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
        Assert.assertEquals(Color.fromString(btnJobStatus.getColour()).asHex(), COLOR_ICON_TRACKING_PROGRESS.get(jobStatus), String.format("FAILED! %s color button is not displayed", jobStatus));
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
                    tooltipMes = TrackingProgress.BETISN_DATA_CRAWLING_STEP_TOOLTIP_MESSAGE;
                    break;
                case "Pinnacle":
                    tooltipMes = TrackingProgress.PINNACLE_DATA_CRAWLING_STEP_TOOLTIP_MESSAGE;
                    break;
                case "Fair999":
                    tooltipMes = TrackingProgress.FAI999_DATA_CRAWLING_STEP_TOOLTIP_MESSAGE;
                    break;
                default:
                    System.err.println("FAILED! Provider is not existed");
            }
        } else {
            tooltipMes = TrackingProgress.REPORT_GENERATING_STEP_TOOLTIP_MESSAGE;
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

    public String getToolTipMesOfSection(String section) {
        Label.xpath(String.format("//span[contains(text(),'%s')]/preceding-sibling::i", section)).moveAndHoverOnControl();
        waitSpinnerDisappeared();
        return Label.xpath(String.format("//span[contains(text(),'%s')]/following-sibling::popover-container//p", section)).getText().trim();
    }

    public void verifyStatusBallDisplay(String date, String provider, String statusName) {
        List<String> lstLineCode = TrackingProgressUtils.getLstLineCodeByStatus(date, provider, statusName);
        for (String lineCode : lstLineCode) {
            List<String> lstDataCralwing = TrackingProgressUtils.getLstStatusBallInStage(date, lineCode, "Data Crawling");
            for (int i = 0; i < lstDataCralwing.size(); i++) {
                int indexStep = i + 1;
                if (lstDataCralwing.get(i).equals(statusName)) {
                    Assert.assertEquals(getBallStatus(provider, lineCode, indexStep), statusName, String.format("FAILED! Status of %s at step %d display incorrect", lineCode, indexStep));
                    if (statusName.equals("IN PROGRESS")) {
                        Assert.assertTrue(Button.xpath(String.format("(//td[contains(text(),'%s')]/following-sibling::td/button[contains(@class,'skeleton-loader')])[%d]", lineCode, indexStep)).isDisplayed(), "In progress status does not flash to emphasize");
                    }
                }
            }
            List<String> lstReport = TrackingProgressUtils.getLstStatusBallInStage(date, lineCode, "Report Generating");
            for (int i = 0; i < lstReport.size(); i++) {
                int indexStep = lstDataCralwing.size() + i + 1;
                if (lstReport.get(i).equals(statusName)) {
                    Assert.assertEquals(getBallStatus(provider, lineCode, indexStep), statusName, String.format("FAILED! Status of %s at step %d display incorrect", lineCode, indexStep));
                    if (statusName.equals("IN PROGRESS")) {
                        Assert.assertTrue(Button.xpath(String.format("(//td[contains(text(),'%s')]/following-sibling::td/button[contains(@class,'skeleton-loader')])[%d]", lineCode, indexStep)).isDisplayed(), "In progress status does not flash to emphasize");
                    }
                }
            }
        }
    }

    public void verifyToolTipDisplay(String date, String provider, String status, String mesEx) {
        List<String> lstLineCode = TrackingProgressUtils.getLstLineCodeByStatus(date, provider, status);
        for (String lineCode : lstLineCode) {
            List<String> lstDataCralwing = TrackingProgressUtils.getLstStatusBallInStage(date, lineCode, "Data Crawling");
            for (int i = 0; i < lstDataCralwing.size(); i++) {
                int indexStep = i + 1;
                if (lstDataCralwing.get(i).equals(status)) {
                    if (status.equals("FINISHED")) {
                        String mesAc = getToolTip(provider, lineCode, indexStep);
                        Assert.assertTrue(mesAc.contains(mesEx), String.format("FAILED! Status of %s at step %d display incorrect", lineCode, indexStep));
                        Assert.assertTrue(mesAc.contains("Start time"), String.format("FAILED! Status of %s at step %d is not displayed Start time", lineCode, indexStep));
                        Assert.assertTrue(mesAc.contains("End time"), String.format("FAILED! Status of %s at step %d is not displayed End time", lineCode, indexStep));
                    } else {
                        Assert.assertEquals(getToolTip(provider, lineCode, indexStep), mesEx, String.format("FAILED! Status of %s at step %d display incorrect", lineCode, indexStep));
                    }
                }
            }
            List<String> lstReport = TrackingProgressUtils.getLstStatusBallInStage(date, lineCode, "Report Generating");
            for (int i = 0; i < lstReport.size(); i++) {
                int indexStep = lstDataCralwing.size() + i + 1;
                if (lstReport.get(i).equals(status)) {
                    if (status.equals("FINISHED") || status.equals("ERROR")) {
                        String mesAc = getToolTip(provider, lineCode, indexStep);
                        Assert.assertTrue(mesAc.contains(mesEx), String.format("FAILED! Status of %s at step %d display incorrect", lineCode, indexStep));
                        Assert.assertTrue(mesAc.contains("Start time"), String.format("FAILED! Status of %s at step %d is not displayed Start time", lineCode, indexStep));
                        Assert.assertTrue(mesAc.contains("End time"), String.format("FAILED! Status of %s at step %d is not displayed End time", lineCode, indexStep));
                    } else {
                        Assert.assertEquals(getToolTip(provider, lineCode, indexStep), mesEx, String.format("FAILED! Status of %s at step %d display incorrect", lineCode, indexStep));
                    }
                }
            }
        }
    }

    public String getBallStatus(String provider, String lineCode, int indexStep) {
        Table tblData = getTableByProvider(provider);
        int indexRow = tblData.getRowIndexContainValue(lineCode, colLineCode, null);
        return convertColorToStatus(tblData.getControlOfCell(1, indexStep + 1, indexRow, "button").getColour());
    }

    public String getToolTip(String provider, String lineCode, int indexStep) {
        Table tblData = getTableByProvider(provider);
        int indexRow = tblData.getRowIndexContainValue(lineCode, colLineCode, null);
        Button btnStep = Button.xpath(tblData.getxPathOfCell(1, indexStep + 1, indexRow, "button"));
        btnStep.moveAndHoverOnControl();
        //Wait for tooltip display
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Label.xpath(String.format("//popover-container//div[contains(@class,'popover-body')] | //popover-container//div[contains(@class,'popover-body')]/div", provider)).getText().trim();
    }

    public void verifyBackgroundIsRed(String date, String provider) {
        List<String> lstLineCode = TrackingProgressUtils.getLstLineCodeByStatus(date, provider, "ERROR");
        for (String lineCode : lstLineCode) {
            List<String> lstDataCralwing = TrackingProgressUtils.getLstStatusBallInStage(date, lineCode, "Data Crawling");
            for (int i = 0; i < lstDataCralwing.size(); i++) {
                if (lstDataCralwing.get(i).equals("ERROR")) {
                    Row row = Row.xpath(String.format("//td[text()='%s']/parent::tr", lineCode));
                    Assert.assertEquals(row.getColour(), "rgba(255, 0, 0, 0.45)", String.format("FAILED! Background ERROR of %s displays incorrect", lineCode));
                    break;
                }
            }
            List<String> lstReport = TrackingProgressUtils.getLstStatusBallInStage(date, lineCode, "Report Generating");
            for (int i = 0; i < lstReport.size(); i++) {
                if (lstReport.get(i).equals("ERROR")) {
                    Row row = Row.xpath(String.format("//td[text()='%s']/parent::tr", lineCode));
                    Assert.assertEquals(row.getColour(), "rgba(255, 0, 0, 0.45)", String.format("FAILED! Background ERROR of %s displays incorrect", lineCode));
                    break;
                }
            }
        }
    }

    public void verifyTooltipError(String date, String provider, String errorMes) {
        List<String> lstLineCode = TrackingProgressUtils.getLstLineCodeByStatus(date, provider, "ERROR");
        for (String lineCode : lstLineCode) {
            List<String> lstDataCralwing = TrackingProgressUtils.getLstStatusBallInStage(date, lineCode, "Data Crawling");
            for (int i = 0; i < lstDataCralwing.size(); i++) {
                if (lstDataCralwing.get(i).equals("ERROR")) {
                    Row row = Row.xpath(String.format("//td[text()='%s']/parent::tr", lineCode));
                    row.moveAndHoverOnControl();
                    if (lblContentError.isDisplayed()) {
                        Assert.assertEquals(lblContentError.getText().trim(), errorMes, String.format("FAILED! Background ERROR of %s displays incorrect", lineCode));
                        break;
                    } else {
                        System.err.println(String.format("Error tooltip is not display at %s of %s provider", lineCode, provider));
                    }
                }
            }
            List<String> lstReport = TrackingProgressUtils.getLstStatusBallInStage(date, lineCode, "Report Generating");
            for (int i = 0; i < lstReport.size(); i++) {
                if (lstReport.get(i).equals("ERROR")) {
                    Row row = Row.xpath(String.format("//td[text()='%s']/parent::tr", lineCode));
                    row.moveAndHoverOnControl();
                    if (lblContentError.isDisplayed()) {
                        Assert.assertEquals(lblContentError.getText().trim(), errorMes, String.format("FAILED! Background ERROR of %s displays incorrect", lineCode));
                        break;
                    } else {
                        System.err.println(String.format("Error tooltip is not display at %s of %s provider", lineCode, provider));
                    }
                }
            }
        }
    }

    public String getSumStatusOfLineCode(String lineCode) {
        List<String> lstLineCodeProvider = TrackingProgressUtils.getLineCodeByProvider(TrackingProgress.PROVIDER_LIST.get(3));
        int stepNum = lstLineCodeProvider.contains(lineCode) ? 6 : 8;
        String statusAc = "FINISHED";
        for (int i = 1; i <= stepNum; i++) {
            String stepStatus = convertColorToStatus(Button.xpath(String.format("(//td[text()='%s']/following-sibling::td/button)[%d]",lineCode,i)).getColour());
            if (stepStatus.equals("ERROR")) {
                return "ERROR";
            } else if (stepStatus.equals("IN PROGRESS")) {
                return "IN PROGRESS";
            } else if (stepStatus.equals("NOT STARTED")) {
                statusAc = "NOT STARTED";
            }
        }
        return statusAc;
    }

    public List<String> getLstLineCodeByStatus(String date, String status) {
        List<String> lstEx = new ArrayList<>();
        List<String> lstBetISN = TrackingProgressUtils.getLstLineCodeByStatus(date, TrackingProgress.PROVIDER_LIST.get(1),status);
        List<String> lstPinnacle = TrackingProgressUtils.getLstLineCodeByStatus(date, TrackingProgress.PROVIDER_LIST.get(2),status);
        List<String> lstFair = TrackingProgressUtils.getLstLineCodeByStatus(date, TrackingProgress.PROVIDER_LIST.get(3),status);
        lstEx.addAll(lstBetISN);
        lstEx.addAll(lstPinnacle);
        lstEx.addAll(lstFair);
        if (lstEx.size() == 0){
            throw new SkipException(String.format("There is no %s line code",status));
        }
        return lstEx;
    }
    public Button getBtnRestart(String lineCode){
        return Button.xpath(String.format("//td[text()='%s']//following-sibling::td/button[contains(text(),'Restart')]",lineCode));
    }
    public void clickToRestart(String lineCode,boolean confirmYes) {
        getBtnRestart(lineCode).click();
        ConfirmPopup confirmPopup = new ConfirmPopup();
        confirmPopup.confirm(confirmYes);
    }
}
