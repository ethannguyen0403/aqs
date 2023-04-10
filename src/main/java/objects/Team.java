package objects;

public class Team {
    private String teamName;
    private String country;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String sportName) {
        this.teamName = teamName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static class Builder {
        private String _teamName;
        private String _country;
        public Builder() {
        }
        public Builder teamName(String val) {
            _teamName = val;
            return this;
        }
        public Builder country(String val) {
            _country = val;
            return this;
        }
        public Team build() {
            return new Team(this);
        }
    }

    public Team(Builder builder) {
        this.teamName= builder._teamName;
        this.country = builder._country;
    }
}
