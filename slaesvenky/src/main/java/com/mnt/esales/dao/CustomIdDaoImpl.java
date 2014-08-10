/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.GetMaxIdBean;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;
import com.mnt.rowmappers.GetMaxIdRowMapper;

/**
 * @author Naresh
 * 
 */
@Repository("customIdDao")
public class CustomIdDaoImpl extends MntDaoSupport implements CustomIdDao {
	@Autowired
	CustomConnection customConnection;

	@Override
	public String getMaxId(String sql) {
		List<GetMaxIdBean> beanList = null;
		int id = 0;
		String maxId = null, returnId = null;
		beanList = getJdbcTemplate().query(sql, new GetMaxIdRowMapper());
		for (GetMaxIdBean ids : beanList) {
			maxId = ids.getId();
		}
		if (maxId != null) {
			id = Integer.parseInt(maxId.substring(1, maxId.length())) + 1;

			if (String.valueOf(id).length() == 1) {
				returnId = maxId.substring(0, 4) + id;
			} else if (String.valueOf(id).length() == 2) {
				returnId = maxId.substring(0, 3) + id;
			} else if (String.valueOf(id).length() == 3) {
				returnId = maxId.substring(0, 2) + id;
			} else if (String.valueOf(id).length() == 4) {
				returnId = maxId.substring(0, 1) + id;
			} else {
				returnId = maxId.substring(0, 1) + id;
			}

		}
		return returnId;
	}

	
	public String getMaxIdForUpload(String sql) {
		List<GetMaxIdBean> beanList = null;
		int id = 0;
		String maxId = null, returnId = null;
		beanList =customConnection.getTemplateConnection().query(sql, new GetMaxIdRowMapper());
		for (GetMaxIdBean ids : beanList) {
			maxId = ids.getId();
		}
		if (maxId != null) {
			id = Integer.parseInt(maxId.substring(1, maxId.length())) + 1;

			if (String.valueOf(id).length() == 1) {
				returnId = maxId.substring(0, 4) + id;
			} else if (String.valueOf(id).length() == 2) {
				returnId = maxId.substring(0, 3) + id;
			} else if (String.valueOf(id).length() == 3) {
				returnId = maxId.substring(0, 2) + id;
			} else if (String.valueOf(id).length() == 4) {
				returnId = maxId.substring(0, 1) + id;
			} else {
				returnId = maxId.substring(0, 1) + id;
			}

		}
		return returnId;
	}
}
