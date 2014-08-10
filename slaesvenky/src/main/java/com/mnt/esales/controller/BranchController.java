/**
 * 
 */
package com.mnt.esales.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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

import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.dao.CustomIdDao;
import com.mnt.esales.service.BranchService;
import com.mnt.esales.service.PopulateService;

/**
 * @author Naresh
 * 
 */
@Controller
public class BranchController {
	private Logger logger = Logger.getLogger(BranchController.class);
	@Autowired
	BranchService branchService;
	@Autowired
	PopulateService populateService;
	@Autowired
	HttpSession session;
	@Autowired
	CustomIdDao custIdDao;
	@Autowired
	ServletContext servletContext;
	@RequestMapping(value = "/branchHome", method = RequestMethod.GET)
	public String branchHome(
			@ModelAttribute("branchCmd") BranchBean branchBeans,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap map, Model model) throws JsonGenerationException,
			JsonMappingException, IOException {
		request.setAttribute("branchActive", "active");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		String json = "";
		List<BranchBean> branchBean = branchService.searchBranch(
				(String) session.getAttribute("clientId"), "clientId");
		ObjectMapper mapper = new ObjectMapper();
		json = mapper.writeValueAsString(branchBean);
		request.setAttribute("branchList", json);
		try {
			if (session != null) {
				branchBeans.setClientId(String.valueOf(session
						.getAttribute("clientId")));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		session.setAttribute("tabName", "Branch");
		return "branchHome";
	}

	@ModelAttribute("clientIds")
	public Map<String, String> ClientIds() {
		Map<String, String> map = null;

		map = populateService
				.populatePopUp("select client_Id,client_Name from clientinfo order by client_Name");
		return map;

	}

	@RequestMapping(value = "/branchDuplCheck", method = RequestMethod.POST)
	public @ResponseBody
	String getBranch(
			@RequestParam(value = "branchName", required = true) String branch,
			HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		int count = 0;
		String msg = null;
		try {
			count = populateService
					.DuplicateCheck("SELECT COUNT(*) FROM `branch` WHERE `branch_Name`='"
							+ branch
							+ "' and client_Id='"
							+ (String) session.getAttribute("clientId") + "'");
			if (count != 0) {
				msg = "Warning ! Branch Name is already exists. Please try some other name";
			} else {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/branchDuplCheckEdit", method = RequestMethod.POST)
	public @ResponseBody
	String getBranchEdit(
			@RequestParam(value = "branchName", required = true) String branchName,
			@RequestParam(value = "branchId", required = true) String branchId,
			HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		int count = 0;
		String msg = null;
		try {
			count = populateService
					.DuplicateCheck("SELECT COUNT(*) FROM `branch` WHERE `branch_Name`='"
							+ branchName
							+ "' and branch_Id!='"
							+ branchId
							+ "'and client_Id='"
							+ (String) session.getAttribute("clientId") + "'");
			if (count != 0) {
				msg = "Warning ! Branch Name is already exists. Please try some other name";
			} else {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/branchAdd", method = RequestMethod.POST)
	public String branchSave(
			@ModelAttribute("branchCmd") BranchBean branchAddBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("clientlogo") MultipartFile mfile) {
		response.setCharacterEncoding("UTF-8");
		boolean flag;
		String clientId = branchAddBean.getClientId();
		String filePath = null;
		File cssFile=null;
		try {
			String maxId = custIdDao
					.getMaxId("select max(branch_Code) from branch");
			if (maxId != null) {
				filePath = servletContext.getRealPath("Branding/"+maxId);
			} else {
				filePath = servletContext.getRealPath("Branding/B0001") ;
			}
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdir();
			}
			String fileName = mfile.getOriginalFilename();
			if (!("").equals(fileName)) {

			request.setAttribute("branchActive", "active");
				File newFile = new File(filePath, fileName);
				if (!newFile.exists()) {
					FileOutputStream fos = new FileOutputStream(newFile);
					fos.write(mfile.getBytes());
					fos.flush();
					fos.close();
				}
				branchAddBean.setLogoPath(filePath +File.separator+ fileName);
			}else{
				branchAddBean.setLogoPath("");
			}
			
			String theme=branchAddBean.getBranchTheme();
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
            flag = branchService.saveBranch(branchAddBean,true);
			fos.close();
			fis.close();
			if (flag == true) {
				List<BranchBean> branchBean = branchService.searchBranch();
				request.setAttribute("branchList", branchBean);
				return "redirect:branchHome.htm?AddSus=" + "Success" + "";

			} else {
				return "redirect:branchHome.htm?AddFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:branchHome.htm?AddFail=" + "fail" + "";
		}
	}
	@RequestMapping(value = "/searchBranch", method = RequestMethod.GET)
	public String searchBranch(
			@ModelAttribute("branchCmd") BranchBean branchSearchBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<BranchBean> branchBean = null;
		String json = "";
		HttpSession session = request.getSession(false);
		try {
			request.setAttribute("branchActive", "active");
			if (branchSearchBean.getBranchName() == ""
					|| branchSearchBean.getBranchName() == null) {
				branchBean = branchService.searchBranch();

			} else {
				branchBean = branchService.searchBranch(
						branchSearchBean.getBranchName(), "name");
			}
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(branchBean);
			map.addAttribute("branchList", json);
			if (session != null) {
				branchSearchBean.setClientId(String.valueOf(session
						.getAttribute("clientId")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "branchHome";

	}

	@RequestMapping(value = "/searchOnBranch", method = RequestMethod.POST)
	public @ResponseBody
	String searchsBranch(ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		List<BranchBean> branchBean = null;
		try {
			if (request.getParameter("clientId") != "") {
				HttpSession ses = request.getSession(false);
				ses.setAttribute("clientId", request.getParameter("clientId"));
				branchBean = branchService.searchBranch(
						request.getParameter("clientId"), "clientId");
				json = mapper.writeValueAsString(branchBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;

	}

	@RequestMapping(value = "/editBranch", method = RequestMethod.GET)
	public String editBranch(
			@ModelAttribute("BranchCmd") BranchBean branchEditBean,
			Model model, ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<BranchBean> branchBean = null;
		BranchBean editBean = null;
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setAttribute("branchActive", "active");
			branchBean = branchService.searchBranch(
					branchEditBean.getBranchId(), "id");

			for (BranchBean d : branchBean) {
				editBean = (BranchBean) d;
			}
			List<BranchBean> branchBeans = branchService.searchBranch();
			json = mapper.writeValueAsString(branchBeans);
			request.setAttribute("branchList", json);
			model.addAttribute("branchCmd", editBean);
			map.addAttribute("branchEdit", branchBean);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "branchHome";

	}

	@RequestMapping(value = "/branchUpdate", method = RequestMethod.POST)
	public String branchUpdate(
			@ModelAttribute("branchCmd") BranchBean branchUpBean, ModelMap map,
			HttpServletRequest request, HttpServletResponse response,@RequestParam("clientlogo") MultipartFile mfile) {
		response.setCharacterEncoding("UTF-8");
		boolean flag=false;
		String originalPath = null;
		String fileName=null;
		File cssFile=null;
		try {
			String maxId = custIdDao
					.getMaxId("select max(branch_Code) from branch");
			String filePath = servletContext.getRealPath("Branding/"+maxId);
			originalPath = filePath ;
			// File file=new File(originalPath);
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdir();
			}
			fileName = mfile.getOriginalFilename();
			if (!("").equals(fileName)) {

				File newFile = new File(filePath, fileName);

				if (!newFile.exists()) {
					// System.out.println("file not exists===");
					FileOutputStream fos = new FileOutputStream(newFile);
					fos.write(mfile.getBytes());
					fos.flush();
					fos.close();
				}
				branchUpBean.setLogoPath(filePath+File.separator+ fileName);
			}else{
				branchUpBean.setLogoPath("");
			}
			if(fileName.equals("")){
				branchUpBean.setLogoPath(branchUpBean.getEditPath());
			}else{
				branchUpBean.setLogoPath(filePath+File.separator+ fileName);
			}
			request.setAttribute("branchActive", "active");
			String theme=branchUpBean.getBranchTheme();
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
            flag = branchService.updateBranch(branchUpBean);
			fos.close();
			fis.close();
			if (flag == true) {
				List<BranchBean> branchBean = branchService.searchBranch();
				request.setAttribute("branchList", branchBean);
				return "redirect:branchHome.htm?UpdateSus=" + "Success" + "";

			} else {
				return "redirect:branchHome.htm?UpdateFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:branchHome.htm?UpdateFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/deleteBranch", method = RequestMethod.POST)
	public @ResponseBody
	String deleteBranch(
			@RequestParam(value = "deleteId", required = true) String dId,
			@RequestParam(value = "clId", required = true) String cId,
			HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		boolean flag;
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		List<BranchBean> branchBean = null;
		try {
			flag = branchService.deleteBranch(dId);
			if (cId != null) {
				branchBean = branchService.searchBranch(
						(String) session.getAttribute("clientId"), "clientId");
			} else {
				branchBean = branchService.searchBranch();
			}
			for (BranchBean b : branchBean) {
				if (flag == true) {
					b.setMsg("Success");
				} else {
					b.setMsg("Fail");
				}
			}
			json = mapper.writeValueAsString(branchBean);

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
