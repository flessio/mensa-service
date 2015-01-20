package de.hrw.swep.persistence;

import java.sql.SQLException;

import org.junit.Test;

public class BreakRealDBDataStoreTest {

	@Test
	public void breakDatabase() throws SQLException {
		DataStoreInterface dsi = new RealDBDataStore();
		System.out.println( dsi.getCredit("\';SELECT matrikel FROM STUDENT WHERE NAME !=\'"));
	}

}
