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
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mnt.esales.bean.ExpensesBean;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;

/**
 * @author srinivas
 * 
 */
@Repository("expensesDao")
public class ExpensesDaoImpl extends MntDaoSupport implements ExpensesDao {

	boolean flag = true;
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	@Autowired
	CustomConnection customConnection;

	@Override
	public boolean ExpensesSave(Object object,boolean status) {
		String maxId=null;
		JdbcTemplate jdbcTemplate=null;
		String expId=null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime());
			ExpensesBean expBean = (ExpensesBean) object;
			if(status){
			 maxId = custIdDao
					.getMaxId("select max(expenses_Id) from expense_details");
			 jdbcTemplate=getJdbcTemplate();
			 expId=maxId;
			}
			else
			{
				 maxId = custIdDao.getMaxIdForUpload("select max(expenses_Id) from expense_details");	
				 jdbcTemplate=customConnection.getTemplateConnection();
				 expId=expBean.getExpenses_Id();
			}
		
			int total = jdbcTemplate.queryForInt("select sum(amount) from expense_details");
			if (maxId != null) {
				String sql = "insert into expense_details(expenses_Id,description,amount,billno) values(?,?,?,?)";

				jdbcTemplate.update(
						sql,
						new Object[] { expId, expBean.getDescription(),
								expBean.getAmount(), expBean.getBillno() });
				String expInfo = "insert into expense_info(expenses_Id,expenses_Date,expenses_Total,status) values(?,?,?,0)";
				jdbcTemplate.update(expInfo,
						new Object[] { expId, date, total });
				HttpSession session = request.getSession(false);
				
				String userId = (String) session.getAttribute("userName");
				
				String userid = jdbcTemplate.queryForObject(
						"select user_Id from users where user_Name='" + userId
								+ "'", String.class);
				// String branch_Id = (String) session.getAttribute("branchId");
				String expbranch = "insert into expenses_branch(expenses_Id,user_Id,branch_Id) values(?,?,?)";
				jdbcTemplate.update(expbranch,
						new Object[] { expId, userid, expBean.getBranchId() });

			} else {
				
				String sql = "insert into expense_details(description,amount,billno) values(?,?,?)";
				jdbcTemplate.update(
						sql,
						new Object[] { expBean.getDescription(),
								expBean.getAmount(), expBean.getBillno() });
				String expInfo = "insert into expense_info(expenses_Id,expenses_Date,expenses_Total,status) values(?,?,?,0)";
				jdbcTemplate.update(expInfo,
						new Object[] { "E0001", date, total });
				
				HttpSession session = request.getSession(false);
				String userId = (String) session.getAttribute("userName");
				String userid = getJdbcTemplate().queryForObject(
						"select user_Id from users where user_Name='" + userId
								+ "'", String.class);
				// String branch_Id = (String) session.getAttribute("branchId");
				String expbranch = "insert into expenses_branch(expenses_Id,user_Id,branch_Id) values(?,?,?)";
				jdbcTemplate
						.update(expbranch,
								new Object[] { "E0001", userid,
										expBean.getBranchId() });

			}

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean ExpensesUpdate(Object object) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime());
			ExpensesBean expBean = (ExpensesBean) object;
			int total = getJdbcTemplate().queryForInt("select sum(amount) from expense_details");
			String sql = "update expense_details set description=?,amount=?,billno=? where expenses_Id=?";
			getJdbcTemplate().update(
					sql,
					new Object[] { expBean.getDescription(),
							expBean.getAmount(), expBean.getBillno(),
							expBean.getExpenses_Id() });
			String userId = (String) session.getAttribute("userName");
			String userid = getJdbcTemplate().queryForObject("select user_Id from users where user_Name='" + userId
							+ "'", String.class);
			String expbranch = "update expenses_branch set user_Id=?,branch_Id=? where expenses_id=?";
			getJdbcTemplate().update(
					expbranch,
					new Object[] { userid, expBean.getBranchId(),
							expBean.getExpenses_Id() });
			String expinfo = "update expense_info set expenses_Date=?,expenses_Total=?";
			getJdbcTemplate().update(expinfo, new Object[] { date, total });
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean ExpensesDelete(String expenses_Id) {
		try {

			String sql = "delete from expense_details where expenses_Id=?";
			getJdbcTemplate().update(sql, new Object[] { expenses_Id });

		} catch (ConstraintViolationException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<ExpensesBean> ExpensesSearch() {
		List<ExpensesBean> expBean = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		try {
			String clientId = (String) session.getAttribute("clientId");
			String userid = (String) session.getAttribute("userId");
			String sql = "SELECT b.branch_Id FROM branch b JOIN permissions p ON b.`branch_Id`=p.`branch_Id` WHERE b.client_Id='"
					+ clientId + "' AND p.user_Id='" + userid + "'";
		String branchId = getJdbcTemplate().queryForObject(sql,
					String.class);
			expBean = getJdbcTemplate()
					.query("SELECT ed.expenses_Id AS expenses_Id,ed.description AS description,ed.amount AS amount,ed.billno AS billno  FROM expense_details ed JOIN `expenses_branch` eb ON eb.`expenses_Id`=ed.`expenses_Id` JOIN `expense_info` ei ON ei.`expenses_Id`=ed.`expenses_Id` WHERE eb.`branch_Id`='"
							+ branchId
							+ "' AND  ei.`expenses_Date`='"
							+ date
							+ "'",
							new BeanPropertyRowMapper<ExpensesBean>(
									ExpensesBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expBean;
	}

	@Override
	public List<ExpensesBean> ExpensesEdit(String expenses_Id, String id) {
		List<ExpensesBean> expBean = null;
		try {
			if (id == "id") {
				expBean = getJdbcTemplate()
						.query("SELECT ed.expenses_Id AS expenses_Id,ed.description AS description,ed.amount AS amount,ed.billno AS billno,eb.`branch_Id` AS branchId FROM expense_details ed JOIN `expenses_branch` eb ON eb.`expenses_Id`=ed.`expenses_Id` WHERE ed.expenses_Id='"
								+ expenses_Id + "'",
								new BeanPropertyRowMapper<ExpensesBean>(
										ExpensesBean.class));
			} else {
				expBean = getJdbcTemplate()
						.query("SELECT ed.expenses_Id AS expenses_Id,ed.description AS description,ed.amount AS amount,ed.billno AS billno,eb.`branch_Id` AS branchId FROM expense_details ed JOIN `expenses_branch` eb ON eb.`expenses_Id`=ed.`expenses_Id` WHERE ed.expenses_Id='"
								+ expenses_Id + "'",
								new BeanPropertyRowMapper<ExpensesBean>(
										ExpensesBean.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return expBean;
	}

	@Override
	public List<ExpensesBean> ExpensesSearchWithId(String branchId) {
		List<ExpensesBean> expBean = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		try {
			expBean = getJdbcTemplate()
					.query("SELECT ed.expenses_Id AS expenses_Id,ed.description AS description,ed.amount AS amount,ed.billno AS billno  FROM expense_details ed JOIN `expenses_branch` eb ON eb.`expenses_Id`=ed.`expenses_Id` JOIN `expense_info` ei ON ei.`expenses_Id`=ed.`expenses_Id` WHERE eb.`branch_Id`='"+branchId+"' AND  ei.`expenses_Date`='"+date+"'",
							new BeanPropertyRowMapper<ExpensesBean>(
									ExpensesBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expBean;
	}

}
