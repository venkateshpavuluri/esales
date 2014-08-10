package com.mnt.esales.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mnt.esales.bean.SubCategoryBean;
import com.mnt.esales.service.PopulateService;
import com.mnt.esales.service.SubCategoryService;

@Controller
public class SubCategoryController {
	@Autowired
	PopulateService populateService;
	@Autowired
	SubCategoryService subCategoryService;
	boolean flag;
	List<SubCategoryBean> listSubCatSearch;
	@Autowired
	HttpServletRequest httpRequest;
	@Autowired
	HttpSession session;
	private Logger logger = Logger.getLogger(CategoryController.class);

	@RequestMapping(value = "/subCatHome", method = RequestMethod.GET)
	public String subCategoryHome(
			@ModelAttribute("subCatCmd") SubCategoryBean subCategoryBean,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		String json = "";
		request.setAttribute("subCatActive","active");
		List<SubCategoryBean> subcatBean = subCategoryService.subCategorySearch();
		ObjectMapper mapper = new ObjectMapper();
		json = mapper.writeValueAsString(subcatBean);
		request.setAttribute("SubCatList", json);
		session.setAttribute("tabName", "SubCategory");
		try {
			if (session != null) {
				subCategoryBean.setDeptId(String.valueOf(session
						.getAttribute("deptId")));
				subCategoryBean.setCategoryId(String.valueOf(session
						.getAttribute("categoryId")));
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "subCatHome";
	}

	@RequestMapping(value = "/populateCategory", method = RequestMethod.POST)
	public @ResponseBody
	String forCategory(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("deptId") String deptId) {
		response.setCharacterEncoding("UTF-8");
		// List<SubCategoryPopulateBean> subCategoryPopulateBeans = null;
		StringWriter out = new StringWriter();
		JSONArray jsonArray = null;
		try {
			jsonArray = subCategoryService.getCategory(deptId);
			if (jsonArray != null) {
				jsonArray.writeJSONString(out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.toString();
	}

	@ModelAttribute("departmentDetails")
	public Map<String, String> populateDeptartmentDetails() {
		Map<String, String> map = null;
		// String clientId= session.getAttribute("clientId").toString();
		try {
			map = populateService
					.populatePopUp("SELECT d.dept_Id AS deptId, d.dept_Desc AS deptName FROM department d 	JOIN dept_client dc ON d.dept_Id = dc.dept_Id order by d.dept_Desc");
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	@ModelAttribute("categroyDetails")
	public Map<String, String> populateCategoryDetails() {
		Map<String, String> map = null;
		String clientId = (String) session.getAttribute("clientId");
		String deptId = (String) session.getAttribute("deptId");
		try {
			map = populateService
					.populatePopUp("SELECT C.`category_Id`, C.`category_Desc` as categoryDesc FROM category C,client_category cc , `department` d, `category_dept` cd WHERE C.category_Id=cc.category_Id AND C.`category_Id`=cd.`category_Id`AND cd.`dept_Id`=d.`dept_Id`AND cc.`client_Id`='"
							+ clientId
							+ "' and d.dept_Id ='"
							+ deptId
							+ "'order by C.category_Desc");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/subCatAdd", method = RequestMethod.POST)
	public String subCategorySave(
			@ModelAttribute("subCatCmd") SubCategoryBean subCategoryBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("subCatActive","active");
			flag = subCategoryService.saveSubCategory(subCategoryBean, true);
			// boolean result = catService.saveCatDepartmetnt(categoryBean);
			String json= null;
			if (flag == true) {
				listSubCatSearch = subCategoryService.subCategorySearch();
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(listSubCatSearch);
				request.setAttribute("SubCatList", json);
				session.setAttribute("tabName", "SubCategory");
				return "redirect:subCatHome.htm?AddSus=" + "Success" + "";
			} else {
				return "redirect:subCatHome.htm?AddFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:subCatHome.htm?AddFail=" + "fail" + "";
		}
	}

	@RequestMapping(value = "/subCatSearch", method = RequestMethod.GET)
	public String subCatSearch(
			@ModelAttribute("subCatCmd") SubCategoryBean subCategoryBean,
			ModelMap map, HttpServletResponse response,
			HttpServletRequest request) {
		request.setAttribute("subCatActive","active");
		response.setCharacterEncoding("UTF-8");
		List<SubCategoryBean> subCatBean = null;
		HttpSession session = request.getSession(false);
		String admin = (String) session.getAttribute("admin");
		String json = "";
		session.setAttribute("categoryId",
				(String) session.getAttribute("categoryId"));
		/*
		 * if ("clientadmin".equals(admin)) {
		 * 
		 * } else { session.setAttribute("categoryId", (String)
		 * session.getAttribute("categoryId")); }
		 */
		try {
			if (subCategoryBean.getSubCategoryDesc() == ""
					|| subCategoryBean.getSubCategoryDesc() == null) {
				if ((String) session.getAttribute("categoryId") != null
						&& (String) session.getAttribute("categoryId") == "") {
					subCatBean = subCategoryService.search(
							(String) session.getAttribute("categoryId"), null);
				} else {
					subCatBean = subCategoryService.search(null, null);
				}
			} else {
				subCatBean = subCategoryService.search(
						subCategoryBean.getSubCategoryDesc(), "name");
			}

			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(subCatBean);
			request.setAttribute("SubCatList", json);
			if (session != null) {
				// subCategoryBean.setCategoryId((String.valueOf(session.getAttribute("categoryId"))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "subCatHome";

	}

	@RequestMapping(value = "/editSubCat", method = RequestMethod.GET)
	public String editSubCat(
			@ModelAttribute("subCatCmd") SubCategoryBean subCategoryBean,
			Model model, ModelMap map, HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<SubCategoryBean> subCatBean = null;
		SubCategoryBean editBean = null;

		try {
			request.setAttribute("subCatActive","active");
			String json = "";
			List<SubCategoryBean> subcatBean = subCategoryService.search(null, null);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(subcatBean);
			request.setAttribute("SubCatList", json);
			
			subCatBean = subCategoryService.search(
					subCategoryBean.getSubCategoryId(), "id");
			for (SubCategoryBean c : subCatBean) {
				editBean = (SubCategoryBean) c;
			}
			model.addAttribute("subCatCmd", editBean);
			map.addAttribute("subCatEdit", subCatBean);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "subCatHome";

	}

	@RequestMapping(value = "/subCatUpdate", method = RequestMethod.POST)
	public String subCatUpdate(
			@ModelAttribute("subCatCmd") SubCategoryBean subCategoryBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("subCatActive","active");
			flag = subCategoryService.subCatUpdate(subCategoryBean);
			String json=null;
			if (flag == true) {
				List<SubCategoryBean> catBean = subCategoryService
						.subCategorySearch();
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(catBean);
				request.setAttribute("SubCatList", json);
				return "redirect:subCatHome.htm?UpdateSus=" + "Success" + "";

			} else {
				return "redirect:subCatHome.htm?UpdateFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:subCatHome.htm?UpdateFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/deleteSubCat", method = RequestMethod.GET)
	public String deleteSubCat(
			@ModelAttribute("subCatCmd") SubCategoryBean subCatBean,
			HttpServletResponse response,HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("subCatActive","active");
			flag = subCategoryService.subCatDelete(subCatBean
					.getSubCategoryId());

			if (flag == true) {
				return "redirect:subCatHome.htm?DeleteSus=" + "Success" + "";
			} else {
				return "redirect:subCatHome.htm?DeleteFail=" + "fail" + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:subCatHome.htm?DeleteFail=" + "fail" + "";
		}
	}

	@RequestMapping(value = "/subCatDuplicate", method = RequestMethod.POST)
	public @ResponseBody
	String subCategoryDuplicate(
			@ModelAttribute("subCatCmd") SubCategoryBean subCatBean,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		int dupId = 0;
		String message = null;
		String subcatDesc = request.getParameter("subCategoryDesc");
		String subCatId = request.getParameter("subCategoryId");
		try {
			if (subCatId != null) {
				dupId = subCategoryService.subCategoryDuplicate(subCatId,
						subcatDesc);
			} else {

				dupId = subCategoryService.subCategoryDuplicate(null,
						subcatDesc);
			}
			if (dupId == 1) {
				message = "SubCategory already exist";
			} else {
				message = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return message;
	}

	@RequestMapping(value = "/searchingBasedOnCategory", method = RequestMethod.POST)
	public @ResponseBody
	String searchingBasedOnSubcategory(
			@ModelAttribute("subCatCmd") SubCategoryBean subCatBean,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		String catId = request.getParameter("categoryId");
		logger.debug("category id in search iss==" + catId);
		if (!"".equals(catId)) {
			HttpSession session = request.getSession(false);
			session.setAttribute("categoryId", catId);
		}
		try {
			if("".equals(catId)){
				 listSubCatSearch = subCategoryService.search(null, null);
			}else{
				listSubCatSearch = subCategoryService.search(catId, null);
			}
			json = mapper.writeValueAsString(listSubCatSearch);
			request.setAttribute("SubCatList", json);
			subCatBean.setCategoryId(catId);
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return json;
	}
}
