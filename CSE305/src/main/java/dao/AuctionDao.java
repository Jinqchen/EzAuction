package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Auction;
import model.Bid;
import model.Customer;
import model.Item;

public class AuctionDao {
	
//	static final String DB_URL = "jdbc:mysql://localhost:3306/EzAuction3";
//	static final String USER = "root";
//	static final String PASS = "password";
	
	

	public List<Auction> getAllAuctions() {
		
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Auction" class object and added to the "auctions" ArrayList
		 * Query to get data about all the auctions should be implemented
		 */
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EzAuction",  "root", "lzmlzm");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Auction a, Described d WHERE a.auctionID = d.auctionID");
			while(rs.next()) {
				Auction auction =new Auction();
				auction.setAuctionID(rs.getInt("a.auctionID"));
				auction.setBidIncrement(rs.getInt("a.increment"));
				auction.setMinimumBid(rs.getInt("a.openingBid")); 
				auction.setCopiesSold(rs.getInt("a.openingBid"));
				auction.setItemID(rs.getInt("d.itemID"));
				auction.setClosingBid(rs.getInt("a.closingBid"));
				auction.setCurrentBid(rs.getInt("a.currentBid"));
				auction.setCurrentHighBid(rs.getInt("a.currentHigh"));
				auction.setReserve(rs.getInt("a.reserve"));
				auctions.add(auction);
			}
		}catch(Exception e){
			System.out.println(e);
		}		
		
		return auctions;
		
	}

	public List<Auction> getAuctions(String customerID) {
		
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Auction" class object and added to the "auctions" ArrayList
		 * Query to get data about all the auctions in which a customer participated should be implemented
		 * customerID is the customer's primary key, given as method parameter
		 */
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EzAuction",  "root", "lzmlzm");
			Statement st = con.createStatement();
			String query = "SELECT * FROM Auction a, Described d, Bidby b WHERE a.auctionID = d.auctionID AND a.auctionID = b.auctionID AND b.customerID = \"" + customerID + "\"";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Auction auction =new Auction();
				auction.setAuctionID(rs.getInt("a.auctionID"));
				auction.setBidIncrement(rs.getInt("a.increment"));
				auction.setMinimumBid(rs.getInt("a.openingBid")); 
				auction.setCopiesSold(rs.getInt("a.openingBid"));
				auction.setItemID(rs.getInt("d.itemID"));
				auction.setClosingBid(rs.getInt("a.closingBid"));
				auction.setCurrentBid(rs.getInt("a.currentBid"));
				auction.setCurrentHighBid(rs.getInt("a.currentHigh"));
				auction.setReserve(rs.getInt("a.reserve"));
				auctions.add(auction);
			}

		}catch(Exception e){
			System.out.println(e);
		}		
		
		return auctions;

	}

	public List<Auction> getOpenAuctions(String employeeEmail) {
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Auction" class object and added to the "auctions" ArrayList
		 * Query to get data about all the open auctions monitored by a customer representative should be implemented
		 * employeeEmail is the email ID of the customer representative, which is given as method parameter
		 */
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EzAuction",  "root", "lzmlzm");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Auction a, Described d,SuperviseBy s, Person P, Employee e"
					+ " WHERE a.auctionID = d.auctionID AND a.auctionID = s.auctionID AND s.employeeID = e.employeeID AND p.SSN = e.employeeID AND p.email= " + employeeEmail);
			while(rs.next()) {
				Auction auction =new Auction();
				auction.setAuctionID(rs.getInt("AuctionID"));
				auction.setBidIncrement(rs.getInt("BidIncrement"));
				auction.setMinimumBid(rs.getInt("OpeningBid")); 
				auction.setCopiesSold(rs.getInt("OpeningBid"));
				auction.setItemID(rs.getInt("ItemID"));
				auction.setClosingBid(rs.getInt("ClosingBid"));
				auction.setCurrentBid(rs.getInt("CurrentBid"));
				auction.setCurrentHighBid(rs.getInt("CurrentHighBid"));
				auction.setReserve(rs.getInt("Reserve"));
				auctions.add(auction);
			}
		    rs.close();
		    st.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return auctions;		
	}

	public String recordSale(String auctionID) {
		/*
		 * The students code to update data in the database will be written here
		 * Query to record a sale, indicated by the auction ID, should be implemented
		 * auctionID is the Auction's ID, given as method parameter
		 * The method should return a "success" string if the update is successful, else return "failure"
		 */
		/* Sample data begins */	
		return "success";
		/* Sample data ends */
		
		/*
		 * INSERT INTO Bid VALUES ('23414', '325415', '2008-12-11', '12.10')
		 */
//		try {
//
//			
////			Class.forName("com.mysql.jdbc.Driver");
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EzAuction",  "root", "lzmlzm");;
//			Statement st = con.createStatement();
//			String query = "SELECT * FROM BidBy b WHERE b.acutionID=" + auctionID;
//			ResultSet rs = st.executeQuery(query);
//			PreparedStatement preStatement;
//		    
//		    if(rs.next()) {
//		    	String insertString = "INSERT INTO BidBy (auctionID, customerID, bidTime, bidPrice) VALUE (?,?,?,?)";
//		    	preStatement = con.prepareStatement(insertString);
//    	    
////		    preStatement.setAuctionID(auctionID.getInt("AuctionID"));
//		    preStatement.setInt(1,rs.getInt("auctionID"));
//		    preStatement.setString(2,rs.getString("customerID"));
//		    preStatement.setDate(3, rs.getDate("bidTime"));
//		    preStatement.setInt(4,rs.getInt("bidPrice"));
//			if(!preStatement.execute()) {
//				return "failure";
//			}
//
//		    }
//    	    
////    	      con.setAutoCommit(false);
////    	      for (Map.Entry<String, Integer> e : salesForWeek.entrySet()) {
////    	    	insertString.setInt(1, e.getValue().intValue());
////    	    	insertString.setString(2, e.getKey());
////    	    	insertString.executeUpdate();
////
////    	        updateTotal.setInt(1, e.getValue().intValue());
////    	        updateTotal.setString(2, e.getKey());
////    	        updateTotal.executeUpdate();
//    	        con.commit();
////    	      }
//    	    } catch (SQLException e) {
//    	    	System.out.println(e);
//    	    }
//		return "success";
		
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EzAuction",  "root", "lzmlzm");;
//			Statement st = con.createStatement();

		
		    
	}

	public List getAuctionData(String auctionID, String itemID) {
		
//		List output = new ArrayList();
//		Item item = new Item();
//		Bid bid = new Bid();
//		Auction auction = new Auction();
//		Customer customer = new Customer();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The item details are required to be encapsulated as a "Item" class object
		 * The bid details are required to be encapsulated as a "Bid" class object
		 * The auction details are required to be encapsulated as a "Auction" class object
		 * The customer details are required to be encapsulated as a "Customer" class object
		 * Query to get data about auction indicated by auctionID and itemID should be implemented
		 * auctionID is the Auction's ID, given as method parameter
		 * itemID is the Item's ID, given as method parameter
		 * The customer details must include details about the current winner of the auction
		 * The bid details must include details about the current highest bid
		 * The item details must include details about the item, indicated by itemID
		 * The auction details must include details about the item, indicated by auctionID
		 * All the objects must be added in the "output" list and returned
		 */
		
//		/*Sample data begins*/
//		for (int i = 0; i < 4; i++) {
//			item.setItemID(123);
//			item.setDescription("sample description");
//			item.setType("BOOK");
//			item.setName("Sample Book");
//			
//			bid.setCustomerID("123-12-1234");
//			bid.setBidPrice(120);
//			
//			customer.setCustomerID("123-12-1234");
//			customer.setFirstName("Shiyong");
//			customer.setLastName("Lu");
//			
//			auction.setMinimumBid(100);
//			auction.setBidIncrement(10);
//			auction.setCurrentBid(110);
//			auction.setCurrentHighBid(115);
//			auction.setAuctionID(Integer.parseInt(auctionID));
//		}
//		/*Sample data ends*/
		
		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Bid> bids = new ArrayList<Bid>();
		List<Auction> auctions = new ArrayList<Auction>();
		List<Customer> customers = new ArrayList<Customer>();
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EzAuction",  "root", "lzmlzm");
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery("select b.customerID, b.bidPrice, a.auctionID,i.itemId, i.itemDescription, i.itemName, i.itemType, pr.firstName,pr.lastName,a.openingBid,a.increment,a.currentHigh\r\n"
					+ "	from Item i, BidBy b, Customer c, Person pr , Auction a, Described d, Post p \r\n"
					+ "	where  i.itemId=d.itemId and a.auctionID=d.auctionID and p.auctionID=a.auctionID\r\n"
					+ "    AND a.auctionID =b.auctionID and pr.SSN = c.customerID and b.customerID=c.customerID and a.auctionID"+ auctionID);
			
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("i.itemId"));
				item.setDescription(rs.getString("i.itemDescription"));
				item.setName(rs.getString("i.itemName"));
				item.setType(rs.getString("i.itemType"));
				items.add(item);
				
				Bid bid = new Bid();
				bid.setCustomerID(rs.getString("b.customerID"));
				bid.setBidPrice(rs.getInt("b.bidPrice"));
				bids.add(bid);
				
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("c.customerID"));
				customer.setFirstName(rs.getString("pr.firstName"));
				customer.setLastName(rs.getString("pr.lastName"));
				customers.add(customer);
				
				Auction auction = new Auction();
				auction.setMinimumBid(rs.getInt("a.openingBid"));
				auction.setBidIncrement(rs.getInt("a.increment"));
				auction.setCurrentBid(rs.getInt("a.currentBid"));
				auction.setCurrentHighBid(rs.getInt("a.currentHigh"));
				auction.setAuctionID(rs.getInt("a.auctionID"));
				auctions.add(auction);
			
			}
		} catch(Exception e) {
			System.out.println(e);
		}

			output.add(items);
			output.add(bids);
			output.add(auctions);
			output.add(customers);
			
			return output;

		}

	
}
