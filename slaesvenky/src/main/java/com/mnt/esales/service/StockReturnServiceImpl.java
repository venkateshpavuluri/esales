/**
 * 
 */
package com.mnt.esales.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.StockReturnBean;
import com.mnt.esales.dao.StockReturnDao;

/**
 * @author yogi
 * 
 */
@Service("stockReturnService")
public class StockReturnServiceImpl implements StockReturnService {
	@Autowired
	StockReturnDao stockReturnDao;

	@Override
	public List<StockReturnBean> getCurrentStock(String branchId,
			String clientId) {
		
		List<StockReturnBean> list = new ArrayList<StockReturnBean>();
		try {
			list = stockReturnDao.getCurrentStock(branchId, clientId);
		} catch (Exception de) {
			de.printStackTrace();
		}
		return list;
	}

	@Override
	public List<StockReturnBean> getReturnStock(String branchId, String clientId) {
		return null;
	}

	@Override
	public boolean saveStockReturn(List<StockReturnBean> stockReturnBeans) {
		
		return stockReturnDao.saveStockReturn(stockReturnBeans);
	}

}
