package com.ldz.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LDZ   
 * @date 2015年9月23日 下午4:09:50 
 */
public class FileUtils {
	/**
	 * 将sql语句的列表写入到指定的文件中
	 * @author LDZ   
	 * @date 2015年9月25日 上午10:16:14 
	 * @param sqls sql语句列表
	 * @param filePath 文件路径
	 * @param append 是否追加到该文件中
	 */
	public static void writeToFile(List<String> sqls,String filePath,boolean append) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filePath, append));
			int lineNum = 1;
			int indexName = 1;
			for(String sql : sqls) {
				bw.write(sql);
				bw.newLine();
				if(lineNum%500==0) {
					bw.flush();
				}
				if(lineNum%5000==0) {
					bw.close();
					if(indexName>1) {
						filePath = filePath.replace((indexName-1)+".sql", indexName+".sql");
					}else {
						filePath = filePath.replace(".sql", indexName+".sql");
					}
					bw = new BufferedWriter(new FileWriter(filePath, append));
					indexName++;
				}
				lineNum++;
			}
			bw.close();
			bw = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 测试生成的新版插入语句模板
	 * @author LDZ   
	 * @date 2015年9月25日 上午10:17:25
	 */
	public static void writeP2PInsertSqlToFile() {
		List<String> inserSqls = new ArrayList<String>();
		inserSqls.add(SQLUtils.tableToSqlTemplate("admin"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("admin_role"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("article"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("article_category"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("bank_account"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("bond"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("bond_repayment_plan"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("borrowing"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("borrowing_repayment_plan"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("comment"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("companysize"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("financing_party"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("income"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("industry"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("member"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("member_account"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("message_record"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("product_type"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("repayment"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("role"));
		inserSqls.add(SQLUtils.tableToSqlTemplate("yee_order"));
		
		FileUtils.writeToFile(inserSqls, "d:/p2p.sql", true);
		
	}
	
	
	public static void main(String[] args) {
		FileUtils.writeP2PInsertSqlToFile();
		/*List<String> sqls = new ArrayList<String>();
		for(int i=0;i<15227;i++) {
			sqls.add("INSERT INTO `admin`(id,create_date,modify_date,username,password,email,name,department,isAccountEnabled,isAccountLocked,isAccountExpired,isCredentialsExpired,loginFailureCount) VALUES ('"+i+"', '2013-04-09 00:00:00.0', '2015-09-21 11:16:36.0', 'admin"+i+"', '64652ee28103d50405bb1a9a2bd0931b', '328471935@qq.com', 'ADMIN', '管理"+i+"',true,false,false,false,'0');");
		}
		FileUtils.writeToFile(sqls, "d:/admin.sql", true);*/
	}
}
