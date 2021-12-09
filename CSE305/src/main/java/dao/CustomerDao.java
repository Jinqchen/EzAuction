// Ziming Li ziming.li@stonybrook.edu
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Customer;

import java.util.stream.IntStream;

public class CustomerDao {
	/*
	 * This class handles all the database operations related to the customer table
	 */
	
	public static void main(String[] args) {
		CustomerDao ex = new CustomerDao();
		
		// test getHighestRevenueCustomer()
//		Customer resCus = ex.getHighestRevenueCustomer();
//		System.out.println(resCus.toString());
		
		// test getCustomerMailingList()
//		List<Customer> res = ex.getCustomerMailingList();
//		for (Customer cus : res) {
//			System.out.println("SSN: " + cus.getCustomerID() + ", firstName: " + cus.getFirstName() 
//			+ ", lastName: " + cus.getLastName() + ", address: " + cus.getAddress() + ", city: " 
//			+ cus.getCity() + "state: " + cus.getState() + "zipCode: " + cus.getZipCode());
//		}
		
		// test getCustomer(String customerID)
//		Customer resCus = ex.getCustomer("111-11-1112");
//		System.out.println(resCus.toString());
		
		// test getCustomerID(String username)
//		String cusID = ex.getCustomerID("shiyong@cs.sunysb.edu");
//		System.out.println(cusID);
		
		// public List<Customer> getSellers()
//		List<Customer> res = ex.getSellers();
//		for (Customer cus : res) {
//			System.out.println("SSN: " + cus.getCustomerID() + ", rating: " + cus.getRating() + ", firstName: " + cus.getFirstName() 
//			+ ", lastName: " + cus.getLastName() + ", telephone: " + cus.getTelephone() + ", email: " 
//			+ cus.getEmail());
//		}
		
		// test public String addCustomer(Customer customer)
		//  and public String deleteCustomer(String customerID)
		//  and public String editCustomer(Customer customer)
		//  at the same time
		Customer testCus = new Customer();
		testCus.setCustomerID("999-99-9999");
		testCus.setFirstName("Ziming");
		testCus.setLastName("Li");
		testCus.setRating(5);
		testCus.setCreditCard("8888-8888-8888-8888");
		testCus.setAddress("21 Happy Street");
		testCus.setCity("Stony Brook");
		testCus.setZipCode(11790);
		testCus.setEmail("ziming.li@stonybrook.edu");
		testCus.setTelephone("888-888-8888");
		
		// add
//		String addCusRes = ex.addCustomer(testCus);
//		System.out.println("Add Test,    Expected: Success,  result: " + addCusRes);
		
//		// edit
//		String editCusRes = ex.editCustomer(testCus);
//		System.out.println("edit original tuple Test,    Expected: Success,  result: " + editCusRes);
		
//		testCus.setRating(4);
//		String editCusRes = ex.editCustomer(testCus);
//		System.out.println("edit change Customer Table only Test,     tuple Test,    Expected: Success,  result: " + editCusRes);
//		
//		testCus.setCity("New York");
//		String editCusRes = ex.editCustomer(testCus);
//		System.out.println("edit change Person Table only Test,     tuple Test,    Expected: Success,  result: " + editCusRes);
//	
//		testCus.setRating(5);
//		testCus.setCity("Stony Brook");
//		String editCusRes = ex.editCustomer(testCus);
//		System.out.println("edit change both tables Test,     tuple Test,    Expected: Success,  result: " + editCusRes);
//	
//		// delete
		String delCusRes = ex.deleteCustomer("999-99-9999");
		System.out.println("Delete Test,    Expected: Success,  result: " + delCusRes);
	
	}
	
