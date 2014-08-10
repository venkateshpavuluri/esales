/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import com.mnt.esales.bean.BranchBean;

/**
 * @author Naresh
 * 
 */
public interface BranchService {
	public boolean saveBranch(Object object, boolean status);

	public boolean updateBranch(Object object);

	public boolean deleteBranch(String BranchId);

	public List<BranchBean> searchBranch();

	public List<BranchBean> searchBranch(String BranchId, String id);
}
