/**
 * 
 */
package com.mnt.esales.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockEntryUpdate;
import com.mnt.esales.service.LoginSericeImpl;
import com.mnt.esales.service.PopulateService;
import com.mnt.esales.service.StockAllocationService;

/**
 * @author venkateshp
 *
 */
@Controller
public class StockAllocationController {
	private Logger logger=Logger.getLogger(StockAllocationController.class);
	@Autowired
	private StockAllocationService stockAllocationService;
	@Autowired
	private PopulateService populateService;
	@RequestMapping(value="/stockAllocAdd", method = RequestMethod.POST)
	public String stockAllockAdd(@ModelAttribute("stockAlloc") StockAllocation stockAllocation,HttpServletRequest request,HttpServletResponse response,Model model) throws IllegalAccessException, InvocationTargetException {
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("stockAllocActive", "active");
        HttpSession httpSession=request.getSession(false);
        String clientId=stockAllocation.getClientId();
        if(clientId!=null)
        {
        	httpSession.setAttribute("clientId",clientId);
        }
        stockAllocationDetails(clientId,request);
		return "stockAllocView";
	}
	@RequestMapping(value="/stockAllocHome", method = RequestMethod.GET)
	public String stockAllockHome(@ModelAttribute("stockAlloc") StockAllocation stockAllocation,HttpServletRequest request,HttpServletResponse response,Model model) throws IllegalAccessException, InvocationTargetException {
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("stockAllocActive", "active");
        HttpSession httpSession=request.getSession(false);
        httpSession.setAttribute("tabName","Stock Allocation");
        String clientId=String.valueOf(httpSession.getAttribute("clientId"));
        stockAllocationDetails(clientId,request);
        stockAllocation.setClientId(clientId);
		//model.addAttribute("stockAlloc", new StockAllocation());
		return "stockAllocView";
	}
	@RequestMapping(value="/stockAllocSave", method = RequestMethod.POST)
	public String stocAllocationSave(@ModelAttribute("stockAlloc") StockAllocation stockAllocation,HttpServletRequest request,HttpServletResponse response,Model model) {
		response.setCharacterEncoding("UTF-8");
HttpSession httpSession=request.getSession(false);
		List<StockAllocation>  stockAllocations=stockAllocationService.getProducts(httpSession.getAttribute("clientId").toString());
		List<StockAllocation> stockAllocationBrances=stockAllocationService.getBranchDetails(String.valueOf(httpSession.getAttribute("clientId")));
		/*request.setAttribute("stockForProducts",stockAllocations);
		request.setAttribute("stockForBranches",stockAllocationBrances);*/
		HttpSession session=request.getSession(false);
		List<StockAllocation> listofAllocations=null;
		List<StockEntryUpdate> entryUpdates=null;
		StockAllocation stkAllocations=null;
		String sucMsg=null;
		double mrps=0;
		try
		{
			request.setAttribute("stockAllocActive", "active");
		if(stockAllocations!=null)
		{
			boolean flags=stockAllocationService.deleteStockAllocc(String.valueOf(session.getAttribute("clientId")));
			listofAllocations=new ArrayList<StockAllocation>();
			entryUpdates=new ArrayList<StockEntryUpdate>();
		for(StockAllocation allocation:stockAllocations)
		{
			 stkAllocations=(StockAllocation)allocation;
			StockEntryUpdate entryUpdate=new StockEntryUpdate();
			float quan=0;
			for(StockAllocation allocationForBranches:stockAllocationBrances)
			{
				StockAllocation stkAllocationforBra=(StockAllocation)allocationForBranches;
				StockAllocation stocAllocSave=new StockAllocation();
				stocAllocSave.setBranchId(stkAllocationforBra.getBranchId());
				stocAllocSave.setProductId(stkAllocations.getProductId());
				String mrp=request.getParameter(stkAllocations.getStockId()+stkAllocationforBra.getBranchId()+"mrp");
				
				if(mrp!=null)
					mrps=Double.parseDouble(mrp);
				
				stocAllocSave.setMrp(mrps);
				stocAllocSave.setStockId(stkAllocations.getStockId());
				logger.debug("client id iss==="+stocAllocSave.getClientId());
				stocAllocSave.setClientId(String.valueOf(session.getAttribute("clientId")));
				String qty=stkAllocations.getStockId()+stkAllocationforBra.getBranchId()+"qty";
				String orgQty=request.getParameter(qty);
				logger.debug(" in save quantity iss==="+orgQty);
			if(orgQty!="")
			{
				stocAllocSave.setQuantity(orgQty);
			}
			else
			{
				stocAllocSave.setQuantity("0");
				orgQty="0";
			}
			quan+=Float.parseFloat(orgQty);
				listofAllocations.add(stocAllocSave);
			}
			entryUpdate.setStockId(stkAllocations.getStockId());
			entryUpdate.setQuantity(String.valueOf(Float.parseFloat(request.getParameter(stkAllocations.getStockId()))-quan));
			entryUpdates.add(entryUpdate);
		}
		boolean flag=stockAllocationService.saveStockAllocations(listofAllocations,entryUpdates);
			if(flag)
			{
				sucMsg="redirect:stockAllocHome.htm?AddSus=success";
			}
			else
			{
				sucMsg="redirect:stockAllocHome.htm?AddFail=fail";
			}
		logger.debug("list size isss==="+listofAllocations.size());
		}
		}
		catch(Exception e)
		{
			sucMsg="redirect:stockAllocHome.htm?AddFail=fail";
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return sucMsg;
	}
	@ModelAttribute("clientDetails")
	public Map<String, String> populatLabelDetails() {
		Map<String, String> map = null;
		try {
			// String
			// sql="select d.category_Id, from  dept_category d join dept_category dc on dc.category_Id=d.category_Id where d.dept_Id=";
			String sql = "select d.client_Id,d.client_Name from clientinfo d";
			map = populateService.populatePopUp(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public void stockAllocationDetails(String clientId,HttpServletRequest request)
	{
		List<StockAllocation>  stockAllocations=stockAllocationService.getProducts(clientId);
		List<StockAllocation> stockAllocationBrances=stockAllocationService.getBranchDetails(clientId);
		request.setAttribute("stockForProducts",stockAllocations);
		request.setAttribute("stockForBranches",stockAllocationBrances);
		StockAllocation stockForPro=null;
		StockAllocation totalStocks=null;
		Map<StockAllocation,List<StockAllocation>> map=null;
List<StockAllocation> stAllcs=null;
try
{
		if(stockAllocationBrances!=null)
		{
			request.setAttribute("branchSize",stockAllocationBrances.size());
			map=new LinkedHashMap<StockAllocation, List<StockAllocation>>();
			stAllcs=new ArrayList<StockAllocation>();
		for(StockAllocation allocation:stockAllocations)
		{
			stockForPro=(StockAllocation)allocation;
			float totalQuan=0;
			int count=0;
			for(StockAllocation sallocations:stockAllocationBrances)
			{
				count++;
				StockAllocation stForBrans=(StockAllocation)sallocations;
				StockAllocation quantity=null;
				logger.debug(stForBrans.getBranchId()+""+stockForPro.getStockId()+""+stockForPro.getProductId());
				 quantity=stockAllocationService.getQuantity(stForBrans.getBranchId(),stockForPro.getStockId(),stockForPro.getProductId());
				//String quantity=stockAllocationService.getQuantity("B0013","S0001","P0001");
				logger.debug("quantity test iss =="+quantity);
				if(quantity!=null)
			{
					if(Float.parseFloat(quantity.getQuantity())==0)
					{
						stForBrans.setQuantity(""); 
					}
					else
					{
				stForBrans.setQuantity(quantity.getQuantity());
					}
				stForBrans.setProductId(stockForPro.getProductId());
				stForBrans.setStockId(stockForPro.getStockId());
				stForBrans.setMrp(quantity.getMrp());
				totalQuan+=Float.parseFloat(quantity.getQuantity());
				totalStocks=new StockAllocation();
				BeanUtils .copyProperties(totalStocks, stForBrans);
				totalStocks.setBransCount(count);
			}
				else
				{
					System.out.println("else ");
					stForBrans.setQuantity("");
					stForBrans.setProductId(stockForPro.getProductId());
					stForBrans.setStockId(stockForPro.getStockId());
					stForBrans.setMrp(0);
					totalStocks=new StockAllocation();
					BeanUtils.copyProperties(totalStocks, stForBrans);
					totalStocks.setBransCount(count);
				}
			stAllcs.add(totalStocks);
			}
		float ttQty=Float.parseFloat(stockForPro.getQuantity());
			ttQty=ttQty+totalQuan;
			logger.debug("total quantity iss=="+ttQty+"==total list size iss=="+stAllcs.size());
			stockForPro.setTotInStock(String.valueOf(ttQty));
			stockForPro.setQuantity(stockForPro.getQuantity());
			map.put(stockForPro, stAllcs);
		}
		request.setAttribute("stocAllocMap",map);
		logger.debug("map size isss==="+map.size());
		}
}
catch(Exception e)
{
	logger.error(e.getMessage());
}
	}
}
