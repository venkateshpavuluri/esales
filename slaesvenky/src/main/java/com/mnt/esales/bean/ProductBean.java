/**
 * @Copyright MNTSOFT
 */
package com.mnt.esales.bean;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.json.simple.JSONAware;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

/**
 * @author Naresh
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public  class ProductBean implements Serializable,JSONStreamAware {
	private static final long serialVersionUID = 1L;
	private String productId;
	private String productName;
	private boolean display;
	private String departmentId;
	private String categoryId;
	private String subCategoryId;
	private String categoryName;
	private String subCategoryName;
	private String productCode;
	private String productNameSearch;
	
	/**
	 * @return the productNameSearch
	 */
	public String getProductNameSearch() {
		return productNameSearch;
	}
	/**
	 * @param productNameSearch the productNameSearch to set
	 */
	public void setProductNameSearch(String productNameSearch) {
		this.productNameSearch = productNameSearch;
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
	 * @return the subCategoryName
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}
	/**
	 * @param subCategoryName the subCategoryName to set
	 */
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}
	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
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
	 * @return the display
	 */
	public boolean isDisplay() {
		return display;
	}
	/**
	 * @param display the display to set
	 */
	public void setDisplay(boolean display) {
		this.display = display;
	}
	@SuppressWarnings("unchecked")
	public void writeJSONString(Writer out) throws IOException {
		// TODO Auto-generated method stub
		@SuppressWarnings("rawtypes")
		LinkedHashMap obj = new LinkedHashMap();
		obj.put("categoryId",categoryId);
		obj.put("categoryName",categoryName);
		obj.put("subCategoryId",subCategoryId);
		obj.put("subCategoryName",subCategoryName);
		JSONValue.writeJSONString(obj, out);
	}
}
