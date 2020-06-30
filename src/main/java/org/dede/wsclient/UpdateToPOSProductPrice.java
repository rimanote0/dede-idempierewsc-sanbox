package org.dede.wsclient;

//import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateToPOSProductPrice {
	public UpdateToPOSProductPrice() {
		//connect to local database
		//ingat buatkan properties file
		String jdbcUrl = "jdbc:postgresql://localhost:5432/unicenta434"; 
	    String username = "postgres";
	    String password = "postgres";
	    
	    //Create cursor on POS Product
	    try (Connection con = DriverManager.getConnection(jdbcUrl, username, password);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * from PRODUCTS")) {
	    	//Loop Cursor
            while (rs.next()) {
                System.out.println("PRODUCTS:" + rs.getString(1)+ " " + rs.getString(2)+ 
                		" " + rs.getString(3)+ " " + rs.getString(4)+ " " + 
                		rs.getString(5) + " " + rs.getString(6));
                
                //select Price from temp_product_price 
                String SQL = "SELECT pricelist, pricestd, pricelimit "+
                		"FROM temp_m_productprice "+
                		"WHERE m_product_id = ?";
                
                try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                	//i dont know it's need or not
                
                	String sMProductID = rs.getString(1);
                	pstmt.setDouble(1, Double.valueOf(sMProductID));
                	ResultSet rsQ = pstmt.executeQuery();
                	
                	while (rsQ.next()) {
                		System.out.println(" temp_m_productprice :" + rsQ.getString(1)+ " " + rsQ.getString(2)+ 
                        		" " + rsQ.getString(3));
                		System.out.println("	Update disini:");
                		
                		String SQLUpdate = "UPDATE PRODUCTS SET PRICEBUY = ?, PRICESELL = ? "
                                + " WHERE ID=? ";
                		
//                		con.prepareStatement(SQLUpdate);
                		
                		Float BuyPrice, SellPrice;
                		BuyPrice = Float.valueOf(rsQ.getString(1));
                		SellPrice = Float.valueOf(rsQ.getString(1));
                		
                        try (PreparedStatement pstmtUpdate = con.prepareStatement(SQLUpdate)) {
                          con.setAutoCommit(false);
                          pstmtUpdate.setFloat(1, BuyPrice);
                          pstmtUpdate.setFloat(2, SellPrice);
                          pstmtUpdate.setString(3, sMProductID);                          
                          
                          pstmtUpdate.executeUpdate();
                          
                          con.commit();
                        }catch (SQLException ex) {

                            if (con != null) {
                                try {
                                    con.rollback();
                                } catch (SQLException ex1) {
                                    Logger lgr = Logger.getLogger(UpdateToPOSProductPrice.class.getName());
                                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                                }
                            }

                            Logger lgr = Logger.getLogger(UpdateToPOSProductPrice.class.getName());
                            lgr.log(Level.SEVERE, ex.getMessage(), ex);                		
                        }
                        
                	}

                }
                

            }

        } catch (SQLException ex) {
        
            Logger lgr = Logger.getLogger(UpdateToPOSProductPrice.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
	    
	}
	
	public static void main(String args[]) {
		UpdateToPOSProductPrice run = new UpdateToPOSProductPrice();
	}
}
