/**
 * 
 */
package com.mnt.esales.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

import com.mnt.esales.bean.Permissions;
import com.mnt.esales.bean.ProductBean;
import com.mnt.esales.service.PermissionsService;
import com.mnt.esales.service.PopulateService;

/**
 * @author venkateshp
 *
 */
@Controller
public class PermissionsController {
	@Autowired
	PopulateService populateService;
	private Logger logger=Logger.getLogger(PermissionsController.class);
	@Autowired
	PermissionsService permissionsService;
	@RequestMapping(value="/permissionsHome", method=RequestMethod.GET)
	public String permissionsHome(@ModelAttribute("permissionsCmd") Permissions permissions,HttpServletRequest request,Model model,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		HttpSession session=null;
		try
		{
			request.setAttribute("perActive", "active");
			session=request.getSession(false);
			session.setAttribute("tabName","Permissions");
			if(session!=null)
			{
				String clientId=String.valueOf(session.getAttribute("clientId"));
				permissions.setClientId(clientId);
				if(clientId!="")
				{
				List<Permissions> listOfBranches=null;		listOfBranches=permissionsService.getBranches(clientId);
				List<Permissions> listOfUsers=null;	listOfUsers=permissionsService.getUsers(clientId);
				 request.setAttribute("listOfBranches",listOfBranches);
						request.setAttribute("listOfUsers", listOfUsers);
				/*		List<Permissions> editPermissions=permissionsService.getPermissions(clientId);
						*/
						request.setAttribute("editPermissions",getEditPers(listOfUsers,listOfBranches,clientId));
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return "permissionsView";
		}
	@RequestMapping(value="/permissionsClient", method=RequestMethod.POST)
	public String permissionsWithClient(@ModelAttribute("permissionsCmd") Permissions permissions,HttpServletRequest request,Model model,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<Permissions> listOfBranches=null;
		List<Permissions> listOfUsers=null;
		try {
			request.setAttribute("perActive", "active");
			String clientId=permissions.getClientId();
			if(clientId!="")
			{
			HttpSession session=request.getSession(false);
			session.setAttribute("clientId",clientId);
			}
			logger.debug("client id iss=="+clientId);
			listOfBranches=permissionsService.getBranches(clientId);
			listOfUsers=permissionsService.getUsers(clientId);
			 request.setAttribute("listOfBranches",listOfBranches);
					request.setAttribute("listOfUsers", listOfUsers);
					
			/*		List<Permissions> editPermissions=permissionsService.getPermissions(clientId);
					request.setAttribute("editPermissions",editPermissions);*/
					request.setAttribute("editPermissions",getEditPers(listOfUsers,listOfBranches,clientId));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "permissionsView";
	}
	@RequestMapping(value="/permissionsSave", method=RequestMethod.POST)
	public String permissionsSave(@ModelAttribute("permissionsCmd") Permissions permissions,HttpServletRequest request,Model model,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		List<Permissions> listOfBranches=null;
		List<Permissions> listOfUsers=null;
		Iterator<Permissions> branchesIterator=null;
		Iterator<Permissions> listUserIterator=null;
		Permissions permissiionSave=null;
		List<Permissions> listOfPerSave=null;
		boolean falg=false;
		String sucMsg=null;
		boolean flagforDel=false;
		try
		{
			request.setAttribute("perActive", "active");
			HttpSession httpSession=request.getSession(false);
			String clientId=String.valueOf(httpSession.getAttribute("clientId"));
			listOfBranches=permissionsService.getBranches(clientId);
			listOfUsers=permissionsService.getUsers(clientId);
			if(listOfUsers!=null)
			{
				flagforDel=permissionsService.deletePermissions(clientId);
			listUserIterator=listOfUsers.iterator();
			listOfPerSave=new ArrayList<Permissions>();
			}
		/*	if(listOfBranches!=null);
			branchesIterator=listOfBranches.iterator();*/
			if(flagforDel)
			{
			while(listUserIterator.hasNext())
			{
				Permissions permisForUser=(Permissions)listUserIterator.next();
				logger.debug("first loop");
			
				for(Permissions perBranch:listOfBranches)
				{
					logger.debug("second loop");
					Permissions permisForBranch=(Permissions)perBranch;
					String checkValue=request.getParameter(permisForUser.getClientUserId()+permisForBranch.getBranchId());
				logger.debug("check value sis==="+checkValue);
					if("on".equals(checkValue))
				{
					permissiionSave=new Permissions();
					permissiionSave.setUserId(permisForUser.getUserId());
					permissiionSave.setBranchId(permisForBranch.getBranchId());
					permissiionSave.setClientId(clientId);
					listOfPerSave.add(permissiionSave);
				}
				}
			}
			 falg=permissionsService.savePermissions(listOfPerSave);
			}
				if(falg)
				{
				sucMsg="redirect:permissionsHome.htm?AddSus=success";
				}
				else
				{
					sucMsg="redirect:permissionsHome.htm?AddFail=fail";
				}
		}
		catch(Exception e)
		{
			sucMsg="redirect:permissionsHome.htm?AddFail=fail";
			logger.error(e.getMessage());
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
	public Map<Permissions,List<Permissions>> getEditPers(List<Permissions> users,List<Permissions> permissions,String clientId)
	{
		Map<Permissions, List<Permissions>> map=null;
		Permissions perm=null;
		 List<Permissions> listofPers=null;
		 Permissions perforusers=null;
		try
		{
			if(users!=null)
			{
				listofPers=new ArrayList<Permissions>();
				map=new LinkedHashMap<Permissions, List<Permissions>>();
				Iterator<Permissions> userit=users.iterator();
				while(userit.hasNext())
				{
					 perforusers=(Permissions)userit.next();
					Iterator<Permissions> iterator=permissions.iterator();
					while(iterator.hasNext())
					{
						Permissions perforbranches=(Permissions)iterator.next();
						List<Permissions> list=permissionsService.getPermissions(perforusers.getUserId(), perforbranches.getBranchId(),clientId);

						if(list.size()!=0)
						{
							logger.debug("list size iss==="+list.size());
							perm=new Permissions();
							perm.setBranchId(perforbranches.getBranchId()+"check");
							perm.setUserId(perforusers.getUserId());
							perm.setClientUserId(perforusers.getClientUserId());
							listofPers.add(perm);
						}
						else
						{
							perm=new Permissions();
							perm.setUserId(perforusers.getUserId());
							perm.setBranchId(perforbranches.getBranchId()+"uncheck");
							perm.setClientUserId(perforusers.getClientUserId());
							listofPers.add(perm);
						}
					}
					map.put(perforusers, listofPers);
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return map;
	}
}
