/**
 * 
 */
package com.mnt.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mnt.esales.bean.PopulateBean;

/**
 * @author Naresh
 * 
 */
public class PopulateRowMapper implements RowMapper<PopulateBean> {

	@Override
	public PopulateBean mapRow(ResultSet rs, int arg1) throws SQLException {
		PopulateBean ps = new PopulateBean();
		ps.setId(rs.getString(1));
		ps.setName(rs.getString(2));
		return ps;
	}

}
