/**
 * @Copyright MNTSOFT
 */
package com.mnt.esales.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.DepartmentBean;
import com.mnt.esales.daosupport.MntDaoSupport;

/**
 * @author Naresh
 * 
 */
@Repository("deptDao")
public class DepartmentDaoImpl extends MntDaoSupport implements DepartmentDao {
	boolean flag = true;
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;

	@Override
	public boolean deptSave(Object object, boolean status) {
		String maxId = null;
		try {
			if (status) {
				maxId = custIdDao
						.getMaxId("select max(dept_Id) from department");
			}
			DepartmentBean deptBean = (DepartmentBean) object;
			session = request.getSession(false);
			if (maxId != null) {
				String sql = "insert into department(dept_Id,dept_Desc) values(?,?)";
				getJdbcTemplate().update(sql,
						new Object[] { maxId, deptBean.getDeptName() });

				session.setAttribute("deptId", maxId);

			} else {
				String sql = "insert into department(dept_Desc) values(?)";
				getJdbcTemplate().update(sql,
						new Object[] { deptBean.getDeptName() });
				session.setAttribute("deptId", "D0001");

			}
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
			deptBean = getJdbcTemplate()
					.query("select dept_Id as deptId,dept_Desc as deptName,dept_Display as display from department order by dept_Desc",
							new BeanPropertyRowMapper<DepartmentBean>(
									DepartmentBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptBean;
	}

	@Override
	public boolean deptUpdate(Object object) {
		try {
			DepartmentBean deptBean = (DepartmentBean) object;
			String sql = "update department set dept_Desc=? where dept_Id=?";
			getJdbcTemplate()
					.update(sql,
							new Object[] { deptBean.getDeptName(),
									deptBean.getDeptId() });
			/*
			 * getJdbcTemplate().update(
			 * "update dept_client set client_Id=? where dept_Id=?", new
			 * Object[] { clientId, deptBean.getDeptId() });
			 */

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deptDelete(String deptId) {
		try {

			String sql = "delete from department where dept_Id=?";
			getJdbcTemplate().update(sql, new Object[] { deptId });

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
			if (id == "id") {
				deptBean = getJdbcTemplate()
						.query("select dept_Id as deptId,dept_Desc as deptName,dept_Display as display from department where dept_Id='"
								+ deptId + "'",
								new BeanPropertyRowMapper<DepartmentBean>(
										DepartmentBean.class));
			} else {
				deptBean = getJdbcTemplate()
						.query("select dept_Id as deptId,dept_Desc as deptName,dept_Display as display from department where dept_Desc like'"
								+ deptId + "%' order by dept_Desc",
								new BeanPropertyRowMapper<DepartmentBean>(
										DepartmentBean.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptBean;
	}

}
