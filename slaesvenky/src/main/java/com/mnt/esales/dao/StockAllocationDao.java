/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockEntryUpdate;

/**
 * @author venkateshp
 * 
 */
public interface StockAllocationDao {
	public List<StockAllocation> getProducts(String clientId);

	public List<StockAllocation> getBranchDetails(String clientId);

	public boolean saveStockAllocations(List<StockAllocation> stockAllocations,boolean status);

	public StockAllocation getQuantity(String branchId, String stockId,
			String productId);

	public boolean deleteStockAllocc(String clientId);

	public boolean updateStkEntrysQuantity(List<StockEntryUpdate> entryUpdates);

}
