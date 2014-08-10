/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.ExpensesBean;
import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.bean.Stock;
import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockBean;
import com.mnt.esales.bean.StockEntryUpdate;
import com.mnt.esales.bean.SubCategoryBean;
import com.mnt.esales.bean.UserBean;
import com.mnt.esales.dao.DataBaseBackupDao;
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
@Service("dbBackupService")
public class DbBackupServiceImpl implements DbBackupService {
@Autowired
DataBaseBackupDao dataBaseBackupDao;
	@Override
	public List<ProductBean > getProducts() {
		return dataBaseBackupDao.getProducts();
	}
	@Override
	public List<CategoryBean> getCategorys() {
		return dataBaseBackupDao.getCategorys();
	}
	@Override
	public List<SubCategoryBean > getSubCategorys() {
		return dataBaseBackupDao.getSubCategorys();
	}
	@Override
	public List<BillInfo> getBillings(String branchId) {
		return dataBaseBackupDao.getBillings(branchId);
	}
	@Override
	public List<ExpensesBean> getExpenses(String branchId) {
		return dataBaseBackupDao.getExpenses(branchId);
	}
	@Override
	public List<com.mnt.esales.bean.Permissions> getPermissions() {
		return dataBaseBackupDao.getPermissions();
	}
	@Override
	public List<UserBean > getUserDetails() {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.getUsers();
	}
	public List<StockBean > getStockEntry() {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.getStockEntry();
	}
	@Override
	public List<StockAllocation > getStockAlocs() {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.getStockAlocs();
	}
	@Override
	public List<BranchBean> getBranchesForClient() {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.getBranchesForClient();
	}
	@Override
	public String getBranches(String clientId) {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.getBranches(clientId);
	}
	@Override
	public List<StockBean> getStocksForBranch(String branchId) {
		// TODO Auto-generated method stub
		
		return dataBaseBackupDao.getStocksForBranch(branchId);
	}
	@Override
	public boolean updateAllDbBackupFlags() {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.updateAllDbBackupFlags();
	}
	@Override
	public boolean updatesalesflags(String branchId) {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.updatesalesflags(branchId);
	}
	@Override
	public boolean updateServerDb(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<BranchClient > getBranchClients() {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.getBranchClients();
	}
	@Override
	public List<ClientCategory> getClientCat() {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.getClientCat();
	}
	@Override
	public List<UserRole> getUserRoles() {
		// TODO Auto-generated method stub
		return dataBaseBackupDao.getUserRoles();
	}

}
