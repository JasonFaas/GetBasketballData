package net.jasonfaas.aws.nba.team.elo;

import net.jasonfaas.aws.nba.team.elo.utility.FileReaderAndWriter;
import org.junit.Assert;
import org.junit.Test;


public class PageFilterTest {

    @Test
    public void testFilterFullTeamPageForBoxTeamRecord() throws Exception {

        String clientCall = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Full").toString();
        String expected = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Box").toString();

        String actual = new PageFilter().tableOfStatsFromEntirePage(clientCall);

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testFilterRecordFromFull() throws Exception {
        String clientCall = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Full").toString();


        String expected = "G,Date,Opponent,Tm,Opp\n" +
                "1,2015-10-27,Detroit_Pistons,94,106\n" +
                "2,2015-10-29,New_York_Knicks,112,101\n" +
                "3,2015-10-30,Charlotte_Hornets,97,94\n" +
                "4,2015-11-01,Charlotte_Hornets,94,92\n" +
                "5,2015-11-03,Miami_Heat,98,92\n" +
                "6,2015-11-04,Brooklyn_Nets,101,87\n" +
                "7,2015-11-06,New_Orleans_Pelicans,121,115\n" +
                "8,2015-11-07,Washington_Wizards,114,99\n";

        String filterRecordFromFullPage = new PageFilter().getFilterRecordFromFullPage(clientCall);

        Assert.assertEquals(expected, filterRecordFromFullPage);
    }

}