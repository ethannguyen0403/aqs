package objects;

public class Transaction {
    private String ledgerDebit;
    private String ledgerDebitNumber;
    private String ledgerCredit;
    private String ledgerCreditNumber;
    private String ledgerDebitCur;
    private String ledgerCreditCur;
    private double amountDebit;
    private double amountCredit;
    private String remark;
    private String transDate;
    private String transType;
    private String clientDebit;
    private String clientCredit;
    private String bookieDebit;
    private String bookieCredit;
    private String level;
    private String creditAccountCode;
    private String debitAccountCode;
    private String ledgerType;
    private double creditBalance;
    private double debitBalance;
    private String transactionId;
    public String getLedgerDebit() { return ledgerDebit; }

    public void setLedgerDebit(String ledgerDebit) {
        this.ledgerDebit = ledgerDebit;
    }

    public String getLedgerCredit() { return ledgerCredit; }

    public void setLedgerCredit(String ledgerCredit) {
        this.ledgerCredit = ledgerCredit;
    }
    public String getLedgerDebitNumber() { return ledgerDebitNumber; }

    public void setLedgerDebitNumber(String ledgerDebitNumber) {
        this.ledgerDebitNumber = ledgerDebitNumber;
    }

    public String getLedgerCreditNumber() { return ledgerCreditNumber; }

    public void setLedgerCreditNumber(String ledgerCreditNumber) {
        this.ledgerCreditNumber = ledgerCreditNumber;
    }

    public String getLedgerDebitCur() { return ledgerDebitCur; }

    public void setLedgerDebitCur(String ledgerDebitCur) {
        this.ledgerDebitCur = ledgerDebitCur;
    }

    public String getLedgerCreditCur() { return ledgerCreditCur; }

    public void setLedgerCreditCur(String ledgerCreditCur) {
        this.ledgerCreditCur = ledgerCreditCur;
    }

    public double getAmountDebit() { return amountDebit; }

    public void setAmountDebit(double amountDebit) { this.amountDebit = amountDebit; }

    public double getAmountCredit() { return amountCredit; }

    public void setAmountCredit(double amountCredit) { this.amountCredit = amountCredit; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransDate() { return transDate; }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransType() { return transType; }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getClientDebit() { return clientDebit; }

    public void setClientDebit(String clientDebit) {
        this.clientDebit = clientDebit;
    }
    public String getClientCredit() { return clientCredit; }

    public void setClientCredit(String clientCredit) {
        this.clientCredit = clientCredit;
    }
    public String getBookieDebit() { return bookieDebit; }

    public void setBookieDebit(String bookieDebit) {
        this.bookieDebit = bookieDebit;
    }
    public String getBookieCredit() { return bookieCredit; }

    public void setBookieCredit(String bookieCredit) {
        this.bookieCredit = bookieCredit;
    }
    public String getLevel() { return level; }

    public void setLevel(String level) {
        this.level = level;
    }
    public String getCreditAccountCode() { return creditAccountCode; }

    public void setCreditAccountCode(String creditAccountCode) {
        this.creditAccountCode = creditAccountCode;
    }
    public String getDebitAccountCode() { return debitAccountCode; }

    public void setDebitAccountCode(String debitAccountCode) {
        this.debitAccountCode = debitAccountCode;
    }
    public String getLedgerType() { return ledgerType; }

    public void setLedgerType(String debitAccountCode) {
        this.ledgerType = ledgerType;
    }

    public double getDebitBalance() { return debitBalance; }

    public void setDebitBalance(double debitBalance) {
        this.debitBalance = debitBalance;
    }

    public double getCreditBalance() { return creditBalance; }

    public void setCreditBalance(double creditBalance) {
        this.creditBalance = creditBalance;
    }

    public String getTransactionId() { return transactionId; }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public static class Builder {
        private String _ledgerDebit;
        private String _ledgerCredit;
        private String _ledgerDebitNumber;
        private String _ledgerCreditNumber;
        private String _ledgerDebitCur;
        private String _ledgerCreditCur;
        private double _amountDebit;
        private double _amountCredit;
        private String _remark;
        private String _transDate;
        private String _transType;
        private String _clientDebit;
        private String _clientCredit;
        private String _bookieDebit;
        private String _bookieCredit;
        private String _level;
        private String _creditAccountCode;
        private String _debitAccountCode;
        private String _ledgerType;
        private double _creditBalance;
        private double _debitBalance;
        private String _transactionId;
        public Builder() {
        }

        public Builder ledgerDebit(String val) {
            _ledgerDebit = val;
            return this;
        }

        public Builder ledgerCredit(String val) {
            _ledgerCredit = val;
            return this;
        }

        public Builder ledgerDebitNumber(String val) {
            _ledgerDebitNumber = val;
            return this;
        }

        public Builder ledgerCreditNumber(String val) {
            _ledgerCreditNumber = val;
            return this;
        }

        public Builder ledgerDebitCur(String val) {
            _ledgerDebitCur = val;
            return this;
        }

        public Builder ledgerCreditCur(String val) {
            _ledgerCreditCur = val;
            return this;
        }

        public Builder amountDebit(double val) {
            _amountDebit = val;
            return this;
        }

        public Builder amountCredit(double val) {
            _amountCredit = val;
            return this;
        }

        public Builder remark(String val) {
            _remark = val;
            return this;
        }

        public Builder transDate(String val) {
            _transDate = val;
            return this;
        }

        public Builder transType(String val) {
            _transType = val;
            return this;
        }
        public Builder clientDebit(String val) {
            _clientDebit = val;
            return this;
        }

        public Builder clientCredit(String val) {
            _clientCredit = val;
            return this;
        }
        public Builder bookieDebit(String val) {
            _bookieDebit = val;
            return this;
        }

        public Builder bookieCredit(String val) {
            _bookieCredit = val;
            return this;
        }
        public Builder level(String val) {
            _level = val;
            return this;
        }

        public Builder creditAccountCode(String val) {
            _creditAccountCode = val;
            return this;
        }
        public Builder debitAccountCode(String val) {
            _debitAccountCode = val;
            return this;
        }
        public Builder ledgerType(String val) {
            _ledgerType = val;
            return this;
        }
        public Builder creditBalance(double val) {
            _creditBalance = val;
            return this;
        }
        public Builder debitBalance(double val) {
            _debitBalance = val;
            return this;
        }
        public Builder transactionId(String val) {
            _transactionId = val;
            return this;
        }
        public Transaction build() {
            return new Transaction(this);
        }
    }

    public Transaction(Builder builder) {
        this.ledgerDebit = builder._ledgerDebit;
        this.ledgerCredit = builder._ledgerCredit;
        this.ledgerDebitNumber = builder._ledgerDebitNumber;
        this.ledgerCreditNumber = builder._ledgerCreditNumber;
        this.ledgerDebitCur = builder._ledgerDebitCur;
        this.ledgerCreditCur = builder._ledgerCreditCur;
        this.amountDebit = builder._amountDebit;
        this.amountCredit = builder._amountCredit;
        this.remark = builder._remark;
        this.transDate = builder._transDate;
        this.transType = builder._transType;
        this.clientDebit = builder._clientDebit;
        this.clientCredit = builder._clientCredit;
        this.bookieDebit = builder._bookieDebit;
        this.bookieCredit = builder._bookieCredit;
        this.level = builder._level;
        this.creditAccountCode = builder._creditAccountCode;
        this.debitAccountCode = builder._debitAccountCode;
        this.ledgerType = builder._ledgerType;
        this.creditBalance = builder._creditBalance;
        this.debitBalance = builder._debitBalance;
        this.transactionId = builder._transactionId;
    }


}
