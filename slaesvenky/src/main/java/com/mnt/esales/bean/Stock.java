/**
 * 
 */
package com.mnt.esales.bean;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author madhav
 * 
 */
public class Stock {

	// Instance Variables
	private String billDetailsId;

	private String stockId;
	private String productId;
	private Double cost;
	private Float quantity;
	private double mrp;
	private String stockEntry;
	private float discount;
	private float vat;
	private String clientId;
	// For child Calculation
	@XmlTransient
	private ProductBean productDetails;
	private double amount;

	// gettes and settrs Methods

	/**
	 * @return the billDetailsId
	 */
	public String getBillDetailsId() {
		return billDetailsId;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @param billDetailsId
	 *            the billDetailsId to set
	 */
	public void setBillDetailsId(String billDetailsId) {
		this.billDetailsId = billDetailsId;
	}

	/**
	 * @return the stockId
	 */
	public String getStockId() {
		return stockId;
	}

	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
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
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the mrp
	 */
	public double getMrp() {
		return mrp;
	}

	/**
	 * @param mrp
	 *            the mrp to set
	 */
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	/**
	 * @return the discount
	 */
	public float getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
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
	 * @param vat
	 *            the vat to set
	 */
	public void setVat(float vat) {
		this.vat = vat;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the stockEntry
	 */
	public String getStockEntry() {
		return stockEntry;
	}

	/**
	 * @param stockEntry
	 *            the stockEntry to set
	 */
	public void setStockEntry(String stockEntry) {
		this.stockEntry = stockEntry;
	}

	/**
	 * @return the productDetails
	 */
	public ProductBean getProductDetails() {
		return productDetails;
	}

	/**
	 * @param productDetails
	 *            the productDetails to set
	 */
	public void setProductDetails(ProductBean productDetails) {
		this.productDetails = productDetails;
	}

}
