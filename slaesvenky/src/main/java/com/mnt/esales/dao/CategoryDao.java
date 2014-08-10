/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;
import java.util.Map;

import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.DepartmentBean;

/**
 * @author yogi
 * 
 */
public interface CategoryDao {
	public boolean saveCategory(Object object, boolean getMaxId);

	public Map<String, String> populateCategory();

	public List<CategoryBean> categorySearch();

	public List<CategoryBean> search(String catId, String id);

	public boolean catUpdate(Object object);

	public boolean catDelete(String catId);

}
