package org.dede.wsclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

//Author : Ajus on 26 June 2020
public class UpdateToPOSStockCurrent {
	public UpdateToPOSStockCurrent() {
		//connect to local database
		String jdbcUrl = "jdbc:postgresql://localhost:5432/unicenta434"; 
	    String username = "postgres";
	    String password = "postgres";

	    //Create cursor on POS Product
	    try (Connection con = DriverManager.getConnection(jdbcUrl, username, password);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * from STOCKCURRENT")) {

	    	//Loop Cursor
            while (rs.next()) {
                System.out.println("STOCKCURRENT:" + rs.getString(1)+ " " + rs.getString(2)+ 
                		" " + rs.getString(3)+ " " + rs.getString(4));
                		
                //select Price from temp_product_price 
                String SQL = "SELECT m_locator_id, m_product_id, m_attributesetinstance_id, qtyonhand "+
                		"FROM temp_m_storageonhand "+
                		"WHERE m_product_id = ?";
                
                try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                	//i dont know it's need or not
                
                	String sM_Product_ID = rs.getString(2);
//                	pstmt.setInt(1, Integer.valueOf(sM_Product_ID));
                	pstmt.setFloat(1, Float.valueOf(sM_Product_ID));
                	ResultSet rsQ = pstmt.executeQuery();
                	
                	while (rsQ.next()) {
                		System.out.println(" temp_m_storageonhand :" + rsQ.getString(1)+ " " + rsQ.getFloat(2)+ 
                        		" " + rsQ.getString(3) + " " + rsQ.getString(4));
                		System.out.println("	Update disini:");
                		
                		String SQLUpdate = "UPDATE STOCKCURRENT SET LOCATION = ?, PRODUCT = ?, ATTRIBUTESETINSTANCE_ID = ?, UNITS = ?"
                                + " WHERE PRODUCT = ? ";
                		
//                		con.prepareStatement(SQLUpdate);
                		
                		String Location, Product, AttributeSetInstance_ID;
                		Float Units;
                		Location = String.valueOf(rsQ.getInt(1));
                		
                		//Harusnya tidak pakai decimal point
                		Product = String.valueOf(rsQ.getFloat(2));	//coba di dcek lagi
                		
                		AttributeSetInstance_ID = String.valueOf(rsQ.getInt(3));
                		Units = Float.valueOf(rsQ.getInt(4));
                		
                        try (PreparedStatement pstmtUpdate = con.prepareStatement(SQLUpdate)) {
                          con.setAutoCommit(false);
                          pstmtUpdate.setString(1, Location);
//                          pstmtUpdate.setString(2, Product);
                          pstmtUpdate.setFloat(2, Float.valueOf(Product));
                          pstmtUpdate.setString(3, AttributeSetInstance_ID);
                          pstmtUpdate.setFloat(4, Units);
                          pstmtUpdate.setString(5, sM_Product_ID);
                          
                          int ret = pstmtUpdate.executeUpdate();
                          
                          if (ret == 0){
                        	  String SQLInsert = "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?,?,?,?)";

	                          	try (PreparedStatement pstmtInsert = con.prepareStatement(SQLInsert)) {
	                                  con.setAutoCommit(false);
	                                  pstmtInsert.setString(1, Location);
	                                  pstmtInsert.setString(2, Product);
	                                  pstmtInsert.setString(3, AttributeSetInstance_ID);
	                                  pstmtInsert.setFloat(4, Units);
	                                  
	                                  pstmtInsert.executeUpdate();
	                                  
	                                  con.commit();
	                                  System.out.println("	Insert disini:");
                                }
                          }
                          
                          con.commit();
                        }catch (SQLException ex) {

                        	String SQLInsert = "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?,?,?,?)";

                        	try (PreparedStatement pstmtInsert = con.prepareStatement(SQLInsert)) {
                                con.setAutoCommit(false);
                                pstmtInsert.setString(1, Location);					//String varchar
//                                pstmtInsert.setString(2, Product);				//String varchar
                                pstmtInsert.setFloat(2, Float.valueOf(Product));	
                                pstmtInsert.setString(3, AttributeSetInstance_ID);	//String varchar
                                pstmtInsert.setFloat(4, Units);						//Float
                                
                                pstmtInsert.executeUpdate();
                                
                                con.commit();
                                System.out.println("	Insert disini:");
                              }
                        	
                        	
                            if (con != null) {
                                try {
//                                    con.rollback();
                                	con.commit();
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
	
  	public static void main(String[] args) {
		// TODO Auto-generated method stub
  		new UpdateToPOSStockCurrent();
	}

}
