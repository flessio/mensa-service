package dbunit;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class Exporter
{
    public static void main(String[] args) throws Exception
    {
        // database connection
        Connection jdbcConnection = DriverManager.getConnection(
				"jdbc:hsqldb:file:../db-layer/database/testdb", "sa", "");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // full database export
        IDataSet fullDataSet = connection.createDataSet();
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
        connection.close();
        jdbcConnection.close();
    }
}