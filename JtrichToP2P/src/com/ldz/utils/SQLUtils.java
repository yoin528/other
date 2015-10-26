package com.ldz.utils;

import java.util.List;

/**
 * @author LDZ   
 * @date 2015年9月23日 下午2:29:22 
 */
public class SQLUtils {
	/**
	 * 根据sql模板，将数据组拼成sql语句
	 * @author LDZ   
	 * @date 2015年9月23日 下午2:30:45 
	 * @param sqlTemplate sql插入模板，如下：
	 * 		INSERT INTO `t_room_record`(id,name, card, gender, birthday, address, zip, mobile, email, version) VALUES ('{0}', '{1}', '{2}', '{3}', '{4}', '{5}', '{6}', '{7}','{8}','{9}');
	 * @param columnValues 数据的字段列值，必需与sql模板中的字段一致
	 * @return
	 */
	public static String dataToSql(String sqlTemplate,Object...columnValues) {
		if (sqlTemplate == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder(sqlTemplate);
		String prefix = "{";
		String suffix = "}";
		for (int index = 0; index < columnValues.length; index++) {
			String value = columnValues[index]+"";
			value = value.replaceAll("\'", "\\\\'");
			if("null".equals(value)||"true".equals(value)||"false".equals(value)) {
				prefix = "'{";
				suffix = "}'";
			}else {
				prefix = "{";
				suffix = "}";
			}
			String key = new StringBuilder(prefix).append(index).append(suffix).toString();
			int i = -1;
			if ((i = builder.indexOf(key, i)) > -1) {
				int len = key.length();
				builder.replace(i, i + len, value);
			}
		}
		return builder.toString();
	}
	/**
	 * 根据表名称生成插入语句的模板 	如：
	 * INSERT INTO `t_room_record`(id,name, card, gender, birthday, address, zip, mobile, email, version) VALUES ('{0}', '{1}', '{2}', '{3}', '{4}', '{5}', '{6}', '{7}','{8}','{9}');
	 * @author LDZ   
	 * @date 2015年9月25日 上午10:19:04 
	 * @param tabName
	 * @return
	 */
	public static String tableToSqlTemplate(String tabName) {
		List<String> columns = JDBCUtils.getColumnName(tabName);
		StringBuffer sqlHead = new StringBuffer("INSERT INTO `").append(tabName).append("`(");
		StringBuffer sqlFoot = new StringBuffer(" VALUES (");
		for(int i=0;i<columns.size();i++) {
			sqlHead.append(columns.get(i)).append(",");
			sqlFoot.append("'{").append(i).append("}'").append(",");
		}
		sqlHead.deleteCharAt(sqlHead.length()-1);
		sqlFoot.deleteCharAt(sqlFoot.length()-1);
		sqlHead.append(")").append(sqlFoot.append(")")).append(";");
		return sqlHead.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(SQLUtils.tableToSqlTemplate("bit_member"));
	}
}
