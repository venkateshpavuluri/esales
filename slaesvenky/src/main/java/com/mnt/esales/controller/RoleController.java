package com.mnt.esales.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.mnt.esales.bean.RoleBean;
import com.mnt.esales.service.RoleService;

@Controller
public class RoleController {
	private Logger logger = Logger.getLogger(RoleController.class);
	@Autowired
	RoleService roleService;
	boolean flag;
	List<RoleBean> listRoleSearch;

	@RequestMapping(value = "/roleHome", method = RequestMethod.GET)
	public ModelAndView roleHome(@ModelAttribute("roleCmd") RoleBean roleBean,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			listRoleSearch = roleService.roleSearch();
			request.setAttribute("RoleList", listRoleSearch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return new ModelAndView("roleHome", "roleCmd", new RoleBean());
	}

	@RequestMapping(value = "/roleAdd", method = RequestMethod.POST)
	public String roleAdd(@ModelAttribute("roleCmd") RoleBean roleBean,
			ModelMap map, HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			flag = roleService.saveRole(roleBean);
			if (flag == true) {
				listRoleSearch = roleService.roleSearch();
				request.setAttribute("RoleList", listRoleSearch);
				return "redirect:roleHome.htm?AddSus=" + "Success" + "";

			} else {
				return "redirect:roleHome.htm?AddFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:roleHome.htm?AddFail=" + "fail" + "";
		}
	}

	@RequestMapping(value = "/searchRole", method = RequestMethod.GET)
	public String searchRole(@ModelAttribute("roleCmd") RoleBean roleBean,
			ModelMap map,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			if (roleBean.getRoleName() == "" || roleBean.getRoleName() == null) {
				listRoleSearch = roleService.roleSearch();
			} else {
				listRoleSearch = roleService.roleSearch(roleBean.getRoleName(),
						"name");
			}
			map.addAttribute("RoleList", roleBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "roleHome";

	}

	@RequestMapping(value = "/editRole", method = RequestMethod.GET)
	public String editRole(@ModelAttribute("roleCmd") RoleBean roleBean,
			Model model, ModelMap map,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		RoleBean editBean = null;

		try {
			listRoleSearch = roleService.roleSearch(roleBean.getRoleId(), "id");
			for (RoleBean c : listRoleSearch) {
				editBean = (RoleBean) c;
			}
			model.addAttribute("roleCmd", editBean);
			map.addAttribute("RoleEdit", listRoleSearch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "roleHome";

	}

	@RequestMapping(value = "/roleUpdate", method = RequestMethod.POST)
	public String roleUpdate(@ModelAttribute("roleCmd") RoleBean roleBean,
			ModelMap map, HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			flag = roleService.roleUpdate(roleBean);
			if (flag == true) {
				listRoleSearch = roleService.roleSearch();
				request.setAttribute("RoleList", roleBean);
				return "redirect:roleHome.htm?UpdateSus=" + "Success" + "";

			} else {
				return "redirect:roleHome.htm?UpdateFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error(e.getMessage());
			return "redirect:roleHome.htm?UpdateFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
	public String deleteRole(@ModelAttribute("roleCmd") RoleBean roleBean,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			flag = roleService.roleDelete(roleBean.getRoleId());

			if (flag == true) {
				return "redirect:roleHome.htm?DeleteSus=" + "Success" + "";
			} else {
				return "redirect:roleHome.htm?DeleteFail=" + "fail" + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:roleHome.htm?DeleteFail=" + "fail" + "";
		}
	}
}
