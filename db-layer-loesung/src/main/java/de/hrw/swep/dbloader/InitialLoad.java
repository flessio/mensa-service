package de.hrw.swep.dbloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InitialLoad {
	public static void main(String[] args) throws SQLException {

		Connection c = DriverManager.getConnection(
				"jdbc:hsqldb:file:../db-layer/database/testdb", "sa", "");
		c.setAutoCommit(false);
		System.out.println("Autocommit " + (c.getAutoCommit() ? "on" : "off"));

		c.createStatement().executeQuery("DROP TABLE TAKES IF EXISTS");
		c.createStatement().executeQuery("DROP TABLE COURSE IF EXISTS");
		c.createStatement().executeQuery("DROP TABLE STUDENT IF EXISTS");

		c.createStatement().executeQuery("CREATE TABLE STUDENT (matrikel INTEGER PRIMARY KEY, name varchar(255)) ");				
		c.createStatement().executeQuery("CREATE TABLE COURSE (id INTEGER PRIMARY KEY, name varchar(255), credit INTEGER) ");
		c.createStatement().executeQuery(
				"CREATE TABLE TAKES (student INTEGER , course INTEGER, grade FLOAT,  "
				+ "constraint PK_TAKES PRIMARY KEY (student, course), "
				+ "constraint FK_COURSE FOREIGN KEY (course) REFERENCES COURSE(id), "
				+ "constraint FK_STUDENT FOREIGN KEY (student) REFERENCES STUDENT(matrikel))");
		
		c.createStatement().executeQuery("INSERT INTO STUDENT VALUES (12345,'Maier')");
		c.createStatement().executeQuery("INSERT INTO STUDENT VALUES (23456,'Müller')");
		c.createStatement().executeQuery("INSERT INTO STUDENT VALUES (34567,'Weber')");
		c.createStatement().executeQuery("INSERT INTO STUDENT VALUES (45678,'Schmidt')");
		
		c.createStatement().executeQuery("INSERT INTO COURSE VALUES (1,'Programmieren',5)");
		c.createStatement().executeQuery("INSERT INTO COURSE VALUES (2,'Objektorientierte Programmierung',5)");
		c.createStatement().executeQuery("INSERT INTO COURSE VALUES (3,'Software Engineering',4)");
		c.createStatement().executeQuery("INSERT INTO COURSE VALUES (4,'Software Engineering Praktikum',3)");
		
		c.createStatement().executeQuery("INSERT INTO TAKES VALUES(12345, 1, 1.1)");
		c.createStatement().executeQuery("INSERT INTO TAKES VALUES(12345, 2, 2.1)");
		
		c.createStatement().executeQuery("INSERT INTO TAKES VALUES(23456, 1, 1.2)");
		c.createStatement().executeQuery("INSERT INTO TAKES VALUES(23456, 2, 2.2)");
		c.createStatement().executeQuery("INSERT INTO TAKES VALUES(23456, 3, 3.2)");

		
		c.createStatement().executeQuery("INSERT INTO TAKES VALUES(34567, 1, 1.3)");
		c.createStatement().executeQuery("INSERT INTO TAKES VALUES(34567, 3, 2.3)");
		c.createStatement().executeQuery("INSERT INTO TAKES VALUES(34567, 4, 3.3)");

		c.createStatement().executeQuery("INSERT INTO TAKES VALUES(45678, 1, 3.4)");

		
		c.commit();
		c.close();
		System.out.println("ready");

	}
}
