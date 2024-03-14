package utils.sb11;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import objects.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static common.SBPConstants.CLIENT_CREDIT_ACC;
import static common.SBPConstants.CLIENT_DEBIT_ACC;
import static testcases.BaseCaseAQS.environment;

public class TransactionUtils {
    private static void sendClientBookieTransactionJson(Transaction trans, String accountIdFrom, String accountIdTo, String fromType, String typeId) throws IOException {
        String jsn;
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-agent-service/payment/transaction-submission";
        switch (fromType) {
            case "Client":
                jsn = String.format("[{\n" +
                                "    \"accountId\": \"%s\",\n" +
                                "    \"accountCode\": \"%s\",\n" +
                                "    \"type\": \"%s\",\n" +
                                "    \"currencyCode\": \"HKD\",\n" +
                                "    \"accountLedgerType\": \"\",\n" +
                                "    \"prefix\": \"-\",\n" +
                                "    \"cashBalance\": 318.9505,\n" +
                                "    \"companyId\": 1,\n" +
                                "    \"isValidAmount\": true,\n" +
                                "    \"amount\": \"%s\",\n" +
                                "    \"remark\": \"%s\",\n" +
                                "    \"transactionType\": \"PT Rebate\",\n" +
                                "    \"typeId\": \"%s\",\n" +
                                "    \"transactionDate\": \"%s\",\n" +
                                "    \"transaction\": \"Withdraw\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"accountId\": \"%s\",\n" +
                                "    \"accountCode\": \"%s\",\n" +
                                "    \"type\": \"%s\",\n" +
                                "    \"currencyCode\": \"HKD\",\n" +
                                "    \"accountLedgerType\": \"\",\n" +
                                "    \"prefix\": \"\",\n" +
                                "    \"cashBalance\": 435.72,\n" +
                                "    \"companyId\": 1,\n" +
                                "    \"isValidAmount\": true,\n" +
                                "    \"amount\": \"%s\",\n" +
                                "    \"remark\": \"%s\",\n" +
                                "    \"transactionType\": \"PT Rebate\",\n" +
                                "    \"typeId\": \"%s\",\n" +
                                "    \"transactionDate\": \"%s\",\n" +
                                "    \"transaction\": \"Deposit\"\n" +
                                "  }]\n"
                        , accountIdFrom, trans.getDebitAccountCode(), fromType, trans.getAmountDebit(), trans.getRemark(), typeId, trans.getTransDate(),
                        accountIdTo, trans.getCreditAccountCode(), fromType, trans.getAmountCredit(), trans.getRemark(), typeId, trans.getTransDate());
                break;
            default:
                jsn = String.format("[{\n" +
                                "    \"accountId\": \"%s\",\n" +
                                "    \"accountCode\": \"%s\",\n" +
                                "    \"type\": \"%s\",\n" +
                                "    \"currencyCode\": \"HKD\",\n" +
                                "    \"accountLedgerType\": \"\",\n" +
                                "    \"prefix\": \"\",\n" +
                                "    \"cashBalance\": 318.9505,\n" +
                                "    \"companyId\": 1,\n" +
                                "    \"isValidAmount\": true,\n" +
                                "    \"amount\": \"%s\",\n" +
                                "    \"remark\": \"%s\",\n" +
                                "    \"transactionType\": \"PT Rebate\",\n" +
                                "    \"typeId\": \"%s\",\n" +
                                "    \"transactionDate\": \"%s\",\n" +
                                "    \"transaction\": \"Withdraw\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"accountId\": \"%s\",\n" +
                                "    \"accountCode\": \"%s\",\n" +
                                "    \"type\": \"%s\",\n" +
                                "    \"currencyCode\": \"HKD\",\n" +
                                "    \"accountLedgerType\": \"\",\n" +
                                "    \"prefix\": \"-\",\n" +
                                "    \"cashBalance\": 435.72,\n" +
                                "    \"companyId\": 1,\n" +
                                "    \"isValidAmount\": true,\n" +
                                "    \"amount\": \"%s\",\n" +
                                "    \"remark\": \"%s\",\n" +
                                "    \"transactionType\": \"PT Rebate\",\n" +
                                "    \"typeId\": \"%s\",\n" +
                                "    \"transactionDate\": \"%s\",\n" +
                                "    \"transaction\": \"Deposit\"\n" +
                                "  }]\n"
                        , accountIdFrom, trans.getDebitAccountCode(), fromType, trans.getAmountDebit(), trans.getRemark(), typeId, trans.getTransDate(),
                        accountIdTo, trans.getCreditAccountCode(), fromType, trans.getAmountCredit(), trans.getRemark(), typeId, trans.getTransDate());
                break;
        }

        WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
    }

