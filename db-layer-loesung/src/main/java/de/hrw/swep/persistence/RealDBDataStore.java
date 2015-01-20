package de.hrw.swep.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RealDBDataStore implements DataStoreInterface,
		DataManipulationInterface {

	public String getName(int matrikel) {
		return getResultAsStrings("SELECT name FROM STUDENT WHERE matrikel=" + matrikel)
				.get(0);
	}

	public Set<String> getModules(int matrikel) {
		return new HashSet<String>(
				getResultAsStrings("SELECT c.name FROM TAKES t inner join COURSE c on (c.id = t.course) WHERE t.student="
						+ matrikel));
	}

	public float getGrade(int matrikel, String module) {
		return getFirstFloat("SELECT t.grade FROM TAKES t inner join COURSE c on (c.id = t.course) WHERE t.student="
				+ matrikel + " AND c.name=\'" + module + "\'");

	}

	public int getCredit(String module) {
		return Integer
				.parseInt(getResultAsStrings(
						"SELECT c.credit FROM COURSE c WHERE c.name=\'"
								+ module + "\'").get(0));
	}

	public Set<Integer> getAllStudents() {
		return new HashSet<Integer>(
				getResultAsInts("SELECT matrikel FROM STUDENT"));
	}

	public void addStudent(int matrikel, String name) {
		try {
			int res = executeUpdate("INSERT INTO STUDENT VALUES(" + matrikel + ",\'" + name
					+ "\')");
			if (res == 0)
				throw new PersistenceException("Student could not be added.");
		} catch (SQLException e) {
			throw new PersistenceException("Student could not be added.",e);
		}
	}

	public void deleteStudent(int matrikel) {
		try {
			executeUpdate("DELETE FROM TAKES WHERE student=" + matrikel);
			int res = executeUpdate("DELETE FROM STUDENT WHERE matrikel=" + matrikel);
			if (res == 0)
				throw new PersistenceException("Student could not be deleted.");
			
			// res > 1 cannot occur due to PK on matrikel
			
		} catch (SQLException e) {
			throw new PersistenceException("Student could not be deleted.",e);
		}

	}

	public void addModule(String name, int credits) {
		try {
			ResultSet rs = executeQuery("select max(id) from course");
			int maxId = getInt(rs);

			if (maxId == -1)
				return;

			maxId++;
			int res = executeUpdate("insert into course values (" + maxId + ",\'" + name
					+ "\'," + credits + ")");
			if (res == 0)
				throw new PersistenceException("Module could not be added.");
		} catch (SQLException e) {
			throw new PersistenceException("Module could not be added.",e);

		}
	}

	public void addGrade(int matrikel, String name, float grade) {
		int moduleId = getModuleID(name);
		try {
			int res = executeUpdate("insert into takes values (" + matrikel + ","
					+ moduleId + "," + grade + ")");
			if (res == 0)
				throw new PersistenceException("Grade could not be added.");
		} catch (SQLException e) {
			throw new PersistenceException("Grade could not be added.",e);
		}
	}

	public int getModuleID(String name) {
		ResultSet rs;
		try {
			rs = executeQuery("select id from course where name=\'" + name
					+ "\'");
			return getInt(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Module id could not be read.",e);
		}

	}

	private ResultSet executeQuery(String sql) throws SQLException {
		Connection c = null;
		try {
			c = DriverManager.getConnection(
					"jdbc:hsqldb:file:../db-layer/database/testdb", "sa", "");
			ResultSet rs = c.createStatement().executeQuery(sql);
			c.commit();
			return rs;
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int executeUpdate(String sql) throws SQLException {
		Connection c = null;
		try {
			c = DriverManager.getConnection(
					"jdbc:hsqldb:file:../db-layer/database/testdb", "sa", "");
			int result = c.createStatement().executeUpdate(sql);
			c.commit();
			return result;
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private int getInt(ResultSet rs) throws SQLException {
		if (rs != null && rs.next())
			return rs.getInt(1);

		throw new PersistenceException("result set error");
	}
	
	private float getFloat(ResultSet rs) throws SQLException {
		if (rs != null && rs.next())
			return rs.getFloat(1);

		throw new PersistenceException("result set error");
	}

	private List<String> getResultAsStrings(String sql) {
		List<String> list = new ArrayList<String>();
		try {
			ResultSet result = executeQuery(sql);
			while (result.next())
				list.add(result.getString(1));
		} catch (SQLException e) {
			throw new PersistenceException("No strings could be read.",e);
		}
		return list;
	}

	private List<Integer> getResultAsInts(String sql) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			ResultSet result = executeQuery(sql);
			while (result.next())
				list.add(result.getInt(1));
		} catch (SQLException e) {
			throw new PersistenceException("No integers could be read.",e);
		}
		return list;
	}

	private Float getFirstFloat(String sql) {
		try {
			ResultSet rs = executeQuery(sql);
			return getFloat(rs);
		} catch (SQLException e) {
			throw new PersistenceException("No float could be read.",e);
		}
	}

}
