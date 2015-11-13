package net.jasonfaas.aws.nba.team.elo.schedule;

import net.jasonfaas.aws.nba.team.elo.DateStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jasonfaas on 11/12/15.
 */
public class NbaScheduleBoxFilter {

    public static final int REGULAR_SEASON_GAME_COUNT = 82;
    public static final int BR_BOX_SEPARATOR_COUNT = 20;
    public static final int GAME_COUNT_CELL = 0;
    public static final int GAME_DATE_CELL = 1;
    public static final int OPPONENT_CELL = 6;
    public static final int TEAM_SCORE_CELL = 9;
    public static final int OPPONENT_SCORE_CELL = 10;
    public static final int WIN_LOSS_CELL = 7;

    public StringBuilder getInfoFromSingleGame(String record) {
        StringBuilder finalRecord = new StringBuilder();
        for (String singleGameInfo:record.split("\n")) {
            List<String> cellOfSingleGame = getArrayListFromSplittingString(singleGameInfo, ",");
            if (isGameComplete(cellOfSingleGame)) {
                finalRecord.append(cellOfSingleGame.get(GAME_COUNT_CELL));
                finalRecord.append(",");
                finalRecord.append(dateOfGame(cellOfSingleGame));
                finalRecord.append(",");
                finalRecord.append(opponent(cellOfSingleGame));
                finalRecord.append(",");
                finalRecord.append(cellOfSingleGame.get(TEAM_SCORE_CELL));
                finalRecord.append(",");
                finalRecord.append(cellOfSingleGame.get(OPPONENT_SCORE_CELL));
                finalRecord.append("\n");
            }
        }
        return finalRecord;
    }

    private String opponent(List<String> cellOfSingleGame) {
        String opponent = cellOfSingleGame.get(OPPONENT_CELL);
        return opponent.substring(opponent.indexOf(">") + 1);
    }

    private String dateOfGame(List<String> cellOfSingleGame) {
        String cellOfGameDate = cellOfSingleGame.get(GAME_DATE_CELL);
        return cellOfGameDate.substring(cellOfGameDate.indexOf(">") + 1);
    }

    private boolean isGameComplete(List<String> cellOfSingleGame) {
        String winLossValue = cellOfSingleGame.get(WIN_LOSS_CELL);
        return "L".equals(winLossValue) || "W".equals(winLossValue);
    }

    private ArrayList getArrayListFromSplittingString(String split, String regex) {
        return new ArrayList(Arrays.asList(split.split(regex)));
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

    public List<String> filterTeamResultsForTitleAnd82Games(String noHTMLString) {
        String regex = "\n";
        List<String> strings = getArrayListFromSplittingString(noHTMLString, regex);
        assert REGULAR_SEASON_GAME_COUNT + REGULAR_SEASON_GAME_COUNT / BR_BOX_SEPARATOR_COUNT + 1 == strings.size();
        strings.remove(0);
        return strings;
    }

    public String removeExtraWhiteSpaceAndHtmlTags(String string) {
        return string
                .replaceAll(" ?, ?", ",")
                .replaceAll("<tr  class=\"\">,", "")
                .replaceAll("</a>", "");
    }

    public String refactorThisBigThing(String filteredBox) {
        List<String> strings = filterTeamResultsForTitleAnd82Games(filteredBox);
        List<String> removedStrings = removeDuplicatesAndOriginal(strings);

        assert 1 == removedStrings.size();
        assert REGULAR_SEASON_GAME_COUNT == strings.size();

        String filteredBoxWithDateFormatted = new DateStructure().convertDate(strings);
        String record = removeExtraWhiteSpaceAndHtmlTags(filteredBoxWithDateFormatted);

        StringBuilder finalRecord = getInfoFromSingleGame(record);

        String s1 = finalRecord.toString().replaceAll(" ", "_");
        assert stuffRename(s1.split("\n"));

        String s = removedStrings.get(0);
        String replace = s.replace("<tr class=\"no_ranker thead\">,", "");
        String actual = replace.replaceAll(",,,,,", ",");
        actual = actual.replaceAll(",,,", ",");
        actual = actual.replaceAll("(.*Opp).*", "$1");

        assert "G,Date,Opponent,Tm,Opp".equals(actual);

        return  actual + "\n" + s1;
    }

    private boolean stuffRename(String[] s1) {
        Pattern p = Pattern.compile("\\d{1,2},\\d{4}-\\d{2}-\\d{2},\\w+,\\d{2,3},\\d{2,3}");
        Matcher matcher = null;
        for (String gameResultFinalFormat: s1) {
            matcher = p.matcher(gameResultFinalFormat);
            if (!matcher.matches()) {
                System.out.println("Failed single game csv test:" + gameResultFinalFormat + ":");
                return false;
            }
        }

        return true;
    }
}
