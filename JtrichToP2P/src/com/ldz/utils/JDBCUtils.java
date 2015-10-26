package com.ldz.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

/**
 * @author LDZ
 * @date 2015年9月23日 上午10:12:28
 */
public class JDBCUtils {
	private static String jdbcURL = ResourceBundle.getBundle("config").getString("jdbcUrl"); 
	private static String jdbcDriver = ResourceBundle.getBundle("config").getString("jdbcDriver");
	private static String username = ResourceBundle.getBundle("config").getString("user");
	private static String password = ResourceBundle.getBundle("config").getString("password");
	static {
		DbUtils.loadDriver(jdbcDriver);
	}
	public static Connection getConn() {
		try {
			return DriverManager.getConnection(jdbcURL, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查出指定的sql列表
	 * @author LDZ   
	 * @date 2015年9月25日 上午10:18:09 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> getColumnsBySql(String sql, Object... params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rmd = null;
		int count = 1;// 有多少列
		try {
			conn = getConn();
			pst = conn.prepareStatement(sql);
			setParameters(pst, params);
			rs = pst.executeQuery();
			rmd = pst.getMetaData();
			count = rmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				// 列从1开始计数，和PreparedStatement设置参数一样
				for (int i = 1; i <= count; i++) {
					// 储存列名和列值
					map.put(rmd.getColumnLabel(i), rs.getObject(rmd.getColumnLabel(i)));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	private static void setParameters(PreparedStatement stmt, Object[] params)
			throws SQLException {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof Date) {
					stmt.setTimestamp(i + 1, new Timestamp(((Date) params[i])
							.getTime()));
				} else {
					stmt.setObject(i + 1, params[i]);
				}
			}
		}
	}
	/**
	 * 根据表名称取得列字段名称
	 * @author LDZ   
	 * @date 2015年9月25日 上午10:18:39 
	 * @param tabName 表名称
	 * @return
	 */
	public static List<String> getColumnName(String tabName) {
		List<String> columnName = new ArrayList<String>();
		String sql = " select * from "+tabName;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int count=rsmd.getColumnCount();
			for(int i=1;i<=count;i++) {
				columnName.add(rsmd.getColumnName(i));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return columnName;
	}
/*	public List getColumnBySql(String sql, Object... params) {
		QueryRunner qRunner = new QueryRunner();
		try {
			List datas = (List)qRunner.query(sql, params, new MapListHandler());
			return datas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	public static void main(String[] args) {
		String adminTemplate = "INSERT INTO `admin`(id,create_date,modify_date,username,password,email,name,department,isAccountEnabled,isAccountLocked,isAccountExpired,isCredentialsExpired,loginFailureCount) VALUES ('{0}', '{1}', '{2}', '{3}', '{4}', '{5}', '{6}', '{7}',{8},{9},{10},{11},'{12}');";
		
		List<Map<String,Object>> datas = JDBCUtils.getColumnsBySql("select id,create_date,modify_date,username,password,email,name,department,is_account_enabled,is_account_locked,is_account_expired,is_credentials_expired,login_failure_count from bit_admin ");
		for(Map<String,Object> data : datas) {
			Object[] obj = data.values().toArray();
			String insertSql = SQLUtils.dataToSql(adminTemplate, obj);
			System.out.println(insertSql);
//			Set<String> keys = data.keySet();
//			for(String key : keys) {
//				System.out.println(key+":"+data.get(key));
//			}
		}
		
		/*Connection conn = null;
		try {
			DbUtils.loadDriver(jdbcDriver);
			conn = DriverManager.getConnection(jdbcURL, username, password);
			QueryRunner qRunner = new QueryRunner();
			System.out.println("***Using MapListHandler***");
			// 以下部分代码采用Map存储方式，可以采用Bean的方式代替进行处理

			List lMap = (List) qRunner.query(conn,
					"select title,authors  from book", new MapListHandler());
			// 以下是处理代码，可以抽取出来
			for (int i = 0; i < lMap.size(); i++) {
				Map vals = (Map) lMap.get(i);
				System.out.println(vals.get("title") + "-------------" + vals.get("authors"));
			}

			
			 * List lBeans = (List)
			 * qRunner.query(conn," select title,authors from book ", new
			 * BeanListHandler(Book.class)); // 以下是处理代码，可以抽取出来
			 * System.out.println("title ------------- authors "); for (int i =
			 * 0; i < lBeans.size(); i++) { Book vals = (Book) lBeans.get(i);
			 * System.out.println(vals); }
			 
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}*/
	}
	
}
