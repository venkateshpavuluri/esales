/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.ExpensesBean;
import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.bean.Stock;
import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockBean;
import com.mnt.esales.bean.SubCategoryBean;
import com.mnt.esales.bean.UserBean;
import com.mnt.esales.marshaling.Billing;
import com.mnt.esales.marshaling.Branch;
import com.mnt.esales.marshaling.BranchClient;
import com.mnt.esales.marshaling.Category;
import com.mnt.esales.marshaling.ClientCategory;
import com.mnt.esales.marshaling.Expenses;
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
public interface DbBackupService {

	public List<ProductBean> getProducts(); 
	public List<CategoryBean> getCategorys();
	public List<SubCategoryBean> getSubCategorys();
	public List<BillInfo> getBillings(String branchId);
	public List<ExpensesBean> getExpenses(String branchId);
	public List<com.mnt.esales.bean.Permissions> getPermissions();
	public List<UserBean> getUserDetails();
	public List<StockBean> getStockEntry();
	public List<StockAllocation > getStockAlocs();
	public List<BranchBean> getBranchesForClient();
	public String getBranches(String clientId);
	public List<StockBean> getStocksForBranch(String branchId);
	public List<BranchClient> getBranchClients();
	public List<ClientCategory > getClientCat() ;
	public List<UserRole> getUserRoles();
	public boolean updateAllDbBackupFlags();
	public boolean updatesalesflags(String branchId);
	public boolean updateServerDb(Object object);

}
