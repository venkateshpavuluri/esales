package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.ExpensesBean;

public interface ExpensesDao {
	public boolean ExpensesSave(Object object,boolean status);
	
	public boolean ExpensesUpdate(Object object);
	
	public boolean ExpensesDelete(String expenses_Id);
	
	public List<ExpensesBean> ExpensesSearch();
	
	public List<ExpensesBean> ExpensesSearchWithId(String billNo);
	
	public List<ExpensesBean> ExpensesEdit(String expenses_Id,String id);
}
