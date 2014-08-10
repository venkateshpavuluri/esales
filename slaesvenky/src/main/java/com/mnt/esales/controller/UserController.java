/**
 * 
 */
package com.mnt.esales.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mnt.esales.bean.UserBean;
import com.mnt.esales.service.PopulateService;
import com.mnt.esales.service.UserService;

/**
 * @author srinivas
 * 
 */
@Controller
public class UserController {
	@Autowired
	UserService usersService;
	@Autowired
	PopulateService populateService;
	@Autowired
	HttpSession session;
	boolean flag;

	@RequestMapping(value = "/userHome", method = RequestMethod.GET)
	public String userHome(@ModelAttribute("USER") UserBean userBean,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap map, Model model) throws JsonGenerationException,
			JsonMappingException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("userActive", "active");
		List<UserBean> listofusers = usersService.userSearch();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(listofusers);
		request.setAttribute("listofuserswithId", json);
		session.setAttribute("tabName", "User");
		userBean.setClientId(String.valueOf(session.getAttribute("clientId")));
		return "userHome";
	}

	@ModelAttribute("client")
	public Map<String, String> populatebranches() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String sql = "select client_Id,client_Name from clientinfo where client_status!=" + 2;
			map = populateService.populatePopUp(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@ModelAttribute("role")
	public Map<String, String> populateroles() {
		Map<String, String> map = new HashMap<String, String>();
		String sql = null;
		try {
			String role = (String) session.getAttribute("admin");
			if (role.equals("admin")) {
				sql = "SELECT role_Id,role_Name FROM roles  WHERE STATUS <" + 3;
				map = populateService.populatePopUp(sql);
			} else {
				sql = "SELECT role_Id,role_Name FROM roles  WHERE STATUS <" + 2;
				map = populateService.populatePopUp(sql);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "/saveStatus", method = RequestMethod.POST)
	public @ResponseBody
	boolean saveStatus(@ModelAttribute("USER") UserBean userBean,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uId") String uId) {
		response.setCharacterEncoding("UTF-8");
		String val = request.getParameter("value");
		try {
			request.setAttribute("userActive", "active");
			if (val == null) {
				flag = usersService.userstatusSave(uId);
			} else {
				flag = usersService.userstatusuncheckSave(uId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@RequestMapping(value = "/searchWithUser", method = RequestMethod.POST)
	public @ResponseBody
	String searchsBranch(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		String roleId = request.getParameter("roleId");
		session.setAttribute("uroleId", roleId);
		List<UserBean> uBean = null;
		String cid = request.getParameter("clientId");
		session.setAttribute("uclient", cid);
		try {
			if (request.getParameter("clientId") != "") {
				uBean = usersService.userSearchwithclientId(roleId, cid);
				json = mapper.writeValueAsString(uBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;

	}

	@RequestMapping(value = "/userSearch", method = RequestMethod.GET)
	public String userSearch(@ModelAttribute("USER") UserBean usersBean,
			HttpServletResponse response, HttpSession session,
			HttpServletRequest request, ModelMap map, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("userActive", "active");
		String clientId = (String) session.getAttribute("clientId");
		usersBean.setClientId(clientId);
		String uid = usersBean.getUname();
		List<UserBean> listofuserswithId = new ArrayList<UserBean>();
		if (uid == null || uid == "") {
			listofuserswithId = usersService.userSearch();

		} else {
			listofuserswithId = usersService.userSearchwithId(uid);
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(listofuserswithId);
		request.setAttribute("listofuserswithId", json);
		return "userHome";
	}

	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public String userSave(@ModelAttribute("USER") UserBean userBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("userActive", "active");
			flag = usersService.userSave(userBean,true);
			String roleid = userBean.getRoleId();
			session.setAttribute("userRoleId", roleid);
			session.setAttribute("userclientId", userBean.getClientId());
			String role = (String) session.getAttribute("admin");
			if (flag == true) {
				if (role == "admin") {
					List<UserBean> exBean = usersService.userSearch(
							(String) session.getAttribute("userRoleId"),
							(String) session.getAttribute("userclientId"));
					userBean.setClientId((String) session.getAttribute("clientId"));
					request.setAttribute("listofusers", exBean);
				} else {
					List<UserBean> exBean = usersService
							.userSearch(
									(String) session.getAttribute("clientId"),
									"client");
					request.setAttribute("listofusers", exBean);
				}
				return "redirect:userHome.htm?AddSus=" + "Success" + "";

			} else {
				return "redirect:userHome.htm?AddFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:userHome.htm?AddFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/userEdit", method = RequestMethod.GET)
	public String userEdit(@ModelAttribute("USER") UserBean uBean, Model model,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		List<UserBean> userBean = null;
		UserBean editBean = null;
		String uid = request.getParameter("user_Id");
		try {
			request.setAttribute("userActive", "active");
			String clientId = (String) session.getAttribute("clientId");
			userBean = usersService.userEdit(uid, "id");
			for (UserBean d : userBean) {
				editBean = (UserBean) d;
				editBean.setClientId(clientId);
				model.addAttribute("USER", editBean);
			}
			map.addAttribute("userEdit", userBean);
			String role=(String)session.getAttribute("admin");
			if(role.equals("admin")){
				List<UserBean> listofuserswithId =usersService.userSearch();
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(listofuserswithId);
				request.setAttribute("listofuserswithId", json);
			}else{
			List<UserBean> listofusers = usersService.userSearch(
					(String) session.getAttribute("clientId"), "clientId");
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(listofusers);
			request.setAttribute("listofuserswithId", json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "userHome";

	}

	@RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
	public String userUpdate(@ModelAttribute("USER") UserBean updateBean,
			ModelMap map, HttpServletRequest request, Model model,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("userActive", "active");
			flag = usersService.userUpdate(updateBean);
			if (flag) {
				List<UserBean> expBean = usersService.userSearch();
				model.addAttribute("userList", expBean);
				return "redirect:userHome.htm?UpdateSus=" + "Success" + "";

			} else {
				return "redirect:userHome.htm?UpdateFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:userHome.htm?UpdateFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/userDelete", method = RequestMethod.GET)
	public String userDelete(@ModelAttribute("USER") UserBean deleteBean,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("userActive", "active");
			String uid = request.getParameter("user_Id");
			flag = usersService.userDelete(uid);
			if (flag == true) {
				return "redirect:userHome.htm?DeleteSus=" + "Success" + "";
			} else {
				return "redirect:userHome.htm?DeleteFail=" + "fail" + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:userHome.htm?DeleteFail=" + "fail" + "";
		}

	}

	@RequestMapping(value = "/userDuplicateCheck", method = RequestMethod.POST)
	public @ResponseBody
	String getUserName(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		int pname = 0;
		String msg = null;
		try {
			String uid = request.getParameter("uname");
			pname = usersService.getUsername(uid);
			if (pname != 0) {
				msg = "Warning ! UserName is already exists. Please try some other name";
			}
			if (pname == 0) {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/userDuplicateCheckEdit", method = RequestMethod.POST)
	public @ResponseBody
	String getUserNameEdit(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		int pname = 0;
		String msg = null;
		try {
			String uid = request.getParameter("userid");
			String uname = request.getParameter("unameedit");
			pname = usersService.getUsernameEdit(uid, uname);
			if (pname != 0) {
				msg = "Warning ! UserName is already exists. Please try some other name";
			}
			if (pname == 0) {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
}
