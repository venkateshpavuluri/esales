/**
 * 
 */
package com.mnt.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mnt.esales.bean.BillDetails;

/**
 * @author madhav
 *
 */
public class BillDetailsRowMapper implements RowMapper<BillDetails> {

	@Override
	public BillDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			BillDetails billDetails =new BillDetails();
			billDetails.setBillDetailsId(rs.getString("bill_Details_Id"));
			billDetails.setProdId(rs.getString("prod_Id"));
			billDetails.setProdDesc(rs.getString("prod_Desc"));
			billDetails.setRate(Double.parseDouble(rs.getString("rate")));
			billDetails.setQuantity(Float.parseFloat(rs.getString("quantity")));
			billDetails.setDiscount(Float.parseFloat(rs.getString("discount")));
			billDetails.setVat(Float.parseFloat(rs.getString("vat")));
			billDetails.setAmount(Float.parseFloat(rs.getString("amount")));
		
			
		return billDetails;
	}

}
