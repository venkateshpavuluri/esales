package com.mnt.esales.dao;

import java.util.List;
import com.mnt.esales.bean.UserBean;

/**
 * @author srinivas
 * 
 */
public interface UserDao {
	public boolean userSave(Object object,boolean status);

	public boolean userstatusSave(String status);

	public boolean userstatusuncheckSave(String status);

	public boolean userUpdate(Object object);

	public boolean userDelete(String deptId);

	public Integer getUsername(String uid);

	public Integer getUsernameEdit(String uid,String uname);

	public List<UserBean> userSearch(String clientId, String id);

	public List<UserBean> userSearch();

	public List<UserBean> userSearchwithclientId(String uid,String clientId);

	public List<UserBean> userSearchwithId(String clientId);

	public List<UserBean> userEdit(String user_Id, String id);

	public List<UserBean> getBranchDetails(String clientId);

}
