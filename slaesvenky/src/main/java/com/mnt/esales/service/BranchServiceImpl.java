/**
 * 
 */
package com.mnt.esales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.dao.BranchDao;

/**
 * @author Naresh
 * 
 */
@Service("branchService")
public class BranchServiceImpl implements BranchService {
	@Autowired
	BranchDao branchDao;

	@Override
	public boolean saveBranch(Object object,boolean status) {
		boolean flag;
		try {
			flag = branchDao.saveBranch(object,status);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateBranch(Object object) {
		boolean flag;
		try {
			flag = branchDao.updateBranch(object);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteBranch(String BranchId) {
		boolean flag;
		try {
			flag = branchDao.deleteBranch(BranchId);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<BranchBean> searchBranch() {
		List<BranchBean> branchBean = null;
		try {
			branchBean = branchDao.searchBranch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchBean;
	}

	@Override
	public List<BranchBean> searchBranch(String BranchId, String id) {
		List<BranchBean> branchBean = null;
		try {
			branchBean = branchDao.searchBranch(BranchId, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchBean;
	}

}
