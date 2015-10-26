package com.ldz.Service;
/**
 * @author LDZ   
 * @date 2015年9月23日 下午3:25:42 
 */
public interface JtrichToP2PService {
	/**
	 * 将Bean名称生成指定路径下的sql文件
	 * @author LDZ   
	 * @date 2015年9月24日 下午3:09:14 
	 * @param entityName 实体名称，为转换的key
	 * @param sqlPath 目录路径，不包含文件名，文件名为entityName文件名
	 */
	public void data2Sqls(String entityName,String sqlPath);
}
