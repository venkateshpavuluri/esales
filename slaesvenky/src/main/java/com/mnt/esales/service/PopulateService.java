/**
 * Copyright MNTSOFT 
 */
package com.mnt.esales.service;

import java.util.List;
import java.util.Map;

import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.bean.PopulateBean;

/**
 * @author Naresh
 * 
 */
public interface PopulateService {

	public java.util.List<PopulateBean> populate(String sql);

	public Map<String, String> populatePopUp(String sql);

	/*
	 * public Map<Integer, String> populateSelectBox(String sql);
	 */
	public Integer DuplicateCheck(String sql);
	public List<ClientInfoBean> populateClient(String sql);

}
