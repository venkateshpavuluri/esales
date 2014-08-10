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
@XmlAccessorType(XmlAccessType.FIELD)
public class SubCategory {
	private String subCategoryId;
	private String subCategoryDesc;
	private String subCatDisplay;
	/**
	 * @return the subCategoryId
	 */
	public String getSubCategoryId() {
		return subCategoryId;
	}
	/**
	 * @param subCategoryId the subCategoryId to set
	 */
	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	/**
	 * @return the subCategoryDesc
	 */
	public String getSubCategoryDesc() {
		return subCategoryDesc;
	}
	/**
	 * @param subCategoryDesc the subCategoryDesc to set
	 */
	public void setSubCategoryDesc(String subCategoryDesc) {
		this.subCategoryDesc = subCategoryDesc;
	}
	/**
	 * @return the subCatDisplay
	 */
	public String getSubCatDisplay() {
		return subCatDisplay;
	}
	/**
	 * @param subCatDisplay the subCatDisplay to set
	 */
	public void setSubCatDisplay(String subCatDisplay) {
		this.subCatDisplay = subCatDisplay;
	}
	
	

}
