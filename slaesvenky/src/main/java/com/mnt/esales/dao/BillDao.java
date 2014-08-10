/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.BillBean;
import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.Stock;

/**
 * @author madhav
 *
 */
public interface BillDao {
	
	public List<BillDetails> getBillingDetails(String productCode,String branchId,float quantity);
	public boolean saveBill(BillBean bill);
	public List<Stock> edit(String stockId);
	public String getUser(String userId);
	public boolean billUpdate(Object object);
	public boolean billDelete(String stockId);
	public boolean checkForProductId(String id,String branchId);
	public int checkForQuantity(String branchId,String prodId,int quantity);
	public boolean saveBillInfo(BillInfo billInfo,boolean status);
}
