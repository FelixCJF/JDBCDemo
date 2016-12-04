package com.jju.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbcp.PoolableCallableStatement;

public class MyDataSource implements DataSource {

	private static String driverClass;
    private static String url;
    private static String user;
    private static String password;
    
    //就是所谓的连接池，（创建链表数组，本身具有排序功能）
    private static LinkedList<Connection> pool = new LinkedList<Connection>();
    
    static{
    	try {
	    	InputStream inputStream = MyDataSource.class.getResourceAsStream("dbconfig.properties");
	    	Properties properties = new Properties();
			properties.load(inputStream);
			driverClass = properties.getProperty("classDriver");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			
			Class.forName(driverClass);
			
			//创建10个连接，并存入到连接池中
			for (int i = 0; i < 10; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				pool.add(connection);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
   //从池中获取数据库连接,因为可能有很多人同时访问，需要加同步锁
    public synchronized Connection getConnection(){
    	if(pool.size()>0){
            Connection conn = pool.remove();//取得同步锁，并把连接从连接池中删除
//            MyConnection1 myconn = new MyConnection1(conn, pool);
//            return myconn;
        }else{//如果池中没有连接，则抛出异常
            throw new RuntimeException("对不起！服务器忙");
        }
    	return null;
    }
    
    
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
