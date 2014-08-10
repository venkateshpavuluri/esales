/**
 * 
 */
package com.mnt.esales.marshaling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author venkateshp
 *
 */
@XmlRootElement(name="category")
@XmlAccessorType(XmlAccessType.FIELD)
public class Category {
	private String categoryId;
	private String categoryDesc;
	private String categoryDisplay;
	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the categoryDesc
	 */
	public String getCategoryDesc() {
		return categoryDesc;
	}
	/**
	 * @param categoryDesc the categoryDesc to set
	 */
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	/**
	 * @return the categoryDisplay
	 */
	public String getCategoryDisplay() {
		return categoryDisplay;
	}
	/**
	 * @param categoryDisplay the categoryDisplay to set
	 */
	public void setCategoryDisplay(String categoryDisplay) {
		this.categoryDisplay = categoryDisplay;
	}
	

}
