package objects;

public class Order {
    private String sport;
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
    private String accountCode;
    private double requireStake;
    private String marketName;
    private boolean isNegativeHdp;
    private double hdpPoint;
    private double price;
    private String oddType;
    private String betType;
    private int liveHomeScore;
    private int liveAwayScore;
    private String accountCurrency;
    private String createDate;
    private String betId;
    private double runs;
    private int handicapWtks;
    private double handicapRuns;
    private boolean isLive;

    public String getSport() {
        return sport;
    }

    public void setSport(String val ) {
        this.sport = val;
    }
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

    public boolean isNegativeHdp() {
        return isNegativeHdp;
    }

    public void setNegativeHdp(boolean negativeHdp) {
        isNegativeHdp = negativeHdp;
    }

    public double getHdpPoint() {
        return hdpPoint;
    }

    public void setHdpPoint(double hdpPoint) {
        this.hdpPoint = hdpPoint;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOddType() {
        return oddType;
    }

    public void setOddType(String oddType) {
        this.oddType = oddType;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public int getLiveHomeScore() {
        return liveHomeScore;
    }

    public void setLiveHomeScore(int liveHomeScore) {
        this.liveHomeScore = liveHomeScore;
    }

    public int getLiveAwayScore() {
        return liveAwayScore;
    }

    public void setLiveAwayScore(int liveAwayScore) {
        this.liveAwayScore = liveAwayScore;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public double getRuns() {
        return runs;
    }

    public void setRuns(double runs) {
        this.runs = runs;
    }

    public int getHandicapWtks() {
        return handicapWtks;
    }

    public void setHandicapWtks(int handicapWtks) {
        this.handicapWtks = handicapWtks;
    }

    public double getHandicapRuns() {
        return handicapRuns;
    }

    public void setHandicapRuns(double handicapRuns) {
        this.handicapRuns = handicapRuns;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
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
        private boolean _isNegativeHdp;
        private double _hdpPoint;
        private double _price;
        private String _oddType;
        private String _betType;
        private int _liveHomeScore;
        private int _liveAwayScore;
        private String _accountCode;
        private String _accountCurrency;
        private String _createDate;
        private String _betId;
        private double _runs;
        private int _handicapWtks;
        private double _handicapRuns;
        private boolean _isLive;
        private String _sport;
        public Builder() {
        }
        public Builder sport(String val) {
            _sport = val;
            return this;
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

        public Builder isNegativeHdp(boolean val) {
            _isNegativeHdp = val;
            return this;
        }
        public Builder hdpPoint(double val) {
            _hdpPoint = val;
            return this;
        }
        public Builder price(double val) {
            _price = val;
            return this;
        }
        public Builder oddType(String val) {
            _oddType = val;
            return this;
        }
        public Builder betType(String val) {
            _betType = val;
            return this;
        }
        public Builder liveHomeScore(int val) {
            _liveHomeScore = val;
            return this;
        }
        public Builder liveAwayScore(int val) {
            _liveAwayScore = val;
            return this;
        }
        public Builder accountCurrency(String val) {
            _accountCurrency = val;
            return this;
        }
        public Builder createDate(String val) {
            _createDate = val;
            return this;
        }
        public Builder betId(String val) {
            _betId = val;
            return this;
        }
        public Builder accountCode(String val) {
            _accountCode = val;
            return this;
        }
        public Builder runs(double val) {
            _runs = val;
            return this;
        }
        public Builder handicapWtks(int val) {
            _handicapWtks = val;
            return this;
        }
        public Builder handicapRuns(double val) {
            _handicapRuns = val;
            return this;
        }
        public Builder isLive(boolean val) {
            _isLive = val;
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
        this.isNegativeHdp = builder._isNegativeHdp;
        this.hdpPoint = builder._hdpPoint;
        this.price = builder._price;
        this.oddType = builder._oddType;
        this.betType = builder._betType;
        this.liveHomeScore = builder._liveHomeScore;
        this.liveAwayScore = builder._liveAwayScore;
        this.accountCode = builder._accountCode;
        this.accountCurrency = builder._accountCurrency;
        this.createDate = builder._createDate;
        this.betId = builder._betId;
        this.runs = builder._runs;
        this.handicapRuns = builder._handicapRuns;
        this.handicapWtks = builder._handicapWtks;
        this.isLive = builder._isLive;
        this.sport = builder._sport;
    }


}
