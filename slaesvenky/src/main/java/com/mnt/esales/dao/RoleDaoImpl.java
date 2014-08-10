/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import com.mnt.esales.bean.RoleBean;
import com.mnt.esales.daosupport.MntDaoSupport;

/**
 * @author yogi
 * 
 */
@Repository("roleDao")
public class RoleDaoImpl extends MntDaoSupport implements RoleDao {

	boolean flag =true;
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	HttpServletRequest request;
	private Logger logger = Logger.getLogger(RoleDaoImpl.class);

	@Override
	public boolean saveRole(Object object) {
		try {
			String maxId = custIdDao.getMaxId("select max(role_Id) from roles");
			RoleBean roleBean = (RoleBean) object;
			if (maxId != null) {
				String sql = "insert into roles(role_Id,role_Name) values(?,?)";
				getJdbcTemplate().update(sql,
						new Object[] { maxId, roleBean.getRoleName() });
			} else {
				String sql = "insert into roles(role_Name) values(?)";
				getJdbcTemplate().update(sql,
						new Object[] { roleBean.getRoleName() });
			}

		} catch (Exception e) {
			flag = false;
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<RoleBean> roleSearch() {
		List<RoleBean> roleLlistBean = null;
		try {
			String sql = "SELECT role_Id as roleId, role_Name as roleName from roles";
			roleLlistBean = getJdbcTemplate().query(sql,
					new BeanPropertyRowMapper<RoleBean>(RoleBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleLlistBean;
	}

	@Override
	public List<RoleBean> search(String roleId, String id) {
		List<RoleBean> roleBeanList = null;
		try {
			if (id == "id") {
				String sql = "select role_Id as roleId, role_Name as roleName from roles where role_Id='"
						+ roleId + "'";
				roleBeanList = getJdbcTemplate().query(sql,
						new BeanPropertyRowMapper<RoleBean>(RoleBean.class));
			} else {
				roleBeanList = getJdbcTemplate().query(
						"SELECT role_Id as roleId, role_Name as roleName from roles like'"
								+ roleId + "%'",
						new BeanPropertyRowMapper<RoleBean>(RoleBean.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return roleBeanList;
	}

	@Override
	public boolean roleUpdate(Object object) {
		try {
			RoleBean roleBean = (RoleBean) object;
			String sql = "update roles set role_Name=? where role_Id=?";
			getJdbcTemplate()
					.update(sql,
							new Object[] { roleBean.getRoleName(),
									roleBean.getRoleId() });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public boolean roleDelete(String roleId) {
		try {
			String sql = "delete from roles where role_Id=?";
			getJdbcTemplate().update(sql, new Object[] { roleId });
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return flag;
	}

}
