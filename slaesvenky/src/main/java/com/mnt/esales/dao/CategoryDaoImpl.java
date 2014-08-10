/**
 * 
 */
package com.mnt.esales.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.DepartmentBean;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;

/**
 * @author yogi
 * 
 */
@Repository("catDao")
public class CategoryDaoImpl extends MntDaoSupport implements CategoryDao {

	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	HttpServletRequest request;
	@Autowired
	CustomConnection customConnection;

	private Logger logger = Logger.getLogger(CategoryDaoImpl.class);
	@Autowired
	HttpSession session;

	@Override
	public boolean saveCategory(Object object, boolean status) {
		boolean flag = true;
		JdbcTemplate jdbcTemplate=null;
		String catId=null;
		try {
			String maxId=null;
			CategoryBean categoryBean = (CategoryBean) object;
			HttpSession httpSession = request.getSession(false);
			String clientId = (String) httpSession.getAttribute("clientId");
			if(status){
			 maxId= custIdDao.getMaxId("select max(category_Code) from category");
			 jdbcTemplate=getJdbcTemplate();
			 catId=clientId+maxId;
			}
			else
			{
				maxId=custIdDao.getMaxIdForUpload("select max(category_Code) from category");
				jdbcTemplate=customConnection.getTemplateConnection();
				 catId=categoryBean.getCategoryId();
			}
		
			String deptId = "";
			if (!"admin".equals((String) session.getAttribute("admin"))) {
				if (maxId != null) {
					String sql = "insert into category(category_Id,category_Desc, category_Code) values(?,?,?)";
					jdbcTemplate.update(
							sql,
							new Object[] { catId,
									categoryBean.getCategoryDesc(), maxId });
					deptId = (String) httpSession.getAttribute("deptId");
					String sqlCat = "insert into category_dept( dept_Id,category_Id) values(?,?)";
					jdbcTemplate.update(sqlCat,
							new Object[] { deptId,catId });
					String sqlClientCat = "insert into client_category( client_Id,category_Id) values(?,?)";
					jdbcTemplate.update(sqlClientCat,
							new Object[] { clientId, catId});
					if(status)
					session.setAttribute("categoryId", catId);

				} else {
					maxId="A0001";
					if(status)
					{
						catId=clientId+"A0001";
					}
					String sql = "insert into category(category_Id,category_Desc, category_Code) values(?,?,?)";
					jdbcTemplate.update(
							sql,
							new Object[] {catId,
									categoryBean.getCategoryDesc() ,"A0001"});
					/*
					 * if
					 * ("admin".equals((String)session.getAttribute("admin"))) {
					 * deptId = categoryBean.getDeptId(); } else { deptId =
					 * (String) httpSession.getAttribute("deptId"); }
					 */
					deptId = (String) httpSession.getAttribute("deptId");
					String sqlCat = "insert into category_dept( dept_Id,category_Id) values(?,?)";
					jdbcTemplate.update(sqlCat,
							new Object[] { deptId,catId});
					String sqlClientCat = "insert into client_category( client_Id,category_Id) values(?,?)";
					jdbcTemplate.update(sqlClientCat,
							new Object[] { clientId, catId});
					session.setAttribute("categoryId", catId);
				}
			}
			else{
				flag = false;
			}

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Map<String, String> populateCategory() {
		Map<String, String> map = new HashMap<String, String>();
		List<DepartmentBean> list = null;
		HttpSession session = request.getSession(false);
		String clientId = (String) session.getAttribute("clientId");
		try {
			String sql = null;
			if ("admin".equals((String) session.getAttribute("admin"))) {
				sql = "select d.dept_Id as deptId, d.dept_Desc as deptName from department d order by d.dept_Desc";
			} else {
				sql = "select d.dept_Id as deptId, d.dept_Desc as deptName from department d join "
						+ "dept_client dc on d.dept_Id = dc.dept_Id where dc.client_Id='"
						+ clientId + "' order by d.dept_Desc";
			}
			list = getJdbcTemplate().query(
					sql,
					new BeanPropertyRowMapper<DepartmentBean>(
							DepartmentBean.class));
			Iterator<DepartmentBean> itr = list.iterator();
			while (itr.hasNext()) {
				DepartmentBean object = itr.next();
				map.put(object.getDeptId(), object.getDeptName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return map;
	}

	@Override
	public List<CategoryBean> categorySearch() {
		List<CategoryBean> catLlistBean = null;
		HttpSession session = request.getSession(false);
		String sql = null;
		try {
			if ("admin".equals((String) session.getAttribute("admin"))) {
				sql = "SELECT C.category_Code as categoryCode, C.category_Id as categoryId, C.category_Desc as categoryDesc, D.dept_Id as deptId ,D.dept_desc as deptName FROM category_dept CD JOIN category C ON C.category_Id= CD.category_Id  JOIN department D ON D.dept_Id= CD.dept_Id  order by C.category_Desc";
			} else {
				String deptId = (String) session.getAttribute("deptId");
				String clientId = (String) session.getAttribute("clientId");
				sql = "SELECT C.category_Code as categoryCode, C.category_Id as categoryId, C.category_Desc as categoryDesc, D.dept_Id as deptId ,D.dept_desc as deptName FROM category_dept CD JOIN category C ON C.category_Id= CD.category_Id "
						+ " JOIN department D ON D.dept_Id= CD.dept_Id and D.dept_Id ='"
						+ deptId
						+ "'JOIN `client_category` cc ON cc.`category_Id`=C.`category_Id` and cc.client_Id='"
						+ clientId + "'order by C.category_Desc";

			}

			catLlistBean = getJdbcTemplate()
					.query(sql,
							new BeanPropertyRowMapper<CategoryBean>(
									CategoryBean.class));
			Iterator<CategoryBean> itr = catLlistBean.iterator();
			while (itr.hasNext()) {
				CategoryBean catBean = (CategoryBean) itr.next();
				session.setAttribute("deptId", catBean.getDeptId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return catLlistBean;
	}

	@Override
	public List<CategoryBean> search(String catId, String id) {
		List<CategoryBean> catBean = null;
		HttpSession session = request.getSession(false);
		String clientId = (String) session.getAttribute("clientId");
		try {
			if (!"admin".equals((String) session.getAttribute("admin"))) {
				if (id == "id") {
					String sql = "SELECT C.category_Id,C.category_Code as categoryCode, C.category_Desc FROM category C WHERE C.category_Id='"
							+ catId + "'";
					catBean = getJdbcTemplate().query(
							sql,
							new BeanPropertyRowMapper<CategoryBean>(
									CategoryBean.class));
				} else if (id == "name") {
					catBean = getJdbcTemplate()
							.query("SELECT C.category_Code as categoryCode, C.category_Id, C.category_Desc,D.dept_desc AS deptName FROM category_dept CD JOIN category C ON C.category_Id= CD.category_Id JOIN department D ON D.dept_Id= CD.dept_Id JOIN  dept_client dc ON dc.`dept_Id` = D.`dept_Id` WHERE dc.`client_Id`='"
									+ clientId
									+ "' AND C.category_Desc LIKE '"
									+ catId + "%'",
									new BeanPropertyRowMapper<CategoryBean>(
											CategoryBean.class));
				} else if (id != "id" && id != "name" && id != "total") {
					String parentSql = "SELECT C.category_Code as categoryCode,C.category_Id as categoryId, C.category_Desc as categoryDesc, D.dept_Id as deptId, D.dept_desc as deptName FROM category_dept CD JOIN category C ON C.category_Id= CD.category_Id JOIN department D ON D.dept_Id= CD.dept_Id WHERE D.dept_Id='"
							+ id + "'";
					catBean = getJdbcTemplate().query(
							parentSql,
							new BeanPropertyRowMapper<CategoryBean>(
									CategoryBean.class));
				} else {
					String sql = "SELECT C.category_Code as categoryCode,C.category_Id as categoryId, C.category_Desc as categoryDesc, D.dept_Id as deptId, D.dept_desc as deptName FROM category_dept CD JOIN category C ON C.category_Id= CD.category_Id JOIN department D ON D.dept_Id= CD.dept_Id and D.dept_Id='"
							+ (String) session.getAttribute("deptId") + "'";
					catBean = getJdbcTemplate().query(
							sql,
							new BeanPropertyRowMapper<CategoryBean>(
									CategoryBean.class));
				}
			} else {
				if (id == "id") {
					String sql = "SELECT C.category_Id, C.category_Desc FROM category C WHERE C.category_Id='"
							+ catId + "'";
					catBean = getJdbcTemplate().query(
							sql,
							new BeanPropertyRowMapper<CategoryBean>(
									CategoryBean.class));
				} else if (id == "name") {
					catBean = getJdbcTemplate()
							.query("SELECT C.category_Code as categoryCode, C.category_Id, C.category_Desc,D.dept_desc AS deptName FROM category_dept CD JOIN category C ON C.category_Id= CD.category_Id JOIN department D ON D.dept_Id= CD.dept_Id AND C.category_Desc LIKE '"
									+ catId + "%'",
									new BeanPropertyRowMapper<CategoryBean>(
											CategoryBean.class));
				} else if (id != "id" && id != "name" && id != "total") {
					String parentSql = "SELECT C.category_Code as categoryCode, C.category_Id as categoryId, C.category_Desc as categoryDesc, D.dept_Id as deptId, D.dept_desc as deptName FROM category_dept CD JOIN category C ON C.category_Id= CD.category_Id JOIN department D ON D.dept_Id= CD.dept_Id WHERE D.dept_Id='"
							+ id + "'";
					catBean = getJdbcTemplate().query(
							parentSql,
							new BeanPropertyRowMapper<CategoryBean>(
									CategoryBean.class));
				} else {
					String sql = "SELECT C.category_Code as categoryCode, C.category_Id as categoryId, C.category_Desc as categoryDesc, D.dept_Id as deptId, D.dept_desc as deptName FROM category_dept CD JOIN category C ON C.category_Id= CD.category_Id JOIN department D ON D.dept_Id= CD.dept_Id and D.dept_Id='"
							+ (String) session.getAttribute("deptId") + "'";
					catBean = getJdbcTemplate().query(
							sql,
							new BeanPropertyRowMapper<CategoryBean>(
									CategoryBean.class));
				}
			}
			/**/

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return catBean;
	}

	@Override
	public boolean catUpdate(Object object) {
		boolean flag = true;
		try {
			CategoryBean catBean = (CategoryBean) object;
			String sql = "update category set category_Desc=? where category_Id=?";
			getJdbcTemplate().update(
					sql,
					new Object[] { catBean.getCategoryDesc(),
							catBean.getCategoryId() });
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public boolean catDelete(String catId) {
		boolean flag = true;
		try {
			String sql = "delete from category where category_Id=?";
			getJdbcTemplate().update(sql, new Object[] { catId });
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return flag;
	}
}
