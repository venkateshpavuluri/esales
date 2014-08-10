/**
 * 
 */
package com.mnt.esales.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.service.CategoryService;
import com.mnt.esales.service.CustomIdService;
import com.mnt.esales.service.PopulateService;

/**
 * @author yogi
 * 
 */
@Controller
public class CategoryController {
	@Autowired
	CustomIdService custIdService;
	@Autowired
	CategoryService catService;
	@Autowired
	PopulateService populateService;
	boolean flag = true;
	List<CategoryBean> listCatSearch;
	private Logger logger = Logger.getLogger(CategoryController.class);

	@RequestMapping(value = "/catHome", method = RequestMethod.GET)
	public String categoryHome(
			@ModelAttribute("catCmd") CategoryBean categoryBean,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		String json = "";
		try {
			request.setAttribute("catActive", "active");
			if (session != null) {
				/*
				 * categoryBean.setDeptId(String.valueOf(session
				 * .getAttribute("deptId")));
				 * System.out.println("with in home deptId=="
				 * +String.valueOf(session .getAttribute("deptId")));
				 */
			}
			List<CategoryBean> catBean = catService.categorySearch();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(catBean);
			request.setAttribute("CatList", json);
			session.setAttribute("tabName", "Category");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "catHome";
	}

	@ModelAttribute("deptDeatails")
	public Map<String, String> populateDeptDetails() {

		Map<String, String> map = null;
		try {
			map = catService.populateCategory();
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/catAdd", method = RequestMethod.POST)
	public String categorySave(
			@ModelAttribute("catCmd") CategoryBean categoryBean, ModelMap map,
			HttpServletRequest request, BindingResult result,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("catActive", "active");
			//HttpSession session = request.getSession(false);
			// session.setAttribute("deptId", categoryBean.getDeptId());
			/*if (!"admin".equals((String) session.getAttribute("admin"))) {
				session.setAttribute("deptId",
						(String) session.getAttribute("deptId"));
			}*/
			flag = catService.saveCategory(categoryBean, true);
			if (flag == true) {
				listCatSearch = catService.categorySearch();
				request.setAttribute("CatList", listCatSearch);
				return "redirect:catHome.htm?AddSus=" + "Success" + "";
			} else {
				return "redirect:catHome.htm?AddFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:catHome.htm?AddFail=" + "fail" + "";
		}
	}

	@RequestMapping(value = "/searchCat", method = RequestMethod.GET)
	public String searchCat(
			@ModelAttribute("catCmd") CategoryBean categoryBean, ModelMap map,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<CategoryBean> catBean = null;
		String json = "";
		request.setAttribute("catActive", "active");
		HttpSession session = request.getSession(false);
		String admin = (String) session.getAttribute("admin");
		if ("clientadmin".equals(admin)) {
			session.setAttribute("deptId",
					(String) session.getAttribute("deptId"));
		} else {
			session.setAttribute("deptId", session.getAttribute("deptId"));
		}
		try {
			if (categoryBean.getCategoryDesc() == ""
					|| categoryBean.getCategoryDesc() == null) {
				catBean = catService.catSearch("total", "total");
			} else {
				catBean = catService.catSearch(categoryBean.getCategoryDesc(),
						"name");
			}
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(catBean);
			request.setAttribute("CatList", json);
			if (session != null) {
				categoryBean.setDeptId((String.valueOf(session
						.getAttribute("deptId"))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "catHome";
	}

	@RequestMapping(value = "/editCat", method = RequestMethod.GET)
	public String editCat(@ModelAttribute("catCmd") CategoryBean categoryBean,
			Model model, ModelMap map, HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<CategoryBean> catBean = null;
		CategoryBean editBean = null;
		String json=null;
		try {
			request.setAttribute("catActive", "active");
			catBean = catService.catSearch(categoryBean.getCategoryId(), "id");
			for (CategoryBean c : catBean) {
				editBean = (CategoryBean) c;
			}
			model.addAttribute("catCmd", editBean);
			map.addAttribute("catEdit", catBean);
			List<CategoryBean> catBeanList = catService.categorySearch();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(catBeanList);
			request.setAttribute("CatList", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "catHome";
	}

	@RequestMapping(value = "/catUpdate", method = RequestMethod.POST)
	public String catUpdate(
			@ModelAttribute("catCmd") CategoryBean categoryBean, ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("catActive", "active");
			flag = catService.catUpdate(categoryBean);
			if (flag == true) {
				List<CategoryBean> catBean = catService.categorySearch();
				request.setAttribute("CatList", catBean);
				return "redirect:catHome.htm?UpdateSus=" + "Success" + "";

			} else {
				return "redirect:catHome.htm?UpdateFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:catHome.htm?UpdateFail=" + "fail" + "";
		}
	}

	@RequestMapping(value = "/deleteCat", method = RequestMethod.GET)
	public String deleteCat(@ModelAttribute("catCmd") CategoryBean catBean,
			HttpServletResponse response,HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("catActive", "active");
			flag = catService.catDelete(catBean.getCategoryId());

			if (flag == true) {
				return "redirect:catHome.htm?DeleteSus=" + "Success" + "";
			} else {
				return "redirect:catHome.htm?DeleteFail=" + "fail" + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:catHome.htm?DeleteFail=" + "fail" + "";
		}
	}

	@RequestMapping(value = "/catDuplicate", method = RequestMethod.POST)
	public @ResponseBody
	String categoryDuplicate(
			@ModelAttribute("catCmd") CategoryBean categoryBean,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("catDesc") String catDesc) {
		response.setCharacterEncoding("UTF-8");
		int count = 0;
		String msg = null;
		try {
			
			count = populateService
					.DuplicateCheck("SELECT COUNT(*) FROM `category` c, category_dept cd  "
							+ "WHERE c.`category_Desc` = '"
							+ catDesc
							+ "' AND cd.dept_Id= '"
							+ (String) session.getAttribute("deptId")
							+ "' AND c.`category_Id`=cd.`category_Id`");
			if (count != 0) {
				msg = "Warning ! Category is already exists. Please try some other name";
			} else {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/catEditDuplicate", method = RequestMethod.POST)
	public @ResponseBody
	String catEditDuplicate(
			@ModelAttribute("catCmd") CategoryBean categoryBean,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("categoryId") String categoryId,
			@RequestParam("categoryDesc") String categoryDesc) {
		response.setCharacterEncoding("UTF-8");
		int count = 0;
		String msg = null;
		try {
			count = populateService
					.DuplicateCheck("select count(*) from category C, category_dept cd where  C.category_Desc='"
							+ categoryDesc
							+ "' and C.category_Id = cd.category_Id and cd.dept_Id='"
							+ (String) session.getAttribute("deptId")
							+ "' and C.category_Id !='" + categoryId + "'");
			if (count != 0) {
				msg = "Warning ! Category  is already exists. Please try some other name";
			} else {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/searchingBasedOnDepartment", method = RequestMethod.POST)
	public @ResponseBody
	String searchingBasedOnDepartment(
			@ModelAttribute("catCmd") CategoryBean categoryBean,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session = request.getSession(false);
		try {
			String deptId = request.getParameter("deptId");
			session.setAttribute("deptId", deptId);

			listCatSearch = catService.catSearch(null, deptId);
			json = mapper.writeValueAsString(listCatSearch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return json;
	}
}
