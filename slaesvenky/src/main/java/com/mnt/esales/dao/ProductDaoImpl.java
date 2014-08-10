/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;
import com.mnt.esales.marshaling.Product;

/**
 * @author venkateshp
 *
 */
@Repository("productDao")
public class ProductDaoImpl extends MntDaoSupport implements ProductDao {
	@Autowired
	HttpServletRequest request;
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	CustomConnection customConnection;
	private Logger logger=Logger.getLogger(ProductDaoImpl.class);
private String sql;
	public boolean productSave(Object object,boolean status) {
		// TODO Auto-generated method stub
		boolean flag=false;
		ProductBean bean=(ProductBean)object;
		JdbcTemplate jdbcTemplate=null;
		int counts=0;
		try
		{
			String subId=null;
			String maxId=null;
		
			if(status)
			{
				 maxId = custIdDao.getMaxId("select max(product_Code) from product");
				 subId=bean.getSubCategoryId()+maxId;
				 jdbcTemplate=getJdbcTemplate();
					
			}
			else
			{
				 maxId = custIdDao.getMaxIdForUpload("select max(product_Code) from product");
				subId=bean.getProductId();
				jdbcTemplate=customConnection.getTemplateConnection();
			}
			
			logger.debug("scuCategory code iss==="+subId);
			 
			if(maxId!=null)
			{
				
	 sql="insert into product(product_Id,prod_Desc,prod_Display,product_Code) values(?,?,?,?)";
			int count=jdbcTemplate.update(sql,new Object[]{subId,bean.getProductName(),0,maxId});
			logger.info("product save id is=="+count);
			if(count!=0)
			{
				HttpSession session=request.getSession(false);
			
				
				/*String spMaxId=custIdDao.getMaxId("select max(client_ProductId) from client_product");
				logger.debug("clientProductId iss==="+spMaxId);*/
			
         String cPSql="insert into client_product(client_Id,product_Id) values(?,?)";	
          String clientId=String.valueOf(session.getAttribute("clientId"));
          jdbcTemplate.update(cPSql,new Object[]{clientId,subId});
				String sqlForPS="insert into prod_subcat(subCategory_Id,product_Id) values(?,?)";
				 counts=jdbcTemplate.update(sqlForPS,new Object[]{bean.getSubCategoryId(),subId});
				if(counts!=0)
				{
				flag=true;
				}
			}
			}
			else
			{
				maxId="P0001";
				if(status)
				{
					 subId=bean.getSubCategoryId()+maxId;
					 jdbcTemplate=getJdbcTemplate();
				}
				
				 sql="insert into product(product_Id,prod_Desc,prod_Display,product_Code) values(?,?,?,?)";
					int count=getJdbcTemplate().update(sql,new Object[]{subId,bean.getProductName(),0,maxId});
					logger.info("product save id is=="+count);
					if(count!=0)
					{
						HttpSession session=request.getSession(false);
						 String cPSql="insert into client_product(client_Id,product_Id) values(?,?)";	
				          String clientId=String.valueOf(session.getAttribute("clientId"));
				   int pcount=jdbcTemplate.update(cPSql,new Object[]{clientId,subId});
						
						String sqlForPS="insert into prod_subcat(subCategory_Id,product_Id) values(?,?)";
						if(pcount!=0)
						{
						 counts=jdbcTemplate.update(sqlForPS,new Object[]{bean.getSubCategoryId(),subId});
						}
						if(counts!=0)
						{
						flag=true;
						}
					}
			}
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public boolean productUpdate(Object object) {
		boolean flag=false;
try
{
	ProductBean bean=(ProductBean)object;
	sql="update product p set p.prod_Desc='"+bean.getProductName()+"' where p.product_Id='"+bean.getProductId()+"'";
	int updateCount=getJdbcTemplate().update(sql);
	if(updateCount!=0)
	{
		flag=true;
	}
}
catch(Exception e)
{
	logger.error(e.getMessage());
	e.printStackTrace();
}
		return flag;
	}
	public boolean productDelete(String productId) {
		boolean flag=false;
		int deleteCount=0;
	try
	{
	sql="delete from product where product_Id=?";	
 deleteCount=getJdbcTemplate().update(sql,new Object[]{productId});
	if(deleteCount!=0)
	{
		flag=true;
	}
	}
	catch(Exception e )
	{
	logger.error(e.getMessage());
	e.printStackTrace();
	flag=false;
	}
	return flag;
	}
	@Override
	public List<ProductBean> productSearch(String clientId) {
		// TODO Auto-generated method stub
		List<ProductBean> productBeans=null;
		String sql=null;
		try
		{
			if("all".equals(clientId))
			//sql="SELECT p.product_Id AS productId,p.prod_Desc AS productName FROM client_product cp  JOIN product p ON p.product_Id=cp.product_Id WHERE cp.client_Id='"+clientId+"'";
			{
				 sql="SELECT p.product_Id,p.prod_Desc AS productName,p.product_Code as productCode,sc.subCategory_Id AS subCategoryId,sc.subCategory_Desc AS subCategoryName,c.category_Id AS categoryId,c.category_Desc AS categoryName FROM  prod_subcat ps JOIN product p ON p.product_Id=ps.product_Id JOIN subcategory sc ON sc.subCategory_Id=ps.subCategory_Id JOIN subcat_category scc ON scc.subcategory_Id=ps.subCategory_Id JOIN category c ON scc.category_Id=c.category_Id join client_product cp on cp.product_Id=p.product_Id";	
			}
			else
			{
				 sql="SELECT p.product_Id,p.prod_Desc AS productName,p.product_Code as productCode,sc.subCategory_Id AS subCategoryId,sc.subCategory_Desc AS subCategoryName,c.category_Id AS categoryId,c.category_Desc AS categoryName FROM  prod_subcat ps JOIN product p ON p.product_Id=ps.product_Id JOIN subcategory sc ON sc.subCategory_Id=ps.subCategory_Id JOIN subcat_category scc ON scc.subcategory_Id=ps.subCategory_Id JOIN category c ON scc.category_Id=c.category_Id join client_product cp on cp.product_Id=p.product_Id  WHERE cp.client_Id='"+clientId+"'";
			}
			productBeans=getJdbcTemplate().query(sql, new BeanPropertyRowMapper<ProductBean>(ProductBean.class));
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return productBeans;
	}

	@Override
	public List<ProductBean> productSearch(String productId, String id) {
		List<ProductBean> beans=null;
		try
		{
			if("id".equals(id))
			{
				sql="select p.product_Id as productId,p.prod_Desc as productName from product p where p.product_Id='"+productId+"'";
			beans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<ProductBean>(ProductBean.class));
			}
			else
			{
				//sql="select p.product_Id as productId,p.prod_Desc as productName from product p where p.prod_Desc like '"+productId+"%'";
				 sql="SELECT p.product_Id,p.prod_Desc AS productName,p.product_Code as productCode,sc.subCategory_Id AS subCategoryId,sc.subCategory_Desc AS subCategoryName,c.category_Id AS categoryId,c.category_Desc AS categoryName FROM  prod_subcat ps JOIN product p ON p.product_Id=ps.product_Id JOIN subcategory sc ON sc.subCategory_Id=ps.subCategory_Id JOIN subcat_category scc ON scc.subcategory_Id=ps.subCategory_Id JOIN category c ON scc.category_Id=c.category_Id WHERE  p.prod_Desc like '"+productId+"%'";
				
				beans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<ProductBean>(ProductBean.class));
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return beans;
	}

	@Override
	public List<ProductBean> getCategoryDetails(String deptId) {
		// TODO Auto-generated method stub
		List<ProductBean> productBeans=null;
		try
		{
		 sql="select d.category_Id as categoryId,c.category_Desc as categoryName from  category_dept d join category c on c.category_Id=d.category_Id where d.dept_Id='"+deptId+"'";
			productBeans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<ProductBean>(ProductBean.class));
		logger.debug("beans size in dao iss=="+productBeans.size()+"===sqal iss==="+sql);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return productBeans;
	}

	@Override
	public List<ProductBean> getSubCategoryDetails(String catId) {
		// TODO Auto-generated method stub
		
List<ProductBean> productBeans=null;
		try
		{
			//String clientId=session.getAttribute("clientId").toString();
		 sql="select d.subcategory_Id as subCategoryId,c.subCategory_Desc as subCategoryName from  subcat_category d join subcategory c on c.subcategory_Id=d.subcategory_Id where d.category_Id='"+catId+"'";
			
			//sql="SELECT d.subcategory_Id AS subCategoryId,c.subCategory_Desc AS subCategoryName FROM  subcat_category d JOIN subcategory c ON c.subcategory_Id=d.subcategory_Id  JOIN client_subcategory cs ON cs.subcat_Id =c.subCategory_Id WHERE d.category_Id='"+catId+"' AND cs.client_Id='"+clientId+"'";
		 
		 productBeans=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<ProductBean>(ProductBean.class));
		    logger.debug("beans size in dao iss=="+productBeans.size()+"===sqal iss==="+sql);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return productBeans;
	}
	@Override
	public List<ProductBean> getProductsWithId(String subCategoryId) {
		// TODO Auto-generated method stub
		List<ProductBean> beans=null;
		try
		{
			//String sql="SELECT p.product_Id,p.prod_Desc as productName FROM  prod_subcat ps JOIN product p ON p.product_Id=ps.product_Id WHERE ps.subCategory_Id='"+subCategoryId+"'";
			String sql="SELECT p.product_Id,p.prod_Desc AS productName,p.product_Code as productCode,sc.subCategory_Id AS subCategoryId,sc.subCategory_Desc AS subCategoryName,c.category_Id AS categoryId,c.category_Desc AS categoryName FROM  prod_subcat ps JOIN product p ON p.product_Id=ps.product_Id JOIN subcategory sc ON sc.subCategory_Id=ps.subCategory_Id JOIN subcat_category scc ON scc.subcategory_Id=ps.subCategory_Id JOIN category c ON scc.category_Id=c.category_Id WHERE ps.subCategory_Id='"+subCategoryId+"'";
			beans=getJdbcTemplate().query(sql, new BeanPropertyRowMapper<ProductBean>(ProductBean.class));
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return beans;
	}
	@Override
	public int duplicateCheck(String subcategoryId, String prodcuId,
			String prodName) {
		// TODO Auto-generated method stub
		int count=0;
		try
		{
			if(prodcuId==null)
			{
				String sql="SELECT COUNT(*) FROM  prod_subcat ps JOIN product p ON ps.product_Id=p.product_Id WHERE ps.subCategory_Id='"+subcategoryId+"' AND p.prod_Desc='"+prodName+"'";
		 count=getJdbcTemplate().queryForInt(sql);
			}
			else
			{
				//String sql="SELECT COUNT(*) FROM  prod_subcat ps JOIN product p ON ps.product_Id=p.product_Id WHERE ps.subCategory_Id='"+subcategoryId+"' AND p.prod_Desc='"+prodName+"' AND p.product_Id!='"+prodcuId+"'";
				String sql="SELECT COUNT(*) FROM product p WHERE p.product_Id!='"+prodcuId+"' AND p.prod_Desc='"+prodName+"'";
				count=getJdbcTemplate().queryForInt(sql);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return count;
	}
}
