/**
 * @Copyright MNTSOFT
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.StockBean;

/**
 * @author Srinivas
 * 
 */
public interface StockDao {

	public boolean StockSave(Object object,boolean status);

	public List<StockBean> getProductDetails(String clientId);

	public boolean StockUpdate(Object object);

	public boolean StockDelete(String stock_Id);

	public List<StockBean> getTotalQuantity(String productId);

	public List<StockBean> stockSearch();


	public List<StockBean> stockSearch(String clientId);

	public List<StockBean> stochSearchwithProduct(String product_Id);

	public List<StockBean> stockEdit(String stock_Id, String id);

}
