/**
 *  @Copyright MNTSOFT
 */
package com.mnt.esales.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mnt.esales.bean.DepartmentBean;
import com.mnt.esales.service.CustomIdService;
import com.mnt.esales.service.DepartmentService;
import com.mnt.esales.service.PopulateService;

/**
 * @author Naresh
 * 
 */

@Controller
public class DepartmentController {
	@Autowired
	DepartmentService deptService;
	@Autowired
	CustomIdService custIdService;
	@Autowired
	PopulateService popService;

	@RequestMapping(value = "/deptHome", method = RequestMethod.GET)
	public ModelAndView deptHome(

	HttpServletResponse response, HttpServletRequest request, ModelMap map,
			Model model, HttpSession ses) throws JsonGenerationException,
			JsonMappingException, IOException {
		request.setAttribute("deptActive", "active");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		List<DepartmentBean> deptBean = deptService.deptSearch();
		json = mapper.writeValueAsString(deptBean);
		request.setAttribute("DeptList", json);
		ses.setAttribute("tabName", "Department");
		return new ModelAndView("deptHome", "deptCmd", new DepartmentBean());
	}

	@RequestMapping(value = "/deptDuplCheck", method = RequestMethod.POST)
	public @ResponseBody
	String getDept(
			@RequestParam(value = "deptName", required = true) String deptName,
			HttpServletResponse response) {
		int count = 0;
		String msg = null;
		try {
			count = popService
					.DuplicateCheck("SELECT COUNT(*) FROM `department` WHERE `dept_Desc`='"
							+ deptName + "'");
			if (count != 0) {
				msg = "Warning ! Department is already exists. Please try some other name";
			} else {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/deptDuplCheckEdit", method = RequestMethod.POST)
	public @ResponseBody
	String getDeptEdit(
			@RequestParam(value = "deptName", required = true) String deptName,
			@RequestParam(value = "deptId", required = true) String deptId,
			HttpServletResponse response) {
		int count = 0;
		String msg = null;
		try {
			count = popService
					.DuplicateCheck("SELECT COUNT(*) FROM `department` WHERE `dept_Desc`='"
							+ deptName + "' and dept_Id!='" + deptId + "'");
			if (count != 0) {
				msg = "Warning ! Department is already exists. Please try some other name";
			} else {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/deptAdd", method = RequestMethod.POST)
	public String deptSave(
			@ModelAttribute("deptCmd") DepartmentBean deptAddBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		boolean flag;
		try {
			request.setAttribute("deptActive", "active");
			flag = deptService.deptSave(deptAddBean, true);
			if (flag == true) {
				List<DepartmentBean> deptBean = deptService.deptSearch();
				json = mapper.writeValueAsString(deptBean);
				request.setAttribute("DeptList", json);
				return "redirect:deptHome.htm?AddSus=" + "Success" + "";

			} else {
				return "redirect:deptHome.htm?AddFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:deptHome.htm?AddFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/searchDept", method = RequestMethod.GET)
	public String searchDept(
			@ModelAttribute("deptCmd") DepartmentBean deptSearchBean,
			ModelMap map, HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		List<DepartmentBean> deptBean = null;
		try {
			request.setAttribute("deptActive", "active");
			if (deptSearchBean.getDeptName() == ""
					|| deptSearchBean.getDeptName() == null) {
				deptBean = deptService.deptSearch();
			} else {
				deptBean = deptService.deptSearch(deptSearchBean.getDeptName(),
						"name");
			}
			json = mapper.writeValueAsString(deptBean);
			map.addAttribute("DeptList", json);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "deptHome";

	}

	@RequestMapping(value = "/editDept", method = RequestMethod.GET)
	public String editDept(
			@ModelAttribute("deptCmd") DepartmentBean deptAddBean, Model model,
			ModelMap map, HttpServletResponse response,
			HttpServletRequest request) {
		List<DepartmentBean> deptBean = null;
		DepartmentBean editBean = null;
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setAttribute("deptActive", "active");
			deptBean = deptService.deptSearch(deptAddBean.getDeptId(), "id");

			for (DepartmentBean d : deptBean) {
				editBean = (DepartmentBean) d;
			}
			model.addAttribute("deptCmd", editBean);
			map.addAttribute("deptEdit", deptBean);
			List<DepartmentBean> deptBeans = deptService.deptSearch();
			json = mapper.writeValueAsString(deptBeans);
			request.setAttribute("DeptList", json);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "deptHome";
	}

	@RequestMapping(value = "/deptUpdate", method = RequestMethod.POST)
	public String deptUpdate(
			@ModelAttribute("deptCmd") DepartmentBean deptUpBean, ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		boolean flag;
		try {
			request.setAttribute("deptActive", "active");
			flag = deptService.deptUpdate(deptUpBean);
			if (flag == true) {
				List<DepartmentBean> deptBean = deptService.deptSearch();
				request.setAttribute("DeptList", deptBean);
				return "redirect:deptHome.htm?UpdateSus=" + "Success" + "";

			} else {
				return "redirect:deptHome.htm?UpdateFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:deptHome.htm?UpdateFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/deleteDept", method = RequestMethod.POST)
	public @ResponseBody
	String deleteDept(HttpServletResponse response,
			@ModelAttribute("deptCmd") DepartmentBean deptUpBean, ModelMap map,
			@RequestParam(value = "deleteId", required = true) String deptId,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		boolean flag;
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			flag = deptService.deptDelete(deptId);
			List<DepartmentBean> deptBean = deptService.deptSearch();
			for (DepartmentBean d : deptBean) {
				if (flag == true) {
					d.setMsg("Success");
				} else {
					d.setMsg("Fail");
				}
			}

			json = mapper.writeValueAsString(deptBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/*
	 * @RequestMapping(value = "/deleteDept", method = RequestMethod.GET) public
	 * String deleteDept(
	 * 
	 * @ModelAttribute("deptCmd") DepartmentBean deptBean, HttpServletResponse
	 * response) { response.setCharacterEncoding("UTF-8"); boolean flag; try {
	 * flag = deptService.deptDelete(deptBean.getDeptId()); if (flag == true) {
	 * return "redirect:deptHome.htm?DeleteSus=" + "Success" + ""; } else {
	 * return "redirect:deptHome.htm?DeleteFail=" + "fail" + ""; } } catch
	 * (Exception e) { e.printStackTrace(); return
	 * "redirect:deptHome.htm?DeleteFail=" + "fail" + ""; }
	 * 
	 * }
	 */
}