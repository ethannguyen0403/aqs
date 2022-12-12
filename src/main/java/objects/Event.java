package objects;

import freemarker.core._SettingEvaluationEnvironment;

public class Event {
    private String sportName;
    private String leagueName;
    private String away;
    private String home;
    private String eventDate;
    private String startDate;
    private String openTime;
    private String eventId;
    private String eventStatus;
    private boolean isLive;
    private boolean isN;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public boolean isN() {
        return isN;
    }

    public void setN(boolean n) {
        isN = n;
    }

    public static class Builder {
        private String _sportName;
        private String _leagueName;
        private String _away;
        private String _home;
        private String _eventDate;
        private String _startDate;
        private String _openTime;
        private String _eventId;
        private String _eventStatus;
        private boolean _isLive;
        private boolean _isN;
        public Builder() {
        }
        public Builder sportName(String val) {
            _sportName = val;
            return this;
        }
        public Builder eventStatus(String val) {
            _eventStatus = val;
            return this;
        }
        public Builder isLive(boolean val) {
            _isLive = val;
            return this;
        }
        public Builder isN(boolean val) {
            _isN = val;
            return this;
        }
        public Builder leagueName(String val) {
            _leagueName = val;
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
        public Builder eventDate(String val) {
            _eventDate = val;
            return this;
        }
        public Builder startDate(String val) {
            _startDate = val;
            return this;
        }
        public Builder openTime(String val) {
            _openTime = val;
            return this;
        }
        public Builder eventId(String val) {
            _eventId = val;
            return this;
        }
        public Event build() {
            return new Event(this);
        }
    }

    public Event(Builder builder) {
        this.sportName= builder._sportName;
        this.leagueName = builder._leagueName;
        this.home = builder._home;
        this.away = builder._away;
        this.eventDate = builder._eventDate;
        this.startDate = builder._startDate;
        this.openTime = builder._openTime;
        this.eventId= builder._eventId;
        this.isLive = builder._isLive;
        this.isN = builder._isN;
        this.eventStatus= builder._eventStatus;
    }


}
