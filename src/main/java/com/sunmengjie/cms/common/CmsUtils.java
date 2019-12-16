package com.sunmengjie.cms.common;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author Administrator
 *
 */
public class CmsUtils {
	
	
	/**
	 *  加盐加密
	 * @param src  明文
	 * @param salt  盐
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encry(String src,String salt) {
		return DigestUtils.md5Hex(salt + src + salt);
		/*
		byte[] md5 = DigestUtils.md5(salt + src + salt);
		
		//String enPwd = new   String(md5,"UTF-8");
		StringBuilder sb = new StringBuilder();
		for (byte b : md5) {
			sb.append(b);
		}
		return sb.toString();
*/		
		
		
	}
}
