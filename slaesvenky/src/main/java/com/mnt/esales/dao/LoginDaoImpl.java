package com.mnt.esales.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.LoginUser;
import com.mnt.esales.daosupport.MntDaoSupport;
import com.mnt.rowmappers.LoginUserRowMapper;

/**
 * @author venkateshp
 * 
 */
@Repository("loginDaoImpl")
public class LoginDaoImpl extends MntDaoSupport implements LoginDao {
	private static Logger logger = Logger.getLogger(LoginDaoImpl.class);
	private String sql;

	@Override
	public List<LoginUser> getLoginDetails(String userName, String password) {
		List<LoginUser> loginUsers = null;
		try {
			sql = "select s.user_Id,s.user_Name ,s.password from users s where s.user_Name='"
					+ userName
					+ "' and s.password='"
					+ password
					+ "' and s.status >" + 0;
			loginUsers = getJdbcTemplate().query(sql, new LoginUserRowMapper());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return loginUsers;
	}

	@Override
	public List<LoginUser> getRole(String role) {
		List<LoginUser> list = null;
		try {
			String sql = "SELECT r.`role_Id` as roleid, r.`role_Name` as rolename FROM `userroles` ur JOIN users u ON ur.`user_Id`=u.`user_Id` JOIN roles r ON ur.`role_Id`= r.`role_Id` WHERE ur.user_Id= '"
					+ role + "'";
			list = getJdbcTemplate().query(sql,
					new BeanPropertyRowMapper<LoginUser>(LoginUser.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LoginUser> getClient(String clientId) {
		List<LoginUser> list = null;
		try {
			String sql = "SELECT uc.client_Id FROM `user_client` uc JOIN users u ON u.`user_Id`=uc.`user_Id` WHERE u.`user_Id`='"
					+ clientId + "'";
			list = getJdbcTemplate().query(sql,
					new BeanPropertyRowMapper<LoginUser>(LoginUser.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getBranchId(String userId) {
		Object branchId = null;
		try {
			String sql = "SELECT d.`dept_Id` FROM `clientinfo` dc, `department` d WHERE dc.dept_Id= d.`dept_Id` AND dc.client_Id ='"
					+ userId + "'";
			branchId = getJdbcTemplate().queryForObject(sql, Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (String) branchId;
	}

	@Override
	public List<BranchBean> getBranchDetails(String userId) {
		List<BranchBean> blist = null;
		try {
			String sql = "SELECT b.`branch_Name` as branchName,b.`city` as city,b.`state` as state,b.`address1` as address1,b.`address2`as address2,t.`theme_Name` as branchTheme,b.image_Path as logoPath FROM `branch` b,`permissions` p,`themes` t WHERE b.`branch_Id`=p.`branch_Id` AND t.`theme_Id`=b.`branch_Theme` AND p.user_Id ='"
					+ userId + "'";
			blist = getJdbcTemplate().query(sql,
					new BeanPropertyRowMapper<BranchBean>(BranchBean.class));
			System.out.println("sql iss ==="+sql);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return blist;
	}


	@Override
	public String getLoginName(String userId) {
		System.out.println(userId);
		
		
		String sql="SELECT  CONCAT_WS('-',`firstname`,`lastname`) AS fullName FROM users WHERE `user_Id`='"+userId+"'";
		String fullName=(String)getJdbcTemplate().queryForObject(sql,Object.class);
		return fullName;
	}


	@Override
	public int loginUser(String userName, String password) {
		// TODO Auto-generated method stub
		int count=0;
		try
		{
			sql="select count(*) from users s where s.user_Name='"+userName+"' and s.password='"+password+"'";
					count=getJdbcTemplate().queryForInt(sql);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}


}
