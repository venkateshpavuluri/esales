package com.mnt.esales.bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

public class BillBean {
	private String productId;
	private String productName;
	private Double cost;
	private Float quantity;
	private float mrp;
	private String branchId;
	private String userId;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the mrp
	 */
	public float getMrp() {
		return mrp;
	}
	/**
	 * @param mrp the mrp to set
	 */
	public void setMrp(float mrp) {
		this.mrp = mrp;
	}
	private String billId;
	private String billDate;
	private String mobileNo;
	private String totalMrp;
	private String totalDiscount;
	private String totaltax;
	private String totalQuantiy;
	private String totalRate;
	private String netAmt;
	private String paidbyCash;
	private String paidbyCard;
	private String cardNo;
	private String paidbyCheque;
	private String chequeNo;
	private String totalPayment;
	private String returnChange;
	private String billStatus;
	private String paymentType;
	
	
	@SuppressWarnings("unchecked")
	private List<BillDetails> billDetails=new ArrayList<BillDetails>();


	
	
	
	
	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}
	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	/**
	 * @return the totalRate
	 */
	public String getTotalRate() {
		return totalRate;
	}
	/**
	 * @param totalRate the totalRate to set
	 */
	public void setTotalRate(String totalRate) {
		this.totalRate = totalRate;
	}
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}



	
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	
	
	/**

	 * @return the netAmt
	 */
	public String getNetAmt() {
		return netAmt;
	}
	/**
	 * @param netAmt the netAmt to set
	 */
	public void setNetAmt(String netAmt) {
		this.netAmt = netAmt;
	}
	/**
	 * @return the paidbyCash
	 */
	public String getPaidbyCash() {
		return paidbyCash;
	}
	/**
	 * @param paidbyCash the paidbyCash to set
	 */
	public void setPaidbyCash(String paidbyCash) {
		this.paidbyCash = paidbyCash;
	}
	/**
	 * @return the paidbyCard
	 */
	public String getPaidbyCard() {
		return paidbyCard;
	}
	/**
	 * @param paidbyCard the paidbyCard to set
	 */
	public void setPaidbyCard(String paidbyCard) {
		this.paidbyCard = paidbyCard;
	}
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the paidbyCheque
	 */
	public String getPaidbyCheque() {
		return paidbyCheque;
	}
	/**
	 * @param paidbyCheque the paidbyCheque to set
	 */
	public void setPaidbyCheque(String paidbyCheque) {
		this.paidbyCheque = paidbyCheque;
	}
	/**
	 * @return the chequeNo
	 */
	public String getChequeNo() {
		return chequeNo;
	}
	/**
	 * @param chequeNo the chequeNo to set
	 */
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	/**
	 * @return the totalPayment
	 */
	public String getTotalPayment() {
		return totalPayment;
	}
	/**
	 * @param totalPayment the totalPayment to set
	 */
	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}
	/**
	 * @return the returnChange
	 */
	public String getReturnChange() {
		return returnChange;
	}
	/**
	 * @param returnChange the returnChange to set
	 */
	public void setReturnChange(String returnChange) {
		this.returnChange = returnChange;
	}
	/**
	 * @return the total_Quantiy
	 */
	
	/**
	 * @return the totalMrp
	 */
	public String getTotalMrp() {
		return totalMrp;
	}
	/**
	 * @return the totalQuantiy
	 */
	public String getTotalQuantiy() {
		return totalQuantiy;
	}
	/**
	 * @param totalQuantiy the totalQuantiy to set
	 */
	public void setTotalQuantiy(String totalQuantiy) {
		this.totalQuantiy = totalQuantiy;
	}
	/**
	 * @return the billStatus
	 */
	public String getBillStatus() {
		return billStatus;
	}
	/**
	 * @param billStatus the billStatus to set
	 */
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	/**
	 * @param totalMrp the totalMrp to set
	 */
	public void setTotalMrp(String totalMrp) {
		this.totalMrp = totalMrp;
	}
	/**
	 * @return the totalDiscount
	 */
	public String getTotalDiscount() {
		return totalDiscount;
	}
	/**
	 * @param totalDiscount the totalDiscount to set
	 */
	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	/**
	 * @return the totaltax
	 */
	public String getTotaltax() {
		return totaltax;
	}
	/**
	 * @param totaltax the totaltax to set
	 */
	public void setTotaltax(String totaltax) {
		this.totaltax = totaltax;
	}
	/**
	 * @return the billDate
	 */
	public String getBillDate() {
		return billDate;
	}
	/**
	 * @param billDate the billDate to set
	 */
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	/**
	 * @return the billId
	 */
	public String getBillId() {
		return billId;
	}
	/**
	 * @param billId the billId to set
	 */
	public void setBillId(String billId) {
		this.billId = billId;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @return the billDetails
	 */
	public List<BillDetails> getBillDetails() {
		return billDetails;
	}
	/**
	 * @param billDetails the billDetails to set
	 */
	public void setBillDetails(List<BillDetails> billDetails) {
		this.billDetails = billDetails;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(Double cost) {
		this.cost = cost;
	}
	/**
	 * @return the quantity
	 */
	public Float getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
	
	
	

}
