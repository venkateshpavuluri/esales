/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.DepartmentBean;
import com.mnt.esales.daosupport.MntDaoSupport;
import com.mnt.rowmappers.BillDetailsRowMapper;
import com.mnt.rowmappers.BillInfoRowMapper;
import com.mnt.rowmappers.BillRowMapper;

/**
 * @author madhav
 *
 */
@Repository("billSearchDao")
public class BillSearchDaoImapl extends MntDaoSupport implements BillSearchDao {

	@Override
	public List<BillDetails> getBillDetailsOfBillInfo(BillInfo billInfo) {
		
		String sql="SELECT bd.`bill_Details_Id`,bd.`prod_Id`,bd.`prod_Desc`,bd.`rate`,bd.`quantity`,bd.`discount`,bd.`vat`,bd.`amount`" +
				" FROM `bill_details` bd WHERE bd.`bill_Info_Id`='"+billInfo.getBillId()+"'";
		System.out.println("Executing:"+sql);
		System.out.println(billInfo.getBillId());
		List<BillDetails> billDetails = getJdbcTemplate().query(sql,new BillDetailsRowMapper());
		
		return billDetails;
	}

	@Override
	public BillInfo findBillInfobyBillNo(String billNo) {
		
		String sql="SELECT bi.`bill_Info_Id`,bi.`bill_Date`,bi.`mobile_No`,bi.`total_mrp`,bi.`total_rate`," +
				"bi.`total_Discount`,bi.`total_tax`,bi.`net_Amt`,bi.`paid_by_Cash`,bi.`paid_by_Card`," +
				"bi.`paid_by_Cheque`,bi.`card_No`,bi.`cheque_No`,bi.`total_Payment`,bi.`return_Change`,bi.`total_Quantiy`,bi.`bill_Status`" +
				"FROM `bill_info` bi WHERE `bi`.`bill_Info_Id`='"+billNo+"'";
		System.out.println("Executing:"+sql);
	
		List<BillInfo> billInformation= getJdbcTemplate().query(sql,new BillInfoRowMapper());
	
		
		return billInformation.get(0);
	}

}
