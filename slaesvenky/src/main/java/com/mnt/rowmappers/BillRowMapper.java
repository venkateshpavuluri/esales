/**
 * 
 */
package com.mnt.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.bean.Stock;

/**
 * @author madhav
 *
 */
public class BillRowMapper implements RowMapper<BillDetails> {

	@Override
	public BillDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		//Bean Initialization for setting the values to bean from resultset
		BillDetails billDetails=new BillDetails();
		//Object for setting the product details
		//ProductBean productDetails=new ProductBean();
		try{
			
			//Setting the product values(Child Bean)
			//productDetails.setProductName(rs.getString("prod_Desc"));
			//billDetails.setProdId(rs.getString("prod_Id"));
			billDetails.setProdDesc(rs.getString("productName"));
			//billDetails.setProductDetails(productDetails);
			
			//Set the Stock values from DB
			billDetails.setStockId(rs.getString("stock_Id"));
			
			billDetails.setProdId(rs.getString("prod_Id"));
			billDetails.setRate(Double.parseDouble(rs.getString("cost")));
			billDetails.setQuantity(Float.parseFloat(rs.getString("quantity")));
			billDetails.setMrp(Double.parseDouble(rs.getString("price")));
			billDetails.setDiscount(Float.parseFloat(rs.getString("discount")));
			billDetails.setVat(Float.parseFloat(rs.getString("vat")));
			billDetails.setStockEntry(rs.getString("stock_Entry"));
			/*productDetails.setProductId(rs.getString("product_Id"));*/
			//setting the product values to Stock object instance variable(Parent Bean)
		}catch(Exception e){
			
		}
		
		return billDetails;
	}

}
