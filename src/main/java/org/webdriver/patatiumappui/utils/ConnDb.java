package org.webdriver.patatiumappui.utils;

import java.sql.*;

public class ConnDb {
	private Log log=new Log(this.getClass());
	public  Connection getConn(String driver,String url,String username,String password )
	{
		Connection conn = null;
		try {
			Class.forName(driver); //classLoader,加载对应驱动
			conn = DriverManager.getConnection(url, username, password);
			log.info("连接数据库成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args)
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.168.0.227:3306/HISAPPOINTMENT";
		String username = "hk";
		String password = "hk@test";
		ConnDb connDb=new ConnDb();
		Connection connection=connDb.getConn(driver,url,username,password);
		String sql="SELECT * FROM Doctor";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();


			while (rs.next())
			{
				int row = rs.getRow();

				int h=rs.getInt("vmNum");
				//System.out.println(row);
				System.out.println(h);
				if (row==1) {
					break;

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//return null;
	}

}


