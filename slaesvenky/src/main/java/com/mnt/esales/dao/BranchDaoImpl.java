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
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;

/**
 * @author Naresh
 * 
 */
@Repository("branchDao")
public class BranchDaoImpl extends MntDaoSupport implements BranchDao {
	boolean flag = true;
	@Autowired
	CustomIdDao custIdDao;
	@Autowired 
	CustomConnection  customConnection;
	@Autowired
	HttpServletRequest request;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public boolean saveBranch(Object object, boolean status) {
		String clientId = null;
		String maxId = null;
		try {
			HttpSession ses = request.getSession(false);
			Calendar cal = Calendar.getInstance();
			String regDate = dateFormat.format(cal.getTime());
			String branchId=null;
			BranchBean branchBean = (BranchBean) object;
			JdbcTemplate jdbcTemplate=getJdbcTemplate();
			if (ses != null) {
				String ca = (String) ses.getAttribute("admin");

				if (ca.equals("clientadmin")) {
					clientId = (String) ses.getAttribute("clientId");
				} else {
					clientId = branchBean.getClientId();
				}
			} else {
				clientId = branchBean.getClientId();
			}
		
			if (status) {
				maxId = custIdDao.getMaxId("select max(branch_Code) from branch");
		      branchId=clientId+maxId;
			}
			else
			{
				jdbcTemplate=customConnection.getJdbcTemplate();
				branchId=branchBean.getBranchId();
				maxId = custIdDao.getMaxIdForUpload("select max(branch_Code) from branch");
			}
			if (maxId != null) {
				String sql = "insert into branch(branch_Id,branch_Name,city,state,pin,phone,contact_Person,contact_No,address1,address2,client_Id,reg_Date,landmark,branch_Theme,image_Path,branch_Code,email_Id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				jdbcTemplate.update(
						sql,
						new Object[] { branchId,
								branchBean.getBranchName(),
								branchBean.getCity(), branchBean.getState(),
								branchBean.getPinNo(), branchBean.getPhone(),
								branchBean.getContactPerson(),
								branchBean.getContactNo(),
								branchBean.getAddress1(),
								branchBean.getAddress2(), clientId, regDate,
								branchBean.getLandmark(),
								branchBean.getBranchTheme(),
								branchBean.getLogoPath(), maxId,
								branchBean.getEmailId() });
		
				jdbcTemplate
						.update("insert into branch_client(client_Id,branch_Id) values(?,?)",
								new Object[] { clientId,branchId});

			} else {
				maxId="B0001";
				if (status) {
				      branchId=clientId+maxId;
					}
					
				String sql = "insert into branch(branch_Id,branch_Name,city,state,pin,phone,contact_Person,contact_No,address1,address2,client_Id,reg_Date,landmark,branch_Theme,image_Path,branch_Code,email_Id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				jdbcTemplate.update(
						sql,
						new Object[] {branchId,
								branchBean.getBranchName(),
								branchBean.getCity(), branchBean.getState(),
								branchBean.getPinNo(), branchBean.getPhone(),
								branchBean.getContactPerson(),
								branchBean.getContactNo(),
								branchBean.getAddress1(),
								branchBean.getAddress2(), clientId, regDate,
								branchBean.getLandmark(),
								branchBean.getBranchTheme(),
								branchBean.getLogoPath(),maxId,
								branchBean.getEmailId() });
logger.debug("in branch clentid iss=="+clientId+"==branchid=="+branchId);
jdbcTemplate.update("insert into branch_client(client_Id,branch_Id) values(?,?)",
								new Object[] { clientId, branchId });
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateBranch(Object object) {
		String clientId = null;
		try {
			BranchBean branchBean = (BranchBean) object;
			Calendar cal = Calendar.getInstance();
			String regDate = dateFormat.format(cal.getTime());
			HttpSession ses = request.getSession(false);
			if (ses != null) {
				String ca = (String) ses.getAttribute("admin");
				if (ca.equals("clientadmin")) {
					clientId = (String) ses.getAttribute("clientId");
				} else {
					clientId = branchBean.getClientId();
				}
			} else {
				clientId = branchBean.getClientId();
			}
			String sql = "update branch set branch_Name=?,city=?,state=?,pin=?,phone=?,contact_Person=?,contact_No=?,address1=?,address2=?,client_Id=?,reg_Date=?,landmark=?,branch_Theme=?,image_Path=?,email_Id=?,branch_Code=? where branch_Id=?";
			getJdbcTemplate().update(
					sql,
					new Object[] { branchBean.getBranchName(),
							branchBean.getCity(), branchBean.getState(),
							branchBean.getPinNo(), branchBean.getPhone(),
							branchBean.getContactPerson(),
							branchBean.getContactNo(),
							branchBean.getAddress1(), branchBean.getAddress2(),
							clientId, regDate, branchBean.getLandmark(),
							branchBean.getBranchTheme(),
							branchBean.getLogoPath(), branchBean.getEmailId(),
							branchBean.getBranchCode(),
							branchBean.getBranchId() });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteBranch(String BranchId) {
		try {

			String sql = "delete from branch where branch_Id=?";
			getJdbcTemplate().update(sql, new Object[] { BranchId });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<BranchBean> searchBranch() {
		List<BranchBean> branchBean = null;
		HttpSession ses = request.getSession(false);
		try {
			branchBean = getJdbcTemplate()
					.query("select b.branch_Id as branchId,b.branch_Name as branchName,b.city as city,b.state as state,b.pin as pinNo,b.phone as phone,b.contact_Person as contactPerson,b.contact_No as contactNo,b.address1 as address1,b.address2 as address2,b.client_Id as clientId,c.client_Name as clientName,b.reg_Date as regDate,b.landmark as landmark,b.branch_Theme as branchTheme,b.status as status,b.email_Id as emailId from branch b,clientinfo c where b.client_Id=c.client_Id and b.client_Id='"
							+ (String) ses.getAttribute("clientId")
							+ "' order by b.branch_Name",
							new BeanPropertyRowMapper<BranchBean>(
									BranchBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchBean;
	}

	@Override
	public List<BranchBean> searchBranch(String BranchId, String id) {
		List<BranchBean> branchBean = null;
		HttpSession ses = request.getSession(false);
		try {
			if (id == "id") {
				branchBean = getJdbcTemplate()
						.query("select branch_Id as branchId,branch_Name as branchName,city as city,state as state,pin as pinNo,phone as phone,contact_Person as contactPerson,contact_No as contactNo,address1 as address1,address2 as address2,client_Id as clientId,reg_Date as regDate,landmark as landmark,t.theme_Id as branchTheme,status as status,image_Path as editPath,email_Id as emailId,branch_Code as branchCode from branch b join themes t on b.branch_Theme= t.theme_Id where branch_Id='"
								+ BranchId + "'",
								new BeanPropertyRowMapper<BranchBean>(
										BranchBean.class));
			} else if (id == "clientId") {
				branchBean = getJdbcTemplate()
						.query("select b.branch_Id as branchId,b.branch_Name as branchName,b.city as city,b.state as state,b.pin as pinNo,b.phone as phone,b.contact_Person as contactPerson,b.contact_No as contactNo,b.address1 as address1,b.address2 as address2,b.client_Id as clientId,c.client_Name as clientName,b.reg_Date as regDate,b.landmark as landmark,b.branch_Theme as branchTheme,b.status as status,b.image_Path as editPath,b.email_Id as emailId from branch b,clientinfo c where b.client_Id=c.client_Id and b.client_Id ='"
								+ BranchId + "' order by b.branch_Name",
								new BeanPropertyRowMapper<BranchBean>(
										BranchBean.class));
			} else {
				branchBean = getJdbcTemplate()
						.query("select b.branch_Id as branchId,b.branch_Name as branchName,b.city as city,b.state as state,b.pin as pinNo,b.phone as phone,b.contact_Person as contactPerson,b.contact_No as contactNo,b.address1 as address1,b.address2 as address2,b.client_Id as clientId,c.client_Name as clientName,b.reg_Date as regDate,b.landmark as landmark,b.branch_Theme as branchTheme,b.status as status,b.image_Path as editPath,b.email_Id as emailId from branch b,clientinfo c where b.client_Id=c.client_Id and b.branch_Name like'"
								+ BranchId
								+ "%' and b.client_Id='"
								+ (String) ses.getAttribute("clientId")
								+ "' order by b.branch_Name",
								new BeanPropertyRowMapper<BranchBean>(
										BranchBean.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchBean;
	}
}
