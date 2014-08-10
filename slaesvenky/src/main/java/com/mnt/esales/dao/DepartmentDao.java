/**
 * @Copyright MNTSOFT
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.DepartmentBean;

/**
 * @author Naresh
 * 
 */
public interface DepartmentDao {

	public boolean deptSave(Object object,boolean status);

	public boolean deptUpdate(Object object);

	public boolean deptDelete(String deptId);

	public List<DepartmentBean> deptSearch();

	public List<DepartmentBean> deptSearch(String deptId, String id);

}
