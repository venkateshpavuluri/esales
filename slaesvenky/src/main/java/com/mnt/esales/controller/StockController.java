package com.mnt.esales.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mnt.esales.bean.StockBean;
import com.mnt.esales.service.CustomIdService;
import com.mnt.esales.service.PopulateService;
import com.mnt.esales.service.StockService;

@Controller
public class StockController {
	private Logger logger = Logger.getLogger(StockController.class);
	private @Autowired
	StockService stockService;
	@Autowired
	CustomIdService custIdService;
	@Autowired
	PopulateService populateService;
	@Autowired
	HttpSession session;
	boolean flag;

	@RequestMapping(value = "/stockEntry", method = RequestMethod.GET)
	public String stockHome(
			@ModelAttribute("STOCKTRANSFER") StockBean stockBeans,
			HttpServletResponse response, HttpSession session,
			HttpServletRequest request, ModelMap map, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		request.setAttribute("stockActive", "active");
		response.setCharacterEncoding("UTF-8");
		session.setAttribute("tabName", "Stock Entry");
		String clientId = (String) session.getAttribute("clientId");
		List<StockBean> stockBean = stockService.stockSearch(clientId);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(stockBean);
		request.setAttribute("listofstock", json);
		try {
			if (session != null) {
				stockBeans.setClientId(String.valueOf(clientId));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "stockEntry";
	}

	@RequestMapping(value = "/stockSearch", method = RequestMethod.GET)
	public String stockSearch(
			@ModelAttribute("STOCKTRANSFER") StockBean stBean,
			HttpServletResponse response, HttpSession session,
			HttpServletRequest request, ModelMap map, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("stockActive", "active");
		String clientId = (String) session.getAttribute("clientId");
		stBean.setClientId(clientId);
		String product_Id = stBean.getProductId();
		if (stBean.getProductId() == "" || stBean.getProductId() == null) {
			List<StockBean> stockBean = stockService.stockSearch(clientId);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(stockBean);
			request.setAttribute("listofstock", json);
		} else {
			List<StockBean> stockBean = stockService
					.stochSearchwithProduct(product_Id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(stockBean);
			request.setAttribute("listofstock", json);
		}

		return "stockEntry";
	}

	@RequestMapping(value = "/stockSearchWithClient", method = RequestMethod.POST)
	public @ResponseBody
	String getData(@ModelAttribute("STOCKTRANSFER") StockBean stockbean,
			HttpServletRequest request,
			@RequestParam("productId") String productId, HttpSession session,
			HttpServletResponse response) {
		request.setAttribute("stockActive", "active");
		response.setCharacterEncoding("UTF-8");
		List<StockBean> beans = null;
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			beans = stockService.stochSearchwithProduct(productId);
			json = mapper.writeValueAsString(beans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "/forQuantity", method = RequestMethod.POST)
	public @ResponseBody
	List<StockBean> getTotalQuanity(
			@ModelAttribute("STOCKTRANSFER") StockBean stockbean,
			HttpServletRequest request,
			@RequestParam("productId") String productId,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<StockBean> stockBean=null;
		try {
			stockBean = stockService.getTotalQuantity(productId);
			ObjectMapper mapper=new ObjectMapper();
			String json=mapper.writeValueAsString(stockBean);
			System.out.println(json);
			request.setAttribute("reStockList", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stockBean;
	}

	@RequestMapping(value = "/forQuantityEdit", method = RequestMethod.POST)
	public @ResponseBody
	String getTotalQuanityEdit(
			@ModelAttribute("STOCKTRANSFER") StockBean stockbean,
			HttpServletRequest request,
			@RequestParam("productIdedit") String productIdedit,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String value = null;
		try {
			//value = stockService.getTotalQuantity(productIdedit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	@ModelAttribute("client")
	public Map<String, String> populatebranches() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String sql = "select client_Id,client_Name from clientinfo";
			map = populateService.populatePopUp(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@ModelAttribute("product")
	public Map<String, String> populateRfqType() {
		List<StockBean> list = null;
		Iterator<StockBean> iterator = null;
		Map<String, String> map = new HashMap<String, String>();
		String sessclient = (String) session.getAttribute("clientId");
		try {
			list = stockService.getProductDetails(sessclient);
			iterator = list.iterator();
			while (iterator.hasNext()) {
				StockBean stock = (StockBean) iterator.next();
				map.put(stock.getProductId(),
						stock.getCategory() + " " + stock.getSubcategory()
								+ " " + stock.getProductName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@ModelAttribute("searchproduct")
	public Map<String, String> populateproducts(HttpServletRequest request,
			HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String clientId = (String) session.getAttribute("clientId");

			String sql = "SELECT p.`product_Id`,p.`prod_Desc`FROM stock s JOIN product p ON p.`product_Id`=s.prod_Id JOIN client_product cp ON cp.`product_Id`=p.`product_Id` WHERE cp.`client_Id`='"
					+ clientId + "'";
			map = populateService.populatePopUp(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "/stockAdd", method = RequestMethod.POST)
	public String stockSave(@ModelAttribute("STOCKTRANSFER") StockBean sBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("stockActive", "active");
			flag = stockService.stockSave(sBean,true);
			if (flag == true) {
				List<StockBean> stBean = stockService.stockSearch();
				request.setAttribute("listofstock", stBean);
				return "redirect:stockEntry.htm?AddSus=" + "Success" + "";

			} else {
				return "redirect:stockEntry.htm?AddFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:stockEntry.htm?AddFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/stockEdit", method = RequestMethod.GET)
	public String stockEdit(
			@ModelAttribute("STOCKTRANSFER") StockBean stockBean, Model model,
			ModelMap map, HttpServletResponse response,HttpServletRequest request, HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		List<StockBean> stBean = null;
		StockBean editBean = null;
		try {
			request.setAttribute("stockActive", "active");
			stBean = stockService.stockEdit(stockBean.getStock_Id(), "id");

			for (StockBean d : stBean) {
				editBean = (StockBean) d;
			}
			String clientId = (String) session.getAttribute("clientId");
			List<StockBean> stockBeans = stockService.stockSearch(clientId);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(stockBeans);
			request.setAttribute("listofstock", json);
			model.addAttribute("STOCKTRANSFER", editBean);
			map.addAttribute("stockentryEdit", stBean);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stockEntry";

	}

	@RequestMapping(value = "/stockUpdate", method = RequestMethod.POST)
	public String stockUpdate(
			@ModelAttribute("STOCKTRANSFER") StockBean stockupdateBean,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("stockActive", "active");
			flag = stockService.stockUpdate(stockupdateBean);
			if (flag == true) {
				List<StockBean> stBean = stockService.stockSearch();
				request.setAttribute("StockList", stBean);
				return "redirect:stockEntry.htm?UpdateSus=" + "Success" + "";

			} else {
				return "redirect:stockEntry.htm?UpdateFail=" + "fail" + "";
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:stockEntry.htm?UpdateFail=" + "fail" + "";

		}

	}

	@RequestMapping(value = "/stockDelete", method = RequestMethod.GET)
	public String stockDelete(
			@ModelAttribute("STOCKTRANSFER") StockBean stBean,
			HttpServletResponse response,HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		try {
			request.setAttribute("stockActive", "active");
			flag = stockService.stockDelete(stBean.getStock_Id());
			if (flag == true) {
				return "redirect:stockEntry.htm?DeleteSus=" + "Success" + "";
			} else {
				return "redirect:stockEntry.htm?DeleteFail=" + "fail" + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:stockEntry.htm?DeleteFail=" + "fail" + "";
		}

	}
}