	/**
	 * @param String searchKeyword
	 * @return ArrayList<Customer> object
	 */
	public List<Customer> getCustomers(String searchKeyword) {
		/*
		 * This method fetches one or more customers based on the searchKeyword and returns it as an ArrayList
		 */
		
		
		/*
		 * The students code to fetch data from the database based on searchKeyword will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */
		
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
		    ResultSet cusRS = cusST.executeQuery("SELECT * FROM Customer");
		    // Extract data from result set
		    while (cusRS.next()) {
		    	Customer newCus = new Customer();
		    	
		    	// from "customer" table
		    	newCus.setCustomerID(cusRS.getString("customerID"));
		    	newCus.setCreditCard(cusRS.getString("creditCardNumber"));
		    	newCus.setRating(cusRS.getInt("rating"));
		    	
		    	// we have extra attributes "itemsPurchased" and "itemsSold"
//		        System.out.print(", itemsPurchased: " + cusRS.getInt("itemsPurchased"));
//		        System.out.println(", itemsSold: " + cusRS.getInt("itemsSold"));
		        
		    	// from "person" table
		        Statement matchPersonST = conn.createStatement();
			    ResultSet matchPersonRS = matchPersonST.executeQuery("SELECT P.* FROM Person P WHERE P.SSN = '" + cusRS.getString("customerID") + "'");
		    	if (matchPersonRS.next()) {
		    		newCus.setAddress(matchPersonRS.getString("address"));
		    		newCus.setCity(matchPersonRS.getString("city"));
		    		newCus.setEmail(matchPersonRS.getString("email"));
		    		newCus.setFirstName(matchPersonRS.getString("firstName"));
		    		newCus.setLastName(matchPersonRS.getString("lastName"));
		    		newCus.setState(matchPersonRS.getString("state"));
		    		newCus.setTelephone(matchPersonRS.getString("telephone"));
		    		newCus.setZipCode(matchPersonRS.getInt("zipCode"));
		    	}
		    	matchPersonRS.close();
		    	matchPersonST.close();
		    	
		        customers.add(newCus);
		     }
		    cusRS.close();
		    cusST.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
		
		return customers;
	}


	public Customer getHighestRevenueCustomer() {
		/*
		 * This method fetches the customer who generated the highest total revenue and returns it
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */


		/*Sample data begins*/
		Customer resCus = new Customer();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
		    ResultSet cusRS = cusST.executeQuery("SELECT C.*, pe.*\r\n"
		    		+ "FROM Post p, Auction a, Person pe, Customer c\r\n"
		    		+ "WHERE p.auctionID=a.auctionID and pe.SSN = c.customerID and p.customerID = c.customerID\r\n"
		    		+ "GROUP BY p.customerID\r\n"
		    		+ "order by sum(a.closingbid) desc\r\n"
		    		+ "LIMIT 1;");
		    
		    if (cusRS.next()) {
		    	// from "customer" table
		    	resCus.setCustomerID(cusRS.getString("customerID"));
		    	resCus.setCreditCard(cusRS.getString("creditCardNumber"));
		    	resCus.setRating(cusRS.getInt("rating"));
		    	
		    	// from "person" table
		    	resCus.setAddress(cusRS.getString("address"));
	    		resCus.setCity(cusRS.getString("city"));
	    		resCus.setEmail(cusRS.getString("email"));
	    		resCus.setFirstName(cusRS.getString("firstName"));
	    		resCus.setLastName(cusRS.getString("lastName"));
	    		resCus.setState(cusRS.getString("state"));
	    		resCus.setTelephone(cusRS.getString("telephone"));
	    		resCus.setZipCode(cusRS.getInt("zipCode"));
		    }
		    cusRS.close();
		    cusST.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
	
		return resCus;
	}

	public List<Customer> getCustomerMailingList() {

		/*
		 * This method fetches the all customer mailing details and returns it
		 * The students code to fetch data from the database will be written here
		 * Each customer record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */
		
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement st = conn.createStatement();
		    ResultSet rs = st.executeQuery("SELECT p.SSN, p.firstName, p.lastName, p.address, p.city, p.state, p.zipCode, p.email\r\n"
		    		+ "		FROM Customer c, Person p\r\n"
		    		+ "		WHERE c.customerID = p.SSN;");
		    // Extract data from result set
		    while (rs.next()) {
		    	Customer newCus = new Customer();
		    	
		    	// from "person" table
		    	newCus.setCustomerID(rs.getString("SSN"));
		    	newCus.setFirstName(rs.getString("firstName"));
	    		newCus.setLastName(rs.getString("lastName"));
	    		newCus.setAddress(rs.getString("address"));
	    		newCus.setCity(rs.getString("city"));
	    		newCus.setState(rs.getString("state"));
	    		newCus.setZipCode(rs.getInt("zipCode"));
	    		newCus.setEmail(rs.getString("email"));
//	    		newCus.setTelephone(rs.getString("telephone"));
	    		
		        customers.add(newCus);
		     }
		    rs.close();
		    st.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
		
		return customers;
	}

