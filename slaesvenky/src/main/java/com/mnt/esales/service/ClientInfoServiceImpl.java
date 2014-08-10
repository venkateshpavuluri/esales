/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.dao.ClientInfoDao;

/**
 * @author Naresh
 * 
 */
@Service("clientInfoService")
public class ClientInfoServiceImpl implements ClientInfoService {
	@Autowired
	ClientInfoDao clientDao;
	boolean flag;

	@Override
	public boolean saveClient(Object object,boolean status) {
		try {
			flag = clientDao.saveClient(object,status);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateClient(Object object) {
		try {
			flag = clientDao.updateClient(object);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteClient(String clientId) {
		try {
			flag = clientDao.deleteClient(clientId);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<ClientInfoBean> searchClient() {
		List<ClientInfoBean> clientBean = null;
		try {
			clientBean = clientDao.searchClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientBean;
	}

	@Override
	public List<ClientInfoBean> searchClient(String clientId, String id) {
		List<ClientInfoBean> clientBean = null;
		try {
			clientBean = clientDao.searchClient(clientId, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientBean;
	}

}
