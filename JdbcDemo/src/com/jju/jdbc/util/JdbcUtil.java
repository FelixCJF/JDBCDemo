package com.jju.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class JdbcUtil {
	private static String DB_USER;
	private static String DB_PASSWORD;
	private static String DB_URL;
	private static String classDriver;
	
	static{
		try {
		//从配置文件中读取信息
			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties");
			Properties properties = new Properties();
			properties.load(in);
			DB_USER = properties.getProperty("user");
			DB_PASSWORD = properties.getProperty("password");
			DB_URL = properties.getProperty("classDriver");
			classDriver = properties.getProperty("url");
			
			Class.forName(classDriver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}
	
    public static void release(ResultSet rs,Statement stat,Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(stat!=null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stat = null;
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }
}
