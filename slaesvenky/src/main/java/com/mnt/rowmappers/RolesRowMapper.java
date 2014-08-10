/**
 * 
 */
package com.mnt.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mnt.esales.bean.RoleBean;

/**
 * @author madhav
 *
 */
public class RolesRowMapper implements RowMapper<RoleBean> {

	@Override
	public RoleBean mapRow(ResultSet rs, int rowNum) throws SQLException {
	      RoleBean roleBean=new RoleBean();
	      roleBean.setRoleId(rs.getString("role_Id"));
	      roleBean.setRoleName(rs.getString("role_Name"));
		return roleBean;
	}

}
