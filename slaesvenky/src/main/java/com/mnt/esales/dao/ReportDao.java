package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.ExpenseReportBean;
import com.mnt.esales.bean.ReportBean;
import com.mnt.esales.bean.StockReportBean;
import com.mnt.esales.bean.saleReportBean;

public interface ReportDao {
	public List<ReportBean> billInfoSearch(String branchId, String sdate,String clientId);
	public List<ReportBean> billDetailSearch(String branchId, String sdate,String clientId);
	public List<ExpenseReportBean> expenseDetailSearch(String branchId, String sdate,String clientId);
	public List<StockReportBean> stockDetailSearch(String branchId, String sdate,String clientId);
	public List<saleReportBean> saleProdSearch(String branchId, String sdate,String clientId);
	public List<saleReportBean> saleSubCatSearch(String branchId, String sdate,String clientId);
	public List<saleReportBean> saleCatSearch(String branchId, String sdate,String clientId);
}
