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
			put("role","INSERT INTO `role`(id,create_date,modify_date,authority_list_store,description,is_system,name) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}');");
			put("adminRole","INSERT INTO `admin_role`(admin_id,role_id) VALUES ('{0}','{1}');");
			put("article","INSERT INTO `article`(id,create_date,modify_date,author,content,down,image_path,link_url,meta_description,meta_keywords,min_content,sub_title,title,up,article_category_id) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}','{11}','{12}','{13}','{14}');");
			put("articleCategory","INSERT INTO `article_category`(id,create_date,modify_date,meta_description,meta_keywords,name,order_list,parent_id,grade,path) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}');");
			put("member","INSERT INTO `member`(id,create_date,modify_date,swift_number,birthday,email,email_auth,gender,graduate_school,id_card,last_login,login_ip,marriage,member_type,mobile,mobile_auth,password,psypassword,true_name,user_status,username,username_md5,bankAccount_id,url) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}','{11}','{12}','{13}','{14}','{15}','{16}','{17}','{18}','{19}','{20}','{21}','{22}','{23}');");
			
			
			put("bankAccount","INSERT INTO `bank_account`(id,create_date,modify_date,account_name,account_number,account_type,bank_address,bank_name,card_bind,member_id,bank_code) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}');");
			put("bond","INSERT INTO `bond`(id,create_date,modify_date,apr,backlog_amount,bond_title,borrowing_id,capital_on_call,each_value,earned_amount,flag,guarantee_mode,invest_amount,p_bid_no,swift_number,the_term,repayment_id,recover_amount,repayment_day,settlement_date,str_left_time,the_total_repayment,transfer_time,member_id) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}','{11}','{12}','{13}','{14}','{15}','{16}','{17}','{18}','{19}','{20}','{21}','{22}','{23}');");
			put("bondRepaymentPlan","INSERT INTO `bond_repayment_plan`(id,create_date,modify_date,flag,periods,real_repayment_day,shall_repayment_amount,shall_repayment_capital,shall_repayment_day,shall_repayment_interest,bond_id) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}');");
			put("borrowing","INSERT INTO `borrowing`(id,create_date,modify_date,apr,borrow_use,borrowing_title,complete_time,content,expire_time,financing_type,flag,least_money,money,overdue_amount,p_bid_no,swift_number,pass_time,pay_off_date,progress,repayment_no,security_mode,the_term,borrowed_amount,the_total_interest,the_total_repayment,upper_limit,financing_party_id,member_id,product_type_id,repayment_day,repayment_id,wind_information,rest_periods) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}','{11}','{12}','{13}','{14}','{15}','{16}','{17}','{18}','{19}','{20}','{21}','{22}','{23}','{24}','{25}','{26}','{27}','{28}','{29}','{30}','{31}','{32}');");
			put("borrowingRepaymentPlan","INSERT INTO `borrowing_repayment_plan`(id,create_date,modify_date,flag,periods,real_repayment_day,shall_repayment_amount,shall_repayment_capital,shall_repayment_day,shall_repayment_interest,borrowing_id) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}');");
			put("comment","INSERT INTO `comment`(id,create_date,comment_desc) VALUES ('{0}','{1}','{2}');");
			put("companysize","INSERT INTO `companysize`(id,create_date,modify_date,name) VALUES ('{0}','{1}','{2}','{3}');");
			put("financingParty","INSERT INTO `financing_party`(id,create_date,modify_date,ent_agent,ent_business_no,ent_enterprise_name,ent_introduction,ent_legal_rep,ent_reg_address,ent_telephone,per_admission,per_age,per_car,per_car_loan,per_current_work_time,per_education,per_gender,per_house,per_introduction,per_location,per_marriage,per_mortgage,per_name,per_position,per_work_city,income_id,industry_id,companysize_id) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}','{11}','{12}','{13}','{14}','{15}','{16}','{17}','{18}','{19}','{20}','{21}','{22}','{23}','{24}','{25}','{26}','{27}');");
			put("income","INSERT INTO `income`(id,create_date,modify_date,name) VALUES ('{0}','{1}','{2}','{3}');");
			put("industry","INSERT INTO `industry`(id,create_date,modify_date,name) VALUES ('{0}','{1}','{2}','{3}');");
			put("memberAccount","INSERT INTO `member_account`(id,create_date,modify_date,total_money,balance,frozen_money,member_id) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}');");
			put("messageRecord","INSERT INTO `message_record`(id,create_date,modify_date,content,moblie) VALUES ('{0}','{1}','{2}','{3}','{4}');");
			put("productType","INSERT INTO `product_type`(id,create_date,modify_date,product_name) VALUES ('{0}','{1}','{2}','{3}');");
			put("repayment","INSERT INTO `repayment`(id,create_date,modify_date,algorithm_information,flag,name) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}');");
			put("yeeOrder","INSERT INTO `yee_order`(id,create_date,modify_date,amout,borrowing_id,member_id,remark,swift_number,order_type,is_success,xml) VALUES ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}');");

			
		}
	};
	//旧板jtrich查询字段，字段数据必须与新版p2p模板的字段对应一致，由于新旧版的字段有些去除、有些更名、有些拆成两张表，因此下面的字段应对应上新版字段才行，
	//可以使用关联表查出，这样才能将拆表、更名的数据迁移到新版数据库
	public static final Map<String,String> jtrichSqls = new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("admin","select id,create_date,modify_date,username,password,email,name,department,is_account_enabled,is_account_locked,is_account_expired,is_credentials_expired,login_failure_count,locked_date,login_date,login_ip from bit_admin ");
			put("role","select id,create_date,modify_date,authority_list_store,description,is_system,name from bit_role ");
			put("adminRole","select admin_set_id,role_set_id from bit_admin_role ");
			put("article","select id,create_date,modify_date,author,content,down,image_path,link_url,meta_description,meta_keywords,min_content,sub_title,title,up,article_category_id from bit_article ");
			put("articleCategory","select id,create_date,modify_date,meta_description,meta_keywords,name,order_list,parent_id,grade,path from bit_article_category ");
			//关联表未插入数据，比如收入表，学历表等等
			put("member","select id,create_date,modify_date,platform_user_no,birthday,email,email_auth,gender,graduate_school,id_card,last_login,login_ip,marriage,member_type,moblie,moblie_auth,password,psypassword,true_name,user_status,username,username_md5,bankAccount_id,url from bit_member ");
			
			
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
	@Test
	public void testRole() {
		jtrichToP2PService.data2Sqls("role","d:p2p");
	}
	@Test
	public void testAdminRole() {
		jtrichToP2PService.data2Sqls("adminRole","d:p2p");
	}
	@Test
	public void testArticle() {
		jtrichToP2PService.data2Sqls("article","d:p2p");
	}
	@Test
	public void testArticleCategory() {
		jtrichToP2PService.data2Sqls("articleCategory","d:p2p");
	}
	@Test
	public void testMember() {
		jtrichToP2PService.data2Sqls("member","d:p2p");
	}
}
