/**
 * @Copyright MNTSOFT
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.DepartmentBean;

/**
 * @author Naresh
 * 
 */
public interface DepartmentService {

	public boolean deptSave(Object object,boolean status);

	public boolean deptUpdate(Object object);

	public boolean deptDelete(String deptId);

	public List<DepartmentBean> deptSearch();

	public List<DepartmentBean> deptSearch(String deptId,String id);

}
