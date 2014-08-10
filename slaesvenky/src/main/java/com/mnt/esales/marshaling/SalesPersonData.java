/**
 * 
 */
package com.mnt.esales.marshaling;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.ExpensesBean;
import com.mnt.esales.bean.Stock;
import com.mnt.esales.bean.StockBean;

/**
 * @author venkateshp
 *
 */
@XmlRootElement(name="esales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesPersonData {
	@XmlElement(name="branch")
	private BranchBean branch;
	@XmlElementWrapper(name="billings")
	@XmlElement(name="billing")
	private List<BillInfo > billings;
	@XmlElementWrapper(name="expenses")
	@XmlElement(name="expense")
	private List<ExpensesBean> expenses;
	@XmlElementWrapper(name="stocks")
	@XmlElement(name="stock")
	private List<StockBean > stocks;
	/**
	 * @return the branch
	 */
	public BranchBean getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(BranchBean branch) {
		this.branch = branch;
	}
	/**
	 * @return the billings
	 */
	public List<BillInfo> getBillings() {
		return billings;
	}
	/**
	 * @param billings the billings to set
	 */
	public void setBillings(List<BillInfo> billings) {
		this.billings = billings;
	}
	
	/**
	 * @return the stocks
	 */
	public List<StockBean> getStocks() {
		return stocks;
	}
	/**
	 * @param stocks the stocks to set
	 */
	public void setStocks(List<StockBean> stocks) {
		this.stocks = stocks;
	}
	/**
	 * @return the expenses
	 */
	public List<ExpensesBean> getExpenses() {
		return expenses;
	}
	/**
	 * @param expenses the expenses to set
	 */
	public void setExpenses(List<ExpensesBean> expenses) {
		this.expenses = expenses;
	}


	
	
}
