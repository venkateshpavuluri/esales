/**
 * 
 */
package com.mnt.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mnt.esales.bean.DepartmentBean;

/**
 * @author Naresh
 * 
 */
public class DepartmentRowMapper implements RowMapper<DepartmentBean> {

	@Override
	public DepartmentBean mapRow(ResultSet rs, int rowNo) throws SQLException {
		DepartmentBean dbBean = new DepartmentBean();
		dbBean.setDeptId(rs.getString(1));
		dbBean.setDeptName(rs.getString(2));
		dbBean.setDisplay(rs.getString(3));

		return dbBean;
	}

}
