package com.jju.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import com.mysql.jdbc.Driver;


public class JdbcDemo {
	public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost:3306/mydb4";
	public static String DB_USER = "root";
	public static String DB_PW = "thanks";
	
	
	public static void main(String[] args) {
		JdbcDemo demo = new JdbcDemo();
		demo.JdbcToMySql2();
	}
	
	public void jdbcToMysql1() {
		Connection connection = null;
		Statement stam = null;
		ResultSet resultSet = null;
		try {
			//注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			//打开链接
			connection = DriverManager.getConnection(DB_URL, DB_USER,DB_PW);
			//执行查询
			stam = connection.createStatement();
			String sql = "select * from user";
			resultSet = stam.executeQuery(sql);
			
			//展开结果集
			while (resultSet.next()) {
				int id = resultSet.getInt("ID");
				String username = resultSet.getString("username");
				String GENDER = resultSet.getString("GENDER");
				Date BIRTHDAY = resultSet.getDate("BIRTHDAY");
				Date ENTRY_DATE = resultSet.getDate("ENTRY_DATE");
				String JOB = resultSet.getString("JOB");
				Float SALARY = resultSet.getFloat("SALARY");
				String RESUME = resultSet.getString("RESUME");
				
				System.out.println(id + username + GENDER + BIRTHDAY + ENTRY_DATE + JOB + SALARY + RESUME);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if ( resultSet!= null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				resultSet = null;
			}
			if ( stam!= null) {
				try {
					stam.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				stam = null;
			}
			if ( connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				connection = null;
			}
		}
	}
	public void JdbcToMySql2() {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet resultSet = null;
		
		try {
			//注册驱动方式二
//			DriverManager.registerDriver(new Driver());
			Class.forName("com.mysql.jdbc.Driver");
			
			//打开链接
			//方式一
//			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			/*//方式二
			Properties properties = new Properties();
			properties.put("user", "root");
			properties.put("password", "thanks");
			properties.put("characterEncoding", "utf8");
			properties.put("useUnicode", "true");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb4", properties);
			
			*/
			//方式三
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb4?user=root&password=thanks");
			//得到sql语句对象
			String sql = "select * from user";
			stm = connection.prepareStatement(sql);
			resultSet = stm.executeQuery();
			
			//遍历
			while (resultSet.next()) {
				int id = resultSet.getInt("ID");
				String username = resultSet.getString("username");
				String GENDER = resultSet.getString("GENDER");
				Date BIRTHDAY = resultSet.getDate("BIRTHDAY");
				Date ENTRY_DATE = resultSet.getDate("ENTRY_DATE");
				String JOB = resultSet.getString("JOB");
				Float SALARY = resultSet.getFloat("SALARY");
				String RESUME = resultSet.getString("RESUME");
				
				System.out.println(id + username + GENDER + BIRTHDAY + ENTRY_DATE + JOB + SALARY + RESUME);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if ( resultSet!= null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				resultSet = null;
			}
			if ( stm!= null) {
				try {
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				stm = null;
			}
			if ( connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				connection = null;
			}
		}
		
	}
	
}








