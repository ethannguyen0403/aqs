package objects;

public class Transaction {
    private String ledgerDebit;
    private String ledgerCredit;
    private double amountDebit;
    private double amountCredit;
    private String remark;
    private String transDate;
    private String transType;
    public String getLedgerDebit() { return ledgerDebit; }

    public void setLedgerDebit(String ledgerDebit) {
        this.ledgerDebit = ledgerDebit;
    }

    public String getLedgerCredit() { return ledgerCredit; }

    public void setLedgerCredit(String ledgerCredit) {
        this.ledgerCredit = ledgerCredit;
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

    public static class Builder {
        private String _ledgerDebit;
        private String _ledgerCredit;
        private double _amountDebit;
        private double _amountCredit;
        private String _remark;
        private String _transDate;
        private String _transType;
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
        public Transaction build() {
            return new Transaction(this);
        }
    }

    public Transaction(Builder builder) {
        this.ledgerDebit = builder._ledgerDebit;
        this.ledgerCredit = builder._ledgerCredit;
        this.amountDebit = builder._amountDebit;
        this.amountCredit = builder._amountCredit;
        this.remark = builder._remark;
        this.transDate = builder._transDate;
        this.transType = builder._transType;
    }


}
