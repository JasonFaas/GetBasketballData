package net.jasonfaas.aws.nba.team.elo;

import java.util.List;

/**
 * Created by jasonfaas on 11/9/15.
 */
public class DateStructure {
    public String convertDate(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            String convertedDate = convertDate(strings.get(i));
            sb.append(convertedDate);
            sb.append("\n");
        }
        return sb.toString();
    }

    private String replaceMonthWithCount(String s) {
        String replace = s.replace("Oct", "10").replace("Nov", "11");
        return replace;
    }

    public String convertDate(String websiteDataStructure) {
        String myDateOrder = rearrangeDateStructure(websiteDataStructure);
        String myDateOrderWithMonthCount = replaceMonthWithCount(myDateOrder);
        String myFinalDateStructure = removeExtraZeroInDays(myDateOrderWithMonthCount);
        return myFinalDateStructure;
    }

    private String removeExtraZeroInDays(String s) {
        String pattern = "(.*-.*-)0(\\d\\d)";
        return s.replaceAll(pattern, "$1$2");
    }

    private String rearrangeDateStructure(String theirs) {
        String pattern = "(.*)\\w\\w\\w, (\\w\\w\\w) (\\d\\d?), (\\d\\d\\d\\d)(.*)";
        return theirs.replaceAll(pattern, "$1$4-$2-0$3$5");
    }
}
