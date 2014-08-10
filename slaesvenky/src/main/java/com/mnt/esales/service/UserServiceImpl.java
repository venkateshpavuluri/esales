/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mnt.esales.bean.UserBean;
import com.mnt.esales.dao.UserDao;

/**
 * @author srinivas
 * 
 */
@Service("usersService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	boolean flag;

	@Override
	public boolean userSave(Object object,boolean status) {
		try {
			flag = userDao.userSave(object,status);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean userUpdate(Object object) {
		try {
			flag = userDao.userUpdate(object);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean userDelete(String deptId) {
		try {
			flag = userDao.userDelete(deptId);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<UserBean> userSearch(String clientId, String id) {
		List<UserBean> uBean = null;
		try {
			uBean = userDao.userSearch(clientId,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uBean;
	}

	@Override
	public List<UserBean> userSearchwithId(String clientId) {
		List<UserBean> uBean = null;
		try {
			uBean = userDao.userSearchwithId(clientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uBean;
	}

	@Override
	public List<UserBean> getBranchDetails(String clientId) {
		List<UserBean> uBean = null;
		try {
			uBean = userDao.getBranchDetails(clientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uBean;
	}

	@Override
	public List<UserBean> userEdit(String user_Id, String id) {
		List<UserBean> uBean = null;
		try {
			uBean = userDao.userEdit(user_Id, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uBean;
	}

	@Override
	public List<UserBean> userSearch() {
		List<UserBean> uBean = null;
		try {
			uBean = userDao.userSearch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uBean;
	}

	@Override
	public List<UserBean> userSearchwithclientId(String uid,String clientId) {
		List<UserBean> uBean = null;
		try {
			uBean = userDao.userSearchwithclientId(uid,clientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uBean;
	}

	@Override
	public boolean userstatusSave(String status) {
		try {
			flag = userDao.userstatusSave(status);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean userstatusuncheckSave(String status) {
		try {
			flag = userDao.userstatusuncheckSave(status);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Integer getUsername(String uid) {
		int id = 0;
		try {
			id = userDao.getUsername(uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Integer getUsernameEdit(String uid, String uname) {
		int id = 0;
		try {
			id = userDao.getUsernameEdit(uid, uname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

}
