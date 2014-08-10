/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.UserBean;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;

/**
 * @author srinivas
 * 
 */
@Repository("userDao")
public class UserDaoImpl extends MntDaoSupport implements UserDao {
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	CustomConnection customConnection;
	boolean flag = false;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;

	@Override
	public boolean userSave(Object object,boolean status) {
	
		UserBean bean = (UserBean) object;
		String clientId = null;
		String maxId=null;
		JdbcTemplate jdbcTemplate=null;
		String userId=null;
		try {
			session = request.getSession(false);
			
			if (session != null) {
				String ca = (String) session.getAttribute("admin");
				if (ca.equals("clientadmin")) {
					clientId = (String) session.getAttribute("clientId");
				} else {
					clientId = bean.getClientId();
				}
			} else {
				clientId = bean.getClientId();
			}
			
			if(status){
				 maxId = custIdDao.getMaxId("select max(user_Code) from users");
				 jdbcTemplate=getJdbcTemplate();
				 userId=clientId+maxId;
				}
				else
				{
					maxId=custIdDao.getMaxIdForUpload("select max(user_Code) from users");
					 jdbcTemplate=customConnection.getTemplateConnection();
					 userId=bean.getUserId();
				}
			
			
			
			
			if (maxId != null) {
				String sql = "insert into users(user_Id,user_Name,password,confirm_password,hint_question,hint_answer,firstname,lastname,email_Id,contactno,address,status,role_Id,client_Id,user_Code) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				jdbcTemplate.update(
						sql,
						new Object[] {userId, bean.getUsername(),
								bean.getPassword(), bean.getCpwd(),
								bean.getHintq(), bean.getHintans(),
								bean.getFirstname(), bean.getLname(),
								bean.getEmail(), bean.getContactno(),
								bean.getAddress(), 1, bean.getRoleId(),
								clientId,maxId });
				String sqlrole = "insert into userroles( user_Id,role_Id) values(?,?)";
				jdbcTemplate.update(sqlrole,
						new Object[] {userId, bean.getRoleId() });
				String sqlbranch = "insert into user_branch(user_Id,branch_Id,status) values(?,?,?)";
				jdbcTemplate.update(sqlbranch,
						new Object[] {userId, bean.getBranchId(), 0 });
				String sqlUser = "insert into user_client( client_Id,user_Id) values(?,?)";
				jdbcTemplate.update(sqlUser,
						new Object[] { clientId,userId });
				flag = true;
			} else {
				if(status){
					maxId="U0001";
					userId=clientId+maxId;
				}
				String sql = "insert into users(user_Id,user_Name,password,confirm_password,hint_question,hint_answer,firstname,lastname,email_Id,contactno,address,status,role_Id,client_Id,user_Code) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				jdbcTemplate.update(sql,new Object[]{userId, bean.getUsername(),
								bean.getPassword(), bean.getCpwd(),
								bean.getHintq(), bean.getHintans(),
								bean.getFirstname(), bean.getLname(),
								bean.getEmail(), bean.getContactno(),
								bean.getAddress(), 1, bean.getRoleId(),
								clientId,maxId});
				String sqlrole = "insert into userroles(user_Id,role_Id) values(?,?)";
				jdbcTemplate.update(sqlrole,
						new Object[] {userId, bean.getRoleId() });
				String sqlbranch = "insert into user_branch(user_Id,branch_Id,status) values(?,?,?)";
				jdbcTemplate.update(sqlbranch,
						new Object[] {userId, bean.getBranchId(), 0 });
				String sqlUser = "insert into user_client( client_Id,user_Id) values(?,?)";
				jdbcTemplate.update(sqlUser,
						new Object[] {clientId,userId });
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public boolean userUpdate(Object object) {

		try {
			UserBean expBean = (UserBean) object;
			String sql = "update users set user_Name=?,password=?,confirm_password=?,hint_question=?,hint_answer=?,firstname=?,lastname=?,email_Id=?,contactno=?,address=?,role_Id=?,client_Id=?  where user_Id=?";
			getJdbcTemplate().update(
					sql,
					new Object[] { expBean.getUsername(),
							expBean.getPassword(), expBean.getCpwd(),
							expBean.getHintq(), expBean.getHintans(),
							expBean.getFirstname(), expBean.getLname(),
							expBean.getEmail(), expBean.getContactno(),
							expBean.getAddress(), expBean.getRoleId(),
							expBean.getClientId(), expBean.getUserId() });
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean userDelete(String userId) {
		try {
			String sql = "update users set status=? where user_Id=?";
			getJdbcTemplate().update(sql, new Object[] { -1, userId });
			flag = true;
		} catch (ConstraintViolationException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<UserBean> userSearch(String clientId, String id) {
		List<UserBean> userBean = null;
		String roleid = "R0002";
		String role = (String) session.getAttribute("admin");
		try {
			if (role.equals("admin")) {
				userBean = getJdbcTemplate()
						.query("SELECT u.user_Id AS user_Id,u.user_Name AS username,u.password AS PASSWORD,u.confirm_password AS cpwd,r.role_Name AS roleName,u.hint_question AS hintq,u.hint_answer AS hintans,u.firstname AS firstname,u.lastname AS lname,u.email_Id AS email,u.contactno AS contactno,u.address AS address,u.status AS STATUS FROM users u JOIN `roles` r ON r.`role_Id`=u.`role_Id` where  u.status <"
								+ 2 + " and u.status >=" + 0,
								new BeanPropertyRowMapper<UserBean>(
										UserBean.class));
			} else {

				if (id == "clientId") {
					userBean = getJdbcTemplate()
							.query("SELECT  b.user_Id AS userId,b.user_Name AS username,b.password AS PASSWORD,b.confirm_password AS cpwd,r.role_Name as roleName,b.hint_question AS hintq,b.hint_answer AS hintans,b.firstname AS firstname,b.lastname AS lname,b.email_Id AS email,b.contactno AS contactno,b.address AS address ,b.status as status FROM user_client uc JOIN users b ON uc.`user_Id`=b.`user_Id` JOIN roles r ON r.role_Id=b.role_Id WHERE  uc.`client_Id`='"
									+ clientId
									+ "' and b.role_Id!='"
									+ roleid
									+ "' and  b.status <"
									+ 2
									+ " and b.status >=" + 0,
									new BeanPropertyRowMapper<UserBean>(
											UserBean.class));
				} else {
					userBean = getJdbcTemplate()
							.query("SELECT  b.user_Id AS userId,b.user_Name AS username,b.password AS PASSWORD,b.confirm_password AS cpwd,r.role_Name as roleName,b.hint_question AS hintq,b.hint_answer AS hintans,b.firstname AS firstname,b.lastname AS lname,b.email_Id AS email,b.contactno AS contactno,b.address AS address ,b.status as status FROM user_client uc JOIN users b ON uc.`user_Id`=b.`user_Id` JOIN roles r ON r.role_Id=b.role_Id WHERE  uc.`client_Id`='"
									+ clientId
									+ "' and b.status <"
									+ 2
									+ " and b.status >=" + 0,
									new BeanPropertyRowMapper<UserBean>(
											UserBean.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBean;
	}

	@Override
	public List<UserBean> userSearchwithId(String uid) {
		List<UserBean> usBean = null;
		String clientId = (String) session.getAttribute("clientId");
		String role = (String) session.getAttribute("admin");
		try {
			if (role.equals("admin")) {
				usBean = getJdbcTemplate()
						.query("SELECT u.user_Id AS user_Id,u.user_Name AS username,u.password AS PASSWORD,u.confirm_password AS cpwd,u.hint_question AS hintq,u.hint_answer AS hintans,u.firstname AS firstname,u.lastname AS lname,u.email_Id AS email,u.contactno AS contactno,r.`role_Name` AS roleName,u.address AS address,u.status AS STATUS FROM users u JOIN roles r ON r.`role_Id`=u.`role_Id` WHERE u.user_Name LIKE '"
								+ uid
								+ "%' AND u.status <"
								+ 2
								+ " AND u.status >=" + 0,
								new BeanPropertyRowMapper<UserBean>(
										UserBean.class));
			} else {
				usBean = getJdbcTemplate()
						.query("select u.user_Id as user_Id,u.user_Name as username,u.password as password,u.confirm_password as cpwd,u.hint_question as hintq,u.hint_answer as hintans,u.firstname as firstname,u.lastname as lname,u.email_Id as email,u.contactno as contactno,u.address as address,u.status as status,r.`role_Name` AS roleName from users u JOIN roles r ON r.`role_Id`=u.`role_Id` where u.user_Name like '"
								+ uid
								+ "%' and u.client_Id='"
								+ clientId
								+ "' and u.status <"
								+ 2
								+ " and u.status >="
								+ 0,
								new BeanPropertyRowMapper<UserBean>(
										UserBean.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return usBean;
	}

	@Override
	public List<UserBean> getBranchDetails(String clientId) {
		List<UserBean> userBeans = null;
		try {
			String sql = "SELECT b.`branch_Id`, b.`branch_Name` FROM `client_branch` cb JOIN `clientinfo` ci ON cb.`client_Id` = ci.`client_Id` JOIN `branch` b ON cb.`branch_Id` = b.`branch_Id` WHERE cb.`client_Id` ='"
					+ clientId + "'";
			userBeans = getJdbcTemplate().query(sql,
					new BeanPropertyRowMapper<UserBean>(UserBean.class));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return userBeans;
	}

	@Override
	public List<UserBean> userEdit(String user_Id, String id) {
		List<UserBean> usBean = null;
		// List<UserBean> usBean1 = null;
		try {
			if (id == "id") {
				usBean = getJdbcTemplate()
						.query("select user_Id as user_Id,user_Name as username,password as password,confirm_password as cpwd,hint_question as hintq,hint_answer as hintans,firstname as firstname,lastname as lname,email_Id as email,contactno as contactno,address as address,role_Id as role_Id,client_Id as clientIdedit from users where user_Id='"
								+ user_Id + "'",
								new BeanPropertyRowMapper<UserBean>(
										UserBean.class));
			} else {
				System.out.println("in else");
				usBean = getJdbcTemplate()
						.query("select user_Id as user_Id,user_Name as username,password as password,confirm_password as cpwd,hint_question as hintq,hint_answer as hintans,firstname as firstname,lastname as lname,email_Id as email,contactno as contactno,address as address,role_Id as role_Id,client_Id as clientIdedit from users where user_Id='"
								+ user_Id + "'",
								new BeanPropertyRowMapper<UserBean>(
										UserBean.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return usBean;
	}

	@Override
	public List<UserBean> userSearch() {
		List<UserBean> userBean = null;
		String role = (String) session.getAttribute("admin");
		String clientId = (String) session.getAttribute("clientId");
		try {
			if (role.equals("admin")) {
				userBean = getJdbcTemplate()
						.query("SELECT u.user_Id AS user_Id,u.user_Name AS username,u.password AS PASSWORD,u.confirm_password AS cpwd,r.role_Name AS roleName,u.hint_question AS hintq,u.hint_answer AS hintans,u.firstname AS firstname,u.lastname AS lname,u.email_Id AS email,u.contactno AS contactno,u.address AS address,u.status AS STATUS FROM users u JOIN `roles` r ON r.`role_Id`=u.`role_Id` where  u.status <"
								+ 2 + " and u.status >=" + 0,
								new BeanPropertyRowMapper<UserBean>(
										UserBean.class));
			} else {
				userBean = getJdbcTemplate()
						.query("select u.user_Id as user_Id,u.user_Name as username,u.password as password,u.confirm_password as cpwd,r.role_Name AS roleName,u.hint_question as hintq,u.hint_answer as hintans,u.firstname as firstname,u.lastname as lname,u.email_Id as email,u.contactno as contactno,u.address as address,u.status as status from users u JOIN `roles` r ON r.`role_Id`=u.`role_Id` where u.client_Id='"
								+ clientId
								+ "' and u.status <"
								+ 2
								+ " and u.status >=" + 0,
								new BeanPropertyRowMapper<UserBean>(
										UserBean.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBean;
	}

	@Override
	public List<UserBean> userSearchwithclientId(String uid, String clientId) {
		List<UserBean> usBean = null;
		try {
			usBean = getJdbcTemplate()
					.query("SELECT  b.user_Id AS userId,b.user_Name AS username,b.password AS PASSWORD,b.confirm_password AS cpwd,r.role_Name AS roleName,b.hint_question AS hintq,b.hint_answer AS hintans,b.firstname AS firstname,b.lastname AS lname,b.email_Id AS email,b.contactno AS contactno,b.address AS address,b.status AS status FROM user_client uc JOIN users b ON uc.`user_Id`=b.`user_Id` JOIN `roles` r ON r.`role_Id`=b.`role_Id` WHERE  b.`role_Id`='"
							+ uid
							+ "' and b.client_Id='"
							+ clientId
							+ "'and b.status <" + 2 + " and b.status >=" + 0,
							new BeanPropertyRowMapper<UserBean>(UserBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usBean;
	}

	@Override
	public boolean userstatusSave(String status) {
		try {
			String sqlrole = "UPDATE users SET STATUS =? WHERE user_id=?";
			getJdbcTemplate().update(sqlrole, new Object[] { 1, status });
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean userstatusuncheckSave(String status) {
		try {
			String sqlrole = "UPDATE users SET STATUS =? WHERE user_id=?";
			getJdbcTemplate().update(sqlrole, new Object[] { 0, status });
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public Integer getUsername(String uid) {
		int l = 0;
		try {
			String clientId = (String) session.getAttribute("uclient");
			String roleId = (String) session.getAttribute("uroleId");
			String psql = "SELECT dc.dept_Id FROM `dept_client` dc, `users` u, `userroles` ur"
					+ " WHERE u.`user_Id`=ur.`user_Id` AND dc.`client_Id`=u.`client_Id` "
					+ "AND ur.`user_Id`= u.`user_Id`"
					+ "AND u.`client_Id`='"
					+ clientId + "' AND ur.`role_Id`='" + roleId + "'";
			UserBean object = getJdbcTemplate().queryForObject(psql,
					new BeanPropertyRowMapper<UserBean>(UserBean.class));
			String deptId = object.getDeptId();
			String sql = "SELECT COUNT(*)  FROM `dept_client` dc , users u, `userroles` ur "
					+ "WHERE u.`client_Id`= dc.`client_Id` "
					+ "AND ur.`user_Id`=u.`user_Id` "
					+ "AND dc.`dept_Id`='"
					+ deptId
					+ "'  "
					+ "AND u.`client_Id`='"
					+ clientId
					+ "' "
					+ "  AND u.`user_Name`='"
					+ uid
					+ "'"
					+ "  AND ur.`role_Id`='" + roleId + "'";
			l = getJdbcTemplate().queryForInt(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public Integer getUsernameEdit(String uid, String uname) {
		int l = 0;
		try {
			String sql = "select count(*) from users p where  p.user_Name ='"
					+ uname + "' and p.user_Id!='" + uid + "'";
			l = getJdbcTemplate().queryForInt(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

}
