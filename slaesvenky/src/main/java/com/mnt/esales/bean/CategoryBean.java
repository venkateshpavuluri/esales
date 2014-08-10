/**
 * 
 */
package com.mnt.esales.bean;

import java.io.Serializable;

/**
 * @author yogi
 * 
 */
public class CategoryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String categoryId;
	private String categoryDesc;
	private String catDisplay;
	private String deptId;
	private String searchCategory;
	private String deptName;
	private int dupId;
	private String clientId;
	private String categoryCode;
	

	/**
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getDupId() {
		return dupId;
	}

	public void setDupId(int dupId) {
		this.dupId = dupId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSearchCategory() {
		return searchCategory;
	}

	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getCatDisplay() {
		return catDisplay;
	}

	public void setCatDisplay(String catDisplay) {
		this.catDisplay = catDisplay;
	}

}
