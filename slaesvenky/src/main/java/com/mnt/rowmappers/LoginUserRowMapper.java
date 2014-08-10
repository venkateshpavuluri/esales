/**
 * 
 */
package com.mnt.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mnt.esales.bean.LoginUser;

/**
 * @author venkateshp
 * 
 */
public class LoginUserRowMapper implements RowMapper<LoginUser> {

	@Override
	public LoginUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		LoginUser loginUser = new LoginUser();
		loginUser.setUserName(rs.getString("user_Name"));
		loginUser.setPassword(rs.getString("password"));
		loginUser.setUserId(rs.getString("user_Id"));
		return loginUser;
	}

}
