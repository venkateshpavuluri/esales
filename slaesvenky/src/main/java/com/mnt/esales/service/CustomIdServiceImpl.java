/**
 * 
 */
package com.mnt.esales.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.dao.CustomIdDao;

/**
 * @author Naresh
 * 
 */
@Service("customIdService")
public class CustomIdServiceImpl implements CustomIdService {
	@Autowired
	CustomIdDao custIdDao;

	@Override
	public String getMaxId(String sql) {
		String returnId = custIdDao.getMaxId(sql);
		return returnId;
	}

}
