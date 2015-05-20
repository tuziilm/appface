package com.zhanghui.appface.common;

import org.springframework.util.DigestUtils;

/**
 * 安全相关工具类
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public class SecurityUtils {
	/**
	 * 使用md5算法加密字串
	 * @param text
	 * @param salt
	 * @return
	 */
	public static String md5Encode(String text, String salt){
		return md5Encode(text,salt.hashCode());
	}
	/**
	 * 使用md5算法加密字串
	 * @param text
	 * @param salt
	 * @return
	 */
	public static String md5Encode(String text, int salt){
		String hexString=new String(DigestUtils.md5DigestAsHex(text.getBytes()));
		String first=hexString.substring(0,15);
		String second=hexString.substring(17);
		Long firstLong = Long.valueOf(first, 16)+salt;
		Long secondLong = Long.valueOf(second, 16)+salt;
		first=Long.toString(firstLong, 16);
		second=Long.toString(secondLong, 16);
		hexString=first+hexString.substring(15,17)+second;
		return new String(DigestUtils.md5DigestAsHex(hexString.getBytes()));
	}
	/**
	 * md5摘要
	 * @param text
	 * @return
	 */
	public static String md5Encode(String text){
		return new String(DigestUtils.md5DigestAsHex(text.getBytes()));
	}
	
	public static void main(String[] args){
		System.out.println(md5Encode("1234", "calvin"));
	}
}
