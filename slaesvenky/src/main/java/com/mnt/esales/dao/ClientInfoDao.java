/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.ClientInfoBean;

/**
 * @author Naresh
 * 
 */
public interface ClientInfoDao {

	public boolean saveClient(Object object,boolean status);

	public boolean updateClient(Object object);

	public boolean deleteClient(String clientId);

	public List<ClientInfoBean> searchClient();

	public List<ClientInfoBean> searchClient(String clientId, String id);

}
