/**
 * 
 */
package com.mnt.esales.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.daosupport.MntDaoSupport;

/**
 * @author Naresh
 * 
 */
@Repository
public class ClientInfoDaoImpl extends MntDaoSupport implements ClientInfoDao {

	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	HttpServletRequest request;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean saveClient(Object object,boolean status) {
		boolean flag = true;
		String maxId = null;
		
		try {
			HttpSession session = request.getSession(false);
			if(status){
				 maxId = custIdDao
							.getMaxId("select max(client_Id) from clientinfo");
			}
			
			ClientInfoBean clientBean = (ClientInfoBean) object;
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime());
			if (maxId != null) {
				String sql = "insert into clientinfo(client_Id,client_Name,address1,address2,landmark,city,state,pin,phone,contact_Person,contact_No,client_Theme,reg_Date,email_Id,dept_Id,image_Path) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				getJdbcTemplate()
						.update(sql,
								new Object[] { maxId,
										clientBean.getClientName(),
										clientBean.getAddress(),
										clientBean.getAddr(),
										clientBean.getLandmark(),
										clientBean.getCity(),
										clientBean.getState(),
										clientBean.getPin(),
										clientBean.getPhone(),
										clientBean.getContactPerson(),
										clientBean.getContactNo(),
										clientBean.getTheme(), date,
										clientBean.getEmail(),
										clientBean.getDeptId(),clientBean.getLogoPath() });
				getJdbcTemplate()
						.update("insert into dept_client(client_Id,dept_Id) values(?,?)",
								new Object[] { maxId, clientBean.getDeptId() });

				session.setAttribute("clientId", maxId);

			} else {
				String sql = "insert into clientinfo(client_Name,address1,address2,landmark,city,state,pin,phone,contact_Person,contact_No,client_Theme,reg_Date,email_Id,dept_Id,image_Path) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				getJdbcTemplate()
						.update(sql,
								new Object[] { clientBean.getClientName(),
										clientBean.getAddress(),
										clientBean.getAddr(),
										clientBean.getLandmark(),
										clientBean.getCity(),
										clientBean.getState(),
										clientBean.getPin(),
										clientBean.getPhone(),
										clientBean.getContactPerson(),
										clientBean.getContactNo(),
										clientBean.getTheme(), date,
										clientBean.getEmail(),
										clientBean.getDeptId(),clientBean.getLogoPath() });

				getJdbcTemplate()
						.update("insert into dept_client(client_Id,dept_Id) values(?,?)",
								new Object[] { "C0001", clientBean.getDeptId() });
				session.setAttribute("clientId", "C0001");
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateClient(Object object) {
		boolean flag = true;
		try {
			ClientInfoBean clientBean = (ClientInfoBean) object;
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime());
			String sql = "update clientinfo set client_Name=?,address1=?,address2=?,landmark=?,city=?,state=?,pin=?,phone=?,contact_Person=?,contact_No=?,client_Theme=?,reg_Date=?, email_Id= ?,dept_Id=?,image_Path=? where client_Id=?";
			getJdbcTemplate().update(
					sql,
					new Object[] { clientBean.getClientName(),
							clientBean.getAddress(), clientBean.getAddr(),
							clientBean.getLandmark(), clientBean.getCity(),
							clientBean.getState(), clientBean.getPin(),
							clientBean.getPhone(),
							clientBean.getContactPerson(),
							clientBean.getContactNo(), clientBean.getTheme(),
							date, clientBean.getEmail(),
							clientBean.getDeptId(),clientBean.getLogoPath(),clientBean.getClientId() });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteClient(String clientId) {
		boolean flag = true;
		try {

			String sql = "delete from clientinfo where client_Id=?";
			getJdbcTemplate().update(sql, new Object[] { clientId });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<ClientInfoBean> searchClient() {
		List<ClientInfoBean> deptBean = null;
		try {
			deptBean = getJdbcTemplate()
					.query("select client_Id as clientId,client_Name as clientName,address1 as address,address2 as addr,landmark as landmark,city as city,state as state,pin as pin,phone as phone,contact_Person as contactPerson,contact_No as contactNo,reg_Date as regDate, email_Id as email from clientinfo  where client_Status!=2 order by client_Name", 
							new BeanPropertyRowMapper<ClientInfoBean>(
									ClientInfoBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptBean;
	}

	@Override
	public List<ClientInfoBean> searchClient(String clientId, String id) {
		List<ClientInfoBean> clientBean = null;
		try {
			if (id == "id") {
				clientBean = getJdbcTemplate()
						.query("select ci.client_Id as clientId,ci.client_Name as clientName,ci.address1 as address,ci.address2 as addr,ci.landmark as landmark,ci.city as city,ci.state as state,ci.pin as pin,ci.phone as phone,ci.contact_Person as contactPerson,ci.contact_No as contactNo,ci.client_Theme as theme,t.theme_Name as themeName,ci.reg_Date as regDate, ci.email_Id as email,ci.dept_Id as deptId,ci.image_Path as editPath from clientinfo ci join themes t on ci.client_Theme= t.theme_Id where client_Id='"
								+ clientId + "'",
								new BeanPropertyRowMapper<ClientInfoBean>(
										ClientInfoBean.class));
			} else if (id == "deptId") {
				clientBean = getJdbcTemplate()
						.query("select client_Id as clientId,client_Name as clientName,address1 as address,address2 as addr,landmark as landmark,city as city,state as state,pin as pin,phone as phone,contact_Person as contactPerson,contact_No as contactNo,t.theme_Name as theme,reg_Date as regDate,email_Id as email,ci.image_Path as editPath from clientinfo ci join themes t on ci.client_Theme= t.theme_Id where dept_Id ='"
								+ clientId
								+ "' and client_Status!=2 order by client_Name",
								new BeanPropertyRowMapper<ClientInfoBean>(
										ClientInfoBean.class));
			} else {
				clientBean = getJdbcTemplate()
						.query("select client_Id as clientId,client_Name as clientName,address1 as address,address2 as addr,landmark as landmark,city as city,state as state,pin as pin,phone as phone,contact_Person as contactPerson,contact_No as contactNo,t.theme_Name as theme,reg_Date as regDate,email_Id as email,ci.image_Path as editPath from clientinfo ci join themes t on ci.client_Theme= t.theme_Id where client_Name like'"
								+ clientId
								+ "%' and client_Status!=2 order by client_Name",
								new BeanPropertyRowMapper<ClientInfoBean>(
										ClientInfoBean.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientBean;
	}

}
