/**
 * 
 */
package com.mnt.esales.dao;

import java.util.List;

import com.mnt.esales.bean.BranchBean;

/**
 * @author Naresh
 * 
 */
public interface BranchDao {

	public boolean saveBranch(Object object,boolean status);

	public boolean updateBranch(Object object);

	public boolean deleteBranch(String BranchId);

	public List<BranchBean> searchBranch();

	public List<BranchBean> searchBranch(String BranchId, String id);
}
