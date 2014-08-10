package com.mnt.rowmappers;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbutill {
	static
	{
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
			Connection con=null;
			try
			{
			 con=DriverManager.getConnection("jdbc:mysql://192.168.1.103:3304/sales_db","venkat","venkat");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return con;
		}
		public static void main(String[] args) {
			System.out.println(dbutill.getConnection());
		}
	}


