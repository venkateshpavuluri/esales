/**
 * 
 */
package com.mnt.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mnt.esales.bean.BillInfo;

/**
 * @author madhav
 *
 */
public class BillInfoRowMapper implements RowMapper<BillInfo> {

	@Override
	public BillInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BillInfo billIn=new BillInfo();
		billIn.setBillId(rs.getString("bill_Info_Id"));
		billIn.setBillDate(rs.getString("bill_Date"));
		billIn.setMobileNo(rs.getString("mobile_No"));
		billIn.setTotalMrp(rs.getString("total_mrp"));
		billIn.setTotalRate(rs.getString("total_rate"));
		billIn.setTotalDiscount(rs.getString("total_Discount"));
		billIn.setTotaltax(rs.getString("total_tax"));
		billIn.setNetAmt(rs.getString("net_Amt"));
		billIn.setPaidbyCash(rs.getString("paid_by_Cash"));
		billIn.setPaidbyCard(rs.getString("paid_by_Card"));
		billIn.setPaidbyCheque(rs.getString("paid_by_Cheque"));
		billIn.setCardNo(rs.getString("card_No"));
		billIn.setChequeNo(rs.getString("cheque_No"));
		billIn.setTotalPayment(rs.getString("total_Payment"));
		billIn.setReturnChange(rs.getString("return_Change"));
		billIn.setTotalQuantiy(rs.getString("total_Quantiy"));
		billIn.setBillStatus(rs.getString("bill_Status"));
		
		return billIn;
	}

}
