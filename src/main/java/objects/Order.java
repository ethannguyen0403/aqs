package objects;

public class Order {
    private String selection;
    private String marketType;
    private String competitionName;
    private String bookie;
    private String agentName;
    private String away;
    private String home;
    private String clientMetadata;
    private String eventDate;
    private String fixtureDisplayName;
    private String hitter;
    private String operator;
    private String orderId;
    private String stage;
    private String phase;
    private double requireStake;
    private String marketName;

    public String getSelection() {
        return selection;
    }

    public void setSelection(String val ) {
        this.selection = val;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String val) {
        this.marketType = val;
    }
    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String val) {
        this.competitionName = val;
    }
    public String getBookie() {
        return bookie;
    }

    public void setBookie(String bookie) {
        this.bookie = bookie;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getClientMetadata() {
        return clientMetadata;
    }

    public void setClientMetadata(String clientMetadata) {
        this.clientMetadata = clientMetadata;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getFixtureDisplayName() {
        return fixtureDisplayName;
    }

    public void setFixtureDisplayName(String fixtureDisplayName) {
        this.fixtureDisplayName = fixtureDisplayName;
    }

    public String getHitter() {
        return hitter;
    }

    public void setHitter(String hitter) {
        this.hitter = hitter;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public double getRequireStake() {
        return requireStake;
    }

    public void setRequireStake(double requireStake) {
        this.requireStake = requireStake;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public static class Builder {
        private String _selection;
        private String _marketType;
        private String _competitionName;
        private String _bookie;
        private String _agentName;
        private String _away;
        private String _home;
        private String _clientMetadata;
        private String _eventDate;
        private String _fixtureDisplayName;
        private String _hitter;
        private String _operator;
        private String _orderId;
        private String _stage;
        private String _phase;
        private double _requireStake;
        private String _marketName;

        public Builder() {
        }

        public Builder selection(String val) {
            _selection = val;
            return this;
        }

        public Builder competitionName(String val) {
            _competitionName = val;
            return this;
        }

        public Builder marketType(String val) {
            _marketType = val;
            return this;
        }
        public Builder bookie(String val) {
            _bookie = val;
            return this;
        }
        public Builder agentName(String val) {
            _agentName = val;
            return this;
        }
        public Builder away(String val) {
            _away = val;
            return this;
        }
        public Builder home(String val) {
            _home = val;
            return this;
        }
        public Builder clientMetadata(String val) {
            _clientMetadata = val;
            return this;
        }
        public Builder eventDate(String val) {
            _eventDate = val;
            return this;
        }
        public Builder fixtureDisplayName(String val) {
            _fixtureDisplayName = val;
            return this;
        }
        public Builder operator(String val) {
            _operator = val;
            return this;
        }
        public Builder orderId(String val) {
            _orderId = val;
            return this;
        }

        public Builder stage(String val) {
            _stage = val;
            return this;
        }

        public Builder phase(String val) {
            _phase = val;
            return this;
        }

        public Builder requireStake(double val) {
            _requireStake = val;
            return this;
        }
        public Builder marketName(String val) {
            _marketName = val;
            return this;
        }

        public Builder hitter(String val) {
            _hitter = val;
            return this;
        }
        public Order build() {
            return new Order(this);
        }
    }

    public Order(Builder builder) {
        this.selection = builder._selection;
        this.marketType = builder._marketType;
        this.competitionName = builder._competitionName;
        this.bookie = builder._bookie;
        this.agentName =builder. _agentName;
        this.clientMetadata = builder. _clientMetadata;
        this.marketName = builder. _marketName;
        this.away = builder. _away;
        this.home = builder. _home;
        this.eventDate = builder. _eventDate;
        this.fixtureDisplayName = builder. _fixtureDisplayName;
        this.hitter = builder. _hitter;
        this.requireStake = builder. _requireStake;
        this.operator = builder. _operator;
        this.phase = builder. _phase;
        this.orderId = builder. _orderId;
        this.stage = builder. _stage;
    }
}
