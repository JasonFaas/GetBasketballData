package net.jasonfaas.aws.nba.team.elo;

import net.jasonfaas.aws.nba.team.elo.utility.FileReaderAndWriter;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


public class NbaRecordGetTest {

    @Ignore
    @Test
    public void testGetRecordsFromInternet() throws Exception {
//        String url = "http://localhost:8080";
//        String url = "http://default-environment-zvvmdifi8c.elasticbeanstalk.com/";
        String url = "http://www.basketball-reference.com/";
        String mainPart = "/teams/ATL/2016_games.html";
        String clientCall = new NetClientGet().getClientCall(url + mainPart);


        System.out.println("Filtered .... \n");
        int beginIndex = clientCall.indexOf("<tr class=\"\">", clientCall.indexOf("<h2 data-mobile-header=\"\" style=\"\">Regular Season</h2>"));
        int endIndex = clientCall.indexOf("</tr>", clientCall.indexOf("<td align=\"right\" >82</td>")) + 6;
        String boxOfStats = clientCall.substring(beginIndex, endIndex);
        System.out.println(boxOfStats);
        String noHTMLString = boxOfStats.replaceAll("\\<th.*?\\>", "");
        noHTMLString = noHTMLString.replaceAll("\\</?td.*?\\>", "");
        noHTMLString = noHTMLString.replaceAll("</thead>\n" + "<tbody>\n", "");
        System.out.println(noHTMLString);

        FileReaderAndWriter.writeToFile("AtlScores_2015-16_Full", clientCall);
        FileReaderAndWriter.writeToFile("AtlScores_2015-16_Box", boxOfStats);
        FileReaderAndWriter.writeToFile("AtlScores_2015-16_Box_Filtered", noHTMLString);

        Assert.assertTrue(clientCall.contains("Hello Servlet"));

    }
}