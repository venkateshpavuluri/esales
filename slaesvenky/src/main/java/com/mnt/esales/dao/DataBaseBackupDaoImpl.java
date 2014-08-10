/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.bean.ExpensesBean;
import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.bean.Stock;
import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockBean;
import com.mnt.esales.bean.SubCategoryBean;
import com.mnt.esales.bean.UserBean;
import com.mnt.esales.daosupport.MntDaoSupport;
import com.mnt.esales.marshaling.Billing;
import com.mnt.esales.marshaling.Branch;
import com.mnt.esales.marshaling.BranchClient;
import com.mnt.esales.marshaling.Category;
import com.mnt.esales.marshaling.CategoryDept;
import com.mnt.esales.marshaling.Client;
import com.mnt.esales.marshaling.ClientCategory;
import com.mnt.esales.marshaling.Esales;
import com.mnt.esales.marshaling.Expenses;
import com.mnt.esales.marshaling.ExpensesInfo;
import com.mnt.esales.marshaling.Permissions;
import com.mnt.esales.marshaling.Product;
import com.mnt.esales.marshaling.StockAllocations;
import com.mnt.esales.marshaling.StockEntry;
import com.mnt.esales.marshaling.SubCategory;
import com.mnt.esales.marshaling.UserDetails;
import com.mnt.esales.marshaling.UserRole;

/**
 * @author venkateshp
 *
 */
