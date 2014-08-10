package com.mnt.esales.controller;

import java.io.IOException;
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
import org.springframework.web.servlet.ModelAndView;

import com.mnt.esales.bean.ExpensesBean;
import com.mnt.esales.service.ExpensesService;
import com.mnt.esales.service.PopulateService;

/**
 * @author srinivas
 * 
 */
@Controller
public class ExpensesController {

	@Autowired
	ExpensesService expensesService;
	@Autowired
	PopulateService populateService;
	@Autowired
	HttpSession session;

	boolean flag;

	@RequestMapping(value = "/expensesHome", method = RequestMethod.GET)
	public ModelAndView expensesHome(HttpServletResponse response,
			HttpServletRequest request, ModelMap map, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("expActive", "active");
		String role = (String) session.getAttribute("admin");
		session.setAttribute("tabName", "Expenses");
		if (role.equals("sales")) {
			List<ExpensesBean> expBean = expensesService.ExpensesSearch();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(expBean);
			request.setAttribute("listofexpenses", json);
		}
		return new ModelAndView("expensesHome", "EXPENSES", new ExpensesBean());
	}

	@RequestMapping(value = "/expsearchwithbranch", method = RequestMethod.POST)
	public @ResponseBody
	String getData(@ModelAttribute("STOCKTRANSFER") ExpensesBean expbean,
			HttpServletRequest request,
			@RequestParam("branchId") String branchId, HttpSession session,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<ExpensesBean> beans = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			request.setAttribute("expActive", "active");
			beans = expensesService.ExpensesSearchWithId(branchId);
			json = mapper.writeValueAsString(beans);
			request.setAttribute("listofexpenseswithbranch", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@ModelAttribute("branch")
	public Map<String, String> populatebranches() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String clientId = (String) session.getAttribute("clientId");
			String sql = "SELECT b.branch_Id,b.branch_Name FROM branch b WHERE b.client_Id='"
					+ clientId + "'";
			map = populateService.populatePopUp(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@ModelAttribute("branchs")
	public Map<String, String> populatebranchess() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String clientId = (String) session.getAttribute("clientId");
			String userid = (String) session.getAttribute("userId");
			String sql = "SELECT b.branch_Id,b.branch_Name FROM branch b JOIN permissions p ON b.`branch_Id`=p.`branch_Id` WHERE b.client_Id='"
					+ clientId + "' AND p.user_Id='" + userid + "'";
			map = populateService.populatePopUp(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "/expSearch", method = RequestMethod.GET)
	public ModelAndView expensesSearch(ExpensesBean expensesBean,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap map, Model model) {
		request.setAttribute("expActive", "active");
		response.setCharacterEncoding("UTF-8");
		String billNo = expensesBean.getBillno();
		List<ExpensesBean> expBean = expensesService
				.ExpensesSearchWithId(billNo);
		request.setAttribute("listofexpenseswithId", expBean);
		return new ModelAndView("expensesHome", "EXPENSES", new ExpensesBean());
	}

	@RequestMapping(value = "/expAdd", method = RequestMethod.POST)
	public String expensesSave(
			@ModelAttribute("EXPENSES") ExpensesBean expBean, ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("expActive", "active");
			flag = expensesService.ExpensesSave(expBean,true);
			if (flag == true) {
				String role = (String) session.getAttribute("admin");
				if (role.equals("sales")) {
					List<ExpensesBean> exBean = expensesService
							.ExpensesSearch();
					request.setAttribute("listofexpenses", exBean);
				}
				return "redirect:expensesHome.htm?AddSus=" + "Success" + "";

			} else {
				return "redirect:expensesHome.htm?AddFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:expensesHome.htm?AddFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/expEdit", method = RequestMethod.GET)
	public String expensesEdit(
			@ModelAttribute("EXPENSES") ExpensesBean expBean, Model model,
			ModelMap map, HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<ExpensesBean> expensesBean = null;
		ExpensesBean editBean = null;
		String json, branchId = null;
		try {
			request.setAttribute("expActive", "active");
			ObjectMapper mapper = new ObjectMapper();
			expensesBean = expensesService.ExpensesEdit(
					expBean.getExpenses_Id(), "id");
			for (ExpensesBean d : expensesBean) {
				editBean = (ExpensesBean) d;
				model.addAttribute("EXPENSES", editBean);
				branchId = editBean.getBranchId();
			}
			map.addAttribute("expensesEdit", expensesBean);
			expensesBean = expensesService.ExpensesSearchWithId(branchId);
			json = mapper.writeValueAsString(expensesBean);
			request.setAttribute("listofexpenses", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "expensesHome";

	}

	@RequestMapping(value = "/expUpdate", method = RequestMethod.POST)
	public String expensesUpdate(
			@ModelAttribute("EXPENSES") ExpensesBean updateBean, ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("expActive", "active");
			flag = expensesService.ExpensesUpdate(updateBean);
			if (flag == true) {
				List<ExpensesBean> expBean = expensesService.ExpensesSearch();
				request.setAttribute("ExpList", expBean);
				return "redirect:expensesHome.htm?UpdateSus=" + "Success" + "";

			} else {
				return "redirect:expensesHome.htm?UpdateFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:expensesHome.htm?UpdateFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/expDelete", method = RequestMethod.GET)
	public String expensesDelete(
			@ModelAttribute("EXPENSES") ExpensesBean deleteBean,
			HttpServletResponse response,HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("expActive", "active");
			flag = expensesService.ExpensesDelete(deleteBean.getExpenses_Id());

			if (flag == true) {
				return "redirect:expensesHome.htm?DeleteSus=" + "Success" + "";
			} else {
				return "redirect:expensesHome.htm?DeleteFail=" + "fail" + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:expensesHome.htm?DeleteFail=" + "fail" + "";
		}

	}
}
