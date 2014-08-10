package com.mnt.esales.service;

import java.util.List;
import com.mnt.esales.bean.RoleBean;

public interface RoleService {

	public boolean saveRole(Object object);

    public List<RoleBean> roleSearch();

    public List<RoleBean> roleSearch(String roleId, String id);

    public boolean roleUpdate(Object object);

    public boolean roleDelete(String roleId);
}
