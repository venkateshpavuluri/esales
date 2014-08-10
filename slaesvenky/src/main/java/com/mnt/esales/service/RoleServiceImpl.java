/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mnt.esales.bean.RoleBean;
import com.mnt.esales.dao.RoleDao;

/**
 * @author yogi
 * 
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleDao roleDao;
	boolean flag = true;
	List<RoleBean> listBean;
	private Logger logger = Logger.getLogger(RoleServiceImpl.class);

	@Override
	public boolean saveRole(Object object) {
		try {
			flag = roleDao.saveRole(object);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public List<RoleBean> roleSearch() {
		try {
		    listBean = roleDao.roleSearch();
		} catch (Exception e) {
		    e.printStackTrace();
		    logger.error(e.getMessage());
		}
		return listBean;
	    }

	@Override
	public List<RoleBean> roleSearch(String roleId, String id) {
		List<RoleBean> roleBean = null;
		try {
			roleBean = roleDao.search(roleId, id);
		} catch (Exception e) {
		    e.printStackTrace();
		    logger.error(e.getMessage());
		}
		return roleBean;
	    }

	@Override
	public boolean roleUpdate(Object object) {
		try {
		    flag = roleDao.roleUpdate(object);
		} catch (Exception e) {
		    flag = false;
		    e.printStackTrace();
		    logger.error(e.getMessage());
		}
		return flag;
	    }

	@Override
	public boolean roleDelete(String roleId) {
		try {
		    flag = roleDao.roleDelete(roleId);
		} catch (Exception e) {
		    flag = false;
		    e.printStackTrace();
		    logger.error(e.getMessage());
		}
		return flag;
	    }

}
