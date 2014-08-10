/**
 * 
 */
package com.mnt.esales.dao;

/**
 * @author Naresh
 * 
 */
public interface CustomIdDao {

	public String getMaxId(String sql);
	public String getMaxIdForUpload(String sql) ;

}
