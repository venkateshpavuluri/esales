package com.mnt.esales.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.ExpenseReportBean;
import com.mnt.esales.bean.ReportBean;
import com.mnt.esales.bean.StockReportBean;
import com.mnt.esales.bean.saleReportBean;
import com.mnt.esales.daosupport.MntDaoSupport;
@Repository("reportDao")
public class ReportDaoImpl extends MntDaoSupport implements ReportDao{
	private String sql;
	@Override
	public List<ReportBean> billInfoSearch(String branchId, String sdate, String clientId) {
		List<ReportBean> infoBeans=new ArrayList<ReportBean>();
		try
		{
				sql="SELECT bi.`bill_Info_Id` as billId,DATE(bi.bill_Date) as billDate,bi.mobile_No as mobileNo,bi.total_mrp as totalMrp,bi.total_rate as totalRate,bi.total_Discount as totalDiscount,bi.net_Amt as netAmt,bi.total_Payment as totalPayment,bi.return_Change as returnChange,bi.total_Quantiy as total_Quantiy FROM branch_client bc JOIN `branch` b ON b.`branch_Id`=bc.`branch_Id` JOIN `bill_branch` bb ON bb.`branch_Id`=b.`branch_Id` JOIN  `bill_info` bi ON bi.`bill_Info_Id`=bb.bill_info_Id JOIN  `bill_details` bd ON bd.`bill_Info_Id`=bi.`bill_Info_Id` WHERE bc.`client_Id`='"+clientId+"' AND b.`branch_Id`='"+branchId+"' AND DATE(bi.`bill_Date`)='"+sdate+"'";
				infoBeans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<ReportBean>(ReportBean.class));
			
		 	 	}
		  
		
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return infoBeans;
	}

	@Override
	public List<ReportBean> billDetailSearch(String branchId, String sdate, String clientId) {
		List<ReportBean> detailBeans=new ArrayList<ReportBean>();
		try
		{
		 	 sql="SELECT bd.`bill_Details_Id` as billDetailId,bd.`prod_Desc` as prodDesc ,bd.`rate` as pdRate,bd.`quantity` as pdQuantity,bd.`discount` as pdDiscount,bd.`vat`,bd.`amount` as pdAmount FROM branch_client bc JOIN `branch` b ON b.`branch_Id`=bc.`branch_Id` JOIN `bill_branch` bb ON bb.`branch_Id`=b.`branch_Id` JOIN  `bill_info` bi ON bi.`bill_Info_Id`=bb.bill_info_Id JOIN  `bill_details` bd ON bd.`bill_Info_Id`=bi.`bill_Info_Id` WHERE bc.`client_Id`='"+clientId+"' AND b.`branch_Id`='"+branchId+"' AND DATE(bi.`bill_Date`)='"+sdate+"'";
		 	detailBeans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<ReportBean>(ReportBean.class));
			}
		  
		
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return detailBeans;
	}
	@Override
	public List<ExpenseReportBean> expenseDetailSearch(String branchId, String sdate, String clientId) {
		List<ExpenseReportBean> exBeans=new ArrayList<ExpenseReportBean>();
		try
		{
			
		 	 sql="SELECT ed.description,ed.amount FROM expenses_branch eb JOIN expense_info ei ON ei.`expenses_Id`=eb.`expenses_Id` JOIN expense_details ed ON ed.`expenses_Id`=ei.`expenses_Id` JOIN branch b ON b.`branch_Id`=eb.`branch_Id` JOIN `branch_client` bc ON bc.`branch_Id`=b.`branch_Id` JOIN `clientinfo` c ON c.`client_Id`=b.`client_Id` AND bc.`client_Id`=c.`client_Id` WHERE eb.`branch_Id`='"+branchId+"' AND  c.`client_Id`='"+clientId+"' and DATE(ei.`expenses_Date`)='"+sdate+"'";
		 	exBeans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<ExpenseReportBean>(ExpenseReportBean.class));
	
			}
		  
		
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return exBeans;
	}

	@Override
	public List<StockReportBean> stockDetailSearch(String branchId, String sdate, String clientId) {
		List<StockReportBean> exBeans=new ArrayList<StockReportBean>();
		try
		{			
		 	 sql="SELECT  CONCAT(c.`category_Desc`,s.`subCategory_Desc`,p.`prod_Desc`) AS Product,st.`quantity`,DATE(st.`stock_Entry`) AS stockDate FROM prod_subcat ps JOIN  `subcategory` s ON s.`subCategory_Id`=ps.`subCategory_Id` JOIN `subcat_category` sc ON sc.subcategory_Id=s.`subCategory_Id` JOIN category c ON c.category_Id=sc.category_Id JOIN `product` p ON p.`product_Id`=ps.`product_Id` JOIN `stock_branch` sb ON sb.`prod_Id`=p.`product_Id` JOIN `stock` st ON st.`stock_Id`=sb.`stock_Id` AND st.`prod_Id`=p.`product_Id` JOIN `branch_client` bc ON bc.`branch_Id`=sb.`branch_Id` JOIN `branch` b ON b.`branch_Id`=bc.`branch_Id` WHERE b.`branch_Id`='"+branchId+"' AND  bc.`client_Id`='"+clientId+"' and DATE(st.`stock_Entry`)='"+sdate+"'";
		 	exBeans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<StockReportBean>(StockReportBean.class));
	      
			}
		  
		
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return exBeans;
	}
	
	@Override
	public List<saleReportBean> saleProdSearch(String branchId, String sdate, String clientId) {
		List<saleReportBean> exBeans=new ArrayList<saleReportBean>();
		try
		{			
		 	 sql=" SELECT s.`subCategory_Id` as sId,bd.prod_Id as pId,p.`prod_Desc` as prodDesc,SUM(bd.amount) AS pAmount FROM `bill_branch` bb ,`bill_info` bi, `bill_details` bd, `product` p, `category` c, `prod_subcat` ps, `subcategory` s, `subcat_category` sc, `branch_client` bc WHERE bb.`bill_Info_Id` =bi.`bill_Info_Id` AND bi.`bill_Info_Id` = bd.`bill_Info_Id` AND ps.`product_Id`= bd.`prod_Id` AND bd.`prod_Id`=p.`product_Id` AND ps.`subCategory_Id`= sc.`subcategory_Id` AND sc.`category_Id`=c.`category_Id` AND s.`subCategory_Id`= sc.`subcategory_Id` AND bb.`branch_Id`=bc.`branch_Id` AND bc.`client_Id`='"+clientId+"' AND bc.`branch_Id`='"+branchId+"' AND  DATE(bi.bill_Date)='"+sdate+"' GROUP BY bd.`prod_Id`";
		 	exBeans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<saleReportBean>(saleReportBean.class));
	      
			}
		  		
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return exBeans;
	}
	@Override
	public List<saleReportBean> saleSubCatSearch(String branchId, String sdate, String clientId) {
		List<saleReportBean> exBeans=new ArrayList<saleReportBean>();
		try
		{			
		 	 sql="SELECT c.`category_Id` as cId,s.`subCategory_Id` as sId,s.`subCategory_Desc` AS subCatDesc,SUM(bd.amount) AS sAmount FROM `bill_branch` bb ,`bill_info` bi, `bill_details` bd, `product` p, `category` c, `prod_subcat` ps, `subcategory` s, `subcat_category` sc, `branch_client` bc WHERE bb.`bill_Info_Id` =bi.`bill_Info_Id` AND bi.`bill_Info_Id` = bd.`bill_Info_Id` AND ps.`product_Id`= bd.`prod_Id` AND bd.`prod_Id`=p.`product_Id` AND ps.`subCategory_Id`= sc.`subcategory_Id` AND sc.`category_Id`=c.`category_Id` AND s.`subCategory_Id`= sc.`subcategory_Id` AND bb.`branch_Id`=bc.`branch_Id` AND bc.`client_Id`='"+clientId+"' AND bc.`branch_Id`='"+branchId+"' AND  DATE(bi.bill_Date)='"+sdate+"' GROUP BY s.`subCategory_Desc`";
		 	exBeans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<saleReportBean>(saleReportBean.class));
	      
			}
		  		
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return exBeans;
	}
	@Override
	public List<saleReportBean> saleCatSearch(String branchId, String sdate, String clientId) {
		List<saleReportBean> exBeans=new ArrayList<saleReportBean>();
		try
		{			
		 	 sql="SELECT c.`category_Id` as cId,c.`category_Desc` AS catDesc,SUM(bd.amount) AS cAmount FROM `bill_branch` bb ,`bill_info` bi, `bill_details` bd, `product` p, `category` c, `prod_subcat` ps, `subcategory` s, `subcat_category` sc, `branch_client` bc WHERE bb.`bill_Info_Id` =bi.`bill_Info_Id` AND bi.`bill_Info_Id` = bd.`bill_Info_Id` AND ps.`product_Id`= bd.`prod_Id` AND bd.`prod_Id`=p.`product_Id` AND ps.`subCategory_Id`= sc.`subcategory_Id` AND sc.`category_Id`=c.`category_Id` AND s.`subCategory_Id`= sc.`subcategory_Id` AND bb.`branch_Id`=bc.`branch_Id` AND bc.`client_Id`='"+clientId+"' AND bc.`branch_Id`='"+branchId+"' AND  DATE(bi.bill_Date)='"+sdate+"' GROUP BY c.`category_Id`";
		 	exBeans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<saleReportBean>(saleReportBean.class));
	      
			}
		  		
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return exBeans;
	}
}
