package dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import model.Auction;
import model.Bid;
import model.Employee;
import model.Item;

public class ItemDao {
	final String DB="jdbc:mysql://localhost:3306/ezauction";
	final String URL="root";
	final String Password="FYPEX123456";
	
	public List<Item> getItems() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of all the items has to be implemented
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" List
		 */

		List<Item> items = new ArrayList<Item>();
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB,URL,Password);
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Item");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("itemID"));
				item.setType(rs.getString("ItemType"));
				item.setName(rs.getString("ItemName"));
				item.setNumCopies(rs.getInt("numCopies"));
				item.setYearManufactured(rs.getInt("yearManufactured"));
				item.setDescription(rs.getString("itemDescription"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;

	}
	
	public List<Item> getBestsellerItems() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of the bestseller items has to be implemented
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" List
		 */

List<Item> items = new ArrayList<Item>();
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB,URL,Password);
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.itemID, I.itemName, I.itemType, COUNT(D.ItemID) as Sold ,itemDescription "
					+ "FROM Auction A, Item I, Described D"
					+ " WHERE I.itemID = D.itemID AND D.auctionID=A.auctionID GROUP BY D.itemID ORDER BY COUNT(itemID) ");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setName(rs.getString("ItemName"));
				item.setType(rs.getString("ItemType"));
				item.setNumCopies(rs.getInt("Sold"));
				item.setDescription(rs.getString("ItemDescription"));
				
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;
	}

	public List<Item> getSummaryListing(String searchKeyword) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of summary listing of revenue generated by a particular item or item type must be implemented
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList
		 * Store the revenue generated by an item in the soldPrice attribute, using setSoldPrice method of each "item" object
		 */

		List<Item> items = new ArrayList<Item>();
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB,URL,Password);
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.itemID, I.itemName, I.itemType, I.itemDescription, MAX(B.bidPrice) as price FROM Auction A, Item I, BidBy B,Described D \r\n"
					+ "WHERE D.itemID = I.itemID AND D.auctionID=A.auctionID AND (I.itemName LIKE '%"+searchKeyword+"%' OR I.itemType LIKE '%"+searchKeyword+"%' )GROUP BY I.itemID");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("itemID"));

				item.setName(rs.getString("itemName"));
				item.setType(rs.getString("itemType"));

				item.setDescription(rs.getString("itemDescription"));
				item.setSoldPrice(rs.getInt("price"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;

	}

	public List<Item> getItemSuggestions(String customerID) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch item suggestions for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom the item suggestions are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList
		 */

		List<Item> items = new ArrayList<Item>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB,URL,Password);
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT itemId,itemName,itemType,numCopies,itemDescription\r\n"
					+ "FROM Item \r\n"
					+ "WHERE itemType=(SELECT  i.itemType\r\n"
					+ "FROM  Auction a, Item i, Described d, BidBy b\r\n"
					+ "WHERE d.auctionID = a.auctionID and d.itemID = i.itemId  and b.auctionID=a.auctionID and b.customerID='"+customerID
					+ "' GROUP BY i.itemType \r\n"
					+ "ORDER BY count(bidPrice)  DESC LIMIT 1 )");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setName(rs.getString("ItemName"));
				item.setType(rs.getString("ItemType"));
				item.setNumCopies(rs.getInt("numCopies"));

				item.setDescription(rs.getString("ItemDescription"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;

	}

	public List getItemsBySeller(String sellerID) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch items sold by a given seller, indicated by sellerID, must be implemented
		 * sellerID, which is the Sellers's ID who's items are fetched, is given as method parameter
		 * The bid and auction details of each of the items should also be fetched
		 * The bid details must have the highest current bid for the item
		 * The auction details must have the details about the auction in which the item is sold
		 * Each item record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Each bid record is required to be encapsulated as a "Bid" class object and added to the "bids" List
		 * Each auction record is required to be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items, bids and auctions Lists have to be added to the "output" List and returned
		 */

		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Bid> bids = new ArrayList<Bid>();
		List<Auction> auctions = new ArrayList<Auction>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB,URL,Password);
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select p.customerID as sellerID, i.itemId,i.itemName, a.*,B.* "
					+ " from item i ,auction a,Described d,post p ,BidBy B "
					+ " where  i.itemId=d.itemId and a.auctionID=d.auctionID and p.auctionID=a.auctionID AND a.auctionID =B.auctionID "
					+ " and p.customerID='"+sellerID+"';");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("itemId"));
				item.setName(rs.getString("itemName"));
				item.setType(rs.getString("itemType"));
				items.add(item);
				Bid bid = new Bid();
				bid.setCustomerID(rs.getString("customerID"));
				bid.setBidPrice(rs.getInt("bidPrice"));
				bids.add(bid);
				Auction auction = new Auction();
				auction.setMinimumBid(rs.getInt("reserve"));
				auction.setBidIncrement(rs.getInt("increment"));
				auction.setAuctionID(rs.getInt("auctionID"));
				auctions.add(auction);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		
		
		output.add(items);
		output.add(bids);
		output.add(auctions);
		
		return output;
	}

	public List<Item> getItemTypes() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList
		 * A query to fetch the unique item types has to be implemented
		 * Each item type is to be added to the "item" object using setType method
		 */
		
		List<Item> items = new ArrayList<Item>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB,URL,Password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT DISTINCT itemType FROM Item");
			while (rs.next()) {
				Item item = new Item();
				item.setType(rs.getString("itemType"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;
	}

	public List getItemsByName(String itemName) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The itemName, which is the item's name on which the query has to be implemented, is given as method parameter
		 * Query to fetch items containing itemName in their name has to be implemented
		 * Each item's corresponding auction data also has to be fetched
		 * Each item record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Each auction record is required to be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items and auctions Lists are to be added to the "output" List and returned
		 */
		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Auction> auctions = new ArrayList<Auction>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB,URL,Password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.itemID, I.itemDescription,I.itemType,I.itemName, A.auctionID,A.reserve,A.increment,A.closingBid FROM Item I, Auction A ,Described D,Post P "
					+ " WHERE I.itemID = D.itemID and D.auctionID=A.auctionID AND P.auctionID=A.auctionID AND I.ItemName LIKE '%"+itemName+"%'");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("itemID"));
				item.setDescription(rs.getString("itemDescription"));
				item.setType(rs.getString("itemType"));
				item.setName(rs.getString("itemName"));
				items.add(item);
				
				Auction auction = new Auction();
				auction.setAuctionID(rs.getInt("auctionID"));
				auction.setMinimumBid(rs.getInt("reserve"));
				auction.setBidIncrement(rs.getInt("increment"));
				auction.setClosingBid(rs.getInt("closingBid"));
				auctions.add(auction);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		output.add(items);
		output.add(auctions);
		
		return output;
	}
	

	public List getItemsByType(String itemType) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The itemType, which is the item's type on which the query has to be implemented, is given as method parameter
		 * Query to fetch items containing itemType as their type has to be implemented
		 * Each item's corresponding auction data also has to be fetched
		 * Each item record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Each auction record is required to be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items and auctions Lists are to be added to the "output" List and returned
		 */

		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Auction> auctions = new ArrayList<Auction>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB,URL,Password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.itemID, I.itemDescription,I.itemType,I.itemName, A.auctionID,A.reserve,A.increment,A.closingBid  FROM Item I, Auction A,Described D WHERE I.ItemType LIKE '%"+itemType+"%' AND D.ItemID=I.ItemID AND A.auctionID=D.auctionID");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("itemID"));
				item.setDescription(rs.getString("itemDescription"));
				item.setType(rs.getString("itemType"));
				item.setName(rs.getString("itemName"));
				items.add(item);
				
				Auction auction = new Auction();
				auction.setAuctionID(rs.getInt("auctionID"));
				auction.setMinimumBid(rs.getInt("reserve"));
				auction.setBidIncrement(rs.getInt("increment"));
				auction.setClosingBid(rs.getInt("closingBid"));
				auctions.add(auction);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		output.add(items);
		output.add(auctions);
		
		return output;
	}

	public List<Item> getBestsellersForCustomer(String customerID) {

		/*
		 * The students code to fetch data from the database will be written here.
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList.
		 * Query to get the Best-seller list of items for a particular customer, indicated by the customerID, has to be implemented
		 * The customerID, which is the customer's ID for whom the Best-seller items have to be fetched, is given as method parameter
		 */

		List<Item> items = new ArrayList<Item>();
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB,URL,Password);
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.itemID, I.itemName, I.itemType, COUNT(D.ItemID) as Sold ,itemDescription "
					+ "FROM Auction A, Item I, Described D"
					+ " WHERE I.itemID = D.itemID AND D.auctionID=A.auctionID GROUP BY D.itemID ORDER BY COUNT(itemID) ");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setName(rs.getString("ItemName"));
				item.setType(rs.getString("ItemType"));
				item.setNumCopies(rs.getInt("Sold"));
				item.setDescription(rs.getString("ItemDescription"));
				
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;

	}

}
