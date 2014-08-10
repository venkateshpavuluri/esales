/**
 * 
 */
package com.mnt.esales.bean;

/**
 * @author yogi
 * 
 */
public class StockReturnBean {
	private String branchId;
	private String stockId;
	private String productCode;
	private String returnStock;
	private String returnEntry;
	private String userId;
	private String clientId;
	private String productName;
	private String inStockQuantity;
	private String productId;
	private String stock;
	
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the inStockQuantity
	 */
	public String getInStockQuantity() {
		return inStockQuantity;
	}
	/**
	 * @param inStockQuantity the inStockQuantity to set
	 */
	public void setInStockQuantity(String inStockQuantity) {
		this.inStockQuantity = inStockQuantity;
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
	
	public String getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the returnStock
	 */
	public String getReturnStock() {
		return returnStock;
	}
	/**
	 * @param returnStock the returnStock to set
	 */
	public void setReturnStock(String returnStock) {
		this.returnStock = returnStock;
	}
	/**
	 * @return the returnEntry
	 */
	public String getReturnEntry() {
		return returnEntry;
	}
	/**
	 * @param returnEntry the returnEntry to set
	 */
	public void setReturnEntry(String returnEntry) {
		this.returnEntry = returnEntry;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
