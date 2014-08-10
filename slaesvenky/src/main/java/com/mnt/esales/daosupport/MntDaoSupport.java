/**
 * 
 */
package com.mnt.esales.daosupport;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author venkateshp
 *
 */
public class MntDaoSupport extends JdbcDaoSupport {
	@Autowired
	public void setDataSourceh(DataSource dataSource)
	{
		setDataSource(dataSource);
	}
}