@Repository("dataBaseBackupDao")
public class DataBaseBackupDaoImpl extends MntDaoSupport implements DataBaseBackupDao {
	private Logger logger=Logger.getLogger(DataBaseBackupDaoImpl.class);
String sql=null;
@Autowired
HttpSession session;
@Override
/*It is for marshaling dont delete this method */
public List<ProductBean> getProducts() {
	List<ProductBean > products=null;
	try
	{
		//sql="select p.product_Id as productId,p.prod_Desc as productDesc,p.prod_Display as productDisplay from product p";
		sql="SELECT p.product_Id AS productId ,p.prod_Desc AS productName,p.prod_Display AS display FROM client_product cp JOIN product p ON p.product_Id=cp.product_Id WHERE p.flag=0 and cp.client_Id='"+(String)session.getAttribute("clientId")+"'";
		products=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<ProductBean>(ProductBean.class));		
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return products;
}
@Override
public List<CategoryBean> getCategorys() {
	// TODO Auto-generated method stub
	List<CategoryBean> categories=null;
	try
	{
		//sql="select c.category_Id as categoryId,c.category_Desc as categoryDesc,c.cat_Display as categoryDisplay from category c";
		sql="SELECT c.category_Id AS categoryId,C.category_Desc AS categoryDesc,C.cat_Display AS catDisplay  AS categoryDisplay FROM client_category cc JOIN category c ON c.category_Id=cc.category_Id  WHERE c.flag=0 and cc.client_Id='"+(String)session.getAttribute("clientId")+"'";
		categories=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<CategoryBean>(CategoryBean.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return categories;
}
@Override
public List<SubCategoryBean> getSubCategorys() {
	// TODO Auto-generated method stub
	List<SubCategoryBean> subCategories=null;
	try
	{
	//sql="select s.subCategory_Id as subCategoryId,s.subCategory_Desc as subCategoryDesc,s.subCat_Display as subCatDisplay from subcategory s";	
	sql="SELECT ss.subCategory_Id AS subCategoryId,ss.subCategory_Desc AS subCategoryDesc,ss.subCat_Display AS subCatDisplay  FROM  client_subcategory cs  JOIN subcategory ss ON ss.subCategory_Id=cs.subcat_Id WHERE ss.flag=0 and  cs.client_Id='"+(String)session.getAttribute("clientId")+"'";
	subCategories=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<SubCategoryBean>(SubCategoryBean.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return subCategories;
}
@Override
public List<BillInfo> getBillings(String branchId) {
	// TODO Auto-generated method stub
	List<BillInfo> billings=null;
	try
	{
		sql="SELECT a.bill_Info_Id AS billId,a.bill_Date AS billDate,a.mobile_No AS mobileNo,a.total_mrp AS totalMrp,a.total_rate AS totalRate,a.total_Discount AS totalDiscount,a.total_tax as totaltax,a.net_Amt as netAmt,a.paid_by_Cash as paidbyCash,a.paid_by_Card as paidbyCard,a.card_No as cardNo,a.paid_by_Cheque as paidbyCheque,a.cheque_No as chequeNo,a.total_Payment as totalPayment,a.return_Change as returnChange,a.total_Quantiy as totalQuantiy,a.bill_Status as billStatus FROM bill_info a JOIN bill_branch bb ON bb.bill_Info_Id=a.bill_Info_Id WHERE a.salesflag=0 and bb.branch_Id='"+branchId+"'";
		billings=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<BillInfo>(BillInfo.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return billings;
}
@Override
public List<ExpensesBean > getExpenses(String branchId) {
	List<ExpensesBean > expenses=null;
	try
	{
		sql="SELECT e.expenses_Id as expenses_Id,e.description as description,e.amount as amount,e.billno as billno FROM expense_details e JOIN expenses_branch eb ON eb.expenses_Id=e.expenses_Id WHERE e.salesflag=0 and eb.branch_Id='"+branchId+"'";
		expenses=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<ExpensesBean>(ExpensesBean.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return expenses;
}
@Override
public List<com.mnt.esales.bean.Permissions > getPermissions() {
	// TODO Auto-generated method stub
	List<com.mnt.esales.bean.Permissions > permissions=null;
	try
	{
		sql="select p.permissions_Id as permissionsId,p.user_Id as userId,p.branch_Id as branchId,p.client_Id as clientId from permissions p where p.flag=0 and p.client_Id='"+(String)session.getAttribute("clientId")+"'";
		permissions=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<com.mnt.esales.bean.Permissions >(com.mnt.esales.bean.Permissions .class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return permissions;
}
@Override
public List<UserBean > getUsers() {
	// TODO Auto-generated method stub
	List<UserBean> userDetails=null;
	try
	{
		sql="select u.user_Id as userId,u.user_Name as username,u.password as password,u.confirm_password as password,u.hint_question as hintq,u.hint_answer as hintans,u.firstname as firstname,u.lastname as lname,u.email_Id as email,u.contactno as contactno,u.address as address,u.status as status,u.role_Id as roleId,u.client_Id as clientId from users u where u.flag=0 and u.client_Id='"+(String)session.getAttribute("clientId")+"'";
		userDetails=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<UserBean >(UserBean.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return userDetails;
}
@Override
public List<StockBean > getStockEntry() {
	// TODO Auto-generated method stub
	List<StockBean> stockEntries=null;
	try
	{
		sql="SELECT s.stock_Id AS stock_Id,s.cost AS cost,s.discount AS discount,s.mrp AS mrp,s.`prod_Id` AS productId,s.quantity AS quantity,s.stock_Entry AS stockEntry  FROM   stock_client sc  JOIN stock s ON s.stock_Id=sc.stock_Id WHERE s.flag=0 and  sc.client_Id='C0002'";
		stockEntries=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<StockBean>(StockBean.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return stockEntries;
}
@Override
public List<StockAllocation > getStockAlocs() {
	// TODO Auto-generated method stub
	 List<StockAllocation> allocations=null;
	try
	{
		sql="select sb.branch_Id as branchId,sb.stock_Id as stockId,sb.prod_Id as prodId,sb.quantity as quantity,sb.client_Id as clientId from stock_branch sb where sb.flag=0 and sb.client_Id='"+(String)session.getAttribute("clientId")+"'";
		allocations=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<StockAllocation>(StockAllocation.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return allocations;
}
@Override
public List<BranchBean > getBranchesForClient() {
	// TODO Auto-generated method stub
	List<BranchBean> branchs=null;
	try
	{
		sql="SELECT b.branch_Id AS branchId,b.branch_Name AS branchName,b.city AS city,b.state AS state,b.pin AS pinNo,b.phone AS phone,b.contact_No AS contactNo,b.contact_Person AS contactPerson,b.address1 AS address1,b.address2 AS address2,b.client_Id AS clientId,b.status AS statuss,b.reg_Date AS regDate,b.branch_Theme AS branchTheme,b.landmark AS landmark   FROM branch_client bc JOIN branch b ON bc.branch_Id=b.branch_Id WHERE b.flag=0  AND bc.client_Id='"+(String)session.getAttribute("clientId")+"'";
		branchs=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<BranchBean>(BranchBean.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return branchs;
}
@Override
public String getBranches(String clientId) {
	// TODO Auto-generated method stub
	List<Branch> branchs=null;
	String branchId=null;
	try
	{
		//sql="SELECT bc.branch_Id FROM branch_client bc WHERE bc.`client_Id`='"+clientId+"'";
		sql="select p.branch_Id as branchId from permissions p where p.user_Id='"+clientId+"'";
		branchs=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<Branch>(Branch.class));
	if(branchs.size()!=0)
	{
		Branch branch=(Branch)branchs.get(0);
		branchId=branch.getBranchId();
		
	}
	}
	catch(Exception e )
	{
		logger.error(e.getMessage());
	}
	return branchId;
}
@Override
public List<StockBean> getStocksForBranch(String branchId) {
	List<StockBean>  stockEntries=null;
	try
	{
	sql="SELECT s.stock_Id AS stockId,s.cost AS cost,s.discount AS discount,s.mrp AS mrp,s.prod_Id AS productId,s.quantity AS quantity,s.stock_Entry AS stockEntry FROM   stock_branch sb JOIN stock s ON s.stock_Id=sb.stock_Id WHERE s.salesflag=0 AND sb.branch_Id='"+branchId+"'";	
	stockEntries=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<StockBean>(StockBean.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return stockEntries;
}
@Override
public boolean updateAllDbBackupFlags() {
	// TODO Auto-generated method stub
	boolean flag=false;
	try
	{
		String clientId=(String)session.getAttribute("clientId");
	String[] sql={"UPDATE product p  JOIN client_product cp ON cp.product_Id=p.product_Id  SET flag=1 WHERE cp.client_Id='"+clientId+"'","UPDATE category c JOIN client_category cc ON cc.category_Id=c.category_Id SET flag=1 WHERE cc.client_Id='"+clientId+"'","UPDATE subcategory sc JOIN client_subcategory cs ON cs.subcat_Id=sc.subCategory_Id SET flag=1 WHERE cs.client_Id='"+clientId+"'","UPDATE permissions p SET p.flag=1 WHERE p.client_Id='"+clientId+"'","UPDATE users u JOIN user_client uc ON uc.user_Id=u.user_Id SET u.flag=1 WHERE uc.client_Id='"+clientId+"'","UPDATE stock s JOIN stock_client sc ON sc.stock_Id=s.stock_Id SET s.flag=1 WHERE sc.client_Id='"+clientId+"'","UPDATE stock_branch sb SET sb.flag=1 WHERE sb.client_Id='"+clientId+"'","UPDATE branch b JOIN branch_client bc ON bc.branch_Id=b.branch_Id SET b.flag=1 WHERE bc.client_Id='"+clientId+"'"};
		getJdbcTemplate().batchUpdate(sql);
		flag=true;
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return flag;
}
@Override
public boolean updatesalesflags(String branchId) {
	// TODO Auto-generated method stub
	boolean flag=false;
	try
	{
		String[] sql={"UPDATE bill_info b JOIN bill_branch bb ON bb.bill_Info_Id=b.bill_Info_Id SET b.salesflag=1 WHERE bb.branch_Id='"+branchId+"'","UPDATE expense_details ed JOIN expenses_branch eb ON eb.expenses_Id=ed.expenses_Id SET ed.salesflag=1 WHERE eb.branch_Id='"+branchId+"'","UPDATE stock s JOIN stock_branch sb ON sb.stock_Id=s.stock_Id SET s.salesflag=1 WHERE sb.branch_Id='"+branchId+"'"};
	getJdbcTemplate().batchUpdate(sql);
	flag=true;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return flag;
}
@Override
public boolean updateServerDb(Object object) {
	// TODO Auto-generated method stub
	boolean flag=false;
	try
	{
		Esales  esales=(Esales)object;
		ClientInfoBean client=esales.getClient();
		Branch branch=(Branch)esales.getBranchs();
		UserDetails userDetails=(UserDetails)esales.getUserDetails();
		SubCategory subCategory=(SubCategory)esales.getSubCategories();
		Product product=(Product)esales.getProducts();
		StockEntry stockEntry=(StockEntry)esales.getStockEntrys();
		StockAllocations stockAllocations=(StockAllocations)esales.getStockAllocations();
		Permissions permissions=(Permissions)esales.getPermissions();
		
		
		
		
	}
	catch(Exception e ){
		logger.error(e.getMessage());
	}
	return flag;
}
@Override
public List<BranchClient> getBranchClients() {
	// TODO Auto-generated method stub
	String sql=null;
	List<BranchClient> branchClients=null;
	try
	{
		sql="SELECT bc.`branch_Id` as branchId FROM branch_client bc WHERE bc.`client_Id`='"+(String)session.getAttribute("clientId")+"'";
		branchClients=getJdbcTemplate().query(sql, new BeanPropertyRowMapper<BranchClient>(BranchClient.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return branchClients;
}
@Override
public List<UserRole> getUserRoles() {
	// TODO Auto-generated method stub
	List<UserRole> userRoles=null;
	try
	{
		sql="SELECT ur.user_Id AS uesrId,ur.role_Id AS roleId FROM `userroles` ur JOIN `users` u ON u.user_Id=ur.user_Id WHERE u.client_Id='"+(String)session.getAttribute("clientId")+"'";
		userRoles=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<UserRole>(UserRole.class));
	}
	catch(Exception e)
	{
		logger.error(e.getCause());
	}
	return userRoles;
}
@Override
public List<ClientCategory> getClientCat() {
	// TODO Auto-generated method stub
	List<ClientCategory> clientCategories=null;
	try
	{
		sql="SELECT cc.category_Id FROM `client_category` cc WHERE cc.client_Id='"+(String)session.getAttribute("clientId")+"'";
		clientCategories=getJdbcTemplate().query(sql, new BeanPropertyRowMapper<ClientCategory>(ClientCategory.class));
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return clientCategories;
}
@Override
public List<CategoryDept> getCategoryDept() {
	// TODO Auto-generated method stub
	try
	{
		sql="";
	}
	catch(Exception e)
	{
		logger.error(e.getMessage());
	}
	return null;
}

}
