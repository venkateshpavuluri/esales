package com.mnt.esales.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockReturnBean;
import com.mnt.esales.service.PopulateService;
import com.mnt.esales.service.StockReturnService;

@Controller
public class StockReturnController {
	@Autowired
	PopulateService service;
	@Autowired
	HttpServletRequest request;
	@Autowired
	StockReturnService stockReturnService;

	@RequestMapping(value = "/stockReturnHome", method = RequestMethod.GET)
	public String deptHome(
			@ModelAttribute("stockCmd") StockReturnBean stockReturnBean,
			HttpServletResponse response, HttpServletRequest request) {
		HttpSession session= request.getSession(false);
		session.setAttribute("tabName", "Stock Return");
		if("sales".equals((String)session.getAttribute("admin"))){
			/*stockReturnSearch();*/
		}
		return "stockReturnHome";
	}

	@ModelAttribute("branchDeatails")
	public Map<String, String> populateDeptDetails() {

		Map<String, String> map = null;
		HttpSession session = request.getSession(false);
		String clientId = (String) session.getAttribute("clientId");
		try {
			map = service
					.populatePopUp("select b.branch_Id ,b.branch_Name from branch b, branch_client bc where b.branch_Id = bc.branch_Id and bc.client_Id='"
							+ clientId + "'");
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/stockReturnSearch", method = RequestMethod.POST)
	public String stockReturnSearch(
			@ModelAttribute("stockCmd") StockReturnBean stockReturnBean,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<StockReturnBean> stockCurrentList = new ArrayList<StockReturnBean>();
		List<StockReturnBean> stockReturnList = new ArrayList<StockReturnBean>();
		HttpSession session = request.getSession(false);
		try {
			String clientId = (String) session.getAttribute("clientId");
			String branchId = stockReturnBean.getBranchId();
			System.out.println(branchId+"== branchId");
			session.setAttribute("branchId", branchId);
			stockCurrentList = stockReturnService.getCurrentStock(branchId,clientId);
			stockReturnList = stockReturnService.getReturnStock(branchId,clientId);
			System.out.println(stockCurrentList+"list value");
			if(!"".equals(stockCurrentList) && !stockCurrentList.equals(null)){
				request.setAttribute("currentStockList", stockCurrentList);
				System.out.println(stockCurrentList+"list value in if");
			}
			request.setAttribute("returnStockList", stockReturnList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stockReturnHome";
	}
	private void stockRe(){
		
	}

	@RequestMapping(value = "/stockReturnSave", method = RequestMethod.POST)
	public String stockReturnSave(
			@ModelAttribute("stockCmd") StockReturnBean stockReturnBean,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<StockReturnBean> stockCurrentList = new ArrayList<StockReturnBean>();
		List<StockReturnBean> stockUpdateList = new ArrayList<StockReturnBean>();
		HttpSession session = request.getSession(false);
		boolean flag = false;
		try {
			String clientId = (String) session.getAttribute("clientId");
			String branchId = (String) session.getAttribute("branchId");
			stockCurrentList = stockReturnService.getCurrentStock(branchId,
					clientId);
			Iterator<StockReturnBean> itr = stockCurrentList.iterator();
			Calendar cal = Calendar.getInstance();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String regDate = dateFormat.format(cal.getTime());
			while (itr.hasNext()) {
				StockReturnBean returnBean = new StockReturnBean();
				StockReturnBean bean = (StockReturnBean) itr.next();
				returnBean.setReturnStock(request.getParameter(bean
						.getStockId() + bean.getProductId()));
				returnBean.setBranchId(bean.getBranchId());
				returnBean.setStockId(bean.getStockId());
				returnBean.setProductCode(bean.getProductId());
				returnBean.setReturnEntry(regDate);
				returnBean.setInStockQuantity(bean.getInStockQuantity());
				returnBean.setUserId((String) session.getAttribute("userId"));
				stockUpdateList.add(returnBean);
			}
			flag = stockReturnService.saveStockReturn(stockUpdateList);
			if (flag == true) {
				return "redirect:stockReturnHome.htm?AddSus=" + "Success" + "";
			} else {
				return "redirect:stockReturnHome.htm?AddFail=" + "fail" + "";
			}

		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
			return "redirect:stockReturnHome.htm?AddFail=" + "fail" + "";
		}

	}
}