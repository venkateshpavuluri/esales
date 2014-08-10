package com.mnt.esales.dao;

import java.util.List;
import com.mnt.esales.bean.RoleBean;


public interface RoleDao {

	public boolean saveRole(Object object);

    public List<RoleBean> roleSearch();

    public List<RoleBean> search(String roleId, String id);

    public boolean roleUpdate(Object object);

    public boolean roleDelete(String roleId);

}
