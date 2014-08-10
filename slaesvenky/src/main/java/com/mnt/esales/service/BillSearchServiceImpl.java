/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.dao.BillDao;
import com.mnt.esales.dao.BillSearchDao;

/**
 * @author madhav
 *
 */
@Service("billSearchService")
public class BillSearchServiceImpl implements BillSearchService {
	@Autowired
	BillSearchDao billSearchDao;
	
	@Override
	public List<BillDetails> getBillDetailsOfBillInfo(BillInfo billInfo) {
		
		return billSearchDao.getBillDetailsOfBillInfo(billInfo);
	}

	@Override
	public BillInfo findBillInfobyBillNo(String billNo) {
		
		return billSearchDao.findBillInfobyBillNo(billNo);
	}

}