    private static void sendLedgerTransactionJson(Transaction trans, String accountIdFrom, String accountIdTo, String ledgerType) throws IOException {
        String jsn;
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-agent-service/payment/transaction-submission";
        switch (ledgerType) {
            case "Asset":
            case "Expenditure": {
                jsn = String.format("[{\n" +
                                "    \"ledgerId\": %s,\n" +
                                "    \"ledgerName\": \"%s\",\n" +
                                "    \"ledgerNum\": \"%s\",\n" +
                                "    \"accountLedgerType\": \"%s\",\n" +
                                "    \"prefix\": \"\",\n" +
                                "    \"currencyCode\": \"HKD\",\n" +
                                "    \"cashBalance\": 0,\n" +
                                "    \"companyId\": 1,\n" +
                                "    \"type\": \"Ledger\",\n" +
                                "    \"accountId\": %s,\n" +
                                "    \"accountName\": \"%s\",\n" +
                                "    \"accountCode\": \"%s\",\n" +
                                "    \"isValidAmount\": true,\n" +
                                "    \"amount\": %s,\n" +
                                "    \"remark\": \"%s\",\n" +
                                "    \"transactionType\": \"PT Rebate\",\n" +
                                "    \"typeId\": -1,\n" +
                                "    \"transactionDate\": \"%s\",\n" +
                                "    \"transaction\": \"Withdraw\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"ledgerId\": %s,\n" +
                                "    \"ledgerName\": \"%s\",\n" +
                                "    \"ledgerNum\": \"%s\",\n" +
                                "    \"accountLedgerType\": \"%s\",\n" +
                                "    \"prefix\": \"-\",\n" +
                                "    \"currencyCode\": \"HKD\",\n" +
                                "    \"cashBalance\": 0,\n" +
                                "    \"companyId\": 1,\n" +
                                "    \"type\": \"Ledger\",\n" +
                                "    \"accountId\": %s,\n" +
                                "    \"accountName\": \"%s\",\n" +
                                "    \"accountCode\": \"%s\",\n" +
                                "    \"isValidAmount\": true,\n" +
                                "    \"amount\": %s,\n" +
                                "    \"remark\": \"%s\",\n" +
                                "    \"transactionType\": \"PT Rebate\",\n" +
                                "    \"typeId\": -1,\n" +
                                "    \"transactionDate\": \"%s\",\n" +
                                "    \"transaction\": \"Deposit\"\n" +
                                "  }]\n"
                        , Integer.parseInt(accountIdFrom), trans.getLedgerDebit(), trans.getLedgerDebitNumber(), ledgerType, Integer.parseInt(accountIdFrom), trans.getLedgerDebit(),
                        trans.getLedgerDebit(), trans.getAmountDebit(), trans.getRemark(), trans.getTransDate(),
                        Integer.parseInt(accountIdTo), trans.getLedgerCredit(), trans.getLedgerCreditNumber(), ledgerType, Integer.parseInt(accountIdTo), trans.getLedgerCredit(),
                        trans.getLedgerCredit(), trans.getAmountDebit(), trans.getRemark(), trans.getTransDate());
                break;
            }
            default:
                jsn = String.format("[{\n" +
                                "    \"ledgerId\": %s,\n" +
                                "    \"ledgerName\": \"%s\",\n" +
                                "    \"ledgerNum\": \"%s\",\n" +
                                "    \"accountLedgerType\": \"%s\",\n" +
                                "    \"prefix\": \"-\",\n" +
                                "    \"currencyCode\": \"HKD\",\n" +
                                "    \"cashBalance\": 0,\n" +
                                "    \"companyId\": 1,\n" +
                                "    \"type\": \"Ledger\",\n" +
                                "    \"accountId\": %s,\n" +
                                "    \"accountName\": \"%s\",\n" +
                                "    \"accountCode\": \"%s\",\n" +
                                "    \"isValidAmount\": true,\n" +
                                "    \"amount\": %s,\n" +
                                "    \"remark\": \"%s\",\n" +
                                "    \"transactionType\": \"PT Rebate\",\n" +
                                "    \"typeId\": -1,\n" +
                                "    \"transactionDate\": \"%s\",\n" +
                                "    \"transaction\": \"Withdraw\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"ledgerId\": %s,\n" +
                                "    \"ledgerName\": \"%s\",\n" +
                                "    \"ledgerNum\": \"%s\",\n" +
                                "    \"accountLedgerType\": \"%s\",\n" +
                                "    \"prefix\": \"\",\n" +
                                "    \"currencyCode\": \"HKD\",\n" +
                                "    \"cashBalance\": 0,\n" +
                                "    \"companyId\": 1,\n" +
                                "    \"type\": \"Ledger\",\n" +
                                "    \"accountId\": %s,\n" +
                                "    \"accountName\": \"%s\",\n" +
                                "    \"accountCode\": \"%s\",\n" +
                                "    \"isValidAmount\": true,\n" +
                                "    \"amount\": %s,\n" +
                                "    \"remark\": \"%s\",\n" +
                                "    \"transactionType\": \"PT Rebate\",\n" +
                                "    \"typeId\": -1,\n" +
                                "    \"transactionDate\": \"%s\",\n" +
                                "    \"transaction\": \"Deposit\"\n" +
                                "  }]\n"
                        , Integer.parseInt(accountIdFrom), trans.getLedgerDebit(), trans.getLedgerDebitNumber(), ledgerType, Integer.parseInt(accountIdFrom), trans.getLedgerDebit(),
                        trans.getLedgerDebit(), trans.getAmountDebit(), trans.getRemark(), trans.getTransDate(),
                        Integer.parseInt(accountIdTo), trans.getLedgerCredit(), trans.getLedgerCreditNumber(), ledgerType, Integer.parseInt(accountIdTo), trans.getLedgerCredit(),
                        trans.getLedgerCredit(), trans.getAmountDebit(), trans.getRemark(), trans.getTransDate());
                break;
        }
        WSUtils.sendPOSTRequestDynamicHeaders(api, jsn, headersParam);
    }

