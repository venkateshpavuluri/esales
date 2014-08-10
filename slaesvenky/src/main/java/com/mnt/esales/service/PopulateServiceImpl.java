/**
 * Copyright MNTSOFT 
 */
package com.mnt.esales.service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.bean.PopulateBean;
import com.mnt.esales.dao.PopulateDao;

/**
 * @author Naresh
 * 
 */
@Service("populateService")
public class PopulateServiceImpl implements PopulateService {
	@Autowired
	PopulateDao dao;

	@Override
	public List<PopulateBean> populate(String sql) {
		List<PopulateBean> list = null;
		try {
			list = dao.populate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Map<String, String> populatePopUp(String sql) {
		// TODO Auto-generated method stub
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<PopulateBean> list = null;

		try {
			list = dao.populate(sql);
			Iterator<PopulateBean> iterator = list.iterator();
			while (iterator.hasNext()) {
				PopulateBean objects = (PopulateBean) iterator.next();
				map.put(objects.getId(), objects.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	/*
	 * public Map<Integer, String> populateSelectBox(String sql) { Map<Integer,
	 * String> map = new HashMap<Integer, String>(); List<Object[]> list = null;
	 * 
	 * try { list = dao.poPulate(sql); Iterator<Object[]> iterator =
	 * list.iterator(); while (iterator.hasNext()) { Object[] objects =
	 * (Object[]) iterator.next(); map.put((Integer) objects[0], (String)
	 * objects[1]); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return map;
	 * 
	 * }
	 */

	@Override
	public Integer DuplicateCheck(String hql) {
		int i = 0;
		try {
			i = dao.DuplicateCheck(hql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public List<ClientInfoBean> populateClient(String sql) {
		List<ClientInfoBean> list = null;
		try {
			list = dao.populateClient(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
