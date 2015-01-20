package de.hrw.swep.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

public class DataManipulationInterfaceTest {
	private RealDBDataStore dmi;
	IDatabaseTester databaseTester;

	@Before
	public void setup() throws Exception {
		dmi = new RealDBDataStore();
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver",
				"jdbc:hsqldb:file:../db-layer/database/testdb", "sa", "");
		databaseTester.setDataSet(new FlatXmlDataSetBuilder().build(new File("full.xml")));
		databaseTester.onSetup();
	}

	@Test
	public void testDeleteUnknownStudent() throws SQLException, Exception {
		try {
			dmi.deleteStudent(0);
		} catch (PersistenceException e) {
			IDataSet actual = databaseTester.getConnection().createDataSet();
			Assertion.assertEquals(new FlatXmlDataSetBuilder().build(new File("full.xml")), actual);
			return;
		}
		fail();
	}

	@Test
	public void testDeleteStudent() throws SQLException, Exception {
		dmi.deleteStudent(12345);
		IDataSet actual = databaseTester.getConnection().createDataSet();
		Assertion.assertEquals(new FlatXmlDataSetBuilder().build(new File("student12345deleted.xml")), actual);
	}

	@Test
	public void testGetModuleID() {
		assertEquals(1, dmi.getModuleID("Programmieren"));
		assertEquals(3, dmi.getModuleID("Software Engineering"));
	}

	@Test
	public void testAddStudent() throws SQLException {
		dmi.addStudent(1, "Hans");
		assertEquals("Hans", dmi.getName(1));
	}

	@Test
	public void testAddModule() throws SQLException, Exception {
		dmi.addModule("P", 25);
		assertEquals(25, dmi.getCredit("P"));
		Assertion.assertEquals(new FlatXmlDataSetBuilder().build(new File("oneAddedModule.xml")), 
				databaseTester.getConnection().createDataSet());
	}

	@Test
	public void testAddGrade() {
		dmi.addGrade(23456, "Software Engineering Praktikum", 1.5f);
		assertEquals(1.5f,
				dmi.getGrade(23456, "Software Engineering Praktikum"), 0.0001);
	}

}
