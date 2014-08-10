/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.json.simple.JSONArray;

import com.mnt.esales.bean.SubCategoryBean;

/**
 * @author yogi
 * 
 */
public interface SubCategoryService {
    public JSONArray getCategory(String deptId);

    public boolean saveSubCategory(Object object, boolean status);

    public List<SubCategoryBean> subCategorySearch();

    public List<SubCategoryBean> search(String subCatId, String id);

    public boolean subCatUpdate(Object object);

    public boolean subCatDelete(String subCatId);
    
    public int subCategoryDuplicate(String catId, String subCatDesc);
}
