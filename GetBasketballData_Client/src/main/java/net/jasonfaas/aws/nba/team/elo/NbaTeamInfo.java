package net.jasonfaas.aws.nba.team.elo;

/**
 * Created by jasonfaas on 11/12/15.
 */
public enum NbaTeamInfo {
    ATLANTA("ATL", "Atlanta", "Hawks"),
    BOSTON("BOS", "", ""),
    BROOKLYN("BRK", "", ""),
    CHARLOTTE("CHO", "", ""),
    CHICAGO("CHI", "Chicago", "Bulls"),
    CLEVELAND("CLE", "", ""),
    DALLAS("DAL", "", ""),
    DENVER("DEN", "", ""),
    DETROIT("DET", "", ""),
    GOLDENSTATE("GSW", "", ""),
    HOUSTON("HOU", "", ""),
    INDIANA("IND", "", ""),
    LOSANGELESCLIPPERS("LAC", "", ""),
    LOSANGELESLAKERS("LAL", "", ""),
    MEMPHIS("MEM", "", ""),
    MIAMI("MIA", "", ""),
    MILWAUKEE("MIL", "", ""),
    MINNESOTA("MIN", "", ""),
    NEWORLEANS("NOP", "", ""),
    NEWYORKKNICKS("NYK", "", ""),
    OKLAHOMA("OKC", "", ""),
    ORLANDO("ORL", "", ""),
    PHILADELPHIA("PHI", "", ""),
    PHOENIX("PHO", "", ""),
    PORTLAND("POR", "", ""),
    SACRAMENTO("SAC", "", ""),
    SANANTONIO("SAS", "", ""),
    TORONTO("TOR", "", ""),
    UTAH("UTA", "", ""),
    WASHINGTON("WAS", "Washington", "Wizards");

    private final String brUrl;
    private final String teamMascot;
    private final String teamLocation;

    public String getBrUrl() {
        return brUrl;
    }

    public String getTeamMascot() {
        return teamMascot;
    }

    public String getTeamLocation() {
        return teamLocation;
    }

    NbaTeamInfo(String brUrl, String teamLocation, String teamMascot) {
        this.brUrl = brUrl;
        this.teamLocation = teamLocation;
        this.teamMascot = teamMascot;
    }
}
