/**
 * 
 */
package com.mnt.esales.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.esales.bean.GetMaxIdBean;
import com.mnt.esales.bean.Permissions;
import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockEntryUpdate;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;
import com.mnt.rowmappers.GetMaxIdRowMapper;

/**
 * @author venkateshp
 *
 */
@Repository("stockAllocationDao")
public class StockAllocationDaoImpl extends MntDaoSupport implements StockAllocationDao {
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	CustomConnection customConnection;
private Logger logger=Logger.getLogger(StockAllocationDaoImpl.class);
private String sql;
private boolean flag;
	@Override
	public List<StockAllocation> getProducts(String clientId) {
		List<StockAllocation> stockAllocations=null;
	try
	{
		
		//sql="SELECT p.prod_Desc as productName,cp.product_Id as productId,s.quantity as quantity,s.stock_Id as stockId FROM client_product cp JOIN stock s  ON cp.product_Id=s.prod_Id JOIN product p ON cp.product_Id=p.product_Id WHERE cp.client_Id='"+clientId+"'";
		sql="SELECT cp.client_Id,p.prod_Desc AS productName,p.product_Code as productCode,cp.product_Id AS productId,s.quantity AS quantity,s.mrp as mrp,s.stock_Id AS stockId,cat.category_Id AS categoryId,cat.category_Desc as categoryName,su.subCategory_Desc as subCategoryName,su.subCategory_Id AS subCategoryId FROM client_product cp JOIN product p ON cp.product_Id=p.product_Id JOIN stock s  ON cp.product_Id=s.prod_Id JOIN prod_subcat ps ON ps.product_Id=p.product_Id JOIN subcat_category sc ON sc.subCategory_Id= ps.subCategory_Id JOIN subcategory su ON su.subcategory_Id = sc.subCategory_Id JOIN category cat ON cat.category_Id = sc.category_Id WHERE cp.client_Id='"+clientId+"'";
		stockAllocations=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<StockAllocation>(StockAllocation.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
		e.printStackTrace();
	}
		return stockAllocations;
	}
	@Override
	public List<StockAllocation> getBranchDetails(String clientId) {
		// TODO Auto-generated method stub
		List<StockAllocation> stockAllocations=null;
		try
		{
			sql="select b.branch_Id as branchId,b.branch_Name as branchName from branch b where b.client_Id='"+clientId+"'";
			stockAllocations=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<StockAllocation>(StockAllocation.class));
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return stockAllocations;
	}
	public boolean saveStockAllocations( final List<StockAllocation> stockAllocations,boolean status) {
		// TODO Auto-generated method stub
		String returnId=null;
		JdbcTemplate jdbcTemplate=null;
		try
		{
			
			if(status)
			{
				jdbcTemplate=getJdbcTemplate();
			}
			else
			{
				jdbcTemplate=customConnection.getTemplateConnection();
			}
			sql="insert into stock_branch(branch_Id,stock_Id,prod_Id,quantity,client_Id,price) values(?,?,?,?,?,?)";
		int[] y=jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				 
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					StockAllocation allocation = stockAllocations.get(i);
					ps.setString(1,allocation.getBranchId());
					ps.setString(2,allocation.getStockId());
					ps.setString(3,allocation.getProductId());
					ps.setString(4,allocation.getQuantity());
					ps.setString(5,allocation.getClientId());
					ps.setDouble(6,allocation.getMrp());
				}
				@Override
				public int getBatchSize() {
					return stockAllocations.size();
				}
			  });
			flag=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			flag=false;
			logger.error(e.getMessage());
		}
		return flag;
	}
	@Override
	public StockAllocation getQuantity(String branchId, String stockId, String productId) {
		// TODO Auto-generated method stub
		String quan=null;
		List<StockAllocation> stockAllocations=null;
		StockAllocation stAlloc=null;
		try
		{
			sql="select quantity as quantity,price  as mrp  from stock_branch where branch_Id='"+branchId+"' and stock_Id='"+stockId+"' and prod_Id='"+productId+"'";
			stockAllocations=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<StockAllocation>(StockAllocation.class)); 
			logger.debug("stockAllocations in dao isss=="+stockAllocations);
			if(stockAllocations.size()!=0)
		{
			 stAlloc=(StockAllocation)stockAllocations.get(0);
			quan=stAlloc.getQuantity();
			logger.debug("quantity in dao isss=="+quan);
		}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return stAlloc;
	}
	@Override
	public boolean deleteStockAllocc(String clientId) {
	try
	{
		sql=" delete from stock_branch where client_Id='"+clientId+"'";
int deletecount=getJdbcTemplate().update(sql);
logger.debug("delete count iss=="+deletecount);
flag=false;
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
		flag=true;
	}
		return flag;
	}
	@Override
	public boolean updateStkEntrysQuantity(final List<StockEntryUpdate> entryUpdates) {
		// TODO Auto-generated method stub
		try
		{
			sql="update stock s set s.quantity=? where s.stock_Id=?";
			int[] y=getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
				 
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					StockEntryUpdate allocation = entryUpdates.get(i);
					ps.setString(1,allocation.getQuantity());
					ps.setString(2,allocation.getStockId());
				}
				@Override
				public int getBatchSize() {
					return entryUpdates.size();
				}
			  });
			flag=true;
		}
		catch(Exception e)
		{
			flag=false;
			logger.error(e.getMessage());
		}
		return flag;
	}
}