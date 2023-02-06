package testcases.sb11test.accounting;

import com.paltech.utils.DateUtils;
import objects.Transaction;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.ACCOUNTING;
import static common.SBPConstants.JOURNAL_ENTRIES;

public class JournalEntriesTest extends BaseCaseAQS {

    String companyUnit = "Kastraki Limited";
    String transType = "Others";
    String financialYear = "Year 2022-2023";
    String bookieDebit = "QA Bookie";
    String clientDedit = "QA Client (No.121 Client)";
    String debitAstAcc = "AutoAssetDebit";
    String creditAstAcc = "AutoAssetCredit";
    String debitLibAcc = "AutoLiabilityDebit";
    String creditLibAcc = "AutoLiabilityCredit";
    String debitCapitalAcc = "AutoCapitalDebit";
    String creditCapitalAcc = "AutoCapitalCredit";
    String lgDebitCur = "AUD";
    String lgCreditCur = "AUD";
    String lgExpenditureGroup = "Auto Expenditure Group";
    String lgAssetGroup = "Auto Asset Group";
    String lgLiabilityGroup = "Auto Liability Group";
    String descExpenditure = "Expenditure Transaction " + DateUtils.getMilliSeconds();
    String descAsset = "Asset Transaction " + DateUtils.getMilliSeconds();
    String descLiability = "Liability Transaction " + DateUtils.getMilliSeconds();

    @TestRails(id="864")
    @Test(groups = {"smoke4"})
    public void Journal_Entries_864(){
        log("@title: Validate users can make transactions successfully between bookies");
        Transaction transaction = new Transaction.Builder()
                .bookieDebit(bookieDebit)
                .clientDebit(clientDedit)
                .ledgerCredit("JO 01")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark("Testttt")
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Access Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

        log("@Step 2: In Debit section > From dropdown, select 'Bookie'");
        log("@Step 3: Choose a bookie from Bookie dropdown (e.g. 'QA Bookie')");
        log("@Step 4: Choose Client (e.g. QA Client (No.121 Client), and Level is 'Super'");
        log("@Step 5: Input super account in precondition (e.g. SM-QA1-QA Test)");
        log("@Step 6: In Credit section, also select Bookie for 'To' dropdown");
        log("@Step 7: Other information is selected with same condition as From section (Level is 'Super', and Account is a super account link to agent-COM)");
        log("@Step 8: Add two accounts to the below tables, then input amount");
        log("@Step 9: Choose Transaction Date and Transaction Types, input Remark if any. Click Submit");
        journalEntriesPage.addClientBookieTransaction(transaction,"Bookie",false);

        log("INFO: Executed completely");
    }
}
