/**
 * 
 */
package com.mnt.esales.bean;

import java.io.Serializable;

/**
 * @author madhav
 *
 */
public class BillDetails implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String billDetailsId;
	private String prodId;
	private String prodDesc;
	private double rate;
	private float quantity;
	private float discount;
	private float vat;
	private double amount;
	private double mrp;
	
	private String stockId;
	private String stockEntry;
	
	
	
	
	/**
	 * @return the stockEntry
	 */
	public String getStockEntry() {
		return stockEntry;
	}
	/**
	 * @param stockEntry the stockEntry to set
	 */
	public void setStockEntry(String stockEntry) {
		this.stockEntry = stockEntry;
	}
	/**
	 * @return the stockId
	 */
	public String getStockId() {
		return stockId;
	}
	/**
	 * @param stockId the stockId to set
	 */
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	/**
	 * @return the mrp
	 */
	public double getMrp() {
		return mrp;
	}
	/**
	 * @param mrp the mrp to set
	 */
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	/**
	 * @return the billDetailsId
	 */
	public String getBillDetailsId() {
		return billDetailsId;
	}
	/**
	 * @param billDetailsId the billDetailsId to set
	 */
	public void setBillDetailsId(String billDetailsId) {
		this.billDetailsId = billDetailsId;
	}
	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return prodId;
	}
	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	/**
	 * @return the prodDesc
	 */
	public String getProdDesc() {
		return prodDesc;
	}
	/**
	 * @param prodDesc the prodDesc to set
	 */
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}
	/**
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the discount
	 */
	public float getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	/**
	 * @return the vat
	 */
	public float getVat() {
		return vat;
	}
	/**
	 * @param vat the vat to set
	 */
	public void setVat(float vat) {
		this.vat = vat;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
}
