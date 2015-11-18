package net.jasonfaas.aws.nba.team.elo.database;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * Created by jasonfaas on 11/17/15.
 */
public class PropertyFileTest {

    @Test
    public void testPropertyFileGet() throws Exception {
        Assert.assertEquals("dbTheName", getDatabaseProperty("dbName"));
    }

    public String getDatabaseProperty(String dbName) throws Exception {

        String result = "";
        InputStream inputStream = null;
        try{
            Properties prop = new Properties();
            String propFileName = "temp/database.properties";

            inputStream = new FileInputStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            result = prop.getProperty(dbName);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }

}
