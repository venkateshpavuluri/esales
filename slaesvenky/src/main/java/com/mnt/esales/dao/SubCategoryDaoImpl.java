/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.SubCategoryBean;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;

@Repository("subCategoryDao")
public class SubCategoryDaoImpl extends MntDaoSupport implements SubCategoryDao {

	boolean flag = true;
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	HttpServletRequest request;
	@Autowired
	CustomConnection customConnection;

	@Override
	public List<SubCategoryBean> listOfCategory(String deptId) {
		List<SubCategoryBean> listOfCategory = null;
		HttpSession session = request.getSession(false);
		try {
			String clientId = (String) session.getAttribute("clientId");
			String sql = "SELECT cc.client_Id,C.category_Id, C.category_Desc  FROM category_dept CD"
					+ " JOIN category C ON C.category_Id= CD.category_Id "
					+ "JOIN department D ON D.dept_Id= CD.dept_Id join client_category cc on cc.category_Id = C.C.category_Id AND CD.dept_Id='"
					+ deptId + "' and cc.client_Id= '" + clientId + "'";
			listOfCategory = getJdbcTemplate().query(
					sql,
					new BeanPropertyRowMapper<SubCategoryBean>(
							SubCategoryBean.class));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return listOfCategory;
	}

	@Override
	public boolean saveSubCategory(Object object, boolean status) {
		String maxId=null;
		String subCatId=null;
		JdbcTemplate jdbcTemplate=null;
		try {
			HttpSession session = request.getSession(false);
			String catId=(String)session.getAttribute("categoryId");
			SubCategoryBean subCategoryBean = (SubCategoryBean) object;
			if(status)
			{
			 maxId = custIdDao
					.getMaxId("select max(subCategory_Code) from subcategory");
			 subCatId=(String)session.getAttribute("categoryId")+maxId;
			 jdbcTemplate=getJdbcTemplate();
			}
			else
			{
				 maxId = custIdDao.getMaxIdForUpload("select max(subCategory_Code) from subcategory");
				 subCatId=subCategoryBean.getSubCategoryId();
				 jdbcTemplate=customConnection.getTemplateConnection();
			}
			
		
			/*String catSql = "select category_Code from category where category_Id='"+subCategoryBean.getCategoryId()+"'";
			String catId = getJdbcTemplate().queryForObject(catSql, String.class);*/
			
			
			if (!"admin".equals(session.getAttribute("admin"))){
			if (maxId != null) {
				String sql = "insert into subcategory(subCategory_Id,subCategory_Desc, subCategory_Code) values(?,?,?)";
				jdbcTemplate.update(
						sql,
						new Object[] { subCatId,
								subCategoryBean.getSubCategoryDesc(), maxId });
				String sqlCat = "insert into subcat_category( category_Id,subcategory_Id) values(?,?)";
				jdbcTemplate
						.update(sqlCat,
								new Object[] { subCategoryBean.getCategoryId(),
								subCatId });
				String sqlSubCat = "insert into client_subcategory( client_Id,subcat_Id) values(?,?)";
				jdbcTemplate
						.update(sqlSubCat,
								new Object[] {
										session.getAttribute("clientId"), subCatId });
			if(status)
			{
				session.setAttribute("subcategoryId", subCatId);
				logger.debug("category id in subcategory iss==="
						+ subCategoryBean.getCategoryId());
				session.setAttribute("categoryId",
						subCategoryBean.getCategoryId());
			}
			} else {
				maxId="B0001";
				if(status)
				{
					subCatId=catId+maxId;
				}
				String sql = "insert into subcategory(subCategory_Id,subCategory_Desc, subCategory_Code) values(?,?,?)";
				jdbcTemplate.update(sql,
						new Object[] {subCatId, subCategoryBean.getSubCategoryDesc(),maxId });
				String sqlCat = "insert into subcat_category( category_Id,subCategory_Id) values(?,?)";
				jdbcTemplate
						.update(sqlCat,
								new Object[] { subCategoryBean.getCategoryId(),
								subCatId });
				String sqlSubCat = "insert into client_subcategory( client_Id,subcat_Id) values(?,?)";
				jdbcTemplate.update(
						sqlSubCat,
						new Object[] { session.getAttribute("clientId"),
								subCatId });
				if(status)
				{
				session.setAttribute("subcategoryId", "B0001");
				session.setAttribute("categoryId",
						subCategoryBean.getCategoryId());
				}
			}
			}else{
				flag=false;
			}

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<SubCategoryBean> subCategorySearch() {

		List<SubCategoryBean> subCatLlistBean = null;
		HttpSession session = request.getSession(false);
		String sql = null;
		try {
			if ("admin".equals(session.getAttribute("admin"))) {
				sql = " SELECT S.subCategory_Code, C.category_Id, S.subCategory_Id as subCategoryId,S.subCategory_Desc as subCategoryDesc, C.category_Desc as categoryDesc FROM subcat_category SD "
						+ "JOIN subcategory S ON SD.subCategory_Id = S.subCategory_Id "
						+ "JOIN category C ON SD.category_Id = C.category_Id ORDER BY S.subCategory_Desc";
			} 
			/*else if(String.valueOf(session.getAttribute("categoryId"))==null ||String.valueOf(session.getAttribute("categoryId"))==""){
				 sql = "SELECT S.subCategory_Code as subCategoryCode, cs.client_Id,C.category_Id, S.subCategory_Id as subCategoryId,S.subCategory_Desc as subCategoryDesc  , C.category_Desc as categoryDesc FROM subcat_category SD "
						+ "JOIN subcategory S ON SD.subCategory_Id = S.subCategory_Id "
						+ "JOIN category C ON SD.category_Id = C.category_Id join client_subcategory cs on cs.`subcat_Id`=S.`subCategory_Id` and cs.client_Id='"
						+ (String)session.getAttribute("clientId"); 
			}*/
			else {
				//String categoryId = String.valueOf(session.getAttribute("categoryId"));
				String clientId = String.valueOf(session
						.getAttribute("clientId"));
				sql = " SELECT S.subCategory_Code as subCategoryCode, cs.client_Id,C.category_Id, S.subCategory_Id as subCategoryId,S.subCategory_Desc as subCategoryDesc, C.category_Desc as categoryDesc FROM subcat_category SD "
						+ "JOIN subcategory S ON SD.subCategory_Id = S.subCategory_Id "
						+ "JOIN category C ON SD.category_Id = C.category_Id "
						+ "JOIN `client_subcategory` cs ON cs.`subcat_Id`=S.`subCategory_Id`"
						+ "where  cs.client_Id='"
						+ clientId
						+ "' ORDER BY S.subCategory_Desc ";
			}
			subCatLlistBean = getJdbcTemplate().query(
					sql,
					new BeanPropertyRowMapper<SubCategoryBean>(
							SubCategoryBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subCatLlistBean;
	}

	@Override
	public List<SubCategoryBean> search(String subCatId, String id) {
		List<SubCategoryBean> catBean = null;
		HttpSession session = request.getSession(false);
		String clientId= (String)session.getAttribute("clientId");
		try {
			if (id == "id") {
				String sql = " SELECT S.subCategory_Code as subCategoryCode, C.category_Id, S.subCategory_Id as subCategoryId,S.subCategory_Desc as subCategoryDesc  , C.category_Desc as categoryDesc FROM subcat_category SD "
						+ "JOIN subcategory S ON SD.subCategory_Id = S.subCategory_Id "
						+ "JOIN category C ON SD.category_Id = C.category_Id where S.subCategory_Id='"
						+ subCatId + "'";
				catBean = getJdbcTemplate().query(
						sql,
						new BeanPropertyRowMapper<SubCategoryBean>(
								SubCategoryBean.class));
			} else if (id == "name") {
				String sql="SELECT S.subCategory_Code as subCategoryCode,cs.client_Id,C.category_Id,S.subcategory_Id as subCategoryId,S.subCategory_Desc as subCategoryDesc, C.category_Desc as CategoryDesc FROM subcat_category SD "
								+ "JOIN subcategory S ON SD.subCategory_Id = S.subCategory_Id "
								+ "JOIN category C ON SD.category_Id = C.category_Id join client_subcategory cs on cs.`subcat_Id`=S.`subCategory_Id` WHERE S.subCategory_Desc like '"
								+ subCatId
								+ "%' and cs.client_Id='"
								+ clientId
								+ "'";
				catBean = getJdbcTemplate()
						.query(sql,new BeanPropertyRowMapper<SubCategoryBean>(SubCategoryBean.class));
			} else if (id == null && subCatId != null) {
				String psql = " SELECT S.subCategory_Code as subCategoryCode, cs.client_Id,C.category_Id, S.subCategory_Id as subCategoryId,S.subCategory_Desc as subCategoryDesc  , C.category_Desc as categoryDesc FROM subcat_category SD "
						+ "JOIN subcategory S ON SD.subCategory_Id = S.subCategory_Id "
						+ "JOIN category C ON SD.category_Id = C.category_Id join client_subcategory cs on cs.`subcat_Id`=S.`subCategory_Id` where C.category_Id='"
						+ subCatId
						+ "' and cs.client_Id='"
						+ clientId + "'";
				catBean = getJdbcTemplate().query(
						psql,
						new BeanPropertyRowMapper<SubCategoryBean>(
								SubCategoryBean.class));
			} else {
				if ("admin".equals((String) session.getAttribute("admin"))) {
					String psql = "SELECT S.subCategory_Code as subCategoryCode, C.category_Id, S.subCategory_Id as subCategoryId,S.subCategory_Desc as subCategoryDesc  , C.category_Desc as categoryDesc FROM subcat_category SD "
							+ "JOIN subcategory S ON SD.subCategory_Id = S.subCategory_Id "
							+ "JOIN category C ON SD.category_Id = C.category_Id ";
					catBean = getJdbcTemplate().query(
							psql,
							new BeanPropertyRowMapper<SubCategoryBean>(
									SubCategoryBean.class));
				} else {
					String psql = "SELECT S.subCategory_Code as subCategoryCode, cs.client_Id,C.category_Id, S.subCategory_Id as subCategoryId,S.subCategory_Desc as subCategoryDesc  , C.category_Desc as categoryDesc FROM subcat_category SD "
							+ "JOIN subcategory S ON SD.subCategory_Id = S.subCategory_Id "
							+ "JOIN category C ON SD.category_Id = C.category_Id join client_subcategory cs on cs.`subcat_Id`=S.`subCategory_Id` and cs.client_Id='"
							+ clientId + "'";
					catBean = getJdbcTemplate().query(
							psql,
							new BeanPropertyRowMapper<SubCategoryBean>(
									SubCategoryBean.class));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return catBean;
	}

	@Override
	public boolean subCatUpdate(Object object) {
		try {
			SubCategoryBean catBean = (SubCategoryBean) object;
			String sql = "update subcategory set subcategory_Desc=? where subCategory_Id=?";
			getJdbcTemplate().update(
					sql,
					new Object[] { catBean.getSubCategoryDesc(),
							catBean.getSubCategoryId() });
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean subCatDelete(String subCatId) {
		try {
			String sql = "delete from subcategory where subCategory_Id=?";
			getJdbcTemplate().update(sql, new Object[] { subCatId });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<SubCategoryBean> subCategoryDuplicate(String catId,
			String subCatDesc) {
		List<SubCategoryBean> dupList = null;
		HttpSession session = request.getSession(false);
		String categoryId = (String) session.getAttribute("categoryId");
		// System.out.println(categoryId);
		try {
			if (catId != null) {
				String sql = "SELECT COUNT(*) as dupId FROM subcat_category sc "
						+ "JOIN `subcategory` s ON sc.`subcategory_Id`= s.`subCategory_Id` "
						+ "WHERE s.subCategory_Desc='"
						+ subCatDesc
						+ "' AND s.`subCategory_Id` !='"
						+ catId
						+ "' AND sc.category_Id='" + categoryId + "'";
				dupList = getJdbcTemplate().query(
						sql,
						new BeanPropertyRowMapper<SubCategoryBean>(
								SubCategoryBean.class));
			} else {
				String sql = "SELECT COUNT(*) as dupId FROM subcat_category sc "
						+ "JOIN `subcategory` s ON sc.`subcategory_Id`= s.`subCategory_Id` "
						+ "WHERE s.subCategory_Desc='"
						+ subCatDesc
						+ "'AND sc.category_Id='" + categoryId + "'";
				dupList = getJdbcTemplate().query(
						sql,
						new BeanPropertyRowMapper<SubCategoryBean>(
								SubCategoryBean.class));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dupList;
	}

}
