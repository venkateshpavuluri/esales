/**
 * 
 */
package com.mnt.esales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.BillBean;
import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.RoleBean;
import com.mnt.esales.bean.Stock;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;
import com.mnt.rowmappers.BillRowMapper;
import com.mnt.rowmappers.RolesRowMapper;
import com.mysql.jdbc.Statement;

/**
 * @author madhav
 * 
 */
@Repository("billDao")
public class BillDaoImpl extends MntDaoSupport implements BillDao {
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	HttpSession session;
	@Autowired
	CustomConnection customConnection;

	boolean flag = true;

	@Override
	public List<BillDetails> getBillingDetails(String productCode,
			String branchId, float quantity) {

		List<BillDetails> billDetails = null;
		try {

			/*
			 * billDetails=getJdbcTemplate().query(
			 * "SELECT p.`prod_Desc`,bs.branch_Id,s.stock_Id,s.prod_Id,s.cost,s.quantity,s.mrp,s.discount,s.vat,s.stock_Entry FROM stock bs JOIN stock s ON s.`stock_Id`=bs.stock_Id JOIN product p ON p.product_Id=bs.prod_Id "
			 * +"WHERE s.prod_Id='"+productCode+"'AND s.cost='"+rate+
			 * "'AND s.quantity='"+quantity+"'" "' AND bs.branch_Id='B0002'",
			 * 
			 * new BillRowMapper());
			 */
			/*
			 * billDetails=getJdbcTemplate().query(
			 * "SELECT p.prod_Desc,s.stock_Id,s.prod_Id,s.cost,s.quantity,s.mrp,s.discount,s.vat,s.stock_Entry FROM stock s"
			 * + " JOIN product p ON p.product_Id=s.prod_Id WHERE prod_Id='"+
			 * productCode+"' AND cost='"+rate+"' AND quantity='"+quantity+"'",
			 * new BillRowMapper());
			 */
			/*
			 * String sql=
			 * "SELECT CONCAT_WS('-',c.`category_Desc`,sc.`subCategory_Desc`,p.`prod_Desc`) AS `productName`,s.stock_Id,sb.prod_Id,s.cost,SUM(sb.quantity)AS quantity,sb.price,s.discount,s.vat,s.stock_Entry FROM stock_branch sb "
			 * + "JOIN stock s ON s.`stock_Id`=sb.stock_Id " +
			 * "JOIN product p ON p.product_Id=sb.prod_Id  " +
			 * "JOIN `prod_subcat` ps ON p.`product_Id`=p.`product_Id` " +
			 * "JOIN `subcategory` sc ON sc.`subCategory_Id`=ps.`subCategory_Id` "
			 * +
			 * "JOIN `subcat_category` scc ON scc.`subcategory_Id`=sc.`subCategory_Id` "
			 * + "JOIN `category` c ON c.`category_Id`=scc.`category_Id` " +
			 * "WHERE p.product_Code='"
			 * +productCode+"'AND sb.branch_Id='"+branchId
			 * +"'GROUP BY sb.prod_Id";
			 */
			String sql = "SELECT CONCAT_WS(' ',c.`category_Desc`,sc.`subCategory_Desc`,p.`prod_Desc`) AS productName,s.stock_Id, sb.prod_Id, s.cost, sb.quantity, sb.`price`,s.`discount`,s.`vat`,s.`stock_Entry` FROM  `stock_branch` sb "
					+ "JOIN `stock` s ON s.`stock_Id`=sb.`stock_Id` "
					+ "JOIN `product` p ON p.`product_Id`=s.`prod_Id` "
					+ "AND p.`product_Id`=sb.`prod_Id` "
					+ "JOIN  `prod_subcat` ps ON ps.`product_Id`=p.`product_Id` "
					+ "JOIN `subcategory` sc ON sc.`subCategory_Id`=ps.`subCategory_Id` "
					+ "JOIN `subcat_category` sct ON sct.subcategory_Id=sc.`subCategory_Id` "
					+ "AND sct.subcategory_Id=ps.`subCategory_Id` JOIN `category` c ON c.`category_Id`=sct.category_Id "
					+ "WHERE p.product_Id='"
					+ productCode
					+ "'AND sb.branch_Id='" + branchId + "'";

			billDetails = getJdbcTemplate().query(sql, new BillRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return billDetails;
	}

	@Override
	public boolean saveBill(final BillBean bill) {

		List<BillDetails> billDetails = null;
		final String sql = "insert into bill_info(bill_Date,mobile_No,total_mrp,total_rate,total_Discount,total_tax,net_Amt,paid_by_Cash,paid_by_Card,paid_by_Cheque,card_No,"
				+ "cheque_No,total_Payment,return_Change,total_Quantiy,bill_Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		final String sql1 = "insert into bill_branch(bill_Info_Id,user_Id,branch_Id) values(?,?,?)";

		try {
			/*
			 * int id=getJdbcTemplate().update(sql,new
			 * Object[]{bill.getBillDate(
			 * ),bill.getMobileNo(),bill.getTotalMrp(),
			 * bill.getTotalRate(),bill.getTotalDiscount(),
			 * bill.getTotaltax(),bill
			 * .getNetAmt(),bill.getPaidbyCash(),bill.getPaidbyCard
			 * (),bill.getCardNo(),bill.getPaidbyCheque(),bill.getChequeNo(),
			 * bill
			 * .getTotalPayment(),bill.getReturnChange(),bill.getQuantity(),bill
			 * .getBillStatus()});
			 */
			KeyHolder holder = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							sql.toString(), Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, bill.getBillDate());
					ps.setString(2, bill.getMobileNo());
					ps.setString(3, bill.getTotalMrp());
					ps.setString(4, bill.getTotalRate());
					ps.setString(5, bill.getTotalDiscount());
					ps.setString(6, bill.getTotaltax());
					ps.setString(7, bill.getNetAmt());
					if (bill.getPaymentType().equalsIgnoreCase("1")) {
						ps.setString(8, bill.getPaidbyCash());
						ps.setString(9, bill.getPaidbyCard());
						ps.setString(11, bill.getPaidbyCheque());
					} else if (bill.getPaymentType().equals("2")) {
						ps.setString(9, bill.getPaidbyCard());
						ps.setString(11, bill.getPaidbyCheque());
						ps.setString(8, bill.getPaidbyCash());
					} else {
						ps.setString(8, bill.getPaidbyCash());
						ps.setString(11, bill.getPaidbyCheque());
						ps.setString(9, bill.getPaidbyCard());
					}
					ps.setString(10, bill.getCardNo());
					ps.setString(12, bill.getChequeNo());
					ps.setString(13, bill.getTotalPayment());
					ps.setString(14, bill.getReturnChange());

					ps.setString(15, String.valueOf(bill.getTotalQuantiy()));
					ps.setString(16, bill.getBillStatus());
					return ps;
				}
			}, holder);

