/**
 * Copyright MNTSOFT 
 */
package com.mnt.esales.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.bean.PopulateBean;
import com.mnt.esales.daosupport.MntDaoSupport;
import com.mnt.rowmappers.PopulateRowMapper;

/**
 * @author Naresh
 * 
 */
@Repository("populateDao")
public class PopulateDaoImpl extends MntDaoSupport implements PopulateDao {
	private Logger logger = Logger.getLogger(PopulateDaoImpl.class);

	@Override
	public List<PopulateBean> populate(String sql) {
		List<PopulateBean> list = null;
		try {
			list = getJdbcTemplate().query(sql, new PopulateRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer DuplicateCheck(String sql) {
		int i = 0;
		try {
			i = getJdbcTemplate().queryForInt(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	@Override
	public List<ClientInfoBean> populateClient(String sql){
		List<ClientInfoBean> list = null;
		try {
			list = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<ClientInfoBean>(ClientInfoBean.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
