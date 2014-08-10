/**
 * 
 */
package com.mnt.esales.bean;

import java.util.List;

/**
 * @author devi
 *
 */
public class SubCatListReportBean {
	private String catId;
	private String subCatId;
	private String subCatDesc;
	private String subCatAmount;
	
	List<ProdListReportBean> plist;

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(String subCatId) {
		this.subCatId = subCatId;
	}

	public String getSubCatDesc() {
		return subCatDesc;
	}

	public void setSubCatDesc(String subCatDesc) {
		this.subCatDesc = subCatDesc;
	}

	

	public String getSubCatAmount() {
		return subCatAmount;
	}

	public void setSubCatAmount(String subCatAmount) {
		this.subCatAmount = subCatAmount;
	}

	public List<ProdListReportBean> getPlist() {
		return plist;
	}

	public void setPlist(List<ProdListReportBean> plist) {
		this.plist = plist;
	}
	
	
	

}
