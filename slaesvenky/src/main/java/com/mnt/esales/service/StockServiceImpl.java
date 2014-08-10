package com.mnt.esales.service;
/*
 * srinivas
 */
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.StockBean;
import com.mnt.esales.dao.StockDao;

@Repository("stockService")	
public class StockServiceImpl implements StockService {
	@Autowired
	StockDao stockDao;
	boolean flag;
	
	@Override
	public boolean stockSave(Object object,boolean status) {
		
		try {
			flag = stockDao.StockSave(object,status);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	
	}

	@Override
	public boolean stockUpdate(Object object) {
		try {
			flag = stockDao.StockUpdate(object);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean stockDelete(String stock_Id) {
		try {
			flag = stockDao.StockDelete(stock_Id);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<StockBean> stockSearch() {
		List<StockBean> stBean = null;
		try {
			stBean = stockDao.stockSearch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stBean;
	}


	@Override
	public List<StockBean> stockEdit(String stock_Id, String id) {
		List<StockBean> stBean = null;
		try {
			stBean = stockDao.stockEdit(stock_Id, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stBean;
	}

	@Override
	public List<StockBean> stochSearchwithProduct(String product_Id) {
		List<StockBean> stBean = null;
		try {
			stBean = stockDao.stochSearchwithProduct(product_Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stBean;
	}

	@Override
	public List<StockBean> getProductDetails(String clientId) {
		List<StockBean> stBean = null;
		try {
			stBean = stockDao.getProductDetails(clientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stBean;
	}

	@Override
	public List<StockBean> stockSearch(String clientId) {
		List<StockBean> stBean = null;
		try {
			stBean = stockDao.stockSearch(clientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stBean;
	}

	@Override
	public List<StockBean> getTotalQuantity(String productId) {
		List<StockBean> stBean = null;
		try{
			stBean=stockDao.getTotalQuantity(productId);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return stBean;
	}

}
