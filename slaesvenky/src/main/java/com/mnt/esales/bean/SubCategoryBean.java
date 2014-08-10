/**
 * 
 */
package com.mnt.esales.bean;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.LinkedHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

/**
 * @author yogi
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SubCategoryBean implements Serializable, JSONStreamAware {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String subCategoryId;
    private String subCategoryDesc;
    private String deptId;
    private String categoryDesc;
    private String categoryId;
    @XmlTransient
    private String deptName;
    @XmlTransient
    private int dupId;
    private String clientId;
    @XmlTransient
    private String subCategoryCode;
    @XmlTransient
    private String subCatDisplay;
    
    

    
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

	/**
	 * @return the subCategoryCode
	 */
	public String getSubCategoryCode() {
		return subCategoryCode;
	}

	/**
	 * @param subCategoryCode the subCategoryCode to set
	 */
	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
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

	public int getDupId() {
		return dupId;
	}

	public void setDupId(int dupId) {
		this.dupId = dupId;
	}

	public String getDeptId() {
	return deptId;
    }

    public void setDeptId(String deptId) {
	this.deptId = deptId;
    }

    public String getCategoryDesc() {
	return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
	this.categoryDesc = categoryDesc;
    }

    public String getCategoryId() {
	return categoryId;
    }

    public void setCategoryId(String categoryId) {
	this.categoryId = categoryId;
    }

    public String getDeptName() {
	return deptName;
    }

    public void setDeptName(String deptName) {
	this.deptName = deptName;
    }

    public String getSubCategoryId() {
	return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
	this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryDesc() {
	return subCategoryDesc;
    }

    public void setSubCategoryDesc(String subCategoryDesc) {
	this.subCategoryDesc = subCategoryDesc;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void writeJSONString(Writer out) throws IOException {
	@SuppressWarnings("rawtypes")
	LinkedHashMap obj = new LinkedHashMap();
	obj.put("categoryId", categoryId);
	obj.put("categoryDesc", categoryDesc);
	JSONValue.writeJSONString(obj, out);
    }
}
