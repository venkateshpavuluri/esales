/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.BillInfo;

/**
 * @author madhav
 *
 */
public interface BillSearchService {
	
	public List<BillDetails> getBillDetailsOfBillInfo(BillInfo billInfo);
	public BillInfo findBillInfobyBillNo(String billNo);

}
