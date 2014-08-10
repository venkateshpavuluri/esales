/**
 * 
 */
package com.mnt.esales.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mnt.esales.bean.BillInfo;
import com.mnt.esales.bean.BranchBean;
import com.mnt.esales.bean.CategoryBean;
import com.mnt.esales.bean.ClientInfoBean;
import com.mnt.esales.bean.DatabaseBackupFileUpload;
import com.mnt.esales.bean.ExpensesBean;
import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.bean.Stock;
import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.bean.StockBean;
import com.mnt.esales.bean.StockEntryUpdate;
import com.mnt.esales.bean.SubCategoryBean;
import com.mnt.esales.bean.UserBean;
import com.mnt.esales.dao.BillDao;
import com.mnt.esales.dao.BranchDao;
import com.mnt.esales.dao.CategoryDao;
import com.mnt.esales.dao.ExpensesDao;
import com.mnt.esales.dao.PermissionsDao;
import com.mnt.esales.dao.ProductDaoImpl;
import com.mnt.esales.dao.StockAllocationDao;
import com.mnt.esales.dao.StockDao;
import com.mnt.esales.dao.SubCategoryDao;
import com.mnt.esales.dao.UserDao;
import com.mnt.esales.dao.UserDaoImpl;
import com.mnt.esales.marshaling.Billing;
import com.mnt.esales.marshaling.Branch;
import com.mnt.esales.marshaling.Category;
import com.mnt.esales.marshaling.Client;
import com.mnt.esales.marshaling.Expenses;
import com.mnt.esales.marshaling.ExpensesInfo;
import com.mnt.esales.marshaling.Permissions;
import com.mnt.esales.marshaling.Product;
import com.mnt.esales.marshaling.Esales;
import com.mnt.esales.marshaling.SalesPersonData;
import com.mnt.esales.marshaling.StockAllocations;
import com.mnt.esales.marshaling.StockEntry;
import com.mnt.esales.marshaling.SubCategory;
import com.mnt.esales.marshaling.UserDetails;
import com.mnt.esales.service.DbBackupService;
import com.mnt.esales.service.LoginSericeImpl;
import com.mnt.esales.service.ProductService;

/**
 * @author venkateshp
 *
 */
@Controller
public class DatabaseBackupFileUpController {
    private static final int BUFFER_SIZE = 4096;
	private Logger logger=Logger.getLogger(DatabaseBackupFileUpController.class);
	@Autowired
	ServletContext servletContext;
	@Autowired
   DbBackupService dbBackupService;
	@Autowired
	BranchDao branchDao;
	@Autowired
	ProductDaoImpl productDaoImpl;
	
