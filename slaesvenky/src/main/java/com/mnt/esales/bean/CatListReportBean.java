/**
 * 
 */
package com.mnt.esales.bean;

import java.util.List;

/**
 * @author devi
 *
 */
public class CatListReportBean {
	private String catId;
	private String catDesc;
	private String cAmount;
	
   List<SubCatListReportBean> slist;
  
   
   
public String getCatId() {
	return catId;
}
public void setCatId(String catId) {
	this.catId = catId;
}
public String getCatDesc() {
	return catDesc;
}
public void setCatDesc(String catDesc) {
	this.catDesc = catDesc;
}
public String getcAmount() {
	return cAmount;
}
public void setcAmount(String cAmount) {
	this.cAmount = cAmount;
}
public List<SubCatListReportBean> getSlist() {
	return slist;
}
public void setSlist(List<SubCatListReportBean> slist) {
	this.slist = slist;
}

 
}
