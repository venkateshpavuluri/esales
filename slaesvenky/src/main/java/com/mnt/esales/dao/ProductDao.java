/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.bean.DepartmentBean;
import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.marshaling.Product;

/**
 * @author venkateshp
 * 
 */
public interface ProductDao {

	public boolean productSave(Object object,boolean flag);

	public boolean productUpdate(Object object);

	public boolean productDelete(String deptId);

	public List<ProductBean> productSearch(String clientId);

	public List<ProductBean> productSearch(String productId, String id);

	public List<ProductBean> getCategoryDetails(String deptId);

	public List<ProductBean> getSubCategoryDetails(String catId);

	public List<ProductBean> getProductsWithId(String subCategoryId);

	public int duplicateCheck(String categoryId, String prodcuId,
			String prodName);
	
	
}
