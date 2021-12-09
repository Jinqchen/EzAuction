// Ziming Li ziming.li@stonybrook.edu
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Employee;

public class EmployeeDao {
	/*
	 * This class handles all the database operations related to the employee table
	 */
	public String addEmployee(Employee employee) {
		

		/*
		 * All the values of the add employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the employee details and return "success" or "failure" based on result of the database insertion.
		 */
		
		
		boolean success = true;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
			ResultSet rs = cusST.executeQuery("SELECT * FROM Person WHERE SSN = '" + employee.getEmployeeID() + "'");
			if (!rs.next()) {
				int status = cusST.executeUpdate("INSERT INTO Person(SSN, firstName, lastName, email, address, city, state, zipCode, telephone, passwords, roles)\r\n"
			    		+ "VALUES ('" + employee.getEmployeeID() + "', '" + employee.getFirstName() + "', '" + employee.getLastName() 
			    		+ "', '" + employee.getEmail() + "', '" + employee.getAddress() + "', '" + employee.getCity() 
			    		+ "', '" + employee.getState() + "', " + employee.getZipCode() + ", '" + employee.getTelephone() 
			    		+ "', passwords='00000000', roles='employee')");
				
			}
			int status = cusST.executeUpdate("INSERT INTO Employee(employeeID, startDate, hourlyRate)\r\n"
	    			+ "VALUES('" + employee.getEmployeeID() + "', '" + employee.getStartDate() + "', " 
	    			+ employee.getHourlyRate() + ")");
		    
	    	if (status == 0)
	    		success = false;
	    	
		    cusST.close();
		} 
		catch (SQLException e) {
			success = false;
			e.printStackTrace();
		} 
		catch (Exception e2) {
			success = false;
			e2.printStackTrace();
		} 
	
