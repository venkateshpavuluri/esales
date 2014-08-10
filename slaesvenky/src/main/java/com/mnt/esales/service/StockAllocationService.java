/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockEntryUpdate;

/**
 * @author venkateshp
 *
 */
public interface StockAllocationService {
	public List<StockAllocation> getProducts(String clientId);
	public List<StockAllocation> getBranchDetails(String clientId);
	public boolean saveStockAllocations(List<StockAllocation> stockAllocations,List<StockEntryUpdate> stockEntryUpdates);
	public StockAllocation getQuantity(String branchId,String stockId,String productId);
	public boolean deleteStockAllocc(String clientId);
}