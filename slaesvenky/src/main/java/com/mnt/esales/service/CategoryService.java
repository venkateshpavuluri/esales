/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;
import java.util.Map;
import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.DepartmentBean;

/**
 * @author yogi
 * 
 */
public interface CategoryService {
    public boolean saveCategory(Object object, boolean status);

    public Map<String, String> populateCategory();

    public List<CategoryBean> categorySearch();

    public List<CategoryBean> catSearch(String catId, String id);

    public boolean catUpdate(Object object);

    public boolean catDelete(String catId);
   
}
