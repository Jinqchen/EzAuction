package dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import model.Employee;
import model.Item;
import model.Post;
import model.Employee;
import model.Item;
import model.Post;
public class PostDao {
	

	
	public List<Item> getSalesReport(Post post) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Query to get sales report for a particular month must be implemented
		 * post, which has details about the month and year for which the sales report is to be generated, is given as method parameter
		 * The month and year are in the format "month-year", e.g. "10-2018" and stored in the expireDate attribute of post object
		 * The month and year can be accessed by getter method, i.e., post.getExpireDate()
		 */
		List<Item> items = new ArrayList<Item>();

		
		

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DatabaseInfo.DB_URL,  DatabaseInfo.USER, DatabaseInfo.PASS);

		    String query = "SELECT I.itemName, I.itemID, I.itemType, I.YearManufactured, COUNT(I.itemID) AS CopiesSold, SUM(B.bidPrice) AS Profits "
		    		+ "FROM Auction A, Item I, BidBy B, Described D , Post P WHERE I.itemID = D.itemID AND A.AuctionID = D.AuctionID AND A.AuctionID=P.auctionID \r\n"
		    		+ "GROUP BY I.ItemID";
		    // create the java statement
		    Statement st = con.createStatement();
		     // execute the query, and get a java resultset
		    ResultSet rs = st.executeQuery(query);
		    while (rs.next()) {
				Item item = new Item();
				item.setName(rs.getString("ItemName"));
				item.setItemID(rs.getInt("ItemID"));
				item.setType(rs.getString("ItemType"));
				item.setNumCopies(rs.getInt("CopiesSold"));
				item.setSoldPrice(rs.getInt("Profits"));
				item.setYearManufactured(rs.getInt("YearManufactured"));
				items.add(item);
			}
		    
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		

		return items;
		
	}
}
