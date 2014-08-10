/**
 * 
 */
package com.mnt.esales.marshaling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * @author venkateshp
 *
 */
@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {
	private String productId;
	private String productDesc;
	private String productDisplay;
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
	 * @return the productDesc
	 */

	public String getProductDesc() {
		return productDesc;
	}
	/**
	 * @param productDesc the productDesc to set
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	/**
	 * @return the productDisplay
	 */

	public String getProductDisplay() {
		return productDisplay;
	}
	/**
	 * @param productDisplay the productDisplay to set
	 */
	public void setProductDisplay(String productDisplay) {
		this.productDisplay = productDisplay;
	}
}