		return success? "success" : "failure";

	}

	public String editEmployee(Employee employee) {
		/*
		 * All the values of the edit employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		boolean success = true;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
			int status = cusST.executeUpdate("UPDATE Person\r\n"
	    			+ "SET firstName='" + employee.getFirstName() + "', lastName='" + employee.getLastName() 
	    			+ "', email='" + employee.getEmail() + "', address='" + employee.getAddress() 
	    			+ "', city='" + employee.getCity() + "', state='" + employee.getState() + "', zipCode= " + employee.getZipCode() 
	    			+ ", telephone='" + employee.getTelephone() + "', roles='employee'\r\n"
	    			+ "WHERE SSN='" + employee.getEmployeeID() + "'");
		    
		    
		    // if 0 rows be affected
		    if (status == 0) {
		    	success = false;
		    }
		    else {
		    	status = cusST.executeUpdate("UPDATE Employee\r\n"
			    		+ "SET startDate='" + employee.getStartDate() + "', hourlyRate= " + employee.getHourlyRate() + "\r\n"
			    		+ "WHERE employeeID='" + employee.getEmployeeID() + "'");
		    	if (!success && status == 0)
		    		success = false;
		    }
		    cusST.close();
		} 
		catch (SQLException e) {
			success = false;
			e.printStackTrace();
		} 
		catch (Exception e2) {
			success = false;
			e2.printStackTrace();
		} 
	
		return success? "success" : "failure";

	}

	public String deleteEmployee(String employeeID) {
		/*
		 * employeeID, which is the Employee's ID which has to be deleted, is given as method parameter
		 * The sample code returns "success" by default.
		 * You need to handle the database deletion and return "success" or "failure" based on result of the database deletion.
		 */
		
		boolean success = true;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
			
			// check does customer table exists the employeeID we want to delete, 
			// if so: only delete from only employee table, 
			// if no: delete from both employee and person tables
			ResultSet rs = cusST.executeQuery("SELECT * FROM customer\r\n"
					+ "WHERE customerID='" + employeeID + "'");
			
			
			
			if (rs.next()) { // if exists
				// save the row in person table, 
				// then delete it, 
				// so the row in employee table can be deleted, 
				// lastly store the row that we saved in person table
				rs = cusST.executeQuery("SELECT * FROM person\r\n"
						+ "WHERE SSN = '" + employeeID + "'");
				if (rs.next()) {
					int status = cusST.executeUpdate("DELETE FROM person\r\n"
			    			+ "WHERE SSN = '" + employeeID + "'");
					
					if (status == 0) {
				    	success = false;
				    }
					else {
						status = cusST.executeUpdate("DELETE FROM employee\r\n"
					    		+ "WHERE employeeID = '" + employeeID + "'");
				    	if (status == 0)
				    		success = false;
				    	else {
				    		status = cusST.executeUpdate("INSERT INTO Person(SSN, firstName, lastName, email, address, city, state, zipCode, telephone, passwords, roles)\r\n"
					    		+ "VALUES ('" + rs.getString("SSN") + "', '" + rs.getString("firstName") + "', '" + rs.getString("lastName") 
					    		+ "', '" + rs.getString("email") + "', '" + rs.getString("address") + "', '" + rs.getString("city") 
					    		+ "', '" + rs.getString("state") + "', " + rs.getInt("zipCode") + ", '" + rs.getString("telephone") 
					    		+ "', passwords='00000000', roles='customer')");
				    		if (status == 0)
					    		success = false;
				    	}
					}
				}
				else {
					success = false;
				}
			}
			else { // if not exists
				int status = cusST.executeUpdate("DELETE FROM employee\r\n"
			    		+ "WHERE employeeID = '" + employeeID + "'");
		    	if (status == 0)
		    		success = false;
		    	else {
		    		status = cusST.executeUpdate("DELETE FROM person\r\n"
			    			+ "WHERE SSN = '" + employeeID + "'");
					
					if (status == 0) {
				    	success = false;
				    }
		    	}
		    	
				
			}
			
		    cusST.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} 
		catch (Exception e2) {
			success = false;
			e2.printStackTrace();
		} 
	
		return success? "success" : "failure";

	}

	
	public List<Employee> getEmployees() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to return details about all the employees must be implemented
		 * Each record is required to be encapsulated as a "Employee" class object and added to the "employees" List
		 */

		List<Employee> employees = new ArrayList<Employee>();
		
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement st = conn.createStatement();
		    ResultSet empRS = st.executeQuery("SELECT * FROM Employee");
		    // Extract data from result set
		    while (empRS.next()) {
		    	Employee newEmp = new Employee();
		    	
		    	// from "employee" table
		    	newEmp.setEmployeeID(empRS.getString("employeeID"));
		    	newEmp.setStartDate(empRS.getString("startDate"));
		    	newEmp.setHourlyRate(empRS.getFloat("hourlyRate"));
		        
		    	// from "person" table
		        Statement matchPersonST = conn.createStatement();
			    ResultSet matchPersonRS = matchPersonST.executeQuery("SELECT P.* FROM Person P WHERE P.SSN = '" + empRS.getString("employeeID") + "'");
		    	if (matchPersonRS.next()) {
		    		newEmp.setFirstName(matchPersonRS.getString("firstName"));
		    		newEmp.setLastName(matchPersonRS.getString("lastName"));
		    		newEmp.setAddress(matchPersonRS.getString("address"));
		    		newEmp.setCity(matchPersonRS.getString("city"));
		    		newEmp.setState(matchPersonRS.getString("state"));
		    		newEmp.setZipCode(matchPersonRS.getInt("zipCode"));
		    		newEmp.setLevel("customerRepresentative");
		    		newEmp.setEmail(matchPersonRS.getString("email"));
		    		newEmp.setTelephone(matchPersonRS.getString("telephone"));
		    		
		    		// set revenue
		    		Statement revenueST = conn.createStatement();
				    ResultSet revenueRS = revenueST.executeQuery("SELECT S.employeeID, SUM(A.closingBid) as revenue\r\n"
				    		+ "FROM supervisedby S, Auction A\r\n"
				    		+ "WHERE S.auctionID = A.auctionID\r\n"
				    		+ "	AND S.employeeID = '" + newEmp.getEmployeeID() + "';");
				    if (revenueRS.next()) {
				    	newEmp.setRevenue(String.valueOf(revenueRS.getInt("revenue")));
				    }
				    revenueRS.close();
				    revenueST.close();
		    	}
		    	matchPersonRS.close();
		    	matchPersonST.close();
		    	
		        employees.add(newEmp);
		     }
		    empRS.close();
		    st.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
		
		return employees;
	}

	public Employee getEmployee(String employeeID) {

		/*
		 * The students code to fetch data from the database based on "employeeID" will be written here
		 * employeeID, which is the Employee's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Employee" class object
		 */

		Employee resEmp = new Employee();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement empST = conn.createStatement();
		    ResultSet empRS = empST.executeQuery("SELECT e.*, p.*\r\n"
		    		+ "FROM employee e, person p\r\n"
		    		+ "WHERE e.employeeID = p.SSN\r\n"
		    		+ "	AND e.employeeID = '" + employeeID  + "'");
		    
		    if (empRS.next()) {
		    	// from "employee" table
		    	resEmp.setEmployeeID(empRS.getString("employeeID"));
		    	resEmp.setStartDate(empRS.getString("startDate"));
		    	resEmp.setHourlyRate(empRS.getInt("hourlyRate"));
		    	
		    	// from "person" table
		    	resEmp.setAddress(empRS.getString("address"));
		    	resEmp.setCity(empRS.getString("city"));
		    	resEmp.setEmail(empRS.getString("email"));
		    	resEmp.setFirstName(empRS.getString("firstName"));
		    	resEmp.setLastName(empRS.getString("lastName"));
		    	resEmp.setState(empRS.getString("state"));
		    	resEmp.setTelephone(empRS.getString("telephone"));
		    	resEmp.setZipCode(empRS.getInt("zipCode"));
		    }
		    empRS.close();
		    empST.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
	
		return resEmp;
	}
	
	public Employee getHighestRevenueEmployee() {
		
		/*
		 * The students code to fetch employee data who generated the highest revenue will be written here
		 * The record is required to be encapsulated as a "Employee" class object
		 */
		
		Employee resEmp = new Employee();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement empRevST = conn.createStatement();
		    ResultSet empRevRS = empRevST.executeQuery("SELECT S.employeeID as highestRevenueEmployeeID, SUM(A.closingBid) as revenue\r\n"
		    		+ "		FROM supervisedby S, Auction A\r\n"
		    		+ "		WHERE S.auctionID = A.auctionID\r\n"
		    		+ "		GROUP BY S.employeeID\r\n"
		    		+ "		ORDER BY SUM(A.closingBid)\r\n"
		    		+ "		LIMIT 1");
		    
		    if (empRevRS.next()) {
				Statement empST = conn.createStatement();
			    ResultSet empRS = empST.executeQuery("SELECT E.*, P.*\r\n"
			    		+ "FROM Employee E, Person P\r\n"
			    		+ "WHERE E.employeeID = '" + empRevRS.getString("highestRevenueEmployeeID") + "'\r\n"
			    		+ "  AND P.SSN = '" + empRevRS.getString("highestRevenueEmployeeID") + "'");
			    if (empRS.next()) {
			    	// from "employee" table
			    	resEmp.setEmployeeID(empRS.getString("employeeID"));
			    	resEmp.setStartDate(empRS.getString("startDate"));
			    	resEmp.setHourlyRate(empRS.getInt("hourlyRate"));
			    	
			    	resEmp.setRevenue(String.valueOf(empRevRS.getInt("revenue")));
			    	
			    	// from "person" table
			    	resEmp.setAddress(empRS.getString("address"));
			    	resEmp.setCity(empRS.getString("city"));
			    	resEmp.setEmail(empRS.getString("email"));
			    	resEmp.setFirstName(empRS.getString("firstName"));
			    	resEmp.setLastName(empRS.getString("lastName"));
			    	resEmp.setState(empRS.getString("state"));
			    	resEmp.setTelephone(empRS.getString("telephone"));
			    	resEmp.setZipCode(empRS.getInt("zipCode"));
			    }
			    empRS.close();
			    empST.close();
		    }
		    empRevRS.close();
		    empRevST.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
	
		return resEmp;
	}

	public String getEmployeeID(String username) {
		/*
		 * The students code to fetch data from the database based on "username" will be written here
		 * username, which is the Employee's email address who's Employee ID has to be fetched, is given as method parameter
		 * The Employee ID is required to be returned as a String
		 */

		String empID = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement empST = conn.createStatement();
		    ResultSet empRS = empST.executeQuery("SELECT E.employeeID\r\n"
		    		+ "FROM Employee E, Person P\r\n"
		    		+ "WHERE P.email='" + username + "' AND P.SSN=E.employeeID");
		    
		    if (empRS.next()) {
		    	// from "employee" table
		    	empID = empRS.getString("employeeID");
		    }
		    empRS.close();
		    empST.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
	
		return empID;
	}

}
