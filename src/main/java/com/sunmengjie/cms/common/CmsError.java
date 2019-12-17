package com.sunmengjie.cms.common;

public class CmsError {
	
	/**
	 * 成功
	 */
	public static final int SUCCESS = 1; 
	
	/**
	 *  内容不存在
	 */
	public static final int NOT_EXIST = 2; 
	
	/**
	 * 更新数据失败
	 */
	public static final int FAILED_UPDATE_DB = 3; 
	
	public static final int NOT_VALIDATED_ARGURMENT=4;//参数不合法
	
	/**
	 *  不需要修改
	 */
	public static final int NEEDNT_UPDATE = 5; 

}
