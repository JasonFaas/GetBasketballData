package net.jasonfaas.aws.nba.team.elo;

import net.jasonfaas.aws.nba.team.elo.schedule.NbaScheduleBoxFilter;
import net.jasonfaas.aws.nba.team.elo.utility.FileReaderAndWriter;
import org.junit.Assert;
import org.junit.Test;


public class AllFilterTest {

    @Test
    public void testFilterRecordFromFull() throws Exception {
        String clientCall = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Full.txt").toString();
        String expected = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Record_In_Csv.txt").toString();

        String filterRecordFromFullPage = new PageFilter().getFilterRecordFromFullPage(clientCall);

        Assert.assertEquals(expected, filterRecordFromFullPage);
    }

    @Test
    public void testFilterFullTeamPageForBoxTeamRecord() throws Exception {

        String clientCall = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Full.txt").toString();
        String expected = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Box.txt").toString();

        String actual = new PageFilter().tableOfStatsFromEntirePage(clientCall);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testBoxToBoxFilteredForBoxTeamRecord() throws Exception {

        String clientCall = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Box.txt").toString();
        String expected = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Box_Filtered.txt").toString();

        String actual = new PageFilter().removeAlignmentTagsAndRemoveNewLinesSimilarToTableSetup(clientCall);

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testBoxFilteredToCsvFormatForBoxTeamRecord() throws Exception {
        String clientCall = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Box_Filtered.txt").toString();
        String expected = FileReaderAndWriter.readContentFromFile("src/test/resources/AtlScores_2015-16_Record_In_Csv.txt").toString();

        String actual = new NbaScheduleBoxFilter().refactorThisBigThing(clientCall);

        Assert.assertEquals(expected, actual);
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