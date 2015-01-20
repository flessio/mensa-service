package de.hrw.swep.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class RealDBDataStoreTest {

	RealDBDataStore ds;
	
	private static final Set<String> MODULES1 = new HashSet<String>(Arrays.asList("Programmieren","Objektorientierte Programmierung","Software Engineering"));
	private static final Set<String> MODULES2 = new HashSet<String>(Arrays.asList("Programmieren","Objektorientierte Programmierung"));
	private static final Set<Integer> ALLSTUDENTS = new HashSet<Integer>(Arrays.asList(12345,23456,34567,45678));
	
	@Before
	public void setup() throws SQLException {
		ds = new RealDBDataStore();
	}
	
	private <E> void  assertEqualSet(Set<E> expected, Set<E> actual) {
		assertNotNull(actual);
		assertEquals(expected.size(), actual.size());
		for (E s : actual)
			assertTrue(expected.contains(s));
	}
	
	
	@Test
	public void testGetName() {
		assertEquals("Maier",ds.getName(12345));
		assertEquals("Weber",ds.getName(34567));
	}

	@Test
	public void testGetModules() {
		assertEqualSet(MODULES2, ds.getModules(12345));
		assertEqualSet(MODULES1, ds.getModules(23456));
		
	}

	@Test
	public void testGetGrade() {
		assertEquals(2.1,ds.getGrade(12345, "Objektorientierte Programmierung"),0.0001);
		assertEquals(2.2,ds.getGrade(23456, "Objektorientierte Programmierung"),0.0001);
	}

	@Test
	public void testGetCredit() {
		assertEquals(5,ds.getCredit("Programmieren"));
	}

	@Test
	public void testGetAllStudents() {
		assertEqualSet(ALLSTUDENTS ,ds.getAllStudents());
	}

}
