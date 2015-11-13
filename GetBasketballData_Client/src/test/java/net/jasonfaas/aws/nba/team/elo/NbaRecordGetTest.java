package net.jasonfaas.aws.nba.team.elo;

import net.jasonfaas.aws.nba.team.elo.utility.FileReaderAndWriter;
import net.jasonfaas.aws.nba.team.elo.utility.NetClientGet;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


public class NbaRecordGetTest {

    @Ignore
    @Test
    public void testGetRecordsFromInternet() throws Exception {
        String url = "http://www.basketball-reference.com/";
        String mainPart = "/teams/ATL/2016_games.html";
        String clientCall = new NetClientGet().getClientCall(url + mainPart);

        FileReaderAndWriter.writeToFile("AtlScores_2015-16_Full_test_v2.txt", clientCall);

        String expected = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Record_In_Csv.txt").toString();

        String filterRecordFromFullPage = new PageFilter().getFilterRecordFromFullPage(clientCall);

        Assert.assertEquals(expected, filterRecordFromFullPage);
    }
}