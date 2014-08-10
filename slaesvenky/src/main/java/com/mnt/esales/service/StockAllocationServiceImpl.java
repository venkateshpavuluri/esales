/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockEntryUpdate;
import com.mnt.esales.dao.StockAllocationDao;

/**
 * @author venkateshp
 *
 */
@Service("stockAllocationService")
public class StockAllocationServiceImpl implements StockAllocationService {
@Autowired
private StockAllocationDao stockAllocationDao;
	@Override
	public List<StockAllocation> getProducts(String clientId) {
		return stockAllocationDao.getProducts(clientId);
	}
	@Override
	public List<StockAllocation> getBranchDetails(String clientId) {
		return stockAllocationDao.getBranchDetails(clientId);
	}

	public boolean saveStockAllocations(List<StockAllocation> stockAllocations,List<StockEntryUpdate> entryUpdates) {
		boolean flag=false;
	try
		{
		flag=stockAllocationDao.saveStockAllocations(stockAllocations,true);
		if(flag)
		{
			flag=stockAllocationDao.updateStkEntrysQuantity(entryUpdates);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	return flag;
	}
@Override
public StockAllocation getQuantity(String branchId, String stockId, String productId) {
	// TODO Auto-generated method stub
	return stockAllocationDao.getQuantity(branchId, stockId, productId);
}
@Override
public boolean deleteStockAllocc(String clientId) {
	// TODO Auto-generated method stub
	return stockAllocationDao.deleteStockAllocc(clientId);
}

}
