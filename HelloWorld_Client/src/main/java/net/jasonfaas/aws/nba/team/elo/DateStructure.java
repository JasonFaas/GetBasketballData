package net.jasonfaas.aws.nba.team.elo;

/**
 * Created by jasonfaas on 11/9/15.
 */
public class DateStructure {
    private static String replaceMonthWithCount(String s) {
        String replace = s.replace("Oct", "10").replace("Nov", "11");
        return replace;
    }

    public static String convertDate(String websiteDataStructure) {
        String myDateOrder = rearrangeDateStructure(websiteDataStructure);
        String myDateOrderWithMonthCount = replaceMonthWithCount(myDateOrder);
        String myFinalDateStructure = removeExtraZeroInDays(myDateOrderWithMonthCount);
        return myFinalDateStructure;
    }

    private static String removeExtraZeroInDays(String s) {
        String pattern = "(.*-.*-)0(\\d\\d)";
        return s.replaceAll(pattern, "$1$2");
    }

    private static String rearrangeDateStructure(String theirs) {
        String pattern = "(.*)\\w\\w\\w, (\\w\\w\\w) (\\d\\d?), (\\d\\d\\d\\d)(.*)";
        return theirs.replaceAll(pattern, "$1$4-$2-0$3$5");
    }
}
