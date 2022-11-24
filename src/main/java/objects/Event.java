package objects;

public class Event {
    private String leagueName;
    private String away;
    private String home;
    private String eventDate;
    private String startDate;
    private String openTime;
    private String eventId;
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

    public static class Builder {
        private String _leagueName;
        private String _away;
        private String _home;
        private String _eventDate;
        private String _startDate;
        private String _openTime;
        private String _eventId;
        public Builder() {
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
        this.leagueName = builder._leagueName;
        this.home = builder._home;
        this.away = builder._away;
        this.eventDate = builder._eventDate;
        this.startDate = builder._startDate;
        this.openTime = builder._openTime;
        this.eventId= builder._eventId;
    }


}
