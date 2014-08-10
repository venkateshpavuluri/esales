/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.ExpensesBean;
import com.mnt.esales.dao.ExpensesDao;

/**
 * @author srinivas
 * 
 */
@Service("expensesService")
public class ExpensesServiceImpl implements ExpensesService {
	@Autowired
	ExpensesDao expensesDao;
	boolean flag;

	@Override
	public boolean ExpensesSave(Object object,boolean status) {
		try {
			flag = expensesDao.ExpensesSave(object,status);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;

	}

	@Override
	public boolean ExpensesUpdate(Object object) {
		try {
			flag = expensesDao.ExpensesUpdate(object);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean ExpensesDelete(String expenses_Id) {
		try {
			flag = expensesDao.ExpensesDelete(expenses_Id);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<ExpensesBean> ExpensesSearch() {
		List<ExpensesBean> exBean = null;
		try {
			exBean = expensesDao.ExpensesSearch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exBean;
	}

	@Override
	public List<ExpensesBean> ExpensesEdit(String expenses_Id, String id) {
		List<ExpensesBean> expBean = null;
		try {
			expBean = expensesDao.ExpensesEdit(expenses_Id, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expBean;
	}

	@Override
	public List<ExpensesBean> ExpensesSearchWithId(String branchId) {
		List<ExpensesBean> exBean = null;
		try {
			exBean = expensesDao.ExpensesSearchWithId(branchId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exBean;
	}

}
