package utils.sb11.sport;

import com.paltech.constant.Configs;
import com.paltech.utils.WSUtils;
import common.SBPConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static common.SBPConstants.*;
import static testcases.BaseCaseAQS.environment;
public class LeagueSeasonTeamInfoUtils {
    public static int getLeagueId(String sport, String leagueName){
        int leagueId = 0;
        String sportId = SPORT_ID_MAP.get(sport);
        String url = String.format("%saqs-agent-service/event/league/list?type=-1&country=undefined&leagueName=&sportId=%s",environment.getSbpLoginURL(),sportId);
        String bearerToken = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", bearerToken);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        JSONArray jsonArray = WSUtils.getGETJSONArraytWithDynamicHeaders(url,headersParam);
        if (Objects.nonNull(jsonArray)){
            if (jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("leagueName").equals(leagueName)){
                        leagueId = jsonObject.getInt("leagueId");
                        break;
                    }
                }
            }
        }
        return leagueId;
    }
    public static void deleteLeague(String sport, String leagueName){
        int leagueId = 0;
        try {
            leagueId = getLeagueId(sport,leagueName);
        } catch (Exception e) {
            e.getMessage();
        }
        String url = String.format("%saqs-agent-service/event/delete/league?leagueId=%s",environment.getSbpLoginURL(),leagueId);
        String bearerToken = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", bearerToken);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        try {
            WSUtils.sendGETRequestDynamicHeaders(url,headersParam);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getTeamId (String sport, String leagueName, String teamName){
        int teamId = 0;
        int leagueId = 0;
        try {
            leagueId = getLeagueId(sport,leagueName);
        } catch (Exception e) {
            e.getMessage();
        }
        String url = String.format("%saqs-agent-service/event/team/list?leagueId=%s&seasonId=0",environment.getSbpLoginURL(),leagueId);
        String bearerToken = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", bearerToken);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        JSONArray jsonArray = WSUtils.getGETJSONArraytWithDynamicHeaders(url,headersParam);
        if (Objects.nonNull(jsonArray)){
            if (jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("teamName").equals(teamName)){
                        teamId = jsonObject.getInt("teamId");
                        break;
                    }
                }
            }
        }
        return teamId;
    }
    public static void deleteTeam(String sport, String leagueName, String teamName){
        int leagueId = 0;
        int teamId = 0;
        try {
            leagueId = getLeagueId(sport,leagueName);
            teamId = getTeamId(sport,leagueName,teamName);
        } catch (Exception e) {
            e.getMessage();
        }
        String url = String.format("%saqs-agent-service/event/remove/team-league-season?teamId=%s&leagueId=%s&seasonId=0",environment.getSbpLoginURL(),teamId,leagueId);
        String bearerToken = String.format("Bearer  %s", AppUtils.tokenfromLocalStorage("token-user"));
        Map<String, String> headersParam = new HashMap<String, String>() {
            {
                put("Authorization", bearerToken);
                put("Content-Type", Configs.HEADER_JSON);
            }
        };
        try {
            WSUtils.sendGETRequestDynamicHeaders(url,headersParam);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