			Long newPersonId = holder.getKey().longValue();

			int ids = newPersonId.intValue();

			String userId = (String) session.getAttribute("userId");
			billDetails = bill.getBillDetails();

			saveChildDetails(billDetails, ids, bill.getBranchId());
			getJdbcTemplate().update(sql1,
					new Object[] { ids, userId, bill.getBranchId() });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		return flag;
	}

	// save the Child information bill Details
	private void saveChildDetails(final List<BillDetails> billDetails,
			final int id, final String branchId) {

		final String sql2 = "insert into bill_details(prod_Id,prod_Desc,rate,quantity,discount,vat,amount,bill_Info_Id) values(?,?,?,?,?,?,?,?)";
		final String sqlforup = "UPDATE stock_branch s SET s.quantity=s.quantity-? WHERE s.prod_Id=? AND s.branch_Id=?";
		try {
			getJdbcTemplate().batchUpdate(sql2,
					new BatchPreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							BillDetails b = billDetails.get(i);
							ps.setString(1, b.getProdId());
							ps.setString(2, b.getProdDesc());
							ps.setString(3, String.valueOf(b.getRate()));
							ps.setString(4, String.valueOf(b.getQuantity()));
							ps.setString(5, String.valueOf(b.getDiscount()));
							ps.setString(6, String.valueOf(b.getVat()));
							ps.setString(7, String.valueOf(b.getAmount()));
							ps.setInt(8, id);
						}

						@Override
						public int getBatchSize() {
							return billDetails.size();
						}
					});

			getJdbcTemplate().batchUpdate(sqlforup,
					new BatchPreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							BillDetails b = billDetails.get(i);
							ps.setString(2, b.getProdId());
							ps.setString(1, String.valueOf(b.getQuantity()));
							ps.setString(3, branchId);
						}

						@Override
						public int getBatchSize() {
							return billDetails.size();
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * @Override public List<Stock> edit(String stockId) { List<Stock> billBean
	 * = null; try {
	 * 
	 * String sql =
	 * "SELECT p.prod_Desc,s.stock_Id,s.prod_Id,s.cost,s.quantity,s.mrp,s.discount,s.vat,s.stock_Entry FROM stock s"
	 * +
	 * " JOIN product p ON p.product_Id=s.prod_Id WHERE stock_Id='"+stockId+"'";
	 * 
	 * billBean = getJdbcTemplate().query( sql, new BillRowMapper());
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return billBean; }
	 */

	@Override
	public boolean billUpdate(Object object) {
		try {
			BillDetails billBean = (BillDetails) object;
			List<BillDetails> blist = null;
			String sql = "update bill_details set prod_Desc=?,rate=?,quantity=?,discount=?,vat=?,amount=? where bill_Details_Id=?";
			getJdbcTemplate().update(
					sql,
					new Object[] { billBean.getProdDesc(), billBean.getRate(),
							billBean.getQuantity(), billBean.getDiscount(),
							billBean.getVat(), billBean.getAmount(),
							billBean.getBillDetailsId() });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean billDelete(String stockId) {
		try {
			String parentSql = "delete from stock where stock_Id=? ";
			getJdbcTemplate().update(parentSql, new Object[] { stockId });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String getUser(String userId) {
		List<RoleBean> roleBean = null;
		RoleBean roleName = null;
		try {
			String sql = "SELECT r.role_Id,r.role_Name FROM userroles ur "
					+ "JOIN roles r ON r.`role_Id`=ur.`role_Id` "
					+ "JOIN users u ON u.`user_Id`=ur.`user_Id` "
					+ "WHERE u.`user_Id`='" + userId + "'";

			roleBean = getJdbcTemplate().query(sql, new RolesRowMapper());

		} catch (DataAccessException e) {

			e.printStackTrace();
		}
		if (roleBean != null) {
			roleName = roleBean.get(0);
		} else {

		}

		return roleName.getRoleName();
	}

	@Override
	public boolean checkForProductId(String id, String branchId) {
		String sql = "SELECT COUNT(*) FROM stock_branch sb  "
				+ "JOIN product p ON p.product_Id=sb.prod_Id "
				+ "WHERE p.product_Code='" + id + "'AND sb.branch_Id='"
				+ branchId + "'";
		int count = getJdbcTemplate().queryForInt(sql);
		if (count == 0) {
			return false;
		} else
			return true;

	}

	@Override
	public int checkForQuantity(String branchId, String prodId, int quantity) {

		int availableQuantity = 0;
		try {
			String sql = "SELECT SUM(quantity) FROM stock_branch sb "
					+ "join product p on p.product_Id=sb.prod_Id"
					+ " WHERE sb.branch_Id='" + branchId
					+ "' AND p.product_Code='" + prodId
					+ "'GROUP BY p.product_Code";
			availableQuantity = getJdbcTemplate().queryForInt(sql);

		} catch (DataAccessException e) {

			e.printStackTrace();

		}
		return availableQuantity;
	}

	@Override
	public List<Stock> edit(String stockId) {
		return null;
	}

	@Override
	public boolean saveBillInfo(BillInfo billInfo, boolean status) {
		// TODO Auto-generated method stub
		try
		{
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return false;
	}

}
