/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.BillBean;
import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.Stock;

/**
 * @author madhav
 *
 */
public interface BillService {

	public List<BillDetails> getBillingDetails(String productCode,String branchId,float quantity);
	public boolean saveBill(BillBean bill);
	public List<Stock> billEdit(String catId);
	public boolean billUpdate(Object object);
	 public boolean billDelete(String stockId);
	 public String getUser(String userId);
	 public boolean checkForProductId(String id ,String branchId);
	 public int checkForQuantity(String branchId,String prodId,int quantity);
	

}
