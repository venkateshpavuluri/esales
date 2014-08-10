package com.mnt.esales.dao;

/*
 * srinivas
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.StockBean;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;
import com.mnt.rowmappers.StockRowMapper;

@Repository("stockDao")
public class StockDaoImpl extends MntDaoSupport implements StockDao {

	boolean flag = true;
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	HttpSession session;
	@Autowired
	CustomConnection customConnection;

	@Override
	public boolean StockSave(Object object,boolean status) {
		String maxId=null;
	   String maxstockbalanceId=null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime());
			String clientId = (String) session.getAttribute("clientId");
			JdbcTemplate jdbcTemplate=null;
			String stockId=null;
			StockBean stockBean = (StockBean) object;
			if(status){
			 maxId = custIdDao
					.getMaxId("select max(stock_Code) from stock");
			 maxstockbalanceId = custIdDao
					.getMaxId("select max(stock_Bal_Id) from stockbalance");
			 jdbcTemplate=getJdbcTemplate();
			 stockId=clientId+maxId;
			}
			else
			{
				 maxId = custIdDao
							.getMaxIdForUpload("select max(stock_Code) from stock");
					 maxstockbalanceId = custIdDao
							.getMaxIdForUpload("select max(stock_Bal_Id) from stockbalance");
					 jdbcTemplate=customConnection.getTemplateConnection();
					 stockId=stockBean.getStock_Id();
			}
			
		
			String stockbal = "SELECT SUM(quantity) AS balance_Stock FROM stock WHERE prod_Id='"
					+ stockBean.getProductId() + "'";
			int balstock = jdbcTemplate.queryForInt(stockbal);
			String productcount =jdbcTemplate.queryForObject(
					"SELECT COUNT(*) FROM stock WHERE `prod_Id`='"
							+ stockBean.getProductId() + "'", String.class);
			if (maxId != null) {
				if (productcount.equals("0")) {
					String sql = "insert into stock(stock_Id,prod_Id,cost,quantity,mrp,discount,vat,stock_Entry,stock_Code) values(?,?,?,?,?,?,?,?,?)";
					jdbcTemplate.update(
							sql,
							new Object[] { stockId,
									stockBean.getProductId(),
									stockBean.getCost(),
									stockBean.getQuantity(),
									stockBean.getMrp(),
									stockBean.getDiscount(),
									stockBean.getVat(), date, maxId });
					String sqlstockclient = "insert into stock_client(client_Id,stock_Id) values(?,?)";
					jdbcTemplate.update(sqlstockclient,
							new Object[] { clientId, stockId });
					if (maxstockbalanceId != null) {
						String sql1 = "insert into stockbalance(stock_Bal_Id,prod_Id,balance_Stock,new_Stock,stock_Entry,stock_Id) values(?,?,?,?,?,?)";
						jdbcTemplate.update(
								sql1,
								new Object[] { maxstockbalanceId,
										stockBean.getProductId(), balstock,
										stockBean.getQuantity(), date,
										stockId });
					} else {
						String sql1 = "insert into stockbalance(prod_Id,balance_Stock,new_Stock,stock_Entry,stock_Id) values(?,?,?,?,?)";
						jdbcTemplate.update(
								sql1,
								new Object[] { stockBean.getProductId(),
										balstock, stockBean.getQuantity(),
										date, stockId });
					}
				} else {
					String sql = "update stock set prod_Id=?,cost=?,quantity=?,mrp=?,discount=?,vat=?,stock_Entry=?,stock_Code=? where stock_Id=?";
					jdbcTemplate.update(
							sql,
							new Object[] { stockBean.getProductId(),
									stockBean.getCost(),
									stockBean.getQuantity()+stockBean.getEq(),
									stockBean.getMrp(),
									stockBean.getDiscount(),
									stockBean.getVat(), date, maxId,
									stockBean.getStock_Id() });
					String sqlstockclient = "update stock_client set client_Id=? where stock_Id=?";
					jdbcTemplate.update(
							sqlstockclient,
							new Object[] { clientId,
									clientId + stockBean.getStock_Id() });
					if (maxstockbalanceId != null) {
						String sql1 = "update stockbalance set stock_Bal_Id=?,prod_Id=?,balance_Stock=?,new_Stock=?,stock_Entry=? where stock_Id=?";
						jdbcTemplate.update(
								sql1,
								new Object[] { maxstockbalanceId,
										stockBean.getProductId(), balstock,
										stockBean.getQuantity()+stockBean.getEq(), date,
										stockBean.getStock_Id() });
					} else {
						String sql1 = "update stockbalance set prod_Id=?,balance_Stock=?,new_Stock=?,stock_Entry=? where stock_Id=?";
						jdbcTemplate.update(
								sql1,
								new Object[] { stockBean.getProductId(),
										balstock, stockBean.getQuantity()+stockBean.getEq(),
										date, stockBean.getStock_Id() });
					}
				}
			} else {
				maxId="S0001";
				if(status)
				{
					stockId=clientId+"S0001";
					
				}
				
				if (productcount.equals("0")) {
					String sql = "insert into stock(stock_Id,prod_Id,cost,quantity,mrp,discount,vat,stock_Entry,stock_Code) values(?,?,?,?,?,?,?,?,?)";
					jdbcTemplate.update(
							sql,
							new Object[] { stockId,
									stockBean.getProductId(),
									stockBean.getCost(),
									stockBean.getQuantity()+stockBean.getEq(),
									stockBean.getMrp(),
									stockBean.getDiscount(),
									stockBean.getVat(), date, "S0001" });
					String sqlstockclient = "insert into stock_client(client_Id,stock_Id) values(?,?)";
					jdbcTemplate.update(sqlstockclient,
							new Object[] { clientId, stockId });
					if (maxstockbalanceId != null) {
						String sql1 = "insert into stockbalance(stock_Bal_Id,prod_Id,balance_Stock,new_Stock,stock_Entry,stock_Id) values(?,?,?,?,?,?)";
						jdbcTemplate.update(
								sql1,
								new Object[] { maxstockbalanceId,
										stockBean.getProductId(), balstock,
										stockBean.getQuantity()+stockBean.getEq(), date,
										stockId });
					} else {
						String sql1 = "insert into stockbalance(prod_Id,balance_Stock,new_Stock,stock_Entry,stock_Id) values(?,?,?,?,?)";
						jdbcTemplate.update(
								sql1,
								new Object[] { stockBean.getProductId(),
										balstock, stockBean.getQuantity()+stockBean.getEq(),
										date,stockId });
					}
				} else {
					String sql = "update stock set prod_Id=?,cost=?,quantity=?,mrp=?,discount=?,vat=?,stock_Entry=?,stock_Code=? where stock_Id=?";
					jdbcTemplate.update(
							sql,
							new Object[] { stockBean.getProductId(),
									stockBean.getCost(),
									stockBean.getQuantity()+stockBean.getEq(),
									stockBean.getMrp(),
									stockBean.getDiscount(),
									stockBean.getVat(), date, "S0001",
									stockBean.getStock_Id() });
					String sqlstockclient = "update stock_client set client_Id=?,stock_Id=?";
					jdbcTemplate.update(sqlstockclient,
							new Object[] { clientId,stockId });
					if (maxstockbalanceId != null) {
						String sql1 = "update stockbalance set prod_Id=?,balance_Stock=?,new_Stock=?,stock_Entry=?,stock_Id=? where stock_Bal_Id=?";
						jdbcTemplate.update(
								sql1,
								new Object[] { stockBean.getProductId(),
										balstock, stockBean.getQuantity()+stockBean.getEq(),
										date, stockBean.getStock_Id(),
										maxstockbalanceId });
					} else {
						String sql1 = "update stockbalance set prod_Id=?,balance_Stock=?,new_Stock=?,stock_Entry=? where stock_Id=?";
						jdbcTemplate.update(
								sql1,
								new Object[] { stockBean.getProductId(),
										balstock, stockBean.getQuantity()+stockBean.getEq(),
										date, stockBean.getStock_Id() });
					}
				}
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean StockUpdate(Object object) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime());
			StockBean stockBean = (StockBean) object;
			String stid = stockBean.getStock_Id();
			String sql = "update stock set prod_Id=?,cost=?,quantity=?,mrp=?,discount=?,vat=?,stock_Entry=? where stock_Id=?";
			getJdbcTemplate().update(
					sql,
					new Object[] { stockBean.getProductId(),
							stockBean.getCost(), stockBean.getQuantity(),
							stockBean.getMrp(), stockBean.getDiscount(),
							stockBean.getVat(), date, stid });
			String stockbal = "SELECT SUM(quantity) AS balance_Stock FROM stock WHERE prod_Id='"
					+ stockBean.getProductId() + "'";
			int balstock = getJdbcTemplate().queryForInt(stockbal);
			String sqlsb = "update stockbalance set prod_Id=?,balance_Stock=?,new_Stock=?,stock_Entry=? where stock_Id=?";
			getJdbcTemplate().update(
					sqlsb,
					new Object[] { stockBean.getProductId(), balstock,
							stockBean.getQuantity(), date, stid });
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean StockDelete(String stockId) {
		try {

			String sql = "delete from stock where stock_Id=?";
			getJdbcTemplate().update(sql, new Object[] { stockId });

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<StockBean> stockSearch() {
		List<StockBean> stockBean = null;
		try {
			stockBean = getJdbcTemplate()
					.query("SELECT s.`stock_Id`, p.`prod_Desc`, s.`cost` , s.`quantity`, s.`mrp`, s.`discount`, s.`vat`,s.`stock_Entry` FROM stock s JOIN product p ON s.`prod_Id`=p.`product_Id`",
							new BeanPropertyRowMapper<StockBean>(
									StockBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stockBean;
	}

	@Override
	public List<StockBean> stockEdit(String stock_Id, String id) {
		List<StockBean> stockBean = null;
		try {
			if (id == "id") {
				stockBean = getJdbcTemplate()
						.query("select stock_Id as stock_Id,prod_Id as productId,cost as cost,quantity as quantity ,mrp as mrp,discount as discount,vat as vat from stock where stock_Id='"
								+ stock_Id + "'",
								new BeanPropertyRowMapper<StockBean>(
										StockBean.class));
			} else {
				stockBean = getJdbcTemplate()
						.query("select stock_Id as stock_Id,prod_Id as productId,cost as cost,quantity as quantity ,mrp as mrp,discount as discount,vat as vat from stock where productId='"
								+ stock_Id + "'",
								new BeanPropertyRowMapper<StockBean>(
										StockBean.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return stockBean;

	}

	@Override
	public List<StockBean> stochSearchwithProduct(String product_Id) {
		List<StockBean> stockBean = null;
		try {
			stockBean = getJdbcTemplate()
					.query("SELECT s.`stock_Id`,p.product_Id AS productId, p.`prod_Desc` AS productName, s.`cost` , s.`quantity`, s.`mrp`, s.`discount`, s.`vat`,s.`stock_Entry`, su.`subCategory_Desc` as subcategory,su.`subCategory_Id` AS subcategoryId, c.`category_Desc` AS category,c.`category_Id` AS categoryId FROM stock s JOIN product p ON s.`prod_Id`=p.`product_Id` JOIN  `stock_client` sc ON sc.`stock_Id`=s.`stock_Id` JOIN `prod_subcat` ps ON ps.`product_Id`=p.`product_Id` JOIN `subcat_category` cs ON cs.`subcategory_Id`=ps.`subCategory_Id` JOIN `subcategory` su ON su.`subCategory_Id`= ps.`subCategory_Id` JOIN `category` c ON c.`category_Id`=cs.`category_Id` WHERE p.`product_Id`='"
							+ product_Id + "'",
							new BeanPropertyRowMapper<StockBean>(
									StockBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stockBean;
	}

	@Override
	public List<StockBean> getProductDetails(String clientId) {
		// TODO Auto-generated method stub
		List<StockBean> beans = null;
		try {
			String sql = "SELECT c.category_Desc as category,s.subcategory_Desc as subcategory,p.`product_Id` as productId,p.prod_desc as productName FROM subcat_category sc JOIN category c ON c.`category_Id`=sc.`category_Id` JOIN `subcategory` s ON s.`subCategory_Id`=sc.`subcategory_Id` JOIN prod_subcat ps ON ps.`subCategory_Id`=s.`subCategory_Id` JOIN product p ON p.`product_Id`=ps.`product_Id` JOIN `client_product` cp ON cp.`product_Id`=p.`product_Id` WHERE cp.`client_Id`='"
					+ clientId + "'";
			beans = getJdbcTemplate().query(sql,
					new BeanPropertyRowMapper<StockBean>(StockBean.class));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return beans;
	}

	@Override
	public List<StockBean> stockSearch(String clientId) {
		List<StockBean> stockBean = null;
		try {

			stockBean = getJdbcTemplate()
					.query("SELECT s.`stock_Id`,p.product_Id AS productId, p.`prod_Desc` AS productName, s.`cost` , s.`quantity`, s.`mrp`, s.`discount`, s.`vat`,s.`stock_Entry`, su.`subCategory_Desc` as subcategory, su.`subCategory_Id` AS subcategoryId, c.`category_Desc` AS category,c.`category_Id` AS categoryId FROM stock s JOIN product p ON s.`prod_Id`=p.`product_Id` JOIN  `stock_client` sc ON sc.`stock_Id`=s.`stock_Id` JOIN `prod_subcat` ps ON ps.`product_Id`=p.`product_Id`JOIN `subcat_category` cs ON cs.`subcategory_Id`=ps.`subCategory_Id`JOIN `subcategory` su ON su.`subCategory_Id`= ps.`subCategory_Id`JOIN `category` c ON c.`category_Id`=cs.`category_Id` WHERE sc.`client_Id`='"
							+ clientId + "'",
							new BeanPropertyRowMapper<StockBean>(
									StockBean.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return stockBean;
	}

	@Override
	public List<StockBean> getTotalQuantity(String productId) {
		List<StockBean> stockBean = null;
		try {
			stockBean = getJdbcTemplate().query(
					"SELECT quantity,cost,quantity,discount,vat,mrp FROM stock WHERE prod_Id='"
							+ productId + "'",
					new BeanPropertyRowMapper<StockBean>(StockBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stockBean;
	}

}
