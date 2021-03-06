package net.jasonfaas.aws.nba.team.elo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jasonfaas on 11/9/15.
 */
public class DateStructureTest {

    private DateStructure dateStructure;

    @Before
    public void setUp() throws Exception {
        dateStructure = new DateStructure();
    }

    @Test
    public void testDateChangerFromTheirsToStandards() throws Exception {
        String theirs = "Tue, Oct 2, 2015";
        String standard = "2015-10-02";

        Assert.assertEquals(standard, dateStructure.convertDate(theirs));
    }

    @Test
    public void testDateChangerFromTheirsToStandardsWith2DigitDayAndExtraText() throws Exception {
        String theirs = "toherstuffTue, Nov 22, 2015what is this";
        String standard = "toherstuff2015-11-22what is this";

        Assert.assertEquals(standard, dateStructure.convertDate(theirs));
    }
}