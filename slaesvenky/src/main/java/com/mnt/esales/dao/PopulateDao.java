/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.bean.PopulateBean;

/**
 * @author Naresh
 *
 */
public interface PopulateDao {
	public List<PopulateBean> populate(String sql);
	
	public Integer DuplicateCheck(String sql);
	public List<ClientInfoBean> populateClient(String sql);

}
