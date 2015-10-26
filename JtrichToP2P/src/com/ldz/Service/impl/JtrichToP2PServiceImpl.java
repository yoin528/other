package com.ldz.Service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ldz.Service.JtrichToP2PService;
import com.ldz.utils.JDBCUtils;
import com.ldz.utils.FileUtils;
import com.ldz.utils.SQLUtils;

/**
 * @author LDZ   
 * @date 2015年9月24日 上午9:30:30 
 */
public class JtrichToP2PServiceImpl implements JtrichToP2PService {
	//新版p2p模板
	public static final Map<String,String> p2pTemplates = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("admin","INSERT INTO `admin`(id,create_date,modify_date,username,password,email,name,department,isAccountEnabled,isAccountLocked,isAccountExpired,isCredentialsExpired,loginFailureCount,lockedDate,loginDate,loginIp) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}','{11}','{12}','{13}','{14}','{15}');");

			
		}
	};
	//旧板jtrich查询字段，字段数据必须与新版p2p模板的字段对应一致，由于新旧版的字段有些去除、有些更名、有些拆成两张表，因此下面的字段应对应上新版字段才行，
	//可以使用关联表查出，这样才能将拆表、更名的数据迁移到新版数据库
	public static final Map<String,String> jtrichSqls = new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("admin","select id,create_date,modify_date,username,password,email,name,department,is_account_enabled,is_account_locked,is_account_expired,is_credentials_expired,login_failure_count,locked_date,login_date,login_ip from bit_admin ");
		}
	};
	
	@Override
	public void data2Sqls(String entityName,String sqlPath) {
		String template = p2pTemplates.get(entityName);
		List<String> sqls = new ArrayList<String>();
		List<Map<String,Object>> datas = JDBCUtils.getColumnsBySql(jtrichSqls.get(entityName));
		for(Map<String,Object> data : datas) {
			Object[] obj = data.values().toArray();
			String insertSql = SQLUtils.dataToSql(template, obj);
			sqls.add(insertSql);
		}
		FileUtils.writeToFile(sqls, sqlPath+"/"+entityName+".sql", true);
	}

	
	public static JtrichToP2PService jtrichToP2PService = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		jtrichToP2PService = new JtrichToP2PServiceImpl();
	}

	@Test
	public void testAdmin() {
		jtrichToP2PService.data2Sqls("admin","d:p2p");
	}
}
