/**
 * 
 */
package com.mnt.esales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mnt.esales.bean.BillBean;
import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.dao.CustomIdDao;
import com.mnt.esales.service.BillService;
import com.mnt.esales.service.PopulateService;

/**
 * @author madhav
 *
 */
@Controller
public class BillController {
	@Autowired
	BillService billService;
	@Autowired
	CustomIdDao custIdDao;
	 @Autowired
	 PopulateService populateService;
	 @Autowired
		HttpServletRequest hreq;
	 List<BillDetails> billDetails=null;
	 boolean flag;
	
	@RequestMapping(value = "/billHome", method = RequestMethod.GET)
	public ModelAndView deptHome(
			@ModelAttribute("billInfo") BillInfo billInfo,@ModelAttribute("getBillCmd") BillBean billBean,
			HttpServletResponse response, HttpServletRequest request,HttpSession session) {
		session.setAttribute("tabName","Bill");
request.setAttribute("billActive","active");
		return new ModelAndView("billHome", "getBillCmd", new BillBean());
	}
	
	@RequestMapping(value = "/getBillDetails", method = RequestMethod.POST)
	public String getBillDetails(
			@ModelAttribute("billInfo") BillInfo billInfo,@ModelAttribute("getBillCmd") BillBean billBean,
			Model map, HttpServletRequest request,HttpServletResponse response) {
		
		//Total Column properties
		double tquantity,tmrp,trate,tdiscount,tvat,tamount;
	
		
		request.setAttribute("billActive","active");
		boolean checkProdId=false;
		List<BillDetails> bill = null;
		List<BillDetails> finalBill=new ArrayList<BillDetails>();
		List<BillDetails> tempBean=null;
		List<BillDetails> tempBillDetails=new ArrayList<BillDetails>();
		boolean flag=false;
		//Get the BranchId
		String branchId=billBean.getBranchId();
		//get the product code
		String productCode=billBean.getProductId();
		
		//get the quantity
		try {
			double userRate=billBean.getCost();
			bill = billBean.getBillDetails();
			
			//check bill grid is empty or not
			if(!bill.isEmpty()){
					
				
					//already bill grid is filled with data
					//checkfor enterd product id is available or not
					checkProdId=billService.checkForProductId(productCode, branchId);
					if(checkProdId==true){
						float quantity=billBean.getQuantity();
						
						//if productId is available
						 for (int i=0; i < bill.size(); i++) {
							 BillDetails c=bill.get(i);
								//finalBill.add(c);
							 if(productCode.equals(c.getProdId())){
									flag=true;
									//if prodId is available then check for quantity
								float f=c.getQuantity()+billBean.getQuantity();
								int availableQuantity=billService.checkForQuantity(branchId, productCode, Math.round(f));
									if(availableQuantity >=Math.round(f)){
										//if quantity is available
										if(c.getRate()<=billBean.getCost()){
										c.setRate(c.getRate());
										double amount=((f*c.getRate())+c.getVat())-c.getDiscount();
										c.setAmount(amount);
										}else{
											c.setRate(billBean.getCost());
											//double amount=((f*billBean.getCost())+c.getVat())-c.getDiscount();
											double  dTotAmt = quantity * userRate;
											double dVatAmt=(double)( c.getVat() * dTotAmt)/100;
											double dDisAmt= (double)( c.getDiscount() * dTotAmt)/100;
											double  dNetAmt =  dTotAmt + dVatAmt - dDisAmt;
											c.setAmount(dNetAmt);
										}
										c.setQuantity(f);
										
									}else{
										c.setQuantity(c.getQuantity());
										//if quantity is not available
										request.setAttribute("LessQuantity", "quantityError");
									}
								//set the remaining values to grid if quantity not available
								//s.setQuantity(c.getQuantity());
									finalBill.add(i,c);
								//set stock to the finallBill	
							}else{
								//if productId is not available new for grid
								finalBill.add(i,c);
							}
						}//billGrid completed
						 if(flag!=true){
								flag=false;
								//check for quantity eneterd available or not
								int availableQuantity=billService.checkForQuantity(branchId, productCode, Math.round(quantity));
									if(availableQuantity >=quantity){
											//if Quantity is available
											billDetails = billService.getBillingDetails(productCode,branchId, quantity);
											for(BillDetails c:billDetails){
												//double amount=((quantity*userRate)+c.getVat())-c.getDiscount();
												double  dTotAmt = quantity * userRate;
												double dVatAmt=(double)( c.getVat() * dTotAmt)/100;
												double dDisAmt= (double)( c.getDiscount() * dTotAmt)/100;
												double  dNetAmt =  dTotAmt + dVatAmt - dDisAmt;
												c.setAmount(dNetAmt);
												c.setRate(userRate);
												c.setQuantity(quantity);
												tempBillDetails.add(c);
											}
											finalBill.addAll(tempBillDetails);
											//request.setAttribute("billDetails", finalBill);
									}else{
											//if Quantity is not available
											request.setAttribute("LessQuantity", "quantityError");
									}
						 }
						request.setAttribute("billDetails", finalBill);
					}else{
						//if productId is not avilable
						tempBean=billBean.getBillDetails();
						request.setAttribute("billDetails", tempBean);
						request.setAttribute("emptyProduct", checkProdId);	
						
					}
				
			}else{
				
				//if billGrid is starting is empty
				//Check if Product is present or not in branch
				
				boolean checkProdId1=billService.checkForProductId(productCode, branchId);
				//check whether the product code is available or not 
				if(checkProdId1==true){
					float quantity1=billBean.getQuantity();
					
						//if available
						//check for quantity eneterd available or not
						int availableQuantity=billService.checkForQuantity(branchId, productCode, Math.round(quantity1));
					
						if(availableQuantity >=quantity1){
							//if Quantity is available
							billDetails = billService.getBillingDetails(productCode,branchId, quantity1);
							for(BillDetails c:billDetails){
								//double amount=((quantity1*userRate)+c.getVat())-c.getDiscount();
								double  dTotAmt = quantity1 * userRate;
								double dVatAmt=(double)( c.getVat() * dTotAmt)/100;
								double dDisAmt= (double)( c.getDiscount() * dTotAmt)/100;
								double  dNetAmt =  dTotAmt + dVatAmt - dDisAmt;
								c.setAmount(dNetAmt);
								c.setRate(userRate);
								c.setQuantity(quantity1);
								tempBillDetails.add(c);
								
							}
							finalBill.addAll(tempBillDetails);
							request.setAttribute("billDetails", finalBill);
						}else{
							//if Quantity is not available
							request.setAttribute("LessQuantity", "quantityError");
							request.setAttribute("availableQuantity", availableQuantity);
						}
					
				}else{
					//if product code is not available
					request.setAttribute("emptyProduct", checkProdId);	
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	
		map.addAttribute(billBean);
		HttpSession session = request.getSession();
		session.setAttribute("billGridForEdit", finalBill);
		billBean.setCost(null);
		billBean.setProductId(null);
		billBean.setQuantity(null);
	
		return "billHome";
	}
	
	@RequestMapping(value = "/saveBill", method = RequestMethod.POST)
	public String saveBillDetails(
			@ModelAttribute("billInfo") BillBean billBean,
			ModelMap map, HttpServletRequest request,HttpServletResponse response) {
		
		try {
			request.setAttribute("billActive","active");
			String billId=billBean.getBillId();
			String userId=billBean.getUserId();
			flag=billService.saveBill(billBean);
		
			if(flag==true){
				return "redirect:billHome.htm?AddSus=" + "Success" + "";

			} else {
				return "redirect:billHome.htm?AddFail=" + "fail" + "";
			}
		}catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return "redirect:billHome.htm?AddFail=" + "fail" + "";
			}
	}

	 @RequestMapping(value = "/editBill", method = RequestMethod.GET)
	    public String editBill(@ModelAttribute("billEditCmd") BillDetails billRow,
		    Model model, HttpServletRequest request,HttpServletResponse response) {
		 List<BillDetails> bill = null;
		
		
		 String useMe=request.getParameter("prodId");
		/* request.setAttribute("method", "Edit");
		 request.setAttribute("id", useMe);*/
		
		 
		
	try {
		request.setAttribute("billActive","active");
		HttpSession session = request.getSession();
			bill=(List<BillDetails>) session.getAttribute("billGridForEdit");
			//System.out.println(bill.size());
			 for (int i=0; i < bill.size(); i++) {
				 
				 BillDetails c=bill.get(i);
				
				 if(useMe.equals(c.getProdId())){
					
					 billRow.setProdId(c.getProdId());
					 billRow.setProdDesc(c.getProdDesc());
					 billRow.setQuantity(c.getQuantity());
					 billRow.setVat(c.getVat());
					 billRow.setRate(c.getRate());
					 billRow.setDiscount(c.getDiscount());
					 request.setAttribute("billEdit",billRow);
					 //request.setAttribute("billEdit",billBean);
				 }
			 }
			 
			  
		} catch (Exception e) {
		    e.printStackTrace();
		}
			
		return "billHome";

	    }
	@RequestMapping(value = "/billUpdate", method = RequestMethod.POST)
	    public String billUpdate(@ModelAttribute("billEditCmd") BillDetails billRow,@ModelAttribute("getBillCmd") BillBean billBean, Model map,
		    HttpServletRequest request,HttpServletResponse response) {
		try{
			request.setAttribute("billActive","active");
		 	HttpSession session = request.getSession();	
			List<BillDetails> testData = (List<BillDetails>) session.getAttribute("billGridForEdit");	
		    String useMe=billRow.getProdId();
		
		    for(int i=0; i<testData.size();i++){
		    	BillDetails c=testData.get(i);
		    	String id=c.getProdId();
		    	if(useMe.equals(id)){
		    		 c.setProdId(billRow.getProdId());
					 c.setProdDesc(billRow.getProdDesc());
					 c.setQuantity(billRow.getQuantity());
					 c.setVat(billRow.getVat());
					 c.setRate(billRow.getRate());
					 c.setDiscount(billRow.getDiscount());
		    		double amount=((billRow.getQuantity()*billRow.getRate())+billRow.getVat())-billRow.getDiscount();
		    		c.setAmount(amount);
		    		testData.set(i, c);
		    	
		    	}
		    }
		    request.setAttribute("billDetails", testData);
		  
		  
		}catch(Exception e){
			return "billHome";
			
		}
		  return "billHome";
	 }
	
	@RequestMapping(value = "/deleteBill", method = RequestMethod.GET)
    public String deleteCat(@ModelAttribute("getBillCmd") BillBean billBean,
    		HttpServletRequest request,HttpServletResponse response) {
		try{
			request.setAttribute("billActive","active");
		HttpSession session = request.getSession();	
		List<BillDetails> testData = (List<BillDetails>) session.getAttribute("billGridForEdit");
		 String useMe=request.getParameter("prodId");
		
		 for(int i=0; i<testData.size(); i++){
			 BillDetails c=testData.get(i);
		    	String id=c.getProdId();
		    	if(useMe.equals(id)){
		    		testData.remove(i);
		    		
		    	}	
		 }
		 request.setAttribute("billDetails", testData);
		}catch(Exception e){
			return "billHome";
		}
		return "billHome";
    }
	
	
	@ModelAttribute("branch")
	public Map<String, String> populatebranches() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			HttpSession session=hreq.getSession(false);
			String clientId=(String)session.getAttribute("clientId");
			String userid=(String)session.getAttribute("userId");
			String sql = "SELECT b.branch_Id,b.branch_Name FROM branch b JOIN permissions p ON b.`branch_Id`=p.`branch_Id` WHERE b.client_Id='"+clientId+"' AND p.user_Id='"+userid+"'";
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
			
			HttpSession session=hreq.getSession(false);
			String clientId=(String)session.getAttribute("clientId");
		
			String sql = "SELECT b.branch_Id,b.branch_Name FROM branch b WHERE b.client_Id='"+clientId+"'";
			map = populateService.populatePopUp(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
}