	@Autowired
	StockDao stockDao;
	@Autowired
	StockAllocationDao stockAllocationDao;
	@Autowired
PermissionsDao permissionsDao;
	@Autowired
	SubCategoryDao subCategoryDao;
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	BillDao billDao;
	@Autowired
	ExpensesDao expensesDao;
	@Autowired
	UserDao userDao;
	@RequestMapping(value = "/DbBackUphome", method = RequestMethod.GET)
	public String clientHome(@ModelAttribute("DbBackup") DatabaseBackupFileUpload backupFileUpload,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
return "dbBackUpView";
	}
	@RequestMapping(value = "/DbBackupFile", method = RequestMethod.POST)
	public String updateOrganization(@ModelAttribute("DbBackup") DatabaseBackupFileUpload backupFileUpload,
		Model model, HttpServletRequest request,HttpServletResponse response,@RequestParam("imageFile") MultipartFile file1) {
		response.setCharacterEncoding("UTF-8");
		logger.debug("fileupload iss===");
		String resumesFile=null;
		String originalPath=null;
		try {
			resumesFile = servletContext.getRealPath("DataBaseBackup");
			Date date = new Date();
			String dt = date.getYear() + date.getMonth() + date.getDay()
					+ date.getHours() + date.getMinutes() + date.getSeconds()
					+ "" + System.currentTimeMillis();
			originalPath = resumesFile+File.separator +dt+".xml";
			//File file=new File(originalPath);
			InputStream inputStream = file1.getInputStream();
			File file=new File(originalPath);
		/*	if(!file.exists())
			{
				file.createNewFile();
			}*/
			java.io.OutputStream outputStream = new FileOutputStream(file);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			inputStream.close();
			outputStream.close();
			logger.debug("original path iss=="+originalPath);
			request.setAttribute("succMessage","DataBase BackUp Uploaded Successfully");
			
			JAXBContext jc = JAXBContext.newInstance(Esales.class);
			Unmarshaller  unmarshallerq = jc.createUnmarshaller();
		Esales	 items = (Esales) unmarshallerq.unmarshal(file);
		
List<BranchBean> branchBeans=items.getBranchs();
List<ProductBean> productBeans=items.getProducts();
List<UserBean> userBeans=items.getUserDetails();
List<StockBean> stockBeans=items.getStockEntrys();
List<StockAllocation> stockAllocations=items.getStockAllocations();
List<com.mnt.esales.bean.Permissions> permissions=items.getPermissions();
List<SubCategoryBean> subCategoryBeans=items.getSubCategories();
List<BillInfo> billInfos=items.getBillings();
List<ExpensesBean> expensesBeans=items.getExpenses();
ClientInfoBean clientInfoBeans=items.getClient();
List<CategoryBean> categoryBeans=items.getCategories();

for(BranchBean bean:branchBeans)
{
	BranchBean branchBean=(BranchBean)bean;
	branchDao.saveBranch(branchBean,false);
}
for(ProductBean productBean:productBeans)
{
	ProductBean bean=(ProductBean)productBean;
	productDaoImpl.productSave(bean,false);
	
}
for(UserBean userBean:userBeans)
{
	UserBean bean=(UserBean)userBean;
	userDao.userSave(bean,false);
	
}
for(StockBean stockBean:stockBeans)
{
	StockBean bean=(StockBean)stockBean;
	stockDao.StockSave(bean, false);
}

	stockAllocationDao.saveStockAllocations(stockAllocations,false);
permissionsDao.savePermissions(permissions,false);

for(SubCategoryBean subCategoryBean:subCategoryBeans)
{
	SubCategoryBean scategoryBean=(SubCategoryBean)subCategoryBean;
	subCategoryDao.saveSubCategory(scategoryBean, false);
}
for(BillInfo billInfo:billInfos)
{
	BillInfo info=(BillInfo)billInfo;
//billDao.saveBill(info);
}
for(ExpensesBean expensesBean:expensesBeans)
{
	ExpensesBean bean=(ExpensesBean)expensesBean;
	expensesDao.ExpensesSave(bean, false);
}
for(CategoryBean categoryBean:categoryBeans)
{
	CategoryBean bean=(CategoryBean)categoryBean;
	categoryDao.saveCategory(bean, false);
	
}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "dbBackUpView";
	}
	@RequestMapping(value = "/getDbBackUp", method = RequestMethod.GET)
	public String getDbBackup(@ModelAttribute("DbBackup") DatabaseBackupFileUpload backupFileUpload,HttpServletResponse response,HttpSession session) {
		response.setCharacterEncoding("UTF-8");
		try
		{
			List<ProductBean> products=dbBackupService.getProducts();
			List<CategoryBean> categories=dbBackupService.getCategorys();
			List<SubCategoryBean> subCategories=dbBackupService.getSubCategorys();
			List<com.mnt.esales.bean.Permissions> permissions=dbBackupService.getPermissions();
			List<UserBean> userDetails=dbBackupService.getUserDetails();
			List<StockBean> stockEntries=dbBackupService.getStockEntry();
			List<StockAllocation> stockAllocations=dbBackupService.getStockAlocs();
			List<BranchBean> branchs=dbBackupService.getBranchesForClient();
			String role=(String)session.getAttribute("admin");
			if("sales".equals(role))
			{
	String branchId=dbBackupService.getBranches((String)session.getAttribute("userId"));
	BranchBean branch=new BranchBean();
	branch.setBranchId(branchId);
	List<BillInfo > billings=dbBackupService.getBillings(branchId);
	List<ExpensesBean > expenses=dbBackupService.getExpenses(branchId);
	List<StockBean> stocks=dbBackupService.getStocksForBranch(branchId);
	
	SalesPersonData salesPersonData=new SalesPersonData();
	salesPersonData.setBillings(billings);
	salesPersonData.setBranch(branch);
	salesPersonData.setExpenses(expenses);
	salesPersonData.setStocks(stocks);
	 JAXBContext jaxbContext = JAXBContext.newInstance(SalesPersonData.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    //Marshal the employees list in console
	    jaxbMarshaller.marshal(salesPersonData, System.out);
	    //Marshal the employees list in file
	    String file=servletContext.getRealPath("/DataBaseBackup/salespersondb.xml");
	    System.out.println("file iss=="+file);
	    jaxbMarshaller.marshal(salesPersonData, new File(file));
	    boolean flag=dbBackupService.updatesalesflags(branchId);
	    
	    
			}
			else
			{
				
				Esales productsmar=new Esales();
				ClientInfoBean client=new ClientInfoBean();
				client.setClientId((String)session.getAttribute("clientId"));
				client.setClientName((String)session.getAttribute("clientName"));
				productsmar.setClient(client);
				productsmar.setBranchs(branchs);
				productsmar.setUserDetails(userDetails);
				productsmar.setCategories(categories);
				productsmar.setSubCategories(subCategories);
				productsmar.setProducts(products);
				productsmar.setStockEntrys(stockEntries);
				productsmar.setStockAllocations(stockAllocations);
				productsmar.setPermissions(permissions);
				   JAXBContext jaxbContext = JAXBContext.newInstance(Esales.class);
				    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				    //Marshal the employees list in console
				    jaxbMarshaller.marshal(productsmar, System.out);
				    //Marshal the employees list in file
				    String file=servletContext.getRealPath("/DataBaseBackup/products.xml");
				    System.out.println("file iss=="+file);
				    jaxbMarshaller.marshal(productsmar, new File(file));
				    boolean flag=dbBackupService.updateAllDbBackupFlags();
				    
				    
			
			 
			        // construct the complete absolute path of the file
			        String fullPath = file;   
			        File downloadFile = new File(fullPath);
			        FileInputStream inputStream = new FileInputStream(downloadFile);
			        // get MIME type of the file
			        String mimeType = servletContext.getMimeType(fullPath);
			        if (mimeType == null) {
			            // set to binary type if MIME mapping not found
			            mimeType = "application/octet-stream";
			        }
			        // set content attributes for the response
			        response.setContentType(mimeType);
			        response.setContentLength((int) downloadFile.length());
			        // set headers for the response
			        String headerKey = "Content-Disposition";
			        String headerValue = String.format("attachment; filename=\"%s\"",
			                downloadFile.getName());
			        response.setHeader(headerKey, headerValue);
			        // get output stream of the response
			        OutputStream outStream = response.getOutputStream();
			        byte[] buffer = new byte[BUFFER_SIZE];
			        int bytesRead = -1;
			        // write bytes read from the input stream into the output stream
			        while ((bytesRead = inputStream.read(buffer)) != -1) {
			            outStream.write(buffer, 0, bytesRead);
			        }
			        inputStream.close();
			        outStream.close();
			}
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
return "dbBackUpView";
	}
	}

