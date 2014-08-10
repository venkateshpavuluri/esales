/**
 * 
 */
package com.mnt.esales.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.esales.bean.BillBean;
import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.Stock;
import com.mnt.esales.dao.BillDao;
import com.mnt.esales.dao.CustomIdDao;

/**
 * @author madhav
 *
 */
@Service("billService")
public class BillServiceImpl implements BillService{
	
	boolean flag=true;
	@Autowired
	BillDao billDao;

	List<BillDetails> listBean;
	@Autowired
	CustomIdDao custIdDao;
	

	@Override
	public List<BillDetails> getBillingDetails(String productCode, String branchId,
			float quantity) {
		
		
		List<BillDetails> bill=billDao.getBillingDetails(productCode, branchId, quantity);
		
		
		return bill;
	}

	@Override
	/*@Transactional("transactionManager")*/
	public boolean saveBill(BillBean bill) {
		
			try {
				flag=billDao.saveBill(bill);
			} catch (Exception e) {
				e.printStackTrace();
				flag=false;
			}
		
		return flag;
	}


	@Override
    public List<Stock> billEdit(String prodId) {
	List<Stock> billBean = null;
	try {
		billBean = billDao.edit(prodId);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return billBean;
    }


		
	@Override
	    public boolean billUpdate(Object object) {
		try {
		    flag = billDao.billUpdate(object);
		} catch (Exception e) {
		    flag = false;
		    e.printStackTrace();
		}
		return flag;
	    }
	@Override
    public boolean billDelete(String stockId) {
	try {
	    flag = billDao.billDelete(stockId);
	} catch (Exception e) {
	    flag = false;
	    e.printStackTrace();
	}
	return flag;
    }

	@Override
	public String getUser(String userId) {
		
		
		return billDao.getUser(userId);
	}

	@Override
	public boolean checkForProductId(String id,String branchId) {
		
		return billDao.checkForProductId(id,branchId);
	}

	@Override
	public int checkForQuantity(String branchId, String prodId, int quantity) {
		
		return billDao.checkForQuantity(branchId, prodId, quantity);
	}

}
