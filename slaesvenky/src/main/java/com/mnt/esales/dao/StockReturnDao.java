package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockReturnBean;


public interface StockReturnDao {
	public List<StockReturnBean> getCurrentStock(String branchId, String clientId);
	public List<StockReturnBean> getReturnStock(String branchId, String clientId);
	public boolean saveStockReturn(  List<StockReturnBean> stockReturnBeans);
}
