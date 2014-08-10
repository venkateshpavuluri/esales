package com.mnt.esales.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.bean.LoginUser;
import com.mnt.esales.service.BranchService;
import com.mnt.esales.service.ClientInfoService;
import com.mnt.esales.service.LoginSericeImpl;

@Controller
public class LoginController {
	private static Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	LoginSericeImpl loginServiceImpl;
	@Autowired
	ClientInfoService clientService;
	@Autowired
	BranchService branchService;
	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginHomePage(
			@ModelAttribute("loginCmd") LoginUser loginUser,
			HttpServletResponse response, HttpServletRequest request,
			HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		String home = "login";
		if (session != null) {

			int count = loginServiceImpl.loginUser(
					(String) session.getAttribute("userName"),
					(String) session.getAttribute("password"));
			if (count != 0) {
				home = "home";
			}
		}
		return home;
	}

	@RequestMapping(value = "/loginHome", method = RequestMethod.GET)
	public String loginHome(@ModelAttribute("loginCmd") LoginUser loginUser,
			HttpServletResponse response, HttpServletRequest request,
			HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		if (session != null) {

			session.invalidate();
		}
		return "login";
	}

	@RequestMapping(value = "/Home", method = RequestMethod.GET)
	public String Home(@ModelAttribute("LOGIN") LoginUser loginUser,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		return "home";
	}

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public String userLogin(@ModelAttribute("deptCmd") LoginUser loginUser,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<LoginUser> list = null;
		Iterator<LoginUser> iterator = null;
		String succMsg = "login";
		String admin = null;
		String clientId = null;
		HttpSession session = request.getSession();
		session.setAttribute("homeDepartment", "Esales Home");
		String path = null;
		try {
			list = loginServiceImpl.getLoginDetails(
					request.getParameter("userName"),
					request.getParameter("password"));

			if (list != null && list.size() != 0) {
				iterator = list.iterator();
				while (iterator.hasNext()) {
					LoginUser loginUsers = (LoginUser) iterator.next();
					if (request.getParameter("userName").equalsIgnoreCase(
							loginUsers.getUserName())
							&& request.getParameter("password").equals(
									loginUsers.getPassword())) {
						String role = loginUsers.getUserId();
						String name = loginServiceImpl.getLoginName(role);
						session.setAttribute("loginUserFullName", name);
						list = loginServiceImpl.getRole(role);
						iterator = list.iterator();
						while (iterator.hasNext()) {
							LoginUser loginuser = (LoginUser) iterator.next();
							admin = loginuser.getRolename();
						}
						session.setAttribute("admin", admin);
						List<LoginUser> list1 = loginServiceImpl
								.getClient(role);
						Iterator<LoginUser> iterator1 = list1.iterator();
						while (iterator1.hasNext()) {
							LoginUser loginusers = (LoginUser) iterator1.next();
							clientId = loginusers.getClientId();
						}

						session.setAttribute("userName",
								loginUsers.getUserName());
						session.setAttribute("password",
								loginUsers.getPassword());
						session.setAttribute("userId", loginUsers.getUserId());
						session.setAttribute("clientId", clientId);

						if ("C0001".equals(clientId)) {
							session.setAttribute("clientNames", "mntClient");
						}

						if (!"C0001".equals(clientId)) {
							session.setAttribute("deptId", loginServiceImpl
									.getBranchId((String) session
											.getAttribute("clientId")));
						}
						ClientInfoBean cb = new ClientInfoBean();
						List<ClientInfoBean> cbList = clientService
								.searchClient((String) session
										.getAttribute("clientId"), "id");
						for (ClientInfoBean d : cbList) {
							cb = (ClientInfoBean) d;

						}
						String cid = (String) session.getAttribute("clientId");
						if (admin.equals("sales")) {
							BranchBean bBean = null;
							List<BranchBean> branchBean = loginServiceImpl
									.getBranchDetails(loginUsers.getUserId());
							for (BranchBean b : branchBean) {
								bBean = (BranchBean) b;
							}
							path = bBean.getLogoPath();
							if(path.equals("")){
								session.setAttribute("imgsrc",
										"./Branding/Brand/logo.png");
								session.setAttribute("theme", "Branding/" + cid
										+ "/Theme.css");
								session.setAttribute("clientName",
										bBean.getBranchName());
								session.setAttribute("address1",
										bBean.getAddress1());
								session.setAttribute("address2",
										bBean.getAddress2());
							}else{
							String[] hu = path.split("\\\\");
							int length = hu.length;
							int lastl = length - 4;
							String a = hu[length - 1];
							String c = hu[length - 2];
							String d = hu[length - 3];
							String bb = hu[lastl];
							bBean.setLogoPath("/" + bb + "/" + d + "/" + c
									+ "/" + a);
							session.setAttribute("theme", "Branding/" + cid
									+ "/Theme.css");
							session.setAttribute("clientName",
									bBean.getBranchName());
							session.setAttribute("address1",
									bBean.getAddress1());
							session.setAttribute("address2",
									bBean.getAddress2());
							session.setAttribute("imgsrc", bBean.getLogoPath());
							}
						} else if (admin.equals("admin")) {
							session.setAttribute("imgsrc",
									"./Branding/Brand/logo.png");
							session.setAttribute("clientName",
									cb.getClientName());
							session.setAttribute("address1", cb.getAddress());
							session.setAttribute("address2", cb.getAddr());
							session.setAttribute("theme", "css1/style.css");
						} else {
							session.setAttribute("theme", "Branding/" + cid
									+ "/Theme.css");
							session.setAttribute("clientName",
									cb.getClientName());
							session.setAttribute("address1", cb.getAddress());
							session.setAttribute("address2", cb.getAddr());
							path = cb.getEditPath();
							if(path.equals("")){
								session.setAttribute("imgsrc",
										"./Branding/Brand/logo.png");
							}else{
							String[] hu = path.split("\\\\");
							int length = hu.length;
							int lastl = length - 4;
							String a = hu[length - 1];
							String c = hu[length - 2];
							String d = hu[length - 3];
							String bb = hu[lastl];
							cb.setLogoPath("/" + bb + "/" + d + "/" + c + "/"
									+ a);
							session.setAttribute("imgsrc", cb.getLogoPath());
							}
						}
						list = loginServiceImpl.getRole(role);
						iterator = list.iterator();
						while (iterator.hasNext()) {
							LoginUser loginuser = (LoginUser) iterator.next();
							admin = loginuser.getRolename();
						}
						if (admin.equals("admin")) {
							succMsg = "redirect:deptHome.htm";
						} else if (admin.equals("clientadmin")) {
							succMsg = "redirect:branchHome.htm";
						} else if (admin.equals("supervisor")) {
							succMsg = "redirect:catHome.htm";

						} else {
							succMsg = "redirect:expensesHome.htm";
						}
					} else {
						succMsg = "login";
						request.setAttribute("FailMessage",
								"Invalid UserName or Password");
					}
				}
			} else {
				succMsg = "login";
				request.setAttribute("FailMessage",
						"Invalid UserName or Password");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return succMsg;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("userId");
		}
		session.invalidate();
		return "redirect:loginHome.htm";
	}
}
