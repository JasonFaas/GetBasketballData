package net.jasonfaas.aws.nba.team.elo.utility;

import net.jasonfaas.aws.nba.team.elo.utility.FileReaderAndWriter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jasonfaas on 11/8/15.
 */
public class OutputFileTest {
    @Test
    public void testContentCanBeWrittenAndRead() throws Exception {
        String x = "The first line\nThe second line\n";
        String fileName = "src/test/resources/testFile.txt";

        FileReaderAndWriter.writeToFile(fileName, x);
        StringBuilder sb = FileReaderAndWriter.readContentFromFile(fileName);

        Assert.assertEquals(sb.toString(), x);

    }

}
