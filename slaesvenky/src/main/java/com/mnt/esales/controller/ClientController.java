/**
 * 
 */
package com.mnt.esales.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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
import org.springframework.web.multipart.MultipartFile;

import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.dao.CustomIdDao;
import com.mnt.esales.service.ClientInfoService;
import com.mnt.esales.service.PopulateService;

/**
 * @author Naresh
 * 
 */

@Controller
public class ClientController {
	@Autowired
	ClientInfoService clientService;
	@Autowired
	PopulateService populateService;
	@Autowired
	HttpSession ses;
	@Autowired
	ServletContext servletContext;
	@Autowired
	CustomIdDao custIdDao;

	@RequestMapping(value = "/clientHome", method = RequestMethod.GET)
	public String clientHome(HttpServletResponse response,
			HttpServletRequest request, ModelMap map, Model model,
			@ModelAttribute("clientCmd") ClientInfoBean cb)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("clientActive", "active");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		ses = request.getSession(false);
		//List<ClientInfoBean> clientBean = clientService.searchClient(String.valueOf(ses.getAttribute("deptId")), "deptId");
		List<ClientInfoBean> clientBean = clientService.searchClient();
		json = mapper.writeValueAsString(clientBean);
		request.setAttribute("clientList", json);
		if (ses != null) {
			cb.setDeptId(String.valueOf(ses.getAttribute("deptId")));
		}
		ses.setAttribute("tabName", "Client");
		return "clientHome";
	}

	@ModelAttribute("deptIds")
	public Map<String, String> deptIds() {
		Map<String, String> map = null;

		map = populateService
				.populatePopUp("SELECT dept_Id,dept_Desc FROM department ORDER BY dept_Desc");
		return map;

	}

	@RequestMapping(value = "/clientDuplCheck", method = RequestMethod.POST)
	public @ResponseBody
	String getClient(
			@RequestParam(value = "clientName", required = true) String client,
			@RequestParam(value = "deptId", required = true) String deptId,
			HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		int count = 0;
		String msg = null;
		try {
			count = populateService
					.DuplicateCheck("SELECT COUNT(*) FROM `clientinfo` WHERE `client_Name`='"
							+ client + "' and dept_Id='" + deptId + "'");
			if (count != 0) {
				msg = "Warning ! Client Name is already exists. Please try some other name";
			} else {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/clientDuplCheckEdit", method = RequestMethod.POST)
	public @ResponseBody
	String getClientEdit(
			@RequestParam(value = "clientName", required = true) String clientName,
			@RequestParam(value = "clientId", required = true) String clientId,
			@RequestParam(value = "deptId", required = true) String deptId,
			HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		int count = 0;
		String msg = null;
		try {
			count = populateService
					.DuplicateCheck("SELECT COUNT(*) FROM `clientinfo` WHERE `client_Name`='"
							+ clientName
							+ "' and client_Id!='"
							+ clientId
							+ "'and dept_Id='" + deptId + "'");
			if (count != 0) {
				msg = "Warning ! Client Name is already exists. Please try some other name";
			} else {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/clientAdd", method = RequestMethod.POST)
	public String ClientSave(
			@ModelAttribute("clientCmd") ClientInfoBean clientAddBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("clientlogo") MultipartFile mfile)throws IOException {
		response.setCharacterEncoding("UTF-8");
		boolean flag;
		String clientId = (String) ses.getAttribute("clientId");
		String filePath = null;
		 File cssFile =null;
		try {
			String maxId = custIdDao
					.getMaxId("select max(client_Id) from clientinfo");
			if (maxId != null) {
				filePath = servletContext.getRealPath("Branding/"+maxId);
			} else {
				filePath = servletContext.getRealPath("Branding/"+clientId);
			}
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdir();
			}
			String fileName = mfile.getOriginalFilename();
			if (!("").equals(fileName)) {
				File newFile = new File(filePath, fileName);
				if (!newFile.exists()) {
					FileOutputStream fos = new FileOutputStream(newFile);
					fos.write(mfile.getBytes());
					fos.flush();
					fos.close();
				}
				clientAddBean.setLogoPath(filePath+File.separator+ fileName);
			}else{
				clientAddBean.setLogoPath("");
			}
			request.setAttribute("clientActive", "active");
			String theme=clientAddBean.getTheme();
			if(theme.equals("1")){
				cssFile = new File(servletContext.getRealPath("css1/style.css"));
			}else if (theme.equals("2")) {
				cssFile = new File(servletContext.getRealPath("css1/black.css"));
			}else{
			cssFile = new File(servletContext.getRealPath("css1/blue.css"));
			}
            File cssFilePath = new File(filePath+File.separator+"Theme.css");
            FileInputStream fis = new FileInputStream(cssFile);
            FileOutputStream fos = new FileOutputStream(cssFilePath);
            int count;
            while ((count = fis.read()) != -1) {
                  fos.write(count);
            }
			flag = clientService.saveClient(clientAddBean,true);
			 fis.close();
	            fos.close();
			if (flag == true) {
				List<ClientInfoBean> clientBean = clientService.searchClient();
				request.setAttribute("clientList", clientBean);
				return "redirect:clientHome.htm?AddSus=" + "Success" + "";

			} else {
				return "redirect:clientHome.htm?AddFail=" + "fail" + "";
			}
		} catch (FileNotFoundException e) {
			flag = false;
			e.printStackTrace();
			return "redirect:clientHome.htm?AddFail=" + "fail" + "";
		}

	}

	@RequestMapping(value = "/searchClient", method = RequestMethod.GET)
	public String searchClient(
			@ModelAttribute("clientCmd") ClientInfoBean clientSearchBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<ClientInfoBean> clientBean = null;
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setAttribute("clientActive", "active");
			if (clientSearchBean.getClientName() == ""
					|| clientSearchBean.getClientName() == null) {
				clientBean = clientService.searchClient();
			} else {
				clientBean = clientService.searchClient(
						clientSearchBean.getClientName(), "name");
			}
			ses = request.getSession(false);
			if (ses != null) {
				clientSearchBean
						.setDeptId(String.valueOf(ses.getAttribute("")));
			}
			json = mapper.writeValueAsString(clientBean);
			map.addAttribute("clientList", json);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "clientHome";

	}

	@RequestMapping(value = "/searchOnClient", method = RequestMethod.POST)
	public @ResponseBody
	String searchsClient(ModelMap map, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "deptId", required = true) String deptId) {
		response.setCharacterEncoding("UTF-8");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		List<ClientInfoBean> clientBean = null;
		try {
			if (deptId != "") {
				HttpSession ses = request.getSession(false);
				ses.setAttribute("deptId", request.getParameter("deptId"));
				clientBean = clientService.searchClient(deptId, "deptId");
				json = mapper.writeValueAsString(clientBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "/editClient", method = RequestMethod.GET)
	public String editClient(
			@ModelAttribute("clientCmd") ClientInfoBean clientEditBean,
			Model model, ModelMap map, HttpServletResponse response,
			HttpServletRequest request, HttpSession ses) {
		List<ClientInfoBean> clientBean = null;
		ClientInfoBean editBean = null;
		List<ClientInfoBean> clientBeans = null;
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setAttribute("clientActive", "active");
			clientBean = clientService.searchClient(
					clientEditBean.getClientId(), "id");

			for (ClientInfoBean d : clientBean) {
				editBean = (ClientInfoBean) d;
			}
			String d = (String) ses.getAttribute("deptId");
			if (d != null) {
				clientBeans = clientService.searchClient(
						String.valueOf(ses.getAttribute("deptId")), "deptId");
			} else {
				clientBeans = clientService.searchClient();
			}
			json = mapper.writeValueAsString(clientBeans);
			request.setAttribute("clientList", json);
			model.addAttribute("clientCmd", editBean);
			map.addAttribute("clientEdit", clientBean);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "clientHome";

	}

	@RequestMapping(value = "/clientUpdate", method = RequestMethod.POST)
	public String deptUpdate(
			@ModelAttribute("clientCmd") ClientInfoBean clientUpBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("clientlogo") MultipartFile mfile) {
		response.setCharacterEncoding("UTF-8");
		boolean flag;
		String originalPath = null;
		String clientId = (String) ses.getAttribute("clientId");
		 File cssFile =null;
		try {
			String filePath = servletContext.getRealPath("Branding/"+clientId);
			originalPath = filePath;
			// File file=new File(originalPath);
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdir();
			}
			String fileName = mfile.getOriginalFilename();
			if (!("").equals(fileName)) {
				File newFile = new File(filePath, fileName);
				if (!newFile.exists()) {
					FileOutputStream fos = new FileOutputStream(newFile);
					fos.write(mfile.getBytes());
					fos.flush();
					fos.close();
				}
				clientUpBean.setLogoPath(filePath+File.separator+ fileName);
			}else{
				clientUpBean.setLogoPath("");
			}
			if(fileName.equals("")){
				clientUpBean.setLogoPath(clientUpBean.getEditPath());
			}else{
				clientUpBean.setLogoPath(filePath+File.separator+ fileName);
			}
			request.setAttribute("clientActive", "active");
			String theme=clientUpBean.getTheme();
			if(theme.equals("1")){
				cssFile = new File(servletContext.getRealPath("css1/style.css"));
			}else if (theme.equals("2")) {
				cssFile = new File(servletContext.getRealPath("css1/black.css"));
			}else{
			cssFile = new File(servletContext.getRealPath("css1/blue.css"));
			}
            File cssFilePath = new File(filePath+File.separator+"Theme.css");
            FileInputStream fis = new FileInputStream(cssFile);
            FileOutputStream fos = new FileOutputStream(cssFilePath);
            int count;
            while ((count = fis.read()) != -1) {
                  fos.write(count);
            }
			flag = clientService.updateClient(clientUpBean);
			fos.close();
			fis.close();
			if (flag == true) {
				List<ClientInfoBean> clientBean = clientService.searchClient();
				request.setAttribute("clientList", clientBean);
				return "redirect:clientHome.htm?UpdateSus=" + "Success" + "";

			} else {
				return "redirect:clientHome.htm?UpdateFail=" + "fail" + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:clientHome.htm?UpdateFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/deleteClient", method = RequestMethod.POST)
	public @ResponseBody
	String deleteDept(HttpServletResponse response,
			@RequestParam(value = "deleteId", required = true) String cId,
			@RequestParam(value = "deptId", required = true) String deptId,
			HttpSession ses, HttpServletRequest request) {
		boolean flag;
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		List<ClientInfoBean> clientBean = null;
		try {
			request.setAttribute("clientActive", "active");
			flag = clientService.deleteClient(cId);
			if (deptId != "") {
				clientBean = clientService.searchClient(
						String.valueOf(ses.getAttribute("deptId")), "deptId");
			} else {
				clientBean = clientService.searchClient();
			}

			for (ClientInfoBean c : clientBean) {
				if (flag == true) {
					c.setMsg("Success");
				} else {
					c.setMsg("Fail");
				}
			}
			json = mapper.writeValueAsString(clientBean);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@ModelAttribute("themeDetails")
	public Map<String, String> populateThemeDetails() {

		Map<String, String> map = null;
		try {
			map = populateService.populatePopUp("select * from themes ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
