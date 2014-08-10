/**
 * 
 */
package com.mnt.esales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mnt.esales.bean.BillBean;
import com.mnt.esales.bean.BillDetails;
import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.service.BillSearchService;
import com.mnt.esales.service.PopulateService;

/**
 * @author madhav
 *
 */
@Controller
public class BillSearchController {

@Autowired
BillSearchService billSearchService;
@Autowired
PopulateService populateService;
	
	@RequestMapping(value = "/billSearchHome", method = RequestMethod.GET)
	public String billSearchHome(
			@ModelAttribute("billInfo") BillInfo billInfo,@ModelAttribute("getBillCmd") BillBean billBean,
			HttpServletResponse response, HttpServletRequest request,HttpSession session) {
		
		return "billSearchHome";
	}
	
	@RequestMapping(value="/searchForBill.htm",method=RequestMethod.POST)
    public @ResponseBody String addUser(@RequestParam String billId, HttpServletResponse response, HttpServletRequest request ){
       
		String json=null;
		System.out.println("In Ajax Call");
		System.out.println("Bill Id:"+billId);
		
		try {
			BillInfo billInfo=billSearchService.findBillInfobyBillNo(billId);
			response.setContentType("application/json");

			List<BillDetails> billDetails=billSearchService.getBillDetailsOfBillInfo(billInfo);
			
			billInfo.setBillDetails(billDetails);
			
			System.out.println(billInfo.getBillId()+".."+billInfo.getNetAmt()+"..."+billInfo.getReturnChange());
			
			List<BillDetails> billd=billInfo.getBillDetails();
			System.out.println(billd.size());
			for(BillDetails b:billd){
				System.out.println(".."+b.getProdDesc());
			}
			
			 Gson gson = new Gson();
		         json = gson.toJson(billInfo);
		        System.out.println("json = " + json);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
       
        return json;
    }
	
	@ModelAttribute("billNums")
	public Map<String, String> populatebranchess() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			

			String sql = "SELECT `bill_Info_Id`,`bill_Info_Id` FROM `bill_info`";
			map = populateService.populatePopUp(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
}
