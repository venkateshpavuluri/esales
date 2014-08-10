/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.Permissions;

/**
 * @author venkateshp
 *
 */
public interface PermissionsService {
	public List<Permissions> getBranches(String clientId);
	public List<Permissions> getUsers(String clientId);
	public boolean savePermissions(List<Permissions> permissions);
	public List<Permissions> getPermissions(String userId,String branchId,String clientId); 
	public boolean deletePermissions(String clientId);
}
