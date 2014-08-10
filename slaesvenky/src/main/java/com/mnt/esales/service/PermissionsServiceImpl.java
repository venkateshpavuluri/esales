/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.Permissions;
import com.mnt.esales.dao.PermissionsDao;

/**
 * @author venkateshp
 *
 */
@Service("permissionsService")
public class PermissionsServiceImpl implements PermissionsService {
@Autowired
PermissionsDao permissionsDao;
	@Override
	public List<Permissions> getBranches(String clientId) {
		return permissionsDao.getBranches(clientId);
				
	}

	@Override
	public List<Permissions> getUsers(String clientId) {
		return permissionsDao.getUsers(clientId);
	}

	@Override
	public boolean savePermissions(List<Permissions> permissions) {
		return permissionsDao.savePermissions(permissions,true);
	}

	@Override
	public List<Permissions> getPermissions(String userId,String branchId,String clientId) {
		return permissionsDao.getPermissions(userId,branchId,clientId);
	}

	@Override
	public boolean deletePermissions(String clientId) {
		// TODO Auto-generated method stub
		return permissionsDao.deletePermissions(clientId);
	}
}
