// Ziming Li ziming.li@stonybrook.edu
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Login;

public class LoginDao {
//	static final String DB_URL = "jdbc:mysql://localhost:3306/ezauction";
//	static final String USER = "root";
//	static final String PASS = "lzmlzm";
	/*
	 * This class handles all the database operations related to login functionality
	 */
	
	
	public Login login(String username, String password) {
		/*
		 * Return a Login object with role as "manager", "customerRepresentative" or "customer" if successful login
		 * Else, return null
		 * The role depends on the type of the user, which has to be handled in the database
		 * username, which is the email address of the user, is given as method parameter
		 * password, which is the password of the user, is given as method parameter
		 * Query to verify the username and password and fetch the role of the user, must be implemented
		 */
		
		
		Login res = new Login();
		res.setUsername(username);
		res.setPassword(password);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT P.roles FROM Person P WHERE P.email='" 
			+ username + "' AND P.passwords = '" + password + "'");
			
			if (rs.next()) {
				String role = rs.getString("roles");
				if (role.equals("manager")) {
					res.setRole("manager");
				}
				else if (role.equals("employee")) {
					res.setRole("customerRepresentative");
				}
				else if (role.equals("customer")) {
					res.setRole("customer");
				}
				else {
					res = null;
				}
					
			}
			else {
				res = null;
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
		
		return res;
		
//		Login login = new Login();
//		login.setRole("customer");
//		return login;
		
//		Login login = new Login();
//		login.setRole("customerRepresentative");
//		return login;
		
//		Login login = new Login();
//		login.setRole("manager");
//		return login;
		
		
		
		
		
		
	}
	
	public String addUser(Login login) {
		/*
		 * Query to insert a new record for user login must be implemented
		 * login, which is the "Login" Class object containing username and password for the new user, is given as method parameter
		 * The username and password from login can get accessed using getter methods in the "Login" model
		 * e.g. getUsername() method will return the username encapsulated in login object
		 * Return "success" on successful insertion of a new user
		 * Return "failure" for an unsuccessful database operation
		 */
		if (login.getRole().equals("manager")) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
				Statement st = conn.createStatement();
				int status = st.executeUpdate("UPDATE Person SET passwords='" + login.getPassword() 
					+ "', roles='manager' WHERE email='" + login.getUsername() + "'");
				
				if (status != 0) {
					return "failure";
				}
			    st.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (Exception e2) {
				e2.printStackTrace();
			} 
		}
		else if (login.getRole().equals("customerRepresentative")) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
				Statement st = conn.createStatement();
				int status = st.executeUpdate("UPDATE Person SET passwords='" + login.getPassword() 
					+ "', roles='customerRepresentative' WHERE email='" + login.getUsername() + "'");
				
				if (status != 0) {
					return "failure";
				}
			    st.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (Exception e2) {
				e2.printStackTrace();
			} 
		}
		else if (login.getRole().equals("customer")) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(DatabaseInfo.DB_URL, DatabaseInfo.USER, DatabaseInfo.PASS);
				Statement st = conn.createStatement();
				int status = st.executeUpdate("UPDATE Person SET passwords='" + login.getPassword() 
					+ "', roles='customer' WHERE email='" + login.getUsername() + "'");
				
				if (status != 0) {
					return "failure";
				}
			    st.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (Exception e2) {
				e2.printStackTrace();
			} 
		}
		else {
			return "failure";
		}
		
		return "success";
	}

}