    private static JSONArray getTransactionListJson(Transaction trans, String fromType) {
        String autho = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", autho);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        String api = environment.getSbpLoginURL() + "aqs-agent-service/payment/list-transactions";
        String jsn = String.format("{\n" +
                        "    \"fromDate\": \"%s\",\n" +
                        "    \"toDate\": \"%s\",\n" +
                        "    \"type\": \"%s\",\n" +
                        "    \"accountName\": \"\",\n" +
                        "    \"typeId\": \"14566\",\n" +
                        "    \"transactionType\": \"All\",\n" +
                        "    \"paging\": \n" +
                        "        {\n" +
                        "            \"currentPage\": 1,\n" +
                        "            \"pageSize\": 20\n" +
                        "        },\n" +
                        "    \"dateType\": \"TRANSACTION\",\n" +
                        "    \"ledgerGroupId\": -1\n" +
                        "  }\n"
                , trans.getTransDate(), trans.getTransDate(), fromType);
        return WSUtils.getPOSTJSONArrayWithDynamicHeaders(api, jsn, headersParam);
    }

    public static void addClientBookieTxn(Transaction trans, String accountIdFrom, String accountIdTo, String fromType, String typeId) throws IOException {
        sendClientBookieTransactionJson(trans, accountIdFrom, accountIdTo, fromType, typeId);
    }

    public static void addLedgerTxn(Transaction trans, String accountIdFrom, String accountIdTo, String ledgerType) throws IOException {
        sendLedgerTransactionJson(trans, accountIdFrom, accountIdTo, ledgerType);
    }

    /**
     *
     * @param trans
     * @param fromType should be: Ledger, Bookie, Client
     * @param debitGroupName input: LedgerGroupName, BookieName, ClientName
     * @param creditGroupName
     */
    public static void addTransByAPI(Transaction trans, String fromType, String debitGroupName, String creditGroupName, String debitParentName, String creditParentName, String clientBookieName) throws IOException {
        if (fromType.equals("Ledger")){
            //debit
            String ledgerDebitGroupId = ChartOfAccountUtils.getLedgerGroupId(debitGroupName);
            String parentDebitId = ChartOfAccountUtils.getParentId(ledgerDebitGroupId, debitParentName);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentDebitId,trans.getLedgerDebit());
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentDebitId,trans.getLedgerDebit());
            //credit
            String ledgerCreditGroupId = ChartOfAccountUtils.getLedgerGroupId(creditGroupName);
            String parentCreditId = ChartOfAccountUtils.getParentId(ledgerCreditGroupId, creditParentName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentCreditId,trans.getLedgerCredit());
            addLedgerTxn(trans,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        } else if (fromType.equals("Bookie")){
            String typeId = BookieInfoUtils.getBookieId(clientBookieName);
            String accountIdDebit = AccountSearchUtils.getAccountId(debitGroupName);
            String accountIdCredit = AccountSearchUtils.getAccountId(creditGroupName);
            addClientBookieTxn(trans,accountIdDebit,accountIdCredit,fromType,typeId);
        } else if (fromType.equals("Client")) {
            String typeId = ClientSystemUtils.getClientId(clientBookieName);
            String accountIdCredit = AccountSearchUtils.getAccountId(debitGroupName);
            String accountIdDebit = AccountSearchUtils.getAccountId(creditGroupName);
            addClientBookieTxn(trans,accountIdDebit,accountIdCredit,fromType,typeId);
        } else {
            System.out.println("Input wrongly From Type");
        }
    }

    public static Transaction getTransactionId(Transaction trans, String fromType) {
        JSONArray jsonArr = null;
        try {
            jsonArr = getTransactionListJson(trans, fromType);
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(jsonArr)) {
            if (jsonArr.length() > 0) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject orderObj = jsonArr.getJSONObject(i);
                    if (orderObj.getString("remark").equals(trans.getRemark())) {
                        trans.setTransactionId(String.valueOf(orderObj.getInt("transactionId")));
                        return trans;
                    }
                }
            }
        }
        return trans;
    }

}
