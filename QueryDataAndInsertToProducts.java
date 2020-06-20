package org.dede.wsclient;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.idempiere.webservice.client.base.DataRow;
import org.idempiere.webservice.client.base.Enums.WebServiceResponseStatus;
import org.idempiere.webservice.client.base.Field;
import org.idempiere.webservice.client.net.WebServiceConnection;
import org.idempiere.webservice.client.request.QueryDataRequest;
import org.idempiere.webservice.client.response.WindowTabDataResponse;
import org.idempiere.wsclienttest.AbstractTestWS;

public class QueryDataAndInsertToProducts extends AbstractTestWS {

	@Override
	public String getWebServiceType() {
		// TODO Auto-generated method stub
		return "QueryProducts";
//      return "QueryBPartner";
	}

	@Override
	public void testPerformed() {
		// TODO Auto-generated method stub
		QueryDataRequest ws = new QueryDataRequest();
		ws.setWebServiceType(getWebServiceType());
		ws.setLogin(getLogin());
		ws.setLimit(3);
		ws.setOffset(3);
		DataRow data = new DataRow();
//		data.addField("Name", "%Store%");
		data.addField("Name", "%");
		ws.setDataRow(data);

		WebServiceConnection client = getClient();
		
		//connect to local database
		String jdbcUrl = "jdbc:postgresql://localhost:5432/unicenta434";
	    String username = "postgres";
	    String password = "postgres";
	    
	    Connection conn = null;
	    PreparedStatement psInsert = null;
	    PreparedStatement psInsertProducts = null;
	    
		try {
			//kapling database
			//Open connection
	        conn = DriverManager.getConnection(jdbcUrl, username, password);
//	        psInsert = conn.prepareStatement("INSERT INTO temp_prod_category (m_product_category_id, value, name, m_product_category_parent_id) " 
//	    	        + "VALUES (?,?,?,?)");         
	    			
	        psInsert = conn.prepareStatement
	        		("INSERT INTO temp_products (prod_id, prod_reference, prod_code, prod_name, prod_pricebuy, prod_pricesell, prod_category, prod_taxcat, prod_stockcost, "
	        				+ "prod_stockvolume, prod_iscom, prod_isscale, prod_isconstant, prod_printkb, prod_sendstatus,"
	        				+ "prod_isservice, prod_isprice, prod_isverpatrib, prod_warranty, prod_stockunits) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	        
	    	        //psInsert untuk Customer pilih dulu yg not null
	    	        psInsertProducts = conn.prepareStatement
	    	        ("INSERT INTO Products (id, reference, code, name, pricebuy, pricesell, category, taxcat, stockcost, stockvolume, iscom, isscale, isconstant, printkb, sendstatus,"
	    	        		+ "isservice, isprice, isverpatrib, warranty, stockunits) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    	        
	    	        //psInsertCust = conn.prepareStatement("select * from where");
	    			
	    			//kapling webservice
	    			WindowTabDataResponse response = client.sendRequest(ws);

	    			if (response.getStatus() == WebServiceResponseStatus.Error) {
	    				System.out.println(response.getErrorMessage());
	    			} else {
	    				System.out.println("Respons Data: " + response.toString());
	    				System.out.println("Total rows: " + response.getTotalRows());
	    				System.out.println("Num rows: " + response.getNumRows());
	    				System.out.println("Start row: " + response.getStartRow());
	    				System.out.println();
	    				
	    				//Variable product category Class 
	    				String 	prod_id = "";
	    				String 	prod_reference = "";
	    				String 	prod_code = "";
	    				String 	prod_name = "";
	    				Float  	prod_pricebuy = (float) 0;
	    				Float  	prod_pricesell = (float) 0;
	    				String 	prod_category = "";
	    				String 	prod_taxcat = "";
	    				Float 	prod_stockcost = (float) 0;
	    				Float 	prod_stockvolume = (float) 0;
	    				Boolean prod_iscom = (false);
	    				Boolean prod_isscale = (false);
	    				Boolean prod_isconstant = (false);
	    				Boolean prod_printkb = (false);
	    				Boolean prod_sendstatus = (false);
	    				Boolean prod_isservice = (false);
	    				Boolean prod_isprice = (false);
	    				Boolean prod_isverpatrib = (false);
	    				Boolean prod_warranty =(false);
	    				Float 	prod_stockunits = (float) 0;
	    				
	    				
	    				//variable categories
	    				String 	id = "";
	    				String 	reference   = "";
	    				String 	code  = "";
	    				String 	name = "";
	    				Float  	pricebuy = (float) 0;
	    				Float  	pricesell = (float) 0;
	    				String 	category = "";
	    				String 	taxcat = "";
	    				Float 	stockcost = (float) 0;
	    				Float 	stockvolume = (float) 0;
	    				Boolean iscom = (false);
	    				Boolean isscale = (false);
	    				Boolean isconstant = (false);
	    				Boolean printkb = (false);
	    				Boolean sendstatus = (false);
	    				Boolean isservice = (false);
	    				Boolean isprice = (false);
	    				Boolean isverpatrib = (false);
	    				Boolean warranty =(false);
	    				Float 	stockunits = (float) 0;
	    				
	    				
	    				for (int i = 0; i < response.getDataSet().getRowsCount(); i++) {
	    					
	    					System.out.println("Row: " + (i + 1));
	    					
	    					for (int j = 0; j < response.getDataSet().getRow(i).getFieldsCount(); j++) {
	    						Field field = response.getDataSet().getRow(i).getFields().get(j);
	    						System.out.println("Column: " + field.getColumn() + " = " + field.getStringValue());
	    						//Array List Penampung BPartner Class
	    						//Atau pakai kondisi per Kolom
	    						if (field.getColumn().equals("prod_id")) {
	    							prod_id = field.getStringValue();
	    							
	    						}else if(field.getColumn().equals("prod_reference")) {
	    							prod_reference = field.getStringValue();
	    							
	    						}else if(field.getColumn().equals("prod_code")) {
	    							prod_code = field.getStringValue();
	    							
	    						}else if(field.getColumn().equals("prod_name")) {
	    							prod_name = field.getStringValue();
	    							
								}else if(field.getColumn().equals("prod_pricebuy")){
									prod_pricebuy = field.getFloatValue();
									
								}else if(field.getColumn().equals("prod_pricesell")){
									prod_pricesell = field.getFloatValue();
									
								}else if(field.getColumn().equals("prod_category")){
									prod_category = field.getStringValue();
									
								}else if(field.getColumn().equals("prod_taxcat")){
									prod_taxcat = field.getStringValue();
									
								}else if(field.getColumn().equals("prod_stockcost")){
									prod_stockcost = field.getFloatValue();
									
								}else if(field.getColumn().equals("prod_stockvolume")){
									prod_stockvolume = field.getFloatValue();
									
								}else if(field.getColumn().equals("prod_iscom")){
									prod_iscom = field.getBooleanValue();
									
								}else if(field.getColumn().equals("prod_isscale")){
									prod_isscale = field.getBooleanValue();
									
								}else if(field.getColumn().equals("prod_isconstant")){
									prod_isconstant = field.getBooleanValue();
									
								}else if(field.getColumn().equals("prod_printkb")){
									prod_printkb = field.getBooleanValue();
									
								}else if(field.getColumn().equals("prod_sendstatus")){
									prod_sendstatus = field.getBooleanValue();
									
								}else if(field.getColumn().equals("prod_isservice")){
									prod_isservice = field.getBooleanValue();
									
								}else if(field.getColumn().equals("prod_isprice")){
									prod_isprice = field.getBooleanValue();
									
								}else if(field.getColumn().equals("prod_isverpatrib")){
									prod_isverpatrib = field.getBooleanValue();
									
								}else if(field.getColumn().equals("prod_warranty")){
									prod_warranty = field.getBooleanValue();
									
								}else if(field.getColumn().equals("prod_stockunits")){
									prod_stockunits = field.getFloatValue();
									
//	    							
//	    							else {
//	    							if(field.getIntValue().equals(null)){setInt(LogoID,0);}
	    							
	    						}
	    					}
	    					System.out.println();
	    					
	    					//Find atau Look at database first:
	    					//pake metode SQL Query:
	    					//Jika ada Lakukan Statement Update:
	    					//Jika tidak ada lakukan Insert:
	    					
	    					//Insert Row Data ke Table BParner
	    					//insertIntoBpartner				
	    					psInsert.setString (1,  prod_id);
	    					psInsert.setString (2,  prod_reference);
	    					psInsert.setString (3,  prod_code);
	    					psInsert.setString (4,  prod_name);
	    					psInsert.setFloat  (5,  prod_pricebuy);
	    					psInsert.setFloat  (6,  prod_pricesell);
	    					psInsert.setString (7,  prod_category);
	    					psInsert.setString (8,  prod_taxcat);
	    					psInsert.setFloat  (9,  prod_stockcost);
	    					psInsert.setFloat  (10, prod_stockvolume);
	    					psInsert.setBoolean(11, prod_iscom);
	    					psInsert.setBoolean(12, prod_isscale);
	    					psInsert.setBoolean(13, prod_isconstant);
	    					psInsert.setBoolean(14, prod_printkb);
	    					psInsert.setBoolean(15, prod_sendstatus);
	    					psInsert.setBoolean(16, prod_isservice);
	    					psInsert.setBoolean(17, prod_isprice);
	    					psInsert.setBoolean(18, prod_isverpatrib);
	    					psInsert.setBoolean(19, prod_warranty);
	    					psInsert.setFloat  (20, prod_stockunits);

	    					psInsert.executeUpdate(); //remark dulu
	    					
	    					
	    					//Insert ke Table Customer nya Unicenta
	    					psInsertProducts.setString (1, id.toString());
	    					
	    					psInsertProducts.setString (2,reference.toString());
	    					
	    					psInsertProducts.setString (3, code.toString());
	    					
	    					psInsertProducts.setString (4, name.toString());
	    					
	    					psInsertProducts.setFloat  (5, pricebuy.floatValue());
	    					
	    					psInsertProducts.setFloat  (6, pricesell.floatValue());
	    					
	    					psInsertProducts.setString (7, category.toString());
	    					
	    					psInsertProducts.setString(8, taxcat.toString());
	    					
	    					psInsertProducts.setFloat(9, stockcost.floatValue());
	    					
	    					psInsertProducts.setFloat(10, stockvolume.floatValue());
	    					
	    					psInsertProducts.setBoolean(11, iscom.booleanValue());
	    					
	    					psInsertProducts.setBoolean(12, isscale.booleanValue());
	    					
	    					psInsertProducts.setBoolean(13, isconstant.booleanValue());
	    					
	    					psInsertProducts.setBoolean(14, printkb.booleanValue());
	    					
	    					psInsertProducts.setBoolean(15, sendstatus.booleanValue());
	    					
	    					psInsertProducts.setBoolean(16, isservice.booleanValue());
	    					
	    					psInsertProducts.setBoolean(17, isprice.booleanValue());
	    					
	    					psInsertProducts.setBoolean(18, isverpatrib.booleanValue());
	    					
	    					psInsertProducts.setBoolean(19, warranty.booleanValue());
	    					
	    					psInsertProducts.setFloat(20, stockunits.floatValue());
	    					
	    					
	    					psInsertProducts.executeUpdate();
	    					
	    				}					

	    			}
	    				
//	    			}
	    			
	    		} catch (Exception e) {
	    		e.printStackTrace();
	    		}finally {
	    	          try {
	    	              // Close connection
	    	              if (psInsert != null) {
	    	              	psInsert.close();
	    	              }
	    	              if (psInsertProducts != null) {
	    		              	psInsert.close();
	    		          }
	    	              if (conn != null) {
	    	                conn.close();
	    	              }
	    	            } catch (Exception e) {
	    	              e.printStackTrace();
	    	            }
	    	          }

	    	}
	}
