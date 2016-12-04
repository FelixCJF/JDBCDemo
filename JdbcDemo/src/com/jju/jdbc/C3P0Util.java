package com.jju.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Util {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource("/c3p0-config.xml");
	
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	 public static void realease(ResultSet rs,Statement stmt,Connection conn){
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            rs = null;
	        }
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            stmt = null;
	        }
	        if (conn != null) {//把链接不要关闭而是要还回池中
	            try {
	                conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            conn = null;
	        }
	    }

}
