package net.jasonfaas.aws.nba.team.elo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jasonfaas on 11/9/15.
 */
public class PageFilter {

    public StringBuilder getInfoFromSingleGame(String record) {
        StringBuilder finalRecord = new StringBuilder();
        for (String piece:record.split("\n")) {
            List<String> split = new ArrayList(Arrays.asList(piece.split(",")));

            for (String smallPiece:  split) {
                System.out.println(smallPiece);
            }
            if (split.get(7).length() > 0) {
                System.out.println("what:" + split.get(7));
                finalRecord.append(split.get(0));
                finalRecord.append(",");
                finalRecord.append(split.get(1).substring(split.get(1).indexOf(">") + 1));
                finalRecord.append(",");
                finalRecord.append(split.get(6).substring(split.get(6).indexOf(">") + 1));
                finalRecord.append(",");
                finalRecord.append(split.get(9));
                finalRecord.append(",");
                finalRecord.append(split.get(10));
                finalRecord.append("\n");
            }
            for (String smallPiece:  split) {
                System.out.println(smallPiece);
            }
        }
        return finalRecord;
    }

    public String removeUnwantedData(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            sb.append(DateStructure.convertDate(strings.get(i)));
            sb.append("\n");
        }
        return sb.toString();
    }

    public List<String> removeDuplicatesAndOriginal(List<String> strings) {
        ArrayList<String> duplicateStrings = new ArrayList<String>();
        for (int startVal = 0; startVal < strings.size(); startVal++) {
            String startListItem = strings.get(startVal);
            boolean isDup = false;
            for (int endVal = startVal + 1; endVal < strings.size(); endVal++) {
                if (startListItem.equals(strings.get(endVal))) {
                    isDup = true;
                    strings.remove(endVal);
                    endVal--;
                }
            }
            if (isDup) {
                duplicateStrings.add(strings.get(startVal));
                strings.remove(startVal);
            }
        }
        return duplicateStrings;
    }

    public String tableOfStatsFromEntirePage(String clientCall) {
        int beginIndex = clientCall.indexOf("<tr class=\"\">", clientCall.indexOf("<h2 data-mobile-header=\"\" style=\"\">Regular Season</h2>"));
        int endIndex = clientCall.indexOf("</tr>", clientCall.indexOf("<td align=\"right\" >82</td>")) + 6;
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

    static List<String> filterTeamResultsForTitleAnd82Games(String noHTMLString) {

        List<String> strings = new ArrayList(Arrays.asList(noHTMLString.split("\n")));
        assert 82 + 82/20 + 1 == strings.size();
        strings.remove(0);
        return strings;
    }

    public String removeExtraWhiteSpaceAndHtmlTags(List<String> strings) {
        return removeUnwantedData(strings)
                .replaceAll(", ", ",")
                .replaceAll(" ,", ",")
                .replaceAll("<tr  class=\"\">,", "")
                .replaceAll("</a>", "");
    }

    public String getFilterRecordFromFullPage(String clientCall) {
        String noHTMLString = tableOfStatsFromEntirePage(clientCall);

        System.out.println("ABCD:"+noHTMLString);
        noHTMLString = removeAlignmentTagsAndRemoveNewLinesSimilarToTableSetup(noHTMLString);
        System.out.println("EFGH:"+noHTMLString);
        List<String> strings = filterTeamResultsForTitleAnd82Games(noHTMLString);
        List<String> removedStrings = removeDuplicatesAndOriginal(strings);
        assert 1 == removedStrings.size();
        assert 82 == strings.size();

        String record = removeExtraWhiteSpaceAndHtmlTags(strings);

        StringBuilder finalRecord = getInfoFromSingleGame(record);


        System.out.println(finalRecord);
        String s1 = finalRecord.toString().replaceAll(" ", "_");
        assert s1.contains("7,2015-11-06,New_Orleans_Pelicans,121,115");

        String s = removedStrings.get(0);
        System.out.println("Whatthat:"+s);
        String replace = s.replace("<tr class=\"no_ranker thead\">,", "");
        String actual = replace.replaceAll(",,,,,", ",");
        actual = actual.replaceAll(",,,", ",");
        actual = actual.replaceAll("(.*Opp).*", "$1");

        assert "G,Date,Opponent,Tm,Opp".equals(actual);

        return actual + "\n" + s1;
    }
}
