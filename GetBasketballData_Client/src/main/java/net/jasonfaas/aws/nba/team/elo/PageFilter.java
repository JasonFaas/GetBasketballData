package net.jasonfaas.aws.nba.team.elo;

import net.jasonfaas.aws.nba.team.elo.schedule.NbaScheduleBoxFilter;

/**
 * Created by jasonfaas on 11/9/15.
 */
public class PageFilter {

    public String tableOfStatsFromEntirePage(String clientCall) {
        int beginIndex = clientCall.indexOf("<tr class=\"\">", clientCall.indexOf("<h2 data-mobile-header=\"\" style=\"\">Regular Season</h2>"));
        String eightySecondGameNotation = "<tr  class=\"\">\n" + "   <td align=\"right\" >82</td>";
        int endIndex = clientCall.indexOf("</tr>", clientCall.indexOf(eightySecondGameNotation)) + "</tr>".length() + 1;
        String tableOfStats = clientCall.substring(beginIndex, endIndex);
        return tableOfStats;
    }

    public String removeAlignmentTagsAndRemoveNewLinesSimilarToTableSetup(String boxOfStats) {
        String noHTMLString = boxOfStats;
        noHTMLString = noHTMLString.replaceAll("</thead>\n<tbody>\n", "");
        noHTMLString = noHTMLString.replaceAll("</?th.*?>", "");
        noHTMLString = noHTMLString.replaceAll("</?td.*?>", "");
        noHTMLString = noHTMLString.replace("\n  ", ",");
        noHTMLString = noHTMLString.replace("\n</tr>", "</tr>");
        return noHTMLString;
    }

    public String getFilterRecordFromFullPage(String clientCall) {
        String tableOfStats = tableOfStatsFromEntirePage(clientCall);
        String removeNewLinesForCsvPrep = removeAlignmentTagsAndRemoveNewLinesSimilarToTableSetup(tableOfStats);
        String s2 = new NbaScheduleBoxFilter().refactorThisBigThing(removeNewLinesForCsvPrep);
        return s2;
    }

}
