package objects;

public class Invoice {
    private String date;
    private String invoiceNumber;
    private String rsEntity;
    private String approvalStatus;
    private String invoiceStatus;
    private String invoiceDescription;
    private double amount;
    private String currency;

    public String getDate() {
        return date;
    }

    public String getRsEntity() {
        return rsEntity;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public String getInvoiceDescription() {
        return invoiceDescription;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Invoice(Builder builder) {
        this.amount = builder._amount;
        this.approvalStatus = builder._approvalStatus;
        this.invoiceStatus = builder._invoiceStatus;
        this.invoiceDescription = builder._invoiceDescription;
        this.currency = builder._currency;
        this.invoiceNumber = builder._invoiceNumber;
        this.date = builder._date;
        this.rsEntity = builder._rsEntity;
    }

    public static class Builder {
        private String _date;
        private String _invoiceNumber;
        private String _rsEntity;
        private String _approvalStatus;
        private String _invoiceStatus;
        private String _invoiceDescription;
        private double _amount;
        private String _currency;

        public Builder() {
        }

        public Builder rsEntity(String val) {
            _rsEntity = val;
            return this;
        }

        public Builder date(String val) {
            _date = val;
            return this;
        }

        public Builder invoiceNumber(String val) {
            _invoiceNumber = val;
            return this;
        }

        public Builder approvalStatus(String val) {
            _approvalStatus = val;
            return this;
        }

        public Builder invoiceStatus(String val) {
            _invoiceStatus = val;
            return this;
        }

        public Builder invoiceDescription(String val) {
            _invoiceDescription = val;
            return this;
        }

        public Builder amount(double val) {
            _amount = val;
            return this;
        }

        public Builder currency(String val) {
            _currency = val;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
