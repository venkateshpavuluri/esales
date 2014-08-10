/**
 * 
 */
package com.mnt.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mnt.esales.bean.GetMaxIdBean;

/**
 * @author Naresh
 * 
 */
public class GetMaxIdRowMapper implements RowMapper<GetMaxIdBean> {

	@Override
	public GetMaxIdBean mapRow(ResultSet rs, int rowNo) throws SQLException {
		GetMaxIdBean bean = new GetMaxIdBean();
		bean.setId(rs.getString(1));
		return bean;
	}

}
