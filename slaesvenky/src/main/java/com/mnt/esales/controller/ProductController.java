/**
 * 
 */
package com.mnt.esales.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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

import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.service.PopulateService;
import com.mnt.esales.service.ProductServiceImpl;
/**
 * @author venkateshp
 * 
 */
@Controller
public class ProductController {
	private static Logger logger = Logger.getLogger(ProductController.class);
	@Autowired
	ProductServiceImpl productService;
	@Autowired
	PopulateService populateService;
	@Autowired
	HttpSession session;
	@RequestMapping(value = "/productHome", method = RequestMethod.GET)
	public String productHome(@ModelAttribute("productCmd") ProductBean productBean,HttpServletRequest request,HttpServletResponse response, Model model) {
		response.setCharacterEncoding("UTF-8");
		ObjectMapper mapper=null;
		try
		{
			request.setAttribute("prodActive","active");
		//productsSearch(request,session);
			HttpSession  session=request.getSession(false);
			session.setAttribute("tabName","Product");
			if(session.getAttribute("categoryId")!=null)
			{
			productBean.setCategoryId(String.valueOf(session.getAttribute("categoryId")));
			}
			if(session.getAttribute("subcategoryId")!=null)
			{
			productBean.setSubCategoryId(String.valueOf(session.getAttribute("subcategoryId")));
		List<ProductBean> productBeans=productService.getProductsWithId(String.valueOf(session.getAttribute("subcategoryId")));
			if(productBeans!=null)
			{
				 mapper=new ObjectMapper();
			String jsonData=mapper.writeValueAsString(productBeans);
			request.setAttribute("productList", jsonData);
			}
			}
			else
			{
				String admin=(String)session.getAttribute("admin");
				
				if("admin".equals(admin))
				{
					List<ProductBean> productBeansg=productService.productSearch("all");
					 mapper=new ObjectMapper();
					String jsonData=mapper.writeValueAsString(productBeansg);
					request.setAttribute("productList", jsonData);
				}
				else
				{
					productsSearch(request, session);
				}
				
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
			return "productView";
	}
	@RequestMapping(value = "/productAdd", method = RequestMethod.POST)
	public String productsSave(
			@ModelAttribute("productCmd") ProductBean productBean,
			HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		boolean flag = false;
		String succMsg = null;
		try {
			request.setAttribute("prodActive","active");
			//List<ProductBean> productBeans = productService.productSearch();
			flag = productService.productSave(productBean);
			session.setAttribute("categoryId",productBean.getCategoryId());
			session.setAttribute("subcategoryId",productBean.getSubCategoryId());
			//request.setAttribute("productList", productBeans);

			if (flag) {
				succMsg = "redirect:productHome.htm?AddSus=Success";
			} else {
				succMsg = "redirect:productHome.htm?AddFail=Success";
			}
		} catch (Exception e) {
			productsSearch(request,session);
			succMsg = "redirect:productHome.htm?AddFail=Success";
		}
		productsSearch(request,session);
		return succMsg;
	}
	@RequestMapping(value = "/searchProduct", method = RequestMethod.GET)
	public String searchProduct(
			@ModelAttribute("productCmd") ProductBean productBean, ModelMap map,HttpServletResponse response,HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<ProductBean> productBeans = null;
		try {
			request.setAttribute("prodActive","active");
			if (productBean.getProductNameSearch() == ""
					|| productBean.getProductNameSearch() == null) {
				//productBeans = productService.productSearch();
				productsSearch(request,session);
			} else {
				ObjectMapper mapper=new ObjectMapper();
				productBeans = productService.productSearchEWithId(
						productBean.getProductNameSearch(), "name");
				System.out.println("search product with name==="+productBeans.size());
				String jsonData=mapper.writeValueAsString(productBeans);
				request.setAttribute("productList",jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productView";
	}
	@RequestMapping(value = "/editProduct", method = RequestMethod.GET)
	public String editProduct(
			@ModelAttribute("productCmd") ProductBean productBean,
			ModelMap map, @RequestParam("productId") String productId,
			Model model,HttpServletResponse response,HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		List<ProductBean> productBeans = null;
		try {
			request.setAttribute("prodActive","active");
			productBeans = productService.productSearchEWithId(productId, "id");
			if (productBeans != null) {
				for (ProductBean product : productBeans) {
					ObjectMapper mapper=new ObjectMapper();
					String json=mapper.writeValueAsString(productBeans);
				
					if((String)session.getAttribute("subcategoryId")!=null)
					{
						getProductsSearch(session, request);
					}
					else
					{
						productsSearch(request, session);
						
					}
					model.addAttribute("productCmd", product);
				}
				map.addAttribute("productEdit", "Edit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productView";
	}
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	public String updateProduct(
			@ModelAttribute("productCmd") ProductBean productBean,
			HttpServletRequest request, ModelMap map, Model model,HttpServletResponse response,HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		boolean flag = false;
		ObjectMapper objectMapper=null;
		try {
			request.setAttribute("prodActive","active");
			System.out.println("update product==");
			flag = productService.productUpdate(productBean);
			if (flag) {
				ProductBean bean=new ProductBean();
				if(session.getAttribute("categoryId")!=null)
				bean.setCategoryId(session.getAttribute("categoryId").toString());
				if(session.getAttribute("subcategoryId")!=null)
				{
				bean.setSubCategoryId(session.getAttribute("subcategoryId").toString());
				getProductsSearch(session, request);
				}
				else
				{
					productsSearch(request, session);
				}
				map.addAttribute("UpdateSus", "updateSuccess");
				model.addAttribute("productCmd",bean);
				productBean.setProductName("");
			}
			
			
			else {
				map.addAttribute("UpdateFail", "updateFail");
			}
		} catch (Exception e) {
			//productsSearch(request);
			e.printStackTrace();
		}
		//productsSearch(request);
		return "productView";
	}
	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
	public String deleteProduct(
			@ModelAttribute("productCmd") ProductBean productBean,
			HttpServletRequest request, ModelMap map, HttpServletResponse response,
			@RequestParam("productId") String productId) {
		response.setCharacterEncoding("UTF-8");
		boolean flag = false;
		try {
			request.setAttribute("prodActive","active");
			flag = productService.productDelete(productId);
			if (flag) {
				request.setAttribute("DeleteSus", "deleteSuccess");
			} else {
				request.setAttribute("DeleteFail", "deleteFail");
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			productsSearch(request,session);
		}
		return "productView";
	}
/*	@RequestMapping(value = "/forCategorys", method = RequestMethod.POST)
	public @ResponseBody
	String getCategorys(@ModelAttribute("productCmd") ProductBean productBean,
			HttpServletRequest request, @RequestParam("deptId") String deptId,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		logger.info("product ajax home isss");
		// List<ProductBean> productBeans=productService.productSearch();
		// request.setAttribute("productList", productBeans);
		List<ProductBean> beans = null;
		ObjectMapper mapper=new ObjectMapper();
		StringWriter out = null;
		String json=null;
		try {
			beans = productService.getCategoryDetails(deptId);
			json=mapper.writeValueAsString(beans);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}*/
	@RequestMapping(value = "/forSubCategorys", method = RequestMethod.POST)
	public @ResponseBody String getSubCategorys(@ModelAttribute("productCmd") ProductBean productBean,
			HttpServletRequest request, @RequestParam("catId") String catId,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<ProductBean> beans = null;
	ObjectMapper  mapper=null;
		String json=null;
		try {
			session.setAttribute("categoryId",catId);
			beans = productService.getSubCategoryDetails(catId);
			if (beans != null) {
				mapper=new ObjectMapper();
				json=mapper.writeValueAsString(beans);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	@RequestMapping(value = "/checkProAddDuplicate", method = RequestMethod.POST)
	public @ResponseBody String duplicatCheck(@ModelAttribute("productCmd") ProductBean productBean,
			HttpServletRequest request, @RequestParam("productName") String productName,@RequestParam("subCatId") String subCatId,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String duplicateMsg="";
		try {
			int count=productService.duplicateCheck(subCatId ,null,productName);
			if(count!=0)
			{
				duplicateMsg="duplicate";
			}
			}
		catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return duplicateMsg;
	}
	@RequestMapping(value = "/checkProUpDuplicate", method = RequestMethod.POST)
	public @ResponseBody String duplicatCheckForUp(@ModelAttribute("productCmd") ProductBean productBean,
			HttpServletRequest request, @RequestParam("productName") String productName,@RequestParam("productId") String productId,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String duplicateMsg="";
		try {
			int count=productService.duplicateCheck(null ,productId,productName);
			if(count!=0)
			{
				duplicateMsg="duplicate";
			}
			}
		catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return duplicateMsg;
	}
	@RequestMapping(value = "/allProductsSearch", method = RequestMethod.POST)
	public @ResponseBody String getAllProducts(@ModelAttribute("productCmd") ProductBean productBean,
			HttpServletRequest request, @RequestParam("catId") String subcatId,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<ProductBean> beans = null;
	ObjectMapper  mapper=null;
		String json=null;
		try {
			
			if("all".equals(subcatId))
			{
				beans=productService.productSearch("all");
			}
			else
			{
				if(subcatId!="")
				{
				session.setAttribute("subcategoryId",subcatId);
				beans = productService.getProductsWithId(subcatId);
				}
			}
			if (beans != null) {
				mapper=new ObjectMapper();
				json=mapper.writeValueAsString(beans);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	@ModelAttribute("categorys")
	public Map<String, String> populatLabelDetails() {
		Map<String, String> map = null;
		String sql=null;
		try {
			// String
			// sql="select d.category_Id, from  dept_category d join dept_category dc on dc.category_Id=d.category_Id where d.dept_Id=";
			//String sql = "select d.category_Id,d.category_Desc from category d";
			String clientId=(String)session.getAttribute("clientId");
			String admin=(String)session.getAttribute("admin");
			if("admin".equals(admin))
			{
				 sql="SELECT cc.category_Id ,c.category_Desc FROM  client_category cc  JOIN category c ON c.category_Id=cc.category_Id ";
			}
			else
			{
				 sql="SELECT cc.category_Id ,c.category_Desc FROM  client_category cc  JOIN category c ON c.category_Id=cc.category_Id WHERE cc.client_Id='"+clientId+"'";
				
			}
			

			map = populateService.populatePopUp(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@ModelAttribute("subCategorys")
	public Map<String, String> getSubCategorys() {
		Map<String, String> map = null;
		try {
			String	categoryId=null;
			// String
			// sql="select d.category_Id, from  dept_category d join dept_category dc on dc.category_Id=d.category_Id where d.dept_Id=";
		Object categoryIds=session.getAttribute("categoryId");
		if(categoryIds!=null)
categoryId=categoryIds.toString();
		if(categoryId!="")
		{
			String sql ="SELECT  s.subcategory_Id,s.subCategory_Desc FROM subcat_category sc JOIN subcategory s ON s.subcategory_Id=sc.subcategory_Id WHERE sc.category_Id='"+categoryId+"'";  
			map = populateService.populatePopUp(sql);
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	private void productsSearch(HttpServletRequest request,HttpSession session) {
		List<ProductBean> productBeans = productService.productSearch(String.valueOf(session.getAttribute("clientId")));
		try
		{
		if(productBeans!=null)
		{
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(productBeans);
		request.setAttribute("productList",json);
		request.setAttribute("counts", "3");
		}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}
	private void getProductsSearch(HttpSession session,HttpServletRequest request)
	{
		try
		{
			if(session.getAttribute("subcategoryId")!=null)
			{
		List<ProductBean> productBeans=productService.getProductsWithId(String.valueOf(session.getAttribute("subcategoryId")));
		if(productBeans!=null)
		{
			ObjectMapper mapper=new ObjectMapper();
			String json=mapper.writeValueAsString(productBeans);
			request.setAttribute("productList",json);	
		}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}
	
}
