package com.trading.repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.trading.models.Block;
import com.trading.models.EquityResponse;
import com.trading.models.Order;

public class EMSRepo {

	private Block block;
	private Order order;
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/equity";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "rootroot";
	
	public boolean orderUpdate(Order newOrder, EquityResponse eqResponse) {
		
		return true;
	}
	
	public boolean blockUpdate(Block block, EquityResponse eqResponse) {
		
		return true;
	}
	
	public List<Order> retrieveBlock(String blockID) {
		List<Order> blockOrders = new ArrayList<Order>();
		
		Connection conn = null;
		
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(
					"select * from equity.order a \r\n" + 
					"join \r\n" + 
					"equity.block_order b\r\n" + 
					"on a.OrderID = b.OrderID\r\n" + 
					"join \r\n" + 
					"equity.block_details c\r\n" + 
					"on b.BlockID = c.Block_ID\r\n" + 
					"where c.Block_ID = ?");
			stmt.setString(1, blockID);
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			
			/*
			String orderId, String symbol, String side, String type, int totalQuantity, 
			String stockName, String manager, String porfolioId, int allocatedQuantity,
			float actualPrice, Date timeCreated,float limitPrice, float stopPrice
			*/
			while(rs.next()) {
				String orderId = rs.getString("OrderID");
				String symbol = rs.getString("Symbol");
				String side = rs.getString("Side");
				String type = rs.getString("Type");
				int totalQuantity = rs.getInt("Total_Quantity");
				String stockName = rs.getString("Stock_Name");
				float limitPrice = rs.getFloat("Limit_Price");
				float stopPrice = rs.getFloat("Stop_Price");
				String manager = rs.getString("Manager");
				String porfolioId = rs.getString("Portfolio_ID");
				int openQuantity = rs.getInt("Open_Quantity");
				int allocatedQuantity = rs.getInt("Allocated_Quantity");
				String status = rs.getString("Status");
				float actualPrice = rs.getFloat("Actual_Price");
				Date timeCreated = rs.getDate("Time_Created");
				Date timeExecuted = rs.getDate("Time_executed");
				Order order = new Order(orderId, symbol, side, type, totalQuantity, stockName, manager, porfolioId,
										allocatedQuantity, actualPrice, timeCreated, limitPrice, stopPrice);
				order.setTimeExecuted(timeExecuted);
				blockOrders.add(order);
			}
			rs.close();
			
			
			System.out.println("Connection closed...");

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		
		return blockOrders;
	}
}
