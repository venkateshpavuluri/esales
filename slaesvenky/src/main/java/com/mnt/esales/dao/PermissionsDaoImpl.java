/**
 * 
 */
package com.mnt.esales.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mnt.esales.bean.Permissions;
import com.mnt.esales.bean.StockAllocation;
import com.mnt.esales.daosupport.CustomConnection;
import com.mnt.esales.daosupport.MntDaoSupport;

/**
 * @author venkateshp
 *
 */
@Repository("permissionsDao")
public class PermissionsDaoImpl extends MntDaoSupport implements PermissionsDao {
	private Logger logger=Logger.getLogger(PermissionsDaoImpl.class);
   private String sql;
   @Autowired
   CustomConnection customConnection;
	@Override
	public List<Permissions> getBranches(String clientId) {
		// TODO Auto-generated method stub
		List<Permissions>  list=null;
		try
		{
			sql="select cb.branch_Client_Id as clientBranchId,cb.branch_Id as branchId,b.branch_Name as branchName from  branch_client cb join branch b on b.branch_Id=cb.branch_Id where cb.client_Id='"+clientId+"'";
			list=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<Permissions>(Permissions.class));
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return list;
	}
	@Override
	public List<Permissions> getUsers(String clientId) {
		// TODO Auto-generated method stub
		List<Permissions>  list=null;
		try
		{
			//sql="select r.role_Name as roleName,ub.client_user_Id as clientUserId,ub.user_Id as userId,u.user_Name as userName,u.firstname as firstName,u.lastname as lastName from  user_client ub join users u on u.user_Id=ub.user_Id  userroles ur JOIN users ul ON ul.user_Id=ur.user_Id JOIN roles r ON r.role_Id=ur.role_Id  where ub.client_Id='"+clientId+"' and u.status=1";
			sql="SELECT r.role_Name as roleName,ub.client_user_Id AS clientUserId,ub.user_Id AS userId,u.user_Name AS userName,u.firstname AS firstName,u.lastname AS lastName FROM  user_client ub JOIN users u ON u.user_Id=ub.user_Id JOIN userroles ur ON ur.`user_Id`=u.`user_Id`JOIN roles r ON r.role_Id=ur.`role_Id` WHERE ub.client_Id='"+clientId+"' AND u.status=1 ";
			list=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<Permissions>(Permissions.class));
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean savePermissions( final List<Permissions> permissions,boolean status) {
		boolean flag=false;
		String returnId=null;
		JdbcTemplate jdbcTemplate=null;
		try
		{
			if(status)
			{
				jdbcTemplate=getJdbcTemplate();
			}
			else
			{
				jdbcTemplate=customConnection.getTemplateConnection();
				
			}
			
			sql="insert into permissions(branch_Id,user_Id,client_Id) values(?,?,?)";
		int[] y=jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				 
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Permissions  permissionsh= permissions.get(i);
					ps.setString(1,permissionsh.getBranchId());
					ps.setString(2,permissionsh.getUserId());
					ps.setString(3,permissionsh.getClientId());
				
				}
				@Override
				public int getBatchSize() {
					return permissions.size();
				}
			  });
			flag=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			flag=false;
			logger.error(e.getMessage());
		}
		return flag;
	}
	@Override
	public List<Permissions> getPermissions(String userId,String branchId,String clientId) {
		// TODO Auto-generated method stub
	List<Permissions> permissions=null;
		try
		{
			sql="select p.user_Id as userId,p.branch_Id as branchId  from  permissions  p join users u on u.user_Id=p.user_Id where   u.status=1 and p.user_Id='"+userId+"' and p.branch_Id='"+branchId+"' and p.client_Id='"+clientId+"'";
			permissions=getJdbcTemplate().query(sql,new BeanPropertyRowMapper<Permissions>(Permissions.class));
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			}
		return permissions;
	}
	@Override
	public boolean deletePermissions(String clientId) {
		// TODO Auto-generated method stub
		boolean flag=true;
		try
		{
			sql="delete from permissions  where client_Id=?";
	int count=getJdbcTemplate().update(sql,new Object[]{clientId});
	logger.debug("delete count=="+count);
	if(count!=0)
	{
		flag=true;
	}
	else
	{
		flag=true;	
	}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
}
