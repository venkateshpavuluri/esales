/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.LoginUser;

/**
 * @author venkateshp
 * 
 */
public interface LoginService {

	public List<LoginUser> getLoginDetails(String userName, String password);

	public List<LoginUser> getRole(String role);

	public List<LoginUser> getClient(String clientId);

	public String getBranchId(String userId);

	public List<BranchBean> getBranchDetails(String userId);

	
	public String getLoginName(String userId);

	public int loginUser(String userName, String password);

}
