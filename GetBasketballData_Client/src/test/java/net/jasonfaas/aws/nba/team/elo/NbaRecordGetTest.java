package net.jasonfaas.aws.nba.team.elo;

import net.jasonfaas.aws.nba.team.elo.utility.FileReaderAndWriter;
import net.jasonfaas.aws.nba.team.elo.utility.NetClientGet;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class NbaRecordGetTest {

    @Ignore
    @Test
    public void testGetRecordsFromInternet() throws Exception {
        String domain = "http://www.basketball-reference.com/";
        String path = null;
        String csvFileName = null;
        String fullFileName = null;

        for (NbaTeamInfo nbaTeam:NbaTeamInfo.values()) {
            System.out.println(nbaTeam.getBrUrl());

            String team = nbaTeam.getBrUrl();
            path = "/teams/" + team + "/2016_games.html";
            csvFileName = "src/main/resources/" + team + "_Scores_2015-16.csv";
            fullFileName = "src/main/resources/" + team + "_Scores_2015-16_Full.txt";

            if (new File(csvFileName).exists()) {
                System.out.println("\tSkip");
            } else {
                String clientCall = new NetClientGet().getClientCall(domain + path);

                FileReaderAndWriter.writeToFile(fullFileName, clientCall);

                String filterRecordFromFullPage = new PageFilter().getFilterRecordFromFullPage(clientCall);
                FileReaderAndWriter.writeToFile(csvFileName, filterRecordFromFullPage);
            }
        }
    }
}