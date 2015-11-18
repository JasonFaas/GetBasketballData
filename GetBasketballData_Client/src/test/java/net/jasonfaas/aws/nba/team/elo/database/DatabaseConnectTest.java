package net.jasonfaas.aws.nba.team.elo.database;

import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

public class DatabaseConnectTest {

    @Test
    public void testConnectToRdsDatabase() throws Exception {
// Read RDS connection information from the environment
        PropertyFileTest propertyFileTest = new PropertyFileTest();

        String dbName = propertyFileTest.getDatabaseProperty("RDS_DB_NAME");
        String userName = propertyFileTest.getDatabaseProperty("RDS_USERNAME");
        String password = propertyFileTest.getDatabaseProperty("RDS_USERNAME");
        String hostname = propertyFileTest.getDatabaseProperty("RDS_HOSTNAME");
        String port = propertyFileTest.getDatabaseProperty("RDS_PORT");

        Assert.assertTrue(!dbName.equals(""));
        Assert.assertTrue(!userName.equals(""));
        Assert.assertTrue(!password.equals(""));
        Assert.assertTrue(!hostname.equals(""));
        Assert.assertTrue(!port.equals(""));

        String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
                port + "/" + dbName + "?user=" + userName + "&password=" + password;

        System.out.println(jdbcUrl);


        // Load the JDBC driver
        try {
            System.out.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }

        Connection conn = null;
        Statement setupStatement = null;
        Statement readStatement = null;
        ResultSet resultSet = null;
        String results = "";
        int numresults = 0;
        String statement = null;

        final String tableName = "Beanstalk4";
        try {
            // Create connection to RDS DB instance
            conn = DriverManager.getConnection(jdbcUrl);

            // Create a table and write two rows
            setupStatement = conn.createStatement();
            String createTable = "CREATE TABLE " + tableName + " (Resource char(50));";
            String insertRow1 = "INSERT INTO " + tableName + " (Resource) VALUES ('EC2 Instance');";
            String insertRow2 = "INSERT INTO " + tableName + " (Resource) VALUES ('RDS Instance');";

            setupStatement.addBatch(createTable);
            setupStatement.addBatch(insertRow1);
            setupStatement.addBatch(insertRow2);
            setupStatement.executeBatch();
            setupStatement.close();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Assert.fail();
        } finally {
            System.out.println("Closing the connection.");
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                    Assert.fail();
                }
            }
        }

        try {
            conn = DriverManager.getConnection(jdbcUrl);

            readStatement = conn.createStatement();
            resultSet = readStatement.executeQuery("SELECT Resource FROM " + tableName + ";");

            resultSet.first();
            results = resultSet.getString("Resource");
            resultSet.next();
            results += ", " + resultSet.getString("Resource");

            resultSet.close();
            readStatement.close();
            conn.close();

        } catch (SQLException ex) {
            // Handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Assert.fail();
        } finally {
            System.out.println("Closing the connection.");
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                    Assert.fail();
                }
            }
        }
    }

}
