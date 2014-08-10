/**
 * 
 */
package com.mnt.esales.marshaling;

import java.io.BufferedReader;
import java.io.File;
import java.util.List;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.bean.ExpensesBean;
import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockBean;
import com.mnt.esales.bean.SubCategoryBean;
import com.mnt.esales.bean.UserBean;

/**
 * @author venkateshp
 * 
 */
@XmlRootElement(name = "esales")
@XmlAccessorType(XmlAccessType.FIELD)
public class Esales {
	@XmlElement(name = "client")
	public ClientInfoBean client;
	@XmlElementWrapper(name = "branches")
	@XmlElement(name = "branch")
	public List<BranchBean> branchs;
	@XmlElementWrapper(name = "users")
	@XmlElement(name = "user")
	public List<UserBean> userDetails;
	@XmlElementWrapper(name = "categorys")
	@javax.xml.bind.annotation.XmlElement(name = "category")
	public List<CategoryBean> categories = null;
	@XmlElementWrapper(name = "subCategorys")
	@XmlElement(name = "subCategory")
	public List<SubCategoryBean> subCategories;
	@XmlElementWrapper(name = "products")
	@javax.xml.bind.annotation.XmlElement(name = "product")
	public List<ProductBean> products = null;
	@XmlElementWrapper(name = "stockEntrys")
	@XmlElement(name = "stockEntry")
	public List<StockBean> stockEntrys;
	@XmlElementWrapper(name = "stockAllocs")
	@XmlElement(name = "stockAllocation")
	public List<StockAllocation> stockAllocations;
	@XmlElementWrapper(name = "permissions")
	@XmlElement(name = "permission")
	public List<com.mnt.esales.bean.Permissions> permissions;
	@XmlElementWrapper(name = "billings")
	@XmlElement(name = "billing")
	public List<BillInfo> billings;
	@XmlElementWrapper(name = "expenses")
	@XmlElement(name = "expense")
	public List<ExpensesBean> expenses;
	/**
	 * @return the client
	 */
	public ClientInfoBean getClient() {
		return client;
	}
	/**
	 * @param client the client to set
	 */
	public void setClient(ClientInfoBean client) {
		this.client = client;
	}
	/**
	 * @return the branchs
	 */
	public List<BranchBean> getBranchs() {
		return branchs;
	}
	/**
	 * @param branchs the branchs to set
	 */
	public void setBranchs(List<BranchBean> branchs) {
		this.branchs = branchs;
	}
	/**
	 * @return the userDetails
	 */
	public List<UserBean> getUserDetails() {
		return userDetails;
	}
	/**
	 * @param userDetails the userDetails to set
	 */
	public void setUserDetails(List<UserBean> userDetails) {
		this.userDetails = userDetails;
	}
	/**
	 * @return the categories
	 */
	public List<CategoryBean> getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<CategoryBean> categories) {
		this.categories = categories;
	}
	/**
	 * @return the subCategories
	 */
	public List<SubCategoryBean> getSubCategories() {
		return subCategories;
	}
	/**
	 * @param subCategories the subCategories to set
	 */
	public void setSubCategories(List<SubCategoryBean> subCategories) {
		this.subCategories = subCategories;
	}
	/**
	 * @return the products
	 */
	public List<ProductBean> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(List<ProductBean> products) {
		this.products = products;
	}
	/**
	 * @return the stockEntrys
	 */
	public List<StockBean> getStockEntrys() {
		return stockEntrys;
	}
	/**
	 * @param stockEntrys the stockEntrys to set
	 */
	public void setStockEntrys(List<StockBean> stockEntrys) {
		this.stockEntrys = stockEntrys;
	}
	/**
	 * @return the stockAllocations
	 */
	public List<StockAllocation> getStockAllocations() {
		return stockAllocations;
	}
	/**
	 * @param stockAllocations the stockAllocations to set
	 */
	public void setStockAllocations(List<StockAllocation> stockAllocations) {
		this.stockAllocations = stockAllocations;
	}
	/**
	 * @return the permissions
	 */
	public List<com.mnt.esales.bean.Permissions> getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List<com.mnt.esales.bean.Permissions> permissions) {
		this.permissions = permissions;
	}
	/**
	 * @return the billings
	 */
	public List<BillInfo> getBillings() {
		return billings;
	}
	/**
	 * @param billings the billings to set
	 */
	public void setBillings(List<BillInfo> billings) {
		this.billings = billings;
	}
	/**
	 * @return the expenses
	 */
	public List<ExpensesBean> getExpenses() {
		return expenses;
	}
	/**
	 * @param expenses the expenses to set
	 */
	public void setExpenses(List<ExpensesBean> expenses) {
		this.expenses = expenses;
	}

}
