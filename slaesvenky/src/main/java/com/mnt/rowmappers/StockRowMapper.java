package com.mnt.rowmappers;
/**
 * @author Srinivas
 * 
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.mnt.esales.bean.StockBean;

public class StockRowMapper implements RowMapper<StockBean> {

	@Override
	public StockBean mapRow(ResultSet rs, int rowNo) throws SQLException {
		StockBean stockBean = new StockBean();
		stockBean.setStock_Id(rs.getString(1));
		stockBean.setProductId(rs.getString(2));
		stockBean.setProductName(rs.getString(3));
		stockBean.setCost(rs.getDouble(4));
		stockBean.setQuantity(rs.getInt(5));
		stockBean.setMrp(rs.getDouble(6));
	/*	stockBean.setDiscount(rs.getInt(7));
		stockBean.setVat(rs.getInt(8));*/
		stockBean.setStockentry(rs.getString(9));
		return stockBean;
	}

}
