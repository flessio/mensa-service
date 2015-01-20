package de.hrw.swep.persistence;

import java.sql.SQLException;

public interface DataManipulationInterface {
	void addStudent(int matrikel, String name) throws SQLException;
	void deleteStudent(int matrikel);
	void addModule(String name, int credits);
	void addGrade(int matrikel, String name, float grade);
	
	int getModuleID(String name);
}
