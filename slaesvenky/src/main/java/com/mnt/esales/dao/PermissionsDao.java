/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.Permissions;

/**
 * @author venkateshp
 *
 */
public interface PermissionsDao {
public List<Permissions> getBranches(String clientId);
public List<Permissions> getUsers(String clientId);
public boolean savePermissions(List<Permissions> permissions,boolean status) ;
public List<Permissions> getPermissions(String userId,String branchId,String clientId);
public boolean deletePermissions(String clientId);
}
