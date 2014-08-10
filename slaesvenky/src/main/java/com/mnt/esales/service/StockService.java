/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.StockBean;

/**
 * @author srinivas
 * 
 */
public interface StockService {
	public boolean stockSave(Object object,boolean status);

	public List<StockBean> getProductDetails(String clientId);
	
	public List<StockBean> stockSearch(String clientId);
	
	public List<StockBean> getTotalQuantity(String productId);

	public boolean stockUpdate(Object object);

	public boolean stockDelete(String stock_Id);

	public List<StockBean> stockSearch();

	public List<StockBean> stochSearchwithProduct(String product_Id);

	public List<StockBean> stockEdit(String stock_Id, String id);
}
