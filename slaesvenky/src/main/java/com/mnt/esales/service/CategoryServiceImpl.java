/**
 * 
 */
package com.mnt.esales.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.dao.CategoryDao;
import com.mysql.jdbc.log.Log;

/**
 * @author yogi
 * 
 */
@Service("catService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao catDao;
    boolean flag;
    List<CategoryBean> listBean;
    private Logger logger =Logger.getLogger(CategoryServiceImpl.class); 
    @Override
    public boolean saveCategory(Object object, boolean status) {
	try {
	    flag = catDao.saveCategory(object, status);
	} catch (Exception e) {
	    flag = false;
	    e.printStackTrace();
	}
	return flag;
    }

    @Override
    public Map<String, String> populateCategory() {
	Map<String, String> map = null;
	try {
	    map = catDao.populateCategory();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return map;
    }

    @Override
    public List<CategoryBean> categorySearch() {
	try {
	    listBean = catDao.categorySearch();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return listBean;
    }

    @Override
    public List<CategoryBean> catSearch(String catId, String id) {
	List<CategoryBean> catBean = null;
	try {
	    catBean = catDao.search(catId, id);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return catBean;
    }

    @Override
    public boolean catUpdate(Object object) {
	try {
	    flag = catDao.catUpdate(object);
	} catch (Exception e) {
	    flag = false;
	    e.printStackTrace();
	}
	return flag;
    }

    @Override
    public boolean catDelete(String catId) {
	try {
	    flag = catDao.catDelete(catId);
	} catch (Exception e) {
	    flag = false;
	    e.printStackTrace();
	}
	return flag;
    }
}
