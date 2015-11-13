package net.jasonfaas.aws.nba.team.elo;

import net.jasonfaas.aws.nba.team.elo.utility.FileReaderAndWriter;
import org.junit.Assert;
import org.junit.Test;


public class PageFilterTest {

    @Test
    public void testFilterFullTeamPageForBoxTeamRecord() throws Exception {

        String clientCall = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Full.txt").toString();
        String expected = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Box.txt").toString();

        String actual = new PageFilter().tableOfStatsFromEntirePage(clientCall);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFilterRecordFromFull() throws Exception {
        String clientCall = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Full.txt").toString();

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

    @Test
    public void testRemovingAlignmentTagsAndRemovingNewLinesSimilarToTableSetup() throws Exception {
        String boxOfStats = "<tr class=\"\">\n" +
                "  <th data-stat=\"g\" align=\"right\"  class=\"tooltip sort_default_asc\"  tip=\"Games\">G</th>\n" +
                "  <th data-stat=\"date_game\" align=\"left\"  class=\"tooltip sort_default_asc\" >Date</th>\n" +
                "  <th data-stat=\"game_start_time\" align=\"CENTER\"  class=\"tooltip\" ></th>\n" +
                "  <th data-stat=\"network\" align=\"CENTER\"  class=\"tooltip\" ></th>\n" +
                "  <th data-stat=\"box_score_text\" align=\"center\"  class=\"tooltip sort_default_asc\" ></th>\n" +
                "  <th data-stat=\"game_location\" align=\"center\"  class=\"tooltip\" ></th>\n" +
                "  <th data-stat=\"opp_name\" align=\"left\"  class=\"tooltip sort_default_asc\" >Opponent</th>\n" +
                "  <th data-stat=\"game_result\" align=\"center\"  class=\"tooltip\" ></th>\n" +
                "  <th data-stat=\"overtimes\" align=\"center\"  class=\"tooltip\" ></th>\n" +
                "  <th data-stat=\"pts\" align=\"right\"  class=\"tooltip\"  tip=\"Points\">Tm</th>\n" +
                "  <th data-stat=\"opp_pts\" align=\"right\"  class=\"tooltip\"  tip=\"Opponent Points\">Opp</th>\n" +
                "  <th data-stat=\"wins\" align=\"right\"  class=\"tooltip\"  tip=\"Wins\">W</th>\n" +
                "  <th data-stat=\"losses\" align=\"right\"  class=\"tooltip\"  tip=\"Losses\">L</th>\n" +
                "  <th data-stat=\"game_streak\" align=\"left\"  class=\"tooltip\" >Streak</th>\n" +
                "  <th data-stat=\"game_remarks\" align=\"left\"  class=\"tooltip sort_default_asc show_partial_when_sorting\" >Notes</th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>\n";
        Assert.assertEquals("<tr class=\"\">,G,Date,,,,,Opponent,,,Tm,Opp,W,L,Streak,Notes</tr>\n", new PageFilter().removeAlignmentTagsAndRemoveNewLinesSimilarToTableSetup(boxOfStats));

    }
}