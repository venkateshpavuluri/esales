package com.mnt.esales.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.ExpenseReportBean;
import com.mnt.esales.bean.ReportBean;
import com.mnt.esales.bean.StockReportBean;
import com.mnt.esales.bean.saleReportBean;
import com.mnt.esales.dao.ReportDao;
@Service("reportService")
public class ReportServiceImpl implements ReportService {
	@Autowired
	ReportDao reportDao;
	List<ReportBean> rList=new ArrayList<ReportBean>();
	List<ExpenseReportBean> exList=new ArrayList<ExpenseReportBean>();
	List<StockReportBean> stList=new ArrayList<StockReportBean>();
	List<saleReportBean> saleList=new ArrayList<saleReportBean>();
	@Override
	public List<ReportBean> billInfoSearch(String branchId, String sdate, String clientId) {
	
		rList= reportDao.billInfoSearch(branchId, sdate, clientId);
		// TODO Auto-generated method stub
		return rList;
	}
	@Override
	public List<ReportBean> billDetailSearch(String branchId, String sdate, String clientId) {
		rList= reportDao.billDetailSearch(branchId, sdate, clientId);
		// TODO Auto-generated method stub
		return rList;
	}
	@Override
	public List<ExpenseReportBean> expenseDetailSearch(String branchId, String sdate, String clientId) {
		exList= reportDao.expenseDetailSearch(branchId, sdate, clientId);
			// TODO Auto-generated method stub
		return exList;
	}
	@Override
	public List<StockReportBean> stockDetailSearch(String branchId, String sdate, String clientId) {
		stList= reportDao.stockDetailSearch(branchId, sdate, clientId);
		// TODO Auto-generated method stub
		return stList;
	}
	@Override
	public List<saleReportBean> saleProdSearch(String branchId, String sdate, String clientId) {
		saleList= reportDao.saleProdSearch(branchId, sdate, clientId);
		// TODO Auto-generated method stub
		return saleList;
	}
	@Override
	public List<saleReportBean> saleSubCatSearch(String branchId, String sdate, String clientId) {
		saleList= reportDao.saleSubCatSearch(branchId, sdate, clientId);
		// TODO Auto-generated method stub
		return saleList;
	}
	public List<saleReportBean> saleCatSearch(String branchId, String sdate, String clientId) {
		saleList= reportDao.saleCatSearch(branchId, sdate, clientId);
		// TODO Auto-generated method stub
		return saleList;
	}
}
