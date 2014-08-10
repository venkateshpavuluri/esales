/**
 * 
 */
package com.mnt.esales.service;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.SubCategoryBean;
import com.mnt.esales.dao.SubCategoryDao;

/**
 * @author yogi
 * 
 */
@Service("subCategoryService")
public class SubCategoryServiceImpl implements SubCategoryService {

    boolean flag;
    List<SubCategoryBean> listBean;
    @Autowired
    SubCategoryDao subCategoryDao;
    private Logger logger =Logger.getLogger(SubCategoryServiceImpl.class); 
    @SuppressWarnings("unchecked")
    @Override
    public JSONArray getCategory(String deptId) {
	JSONArray jsonArray = new JSONArray();
	SubCategoryBean subCategoryBean= null;
	try {
	    listBean = subCategoryDao
		    .listOfCategory(deptId);
	    if (listBean != null) {
		Iterator<SubCategoryBean> iterator = listBean.iterator();
		while (iterator.hasNext()) {
		    SubCategoryBean objects = (SubCategoryBean) iterator.next();
		     subCategoryBean = new SubCategoryBean();
		    subCategoryBean.setCategoryId((String) objects
			    .getCategoryId());
		    subCategoryBean.setCategoryDesc((String) objects
			    .getCategoryDesc());
		    jsonArray.add(subCategoryBean);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return jsonArray;
    }

    @Override
    public boolean saveSubCategory(Object object, boolean status) {
	try {
	    flag = subCategoryDao.saveSubCategory(object, status);
	} catch (Exception e) {
	    flag = false;
	    e.printStackTrace();
	}
	return flag;
    }

    @Override
    public List<SubCategoryBean> subCategorySearch() {
	try {
	    listBean = subCategoryDao.subCategorySearch();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return listBean;
    }

    @Override
    public List<SubCategoryBean> search(String subCatId, String id) {
	try {
	    listBean = subCategoryDao.search(subCatId, id);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return listBean;
    }

    @Override
    public boolean subCatUpdate(Object object) {
	try {
	    flag = subCategoryDao.subCatUpdate(object);
	} catch (Exception e) {
	    flag = false;
	    e.printStackTrace();
	}
	return flag;
    }

    @Override
    public boolean subCatDelete(String subCatId) {
	try {
	    flag = subCategoryDao.subCatDelete(subCatId);
	} catch (Exception e) {
	    flag = false;
	    e.printStackTrace();
	}
	return flag;
    }
    @Override
	public int subCategoryDuplicate(String catId, String subCatDesc) {
		int dupId =0;
		try{
			listBean= subCategoryDao.subCategoryDuplicate(catId, subCatDesc);
			Iterator< SubCategoryBean> itr= listBean.iterator();
			while(itr.hasNext()){
				SubCategoryBean subCategoryBean = (SubCategoryBean)itr.next();
				 dupId = subCategoryBean.getDupId();
			}
		}
		catch(Exception e){
			logger.error(e.getMessage());
		}
		return dupId;
	}
}
