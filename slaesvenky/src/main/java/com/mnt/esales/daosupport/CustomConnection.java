/**
 * 
 */
package com.mnt.esales.daosupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author venkateshp
 *
 */
public class CustomConnection extends JdbcDaoSupport{
	public JdbcTemplate getTemplateConnection()
	{
		return getJdbcTemplate();
	}


}
