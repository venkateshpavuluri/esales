/**
 * 
 */
package com.mnt.esales.service;

import java.sql.Connection;
import java.sql.DriverManager;



/**
 * @author venkateshp
 *
 */
public class Util {

	static{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}
		
		public static Connection getConnection()
		{
			Connection connection=null;
			try
			{
				
		 connection=DriverManager.getConnection("jdbc:mysql://162.253.124.32/mysalesi_newSales_db","mysalesi", "localhost");
			//connection=DriverManager.getConnection("jdbc:sqlserver://204.93.178.157;databaseName=mydasecc_MobileTracking","mydasecc_sa", "mntmob1");
			//connection=DriverManager.getConnection("jdbc:sqlserver://192.168.1.103:1433;databaseName=MobileTracking","mnterpuser", "mnterpuser");
				}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return connection;
		}
		public static void main(String[] args) {
			System.out.println(Util.getConnection());
		}
}
