/**
 *@Copyright MNTSOFT 
 */
package com.mnt.esales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.DepartmentBean;
import com.mnt.esales.dao.DepartmentDao;

/**
 * @author Naresh
 * 
 */
@Service("deptService")
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	DepartmentDao deptDao;

	@Override
	public boolean deptSave(Object object,boolean status) {
		boolean flag;
		try {
			flag = deptDao.deptSave(object, status);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<DepartmentBean> deptSearch() {
		List<DepartmentBean> deptBean = null;
		try {
			deptBean = deptDao.deptSearch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptBean;
	}

	@Override
	public boolean deptUpdate(Object object) {
		boolean flag;
		try {
			flag = deptDao.deptUpdate(object);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deptDelete(String deptId) {
		boolean flag;
		try {
			flag = deptDao.deptDelete(deptId);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<DepartmentBean> deptSearch(String deptId, String id) {
		List<DepartmentBean> deptBean = null;
		try {
			deptBean = deptDao.deptSearch(deptId, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptBean;
	}

}
