/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.DepartmentBean;
import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.marshaling.Product;
import com.mnt.esales.marshaling.SubCategory;

/**
 * @author venkateshp
 * 
 */
public interface ProductService {
	public boolean productSave(Object object);

	public boolean productUpdate(Object object);

	public boolean productDelete(String deptId);

	public List<ProductBean> productSearch(String clientId);

	public List<ProductBean> productSearchEWithId(String productId, String id);

	public List<ProductBean> getCategoryDetails(String deptId);

	public List<ProductBean> getSubCategoryDetails(String deptId);

	public List<ProductBean> getProductsWithId(String subCategoryId);

	public int duplicateCheck(String categoryId, String prodcuId,
			String prodName);

}
