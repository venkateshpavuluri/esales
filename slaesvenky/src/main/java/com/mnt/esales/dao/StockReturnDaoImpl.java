/**
 * 
 */
package com.mnt.esales.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mnt.esales.bean.StockReturnBean;
import com.mnt.esales.daosupport.MntDaoSupport;

/**
 * @author yogi
 * 
 */
@Repository("stockReturnDao")
public class StockReturnDaoImpl extends MntDaoSupport implements StockReturnDao {

	@Override
	public List<StockReturnBean> getCurrentStock(String branchId,
			String clientId) {

		List<StockReturnBean> currentStockList = new ArrayList<StockReturnBean>();
		try {
			String sql = "SELECT concat(c.category_Desc,s.`subCategory_Desc`,p.prod_Desc) as productName,sb.stock_Id as stockId,p.`product_Id` as productId, sb.`branch_Id`,c.`category_Desc`, sb.`quantity` as inStockQuantity  FROM `subcat_category` sc , category c , `subcategory` s, `prod_subcat` ps, product p, `stock_branch` sb, `client_product` cp "
					+ "WHERE sc.`category_Id`=c.`category_Id` "
					+ "AND sc.`subcategory_Id`=s.`subCategory_Id` "
					+ "AND ps.`product_Id`= p.`product_Id` "
					+ "AND ps.`subCategory_Id`= s.`subCategory_Id` "
					+ "AND sb.`prod_Id` = p.`product_Id` "
					+ "AND cp.`product_Id`= p.`product_Id` "
					+ "AND sb.`branch_Id`='"
					+ branchId
					+ "' "
					+ "AND cp.`client_Id`='"
					+ clientId
					+ "' "
					+ "AND sb.`quantity`!=0";
			currentStockList = getJdbcTemplate().query(
					sql,
					new BeanPropertyRowMapper<StockReturnBean>(
							StockReturnBean.class));
		} catch (Exception s) {
			s.printStackTrace();
		}
		return currentStockList;
	}

	@Override
	public List<StockReturnBean> getReturnStock(String branchId, String clientId) {
		List<StockReturnBean> returnStockList = new ArrayList<StockReturnBean>();
		try {
			String sql = "SELECT p.product_Id,CONCAT(c.category_Desc,sc.subCategory_Desc ,p.prod_Desc) AS productName , "
					+ "s.`quantity` AS qty, sb.`quantity` FROM prod_subcat ps  "
					+ "JOIN product p ON p.product_Id=ps.product_Id "
					+ "JOIN subcategory sc ON sc.subCategory_Id=ps.subCategory_Id "
					+ "JOIN subcat_category scc ON scc.subcategory_Id=ps.subCategory_Id "
					+ "JOIN category c ON scc.category_Id=c.category_Id "
					+ "JOIN `stock_branch` sb ON sb.`prod_Id`=p.`product_Id` "
					+ "JOIN stock s ON s.`stock_Id`= sb.`stock_Id` "
					+ "JOIN `stock_client` stc ON stc.`stock_Id`= s.`stock_Id` "
					+ "AND stc.`client_Id`='"
					+ clientId
					+ "' AND sb.`branch_Id`='"
					+ branchId
					+ "' AND sb.`quantity`!=0";
			returnStockList = getJdbcTemplate().query(
					sql,
					new BeanPropertyRowMapper<StockReturnBean>(
							StockReturnBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStockList;
	}

	@Override
	public boolean saveStockReturn(final List<StockReturnBean> stockReturnBeans) {
		// String returnId=null;
		boolean flag = false;
		try {
			String sql = "insert into stock_return(branch_Id,stock_Id,product_Code,return_stock,return_entry,user_id) values(?,?,?,?,?,?)";
			String updateSql = "UPDATE `stock_branch` s SET s.`quantity`=? WHERE s.stock_Id=? AND s.`prod_Id`=? AND s.`branch_id`=?";

			int[] y = getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							StockReturnBean allocation = stockReturnBeans
									.get(i);
							if (allocation.getReturnStock() != null) {
								ps.setString(1, allocation.getBranchId());
								ps.setString(2, allocation.getStockId());
								ps.setString(3, allocation.getProductCode());
								ps.setString(4, allocation.getReturnStock());
								ps.setString(5, allocation.getReturnEntry());
								ps.setString(6, allocation.getUserId());
							}
						}

						@Override
						public int getBatchSize() {
							return stockReturnBeans.size();
						}
					});
			int[] z = getJdbcTemplate().batchUpdate(updateSql,
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int j)
								throws SQLException {
							StockReturnBean allocation = stockReturnBeans
									.get(j);

							if (allocation.getReturnStock() == "") {
								System.out.println(allocation.getReturnStock()
										+ "return stock in if");
								int rStock = 0;
								int qty = Integer.parseInt(allocation
										.getInStockQuantity()) - rStock;
								ps.setString(1, String.valueOf(qty));
								ps.setString(2, allocation.getStockId());
								ps.setString(3, allocation.getProductCode());
								ps.setString(4, allocation.getBranchId());
							} else {
								System.out.println(allocation.getReturnStock()
										+ "return stock in else");
								int qty = Integer.parseInt(allocation
										.getInStockQuantity())
										- Integer.parseInt(allocation
												.getReturnStock());
								ps.setString(1, String.valueOf(qty));
								ps.setString(2, allocation.getStockId());
								ps.setString(3, allocation.getProductCode());
								ps.setString(4, allocation.getBranchId());
							}
						}

						@Override
						public int getBatchSize() {
							return stockReturnBeans.size();
						}
					});
			String updateSq = "UPDATE `stock` s SET s.`quantity`=s.`quantity`+? WHERE s.stock_Id=? ";
			int[] x = getJdbcTemplate().batchUpdate(updateSq,
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int j)
								throws SQLException {
							StockReturnBean allocation = stockReturnBeans.get(j);
							String rso=allocation.getReturnStock();
							System.out.println(rso+"rstock");
							ps.setInt(1, Integer.parseInt(allocation.getReturnStock()));
							ps.setString(2, allocation.getStockId());
						}

						@Override
						public int getBatchSize() {
							return stockReturnBeans.size();
						}
					});
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error(e.getMessage());
		}
		return flag;
	}
	private boolean deleteStockRetrun(String branchId){
		boolean flag=false;
		try{
			String sql="delete from stock_return where branch_Id='"+branchId+"'";
			getJdbcTemplate().update(sql);
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}
}
