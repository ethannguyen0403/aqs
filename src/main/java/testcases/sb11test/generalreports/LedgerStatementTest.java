package testcases.sb11test.generalreports;

import objects.Transaction;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalreports.LedgerStatementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class LedgerStatementTest extends BaseCaseAQS {

    @TestRails(id="841")
    @Test(groups = {"smoke1"})
    public void Bet_Entry_TC841(){
        log("@title: Validate transaction Debit of Ledger Type = Expenditure");
        String companyUnit = "Kastraki Limited";
        String ledgerAccount = "AutoExpenditure";
        String transType = "Others";
        String accountType = "Expenditure";
        String financialYear = "Year 2022-2023";

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(ledgerAccount)
                .ledgerCredit(ledgerAccount)
                .amountDebit(1)
                .amountCredit(1)
                .remark("Automation Testing Transaction Ledger")
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addLedgerTransaction(transaction,true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,accountType,ledgerAccount,"","");
        log("INFO: Executed completely");
    }
}
