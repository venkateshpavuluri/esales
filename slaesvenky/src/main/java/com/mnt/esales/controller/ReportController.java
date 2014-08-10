/**
 * 
 */
package com.mnt.esales.controller;


import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mnt.esales.bean.CatListReportBean;
import com.mnt.esales.bean.ExpenseReportBean;
import com.mnt.esales.bean.ProdListReportBean;
import com.mnt.esales.bean.StockReportBean;
import com.mnt.esales.bean.SubCatListReportBean;
import com.mnt.esales.bean.saleReportBean;
import com.mnt.esales.bean.todayReportBean;
import com.mnt.esales.service.PopulateService;
import com.mnt.esales.service.ReportService;

/**
 * @author Devi
 *
 */

@Controller
public class ReportController {
	
	@Autowired
	ReportService reportService;
	@Autowired
	PopulateService populateService;
	@Autowired
	HttpServletRequest hreq;
	
	
	
	@RequestMapping(value = "/expenseReportHome.htm", method = RequestMethod.GET)
	public ModelAndView expenseReportHome(@ModelAttribute("expenseCmd") ExpenseReportBean exBean,HttpServletResponse response,HttpServletRequest request,HttpSession sess) {
		response.setCharacterEncoding("UTF-8");
		sess.setAttribute("tabName", "Expenses Report");
		 // Setting response's headers
       return new ModelAndView("expenseReportHome","expenseCmd",exBean);

	}
	@RequestMapping(value = "/stockReportHome.htm", method = RequestMethod.GET)
	public ModelAndView stockReportHome(@ModelAttribute("stockCmd") StockReportBean stBean,HttpServletResponse response,HttpServletRequest request, HttpSession ses) {
		response.setCharacterEncoding("UTF-8");
		ses.setAttribute("tabName", "Stock Report");
		 // Setting response's headers
       return new ModelAndView("stockReportHome","stockCmd",stBean);

	}
	@RequestMapping(value = "/saleReportHome", method = RequestMethod.GET)
	public ModelAndView saleReportHome(@ModelAttribute("saleCmd") saleReportBean sbean,HttpServletResponse response,HttpServletRequest request,HttpSession ses) {
		response.setCharacterEncoding("UTF-8");
		ses.setAttribute("tabName", "Sale Report");
		 // Setting response's headers
       return new ModelAndView("saleReportHome","saleCmd",sbean);

	}
	@RequestMapping(value = "/todayReportHome", method = RequestMethod.GET)
	public ModelAndView salesReportHome(@ModelAttribute("todayCmd") todayReportBean rbean,HttpServletResponse response,HttpServletRequest request,HttpSession sess) {
		response.setCharacterEncoding("UTF-8");
		sess.setAttribute("tabName", "Today Report");
		 // Setting response's headers
       return new ModelAndView("todayReportHome","todayCmd",rbean);

	}
	@RequestMapping(value = "/dailySales", method = RequestMethod.GET)
	public String searchSale(
			@ModelAttribute("saleCmd") saleReportBean saleBean, ModelMap map,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<saleReportBean> saleProdList = new ArrayList<saleReportBean>();
		List<saleReportBean> saleSubCatList = new ArrayList<saleReportBean>();
		List<saleReportBean> saleCatList = new ArrayList<saleReportBean>();
		HttpSession session = request.getSession(false);
		String branchId=request.getParameter("branchId");
		String clientId=(String) session.getAttribute("clientId");
		String date=request.getParameter("sdate");
		Iterator<saleReportBean> citerator=null;
		Iterator<saleReportBean> siterator=null;
		Iterator<saleReportBean> piterator=null;
		List<SubCatListReportBean> sbeanlist=null;
		List<ProdListReportBean> pbeanlist=null;
		double camount=0;
		try{
			
			saleProdList=reportService.saleProdSearch(branchId, date, clientId);
			saleSubCatList=reportService.saleSubCatSearch(branchId, date, clientId);
			saleCatList=reportService.saleCatSearch(branchId, date, clientId);
			
			
			List<CatListReportBean> categorysList=new ArrayList<CatListReportBean>();
	
			citerator = saleCatList.iterator();
		    
			while(citerator.hasNext()){
				
				saleReportBean cobj=(saleReportBean) citerator.next();
				CatListReportBean cbean=new CatListReportBean();
				cbean.setCatId(cobj.getcId());
				cbean.setCatDesc(cobj.getCatDesc());
				cbean.setcAmount(cobj.getcAmount());
				
				double catAmount=Double.parseDouble(cobj.getcAmount());
				 camount=camount+catAmount;
				
				if(saleSubCatList.size()!=0){
				 sbeanlist =new ArrayList<SubCatListReportBean>();
					siterator=saleSubCatList.iterator();
				
				while(siterator.hasNext()){
					saleReportBean sobj=(saleReportBean) siterator.next();
					String cId=cobj.getcId();
					String sId=sobj.getcId();
					String subcatId=sobj.getsId();
					
					if(sId.equals(cId)){
						
						SubCatListReportBean sbean=new SubCatListReportBean();
						sbean.setSubCatId(sobj.getsId());
						sbean.setSubCatDesc(sobj.getSubCatDesc());
						sbean.setSubCatAmount(sobj.getsAmount());
						
						if(saleProdList.size()!=0)
						{
						 pbeanlist=new ArrayList<ProdListReportBean>();
						piterator=saleProdList.iterator();
						
						while(piterator.hasNext()){
							saleReportBean pobj=(saleReportBean) piterator.next();
							String pId=pobj.getsId();
								if(pId.equals(subcatId)){
									
									ProdListReportBean pbean=new ProdListReportBean();
								pbean.setProdId(pobj.getpId());
								pbean.setProdDesc(pobj.getProdDesc());
								pbean.setpAmount(pobj.getpAmount());
								pbeanlist.add(pbean);
							}
						
						}
						}
					
						sbean.setPlist(pbeanlist);
						sbeanlist.add(sbean);
					}
					cbean.setSlist(sbeanlist);
				}
				categorysList.add(cbean);
				
				
			}
			}	
			 
			request.setAttribute("categoryList", categorysList);
			request.setAttribute("cAmount",camount);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "saleReportHome";
	}
	
	@RequestMapping(value = "/dailyExpenses", method = RequestMethod.GET)
	public String searchExpenses(
			@ModelAttribute("expenseCmd") ExpenseReportBean exBean, ModelMap map,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<ExpenseReportBean> expenseList=new ArrayList<ExpenseReportBean>();
		Iterator<ExpenseReportBean> itr=null;
		HttpSession session=request.getSession(false);
		String branchId=request.getParameter("branchId");
		String date=request.getParameter("sdate");
		String clientId=(String) session.getAttribute("clientId");
		double amount=0;
		try{
			expenseList=reportService.expenseDetailSearch(branchId, date, clientId);
			itr=expenseList.iterator();
			List<ExpenseReportBean> expenList=new ArrayList<ExpenseReportBean>();
			while(itr.hasNext()){
				ExpenseReportBean sitr=(ExpenseReportBean) itr.next();
				ExpenseReportBean sbean=new ExpenseReportBean();
				amount=amount+sitr.getAmount();
				sbean.setDescription(sitr.getDescription());
				sbean.setAmount(sitr.getAmount());
				expenList.add(sbean);
			}
			request.setAttribute("expenseList", expenList);
			request.setAttribute("expenAmount", amount);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "expenseReportHome";
	}
		
	@RequestMapping(value = "/dailyStock", method = RequestMethod.GET)
	public String searchStock(
			@ModelAttribute("stockCmd") StockReportBean stBean, ModelMap map,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<StockReportBean> stockList=new ArrayList<StockReportBean>();
		HttpSession session= request.getSession(false);
		String branchId=request.getParameter("branchId");
		String date=request.getParameter("sdate");
		String clientId=(String)session.getAttribute("clientId");
		List<StockReportBean> stList=new ArrayList<StockReportBean>();
		try{
			stockList=reportService.stockDetailSearch(branchId, date, clientId);
			Iterator<StockReportBean> itr=stockList.iterator();
			while(itr.hasNext()){
				StockReportBean sitr=(StockReportBean) itr.next();
				StockReportBean sbean=new StockReportBean();
				sbean.setProduct(sitr.getProduct());
				sbean.setQuantity(sitr.getQuantity());
				stList.add(sbean);
								
			}
			request.setAttribute("stockList", stList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "stockReportHome";
	}
	@RequestMapping(value = "/todayReport", method = RequestMethod.GET)
	public String searchTodayReport(
			@ModelAttribute("todayCmd") todayReportBean stBean, ModelMap map,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<saleReportBean> saleProdList = new ArrayList<saleReportBean>();
		List<saleReportBean> saleSubCatList = new ArrayList<saleReportBean>();
		List<saleReportBean> saleCatList = new ArrayList<saleReportBean>();
		HttpSession session = request.getSession(false);
		String branchId=request.getParameter("branchId");
		String clientId=(String) session.getAttribute("clientId");
		String date=request.getParameter("sdate");
		Iterator<saleReportBean> citerator=null;
		Iterator<saleReportBean> siterator=null;
		Iterator<saleReportBean> piterator=null;
		List<SubCatListReportBean> sbeanlist=null;
		List<ProdListReportBean> pbeanlist=null;
		List<ExpenseReportBean> expenseList=new ArrayList<ExpenseReportBean>();
		Iterator<ExpenseReportBean> itr=null;
		double amount=0;double camount=0;double finalvalue=0;
		
		try{
			saleProdList=reportService.saleProdSearch(branchId, date, clientId);
			saleSubCatList=reportService.saleSubCatSearch(branchId, date, clientId);
			saleCatList=reportService.saleCatSearch(branchId, date, clientId);
			
			
			List<CatListReportBean> categorysList=new ArrayList<CatListReportBean>();
	
			citerator = saleCatList.iterator();
		    
			while(citerator.hasNext()){
				
				saleReportBean cobj=(saleReportBean) citerator.next();
				CatListReportBean cbean=new CatListReportBean();
				cbean.setCatId(cobj.getcId());
				cbean.setCatDesc(cobj.getCatDesc());
				cbean.setcAmount(cobj.getcAmount());
				
				double catAmount=Double.parseDouble(cobj.getcAmount());
				 camount=camount+catAmount;
				
				if(saleSubCatList.size()!=0){
				 sbeanlist =new ArrayList<SubCatListReportBean>();
					siterator=saleSubCatList.iterator();
				
				while(siterator.hasNext()){
					saleReportBean sobj=(saleReportBean) siterator.next();
					String cId=cobj.getcId();
					String sId=sobj.getcId();
					String subcatId=sobj.getsId();
					
					if(sId.equals(cId)){
						
						SubCatListReportBean sbean=new SubCatListReportBean();
						sbean.setSubCatId(sobj.getsId());
						sbean.setSubCatDesc(sobj.getSubCatDesc());
						sbean.setSubCatAmount(sobj.getsAmount());
						
						if(saleProdList.size()!=0)
						{
						 pbeanlist=new ArrayList<ProdListReportBean>();
						piterator=saleProdList.iterator();
						
						while(piterator.hasNext()){
							saleReportBean pobj=(saleReportBean) piterator.next();
							String pId=pobj.getsId();
								if(pId.equals(subcatId)){
									
									ProdListReportBean pbean=new ProdListReportBean();
								pbean.setProdId(pobj.getpId());
								pbean.setProdDesc(pobj.getProdDesc());
								pbean.setpAmount(pobj.getpAmount());
								pbeanlist.add(pbean);
							}
						
						}
						}
					
						sbean.setPlist(pbeanlist);
						sbeanlist.add(sbean);
					}
					cbean.setSlist(sbeanlist);
				}
				categorysList.add(cbean);
						
			}
			}	
			request.setAttribute("categoryList", categorysList);
			request.setAttribute("cAmount",camount);
			
	/*----------------------------Start expeses data---------------------------------------*/
			expenseList=reportService.expenseDetailSearch(branchId, date, clientId);
			itr=expenseList.iterator();
			List<ExpenseReportBean> expenList=new ArrayList<ExpenseReportBean>();
			while(itr.hasNext()){
				ExpenseReportBean sitr=(ExpenseReportBean) itr.next();
				ExpenseReportBean sbean=new ExpenseReportBean();
				amount=amount+sitr.getAmount();
				sbean.setDescription(sitr.getDescription());
				sbean.setAmount(sitr.getAmount());
				expenList.add(sbean);
			}
			
			request.setAttribute("expenseList", expenList);
			request.setAttribute("expenAmount", amount);
			/*----------------------------End of Expeses data---------------------------------------*/			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		finalvalue=camount-amount;
		request.setAttribute("netValue",finalvalue);
		return "todayReportHome";
	}
	
	
	@ModelAttribute("branchDetails")
	public Map<String, String> populatLabelDetails() {
		Map<String, String> map = null;
		try {
			HttpSession session=hreq.getSession(false);
			String clientId=(String) session.getAttribute("clientId");
		
			if("admin".equals((String) session.getAttribute("admin"))){
			String sql = "SELECT b.branch_Id,b.branch_Name FROM branch b";
			map = populateService.populatePopUp(sql);
			}
			if("clientadmin".equals((String) session.getAttribute("admin"))||"sales".equals((String) session.getAttribute("admin"))||"supervisor".equals((String) session.getAttribute("admin"))){
				String sql = "SELECT b.branch_Id,b.branch_Name FROM branch b JOIN branch_client bc ON bc.`branch_Id`=b.`branch_Id` WHERE bc.`client_Id`='"+clientId+"'";
				map = populateService.populatePopUp(sql);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
