/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.dao.ProductDao;
import com.mnt.esales.marshaling.Product;
import com.mnt.esales.marshaling.SubCategory;

/**
 * @author venkateshp
 *
 */
@Service("productService")
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDao productDao;

	public boolean productSave(Object object) {
		// TODO Auto-generated method stub
		return productDao.productSave(object,true);
	}
	@Override
	public boolean productUpdate(Object object) {
		// TODO Auto-generated method stub
		return productDao.productUpdate(object);
	}

	@Override
	public boolean productDelete(String productId) {
		// TODO Auto-generated method stub
		return productDao.productDelete(productId);
	}
	@Override
	public List<ProductBean> productSearch(String clientId) {
		// TODO Auto-generated method stub
		return productDao.productSearch(clientId);
	}
	@Override
	public List<ProductBean> productSearchEWithId(String productId, String id) {
		// TODO Auto-generated method stub
		return productDao.productSearch(productId, id);
	}
	@Override
	public List<ProductBean> getCategoryDetails(String deptId) {
		// TODO Auto-generated method stub
		return productDao.getCategoryDetails(deptId);
	}
	@Override
	public List<ProductBean> getSubCategoryDetails(String catId) {
		// TODO Auto-generated method stub
		return productDao.getSubCategoryDetails(catId);
	}
	@Override
	public List<ProductBean> getProductsWithId(String subCategoryId) {
		// TODO Auto-generated method stub
		return productDao.getProductsWithId(subCategoryId);
	}
	@Override
	public int duplicateCheck(String categoryId, String prodcuId,
			String prodName) {
		// TODO Auto-generated method stub
		return productDao.duplicateCheck(categoryId, prodcuId, prodName);
	}


}
