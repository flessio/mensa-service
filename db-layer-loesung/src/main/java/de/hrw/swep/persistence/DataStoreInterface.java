package de.hrw.swep.persistence;

import java.util.Set;

/**
 * This is the interface to the data store. The StudentService can only get data from this DataStore.
 * 
 * @author Michael Friedrich
 *
 */
public interface DataStoreInterface {
	
	/**
	 * returns the student's name for a given matricualtion number
	 * 
	 * @param matrikel the student's matriculation number
	 * @return the student's name
	 */
	String getName(int matrikel);
	
	/**
	 * returns the set of passed modules for a given student
	 * 
	 * @param matrikel the student's matriculation number
	 * @return the set of passed modules
	 */
	Set<String> getModules(int matrikel);
	
	/**
	 * the grade of a student for a given module
	 * 
	 * @param matrikel the student's matriculation number
	 * @param module the name of the module
	 * @return the grade of the module or -1 if the exam has not yet been taken
	 */
	float getGrade(int matrikel, String module);
	
	/**
	 * returns the number of credit points for a given module
	 *  
	 * @param module the name of the module
	 * @return the credit points
	 */
	int getCredit(String module);
	
	/**
	 * returns a set of the matriculation numbers of all students.
	 * 
	 * @return set of matricualaltion numbers
	 */
	Set<Integer> getAllStudents();	
}
