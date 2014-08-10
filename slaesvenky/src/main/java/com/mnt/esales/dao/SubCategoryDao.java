/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.SubCategoryBean;

/**
 * @author yogi
 * 
 */
public interface SubCategoryDao {
    public List<SubCategoryBean> listOfCategory(String deptId);

    public boolean saveSubCategory(Object object, boolean status);

    public List<SubCategoryBean> subCategorySearch();

    public List<SubCategoryBean> search(String subCatId, String id);

    public boolean subCatUpdate(Object object);

    public boolean subCatDelete(String subCatId);
    
    public List<SubCategoryBean> subCategoryDuplicate(String catId, String subCatDesc);
}
