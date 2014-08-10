/**
 * 
 */
package com.mnt.esales.bean;

/**
 * @author srinivas
 *
 */
public class ExpensesBean {
private String expenses_Id;
private String description;
private Double amount;
private String billno;
private String billdate;
private String total;
private String branchId;
private String totalAmt;
/**
 * @return the expenses_Id
 */
public String getExpenses_Id() {
	return expenses_Id;
}
/**
 * @return the totalAmt
 */
public String getTotalAmt() {
	return totalAmt;
}
/**
 * @param totalAmt the totalAmt to set
 */
public void setTotalAmt(String totalAmt) {
	this.totalAmt = totalAmt;
}
/**
 * @param expenses_Id the expenses_Id to set
 */
public void setExpenses_Id(String expenses_Id) {
	this.expenses_Id = expenses_Id;
}
/**
 * @return the description
 */
public String getDescription() {
	return description;
}
/**
 * @param description the description to set
 */
public void setDescription(String description) {
	this.description = description;
}
/**
 * @return the amount
 */

/**
 * @return the billno
 */
public String getBillno() {
	return billno;
}
/**
 * @return the amount
 */
public Double getAmount() {
	return amount;
}
/**
 * @param amount the amount to set
 */
public void setAmount(Double amount) {
	this.amount = amount;
}
/**
 * @param billno the billno to set
 */
public void setBillno(String billno) {
	this.billno = billno;
}
/**
 * @return the billdate
 */
public String getBilldate() {
	return billdate;
}
/**
 * @param billdate the billdate to set
 */
public void setBilldate(String billdate) {
	this.billdate = billdate;
}
/**
 * @return the total
 */
public String getTotal() {
	return total;
}
/**
 * @param total the total to set
 */
public void setTotal(String total) {
	this.total = total;
}
/**
 * @return the branchId
 */
public String getBranchId() {
	return branchId;
}
/**
 * @param branchId the branchId to set
 */
public void setBranchId(String branchId) {
	this.branchId = branchId;
}

}