	public Customer getCustomer(String customerID) {

		/*
		 * This method fetches the customer details and returns it
		 * customerID, which is the Customer's ID who's details have to be fetched, is given as method parameter
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
		
		Customer resCus = new Customer();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
		    ResultSet cusRS = cusST.executeQuery("SELECT c.*, p.*\r\n"
		    		+ "FROM customer c, person p\r\n"
		    		+ "WHERE c.customerID = p.SSN\r\n"
		    		+ "	AND c.customerID = '" + customerID  + "'");
		    
		    if (cusRS.next()) {
		    	// from "customer" table
		    	resCus.setCustomerID(cusRS.getString("customerID"));
		    	resCus.setCreditCard(cusRS.getString("creditCardNumber"));
		    	resCus.setRating(cusRS.getInt("rating"));
		    	
		    	// from "person" table
		    	resCus.setAddress(cusRS.getString("address"));
	    		resCus.setCity(cusRS.getString("city"));
	    		resCus.setEmail(cusRS.getString("email"));
	    		resCus.setFirstName(cusRS.getString("firstName"));
	    		resCus.setLastName(cusRS.getString("lastName"));
	    		resCus.setState(cusRS.getString("state"));
	    		resCus.setTelephone(cusRS.getString("telephone"));
	    		resCus.setZipCode(cusRS.getInt("zipCode"));
		    }
		    cusRS.close();
		    cusST.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
	
		return resCus;
	}
	
	public String deleteCustomer(String customerID) {

		/*
		 * This method deletes a customer returns "success" string on success, else returns "failure"
		 * The students code to delete the data from the database will be written here
		 * customerID, which is the Customer's ID who's details have to be deleted, is given as method parameter
		 */

		boolean success = true;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
			
			// check does employee table exists the customerID we want to delete, 
			// if so: only delete from only customer table, 
			// if no: delete from both customer and person tables
			ResultSet rs = cusST.executeQuery("SELECT * FROM employee\r\n"
					+ "WHERE employeeID='" + customerID + "'");
			
			
			
			if (rs.next()) { // if exists
				// save the row in person table, 
				// then delete it, 
				// so the row in customer table can be deleted, 
				// lastly store the row that we saved in person table
				rs = cusST.executeQuery("SELECT * FROM person\r\n"
						+ "WHERE SSN = '" + customerID + "'");
				if (rs.next()) {
					int status = cusST.executeUpdate("DELETE FROM person\r\n"
			    			+ "WHERE SSN = '" + customerID + "'");
					
					if (status == 0) {
				    	success = false;
				    }
					else {
						status = cusST.executeUpdate("DELETE FROM customer\r\n"
					    		+ "WHERE customerID = '" + customerID + "'");
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
				int status = cusST.executeUpdate("DELETE FROM customer\r\n"
			    		+ "WHERE customerID = '" + customerID + "'");
		    	if (status == 0)
		    		success = false;
		    	else {
		    		status = cusST.executeUpdate("DELETE FROM person\r\n"
			    			+ "WHERE SSN = '" + customerID + "'");
					
					if (status == 0) {
				    	success = false;
				    }
		    	}
		    	
				
			}
			
		    cusST.close();
		    rs.close();
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


	public String getCustomerID(String username) {
		/*
		 * This method returns the Customer's ID based on the provided email address
		 * The students code to fetch data from the database will be written here
		 * username, which is the email address of the customer, who's ID has to be returned, is given as method parameter
		 * The Customer's ID is required to be returned as a String
		 */
		String cusID = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
		    ResultSet cusRS = cusST.executeQuery("SELECT c.customerID\r\n"
		    		+ "FROM Customer c, Person p\r\n"
		    		+ "WHERE p.email='" + username + "' AND p.SSN=c.customerID;");
		    
		    if (cusRS.next()) {
		    	// from "customer" table
		    	cusID = cusRS.getString("customerID");
		    }
		    cusRS.close();
		    cusST.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
	
		return cusID;
	}


	public List<Customer> getSellers() {
		
		/*
		 * This method fetches the all seller details and returns it
		 * The students code to fetch data from the database will be written here
		 * The seller (which is a customer) record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		List<Customer> sellers = new ArrayList<Customer>();
		
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement st = conn.createStatement();
		    ResultSet rs = st.executeQuery("SELECT c.customerID, c.rating, pe.firstName, pe.lastName, pe.telephone, pe.email\r\n"
		    		+ "FROM Customer c, Person pe\r\n"
		    		+ "WHERE c.customerID in \r\n"
		    		+ "		(SELECT DISTINCT p.customerID\r\n"
		    		+ "		FROM Post p)\r\n"
		    		+ "	AND\r\n"
		    		+ "		c.customerID = pe.SSN\r\n"
		    		+ "ORDER BY c.rating desc;");
		    // Extract data from result set
		    while (rs.next()) {
		    	Customer newCus = new Customer();
		    	
		    	// from "customer" table
		    	newCus.setCustomerID(rs.getString("customerID"));
		    	newCus.setRating(rs.getInt("rating"));
		    	
		    	// from "person" table
		    	newCus.setFirstName(rs.getString("firstName"));
	    		newCus.setLastName(rs.getString("lastName"));
	    		newCus.setTelephone(rs.getString("telephone"));
	    		newCus.setEmail(rs.getString("email"));
	    		
		        sellers.add(newCus);
		     }
		    rs.close();
		    st.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e2) {
			e2.printStackTrace();
		} 
		
		return sellers;

	}


	public String addCustomer(Customer customer) {

		/*
		 * All the values of the add customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the customer details and return "success" or "failure" based on result of the database insertion.
		 */
		
		boolean success = true;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
			ResultSet rs = cusST.executeQuery("SELECT * FROM Person WHERE SSN = '" + customer.getCustomerID() + "'");
			if (!rs.next()) {
				int status = cusST.executeUpdate("INSERT INTO Person(SSN, firstName, lastName, email, address, city, state, zipCode, telephone, passwords, roles)\r\n"
			    		+ "VALUES ('" + customer.getCustomerID() + "', '" + customer.getFirstName() + "', '" + customer.getLastName() 
			    		+ "', '" + customer.getEmail() + "', '" + customer.getAddress() + "', '" + customer.getCity() 
			    		+ "', '" + customer.getState() + "', " + customer.getZipCode() + ", '" + customer.getTelephone() 
			    		+ "', passwords='00000000', roles='customer')");
				
			}
			int status = cusST.executeUpdate("INSERT INTO Customer(customerID, creditCardNumber, rating)\r\n"
	    			+ "VALUES('" + customer.getCustomerID() + "', '" + customer.getCreditCard() + "', " 
	    			+ customer.getRating() + ")");
		    
	    	
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

	public String editCustomer(Customer customer) {
		/*
		 * All the values of the edit customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		
		boolean success = true;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement cusST = conn.createStatement();
			int status = cusST.executeUpdate("UPDATE Person\r\n"
	    			+ "SET firstName='" + customer.getFirstName() + "', lastName='" + customer.getLastName() 
	    			+ "', email='" + customer.getEmail() + "', address='" + customer.getAddress() 
	    			+ "', city='" + customer.getCity() + "', state='" + customer.getState() + "', zipCode= " + customer.getZipCode() 
	    			+ ", telephone='" + customer.getTelephone() + "'\r\n"
	    			+ "WHERE SSN='" + customer.getCustomerID() + "'");
		    
		    
		    // if 0 rows be affected
		    if (status == 0) {
		    	success = false;
		    }
		    else {
		    	status = cusST.executeUpdate("UPDATE Customer\r\n"
			    		+ "SET creditCardNumber='" + customer.getCreditCard() + "', rating= " + customer.getRating() + "\r\n"
			    		+ "WHERE customerID='" + customer.getCustomerID() + "'");
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

}
