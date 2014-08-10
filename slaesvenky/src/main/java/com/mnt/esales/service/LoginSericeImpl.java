/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.LoginUser;
import com.mnt.esales.dao.LoginDaoImpl;

/**
 * @author venkateshp
 * 
 */
@Service("loginServiceImpl")
public class LoginSericeImpl implements LoginService {
	private static Logger logger = Logger.getLogger(LoginSericeImpl.class);
	@Autowired
	LoginDaoImpl loginDaoImpl;
	List<LoginUser> list = null;

	@Override
	public List<LoginUser> getLoginDetails(String userName, String password) {
		List<LoginUser> list = null;
		try {
			list = loginDaoImpl.getLoginDetails(userName, password);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	@Override
	public List<LoginUser> getRole(String role) {
		list = loginDaoImpl.getRole(role);
		return list;
	}

	@Override
	public List<LoginUser> getClient(String clientId) {
		list = loginDaoImpl.getClient(clientId);
		return list;
	}

	@Override
	public String getBranchId(String userId) {
		String bId = loginDaoImpl.getBranchId(userId);
		return bId;
	}

	@Override
	public List<BranchBean> getBranchDetails(String userId) {
		List<BranchBean> blist = null;
		blist = loginDaoImpl.getBranchDetails(userId);
		return blist;
	}

	@Override
	public String getLoginName(String userId) {
		
		return loginDaoImpl.getLoginName(userId);
	}


	@Override
	public int loginUser(String userName, String password) {
		// TODO Auto-generated method stub
		return loginDaoImpl.loginUser(userName, password);
	}

}
