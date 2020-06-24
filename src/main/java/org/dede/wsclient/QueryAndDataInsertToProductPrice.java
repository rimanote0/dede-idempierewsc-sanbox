package org.dede.wsclient;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.idempiere.webservice.client.base.DataRow;
import org.idempiere.webservice.client.base.Enums.WebServiceResponseStatus;
import org.idempiere.webservice.client.base.Field;
import org.idempiere.webservice.client.net.WebServiceConnection;
import org.idempiere.webservice.client.request.QueryDataRequest;
import org.idempiere.webservice.client.response.WindowTabDataResponse;
import org.idempiere.wsclienttest.AbstractTestWS;

public class QueryAndDataInsertToProductPrice extends AbstractTestWS {
	
	@Override
	public String getWebServiceType() {
		// TODO Auto-generated method stub
		return "QueryProductPrice";
//      return "QueryBPartner";
		
	}
		@Override
		public void testPerformed() {
			// TODO Auto-generated method stub
			QueryDataRequest ws = new QueryDataRequest();
			ws.setWebServiceType(getWebServiceType());
			ws.setLogin(getLogin());
			ws.setLimit(0);
			ws.setOffset(0);
			DataRow data = new DataRow();
//			data.addField("Name", "%Store%");
//			data.addField("M_ProductPrice_UU", "%");
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
		        
		        psInsert = conn.prepareStatement ("INSERT INTO temp_m_productprice (m_pricelist_version_id ," +
		        "m_product_id ," +
		        "ad_client_id ," +
		        "ad_org_id ," +
		        "isactive ," +
		        "created ," +
		        "createdby ," +
		        "updated ," +
		        "updatedby ," +
		        "pricelist ," +
		        "pricestd ," +
		        "pricelimit ," +
		        "m_product_price_id)"  + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
		    	        
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
					
					//variable products
					Number 	m_pricelist_version_id = 0;
					Number 	m_product_id   = 0;
					Number 	ad_client_id  = 0;
					Number 	ad_org_id = 0;
					String  isactive = "";
//					Date    created = System.currentTimeMillis();
					long    created = System.currentTimeMillis();
					Number 	createdby = 0;
					long    updated = System.currentTimeMillis();
					Number  updatedby = 0;
					Number  pricelist = 0;
					Number  pricestd = 0;
					Number  pricelimit = 0;
					Number  m_product_price_id = 0;
					
					for (int i = 0; i < response.getDataSet().getRowsCount(); i++) {
						
						System.out.println("Row: " + (i + 1));
						
						for (int j = 0; j < response.getDataSet().getRow(i).getFieldsCount(); j++) {
							Field field = response.getDataSet().getRow(i).getFields().get(j);
							System.out.println("Column: " + field.getColumn() + " = " + field.getStringValue());
							//Array List Penampung BPartner Class
							//Atau pakai kondisi per Kolom
							if (field.getColumn().equals("M_Pricelist_id")) {
								String sM_Pricelist_id = field.getStringValue();
								
							}else if(field.getColumn().equals("M_product_id")) {
								String sM_product_id = field.getStringValue();
								
							}else if(field.getColumn().equals("Ad_client_id")) {
								String sAd_client_id = field.getStringValue();
//								IsSummary = sIsSummary.charAt(0);
								
							}else if(field.getColumn().equals("Ad_org_id")) {
								String sAd_ord_id = field.getStringValue();
								
							}else if(field.getColumn().equals("Isactive")){
								String sIsactive = field.getStringValue();
//								IsStocked = sIsStocked.charAt(0);
								
							}else if(field.getColumn().equals("Created")){
								Date Created = field.getDateValue();
//								IsPurchased = sIsPurchased.charAt(0);		
								
							}else if(field.getColumn().equals("Createdby")){
								String sCreatedby = field.getStringValue();
//								IsSold = sIsSold.charAt(0);
								
							}else if(field.getColumn().equals("Updated")){
								Date Updated = field.getDateValue();
								
							}else if(field.getColumn().equals("Updatedby")){
								Integer Updatedby = field.getIntValue();
//								M_Product_Category_ID = (float) sM_Product_Category_ID;
//								double varDouble = Double.parseDouble(sM_Product_Category_ID);
							
							}else if(field.getColumn().equals("Pricelist")){
//								C_TaxCategory_ID = field.getFloatValue();
								Integer Pricelist = field.getIntValue();
								
								
							}else if(field.getColumn().equals("Pricestd")){
								 Integer Pricestd = field.getIntValue();
//								 IsBOM = sIsBOM.charAt(0);
								 
								
							}else if(field.getColumn().equals("Pricelimit")){
								Integer Pricelimit = field.getIntValue();
//								IsInvoicePrintDetails = sIsInvoicePrintDetails.charAt(0);
								
								
							}else if(field.getColumn().equals("M_Product_Price_Id")){
								Integer M_Product_Price_Id = field.getIntValue();
//								IsPickListPrintDetails = sIsPickListPrintDetails.charAt(0); 
								
							}						
							
						}
						
						System.out.println();
						
						//Find atau Look at database first:
						//pake metode SQL Query:
						//Jika ada Lakukan Statement Update:
						//Jika tidak ada lakukan Insert:
						
						//Insert Row Data ke Table Temp_Products
						//insertIntoBpartner				
						psInsert.setInt		(1,  (int) m_pricelist_version_id);
						psInsert.setInt 	(2,  (int) m_product_id);
						psInsert.setInt 	(3,  (int) ad_client_id);
						psInsert.setInt 	(4,  (int) ad_org_id);
						psInsert.setString 	(5,  isactive);
						
						//created
//						long yourmilliseconds = System.currentTimeMillis();
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
						Time resultdate = new Time(created);
						System.out.println(sdf.format(resultdate));
						psInsert.setTime   	(6,  (Time) resultdate);
						
						psInsert.setInt 	(7,  (int) createdby);
						
						//updated
						Time resultdate2 = new Time(updated);
						System.out.println(sdf.format(resultdate2));
						psInsert.setTime   	(8,  resultdate2);
						
						psInsert.setInt 	(9,  (int) updatedby);
						psInsert.setInt 	(10, (int) pricelist);
						psInsert.setInt 	(11, (int) pricestd);
						psInsert.setInt 	(12, (int) pricelimit);
						psInsert.setInt 	(13, (int) m_product_price_id);
						
						psInsert.executeUpdate();
								
					}
		}					

			
	    				
			
		} catch (Exception e) {
		e.printStackTrace();
		}finally {
			try {
		           // Close connection
		           if (psInsert != null) {
		        	   psInsert.close();
		
		           }
	          	} catch (Exception e) {
	              e.printStackTrace();
	          	}
		}
	}
}